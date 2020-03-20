package org.hopto.pcrhome.essentialspl.mechanics;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.lib.Utils;

import java.util.Random;

public class PlayerHead implements Listener {

    private EssentialsPl plugin = EssentialsPl.getInstance();

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event){
        LivingEntity killed = event.getEntity();

        if(plugin.getConfig().getBoolean("head_drop")){
            if(killed.getType().equals(EntityType.PLAYER) && killed.getKiller() != null){
                Player killedPlayer = (Player) killed;

                double rnd = Math.random();
                int chance = plugin.getConfig().getInt("head_chance");

                if(((rnd/1) * 100 ) <= chance){
                    event.getDrops().add(Utils.getPlayerHead(killedPlayer.getUniqueId()));
                }
            }
        }
    }
}
