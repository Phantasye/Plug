package com.plug.world.entity.player.packets;

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

public class IdleLogout implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        // if (!c.playerName.equalsIgnoreCase("Sanity"))
        // c.logout();
    }
}