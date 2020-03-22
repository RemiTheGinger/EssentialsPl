package org.hopto.pcrhome.essentialspl.mechanics;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.lib.Utils;

import java.util.Random;

public class PlayerHead implements Listener {

    private EssentialsPl plugin = EssentialsPl.getInstance();

    /*
    * Si un joueur tu un autre joueur, il y a un pourcentage de chance.
    * Ce pourcentage de chance est augmenté si l'item utilisé à l'enchantement Looting.
    * Le pourcentage de base est changable de le fichier config.yml du plugin.
     */

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event){
        LivingEntity killed = event.getEntity();

        if(plugin.getConfig().getBoolean("head_drop")){
            if(killed.getType().equals(EntityType.PLAYER) && killed.getKiller() != null){
                Player killedPlayer = (Player) killed;
                ItemStack item = killed.getKiller().getInventory().getItemInMainHand();

                double rnd = Math.random();
                int chance = plugin.getConfig().getInt("head_chance");
                int mod = 0;

                /*
                * Verfie si l'itemstack utilisé pour tué le joueur a l'enchantement Looting
                * Si l'item a l'enchante looting ajouté un modifier au pourcentage de chance
                * par raport au niveau de l'enchante
                * - 5 * le level de l'enchante
                 */

                if(item.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)){
                    mod = 5 * item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                }

                if(((rnd/1) * 100 - mod ) <= chance){
                    event.getDrops().add(Utils.getPlayerHead(killedPlayer.getUniqueId()));
                }
            }
        }
    }
}
