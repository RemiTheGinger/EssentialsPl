package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.InfoType;
import org.hopto.pcrhome.essentialspl.lib.Utils;

public class CommandToggleDown implements CommandExecutor {

    EssentialsPl plugin = EssentialsPl.getInstance();

    /*
    TODO
    Redo /tg so i can summon rain or thunder and not just sun
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("essential.toggledown")){
            for(World w : EssentialsPl.getInstance().getServer().getWorlds()) {
                w.setThunderDuration(0);
                w.setWeatherDuration(0);
                w.setStorm(false);
                w.setThundering(false);
            }

            Utils.sendInfo("Toggled downfall", sender, InfoType.INFO);

        } else {
            Utils.sendInfo("Tu n'as pas la permission pour cette commande", sender, InfoType.ERROR);
        }
        return true;
    }
}
