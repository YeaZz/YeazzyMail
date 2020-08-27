package com.gmail.perhapsitisyeazz.yeazzymail.command;

import com.gmail.perhapsitisyeazz.yeazzymail.manager.Mail;
import com.moderocky.mask.command.ArgInteger;
import com.moderocky.mask.command.ArgOfflinePlayer;
import com.moderocky.mask.command.ArgString;
import com.moderocky.mask.command.ArgStringFinal;
import com.moderocky.mask.command.Commander;
import com.moderocky.mask.template.WrappedCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends Commander<CommandSender> implements WrappedCommand {

	private final Mail mail = new Mail();

	@Override
	protected CommandImpl create() {
		return command("mail")
				.arg("help", sender -> mail.mailHelpCommand(sender, this))
				.arg("list", desc("List your mails"), sender -> mail.getMailList((Player) sender))
				.arg("send", desc("Send a mail"), sender -> sender.sendMessage("Put a string"),
						arg(
								(sender, input) -> mail.sendMail((Player) sender, (OfflinePlayer) input[0], (String) input[1]),
								new ArgOfflinePlayer(),
								new ArgStringFinal()
						))
				.arg("delete", desc("Delete a mail"), sender -> sender.sendMessage("Put a integer"),
						arg(
								(sender, input) -> mail.deleteMail((Player) sender, (Integer) input[0], input[1].equals("confirm")),
								new ArgInteger().setRequired(true),
								new ArgString().setRequired(false)
						));
	}

	@Override
	public CommandSingleAction<CommandSender> getDefault() {
		return mail::sendDefaultMessage;
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
