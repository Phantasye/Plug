package com.plug.world.entity.player.packets;

import com.plug.Game;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.util.Misc;
import com.plug.world.entity.player.Player;

public class ItemOnGroundItem implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int a1 = c.getInStream().readSignedWordBigEndian();
        int itemUsed = c.getInStream().readSignedWordA();
        int groundItem = c.getInStream().readUnsignedWord();
        int gItemY = c.getInStream().readSignedWordA();
        int itemUsedSlot = c.getInStream().readSignedWordBigEndianA();
        int gItemX = c.getInStream().readUnsignedWord();
        if (!c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
            return;
        }
        if (!Game.itemHandler.itemExists(groundItem, gItemX, gItemY)) {
            return;
        }

        switch (itemUsed) {

            default:
                if (c.playerRights == 3)
                    Misc.println("ItemUsed " + itemUsed + " on Ground Item " + groundItem);
                break;
        }
    }

}
