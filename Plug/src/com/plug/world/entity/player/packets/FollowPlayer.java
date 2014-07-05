package com.plug.world.entity.player.packets;

import com.plug.Game;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Follow Player
 */
public class FollowPlayer implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int followPlayer = c.getInStream().readUnsignedWordBigEndian();
        if (Game.playerHandler.players[followPlayer] == null) {
            return;
        }
        c.playerIndex = 0;
        c.npcIndex = 0;
        c.mageFollow = false;
        c.usingBow = false;
        c.usingRangeWeapon = false;
        c.followDistance = 1;
        c.followId = followPlayer;
    }
}
