package server.world.entity.player.packets;

import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;

public class IdleLogout implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        // if (!c.playerName.equalsIgnoreCase("Sanity"))
        // c.logout();
    }
}