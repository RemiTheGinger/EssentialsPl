package org.hopto.pcrhome.essentialspl.commands;

import java.lang.Math;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

public class CmdNecro implements CommandExecutor {

    EssentialsPl plugin = EssentialsPl.getInstance();
    private int m_distanceMin = 0;

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player){ //Verifie que la commande a été faite par un joueur sinon Error
            Player player = (Player) sender;
            if(player.hasPermission("essential.necro")){    //Droit d'execution
                ItemStack handItem = player.getInventory().getItemInMainHand();

                //Verifie que le joueur a bien un item dans la main sinon Error
                if(handItem.getType() == Material.PLAYER_HEAD){

                    SkullMeta meta = (SkullMeta) handItem.getItemMeta();
                    UserDataHandler userData = plugin.getPlayerData((Player)meta.getOwningPlayer());

                    if(userData.getHome()!= null)
                    {
                        String dir = getDirection(player,userData.getHome());

                        Utils.sendInfo(dir, sender, InfoType.INFO);

                        player.getInventory().removeItem(handItem);  //Suprime les têtes de sa main


                    }else {
                        Utils.sendInfo("La personne n'a pas de maison.", sender, InfoType.ERROR);
                    }



                }else {
                    Utils.sendInfo("Tu n'as pas de tête a faire parler dans ta main", sender, InfoType.ERROR);
                }
            } else {
                Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
            }
        } else {
            Utils.sendInfo("La console ne peut pas faire cette commande", sender, InfoType.ERROR);
        }

        return true;
    }

    private String getDirection(Player toi, Location objectif)
    {
        String direction = "Direction";
        Location dif = toi.getLocation().subtract(objectif);
        if(Math.abs(dif.getBlockX()) + Math.abs(dif.getBlockZ()) < plugin.getConfig().getInt("necro_distance"))
        {return "Vous êtes pas loin";}

        if(dif.getBlockZ() < 0)
            direction += " Sud";
        if(dif.getBlockZ() > 0)
            direction += " Nord";
        if(dif.getBlockX() < 0)
            direction += " Est";
        if(dif.getBlockX() > 0)
            direction += " Ouest";


        return direction;
    }

}
