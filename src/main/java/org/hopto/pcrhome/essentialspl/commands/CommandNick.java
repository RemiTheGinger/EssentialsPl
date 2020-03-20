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
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

public class CommandNick implements CommandExecutor {

    EssentialsPl plugin = EssentialsPl.getInstance();

    /*
    TODO
    Redo nickname system, make it so a nick name can be more than 1 word
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0 :
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    UserDataHandler user = new UserDataHandler(player.getUniqueId());

                    if (player.hasPermission("essential.nick.clear")) {
                        setNick(player, player.getName());

                        Utils.sendInfo("Tu a enlevé ton surnom", player, InfoType.INFO);
                    } else {
                        Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
                    }
                } else {
                    Utils.sendInfo("Ussage : " + ChatColor.GOLD + "/nick (nickname)" + ChatColor.GRAY + "[player]", sender, InfoType.ERROR);
                }
                break;
            case 1:
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    UserDataHandler user = new UserDataHandler(player.getUniqueId());

                    String nick = args[0];

                    if(!(player.hasPermission("essential.nick.self"))){
                        Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
                    } else {
                        setNick(player, nick);
                        Utils.sendInfo("Tu t'es renomé " + Utils.colorize(nick), sender, InfoType.INFO);
                    }

                } else {
                    Utils.sendInfo("Ussage : " + ChatColor.GOLD + "/nick (nickname)" + ChatColor.GRAY + "[player]", sender, InfoType.ERROR);
                }
                break;
            case 2 :
                Player target = Bukkit.getPlayer(args[1]);
                 
                if(sender.hasPermission("essential.nick.player")) {
                    if(target == null) {
                        Utils.sendInfo("Ce joueur n'est pas en ligne", sender, InfoType.ERROR);
                    } else {
                        setNick(target, args[0]);
                        Utils.sendInfo("Tu as renomé " + ChatColor.GOLD + target.getName() + ChatColor.LIGHT_PURPLE + " à " + ChatColor.GOLD + Utils.colorize(args[0]), sender, InfoType.INFO);
                    }
                } else {
                    Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
                }


        }
        return true;
    }

    private void setNick(Player p, String nick){
        UserDataHandler userData = plugin.getPlayerData(p);
        String coloredNick = Utils.colorize(nick);

        userData.setNick(coloredNick);
    }
}
