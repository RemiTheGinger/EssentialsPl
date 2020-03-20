package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.lib.Utils;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;


public class CommandHome implements CommandExecutor, Listener {

    private EssentialsPl plugin = EssentialsPl.getInstance();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            UserDataHandler userData = plugin.getPlayerData(player);

            Location pHome = userData.getHome();

            if(pHome == null) {
                player.sendActionBar(ChatColor.LIGHT_PURPLE + "Tu n'as pas de maison");
            } else {
                player.sendActionBar(ChatColor.LIGHT_PURPLE + "Tu vas te tp dans 3 secondes");
                startTimer(3, player, pHome);
            }


        } else {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Tu ne peux pas utilisé cette commande");
        }

        return true;
    }

    int task;
    private void startTimer(final int i, Player p, Location home) {
        Location firstLocation = p.getLocation();
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                int time = i;
                Location nowLoc = p.getLocation();

                if(time != -1){
                    if (Utils.compareLocation(nowLoc, firstLocation)) {
                        p.sendActionBar(ChatColor.LIGHT_PURPLE + "Tu vas te tp dans " + time + " secondes");
                        time--;
                        startTimer(time, p, home);
                    } else {
                        p.playSound(nowLoc, Sound.BLOCK_ANVIL_LAND, 0.5f, 0.5f);
                        p.sendActionBar(ChatColor.LIGHT_PURPLE + "Téléportation avortée, tu as bougé !");
                        Bukkit.getScheduler().cancelTask(task);
                    }

                }

                if(time == -1) {
                    p.teleport(home);

                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);

                    Location particleLoc = p.getLocation();
                    particleLoc.setY(particleLoc.getY() + 1);

                    particleLoc.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleLoc, 50);
                    Bukkit.getScheduler().cancelTask(task);
                }
            }
        }, 20);
    }

}
