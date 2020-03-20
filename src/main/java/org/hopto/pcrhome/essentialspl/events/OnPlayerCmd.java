package org.hopto.pcrhome.essentialspl.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.hopto.pcrhome.essentialspl.EssentialsPl;

public class OnPlayerCmd implements Listener {

    private EssentialsPl plugin = EssentialsPl.getInstance();

    @EventHandler
    public void onPlayerCmd(PlayerCommandPreprocessEvent event){
        Player p = event.getPlayer();

        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if(plugin.getChatAdmins().contains(player.getUniqueId()))
                if(!event.getMessage().contains("chatadmin"))
                    player.sendMessage(ChatColor.DARK_RED + "[ChatAdmin] " + ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_RED + " >> " + ChatColor.LIGHT_PURPLE + event.getMessage());
        }
    }
}
