package server.core.net.protocol.packet;

import server.world.entity.player.Player;
import server.world.entity.player.packets.AttackPlayer;
import server.world.entity.player.packets.Bank10;
import server.world.entity.player.packets.Bank5;
import server.world.entity.player.packets.BankAll;
import server.world.entity.player.packets.BankX1;
import server.world.entity.player.packets.BankX2;
import server.world.entity.player.packets.ChallengePlayer;
import server.world.entity.player.packets.ChangeAppearance;
import server.world.entity.player.packets.ChangeRegions;
import server.world.entity.player.packets.Chat;
import server.world.entity.player.packets.ClanChat;
import server.world.entity.player.packets.ClickItem;
import server.world.entity.player.packets.ClickNPC;
import server.world.entity.player.packets.ClickObject;
import server.world.entity.player.packets.ClickingButtons;
import server.world.entity.player.packets.ClickingInGame;
import server.world.entity.player.packets.ClickingStuff;
import server.world.entity.player.packets.Commands;
import server.world.entity.player.packets.Dialogue;
import server.world.entity.player.packets.DropItem;
import server.world.entity.player.packets.FollowPlayer;
import server.world.entity.player.packets.IdleLogout;
import server.world.entity.player.packets.ItemClick2;
import server.world.entity.player.packets.ItemClick3;
import server.world.entity.player.packets.ItemOnGroundItem;
import server.world.entity.player.packets.ItemOnItem;
import server.world.entity.player.packets.ItemOnNpc;
import server.world.entity.player.packets.ItemOnObject;
import server.world.entity.player.packets.MagicOnFloorItems;
import server.world.entity.player.packets.MagicOnItems;
import server.world.entity.player.packets.MoveItems;
import server.world.entity.player.packets.PickupItem;
import server.world.entity.player.packets.PrivateMessaging;
import server.world.entity.player.packets.RemoveItem;
import server.world.entity.player.packets.SilentPacket;
import server.world.entity.player.packets.Trade;
import server.world.entity.player.packets.Walking;
import server.world.entity.player.packets.WearItem;

public class PacketHandler {

    private static PacketType packetId[] = new PacketType[256];

    static {

        SilentPacket u = new SilentPacket();
        packetId[3] = u;
        packetId[202] = u;
        packetId[77] = u;
        packetId[86] = u;
        packetId[78] = u;
        packetId[36] = u;
        packetId[226] = u;
        packetId[246] = u;
        packetId[148] = u;
        packetId[183] = u;
        packetId[230] = u;
        packetId[136] = u;
        packetId[189] = u;
        packetId[152] = u;
        packetId[200] = u;
        packetId[85] = u;
        packetId[165] = u;
        packetId[238] = u;
        packetId[150] = u;
        packetId[40] = new Dialogue();
        ClickObject co = new ClickObject();
        packetId[132] = co;
        packetId[252] = co;
        packetId[70] = co;
        packetId[57] = new ItemOnNpc();
        ClickNPC cn = new ClickNPC();
        packetId[72] = cn;
        packetId[131] = cn;
        packetId[155] = cn;
        packetId[17] = cn;
        packetId[21] = cn;
        packetId[16] = new ItemClick2();
        packetId[75] = new ItemClick3();
        packetId[122] = new ClickItem();
        packetId[241] = new ClickingInGame();
        packetId[4] = new Chat();
        packetId[236] = new PickupItem();
        packetId[87] = new DropItem();
        packetId[185] = new ClickingButtons();
        packetId[130] = new ClickingStuff();
        packetId[103] = new Commands();
        packetId[214] = new MoveItems();
        packetId[237] = new MagicOnItems();
        packetId[181] = new MagicOnFloorItems();
        packetId[202] = new IdleLogout();
        AttackPlayer ap = new AttackPlayer();
        packetId[73] = ap;
        packetId[249] = ap;
        packetId[128] = new ChallengePlayer();
        packetId[139] = new Trade();
        packetId[39] = new FollowPlayer();
        packetId[41] = new WearItem();
        packetId[145] = new RemoveItem();
        packetId[117] = new Bank5();
        packetId[43] = new Bank10();
        packetId[129] = new BankAll();
        packetId[101] = new ChangeAppearance();
        PrivateMessaging pm = new PrivateMessaging();
        packetId[188] = pm;
        packetId[126] = pm;
        packetId[215] = pm;
        packetId[59] = pm;
        packetId[95] = pm;
        packetId[133] = pm;
        packetId[135] = new BankX1();
        packetId[208] = new BankX2();
        Walking w = new Walking();
        packetId[98] = w;
        packetId[164] = w;
        packetId[248] = w;
        packetId[53] = new ItemOnItem();
        packetId[192] = new ItemOnObject();
        packetId[25] = new ItemOnGroundItem();
        ChangeRegions cr = new ChangeRegions();
        packetId[121] = cr;
        packetId[210] = cr;
        packetId[60] = new ClanChat();
    }

    public static void processPacket(Player c, int packetType, int packetSize) {
        if (packetType == -1) {
            return;
        }
        PacketType p = packetId[packetType];
        if (p != null) {
            try {
                // System.out.println("packet: " + packetType);
                p.processPacket(c, packetType, packetSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unhandled packet type: " + packetType + " - size: " + packetSize);
        }
    }

}
