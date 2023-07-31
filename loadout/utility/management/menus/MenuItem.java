package org.daniel.loadout.utility.management.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;

@Getter
public class MenuItem {
 private final ItemStack itemStack;
 private final Runnable action;

 public MenuItem(ItemStack itemStack, Runnable action) {
	this.itemStack = itemStack;
	this.action = action;
 }

 public void execute(Player player) {
	action.run();
 }

}