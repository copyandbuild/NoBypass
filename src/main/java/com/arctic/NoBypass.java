package com.arctic;

import com.arctic.events.PlayerChat;
import com.arctic.events.PlayerCommand;
import com.arctic.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class NoBypass extends JavaPlugin implements Listener {

    private Util util;

    public Set<String> swearWords;
    public boolean isMessageEnabled;
    public String responseOnMessage;
    public String responseOnCommand;
    public boolean isPunishCmdEnabled;

    @Override
    public void onEnable() {
        util = new Util(this);

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new PlayerCommand(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(this), this);

        saveDefaultConfig();
        util.loadConfigValues();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("nobypass")) {
            PlayerCommand playerCommand = new PlayerCommand(this);
            return playerCommand.reloadCommand(sender, command, label, args);
        }
        return false;
    }

    public boolean containsSwearWord(String text) {
        return swearWords.stream().anyMatch(text::contains);
    }

    public void onSwearWord(Player player, boolean command) {
        if (isMessageEnabled) {
            String message = util.replacePlaceholders(command ? responseOnCommand : responseOnMessage, player);
            player.sendMessage(message);
        }

        if (isPunishCmdEnabled) {
            util.punishPlayer(player);
        }
    }
}
