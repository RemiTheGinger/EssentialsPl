package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

public class CommandSetSpawn implements CommandExecutor {

    private EssentialsPl plugin = EssentialsPl.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("essential.spawn.set")){

                if(plugin.getWarpManager().createWarp("spawn", player.getLocation())){
                    setSpawn(player.getLocation(), player);
                } else {
                    if(plugin.getWarpManager().replaceWarpLocation("spawn", player.getLocation())){
                        setSpawn(player.getLocation(), player);
                    } else {
                        Utils.sendInfo("Le spawn n'as pas pu etre set à t'as location", player, InfoType.ERROR);
                    }
                }
            } else {
               Utils.sendInfo("Tu n'as pas la permission pour cette commande", player, InfoType.ERROR);
            }
        } else {
            Utils.sendInfo("The console can not do this command", sender, InfoType.ERROR);
        }
        return true;
    }

    private void setSpawn(Location location, Player commandExecutor){
        if(location.getWorld().setSpawnLocation(location)){
            Utils.sendInfo("Tu as set le spawn de ce monde à ta location", commandExecutor, InfoType.INFO);
        }else  {
            Utils.sendInfo("Le spawn n'as pas pu etre set à t'as location", commandExecutor, InfoType.ERROR);
        }
    }
}
