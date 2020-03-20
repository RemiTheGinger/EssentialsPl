package org.hopto.pcrhome.essentialspl.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.pcrhome.essentialspl.EssentialsPl;

public class CommandSuicide implements CommandExecutor {

    private EssentialsPl plugin;

    public CommandSuicide(EssentialsPl plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            startTimer(3 ,p);
        } else {
            sender.sendMessage("You can not suicide !");
        }

        return true;
    }

    public int task;
    public void startTimer(final int i, Player p){
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {

                int time = i;

                if(time != -1) {
                    p.sendActionBar(ChatColor.LIGHT_PURPLE + "Tu mourras dans " + time + " secondes");
                    time--;
                    startTimer(time, p);
                }

                if(time == -1) {
                    p.setHealth(0);
                    Bukkit.getScheduler().cancelTask(task);
                }
            }
        }, 20);
    }
}
