package org.hopto.pcrhome.essentialspl.lib;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.hopto.pcrhome.essentialspl.EssentialsPl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class WarpManager {

    private final EssentialsPl plugin;

    private HashMap<String, File> warps = new HashMap<String, File>();

    private File warpPath;

    public WarpManager(EssentialsPl plugin){
        this.plugin = plugin;

        warpPath =  new File(plugin.getDataFolder() + "/warps");

        //Initialize existing warps
        if(warpPath.listFiles() != null){
            for(File warp : warpPath.listFiles()){
                warps.put(FilenameUtils.removeExtension(warp.getName()), warp);
            }
        }
    }

    public boolean createWarp(String warpName, Location warpLoc) {
        if(getWarps().containsKey(warpName.toLowerCase())){
            return false;
        }

        File warpFile = new File(warpPath, warpName.toLowerCase() + ".yml");
        FileConfiguration warpConfig = YamlConfiguration.loadConfiguration(warpFile);

        try {
            warpConfig.set("x", warpLoc.getBlockX());
            warpConfig.set("y", warpLoc.getBlockY());
            warpConfig.set("z", warpLoc.getBlockZ());
            warpConfig.set("world", warpLoc.getWorld().getUID().toString());
            warpConfig.set("yaw", warpLoc.getYaw());
            warpConfig.save(warpFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        getWarps().put(warpName, warpFile);
        return true;
    }

    public boolean removeWarp(String warpName){
        if(!getWarps().containsKey(warpName.toLowerCase())){
            return false;
        }

        File warpFile = getWarps().get(warpName.toLowerCase());
        getWarps().remove(warpName.toLowerCase());
        return warpFile.delete();
    }

    public boolean replaceWarpLocation(String warpName, Location location){
        File warpFile = null;
        FileConfiguration warpConfig = null;

        for(String _warpName : getWarps().keySet()){
            if(warpName.toLowerCase().equalsIgnoreCase(_warpName.toLowerCase())){
                warpFile = getWarps().get(_warpName.toLowerCase());
            }
        }

        if(warpConfig == null){
            return false;
        } else {
            try{
                warpConfig = YamlConfiguration.loadConfiguration(warpFile);

                warpConfig.set("x", location.getBlockX());
                warpConfig.set("y", location.getBlockY());
                warpConfig.set("z", location.getBlockZ());
                warpConfig.set("world", location.getWorld().getUID().toString());
                warpConfig.set("yaw", location.getYaw());
                warpConfig.save(warpFile);
            } catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public Location getWarpLocation(String _warpName){
        FileConfiguration warpConfig = null;
        Location warpLoc= null;

        for(String warpName : getWarps().keySet()){
            if(warpName.toLowerCase().equalsIgnoreCase(_warpName.toLowerCase())){
                warpConfig = YamlConfiguration.loadConfiguration(getWarps().get(warpName.toLowerCase()));
            }
        }

        if(warpConfig != null){
            int x = warpConfig.getInt("x");
            int y = warpConfig.getInt("y");
            int z = warpConfig.getInt("z");
            UUID world = UUID.fromString(warpConfig.getString("world"));
            float yaw = (float) warpConfig.getDouble("yaw");

            warpLoc = new Location(plugin.getServer().getWorld(world), x, y, z, yaw, 0.0f);
        }

        return warpLoc;
    }

    //get warp list on server
    public HashMap<String, File> getWarps() {
        return warps;
    }
}
