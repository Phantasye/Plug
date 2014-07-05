package com.plug.world.entity.player.packets;

import com.plug.Game;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.UseItem;

public class ItemOnNpc implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int itemId = c.getInStream().readSignedWordA();
        int i = c.getInStream().readSignedWordA();
        int slot = c.getInStream().readSignedWordBigEndian();
        int npcId = Game.npcHandler.npcs[i].npcType;
        if (!c.getItems().playerHasItem(itemId, 1, slot)) {
            return;
        }

        UseItem.ItemonNpc(c, itemId, npcId, slot);
    }
}
