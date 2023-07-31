package org.daniel.loadout.utility.bukkit;

import static org.daniel.loadout.utility.bukkit.BukkitText.format;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import lombok.Getter;

@Getter
public class BukkitManager {


    private final World world;
    public BukkitManager(){
        this.world = Bukkit.getWorlds().get(0);
    }

    public void broadcast(String text) {
        Bukkit.broadcastMessage(format(text));
    }

    public long getTime(){
        return world.getTime();
    }
    public void setTime(long time){
        world.setTime(time);
    }

    public  Player findNearestPlayer(Location location) {
        double nearestDistanceSquared = Double.MAX_VALUE;
        Player nearestPlayer = null;

        for (Player player : location.getWorld().getPlayers()) {
            double distanceSquared = player.getLocation().distanceSquared(location);
            if (distanceSquared < nearestDistanceSquared) {
                nearestDistanceSquared = distanceSquared;
                nearestPlayer = player;
            }
        }

        return nearestPlayer;
    }
}