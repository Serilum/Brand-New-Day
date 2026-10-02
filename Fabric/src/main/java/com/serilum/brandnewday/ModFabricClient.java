package com.serilum.brandnewday;

import com.serilum.brandnewday.events.ClientEvents;
import com.serilum.brandnewday.fabric.cmds.FabricCommandNewDay;
import com.serilum.brandnewday.gui.NewDayOverlay;
import com.serilum.brandnewday.util.Reference;
import com.natamus.collective.check.ShouldLoadCheck;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		registerEvents();
	}
	
	private void registerEvents() {
		ClientTickEvents.END_CLIENT_TICK.register(ClientEvents::onClientTick);

		HudRenderCallback.EVENT.register((GuiGraphics guiGraphics, float tickDelta) -> NewDayOverlay.render(guiGraphics, tickDelta));

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> FabricCommandNewDay.register(dispatcher));
	}
}
