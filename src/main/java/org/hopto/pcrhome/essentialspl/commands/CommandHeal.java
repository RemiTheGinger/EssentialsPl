package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

public class CommandHeal implements CommandExecutor {

    EssentialsPl plugin = EssentialsPl.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0 :
                if(sender instanceof Player) {
                    Player player = (Player) sender;

                    if(player.hasPermission("essential.heal.self")) {
                        player.setHealth(20);

                        Utils.sendInfo("Tu as été heal", sender, InfoType.INFO);
                    } else {
                        Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
                    }
                } else {
                    Utils.sendInfo("Usage : " + ChatColor.GOLD + "/heal (player)", sender, InfoType.ERROR);
                }
                break;
            case 1 :
                if(sender.hasPermission("essential.heal.player")) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if(target == null){
                        Utils.sendInfo("Ce joueur n'est pas en ligne", sender, InfoType.ERROR);
                    } else {
                        target.setHealth(20);

                        Utils.sendInfo("Tu as heal " + ChatColor.GOLD + target.getDisplayName(), sender, InfoType.INFO);
                    }
                } else {
                    Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
                }
                break;
        }
        return true;
    }
}
