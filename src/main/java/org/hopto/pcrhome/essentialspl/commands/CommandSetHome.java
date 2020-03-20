package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

public class CommandSetHome implements CommandExecutor {

    EssentialsPl plugin = EssentialsPl.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            UserDataHandler userData = plugin.getPlayerData(p);

            Location newHome = p.getLocation();

            userData.setHome(newHome);
            p.sendActionBar(ChatColor.LIGHT_PURPLE + "Tu as set ta maison à ta location");
        } else{
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Tu ne peux pas set une maison");
        }

        return true;
    }
}
