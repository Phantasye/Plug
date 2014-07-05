package com.plug.world.entity.player.packets;

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.util.Misc;
import com.plug.world.entity.player.Player;

/**
 * Chat
 */
public class ClanChat implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        String textSent = Misc.longToPlayerName2(c.getInStream().readQWord());
        textSent = textSent.replaceAll("_", " ");
        // c.sendMessage(textSent);
        // Server.clanChat.handleClanChat(c, textSent);
    }
}
