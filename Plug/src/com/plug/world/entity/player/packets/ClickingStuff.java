package com.plug.world.entity.player.packets;

import com.plug.Game;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.util.Misc;
import com.plug.world.entity.player.Player;

/**
 * Clicking stuff (interfaces)
 */
public class ClickingStuff implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        if (c.inTrade) {
            if (!c.acceptedTrade) {
                Misc.println("trade reset");
                c.getTradeAndDuel().declineTrade();
            }
        }

        Player o = Game.playerHandler.players[c.duelingWith];
        if (o != null) {
            if (c.duelStatus >= 1 && c.duelStatus <= 4) {
                c.getTradeAndDuel().declineDuel();
                o.getTradeAndDuel().declineDuel();
            }
        }

        if (c.duelStatus == 6) {
            c.getTradeAndDuel().claimStakedItems();
        }

    }

}
