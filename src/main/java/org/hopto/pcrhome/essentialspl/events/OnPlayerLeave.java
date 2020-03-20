package org.hopto.pcrhome.essentialspl.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

public class OnPlayerLeave implements Listener {

    private EssentialsPl plugin = EssentialsPl.getInstance();

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        UserDataHandler userData = plugin.getPlayerData(p);

        plugin.removeUser(userData);

        if(plugin.getChatAdmins().contains(p.getUniqueId())){
            plugin.getChatAdmins().remove(p.getUniqueId());
        }

        //Quit message
        event.setQuitMessage(p.getDisplayName() + ChatColor.LIGHT_PURPLE +" >> " + ChatColor.BLUE + "Left !");

    }
}
