package org.daniel.loadout.utility.management.menus;

import static org.daniel.loadout.utility.bukkit.BukkitText.format;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class Menu {
 @Getter private final String title;
 @Getter private final int size;
 @Getter
 private final Inventory inventory;
 private final Map<Integer, MenuItem> menuItems;

 public Menu(String title, int size) {
	this.title = title;
	this.size = size;
	this.inventory = Bukkit.createInventory(null, size, format(title));
	this.menuItems = new HashMap<>();
 }

 public void addItem(int slot, ItemStack itemStack, Runnable accion) {
	MenuItem menuItem = new MenuItem(itemStack, accion);
	menuItems.put(slot, menuItem);
	ItemStack item = itemStack.clone();
	inventory.setItem(slot, item);
 }

 public MenuItem getItem(int slot) {
	return menuItems.get(slot);
 }

}
