package com.gmail.perhapsitisyeazz.manager;

import com.gmail.perhapsitisyeazz.util.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SendMail {

	public static void sendMail(Player sender, OfflinePlayer target, String message) {
		JsonObject object = Data.getJsonObject(target);
		JsonArray array = object.getAsJsonArray("EmailList");
		for (JsonElement mails : array) {
			JsonArray finalArray = mails.getAsJsonArray();
			if (finalArray.get(1).getAsString().equals(sender.getUniqueId().toString()) && finalArray.get(2).getAsString().equals(message)) {
				sender.sendMessage("Same message already sent.");
				return;
			}
		}
	}
}
