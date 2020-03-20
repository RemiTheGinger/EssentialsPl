package org.hopto.pcrhome.essentialspl.commands.tp;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;
import org.hopto.pcrhome.essentialspl.lib.Utils;

import java.util.HashMap;
import java.util.UUID;

public class CmdTpa implements CommandExecutor {

    private EssentialsPl plugin = EssentialsPl.getInstance();

    //              Receiver,Sender
    private HashMap<UUID, UUID> tpaPlayers = plugin.getTpaPlayers();
    private boolean isAccepted = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /*
        TPA
         */
        if(command.getName().equalsIgnoreCase("tpa")) {
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(player.hasPermission("essential.tpa")){
                    if(args.length >= 1){
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null && target.isOnline() && target.hasPermission("essential.tpa")){
                            if(!tpaPlayers.containsValue(player.getUniqueId())){
                                tpaPlayers.put(target.getUniqueId(), player.getUniqueId());
                                plugin.getTpaAccepted().put(target.getUniqueId(), isAccepted);

                                TextComponent command1 = new TextComponent("[ACCEPT]");
                                TextComponent seperator = new TextComponent(" ou ");
                                TextComponent command2 = new TextComponent("[DENY]");

                                TextComponent hover1 = new TextComponent("Accepter la téléportation (Dans 3 secondes)");
                                hover1.setColor(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE);
                                BaseComponent[] hoverBase1 = {hover1};
                                TextComponent hover2 = new TextComponent("Refuser la téléportation");
                                hover2.setColor(net.md_5.bungee.api.ChatColor.DARK_RED);
                                BaseComponent[] hoverBase2 = {hover2};

                                ClickEvent clickCmd1 = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept");
                                HoverEvent hoverEvent1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverBase1);
                                ClickEvent clickCmd2 = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny");
                                HoverEvent hoverEvent2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverBase2);

                                command1.setClickEvent(clickCmd1);
                                command1.setHoverEvent(hoverEvent1);
                                command1.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                                command1.setBold(true);
                                command2.setClickEvent(clickCmd2);
                                command2.setHoverEvent(hoverEvent2);
                                command2.setColor(net.md_5.bungee.api.ChatColor.DARK_RED);
                                command2.setBold(true);

                                seperator.setColor(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE);

                                BaseComponent[] text = {command1, seperator, command2};

                                target.sendMessage(player.getDisplayName() + ChatColor.LIGHT_PURPLE + " veux se téléporté à toi.");
                                target.spigot().sendMessage(text);
                                target.sendMessage(ChatColor.LIGHT_PURPLE + "Tu as 20 secondes pour accepté");

                                acceptCooldown(20, player, target);
                            } else {
                                player.sendActionBar(target.getDisplayName() + ChatColor.DARK_RED + " a deja une demande en cours !");
                            }
                        } else {
                            player.sendActionBar(ChatColor.DARK_RED + "Ce joueur n'as pas été trouvé ou ne peux pas accepter");
                        }
                    }
                } else{
                    player.sendActionBar(ChatColor.DARK_RED + "Tu n'as pas la permission pour cette commande !");
                }
            }
        }

        /*
        TP ACCEPT
         */
        if(command.getName().equalsIgnoreCase("tpaccept")){
            if(sender instanceof Player){
                Player target = (Player) sender;
                if(target.hasPermission("essential.tpa")){
                    if(tpaPlayers.containsKey(target.getUniqueId())){
                        Player TpSender = Bukkit.getPlayer(tpaPlayers.get(target.getUniqueId()));
                        if(TpSender != null && TpSender.isOnline()){
                            //Receiver mechanics
                            isAccepted = true;

                            plugin.getTpaAccepted().replace(target.getUniqueId(), isAccepted);

                        } else {
                            target.sendActionBar( ChatColor.DARK_RED + "Le joueur n'est plus trouvable sur le serveur");
                        }
                    } else {
                        target.sendActionBar(ChatColor.DARK_RED +  "Tu n'as pas de tpa à accepter");
                    }
                } else{
                    target.sendActionBar(ChatColor.DARK_RED + "Tu n'as pas la permission pour cette commande !");
                }
            }

        }

        /*
        TP DENY
         */
        if(command.getName().equalsIgnoreCase("tpdeny")){
            if(sender instanceof Player){
                Player target = (Player) sender;
                if(target.hasPermission("essential.tpa")){
                    Player tpPlayer = Bukkit.getPlayer(tpaPlayers.get(target.getUniqueId()));

                    if(tpaPlayers.containsKey(target.getUniqueId())){
                        if(tpPlayer != null && tpPlayer.isOnline()){
                            tpPlayer.sendActionBar(target.getDisplayName() + ChatColor.DARK_RED + " a refusé ta demande de téléportation !");
                            tpPlayer.playSound(tpPlayer.getLocation() , Sound.BLOCK_ANVIL_LAND, 1f, .5f);

                            target.sendActionBar(ChatColor.DARK_RED + "Tu as refusé la demande de téléportation de " + tpPlayer.getDisplayName());

                            Bukkit.getScheduler().cancelTask(acceptCooldown);
                            tpaPlayers.remove(target.getUniqueId());
                            plugin.getTpaAccepted().remove(target.getUniqueId());
                        } else {
                            target.sendActionBar(ChatColor.DARK_RED + "Le joueur n'est plus trouvable sur le serveur");
                        }
                    } else {
                        target.sendActionBar(ChatColor.DARK_RED +  "Tu n'as pas de tpa à refusé");
                    }
                } else{
                    target.sendActionBar(ChatColor.DARK_RED + "Tu n'as pas la permission pour cette commande !");
                }
            }

        }
        return true;
    }

    /*
    COOLDOWN TO ACCEPT
     */
    private int acceptCooldown;
    private void acceptCooldown(final int i, Player p, Player target){

        UUID pUID = p.getUniqueId();
        UUID targetUID = target.getUniqueId();

        acceptCooldown = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                int time = i;

                if(target.isOnline() && p.isOnline()){
                    if(i != -1){
                        if(plugin.getTpaAccepted().get(targetUID)){

                            p.sendActionBar(ChatColor.LIGHT_PURPLE + "Tu vas te téléporté dans 3 secondes");
                            tpSequence(3, p, target);

                            Bukkit.getScheduler().cancelTask(acceptCooldown);
                        } else {
                            time--;
                            acceptCooldown(time, p, target);
                        }
                    }

                    if(i == -1){

                        target.sendActionBar(ChatColor.DARK_RED + "Tpa annulé, tu as pris trop de temps à accepter");

                        p.sendActionBar(ChatColor.DARK_RED + "Tpa annulé, " + target.getDisplayName() + ChatColor.DARK_RED + " as pris trop de temps à accepter");
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, .5f);

                        tpaPlayers.remove(targetUID);
                        Bukkit.getScheduler().cancelTask(acceptCooldown);
                    }
                } else {
                    /*
                    Case where one or more players disconnects during the sequence
                     */
                    tpaPlayers.remove(targetUID);
                    Bukkit.getScheduler().cancelTask(acceptCooldown);
                }
            }
        }, 20);
    }


    /*
    TP SEQUENCE WHEN ACCEPTED
     */
    private int tpSequence;
    private void tpSequence(final int i, Player tpPlayer, Player target){
        Location tppLocFirst = tpPlayer.getLocation();

        UUID targetUID = target.getUniqueId();
        tpSequence = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                int time = i;
                Location tppLocNow = tpPlayer.getLocation();

                if(target.isOnline() && tpPlayer.isOnline()){
                    if(time != -1){
                        if(Utils.compareLocation(tppLocNow, tppLocFirst)){
                            tpPlayer.sendActionBar(ChatColor.LIGHT_PURPLE + "Tu vas te téléporté dans " + time + " secondes");
                            tpPlayer.playSound(tpPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f);
                            time--;

                            tpSequence(time, tpPlayer, target);
                        } else {
                            tpPlayer.sendActionBar(ChatColor.DARK_RED + "Téléportation avortée, tu as bougé !");
                            tpPlayer.playSound(tpPlayer.getLocation(), Sound.BLOCK_ANVIL_LAND , 1f, .5f);

                            tpaPlayers.remove(targetUID);
                            plugin.getTpaAccepted().remove(targetUID);
                            Bukkit.getScheduler().cancelTask(tpSequence);
                        }

                    }

                    if(time == -1){
                        TpPlayer(tpPlayer, target);
                        Bukkit.getScheduler().cancelTask(tpSequence);
                    }
                } else {
                    /*
                    Case where one or more players disconnects during the sequence
                     */
                    tpaPlayers.remove(targetUID);
                    Bukkit.getScheduler().cancelTask(tpSequence);
                }


            }
        }, 20);
    }

    /*
    TP FUNCTION
     */
    public  void TpPlayer(Player tpPlayer, Player target){
        tpPlayer.teleport(target);

        tpPlayer.sendActionBar(ChatColor.LIGHT_PURPLE + "Tu as été téléporté à " + target.getDisplayName());
        target.sendActionBar(tpPlayer.getDisplayName() + ChatColor.LIGHT_PURPLE + " s'est téléporté à toi !");

        tpPlayer.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, tpPlayer.getLocation(), 50);
        tpPlayer.playSound(tpPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        tpaPlayers.remove(target.getUniqueId());
    }
}
