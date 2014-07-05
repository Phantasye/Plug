package server.world.entity.player.packets;

import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;

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
