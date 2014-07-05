package com.plug.world.entity.player.packets;

import com.plug.Constants;
import com.plug.Game;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Drop Item
 */
public class DropItem implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int itemId = c.getInStream().readUnsignedWordA();
        c.getInStream().readUnsignedByte();
        c.getInStream().readUnsignedByte();
        int slot = c.getInStream().readUnsignedWordA();
        if (System.currentTimeMillis() - c.alchDelay < 1800) {
            return;
        }
        if (c.arenas()) {
            c.sendMessage("You can't drop items inside the arena!");
            return;
        }
        if (c.inTrade) {
            c.sendMessage("You can't drop items while trading!");
            return;
        }

        boolean droppable = true;
        for (int i : Constants.UNDROPPABLE_ITEMS) {
            if (i == itemId) {
                droppable = false;
                break;
            }
        }
        if (c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
            if (droppable) {
                if (c.underAttackBy > 0) {
                    if (c.getShops().getItemShopValue(itemId) > 1000) {
                        c.sendMessage("You may not drop items worth more than 1000 while in combat.");
                        return;
                    }
                }
                Game.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
                c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
            } else {
                c.sendMessage("This items cannot be dropped.");
            }
        }

    }
}
