package com.gmail.perhapsitisyeazz.manager;

import com.gmail.perhapsitisyeazz.util.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
		sender.sendMessage(new ComponentBuilder()
				.append("YES")
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("YES")))
				.append("NO")
				.create());
	}

	public static void confirmDeleteMail(Player sender, Integer integer) {
		JsonObject object = Data.getJsonObject(sender);
		JsonArray array = object.getAsJsonArray("EmailList");
		array.remove(integer);
	}
}
