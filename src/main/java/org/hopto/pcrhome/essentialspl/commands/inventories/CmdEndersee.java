package org.hopto.pcrhome.essentialspl.commands.inventories;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

/*
 * Cette commande permet de regardé et edité n'import quel enderchest de joueur
 * tant que le joueur en question est en ligne.
 * Cette commande est faite pour les Modérateurs du serveur
 * @permission essential.endersee
 * @args PlayerName
 */

public class CmdEndersee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Verifie que le sender de la commande est un joueur sinon Error
        if(sender instanceof Player) {
            Player player = (Player) sender;

            //Verfie que le joueur a la permission requise pour effectuer la commande sinon Error
            if(player.hasPermission("essential.endersee")){

                //Verfie qu'il y a bien un argument deriere la commande
                if(args.length >= 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    //Verifie que le joueur existe sur le serveur
                    if(target != null){
                        player.openInventory(target.getEnderChest());
                    } else
                        Utils.sendInfo("Le joueur" + args[0] + "n'existe pas", sender, InfoType.ERROR);
                } else
                    Utils.sendInfo("Tu dois donné le nom d'un joueur", sender, InfoType.ERROR);

            } else
                Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);

        } else
            Utils.sendInfo("La console ne peux pas faire cette commande", sender, InfoType.ERROR);

        return true;
    }
}
