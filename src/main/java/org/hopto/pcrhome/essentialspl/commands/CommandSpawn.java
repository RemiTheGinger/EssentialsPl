package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

import java.util.UUID;

public class CommandSpawn implements CommandExecutor {

    private EssentialsPl plugin = EssentialsPl.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("essential.spawn.tp")) {
                Location spawnLoc = plugin.getWarpManager().getWarpLocation("spawn");

                if(spawnLoc != null){
                    player.teleport(spawnLoc);

                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                    player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 50);
                }else {
                    Utils.sendInfo("Le spawn n'as pas été set", player, InfoType.ERROR);
                }

            } else {
                Utils.sendInfo("Tu n'as pas la permission pour cette commande", player, InfoType.ERROR);
            }
        } else {
            Utils.sendInfo("The console can not do this command", sender, InfoType.ERROR);
        }
        return true;
    }
}
