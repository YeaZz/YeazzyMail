package com.gmail.perhapsitisyeazz.yeazzymail.manager;

import com.gmail.perhapsitisyeazz.yeazzymail.util.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ListMail {

	public static void getMailList(Player player) {
		JsonObject object = Data.getJsonObject(player);
		JsonArray array = object.getAsJsonArray("EmailList");
		if (array.size() == 0) {
			player.sendMessage("You do not have any mails.");
			return;
		}
		ComponentBuilder builder = new ComponentBuilder();
		builder
				.append("[").color(ChatColor.DARK_GRAY)
				.append("Mail").color(ChatColor.DARK_AQUA)
				.append("] ").color(ChatColor.DARK_GRAY)
				.append("Here is your friend list: ").color(ChatColor.DARK_GREEN)
				.append("(").color(ChatColor.DARK_GRAY)
				.append(String.valueOf(array.size())).color(ChatColor.AQUA)
				.append(")").color(ChatColor.DARK_GRAY);
		int size = 0;
		for (JsonElement element : array) {
			JsonArray secondArray = element.getAsJsonArray();
			UUID uuid = UUID.fromString(secondArray.get(1).getAsString());
			Player mailAuthor = Bukkit.getPlayer(uuid);
			if (mailAuthor == null) continue;
			size++;
			builder
					.append("\n")
					.append(String.valueOf(size)).color(ChatColor.GREEN)
					.append(". ")
					.append(mailAuthor.toString())
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(secondArray.get(2).getAsString())))
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"))
					.color(ChatColor.AQUA).retain(ComponentBuilder.FormatRetention.NONE).reset();
		}
	}
}
