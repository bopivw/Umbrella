package org.daniel.loadout;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.daniel.loadout.utility.bukkit.BukkitManager;
import org.daniel.loadout.utility.bukkit.BukkitText;
import org.daniel.loadout.utility.configuration.Configuration;
import org.daniel.loadout.utility.configuration.ConfigurationManager;
import org.daniel.loadout.utility.utility;

import lombok.Getter;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.bukkit.BukkitCommandHandler;


public final class loadout extends JavaPlugin {

    @Getter private static loadout instance;
    private ConfigurationManager cm;
    @Getter private Configuration config;
    @Getter private Configuration messages;
    @Getter private Configuration locations;
    private CommandHandler commandHandler;
    public static BukkitManager bukkitManager;
    public static BukkitText bukkitText;

    @Override
    public void onEnable() {
        loadout.instance = this;
        this.commandHandler = BukkitCommandHandler.create(this);

        this.cm = new ConfigurationManager();
        this.config = cm.getConfig("config.yml");
        this.messages = cm.getConfig("messages.yml");
        this.locations = cm.getConfig("locations.yml");

        loadout.bukkitManager = new BukkitManager();
        loadout.bukkitText = new BukkitText();

        new utility(this).register();

    }

    public void commmand(Object ... command){
        for (Object o : command) {
            commandHandler.register(o);
        }
    }
    public void event(Listener eventClass){
        Bukkit.getPluginManager().registerEvents(eventClass,this);
    }
}