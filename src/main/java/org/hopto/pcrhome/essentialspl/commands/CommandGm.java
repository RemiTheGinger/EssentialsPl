package org.hopto.pcrhome.essentialspl.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

public class CommandGm implements CommandExecutor {

    EssentialsPl p = EssentialsPl.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("essential.gm")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(args.length == 0){
                    Utils.sendInfo("Usage : " + ChatColor.GOLD + "/gm 0|1|2|3" + ChatColor.GRAY + " [player]", sender, InfoType.ERROR);
                }else if(args.length == 1){
                    changeGamemode(args[0], player, player);
                }else if(args.length >= 2){
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target == null){
                        Utils.sendInfo("Ce joueur n'est pas en ligne", sender, InfoType.ERROR);
                    }else{
                        changeGamemode(args[0], target, player);
                    }
                }

            }else{
                if(args.length <= 1){
                    Utils.sendInfo("Usage : " + ChatColor.GOLD + "/gm 0|1|2|3" + ChatColor.GRAY + " [player]", sender, InfoType.ERROR);
                }else if(args.length >= 2){
                    Player target = Bukkit.getPlayer(args[1]);

                    if(target == null){
                        Utils.sendInfo("Ce joueur n'est pas en ligne", sender, InfoType.ERROR);
                    } else{
                        changeGamemode(args[0], target, sender);
                    }
                }
            }
        } else {
            Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
        }


        return true;
    }

    //Void to change a player's gamemode and send info to sender
    private void changeGamemode(String gm, Player player, CommandSender sender){
        //Check if CommandGm argument is number else send command usage
        UserDataHandler userData = p.getPlayerData(player);
        if(StringUtils.isNumeric(gm)){
            switch (gm) {
                case "0" :
                    userData.setGameMode(GameMode.SURVIVAL);
                    userData.toggleFlying(false);
                    Utils.sendInfo(player.getDisplayName() + ChatColor.LIGHT_PURPLE + " est maintenent en gamemode " + ChatColor.GOLD + player.getGameMode().name(), sender, InfoType.INFO);
                    break;
                case "1" :
                    userData.setGameMode(GameMode.CREATIVE);
                    userData.toggleFlying(true);
                    Utils.sendInfo(player.getDisplayName() + ChatColor.LIGHT_PURPLE + " est maintenent en gamemode " + ChatColor.GOLD + player.getGameMode().name(), sender, InfoType.INFO);
                    break;
                case "2" :
                    userData.setGameMode(GameMode.ADVENTURE);
                    userData.toggleFlying(false);
                    Utils.sendInfo(player.getDisplayName() + ChatColor.LIGHT_PURPLE + " est maintenent en gamemode " + ChatColor.GOLD + player.getGameMode().name(), sender, InfoType.INFO);
                    break;
                case "3" :
                    userData.setGameMode(GameMode.SPECTATOR);
                    userData.toggleFlying(true);
                    Utils.sendInfo(player.getDisplayName() + ChatColor.LIGHT_PURPLE + " est maintenent en gamemode " + ChatColor.GOLD + player.getGameMode().name(), sender, InfoType.INFO);
                    break;
                default:
                    Utils.sendInfo("Ussage : " + ChatColor.GOLD + "/CommandGm 0|1|2|3" + ChatColor.GRAY + " [player]", sender, InfoType.ERROR);
                    break;
            }
        }else{
            sender.sendMessage(ChatColor.GOLD + "Usage : " + ChatColor.GREEN + "/CommandGm 0|1|2|3" + ChatColor.GRAY + " [player]");
        }

    }

}
