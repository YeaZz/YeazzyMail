package com.gmail.perhapsitisyeazz.yeazzymail.listener;

import com.gmail.perhapsitisyeazz.yeazzymail.util.Data;
import com.gmail.perhapsitisyeazz.yeazzymail.util.JoinFile;
import com.google.gson.JsonObject;
import com.moderocky.mask.internal.utility.FileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class JoinEvent implements Listener {

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		File playerData = Data.getData(player);
		if (!playerData.exists()) JoinFile.createPlayerFile(player);
		JsonObject object = Data.getJsonObject(player);
		String name = object.get("Username").getAsString();
		if (!name.equals(player.getName())) {
			object.addProperty("Username", player.getName());
			String jsonObjectToString = object.toString();
			FileManager.write(playerData, jsonObjectToString);
		}
	}
}
