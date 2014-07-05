package server.world.entity.player.packets;

import server.core.net.protocol.packet.PacketType;
import server.util.Misc;
import server.world.entity.player.Player;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 * Proper Streams
 */

public class ItemClick3 implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int itemId11 = c.getInStream().readSignedWordBigEndianA();
        int itemId1 = c.getInStream().readSignedWordA();
        int itemId = c.getInStream().readSignedWordA();
        if (!c.getItems().playerHasItem(itemId, 1)) {
            return;
        }

        switch (itemId) {

            default:
                if (c.playerRights == 3)
                    Misc.println(c.playerName + " - Item3rdOption: " + itemId + " : " + itemId11 + " : " + itemId1);
                break;
        }

    }

}
