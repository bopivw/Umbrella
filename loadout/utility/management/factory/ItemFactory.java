package org.daniel.loadout.utility.management.factory;

import static org.daniel.loadout.utility.bukkit.BukkitText.format;
import static org.daniel.loadout.utility.bukkit.BukkitText.formatList;
import static org.daniel.loadout.utility.bukkit.BukkitText.formatListPAPI;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ItemFactory {

    @Setter private String displayName;
    @Getter @Setter private List<String> lore;
    @Getter @Setter private Material material;
    private Player player;
    private final Player randomPlayer;

    public ItemFactory(Player player,Material material, String displayName, String... lore) {
        this.displayName = displayName;
        this.lore = new ArrayList<>(formatListPAPI(player,Arrays.asList(lore)));
        this.material = material;
        this.randomPlayer = randomPlayer();
        this.player = player;
    }

    public ItemFactory(Player player,Material material, String displayName, List<String> lore) {
        this.displayName = displayName;
        this.lore = new ArrayList<>(formatListPAPI(player,lore));
        this.material = material;
        this.randomPlayer = randomPlayer();
        this.player = player;
    }

    public ItemFactory(Material material, String displayName, String... lore) {
        this.displayName = displayName;
        this.lore = new ArrayList<>(Arrays.asList(lore));
        this.material = material;
        this.randomPlayer = randomPlayer();
    }

    public ItemFactory(Material material, String displayName, List<String> lore) {
        this.displayName = displayName;
        this.lore = new ArrayList<>(lore);
        this.material = material;
        this.randomPlayer = randomPlayer();
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        itemMeta.setLore(formatList(lore));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack buildCount(int count) {
        ItemStack itemStack = new ItemStack(material, count);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        itemMeta.setLore(formatList(lore));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
    public ItemStack buildLeather(Color color) {
        ItemStack itemStack = new ItemStack(material);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        itemMeta.setLore(formatList(lore));
        itemMeta.setColor(color);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    public ItemStack buildPlayerHead(String playerName) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
        skullMeta.setDisplayName(format(displayName));
        skullMeta.setLore(formatList(lore));

        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    public Player randomPlayer() {
        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        return list.get(new Random().nextInt(list.size()));
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLore(List<String> lore) {
        this.lore = new ArrayList<>(lore);
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ItemStack buildPlayerHeadTexture(String textureUrl) {
        textureUrl = "http://textures.minecraft.net/texture/" + textureUrl;
        ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        String base64Texture = getBase64FromTextureUrl(textureUrl);
        profile.getProperties().put("textures", new Property("textures", base64Texture));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skullMeta.setDisplayName(format(displayName));
        skullMeta.setLore(formatList(lore));

        skullItem.setItemMeta(skullMeta);
        return skullItem;
    }

    public String getBase64FromTextureUrl(String textureUrl) {
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + UUID.randomUUID().toString();
        String payload = "{\"textures\":{\"SKIN\":{\"url\":\"" + textureUrl + "\"}}}";

        byte[] encodedPayload = Base64.getEncoder().encode(payload.getBytes());
        String encodedPayloadString = new String(encodedPayload);

        return encodedPayloadString;
    }
}
