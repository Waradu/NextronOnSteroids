package dev.waradu.nextrononsteroids.commands;

import dev.waradu.nextrononsteroids.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class TrollCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nxos.troll")) {
            sender.sendMessage(Main.getPrefix()+"Not enought permissions!");
            return false;
        }

        Player player = (Player) (sender);

        if (Objects.equals(args[0], "join")) {
            String playerName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            Bukkit.broadcastMessage(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            Main.getNexInstance().getConfig().getString("join_message").replace(
                                    "%p",
                                    playerName
                            )
                    )
            );
        }
        else if (Objects.equals(args[0], "leave")) {
            String playerName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            Bukkit.broadcastMessage(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            Main.getNexInstance().getConfig().getString("leave_message").replace(
                                    "%p",
                                    playerName
                            )
                    )
            );
        }
        else if (Objects.equals(args[0], "message")) {
            String targetPlayerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer == null) {
                player.sendMessage(Main.getPrefix() + "Player not found: " + targetPlayerName);
            } else {
                String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

                if (message.startsWith("\"") && message.endsWith("\"")) {
                    message = message.substring(1, message.length() - 1);
                }

                targetPlayer.sendMessage(targetPlayer.getDisplayName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', message));
            }
        }
        else if (Objects.equals(args[0], "ban")) {
            String targetPlayerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer == null) {
                player.sendMessage(Main.getPrefix() + "Player not found: " + targetPlayerName);
            } else {
                targetPlayer.kickPlayer("You are banned from this server");
                player.sendMessage(Main.getPrefix()+targetPlayerName + " has been fake-banned!");
            }
        }
        else if (Objects.equals(args[0], "op")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Main.getPrefix() + "Player not found: " + args[1]);
                return false;
            }
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players.isOp()) {
                    players.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&', "&7&o[Server: Made " + target.getName() + " a server operator]"
                        )
                    );
                }
            };
        }
        else if (Objects.equals(args[0], "spam")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Main.getPrefix() + "Player not found: " + args[1]);
                return false;
            }
            for (int i = 0; i < 20; i++) {
                target.sendMessage(
                    ChatColor.translateAlternateColorCodes(
                    '&', "Important Message:\n&cA wild error occured. you suck!"
                    )
                );
            }

            return true;
        }
        else if (Objects.equals(args[0], "launch")) {
            float velocity = args.length == 3 ? Float.parseFloat(args[2]) : 2;
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Main.getPrefix() + "Player not found: " + args[1]);
                return false;
            }
            target.setVelocity(target.getVelocity().add(new Vector(0, velocity, 0)));

            return true;
        }
        else if (Objects.equals(args[0], "unheal")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Main.getPrefix() + "Player not found: " + args[1]);
                return false;
            }

            target.setHealth(1);

            target.setSaturation(0);

            target.setFoodLevel(1);

            return true;
        }
        else if (Objects.equals(args[0], "popup")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Main.getPrefix() + "Player not found: " + args[1]);
                return false;
            }

            target.openInventory(target.getInventory());

            return true;
        }
        else {
            player.sendMessage(Main.getPrefix()+"Usage: /troll <mode> <player> [args]");
        }

        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        list.add("join");
        list.add("leave");
        list.add("message");
        list.add("ban");
        list.add("op");
        list.add("spam");
        list.add("launch");
        list.add("unheal");
        list.add("popup");

        if (args.length == 2) {
            list = new ArrayList<String>();
            for (Player player : Bukkit.getOnlinePlayers()) list.add(player.getName());
        };
        if (args.length > 2) {
            list = new ArrayList<String>();
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentArg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentArg)) {
                continue;
            }
            completerList.add(s);
        }

        if (Objects.equals(args[0], "message")) {
            if (args.length > 2) {
                completerList = new ArrayList<String>();
                String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                message = message.replace("\"", "");
                completerList.add("\""+message+"\"");
            }
        }

        return completerList;
    }
}