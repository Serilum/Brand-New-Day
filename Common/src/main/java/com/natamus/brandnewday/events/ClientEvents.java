package com.natamus.brandnewday.events;

import com.natamus.brandnewday.data.Variables;
import com.natamus.brandnewday.gui.NewDayOverlay;
import com.natamus.brandnewday.functions.NewDayFunctions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class ClientEvents {
	private static final int WORLD_LOAD_SETTLE_TICKS = 20;

	public static void onClientTick(Minecraft mc) {
		ClientLevel level = mc.level;
		if (level == null) {
			Variables.lastDay = Long.MIN_VALUE;
			Variables.loadSettleTicks = 0;
			NewDayFunctions.reset();
			NewDayOverlay.reset();
			return;
		}

		NewDayOverlay.clientTick();

		long time = level.getOverworldClockTime();
		long day = Math.floorDiv(time, 24000L);

		if (Variables.lastDay == Long.MIN_VALUE) {
			Variables.lastDay = day;
			Variables.loadSettleTicks = WORLD_LOAD_SETTLE_TICKS;
			NewDayFunctions.onWorldLoad(mc);
			return;
		}

		if (Variables.loadSettleTicks > 0) {
			Variables.loadSettleTicks--;
			Variables.lastDay = day;
			return;
		}

		if (day > Variables.lastDay) {
			Variables.lastDay = day;
			NewDayFunctions.startNewDay(mc, day);
		}
	}
}
