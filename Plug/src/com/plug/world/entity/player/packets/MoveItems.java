package com.plug.world.entity.player.packets;

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Move Items
 */
public class MoveItems implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int somejunk = c.getInStream().readUnsignedWordA(); // junk
        int itemFrom = c.getInStream().readUnsignedWordA();// slot1
        int itemTo = (c.getInStream().readUnsignedWordA() - 128);// slot2
        // c.sendMessage("junk: " + somejunk);
        if (c.inTrade) {
            c.getTradeAndDuel().declineTrade();
            return;
        }
        if (c.tradeStatus == 1) {
            c.getTradeAndDuel().declineTrade();
            return;
        }
        if (c.duelStatus == 1) {
            c.getTradeAndDuel().declineDuel();
            return;
        }
        c.getItems().moveItems(itemFrom, itemTo, somejunk);
    }
}
