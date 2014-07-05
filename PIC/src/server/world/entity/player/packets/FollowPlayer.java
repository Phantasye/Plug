package server.world.entity.player.packets;

import server.Server;
import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;

/**
 * Follow Player
 */
public class FollowPlayer implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int followPlayer = c.getInStream().readUnsignedWordBigEndian();
        if (Server.playerHandler.players[followPlayer] == null) {
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
