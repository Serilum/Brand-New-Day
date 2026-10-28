package com.serilum.brandnewday.gui;

import com.serilum.brandnewday.config.ConfigHandler;
import com.serilum.brandnewday.gui.module.NewDayContext;
import com.serilum.brandnewday.gui.module.NewDayModule;
import com.serilum.brandnewday.gui.module.impl.DayModule;
import com.serilum.brandnewday.gui.module.impl.HeaderModule;
import com.serilum.brandnewday.gui.module.impl.StatModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.List;

public final class NewDayOverlay {
	private static final List<NewDayModule> MODULES = List.of(
		new HeaderModule(),
		new DayModule(),
		new StatModule()
	);

	private static final float REFERENCE_GUI_SCALE = 4.0f;

	private static int ticksLeft = 0;
	private static long day = 0;
	private static long previousDay = Long.MIN_VALUE;

	public static void trigger(long day) {
		NewDayOverlay.day = day;
		NewDayOverlay.previousDay = day;
		ticksLeft = ConfigHandler.newDayScreenDurationTicks;

		for (NewDayModule module : MODULES) {
			if (module.isEnabled()) {
				module.onNewDay();
			}
		}
	}

	public static void preview(long day) {
		NewDayOverlay.day = day;
		ticksLeft = ConfigHandler.newDayScreenDurationTicks;

		for (NewDayModule module : MODULES) {
			if (module.isEnabled()) {
				module.onPreview();
			}
		}
	}

	public static boolean showLast() {
		if (previousDay == Long.MIN_VALUE) {
			return false;
		}

		day = previousDay;
		ticksLeft = ConfigHandler.newDayScreenDurationTicks;

		for (NewDayModule module : MODULES) {
			if (module.isEnabled()) {
				module.onShowLast();
			}
		}

		return true;
	}

	public static void clientTick() {
		if (ticksLeft > 0) {
			ticksLeft--;
		}
	}

	public static boolean isActive() {
		return ticksLeft > 0;
	}

	public static void reset() {
		ticksLeft = 0;
		previousDay = Long.MIN_VALUE;

		for (NewDayModule module : MODULES) {
			module.reset();
		}
	}

	public static void onWorldLoad(String worldKey) {
		for (NewDayModule module : MODULES) {
			module.onWorldLoad(worldKey);
		}
	}

	public static void seedStats() {
		for (NewDayModule module : MODULES) {
			module.seedFromStats();
		}
	}

	public static void render(GuiGraphicsExtractor guiGraphics, float partial) {
		if (ticksLeft <= 0) {
			return;
		}

		float remaining = ticksLeft - partial;
		float fadeIn = (ConfigHandler.newDayScreenDurationTicks - remaining) / (float) ConfigHandler.newDayScreenFadeInTicks;
		float fadeOut = remaining / (float) ConfigHandler.newDayScreenFadeOutTicks;

		float fade = Math.min(fadeIn, fadeOut);
		fade = Math.max(0f, Math.min(1f, fade));

		int alpha = (int) (fade * 255f) & 0xFF;
		if (alpha == 0) {
			return;
		}

		float elapsed = ConfigHandler.newDayScreenDurationTicks - remaining;
		int guiScale = (int) Minecraft.getInstance().getWindow().getGuiScale();
		float scale = ConfigHandler.guiScaleModifier * guiScale / REFERENCE_GUI_SCALE;
		NewDayContext context = new NewDayContext(guiGraphics.guiWidth(), guiGraphics.guiHeight(), alpha, day, elapsed, scale);

		for (NewDayModule module : MODULES) {
			if (module.isEnabled()) {
				module.render(guiGraphics, context);
			}
		}
	}
}
