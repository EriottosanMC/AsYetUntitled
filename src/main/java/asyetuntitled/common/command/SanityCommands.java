package asyetuntitled.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import asyetuntitled.SanityConfig;
import asyetuntitled.common.util.capability.PlayerSanityProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class SanityCommands {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		dispatcher.register(Commands.literal("sanity").requires((commandSource) -> {
			return commandSource.hasPermission(2);
		}).then(Commands.literal("set").then(Commands.argument("sanity", IntegerArgumentType.integer(0, SanityConfig.MAX_SANITY.get())).executes((command) -> {
			return setSanity(command.getSource(), IntegerArgumentType.getInteger(command, "sanity"), false);
		}))).then(Commands.literal("change").then(Commands.argument("sanity", IntegerArgumentType.integer(-SanityConfig.MAX_SANITY.get(), SanityConfig.MAX_SANITY.get())).executes((command) -> {
			return changeSanity(command.getSource(), IntegerArgumentType.getInteger(command, "sanity"), false);
		}))).then(Commands.literal("force").then(Commands.literal("set").then(Commands.argument("sanity", IntegerArgumentType.integer(0, SanityConfig.MAX_SANITY.get())).executes((command) -> {
			return setSanity(command.getSource(), IntegerArgumentType.getInteger(command, "sanity"), true);
		}))).then(Commands.literal("change").then(Commands.argument("sanity", IntegerArgumentType.integer(-SanityConfig.MAX_SANITY.get(), SanityConfig.MAX_SANITY.get())).executes((command) -> {
			return changeSanity(command.getSource(), IntegerArgumentType.getInteger(command, "sanity"), true);
		})))));
	}
	
	private static int setSanity(CommandSourceStack command, int set, boolean force) throws CommandSyntaxException
	{
		Player player = command.getPlayerOrException();
		
		player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
			sanity.setSanity(player, set, force);
		});		
		command.sendSuccess(new TranslatableComponent("commands.asyetuntitled.sanity.set", set, force), false);

		return set;
	}
	
	private static int changeSanity(CommandSourceStack command, int change, boolean force) throws CommandSyntaxException
	{
		Player player = command.getPlayerOrException();
		
		player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
			sanity.changeSanity(player, change, force);
		});		
		command.sendSuccess(new TranslatableComponent("commands.asyetuntitled.sanity.change", change, force), false);

		return change;
	}
}
