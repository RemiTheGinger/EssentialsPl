package org.hopto.pcrhome.essentialspl.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

public class CmdTphere implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!(player.hasPermission("essential.tphere"))){
                //Permission Error
                Utils.sendInfo("Tu n'as pas la permission pour cette commande !", player, InfoType.ERROR);
            } else {
                if(args.length < 1) {

                    Player tpPlayer = Bukkit.getPlayer(args[0]);

                    if(tpPlayer != null) {
                        tpPlayer(tpPlayer, player);
                    } else {
                        //Unknown Player Error
                        Utils.sendInfo(args[0] + ChatColor.DARK_RED + " n'existe pas ou n'est pas en ligne", player, InfoType.ERROR);
                    }
                }
            }
        } else
            Utils.sendInfo("You can not do this command as the Console", sender, InfoType.ERROR);
        return true;
    }

    public void tpPlayer(Player target, Player player){
        target.teleport(player);

        Utils.sendInfo("Tu as été téléporté à " + player.getDisplayName(), target, InfoType.INFO);
        Utils.sendInfo("Tu as téléporté " + target.getDisplayName() + ChatColor.LIGHT_PURPLE + " à toi", player, InfoType.INFO);

        target.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, target.getLocation(), 50);
        target.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
    }
}
