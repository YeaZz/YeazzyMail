package com.gmail.perhapsitisyeazz.yeazzymail.manager;

import com.gmail.perhapsitisyeazz.yeazzymail.YeazzyMail;
import com.gmail.perhapsitisyeazz.yeazzymail.util.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moderocky.mask.command.Commander;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class Mail {

    private final YeazzyMail instance = YeazzyMail.getInstance();
    private final Data data = new Data();

    public void sendMail(Player sender, OfflinePlayer target, String message) {
        JsonObject targetObject = data.getJsonObject(target);
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
        data.saveObject(data.getData(target), targetObject);
    }

    public void deleteMail(Player sender, Integer integer, boolean confirm) {
        if (!confirm) {
            JsonObject object = data.getJsonObject(sender);
            JsonArray array = object.getAsJsonArray("EmailList");
            int size = array.size();
            if (integer <= 0 && integer > size) {
                sender.sendMessage("This mail doesn't exist.");
                return;
            }
            sender.sendMessage(new ComponentBuilder()
                    .append("     ")
                    .append("YES")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("YES")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mail delete " + integer + " confirm"))
                    .append("           ").retain(ComponentBuilder.FormatRetention.NONE).reset()
                    .append("NO")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("YES")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "test"))
                    .create());
            return;
        }
        JsonObject object = data.getJsonObject(sender);
        JsonArray array = object.getAsJsonArray("EmailList");
        array.remove(integer);
    }

    public void getMailList(Player player) {
        JsonObject object = data.getJsonObject(player);
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

    public void mailHelpCommand(CommandSender sender, Commander<?> commander) {
        if (sender instanceof Player) {
            ComponentBuilder builder = new ComponentBuilder();
            builder.append("Running ").color(ChatColor.DARK_GREEN);
            builder.append(instance.getName()).color(ChatColor.AQUA);
            builder.append(" v");
            builder.append(instance.getVersion());
            builder.append(".").color(ChatColor.DARK_GREEN);
            for (Map.Entry<String, String> entry : commander.getPatternDescriptions().entrySet()) {
                String pattern = entry.getKey();
                String desc = entry.getValue();
                if (!pattern.contains("help")) {
                    String command = "/" + commander.getCommand();
                    String arg = pattern.split(" ")[0].substring(0, 1).toUpperCase() + pattern.split(" ")[0].substring(1);
                    builder
                            .append("\n» ").color(ChatColor.DARK_AQUA)
                            .append("/" + commander.getCommand() + " " + pattern).color(ChatColor.GREEN)
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(
                                    ChatColor.DARK_GREEN + "Command: " + ChatColor.AQUA + arg +
                                            ChatColor.DARK_GREEN + "\nDescription: " + ChatColor.AQUA + desc +
                                            ChatColor.DARK_GREEN + "\nUsage: " + ChatColor.AQUA + command + " " + pattern +
                                            "\n \n" + ChatColor.GRAY + "Click to execute.")))
                            .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command + " " + pattern.split(" ")[0] + " "))
                            .append(" ").retain(ComponentBuilder.FormatRetention.NONE).reset();
                }
            }
            sender.sendMessage(builder.create());
        } else {
            sender.sendMessage(new ComponentBuilder()
                    .append("Running ").color(ChatColor.DARK_GREEN)
                    .append(instance.getName()).color(ChatColor.AQUA)
                    .append(" v")
                    .append(instance.getVersion())
                    .append(".").color(ChatColor.DARK_GREEN)
                    .create());
            sender.sendMessage(new ComponentBuilder()
                    .append("Only executable by players.").color(ChatColor.RED)
                    .create());
            for (String pattern : commander.getPatterns()) {
                if (!pattern.contains("help")) {
                    sender.sendMessage(new ComponentBuilder()
                            .append("» ").color(ChatColor.DARK_AQUA)
                            .append("/" + commander.getCommand() + " " + pattern).color(ChatColor.GREEN)
                            .append(" ").retain(ComponentBuilder.FormatRetention.NONE).reset()
                            .create());
                }
            }
        }
    }

    public void sendDefaultMessage(CommandSender sender) {
        ComponentBuilder builder = new ComponentBuilder();
        if (sender instanceof Player) {
            builder
                    .append("Running ").color(ChatColor.DARK_GREEN)
                    .append(instance.getName()).color(ChatColor.AQUA)
                    .append(" v")
                    .append(instance.getVersion())
                    .append(".").color(ChatColor.DARK_GREEN).reset()
                    .append("\nUse ")
                    .append("/friend help").color(ChatColor.GREEN)
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Click to execute.", ChatColor.GRAY)))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend help"))
                    .append(" to see all commands.").retain(ComponentBuilder.FormatRetention.NONE).reset();
            if (sender.isOp()) {
                builder
                        .append("\nBuilt on the ").color(ChatColor.DARK_AQUA).italic(true)
                        .append("Mask").color(ChatColor.GREEN).italic(true)
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Click to copy", ChatColor.GRAY)))
                        .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://gitlab.com/Pandaemonium/Mask"))
                        .append(" framework.").retain(ComponentBuilder.FormatRetention.NONE).reset().color(ChatColor.DARK_AQUA).italic(true);
            }
            sender.sendMessage(builder.create());
        } else {
            sender.sendMessage(new ComponentBuilder()
                    .append("Running ").color(ChatColor.DARK_GREEN)
                    .append(instance.getName()).color(ChatColor.AQUA)
                    .append(" v")
                    .append(instance.getVersion())
                    .append(".").color(ChatColor.DARK_GREEN)
                    .create());
            sender.sendMessage(new ComponentBuilder()
                    .append("Use ")
                    .append("/friend help").color(ChatColor.GREEN)
                    .append(" to see all commands.").color(ChatColor.WHITE)
                    .create());
            sender.sendMessage(new ComponentBuilder()
                    .append("Built on the ").color(ChatColor.DARK_AQUA)
                    .append("Mask").color(ChatColor.GREEN)
                    .append(" framework.").color(ChatColor.DARK_AQUA)
                    .create());
            sender.sendMessage(new ComponentBuilder()
                    .append("(").color(ChatColor.DARK_GRAY)
                    .append("https://gitlab.com/Pandaemonium/Mask").color(ChatColor.GREEN)
                    .append(")").color(ChatColor.DARK_GRAY)
                    .create());
        }
    }
}
