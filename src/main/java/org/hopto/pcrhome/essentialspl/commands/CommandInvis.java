package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

public class CommandInvis implements CommandExecutor {

    EssentialsPl p = EssentialsPl.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            UserDataHandler userData = this.p.getPlayerData(p);

            userData.toggleInvis();
            if(userData.getInvis()){
                Utils.sendInfo("*Poof* Tu es devenu invisible", sender, InfoType.INFO);
            } else {
                Utils.sendInfo("Trouvé ! Tu es redevenu visible", sender, InfoType.INFO);
            }

        } else {
            Utils.sendInfo("Tu ne peux pas faire cette commandes", sender, InfoType.ERROR);
        }
        return true;
    }
}
