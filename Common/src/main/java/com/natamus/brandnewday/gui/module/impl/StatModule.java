package com.natamus.brandnewday.gui.module.impl;

import com.natamus.brandnewday.config.ConfigHandler;
import com.natamus.brandnewday.gui.module.NewDayContext;
import com.natamus.brandnewday.gui.module.NewDayModule;
import com.natamus.brandnewday.util.StatStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class StatModule implements NewDayModule {
	private static final int LEFT_MARGIN = 10;
	private static final int COLUMN_GAP = 6;

	private interface StatValue {
		long get(StatsCounter stats);
	}

	private record StatLine(String labelKey, String valueKey, StatValue value, BooleanSupplier enabled) { }

	private static final List<StatLine> LINES = List.of(
		new StatLine("collective.brandnewday.stat.mobs_slain", null, stats -> stats.getValue(Stats.CUSTOM, Stats.MOB_KILLS), () -> ConfigHandler.showMobsKilled),
		new StatLine("collective.brandnewday.stat.deaths", null, stats -> stats.getValue(Stats.CUSTOM, Stats.DEATHS), () -> ConfigHandler.showDeaths),
		new StatLine("collective.brandnewday.stat.blocks_broken", null, StatModule::totalBlocksBroken, () -> ConfigHandler.showBlocksBroken),
		new StatLine("collective.brandnewday.stat.items_crafted", null, StatModule::totalItemsCrafted, () -> ConfigHandler.showItemsCrafted),
		new StatLine("collective.brandnewday.stat.blocks_traveled", null, StatModule::distanceTraveled, () -> ConfigHandler.showDistanceTraveled)
	);

	private final long[] snapshot = new long[LINES.size()];
	private final long[] delta = new long[LINES.size()];
	private final long[] lastDelta = new long[LINES.size()];
	private boolean primed = false;
	private String worldKey = null;

	@Override
	public void onWorldLoad(String worldKey) {
		this.worldKey = worldKey;
		primed = false;

		Map<String, Long> saved = StatStorage.load(worldKey);
		if (saved == null) {
			return;
		}

		for (int i = 0; i < LINES.size(); i++) {
			snapshot[i] = saved.getOrDefault(LINES.get(i).labelKey(), 0L);
		}
		primed = true;
	}

	@Override
	public void seedFromStats() {
		if (primed) {
			return;
		}

		StatsCounter stats = currentStats();
		if (stats == null) {
			return;
		}

		for (int i = 0; i < LINES.size(); i++) {
			snapshot[i] = LINES.get(i).value().get(stats);
		}
		primed = true;
		save();
	}

	@Override
	public void onPreview() {
		StatsCounter stats = currentStats();
		if (stats == null) {
			return;
		}

		for (int i = 0; i < LINES.size(); i++) {
			long current = LINES.get(i).value().get(stats);
			delta[i] = computeDelta(i, current);
		}
	}

	@Override
	public void onShowLast() {
		System.arraycopy(lastDelta, 0, delta, 0, delta.length);
	}

	@Override
	public void onNewDay() {
		StatsCounter stats = currentStats();
		if (stats == null) {
			return;
		}

		for (int i = 0; i < LINES.size(); i++) {
			long current = LINES.get(i).value().get(stats);
			delta[i] = computeDelta(i, current);
			snapshot[i] = current;
		}
		System.arraycopy(delta, 0, lastDelta, 0, delta.length);
		primed = true;
		save();
	}

	@Override
	public void reset() {
		primed = false;
	}

	@Override
	public boolean isEnabled() {
		return ConfigHandler.showStats;
	}

	@Override
	public void render(GuiGraphics guiGraphics, NewDayContext context) {
		Font font = Minecraft.getInstance().font;

		List<Integer> order = new ArrayList<>();
		for (int i = 0; i < LINES.size(); i++) {
			if (LINES.get(i).enabled().getAsBoolean()) {
				order.add(i);
			}
		}
		if (order.isEmpty()) {
			return;
		}
		order.sort(Comparator.comparing(StatModule::labelText, String.CASE_INSENSITIVE_ORDER));

		int labelWidth = 0;
		for (int i : order) {
			int width = font.width(Component.translatable(LINES.get(i).labelKey()));
			labelWidth = Math.max(labelWidth, width);
		}

		int valueX = labelWidth + COLUMN_GAP;
		int keyColour = ARGB.color(context.alpha, ConfigHandler.statsKeyTextRGB_R, ConfigHandler.statsKeyTextRGB_G, ConfigHandler.statsKeyTextRGB_B);
		int valueColour = ARGB.color(context.alpha, ConfigHandler.statsValueTextRGB_R, ConfigHandler.statsValueTextRGB_G, ConfigHandler.statsValueTextRGB_B);

		int lineStep = font.lineHeight + NewDayContext.SPACING;
		int blockHeight = (order.size() + 1) * lineStep - NewDayContext.SPACING;

		guiGraphics.pose().pushMatrix();
		guiGraphics.pose().translate(LEFT_MARGIN, context.screenHeight / 2f);
		guiGraphics.pose().scale(context.scale, context.scale);

		int y = -blockHeight / 2;
		Component summary = Component.translatable("collective.brandnewday.summary").withStyle(ChatFormatting.UNDERLINE);
		guiGraphics.drawString(font, summary, 0, y, keyColour);
		y += lineStep;

		for (int i : order) {
			StatLine line = LINES.get(i);
			guiGraphics.drawString(font, Component.translatable(line.labelKey()), 0, y, keyColour);
			guiGraphics.drawString(font, valueComponent(line, delta[i]), valueX, y, valueColour);
			y += lineStep;
		}

		guiGraphics.pose().popMatrix();
	}

	private long computeDelta(int index, long current) {
		if (!primed) {
			return 0L;
		}

		return Math.max(0L, current - snapshot[index]);
	}

	private static String labelText(int index) {
		return Component.translatable(LINES.get(index).labelKey()).getString();
	}

	private static Component valueComponent(StatLine line, long amount) {
		if (line.valueKey() == null) {
			return Component.literal(Long.toString(amount));
		}

		return Component.translatable(line.valueKey(), amount);
	}

	private void save() {
		Map<String, Long> data = new HashMap<>();
		for (int i = 0; i < LINES.size(); i++) {
			data.put(LINES.get(i).labelKey(), snapshot[i]);
		}
		StatStorage.save(worldKey, data);
	}

	private static StatsCounter currentStats() {
		Minecraft mc = Minecraft.getInstance();
		return mc.player == null ? null : mc.player.getStats();
	}

	private static long totalBlocksBroken(StatsCounter stats) {
		long total = 0L;
		for (Block block : BuiltInRegistries.BLOCK) {
			total += stats.getValue(Stats.BLOCK_MINED, block);
		}

		return total;
	}

	private static long totalItemsCrafted(StatsCounter stats) {
		long total = 0L;
		for (Item item : BuiltInRegistries.ITEM) {
			total += stats.getValue(Stats.ITEM_CRAFTED, item);
		}

		return total;
	}

	private static final Identifier[] DISTANCE_STATS = {
		Stats.WALK_ONE_CM, Stats.SPRINT_ONE_CM, Stats.CROUCH_ONE_CM, Stats.SWIM_ONE_CM,
		Stats.WALK_ON_WATER_ONE_CM, Stats.WALK_UNDER_WATER_ONE_CM, Stats.CLIMB_ONE_CM, Stats.FLY_ONE_CM,
		Stats.MINECART_ONE_CM, Stats.BOAT_ONE_CM, Stats.PIG_ONE_CM, Stats.HORSE_ONE_CM, Stats.STRIDER_ONE_CM,
		Stats.AVIATE_ONE_CM, Stats.HAPPY_GHAST_ONE_CM, Stats.NAUTILUS_ONE_CM
	};

	private static long distanceTraveled(StatsCounter stats) {
		long cm = 0L;
		for (Identifier stat : DISTANCE_STATS) {
			cm += stats.getValue(Stats.CUSTOM, stat);
		}

		return cm / 100L;
	}
}
