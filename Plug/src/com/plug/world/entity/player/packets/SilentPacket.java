package com.plug.world.entity.player.packets;

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Slient Packet
 */
public class SilentPacket implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {

    }
}
