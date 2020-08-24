package com.gmail.perhapsitisyeazz.yeazzymail;

import com.gmail.perhapsitisyeazz.yeazzymail.command.MainCommand;
import com.gmail.perhapsitisyeazz.yeazzymail.listener.JoinEvent;
import com.moderocky.mask.template.BukkitPlugin;

import java.io.File;

public class YeazzyMail extends BukkitPlugin {

    private static YeazzyMail instance;

    public static YeazzyMail getInstance() {
        return instance;
    }

    public static File storingFile = new File("plugins/YeazzyMail/PlayerData/");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void startup() {
        instance = this;
        if (!storingFile.exists()) storingFile.mkdirs();
        register(
                new JoinEvent()
        );
        register(
                new MainCommand()
        );
    }

    @Override
    public void disable() {
        instance = null;
    }
}
