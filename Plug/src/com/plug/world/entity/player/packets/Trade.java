package com.plug.world.entity.player.packets;

import com.plug.Constants;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Trading
 */
public class Trade implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int tradeId = c.getInStream().readSignedWordBigEndian();
        c.getPA().resetFollow();
        if (c.disconnected) {
            c.tradeStatus = 0;
        }
        if (c.arenas()) {
            c.sendMessage("You can't trade inside the arena!");
            return;
        }

        if (c.playerRights == 2 && !Constants.ADMIN_CAN_TRADE) {
            c.sendMessage("Trading as an admin has been disabled.");
            return;
        }
        if (tradeId != c.slot)
            c.getTradeAndDuel().requestTrade(tradeId);
    }

}
