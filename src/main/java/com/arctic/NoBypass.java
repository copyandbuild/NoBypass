package com.arctic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public class NoBypass extends JavaPlugin implements Listener, TabCompleter {
    private List<String> swearWords;
    private boolean isMsgTrue;
    private String responses;
    private String responsecommand;
    private boolean isTrue;
    private String prefix = "§8[§aNoBypass§8] §7";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("nobypass").setExecutor(this::reloadCommand);
        saveDefaultConfig();
        loadConfigValues();
    }

    private void loadConfigValues() {
        FileConfiguration config = getConfig();
        swearWords = config.getStringList("words").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        isMsgTrue = config.getBoolean("messageEnable", false);
        responses = config.getString("message", "");
        responsecommand = config.getString("command-message", "");
        isTrue = config.getBoolean("commandEnable", false);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        String[] words = message.split("\\s+");

        for (String word : words) {
            if (swearWords.contains(word)) {
                event.setCancelled(true);
                if (isMsgTrue) {
                    player.sendMessage(ChatColor.RED + responses);
                }
                if (isTrue) {
                    punishPlayer(player);
                }
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();

        for (String word : swearWords) {
            if (command.contains(word)) {
                event.setCancelled(true);
                
                player.sendMessage(responsecommand);
                return;
            }
        }
    }

    public boolean reloadCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            loadConfigValues();
            sender.sendMessage(prefix + "config §asuccessfully§7 reloaded.");
            return true;
        } else {
            sender.sendMessage(prefix + "Usage: §c/nobypass reload");
            return false;
        }
    }

    private void punishPlayer(Player player) {
        FileConfiguration config = getConfig();
        String command = config.getString("command");
        if (command != null) {
            String executedCommand = command.replace("%player%", player.getName());
            getServer().getScheduler().runTask(this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), executedCommand));
        }
    }
}