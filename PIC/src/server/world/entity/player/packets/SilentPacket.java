package server.world.entity.player.packets;

import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;

/**
 * Slient Packet
 */
public class SilentPacket implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {

    }
}
