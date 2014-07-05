package com.plug.world.entity.player.packets;

import com.plug.Game;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        Game.objectHandler.updateObjects(c);
        Game.itemHandler.reloadItems(c);
        c.updateSigns();

        if (c.skullTimer > 0) {
            c.isSkulled = true;
            c.headIconPk = 0;
            c.getPA().requestUpdates();
        }

    }

}
