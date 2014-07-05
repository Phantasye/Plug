package server.world.entity.player.packets;

import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;

/**
 * Magic on items
 */
public class MagicOnItems implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int slot = c.getInStream().readSignedWord();
        int itemId = c.getInStream().readSignedWordA();
        int junk = c.getInStream().readSignedWord();
        int spellId = c.getInStream().readSignedWordA();

        c.usingMagic = true;
        c.getPA().magicOnItems(slot, itemId, spellId);
        c.usingMagic = false;

    }

}
