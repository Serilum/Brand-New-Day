package com.serilum.brandnewday.neoforge.events;

import com.serilum.brandnewday.cmds.CommandNewDay;
import com.serilum.brandnewday.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class NeoForgeClientEvents {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post e) {
		ClientEvents.onClientTick(Minecraft.getInstance());
	}

	@SubscribeEvent
	public static void registerCommands(RegisterClientCommandsEvent e) {
		CommandNewDay.register(e.getDispatcher());
	}
}
