package com.plug.world.entity.player.packets;

/**
 * @author Ryan / Lmctruck30
 */

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.UseItem;

public class ItemOnItem implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int usedWithSlot = c.getInStream().readUnsignedWord();
        int itemUsedSlot = c.getInStream().readUnsignedWordA();
        int useWith = c.playerItems[usedWithSlot] - 1;
        int itemUsed = c.playerItems[itemUsedSlot] - 1;
        if (!c.getItems().playerHasItem(useWith, 1, usedWithSlot) || !c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
            return;
        }
        UseItem.ItemonItem(c, itemUsed, useWith);
    }

}
