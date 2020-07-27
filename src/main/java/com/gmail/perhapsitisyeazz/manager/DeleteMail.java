package com.gmail.perhapsitisyeazz.manager;

import com.gmail.perhapsitisyeazz.util.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

public class DeleteMail {

	public static void deleteMail(Player sender, Integer integer) {
		JsonObject object = Data.getJsonObject(sender);
		JsonArray array = object.getAsJsonArray("EmailList");
		int size = array.size();
		if (integer <= 0 && integer > size) {
			sender.sendMessage("This mail doesn't exist.");
			return;
		}
		sender.sendMessage("Are you sure ?");
		confirmDeleteMail(sender, integer);
	}

	public static void confirmDeleteMail(Player sender, Integer integer) {
		JsonObject object = Data.getJsonObject(sender);
		JsonArray array = object.getAsJsonArray("EmailList");
		array.remove(integer);
	}
}
