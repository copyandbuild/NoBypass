package com.arctic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoBypass extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("nobypass").setExecutor(this::reloadCommand);
        saveDefaultConfig();
        getLogger().info(ChatColor.GREEN + "NoBypass has been enabled");
    }


        FileConfiguration config = getConfig();
        List<String> swearWords = config.getStringList("words");
        Boolean isMsgTrue = config.getBoolean("messageEnable", false);
        String responses = config.getString("message");
        Boolean isTrue = config.getBoolean("commandEnable", false);



    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage().toLowerCase();
        Player player = event.getPlayer();
        for (String word : swearWords) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word.toLowerCase()) + "\\b");
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                event.setCancelled(true);
                if(isMsgTrue.equals(true)) {
                    player.sendMessage(ChatColor.RED + responses);
                }
                if (isTrue.equals(true)) {
                    punishPlayer(player);
                }
                return;
            }
        }
    }

    public boolean reloadCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            FileConfiguration config = getConfig();
            swearWords = config.getStringList("words");
            isMsgTrue = config.getBoolean("messageEnable", false);
            responses = config.getString("message");
            isTrue = config.getBoolean("commandEnable", false);

            sender.sendMessage(ChatColor.GREEN + "NoBypass config.yml successfully reloaded.");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /nobypass reload");
            return false;
        }
    }


    private void punishPlayer(Player player) {
        FileConfiguration config = getConfig();
        String Command = config.getString("command");
            if (Command != null) {
                String command = Command.replace("%player%", player.getName());
                getServer().getScheduler().runTask(this, () -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                });
            }
    }

}

