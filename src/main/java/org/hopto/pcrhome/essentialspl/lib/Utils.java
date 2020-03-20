package org.hopto.pcrhome.essentialspl.lib;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;

import java.util.UUID;

public class Utils {

    private static EssentialsPl plugin = EssentialsPl.getInstance();

    //colorize a msg or text
    public static String colorize(String text){
        String coloredText = "";
        for(int i = 0; i < text.length(); i++ ){
            if(text.charAt(i) == '&')
                coloredText += '§';
            else
                coloredText += text.charAt(i);
        }
        coloredText += ChatColor.WHITE;
        return coloredText;
    }

    /*
    Compare to location without comparing the pitch or yaw of the locations
    Useful in the tpa or home commands
     */
    public static boolean compareLocation(Location loc1, Location loc2){
        if(loc1.getX() != loc2.getX() || loc1.getY() != loc2.getY() || loc1.getZ() != loc2.getZ() || loc1.getWorld().getUID() != loc2.getWorld().getUID()){
            return false;
        } else {
            return true;
        }
    }

    //Send info to a command sender
    public static void sendInfo(String info, CommandSender sender, InfoType infoType) {
        String sendMessage = "";

        //Colorize the message depending of the info type
        switch (infoType){
            case INFO:
                sendMessage = ChatColor.LIGHT_PURPLE + info;
                break;
            case ERROR:
                sendMessage = ChatColor.DARK_RED + info;
                break;
            default:
                break;
        }

        if(sender instanceof Player && plugin.getConfig().getString("message_displaymode").equalsIgnoreCase("actionbar")) {
            Player p = (Player) sender;

            p.sendActionBar(sendMessage);
        } else {
            sender.sendMessage(sendMessage);
        }
    }

    //Get OnlinePlayer Skull
    public static ItemStack getPlayerHead(UUID player){

        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(player));

        item.setItemMeta(meta);

        return item;
    }
}
