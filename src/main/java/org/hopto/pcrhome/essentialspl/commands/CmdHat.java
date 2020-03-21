package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

/*
* Cette Commande permet au joueur de choisir n'importe quel item
* et de le mettre sur leurs tête
* Elle ne prend pas de paramettre et ne peux etre fait que par un joueur
* @permission essential.hat
 */

public class CmdHat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        //Verifie que la commande a été faite par un joueur sinon Error
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("essential.hat")){
                ItemStack handItem = player.getInventory().getItemInMainHand();

                //Verifie que le joueur a bien un item dans la main sinon Error
                if(handItem != null){
                    player.getInventory().setItemInMainHand(player.getInventory().getHelmet());
                    
                    player.getInventory().setHelmet(handItem);
                    Utils.sendInfo("Tu as un nouveau chapeau", sender, InfoType.INFO);
                }else {
                    Utils.sendInfo("Tu n'as pas d'item dans ta main", sender, InfoType.ERROR);
                }
            } else {
                Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
            }
        } else {
            Utils.sendInfo("La console ne peut pas faire cette commande", sender, InfoType.ERROR);
        }

        return true;
    }
}
