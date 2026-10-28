package com.serilum.brandnewday.neoforge.events;

import com.serilum.brandnewday.gui.NewDayOverlay;
import com.serilum.brandnewday.util.Reference;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class NeoForgeGuiEvents {
	public static void onRegisterHud(RegisterGuiLayersEvent e) {
		e.registerAboveAll(Identifier.fromNamespaceAndPath(Reference.MOD_ID, "new_day"), (g, dt) -> NewDayOverlay.render(g, dt.getGameTimeDeltaPartialTick(false)));
	}
}
