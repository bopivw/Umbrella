package org.daniel.loadout.utility.management.menus;

import static org.daniel.loadout.utility.bukkit.BukkitText.formatItemStack;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class MenuUtils implements Listener {
 private final Map<Player, Menu> playerMenus;

 public MenuUtils(Plugin plugin) {
  this.playerMenus = new HashMap<>();
  Bukkit.getPluginManager().registerEvents(this, plugin);
 }

 public void newMenu(Player player, String titulo, int tamanio) {
  Menu menu = new Menu(titulo, tamanio);
  playerMenus.put(player, menu);
  player.openInventory(menu.getInventory());
 }

 public void addItem(Player player, int slot, ItemStack itemStack, Runnable accion) {
  Menu menu = playerMenus.get(player);
  if (menu == null) {
   return;
  }

  menu.addItem(slot, formatItemStack(player,itemStack), accion);
 }

 @EventHandler
 public void onInventoryClick(InventoryClickEvent event) {
  if (! (event.getWhoClicked() instanceof Player player)) {
   return;
  }

  Menu menu = playerMenus.get(player);
  if (menu == null || ! menu.getInventory().equals(event.getInventory())) {
   return;
  }

  int clickedSlot = event.getRawSlot();
  MenuItem menuItem = menu.getItem(clickedSlot);
  if (menuItem != null) {
   menuItem.execute(player);
   event.setCancelled(true);
  }
 }

 @EventHandler
 public void onInventoryClickItem(InventoryClickEvent event) {
  if (! (event.getWhoClicked() instanceof Player player)) {
   return;
  }

  Menu menu = playerMenus.get(player);
  if (menu == null || ! menu.getInventory().equals(event.getInventory())) {
   return;
  }

  int clickedSlot = event.getRawSlot();
  MenuItem menuItem = menu.getItem(clickedSlot);
  if (menuItem != null) {
   player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1F, 3F);
  }
 }

 public void openLastMenu(Player player) {
  Menu menu = playerMenus.get(player);
  if (menu != null) {
   player.openInventory(menu.getInventory());
  } else {
   player.closeInventory();
  }
 }

}