package com.plug.world.entity.player.packets;

import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Bank X Items
 */
public class BankX1 implements PacketType {

    public static final int PART1 = 135;
    public static final int PART2 = 208;
    public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        if (packetType == 135) {
            c.xRemoveSlot = c.getInStream().readSignedWordBigEndian();
            c.xInterfaceId = c.getInStream().readUnsignedWordA();
            c.xRemoveId = c.getInStream().readSignedWordBigEndian();
        }
        if (c.xInterfaceId == 3900) {
            c.getShops().buyItem(c.xRemoveId, c.xRemoveSlot, 20);// buy 20
            c.xRemoveSlot = 0;
            c.xInterfaceId = 0;
            c.xRemoveId = 0;
            return;
        }

        if (packetType == PART1) {
            synchronized (c) {
                c.getOutStream().createFrame(27);
            }
        }

    }
}
