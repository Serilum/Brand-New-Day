package com.natamus.brandnewday;

import com.natamus.brandnewday.events.ClientEvents;
import com.natamus.brandnewday.fabric.cmds.FabricCommandNewDay;
import com.natamus.brandnewday.gui.NewDayOverlay;
import com.natamus.brandnewday.util.Reference;
import com.natamus.collective.check.ShouldLoadCheck;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

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

		HudRenderCallback.EVENT.register((g, dt) -> NewDayOverlay.render(g, dt.getGameTimeDeltaPartialTick(false)));

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> FabricCommandNewDay.register(dispatcher));
	}
}
