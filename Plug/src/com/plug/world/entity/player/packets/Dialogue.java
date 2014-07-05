package com.plug.world.entity.player.packets;

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Dialogue
 */
public class Dialogue implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {

        if (c.nextChat > 0) {
            c.getDH().sendDialogues(c.nextChat, c.talkingNpc);
        } else {
            c.getDH().sendDialogues(0, -1);
        }

    }

}
