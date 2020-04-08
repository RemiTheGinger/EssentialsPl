package org.hopto.pcrhome.essentialspl.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class UserDataHandler {

    private EssentialsPl p = EssentialsPl.getInstance();
    private UUID u;
    private File userFile;
    private FileConfiguration userConfig;

    private ArrayList<String> paths = new ArrayList<String>();

    private Player player;

    private Location home = null;
    private String nick;

    private boolean isInvis;
    private boolean canFly;
    private boolean isChatAdmin;

    private GameMode gameMode;

    public UserDataHandler(UUID u) {

        this.u = u;

        userFile = new File(p.getUserDataFolder(), u + ".yml");

        createUser(Bukkit.getPlayer(u));

        userConfig = YamlConfiguration.loadConfiguration(userFile);

        player = Bukkit.getPlayer(u);

        if(userConfig.get("User.home") != null){
            this.home = new Location(Bukkit.getWorld(UUID.fromString(userConfig.getString("User.home.world"))), userConfig.getDouble("User.home.x"),
                    userConfig.getDouble("User.home.y"), userConfig.getDouble("User.home.z"));
        }

        this.nick  = (userConfig.getString("User.game.nick"));

        this.gameMode = GameMode.valueOf(userConfig.getString("User.gameinfo.gm"));

        this.isInvis = userConfig.getBoolean("User.isInvis");

        this.canFly = userConfig.getBoolean("User.canFly");

        this.isChatAdmin = userConfig.getBoolean("User.chatAdmin");
    }

    //TODO Create init void to set players infos

    public void initPlayer(){
        setNick(userConfig.getString("User.gameinfo.nick"));
        setGameMode(gameMode);

        toggleInvis(isInvis);
        toggleFlying(canFly);

        toggleChatAdmin(isChatAdmin);
    }

    //Create file void
    public void createUser(final Player player) {
        if( !(userFile.exists())) {
            try {
                YamlConfiguration userConfig = YamlConfiguration.loadConfiguration(userFile);

                userConfig.set("User.gameinfo.nick", player.getName());

                //User Perssonnal info
                userConfig.set("User.ip", player.getAddress().getAddress().getHostAddress());
                userConfig.set("User.UUID", player.getUniqueId().toString());

                userConfig.set("User.isInvis", false);
                userConfig.set("User.canFly", false);
                userConfig.set("User.chatAdmin", false);

                userConfig.set("User.gameinfo.gm", p.getConfig().getString("defaultGameMode"));

                userConfig.save(userFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Get User File
    public FileConfiguration getUserFile(){
        return userConfig;
    }

    //Save User File
    public void saveUserFile(){
        try {
            getUserFile().save(userFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO - Create get and set fonction for user info

    public Player getPlayer(){
        return player;
    }

    //Set functions
    public void setHome(Location home){
        World w = home.getWorld();
        Double x = home.getX();
        Double y = home.getY();
        Double z = home.getZ();

        this.home = home;

        userConfig.set("User.home.world", w.getUID().toString());
        userConfig.set("User.home.x", x);
        userConfig.set("User.home.y", y);
        userConfig.set("User.home.z", z);
        saveUserFile();
    }

    public void setNick(String nick){
        if(nick != null){
            this.nick = nick;

            userConfig.set("User.gameinfo.nick", nick);
            player.setDisplayName(nick);
            player.setPlayerListName(nick);
            saveUserFile();
        }
    }

    public void toggleInvis(){
        if(isInvis){
            isInvis = false;

            userConfig.set("User.isInvis", false);
            saveUserFile();

            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                if(!(p.hasPermission("essential.seeinvis"))){
                    p.showPlayer(this.p, player);
                }
            }
        } else {
            isInvis = true;

            userConfig.set("User.isInvis", true);
            saveUserFile();

            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                if(!(p.hasPermission("essential.seeinvis"))){
                    p.hidePlayer(this.p, player);
                }
            }
        }
    }
    public void toggleInvis(boolean foo){
        isInvis = foo;

        userConfig.set("User.isInvis", foo);
        saveUserFile();

        if(isInvis){
            for (Player p : Bukkit.getServer().getOnlinePlayers()){
                if(!p.hasPermission("essential.seeinvis")){
                    p.hidePlayer(this.p, player);
                }
            }
        } else {
            for (Player p : Bukkit.getServer().getOnlinePlayers()){
                if(!p.hasPermission("essential.seeinvis")){
                    p.showPlayer(this.p, player);
                }
            }
        }
    }

    public void toggleFlying(){
        if(canFly) {
            canFly = false;

            userConfig.set("User.canFly", false);
            saveUserFile();

            player.setAllowFlight(false);
        } else {
            canFly = true;

            userConfig.set("User.canFly", true);
            saveUserFile();

            player.setAllowFlight(true);
        }
    }
    public void toggleFlying(boolean foo){
        canFly = foo;

        userConfig.set("User.canFly", foo);
        saveUserFile();

        player.setAllowFlight(foo);
    }

    /*
    CHAT ADMIN TOGGLE
     */
    public void toggleChatAdmin(){
        if(isChatAdmin){
            isChatAdmin = false;

            if(p.getChatAdmins().contains(player.getUniqueId())){
                p.getChatAdmins().remove(player.getUniqueId());
            }

            userConfig.set("User.chatAdmin",  false);
            saveUserFile();
        } else {
            isChatAdmin = true;

            if(!p.getChatAdmins().contains(player.getUniqueId())){
                p.getChatAdmins().add(player.getUniqueId());
            }

            userConfig.set("User.chatAdmin", true);
            saveUserFile();
        }
    }
    public void toggleChatAdmin(boolean foo) {
        isChatAdmin = foo;

        userConfig.set("User.chatAdmin", foo);
        saveUserFile();
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;

        player.setGameMode(gameMode);
        userConfig.set("User.gameinfo.gm", gameMode.toString());
        saveUserFile();
    }

    //Get function
    public Location getHome(){
        return home;
    }

    public String getNick(){
        return nick;
    }

    public boolean getInvis(){ return isInvis; }

    public boolean canFly() { return canFly; }

    public boolean isChatAdmin() {
        return isChatAdmin;
    }

    public GameMode getGameMode(){ return gameMode; }
}
