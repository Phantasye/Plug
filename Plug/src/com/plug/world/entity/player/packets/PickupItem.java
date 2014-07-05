package com.plug.world.entity.player.packets;

import com.plug.Game;
import com.plug.core.event.CycleEvent;
import com.plug.core.event.CycleEventHandler;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Pickup Item
 */
public class PickupItem implements PacketType {

    @Override
    public void processPacket(final Player c, int packetType, int packetSize) {
        c.pItemY = c.getInStream().readSignedWordBigEndian();
        c.pItemId = c.getInStream().readUnsignedWord();
        c.pItemX = c.getInStream().readSignedWordBigEndian();
        if (Math.abs(c.getX() - c.pItemX) > 25 || Math.abs(c.getY() - c.pItemY) > 25) {
            c.resetWalkingQueue();
            return;
        }
        c.getCombat().resetPlayerAttack();
        CycleEventHandler.getSingleton().stopEvents(Player.WALKING_TO_ACTIONS_KEY);
        CycleEventHandler.getSingleton().addEvent(new CycleEvent(Player.WALKING_TO_ACTIONS_KEY, 1) {
            @Override
            public void execute() {
                if (c.disconnected) {
                    this.stop();
                }
                if (c.getX() == c.pItemX && c.getY() == c.pItemY) {
                    Game.itemHandler.removeGroundItem(c, c.pItemId, c.pItemX, c.pItemY, true);
                    this.stop();
                }
            }
        });
    }
}
