package com.natamus.brandnewday.forge.events;

import com.natamus.brandnewday.cmds.CommandNewDay;
import com.natamus.brandnewday.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeClientEvents {
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent e) {
		if (e.phase.equals(TickEvent.Phase.START)) {
			ClientEvents.onClientTick(Minecraft.getInstance());
		}
	}

	@SubscribeEvent
	public static void registerCommands(RegisterClientCommandsEvent e) {
		CommandNewDay.register(e.getDispatcher());
	}
}
