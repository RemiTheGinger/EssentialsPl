package org.hopto.pcrhome.essentialspl.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.lib.Utils;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;



public class OnPlayerJoin implements Listener {

    EssentialsPl plugin = EssentialsPl.getInstance();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();

        //Init player
        UserDataHandler user = new UserDataHandler(p.getUniqueId());
        user.initPlayer();

       plugin.addUser(user);

       if(user.isChatAdmin()){
           plugin.getChatAdmins().add(p.getUniqueId());
       }

        if(!p.hasPermission("essential.seeinvis")){
           for(Player player : plugin.getServer().getOnlinePlayers()){
               UserDataHandler userData = plugin.getPlayerData(player);
               if(userData.getInvis()){
                   p.hidePlayer(plugin, player);
               }

           }
        }

        if(plugin.getConfig().getBoolean("tablist_motd")){
            if(plugin.getConfig().getBoolean("colorize_motd")){
                p.setPlayerListHeader(Utils.colorize(plugin.getServer().getMotd()));
            } else {
                p.setPlayerListHeader(plugin.getServer().getMotd());
            }

        }

        //Join message

        if(!event.getPlayer().hasPlayedBefore()){
            plugin.getServer().broadcastMessage(ChatColor.GOLD + event.getPlayer().getName() + ChatColor.LIGHT_PURPLE + " est nouveau. S'il vous plaît soyez gentil·le !");
        }

        event.setJoinMessage(user.getNick() + ChatColor.LIGHT_PURPLE +" >> " + ChatColor.BLUE + "Joined !");

    }
}
