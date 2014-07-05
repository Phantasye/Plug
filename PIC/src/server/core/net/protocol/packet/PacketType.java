package server.core.net.protocol.packet;

import server.world.entity.player.Player;

public interface PacketType {
    public void processPacket(Player c, int packetType, int packetSize);
}
