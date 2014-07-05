package com.plug.world.entity.player.packets;

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Bank X Items
 */
public class BankX2 implements PacketType {
    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int Xamount = c.getInStream().readDWord();
        if (Xamount == 0)
            Xamount = 1;
        switch (c.xInterfaceId) {
            case 5064:
                if (c.inTrade) {
                    c.sendMessage("You can't store items while trading!");
                    return;
                }
                c.getItems().bankItem(c.playerItems[c.xRemoveSlot], c.xRemoveSlot, Xamount);
                break;

            case 5382:
                c.getItems().fromBank(c.bankItems[c.xRemoveSlot], c.xRemoveSlot, Xamount);
                break;

            case 3322:
                if (c.duelStatus <= 0) {
                    c.getTradeAndDuel().tradeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
                } else {
                    c.getTradeAndDuel().stakeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
                }
                break;

            case 3415:
                if (c.duelStatus <= 0) {
                    c.getTradeAndDuel().fromTrade(c.xRemoveId, c.xRemoveSlot, Xamount);
                }
                break;

            case 6669:
                c.getTradeAndDuel().fromDuel(c.xRemoveId, c.xRemoveSlot, Xamount);
                break;
        }
    }
}