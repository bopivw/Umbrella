package org.daniel.loadout.utility.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.daniel.loadout.utility.bukkit.BukkitManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Configuration extends YamlConfiguration {
    private final File file;
    
    public Configuration(File file) {
        this.file = file;
    }
    
    public void reload() {
        try {
            load(file);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            throw new RuntimeException("Error reloading configuration: " + file.getName(), e);
        }
    }
    
    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Error saving configuration: " + file.getName());
            e.printStackTrace();
        }
    }
    
    public void safeSave() {
        save();
    }
    
    public void setIfNotExist(String path, Object value) {
        if (! contains(path) && value != null) {
            set(path, value);
            safeSave();
        }
    }
    
    public int getInt(@NotNull String path, int defaultValue) {
        setIfNotExist(path, defaultValue);
        return super.getInt(path, defaultValue);
    }
    
    public String getString(@NotNull String path, String defaultValue) {
        setIfNotExist(path, defaultValue);
        return super.getString(path, defaultValue);
    }
    
    public boolean getBoolean(@NotNull String path, boolean defaultValue) {
        setIfNotExist(path, defaultValue);
        return super.getBoolean(path, defaultValue);
    }
    
    public void setBoolean(String path, boolean value) {
        set(path, value);
        safeSave();
    }
    
    public List<String> getStringList(String path, List<String> defaultValue) {
        setIfNotExist(path, defaultValue);
        return getStringList(path);
    }
    
    private Sound getSound(String key) {
        String name = getString(key);
        for (Sound sound : Sound.values()) {
            if (name.equalsIgnoreCase(sound.name())) {
                return sound;
            }
        }
        
        Bukkit.getLogger().warning("Couldn't load sound '" + name + "' from configuration file! (Invalid name?)");
        return null;
    }
    
    public Sound getSound(String key, String defaultValue) {
        setIfNotExist(key, defaultValue);
        return getSound(key);
    }
    
    public Sound getSound(String key, Sound defaultValue) {
        return getSound(key, defaultValue.toString());
    }
    
    public Location getLocation(@NotNull String key) {
        String worldName = getString(key + ".world");
        if (worldName == null) {
            throw new IllegalArgumentException("World name is null for key: " + key);
        }
        
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            world = new BukkitManager().getWorld();
        }
        
        double x = getDouble(key + ".x");
        double y = getDouble(key + ".y");
        double z = getDouble(key + ".z");
        float pitch = (float) getDouble(key + ".pitch");
        float yaw = (float) getDouble(key + ".yaw");
        return new Location(world, x, y, z, yaw, pitch);
    }
    
    public void setLocation(String key, Location location) {
        set(key + ".world", location.getWorld().getName());
        set(key + ".x", location.getX());
        set(key + ".y", location.getY());
        set(key + ".z", location.getZ());
        set(key + ".pitch", location.getPitch());
        set(key + ".yaw", location.getYaw());
        safeSave();
    }
    
    public void toggleBoolean(String key) {
        set(key, ! getBoolean(key));
        safeSave();
    }
    
    public File getFile() {
        return file;
    }
}

