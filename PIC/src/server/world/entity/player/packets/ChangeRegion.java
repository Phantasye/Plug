package server.world.entity.player.packets;

import server.Server;
import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;

public class ChangeRegion implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        c.getPA().removeObjects();
        Server.objectHandler.updateObjects(c);
    }

}
