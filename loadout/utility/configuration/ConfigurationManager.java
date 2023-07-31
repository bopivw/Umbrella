package org.daniel.loadout.utility.configuration;


import org.daniel.loadout.loadout;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {
    private final Map<String, Configuration> configurationMap = new HashMap<>();
    
    public Configuration getConfig(String name) {
        Configuration configuration = configurationMap.get(name);
        if (configuration == null) {
            File configurationFile = new File(loadout.getInstance().getDataFolder(), name);
            if (! configurationFile.exists()) {
                configurationFile.getParentFile().mkdirs();
                loadout.getInstance().saveResource(name, false);
            }
            
            configuration = new Configuration(configurationFile);
            configuration.reload();
            configurationMap.put(name, configuration);
        }
        
        return configuration;
    }
    
    public void removeConfig(String name) {
        Configuration configuration = configurationMap.remove(name);
        if (configuration != null) {
            File configFile = configuration.getFile();
            if (configFile.exists()) {
                configFile.delete();
            }
        }
    }
}