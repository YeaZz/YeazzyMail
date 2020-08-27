package com.gmail.perhapsitisyeazz.yeazzymail.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moderocky.mask.internal.utility.FileManager;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.util.UUID;

import static com.gmail.perhapsitisyeazz.yeazzymail.YeazzyMail.storingFile;

public class Data {

	@SuppressWarnings("deprecation")
	public JsonObject getJsonObject(OfflinePlayer player) {
		String playerJsonString = getStringObject(player);
		return (JsonObject) new JsonParser().parse(playerJsonString);
	}

	public String getStringObject(OfflinePlayer player) {
		File playerData = getData(player);
		return FileManager.read(playerData);
	}

	public File getData(OfflinePlayer player) {
		UUID playerUniqueId = player.getUniqueId();
		return new File(storingFile, playerUniqueId + ".json");
	}

	public void saveObject(File file, JsonObject jsonObject) {
		String string = jsonObject.toString();
		FileManager.write(file, string);
	}
}
