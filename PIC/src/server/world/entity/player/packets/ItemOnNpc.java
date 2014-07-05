package server.world.entity.player.packets;

import server.Server;
import server.core.net.protocol.packet.PacketType;
import server.world.entity.player.Player;
import server.world.entity.player.UseItem;

public class ItemOnNpc implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int itemId = c.getInStream().readSignedWordA();
        int i = c.getInStream().readSignedWordA();
        int slot = c.getInStream().readSignedWordBigEndian();
        int npcId = Server.npcHandler.npcs[i].npcType;
        if (!c.getItems().playerHasItem(itemId, 1, slot)) {
            return;
        }

        UseItem.ItemonNpc(c, itemId, npcId, slot);
    }
}
