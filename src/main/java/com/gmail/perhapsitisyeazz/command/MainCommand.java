package com.gmail.perhapsitisyeazz.command;

import com.gmail.perhapsitisyeazz.manager.HelpCommand;
import com.moderocky.mask.command.Commander;
import com.moderocky.mask.template.WrappedCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends Commander<CommandSender> implements WrappedCommand {


	@Override
	protected CommandImpl create() {
		return command("mail")
				.arg("help", sender -> HelpCommand.mailHelpCommand(sender, this))
				.arg("list")
				.arg("send");
	}

	@Override
	public CommandSingleAction<CommandSender> getDefault() {
		return HelpCommand::sendHelpMessage;
	}

	@Override
	public @NotNull List<String> getAliases() {
		return new ArrayList<>();
	}

	@Override
	public @NotNull String getUsage() {
		return "/" + getCommand();
	}

	@Override
	public @NotNull String getDescription() {
		return "This is the main command";
	}

	@Override
	public @Nullable String getPermission() {
		return null;
	}

	@Override
	public @Nullable String getPermissionMessage() {
		return null;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		return execute(sender, args);
	}
}
