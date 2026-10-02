package com.serilum.brandnewday.forge.events;

import com.serilum.brandnewday.gui.NewDayOverlay;
import com.serilum.brandnewday.util.Reference;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;

public class ForgeGuiEvents {
	public static void onAddGuiLayers(AddGuiOverlayLayersEvent e) {
		e.getLayeredDraw().add(Identifier.fromNamespaceAndPath(Reference.MOD_ID, "new_day"), (g, dt) -> NewDayOverlay.render(g, dt.getGameTimeDeltaPartialTick(false)));
	}
}
