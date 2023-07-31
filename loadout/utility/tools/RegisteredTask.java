 package org.daniel.loadout.utility.tools;

 import org.bukkit.Bukkit;
 import org.bukkit.plugin.java.JavaPlugin;

 public class RegisteredTask {

     private JavaPlugin javaPlugin;
     public RegisteredTask(JavaPlugin javaPlugin){
         this.javaPlugin = javaPlugin;

     }
     public RegisteredTask(Runnable runnable) {
         this(runnable, 0L);
     }

     public RegisteredTask(Runnable runnable, long delay) {

         if (javaPlugin.isEnabled()) {
             int id = Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, runnable, delay);
         } else {
             runnable.run();
         }
     }
 }