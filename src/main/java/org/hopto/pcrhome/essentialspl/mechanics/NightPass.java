package org.hopto.pcrhome.essentialspl.mechanics;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

import java.util.ArrayList;

public class NightPass implements Listener {

    private ArrayList<Player> inBedPlayers = new ArrayList<Player>();

    EssentialsPl plugin = EssentialsPl.getInstance();

    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent event){
        if(!event.getPlayer().getWorld().isDayTime()){
            inBedPlayers.add(event.getPlayer());

            for(Player player : plugin.getServer().getOnlinePlayers()){
                Utils.sendInfo(event.getPlayer().getDisplayName() + ChatColor.LIGHT_PURPLE + " s'est couché " + ChatColor.GRAY + "(" + inBedPlayers.size() + "/" + plugin.getServer().getOnlinePlayers().size() + ")"
                        , player, InfoType.INFO);
            }
        }
    }

    @EventHandler
    public void onPlayerWake(PlayerBedLeaveEvent event){
        if(inBedPlayers.contains(event.getPlayer()))
            inBedPlayers.remove(event.getPlayer());

        for(Player player : plugin.getServer().getOnlinePlayers()){
            Utils.sendInfo(event.getPlayer().getDisplayName() + ChatColor.LIGHT_PURPLE + " s'est levé " + ChatColor.GRAY + "(" + inBedPlayers.size() + "/" + plugin.getServer().getOnlinePlayers().size() + ")"
                    , player, InfoType.INFO);
        }
    }



    private void setServerToMorning(){

        for(World world : plugin.getServer().getWorlds()){
            world.setTime(0);
            if(world.hasStorm()){
                world.setStorm(false);
            }

            if(world.isThundering()){
                world.setThundering(false);
            }
        }

        for(Player player : plugin.getServer().getOnlinePlayers()){
            Utils.sendInfo("Il est l'heure de se réveiller !", player, InfoType.INFO);
        }

        inBedPlayers.clear();
    }


}
