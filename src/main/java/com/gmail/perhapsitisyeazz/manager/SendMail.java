package com.gmail.perhapsitisyeazz.manager;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SendMail {

	public static void sendMail(Player sender, OfflinePlayer target, String message) {
		sender.sendMessage(message);
	}
}
