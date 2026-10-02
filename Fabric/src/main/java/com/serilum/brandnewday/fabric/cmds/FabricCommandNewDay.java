package com.serilum.brandnewday.fabric.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.serilum.brandnewday.functions.NewDayFunctions;
import com.serilum.brandnewday.util.Reference;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class FabricCommandNewDay {
	public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
		dispatcher.register(ClientCommands.literal(Reference.MOD_ID)
			.then(ClientCommands.literal("preview")
			.executes((command) -> {
				command.getSource().sendFeedback(NewDayFunctions.preview());
				return 1;
			}))
			.then(ClientCommands.literal("last")
			.executes((command) -> {
				command.getSource().sendFeedback(NewDayFunctions.last());
				return 1;
			}))
		);
	}
}
