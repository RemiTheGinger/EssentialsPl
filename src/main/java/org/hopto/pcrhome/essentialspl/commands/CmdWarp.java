package org.hopto.pcrhome.essentialspl.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CmdWarp implements CommandExecutor, TabCompleter {

     private EssentialsPl plugin = EssentialsPl.getInstance();

     public CmdWarp(){
         plugin.getCommand("warp").setTabCompleter(this);
     }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("essential.warp.*")) {
                if (args.length < 1) {

                } else if (args.length == 1) {

                    switch (args[0]){
                        case "list":
                            Set<String> warpNames = plugin.getWarpManager().getWarps().keySet();
                            ArrayList<TextComponent> warpList = new ArrayList<TextComponent>();

                            for(String _warpName : warpNames){
                                TextComponent warp = new TextComponent("- " + _warpName);

                                ClickEvent click = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/warp " + _warpName);
                                warp.setClickEvent(click);
                                warp.setColor(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE);
                                warp.setBold(true);
                                warpList.add(warp);
                            }

                            player.sendMessage(ChatColor.GOLD + "======" + ChatColor.LIGHT_PURPLE + " List des warps " + ChatColor.GOLD + "======");

                            for(TextComponent textComponent : warpList){
                                player.spigot().sendMessage(textComponent);
                            }
                            break;
                        default:
                            Location warpLoc = plugin.getWarpManager().getWarpLocation(args[0]);

                            if(warpLoc != null){
                                if(player.hasPermission("essential.warp.tp")){
                                    player.teleport(warpLoc);
                                    Utils.sendInfo("Tu as été téléporté au warp " + ChatColor.GOLD + args[0].toLowerCase(), player, InfoType.INFO);
                                } else{
                                    Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
                                }
                            } else {
                                Utils.sendInfo("Ce warp n'existe pas", player, InfoType.ERROR);
                            }
                            break;
                    }

                } else if(args.length == 2) {
                    String subCommand = args[0];
                    String warpName = args[1];

                    switch (subCommand){
                        case "create":
                            if(plugin.getWarpManager().createWarp(warpName, player.getLocation())){
                                Utils.sendInfo("Tu a créé le warp " + ChatColor.GOLD + warpName.toLowerCase() + ChatColor.LIGHT_PURPLE + " a ta location", player, InfoType.INFO);
                            } else {
                                Utils.sendInfo("Ce nom de warp est déja utilisé", player, InfoType.ERROR);
                            }
                            break;
                        case "remove":
                            if(plugin.getWarpManager().removeWarp(warpName)){
                                Utils.sendInfo("Tu a remove le warp " + ChatColor.GOLD + warpName, player, InfoType.INFO);
                            } else {
                                Utils.sendInfo("Ce warp n'existe pas", player, InfoType.ERROR);
                            }
                            break;
                        default:
                            Utils.sendInfo("Cette commande n'existe pas", player, InfoType.ERROR);
                            break;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completion = new ArrayList<>();

        String subCommand = args[0];

        if(args.length == 1) {
            completion.add("create");
            completion.add("remove");
            completion.add("list");

            Set<String> warpNames = plugin.getWarpManager().getWarps().keySet();

            for(String warp: warpNames){
                completion.add(warp);
            }
        } else if(args.length == 2){
            switch (subCommand){
                case "remove" :
                    Set<String> warpNames = plugin.getWarpManager().getWarps().keySet();

                    for(String warp: warpNames){
                        completion.add(warp);
                    }
                    break;
                default:
                    break;
            }
        }

        return completion;
    }
}
