package org.hopto.pcrhome.essentialspl;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.hopto.pcrhome.essentialspl.commands.*;
import org.hopto.pcrhome.essentialspl.commands.inventories.CmdEndersee;
import org.hopto.pcrhome.essentialspl.commands.inventories.CmdInvsee;
import org.hopto.pcrhome.essentialspl.commands.tp.CmdTpa;
import org.hopto.pcrhome.essentialspl.commands.tp.CmdTpall;
import org.hopto.pcrhome.essentialspl.commands.tp.CmdTphere;
import org.hopto.pcrhome.essentialspl.events.OnPlayerCmd;
import org.hopto.pcrhome.essentialspl.events.OnPlayerJoin;
import org.hopto.pcrhome.essentialspl.events.OnPlayerLeave;
import org.hopto.pcrhome.essentialspl.lib.WarpManager;
import org.hopto.pcrhome.essentialspl.mechanics.NightPass;
import org.hopto.pcrhome.essentialspl.mechanics.PlayerHead;
import org.hopto.pcrhome.essentialspl.mechanics.ServerMOTD;
import org.hopto.pcrhome.essentialspl.mechanics.UserDataHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public final class EssentialsPl extends JavaPlugin {

    private static EssentialsPl instance;

    FileConfiguration config = getConfig();

    private File userDataFolder = new File(getDataFolder() + "/userData");

    private ArrayList<UserDataHandler> users = new ArrayList<UserDataHandler>();

    private HashMap<UUID, UUID> tpaPlayers = new HashMap<UUID, UUID>();

    private HashMap<UUID, Boolean> tpaAccepted = new HashMap<UUID, Boolean>();

    private ArrayList<UUID> chatAdmins = new ArrayList<UUID>();

    private WarpManager warpManager;



    @Override
    public void onEnable() {
        getLogger().info("EssentialsPl is now loaded !");
        instance = this;

        //Generate config.yml if not created already
        getConfig().addDefault("defaultGameMode", GameMode.SURVIVAL);
        getConfig().addDefault("colorize_motd", true);
        getConfig().addDefault("tablist_motd", true);
        getConfig().addDefault("message_displaymode", "actionbar");
        getConfig().addDefault("head_drop", true);
        getConfig().addDefault("head_chance", 15);
        saveDefaultConfig();

        /*
        ------------------------------------------------------------------
        USERDATA HANDLER
        ------------------------------------------------------------------
         */

        //Create User Data Folder
        if(!(userDataFolder.exists())){
            getLogger().info("Creating User Data folder");
            userDataFolder.mkdir();
        }

        //Create userDataHandler for online players
        for(Player p : Bukkit.getOnlinePlayers()){
            UserDataHandler userData = new UserDataHandler(p.getUniqueId());

            addUser(userData);
        }

        /*
        ------------------------------------------------------------------
        WARP MANAGER
        ------------------------------------------------------------------
        */

        warpManager = new WarpManager(this);

        /*
        ------------------------------------------------------------------
        EVENTS & COMMANDS
        ------------------------------------------------------------------
         */

        //Events registry
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);

        /*
        MECHANICS
         */

        //Bed mechanic
        getServer().getPluginManager().registerEvents(new NightPass(), this);

        //Player Head Mechanic
        getServer().getPluginManager().registerEvents(new PlayerHead(), this);

        //Chat Admins
        getServer().getPluginManager().registerEvents(new OnPlayerCmd(), this);

        //Colorize MOTD
        getServer().getPluginManager().registerEvents(new ServerMOTD(), this);

        /*
        *Command Executors
        * TODO => Set executor automaticly for all commands
        */
        try {
            this.getCommand("gm").setExecutor(new CommandGm());
            this.getCommand("spawn").setExecutor(new CommandSpawn());
            this.getCommand("setspawn").setExecutor(new CommandSetSpawn());
            this.getCommand("toggledown").setExecutor(new CommandToggleDown());
            this.getCommand("nick").setExecutor(new CommandNick());
            this.getCommand("feed").setExecutor(new CommandFeed());
            this.getCommand("heal").setExecutor(new CommandHeal());
            this.getCommand("suicide").setExecutor(new CommandSuicide(this));
            this.getCommand("sethome").setExecutor(new CommandSetHome());
            this.getCommand("home").setExecutor(new CommandHome());
            this.getCommand("invis").setExecutor(new CommandInvis());
            this.getCommand("fly").setExecutor(new commandFly());
            this.getCommand("chatadmin").setExecutor(new CmdChatAdmin());
            this.getCommand("hat").setExecutor(new CmdHat());
            this.getCommand("annouce").setExecutor(new CmdAnnouce(this));
            this.getCommand("necro").setExecutor(new CmdNecro());

            //Commandes d'inspection d'inventaire
            this.getCommand("invsee").setExecutor( new CmdInvsee());
            this.getCommand("endersee").setExecutor(new CmdEndersee());

            this.getCommand("tpall").setExecutor(new CmdTpall());
            this.getCommand("tphere").setExecutor(new CmdTphere());


            this.getCommand("warp").setExecutor(new CmdWarp());

            ArrayList<String> tpaCmds = new ArrayList<String>();
            tpaCmds.add("tpa");
            tpaCmds.add("tpaccept");
            tpaCmds.add("tpdeny");
            for(String cmd : tpaCmds){
                this.getCommand(cmd).setExecutor(new CmdTpa());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        getLogger().info("EssentialsPl is now unloaded !");
    }


    /*
    ------------------------------------------------------------------
    GETTER AND SETTER FUNCTION
    ------------------------------------------------------------------
    */

    //Get Plugin instance in other classes
    public static EssentialsPl getInstance() {
        return instance;
    }

    //get the User Data folder
    public File getUserDataFolder() {
        return userDataFolder;
    }

    //get instance warp manager
    public WarpManager getWarpManager() {
        return warpManager;
    }

    //List of Active TPAs on the server
    public HashMap<UUID, UUID> getTpaPlayers() {
        return tpaPlayers;
    }

    public HashMap<UUID, Boolean> getTpaAccepted() {
        return tpaAccepted;
    }

    //List of chat admin players
    public ArrayList<UUID> getChatAdmins() {
        return chatAdmins;
    }

    //Users on server
    public ArrayList<UserDataHandler> getUsers(){
        return users;
    }

    public void addUser(UserDataHandler user){
        users.add(user);
    }

    public void removeUser(UserDataHandler user){
        users.remove(user);
    }

    public UserDataHandler getPlayerData(Player p){
        UserDataHandler targetData = null;
        for(UserDataHandler userData : users){
            if(userData.getPlayer() == p){
                targetData = userData;
            }
        }
        return targetData;
    }

}