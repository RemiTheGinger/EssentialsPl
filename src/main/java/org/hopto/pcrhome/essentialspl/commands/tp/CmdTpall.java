package org.hopto.pcrhome.essentialspl.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

public class CmdTpall implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!(player.hasPermission("essential.tpall"))){

                //Permission Error
                Utils.sendInfo("Tu n'as pas la permission pour cette commande !", player, InfoType.ERROR);
            } else {
                for(Player p : Bukkit.getServer().getOnlinePlayers()){
                    if(!(p.hasPermission("essential.tpall.override"))){
                        //Teleporting online players
                        p.teleport(player);
                        Utils.sendInfo("Tu as été téléporté par " + player.getDisplayName(), p, InfoType.INFO);
                    }
                }

                Utils.sendInfo("Tu as téléporté tous les joueurs en ligne a toi !", player, InfoType.INFO);
            }
        } else {

            //Error
            Utils.sendInfo("You can not do this command as the Console", sender, InfoType.ERROR);
        }
        return true;
    }
}
