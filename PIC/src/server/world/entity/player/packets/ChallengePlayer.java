package server.world.entity.player.packets;

import server.Server;
import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;

/**
 * Challenge Player
 */
public class ChallengePlayer implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        switch (packetType) {
            case 128:
                int answerPlayer = c.getInStream().readUnsignedWord();
                if (Server.playerHandler.players[answerPlayer] == null) {
                    return;
                }

                if (c.arenas() || c.duelStatus == 5) {
                    c.sendMessage("You can't challenge inside the arena!");
                    return;
                }

                c.getTradeAndDuel().requestDuel(answerPlayer);
                break;
        }
    }
}
