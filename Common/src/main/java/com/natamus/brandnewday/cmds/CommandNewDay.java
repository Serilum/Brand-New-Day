package com.natamus.brandnewday.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.brandnewday.functions.NewDayFunctions;
import com.natamus.brandnewday.util.Reference;
import com.natamus.collective.functions.MessageFunctions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandNewDay {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal(Reference.MOD_ID)
			.then(Commands.literal("preview")
			.executes((command) -> {
				MessageFunctions.sendMessage(command.getSource(), NewDayFunctions.preview());
				return 1;
			}))
			.then(Commands.literal("last")
			.executes((command) -> {
				MessageFunctions.sendMessage(command.getSource(), NewDayFunctions.last());
				return 1;
			}))
		);
	}
}
