package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

public class CmdChatAdmin implements CommandExecutor {

    private EssentialsPl plugin = EssentialsPl.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;

            if(p.hasPermission("essential.chatadmin")){
                UserDataHandler user = plugin.getPlayerData(p);

                user.toggleChatAdmin();

                if(user.isChatAdmin()){
                    Utils.sendInfo("Tu es devenu chat admin !", sender, InfoType.INFO);
                } else {
                    Utils.sendInfo("Tu n'es plus chat admin", sender, InfoType.INFO);
                }

            } else {
                Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
            }
        } else {
            Utils.sendInfo("The Console can not do this command", sender, InfoType.ERROR);
        }
        return true;
    }
}
