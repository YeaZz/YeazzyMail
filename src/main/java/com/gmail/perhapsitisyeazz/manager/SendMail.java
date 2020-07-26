package com.gmail.perhapsitisyeazz.manager;

import com.gmail.perhapsitisyeazz.util.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SendMail {

	public static void sendMail(Player sender, OfflinePlayer target, String message) {
		JsonObject targetObject = Data.getJsonObject(target);
		boolean targetBoolean = targetObject.get("hasEmailToggle").getAsBoolean();
		if (!targetBoolean) {
			sender.sendMessage(new ComponentBuilder()
					.append("[").color(ChatColor.DARK_GRAY)
					.append("Mail").color(ChatColor.DARK_AQUA)
					.append("] ").color(ChatColor.DARK_GRAY)
					.append(target.getName()).color(ChatColor.AQUA)
					.append(" doesn't accept mails.").color(ChatColor.DARK_GREEN)
					.create());
			return;
		}
		JsonArray targetArray = targetObject.getAsJsonArray("EmailList");
		for (JsonElement mails : targetArray) {
			JsonArray finalArray = mails.getAsJsonArray();
			if (finalArray.get(1).getAsString().equals(sender.getUniqueId().toString()) && finalArray.get(2).getAsString().equals(message)) {
				sender.sendMessage("Same message already sent.");
				return;
			}
		}
		JsonArray senderMail = new JsonArray();
		senderMail.add(sender.getUniqueId().toString());
		senderMail.add(message);
		targetArray.addAll(senderMail);
		Data.saveObject(Data.getData(target), targetObject);
	}
}
