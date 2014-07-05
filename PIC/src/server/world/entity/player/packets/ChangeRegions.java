package server.world.entity.player.packets;

import server.Server;
import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        Server.objectHandler.updateObjects(c);
        Server.itemHandler.reloadItems(c);
        c.updateSigns();

        if (c.skullTimer > 0) {
            c.isSkulled = true;
            c.headIconPk = 0;
            c.getPA().requestUpdates();
        }

    }

}
