package dev.waradu.nextrononsteroids;

import dev.waradu.nextrononsteroids.commands.TrollCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static String prefix;

    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin nextronPlugin = pluginManager.getPlugin("Nextron");

        if (nextronPlugin == null) {
            Bukkit.getServer().shutdown();
            System.err.println("NextronOnSteroids: Nextron plugin is not installed. Please install it to run this server.");
            return;
        }

        prefix = tk.pandadev.nextron.Main.getPrefix();
        instance = this;
        Bukkit.getConsoleSender().sendMessage("Nextron is now on Steroids!");
        getCommand("troll").setExecutor(new TrollCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPrefix() {
        return prefix;
    }

    public static tk.pandadev.nextron.Main getNexInstance() {
        return tk.pandadev.nextron.Main.getInstance();
    }

    public static Main getInstance() {
        return instance;
    }
}
