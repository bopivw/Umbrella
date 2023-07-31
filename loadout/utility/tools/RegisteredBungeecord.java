package org.daniel.loadout.utility.tools;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.daniel.loadout.loadout;

public class RegisteredBungeecord implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subChannel = input.readUTF();
    }

    public void sendPlayer(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);
        player.sendPluginMessage(loadout.getInstance(), "BungeeCord", output.toByteArray());
    }
}