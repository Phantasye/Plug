package com.plug.core.net.protocol.packet;

import com.plug.world.entity.player.Player;

public interface PacketType {
    public void processPacket(Player c, int packetType, int packetSize);
}
