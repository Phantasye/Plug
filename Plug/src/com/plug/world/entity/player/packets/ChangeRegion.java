package com.plug.world.entity.player.packets;

import com.plug.Game;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

public class ChangeRegion implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        c.getPA().removeObjects();
        Game.objectHandler.updateObjects(c);
    }

}
