package com.arctic.events;

import com.arctic.NoBypass;
import com.arctic.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    private NoBypass main;

    public PlayerChat(NoBypass main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        if (main.containsSwearWord(message)) {
            event.setCancelled(true);
            main.onSwearWord(player, false);
        }
    }
}
