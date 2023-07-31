package org.daniel.loadout.utility.bukkit;


import static org.daniel.loadout.utility.bukkit.BukkitText.format;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.net.InetAddress;
import java.util.Optional;

import lombok.Getter;

@Getter
public class BukkitPlayer {

    private final Player player;

    public BukkitPlayer(Player player) {
        this.player = player;
    }

    public World getWorld() {
        return player.getWorld();
    }

    public Location getLocation() {
        return player.getLocation().toCenterLocation();
    }

    public String getAddress() {
        InetAddress address = player.getAddress().getAddress();
        return address.toString().replace("/", "").replace("\\", "");
    }

    public void teleport(Location location) {
        player.teleport(location.toCenterLocation());
    }

    public Optional<ItemStack> getItemInHand() {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        return itemStack.getType() == Material.AIR ? Optional.empty() : Optional.of(itemStack);
    }

    public void clear() {
        player.getInventory().clear();
    }

    public void addItem(ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
    }

    public void sendMessage(String... message) {
        for (String s : message){
        player.sendMessage(format(player,s));
    }}


    public void playSound(Sound sound,float volume,float pitch){
        player.playSound(player.getLocation(),sound,volume,pitch);
    }
    public void sendActionbar(String message) {
        TextComponent textComponent = new TextComponent(format(message));
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
    }

    public String getDisplayName() {
        return player.getDisplayName();
    }

    public void setFly(boolean flight) {
        player.setAllowFlight(flight);
        player.setFlying(flight);
    }
    public boolean getFly(){
        return player.getAllowFlight();
    }

    public void addPotionEffect(PotionEffectType effect, int duration, int amplifier) {
        player.addPotionEffect(new PotionEffect(effect, duration, amplifier));
    }

    public void clearPotionEffect() {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public int getExperience() {
        return player.getTotalExperience();
    }

    public void setExperience(int experience) {
        player.setTotalExperience(experience);
    }

    public int getExperienceLevel() {
        return player.getLevel();
    }

    public void setExperienceLevel(int experienceLevel) {
        player.setLevel(experienceLevel);
    }
}
