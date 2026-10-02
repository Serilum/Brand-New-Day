package com.serilum.brandnewday.forge.events;

import com.serilum.brandnewday.gui.NewDayOverlay;
import com.natamus.collective.globalcallbacks.CollectiveGuiCallback;

public class ForgeGuiEvents {
	public static void register() {
		CollectiveGuiCallback.ON_GUI_RENDER.register((guiGraphics, deltaTracker) -> {
			NewDayOverlay.render(guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(false));
		});
	}
}
