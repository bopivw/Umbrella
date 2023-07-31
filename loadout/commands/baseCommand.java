package org.daniel.loadout.commands;

import static org.daniel.loadout.utility.bukkit.BukkitText.format;

import org.daniel.loadout.loadout;

import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.orphan.OrphanCommand;



@CommandPermission("base.command")
public class baseCommand implements OrphanCommand {

    @Subcommand("reload")
    void reload(BukkitCommandActor actor){
        loadout.getInstance().getConfig().reload();
        actor.getAsPlayer().sendMessage(format("&aRefrezcaste los archivos correctamente."));
    }
}
