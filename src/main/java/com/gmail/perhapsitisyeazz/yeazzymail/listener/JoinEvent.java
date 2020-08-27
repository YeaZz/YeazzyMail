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

	private final JoinFile joinFile = new JoinFile();
	private final Data data = new Data();

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		File playerData = data.getData(player);
		if (!playerData.exists()) joinFile.createPlayerFile(player);
		JsonObject object = data.getJsonObject(player);
		String name = object.get("Username").getAsString();
		if (!name.equals(player.getName())) {
			object.addProperty("Username", player.getName());
			String jsonObjectToString = object.toString();
			FileManager.write(playerData, jsonObjectToString);
		}
	}
}
