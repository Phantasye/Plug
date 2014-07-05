package com.plug.world.entity.player.packets;

import com.plug.Constants;
import com.plug.Game;
import com.plug.core.net.protocol.packet.PacketType;
import com.plug.world.entity.player.Player;

/**
 * Attack Player
 */
public class AttackPlayer implements PacketType {

    public static final int ATTACK_PLAYER = 73, MAGE_PLAYER = 249;

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        c.playerIndex = 0;
        c.npcIndex = 0;
        switch (packetType) {

            /**
             * Attack player
             */
            case ATTACK_PLAYER:
                c.playerIndex = c.getInStream().readSignedWordBigEndian();
                if (Game.playerHandler.players[c.playerIndex] == null) {
                    break;
                }

                if (c.respawnTimer > 0) {
                    break;
                }

                if (c.autocastId > 0)
                    c.autocasting = true;

                if (!c.autocasting && c.spellId > 0) {
                    c.spellId = 0;
                }
                c.mageFollow = false;
                c.spellId = 0;
                c.usingMagic = false;
                boolean usingBow = false;
                boolean usingOtherRangeWeapons = false;
                boolean usingArrows = false;
                boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185;
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
                if (c.duelStatus == 5) {
                    if (c.duelCount > 0) {
                        c.sendMessage("The duel hasn't started yet!");
                        c.playerIndex = 0;
                        return;
                    }
                    if (c.duelRule[9]) {
                        boolean canUseWeapon = false;
                        for (int funWeapon : Constants.FUN_WEAPONS) {
                            if (c.playerEquipment[c.playerWeapon] == funWeapon) {
                                canUseWeapon = true;
                            }
                        }
                        if (!canUseWeapon) {
                            c.sendMessage("You can only use fun weapons in this duel!");
                            return;
                        }
                    }

                    if (c.duelRule[2] && (usingBow || usingOtherRangeWeapons)) {
                        c.sendMessage("Range has been disabled in this duel!");
                        return;
                    }
                    if (c.duelRule[3] && (!usingBow && !usingOtherRangeWeapons)) {
                        c.sendMessage("Melee has been disabled in this duel!");
                        return;
                    }
                }

                if ((usingBow || c.autocasting) && c.goodDistance(c.getX(), c.getY(), Game.playerHandler.players[c.playerIndex].getX(), Game.playerHandler.players[c.playerIndex].getY(), 6)) {
                    c.usingBow = true;
                    c.stopMovement();
                }

                if (usingOtherRangeWeapons && c.goodDistance(c.getX(), c.getY(), Game.playerHandler.players[c.playerIndex].getX(), Game.playerHandler.players[c.playerIndex].getY(), 3)) {
                    c.usingRangeWeapon = true;
                    c.stopMovement();
                }
                if (!usingBow)
                    c.usingBow = false;
                if (!usingOtherRangeWeapons)
                    c.usingRangeWeapon = false;

                if (!usingCross && !usingArrows && usingBow && c.playerEquipment[c.playerWeapon] < 4212 && c.playerEquipment[c.playerWeapon] > 4223) {
                    c.sendMessage("You have run out of arrows!");
                    return;
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
                if (c.getCombat().checkReqs()) {
                    c.followId = c.playerIndex;
                    if (!c.usingMagic && !usingBow && !usingOtherRangeWeapons) {
                        c.followDistance = 1;
                        c.getPA().followPlayer();
                    }
                    if (c.attackTimer <= 0) {
                        // c.sendMessage("Tried to attack...");
                        // c.getCombat().attackPlayer(c.playerIndex);
                        // c.attackTimer++;
                    }
                }
                break;

            /**
             * Attack player with magic
             */
            case MAGE_PLAYER:
                if (!c.mageAllowed) {
                    c.mageAllowed = true;
                    break;
                }
                // c.usingSpecial = false;
                // c.getItems().updateSpecialBar();

                c.playerIndex = c.getInStream().readSignedWordA();
                int castingSpellId = c.getInStream().readSignedWordBigEndian();
                c.usingMagic = false;
                if (Game.playerHandler.players[c.playerIndex] == null) {
                    break;
                }

                if (c.respawnTimer > 0) {
                    break;
                }

                for (int i = 0; i < c.MAGIC_SPELLS.length; i++) {
                    if (castingSpellId == c.MAGIC_SPELLS[i][0]) {
                        c.spellId = i;
                        c.usingMagic = true;
                        break;
                    }
                }

                if (c.autocasting)
                    c.autocasting = false;

                if (!c.getCombat().checkReqs()) {
                    break;
                }
                if (c.duelStatus == 5) {
                    if (c.duelCount > 0) {
                        c.sendMessage("The duel hasn't started yet!");
                        c.playerIndex = 0;
                        return;
                    }
                    if (c.duelRule[4]) {
                        c.sendMessage("Magic has been disabled in this duel!");
                        return;
                    }
                }

                for (int r = 0; r < c.REDUCE_SPELLS.length; r++) { // reducing
                    // spells,
                    // confuse etc
                    if (Game.playerHandler.players[c.playerIndex].REDUCE_SPELLS[r] == c.MAGIC_SPELLS[c.spellId][0]) {
                        if ((System.currentTimeMillis() - Game.playerHandler.players[c.playerIndex].reduceSpellDelay[r]) < Game.playerHandler.players[c.playerIndex].REDUCE_SPELL_TIME[r]) {
                            c.sendMessage("That player is currently immune to this spell.");
                            c.usingMagic = false;
                            c.stopMovement();
                            c.getCombat().resetPlayerAttack();
                        }
                        break;
                    }
                }

                if (System.currentTimeMillis() - Game.playerHandler.players[c.playerIndex].teleBlockDelay < Game.playerHandler.players[c.playerIndex].teleBlockLength && c.MAGIC_SPELLS[c.spellId][0] == 12445) {
                    c.sendMessage("That player is already affected by this spell.");
                    c.usingMagic = false;
                    c.stopMovement();
                    c.getCombat().resetPlayerAttack();
                }

                /*
                 * if(!c.getCombat().checkMagicReqs(c.spellId)) {
                 * c.stopMovement(); c.getCombat().resetPlayerAttack(); break; }
                 */

                if (c.usingMagic) {
                    if (c.goodDistance(c.getX(), c.getY(), Game.playerHandler.players[c.playerIndex].getX(), Game.playerHandler.players[c.playerIndex].getY(), 7)) {
                        c.stopMovement();
                    }
                    if (c.getCombat().checkReqs()) {
                        c.followId = c.playerIndex;
                        c.mageFollow = true;
                        if (c.attackTimer <= 0) {
                            // c.getCombat().attackPlayer(c.playerIndex);
                            // c.attackTimer++;
                        }
                    }
                }
                break;

        }

    }

}
