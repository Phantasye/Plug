package com.plug.world.entity.player.packets;

/**
 * @author Ryan / Lmctruck30
 */

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.UseItem;

public class ItemOnObject implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        /*
         * a = ? b = ?
         */

        int a = c.getInStream().readUnsignedWord();
        int objectId = c.getInStream().readSignedWordBigEndian();
        int objectY = c.getInStream().readSignedWordBigEndianA();
        int b = c.getInStream().readUnsignedWord();
        int objectX = c.getInStream().readSignedWordBigEndianA();
        int itemId = c.getInStream().readUnsignedWord();
        if (!c.getItems().playerHasItem(itemId, 1)) {
            return;
        }
        UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);

    }

}
