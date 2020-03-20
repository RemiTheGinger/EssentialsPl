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

public class CommandFeed implements CommandExecutor {

    EssentialsPl plugin = EssentialsPl.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0 :
                if(sender instanceof Player) {
                    Player player = (Player) sender;

                    if(player.hasPermission("essential.feed.self")) {
                        player.setFoodLevel(20);

                        Utils.sendInfo("Tu as été nourris", sender, InfoType.INFO);
                    } else {
                        Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
                    }
                } else {
                    Utils.sendInfo("Usage : " + ChatColor.GOLD + "/feed (player)", sender, InfoType.ERROR);
                }
                break;
            case 1 :
                if(sender.hasPermission("essential.feed.player")) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if(target == null){
                        Utils.sendInfo(ChatColor.RED + "Ce joueur n'est pas en ligne", sender, InfoType.ERROR);
                    } else {
                        target.setFoodLevel(20);

                        Utils.sendInfo("Tu as nourris " + ChatColor.GOLD + target.getDisplayName(), sender, InfoType.INFO);
                    }
                } else {
                    Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
                }
                break;
        }
        return true;
    }
}
