package com.arctic.events;

import com.arctic.NoBypass;
import com.arctic.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommand implements Listener {

    private Util util;
    private NoBypass main;

    public PlayerCommand(NoBypass main) {
        this.main = main;
        this.util = new Util(main);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();

        if (main.containsSwearWord(command)) {
            event.setCancelled(true);
            main.onSwearWord(player, true);
        }
    }

    public boolean reloadCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            main.reloadConfig();
            util.loadConfigValues();
            sender.sendMessage(util.prefix + "config §asuccessfully§7 reloaded.");
            return true;
        } else {
            sender.sendMessage(util.prefix + "Usage: §c/nobypass reload");
            return false;
        }
    }
}
