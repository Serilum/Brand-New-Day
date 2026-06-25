package com.natamus.brandnewday.forge.events;

import com.natamus.brandnewday.gui.NewDayOverlay;
import com.natamus.brandnewday.util.Reference;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeGuiEvents {

	@SubscribeEvent
	public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
		event.registerAboveAll(Reference.MOD_ID, (gui, guiGraphics, partialTick, screenWidth, screenHeight) ->
			NewDayOverlay.render(guiGraphics, partialTick)
		);
	}
}
