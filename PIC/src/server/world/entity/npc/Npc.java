package server.world.entity.npc;

import server.Server;
import server.core.net.Stream;
import server.util.Misc;
import server.world.entity.Entity;

public class Npc extends Entity {
    public int npcType;
    public int makeX, makeY, maxHit, defence, attack, walkingType;
    public int spawnX, spawnY;

    /**
     * attackType: 0 = melee, 1 = range, 2 = mage
     */
    public int attackType, projectileId, endGfx, spawnedBy, hitDelayTimer, HP,
            MaxHP, actionTimer, enemyX, enemyY;
    public boolean applyDead, isDead, needRespawn, respawns;
    public boolean walkingHome, underAttack;
    public int freezeTimer, attackTimer, killerId, killedBy, oldIndex,
            underAttackBy;
    public long lastDamageTaken;
    public boolean randomWalk;
    public int firstAttacker;

    public Npc(int _npcId, int _npcType) {
        slot = _npcId;
        npcType = _npcType;
        direction = -1;
        isDead = false;
        applyDead = false;
        actionTimer = 0;
        randomWalk = true;
    }

    public void updateNPCMovement(Stream str) {
        if (direction == -1) {

            if (updateRequired) {

                str.writeBits(1, 1);
                str.writeBits(2, 0);
            } else {
                str.writeBits(1, 0);
            }
        } else {

            str.writeBits(1, 1);
            str.writeBits(2, 1);
            str.writeBits(3, Misc.xlateDirectionToClient[direction]);
            if (updateRequired) {
                str.writeBits(1, 1);
            } else {
                str.writeBits(1, 0);
            }
        }
    }

    /**
     * Graphics
     */

    public void appendMask80Update(Stream str) {
        str.writeWord(gfx);
        str.writeDWord(gfxVar2);
    }

    public void appendAnimUpdate(Stream str) {
        str.writeWordBigEndian(animation);
        str.writeByte(1);
    }

    /**
     * 
     * Face
     * 
     */

    private void appendSetFocusDestination(Stream str) {
        str.writeWordBigEndian(faceX);
        str.writeWordBigEndian(faceY);
    }

    public void appendFaceEntity(Stream str) {
        str.writeWord(entity);
    }

    public void facePlayer(int player) {
        faceEntity(player + 32768);
    }

    public void appendNPCUpdateBlock(Stream str) {
        if (!updateRequired)
            return;
        int updateMask = 0;
        if (animationUpdateRequired)
            updateMask |= 0x10;
        if (secondaryHitUpdateRequired)
            updateMask |= 8;
        if (graphicsUpdateRequired)
            updateMask |= 0x80;
        if (faceEntityUpdateRequired)
            updateMask |= 0x20;
        if (forcedChatUpdateRequired)
            updateMask |= 1;
        if (hitUpdateRequired)
            updateMask |= 0x40;
        if (facePositionUpdateRequired)
            updateMask |= 4;

        str.writeByte(updateMask);

        if (animationUpdateRequired)
            appendAnimUpdate(str);
        if (secondaryHitUpdateRequired)
            appendHitUpdate2(str);
        if (graphicsUpdateRequired)
            appendMask80Update(str);
        if (faceEntityUpdateRequired)
            appendFaceEntity(str);
        if (forcedChatUpdateRequired) {
            str.writeString(forcedChat);
        }
        if (hitUpdateRequired)
            appendHitUpdate(str);
        if (facePositionUpdateRequired)
            appendSetFocusDestination(str);

    }

    public int getNextWalkingDirection() {
        int dir;
        dir = Misc.direction(absX, absY, (absX + moveX), (absY + moveY));
        if (dir == -1)
            return -1;
        dir >>= 1;
        absX += moveX;
        absY += moveY;
        return dir;
    }

    public void getNextNPCMovement(int i) {
        direction = -1;
        if (Server.npcHandler.npcs[i].freezeTimer == 0) {
            direction = getNextWalkingDirection();
        }
    }

    public void appendHitUpdate(Stream str) {
        if (HP <= 0) {
            isDead = true;
        }
        str.writeByteC(hit.getDamage());
        str.writeByteS(hit.getType().getId());
        str.writeByteS(HP);
        str.writeByteC(MaxHP);
    }

    public void appendHitUpdate2(Stream str) {
        if (HP <= 0) {
            isDead = true;
        }
        str.writeByteA(secondaryHit.getDamage());
        str.writeByteC(secondaryHit.getType().getId());
        str.writeByteA(HP);
        str.writeByte(MaxHP);
    }

    public int getX() {
        return absX;
    }

    public int getY() {
        return absY;
    }

    public boolean inMulti() {
        if ((absX >= 3136 && absX <= 3327 && absY >= 3519 && absY <= 3607) || (absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839) || (absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967) || (absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967) || (absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831) || (absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903) || (absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711) || (absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647) || (absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619) || (absX >= 2371 && absX <= 2422 && absY >= 5062 && absY <= 5117) || (absX >= 2896 && absX <= 2927 && absY >= 3595 && absY <= 3630) || (absX >= 2892 && absX <= 2932 && absY >= 4435 && absY <= 4464) || (absX >= 2256 && absX <= 2287 && absY >= 4680 && absY <= 4711)) {
            return true;
        }
        return false;
    }

    public boolean inWild() {
        if (absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966 || absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366) {
            return true;
        }
        return false;
    }
}
