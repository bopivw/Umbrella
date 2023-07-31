package org.daniel.loadout.utility.bukkit;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.clip.placeholderapi.PlaceholderAPI;

@SuppressWarnings("all")
public class BukkitText {

    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");
    private static final Pattern FORMAT_PATTERN = Pattern.compile("&([a-zA-Z0-9])");

    public static String format(String text) {
        text = colorize(text);
        text = hexColors(text);
        text = replacePlaceholders(text);
        return text;
    }

    public static String format(Player player, String text) {
        text = replacePlaceholders(text);
        text = formatPlaceholder(player, text);
        text = colorize(text);
        text = hexColors(text);
        return text;
    }

    public static String formatPP(Player player, String text) {
        text = PlaceholderAPI.setPlaceholders(player, text);
        text = format(text);
        return text;
    }

    public static List<String> formatListPAPI(Player player, List<String> list) {
        List<String> formatted = new ArrayList<>();
        for (String s : list) {
            formatted.add(formatPP(player, s));
        }
        return formatted;
    }


    public static String formatPlaceholder(Player player, String str) {

        return PlaceholderAPI.setPlaceholders(player, str);
    }

    public static ItemStack formatItemStack(Player player, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(formatListPAPI(player, itemMeta.getLore()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static String formatTime(int seconds) {
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    public static String formatBoolean(boolean value, String one, String two) {
        return value ? one : two;
    }

    public static @NotNull List<String> formatList(List<String> list) {
        List<String> formatted = new ArrayList<>();
        for (String s : list) {
            formatted.add(format(s));
        }
        return formatted;
    }

    public static String formatLocation(Location location) {
        return "x=" + location.getBlockX() + ", y=" + location.getBlockY() + ", z=" + location.getBlockZ();
    }

    public static Location getCenterLocation(Location location) {
        World world = location.getWorld();
        double x = location.getBlockX() + 0.5;
        double y = location.getBlockY() + 0.5;
        double z = location.getBlockZ() + 0.5;
        return new Location(world, x, y, z);
    }

    private static String hexColors(String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String colorString = matcher.group(1);
            ChatColor color = ChatColor.of("#" + colorString);
            matcher.appendReplacement(sb, color.toString());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String colorize(String text) {
        Matcher matcher = FORMAT_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String formatCode = matcher.group(1);
            char formatChar = formatCode.charAt(0);
            ChatColor format = ChatColor.getByChar(formatChar);
            matcher.appendReplacement(sb, String.valueOf(format));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String replacePlaceholders(String text) {
        return text;
    }
}
