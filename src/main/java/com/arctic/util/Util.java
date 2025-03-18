package com.arctic.util;

import com.arctic.NoBypass;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

import java.util.HashSet;
import java.util.stream.Collectors;

public class Util {

    private NoBypass main;
    public Util instance;

    public String prefix = "§8[§aNoBypass§8] §7";

    public Util(NoBypass main) {
        this.main = main;
        instance = this;
        loadConfigValues();
    }

    public void loadConfigValues() {
        FileConfiguration config = main.getConfig();
        main.swearWords = new HashSet<>(config.getStringList("words").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList()));
        main.isMessageEnabled = config.getBoolean("messageEnable", false);
        main.responseOnMessage = config.getString("message", "");
        main.responseOnCommand = config.getString("command-message", "");
        main.isPunishCmdEnabled = config.getBoolean("commandEnable", false);
    }

    public String replacePlaceholders(String message, Player player) {
        return message.replace("%player%", player.getName())
                .replace("%displayname%", player.getDisplayName())
                .replace("%address%", player.getAddress().getHostString())
                .replace("%level%", String.valueOf(player.getLevel()))
                .replace("%ping%", String.valueOf(player.getPing()))
                .replace("%prefix%", prefix);
    }

    public void punishPlayer(Player player) {
        FileConfiguration config = main.getConfig();
        String punishCommand = config.getString("command");
        if (punishCommand != null && !punishCommand.isEmpty()) {
            String finalCommand = replacePlaceholders(punishCommand, player);
            getServer().getScheduler().runTask(main, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand));
        }
    }

}
