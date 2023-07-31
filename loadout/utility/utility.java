package org.daniel.loadout.utility;

import org.bukkit.plugin.java.JavaPlugin;
import org.daniel.loadout.utility.tools.RegisteredTask;

import lombok.Getter;

@Getter
public class utility {

    private final JavaPlugin javaPlugin;
    public utility(JavaPlugin plugin){
        this.javaPlugin = plugin;
    }

    public void register(){

        new RegisteredTask(javaPlugin);

    }

}
