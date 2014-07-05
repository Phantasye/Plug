package com.plug.world.entity.player.packets;

import com.plug.Constants;
import com.plug.Game;
import com.plug.core.event.CycleEvent;
import com.plug.core.event.CycleEventHandler;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Click NPC
 */
public class ClickNPC implements PacketType {
    public static final int ATTACK_NPC = 72, MAGE_NPC = 131, FIRST_CLICK = 155,
            SECOND_CLICK = 17, THIRD_CLICK = 21;

    @Override
    public void processPacket(final Player c, int packetType, int packetSize) {
        c.npcIndex = 0;
        c.npcClickIndex = 0;
        c.playerIndex = 0;
        c.clickNpcType = 0;
        c.getPA().resetFollow();
        switch (packetType) {

            /**
             * Attack npc melee or range
             */
            case ATTACK_NPC:
                if (!c.mageAllowed) {
                    c.mageAllowed = true;
                    c.sendMessage("I can't reach that.");
                    break;
                }
                c.npcIndex = c.getInStream().readUnsignedWordA();
                if (Game.npcHandler.npcs[c.npcIndex] == null) {
                    c.npcIndex = 0;
                    break;
                }
                if (Game.npcHandler.npcs[c.npcIndex].MaxHP == 0) {
                    c.npcIndex = 0;
                    break;
                }
                if (Game.npcHandler.npcs[c.npcIndex] == null) {
                    break;
                }
                if (c.autocastId > 0)
                    c.autocasting = true;
                if (!c.autocasting && c.spellId > 0) {
                    c.spellId = 0;
                }
                c.faceEntity(c.npcIndex);
                c.usingMagic = false;
                boolean usingBow = false;
                boolean usingOtherRangeWeapons = false;
                boolean usingArrows = false;
                boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185;
                if (c.playerEquipment[c.playerWeapon] >= 4214 && c.playerEquipment[c.playerWeapon] <= 4223)
                    usingBow = true;
                for (int bowId : c.BOWS) {
                    if (c.playerEquipment[c.playerWeapon] == bowId) {
                        usingBow = true;
                        for (int arrowId : c.ARROWS) {
                            if (c.playerEquipment[c.playerArrows] == arrowId) {
                                usingArrows = true;
                            }
                        }
                    }
                }
                for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
                    if (c.playerEquipment[c.playerWeapon] == otherRangeId) {
                        usingOtherRangeWeapons = true;
                    }
                }
                if ((usingBow || c.autocasting) && c.goodDistance(c.getX(), c.getY(), Game.npcHandler.npcs[c.npcIndex].getX(), Game.npcHandler.npcs[c.npcIndex].getY(), 7)) {
                    c.stopMovement();
                }

                if (usingOtherRangeWeapons && c.goodDistance(c.getX(), c.getY(), Game.npcHandler.npcs[c.npcIndex].getX(), Game.npcHandler.npcs[c.npcIndex].getY(), 4)) {
                    c.stopMovement();
                }
                if (!usingCross && !usingArrows && usingBow && c.playerEquipment[c.playerWeapon] < 4212 && c.playerEquipment[c.playerWeapon] > 4223 && !usingCross) {
                    c.sendMessage("You have run out of arrows!");
                    break;
                }
                if (c.getCombat().correctBowAndArrows() < c.playerEquipment[c.playerArrows] && Constants.CORRECT_ARROWS && usingBow && !c.getCombat().usingCrystalBow() && c.playerEquipment[c.playerWeapon] != 9185) {
                    c.sendMessage("You can't use " + c.getItems().getItemName(c.playerEquipment[c.playerArrows]).toLowerCase() + "s with a " + c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + ".");
                    c.stopMovement();
                    c.getCombat().resetPlayerAttack();
                    return;
                }
                if (c.playerEquipment[c.playerWeapon] == 9185 && !c.getCombat().properBolts()) {
                    c.sendMessage("You must use bolts with a crossbow.");
                    c.stopMovement();
                    c.getCombat().resetPlayerAttack();
                    return;
                }

                if (c.followId > 0) {
                    c.getPA().resetFollow();
                }
                if (c.attackTimer <= 0) {
                    c.getCombat().attackNpc(c.npcIndex);
                    c.attackTimer++;
                }

                break;

            /**
             * Attack npc with magic
             */
            case MAGE_NPC:
                if (!c.mageAllowed) {
                    c.mageAllowed = true;
                    c.sendMessage("I can't reach that.");
                    break;
                }
                // c.usingSpecial = false;
                // c.getItems().updateSpecialBar();

                c.npcIndex = c.getInStream().readSignedWordBigEndianA();
                int castingSpellId = c.getInStream().readSignedWordA();
                c.usingMagic = false;

                if (Game.npcHandler.npcs[c.npcIndex] == null) {
                    break;
                }

                if (Game.npcHandler.npcs[c.npcIndex].MaxHP == 0 || Game.npcHandler.npcs[c.npcIndex].npcType == 944) {
                    c.sendMessage("You can't attack this npc.");
                    break;
                }

                for (int i = 0; i < c.MAGIC_SPELLS.length; i++) {
                    if (castingSpellId == c.MAGIC_SPELLS[i][0]) {
                        c.spellId = i;
                        c.usingMagic = true;
                        break;
                    }
                }
                if (castingSpellId == 1171) { // crumble undead
                    for (int npc : Constants.UNDEAD_NPCS) {
                        if (Game.npcHandler.npcs[c.npcIndex].npcType != npc) {
                            c.sendMessage("You can only attack undead monsters with this spell.");
                            c.usingMagic = false;
                            c.stopMovement();
                            break;
                        }
                    }
                }
                /*
                 * if(!c.getCombat().checkMagicReqs(c.spellId)) {
                 * c.stopMovement(); break; }
                 */

                if (c.autocasting)
                    c.autocasting = false;

                if (c.usingMagic) {
                    if (c.goodDistance(c.getX(), c.getY(), Game.npcHandler.npcs[c.npcIndex].getX(), Game.npcHandler.npcs[c.npcIndex].getY(), 6)) {
                        c.stopMovement();
                    }
                    if (c.attackTimer <= 0) {
                        c.getCombat().attackNpc(c.npcIndex);
                        c.attackTimer++;
                    }
                }

                break;

            case FIRST_CLICK:
                c.npcClickIndex = c.inStream.readSignedWordBigEndian();
                c.npcType = Game.npcHandler.npcs[c.npcClickIndex].npcType;

                CycleEventHandler.getSingleton().stopEvents(Player.WALKING_TO_ACTIONS_KEY);
                CycleEventHandler.getSingleton().addEvent(new CycleEvent(Player.WALKING_TO_ACTIONS_KEY, 1) {
                    @Override
                    public void execute() {
                        if (c.disconnected) {
                            this.stop();
                        }
                        if (c.goodDistance(Game.npcHandler.npcs[c.npcClickIndex].getX(), Game.npcHandler.npcs[c.npcClickIndex].getY(), c.getX(), c.getY(), 1)) {
                            c.facePosition(Game.npcHandler.npcs[c.npcClickIndex].getX(), Game.npcHandler.npcs[c.npcClickIndex].getY());
                            Game.npcHandler.npcs[c.npcClickIndex].facePlayer(c.slot);
                            c.getActions().firstClickNpc(c.npcType);
                            this.stop();
                        }
                    }
                });
                break;

            case SECOND_CLICK:
                c.npcClickIndex = c.inStream.readUnsignedWordBigEndianA();
                c.npcType = Game.npcHandler.npcs[c.npcClickIndex].npcType;
                CycleEventHandler.getSingleton().stopEvents(Player.WALKING_TO_ACTIONS_KEY);
                CycleEventHandler.getSingleton().addEvent(new CycleEvent(Player.WALKING_TO_ACTIONS_KEY, 1) {
                    @Override
                    public void execute() {
                        if (c.disconnected) {
                            this.stop();
                        }
                        if (c.goodDistance(Game.npcHandler.npcs[c.npcClickIndex].getX(), Game.npcHandler.npcs[c.npcClickIndex].getY(), c.getX(), c.getY(), 1)) {
                            c.facePosition(Game.npcHandler.npcs[c.npcClickIndex].getX(), Game.npcHandler.npcs[c.npcClickIndex].getY());
                            Game.npcHandler.npcs[c.npcClickIndex].facePlayer(c.slot);
                            c.getActions().secondClickNpc(c.npcType);
                            this.stop();
                        }
                    }
                });
                break;

            case THIRD_CLICK:
                c.npcClickIndex = c.inStream.readSignedWord();
                c.npcType = Game.npcHandler.npcs[c.npcClickIndex].npcType;

                CycleEventHandler.getSingleton().stopEvents(Player.WALKING_TO_ACTIONS_KEY);
                CycleEventHandler.getSingleton().addEvent(new CycleEvent(Player.WALKING_TO_ACTIONS_KEY, 1) {
                    @Override
                    public void execute() {
                        if (c.disconnected) {
                            this.stop();
                        }
                        if (c.goodDistance(Game.npcHandler.npcs[c.npcClickIndex].getX(), Game.npcHandler.npcs[c.npcClickIndex].getY(), c.getX(), c.getY(), 1)) {
                            c.facePosition(Game.npcHandler.npcs[c.npcClickIndex].getX(), Game.npcHandler.npcs[c.npcClickIndex].getY());
                            Game.npcHandler.npcs[c.npcClickIndex].facePlayer(c.slot);
                            c.getActions().thirdClickNpc(c.npcType);
                            this.stop();
                        }
                    }
                });
                break;
        }

    }
}
