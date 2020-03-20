package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

public class commandFly implements CommandExecutor {

    EssentialsPl plugin = EssentialsPl.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("essential.fly")){
            switch (args.length){

                /*
                TODO
                Create a target system to set flight for other players
                 */

                case 0 :
                    if(sender instanceof Player){
                        Player p = (Player) sender;
                        UserDataHandler userData = plugin.getPlayerData(p);

                        userData.toggleFlying();

                        if(userData.canFly()){
                            Utils.sendInfo("Tu peux maintenent voler", sender, InfoType.INFO);
                        } else {
                            Utils.sendInfo("Tu ne peux plus voler", sender, InfoType.INFO);
                        }

                    } else {
                        Utils.sendInfo("Tu ne peux pas fair cette commande", sender, InfoType.ERROR);
                    }
                    break;
                default:
                    break;
            }
        } else {
            
        }
        return true;
    }
}
