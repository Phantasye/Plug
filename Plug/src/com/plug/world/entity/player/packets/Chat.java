package com.plug.world.entity.player.packets;

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Chat
 */
public class Chat implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        c.setChatTextEffects(c.getInStream().readUnsignedByteS());
        c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte) (c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
        c.chatTextUpdateRequired = true;
    }
}
