package org.hopto.pcrhome.essentialspl.commands;

import com.destroystokyo.paper.Title;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

import java.util.Collection;

/*
* Cette ccommande permet au Modo de
* créé un Title envoyer a tous les joueur en ligne
* pour faire une annonce.
* @permission essential.annouce
* @args annonce
 */

public class CmdAnnouce implements CommandExecutor {

    private final EssentialsPl plugin;

    public CmdAnnouce(EssentialsPl _plugin){
        plugin = _plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Collection<Player> onlinePlayers = (Collection<Player>) plugin.getServer().getOnlinePlayers();

        Title annoucement = null;

        //Verifie si le sender de la commande à bien la permission pour la commande
        if(sender.hasPermission("essential.annouce")){
            //Verifie si ils a des argurments sinon erreur
            if(!(args.length < 1)){
                String colorizedAnnoucement = Utils.colorize(String.join(" ", args));

                //Création du Title, les fades sont calculé en ticks
                annoucement = annoucement.builder()
                        .fadeIn(10)
                        .fadeOut(10)
                        .stay(120)
                        .title(ChatColor.GOLD + "== Annonce ==")
                        .subtitle(colorizedAnnoucement)
                        .build();

                for(Player player : onlinePlayers){
                    player.sendTitle(annoucement);
                }

            } else {
                Utils.sendInfo("Il faut metre une annonce en argument", sender, InfoType.ERROR);
            }
        } else {
            Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
        }

        return true;
    }
}
