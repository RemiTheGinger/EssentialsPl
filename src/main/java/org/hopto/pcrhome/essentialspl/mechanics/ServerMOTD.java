package org.hopto.pcrhome.essentialspl.mechanics;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.lib.Utils;

public class ServerMOTD implements Listener {

    EssentialsPl plugin = EssentialsPl.getInstance();
    FileConfiguration config = plugin.getConfig();

    /*
    CLASS INFO :
        This is to colorize the server MOTD if specified in the plugin config.
    */

    @EventHandler
    public void onServerListPing(ServerListPingEvent event){

        if(config.getBoolean("colorize_motd")){
            String old_motd = event.getMotd();
            String new_motd = Utils.colorize(old_motd);

            event.setMotd(new_motd);
        }
    }
}
