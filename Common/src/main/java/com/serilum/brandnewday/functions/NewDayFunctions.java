package com.serilum.brandnewday.functions;

import com.serilum.brandnewday.config.ConfigHandler;
import com.serilum.brandnewday.data.Variables;
import com.serilum.brandnewday.gui.NewDayOverlay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.storage.LevelResource;

public class NewDayFunctions {
	public static void runNewDay(long day) {
		if (ConfigHandler.playSound) {
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F));
		}

		NewDayOverlay.trigger(day);
	}

	public static MutableComponent preview() {
		Minecraft mc = Minecraft.getInstance();

		if (ConfigHandler.showStats && mc.getConnection() != null) {
			Variables.previewPending = true;
			requestStats(mc);
		}
		else {
			showPreview();
		}

		return message("collective.brandnewday.message.previewing", ChatFormatting.DARK_GREEN);
	}

	private static void showPreview() {
		Minecraft mc = Minecraft.getInstance();
		NewDayOverlay.preview(currentDay(mc));
	}

	private static long currentDay(Minecraft mc) {
		if (mc.level == null) {
			return 0L;
		}

		return Math.floorDiv(mc.level.getDayTime(), 24000L);
	}

	public static MutableComponent last() {
		if (NewDayOverlay.showLast()) {
			return message("collective.brandnewday.message.showinglast", ChatFormatting.DARK_GREEN);
		}

		return message("collective.brandnewday.message.nolast", ChatFormatting.RED);
	}

	private static MutableComponent message(String key, ChatFormatting colour) {
		return Component.translatable(key).withStyle(colour);
	}

	public static void startNewDay(Minecraft mc, long day) {
		if (ConfigHandler.showStats && mc.getConnection() != null) {
			Variables.pendingDay = day;
			requestStats(mc);
			return;
		}

		runNewDay(day);
	}

	public static void onWorldLoad(Minecraft mc) {
		NewDayOverlay.onWorldLoad(worldKey(mc));

		if (ConfigHandler.showStats) {
			requestStats(mc);
		}
	}

	public static void onStatsReceived() {
		if (Variables.pendingDay != Long.MIN_VALUE) {
			long day = Variables.pendingDay;
			Variables.pendingDay = Long.MIN_VALUE;
			runNewDay(day);
			return;
		}

		if (Variables.previewPending) {
			Variables.previewPending = false;
			showPreview();
			return;
		}

		NewDayOverlay.seedStats();
	}

	private static void requestStats(Minecraft mc) {
		if (mc.getConnection() != null) {
			mc.getConnection().send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.REQUEST_STATS));
		}
	}

	public static void reset() {
		Variables.pendingDay = Long.MIN_VALUE;
		Variables.previewPending = false;
	}

	private static String worldKey(Minecraft mc) {
		if (mc.hasSingleplayerServer()) {
			return "sp-" + mc.getSingleplayerServer().getWorldPath(LevelResource.ROOT).normalize().getFileName().toString();
		}

		ServerData server = mc.getCurrentServer();
		if (server != null) {
			return "mp-" + server.ip;
		}

		return null;
	}
}
