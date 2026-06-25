package com.natamus.brandnewday.forge.events;

import com.natamus.brandnewday.cmds.CommandNewDay;
import com.natamus.brandnewday.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeClientEvents {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeClientEvents.class);
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre e) {
		ClientEvents.onClientTick(Minecraft.getInstance());
	}

	@SubscribeEvent
	public static void registerCommands(RegisterClientCommandsEvent e) {
		CommandNewDay.register(e.getDispatcher());
	}
}
