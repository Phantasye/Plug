package com.plug.world.entity;

import com.plug.world.entity.npc.Npc;
import com.plug.world.entity.player.Player;

/**
 * A parent class that can represent either a {@link Player} or an {@link Npc}.
 * Fields and methods that are relevant to both of those types should be placed
 * here.
 * 
 * @author lare96
 */
public abstract class Entity {

    // XXX: Didn't bother using getters and setters, this also isn't completed
    // but is useful enough where you shouldn't have to write code twice to do
    // something like entity based combat. Also lets people who somewhat know
    // what they are doing add more stuff here. Enjoy!

    // XXX: The whole 'hitdiff' shit was so bad I just had to redo them. Thank
    // me later lol

    /** The slot of this entity. */
    public int slot = -1;

    /** The position of this entity. */
    public int absX, absY, heightLevel;

    /** Stuff for npcs, no effect on players. */
    public int moveX, moveY, direction;

    /** Stuff for players, no effect on npcs. */
    public boolean chatTextUpdateRequired;

    /** Update flags. */
    // might need to set update req to true
    public boolean updateRequired, appearanceUpdateRequired,
            animationUpdateRequired, graphicsUpdateRequired,
            forcedChatUpdateRequired, faceEntityUpdateRequired,
            facePositionUpdateRequired, hitUpdateRequired,
            secondaryHitUpdateRequired;

    /** Fields for update flags. */
    public int animation = -1, animationWait = -1, gfx = -1, gfxVar2 = -1,
            entity = -1, faceX = -1, faceY = -1;
    public String forcedChat;
    public Hit hit;
    public Hit secondaryHit;

    /**
     * Resets update flags and the corresponding values for those update flags.
     */
    public void reset() {
        updateRequired = false;
        appearanceUpdateRequired = false;
        animationUpdateRequired = false;
        graphicsUpdateRequired = false;
        forcedChatUpdateRequired = false;
        faceEntityUpdateRequired = false;
        facePositionUpdateRequired = false;
        hitUpdateRequired = false;
        secondaryHitUpdateRequired = false;
        gfx = -1;
        gfxVar2 = -1;
        animation = -1;
        animationWait = -1;
        forcedChat = null;
        moveX = 0;
        moveY = 0;
        entity = 65535;
        faceX = -1;
        faceY = -1;
        hit = null;
        secondaryHit = null;
    }

    /**
     * Start an animation for an entity.
     * 
     * @param id
     *        the id of the animation to start.
     */
    public void startAnimation(int id) {
        animation = id;
        animationWait = 0;
        updateRequired = true;
        animationUpdateRequired = true;
    }

    /**
     * Forces the entity to speak.
     * 
     * @param text
     *        the text that the entity is forced to say.
     */
    public void forceChat(String text) {
        forcedChat = text;
        updateRequired = true;
        forcedChatUpdateRequired = true;
    }

    /**
     * Creates a graphic for the entity.
     * 
     * @param gfx
     *        the graphic to create.
     */
    public void gfx0(int id) {
        gfx = id;
        gfxVar2 = 65536;
        updateRequired = true;
        graphicsUpdateRequired = true;
    }

    /**
     * Creates a graphic for the entity.
     * 
     * @param gfx
     *        the graphic to create.
     */
    public void gfx100(int id) {
        gfx = id;
        gfxVar2 = 6553600;
        updateRequired = true;
        graphicsUpdateRequired = true;
    }
    
    public void performGfx(int mask, int gfx) {
		switch (mask) {
		case 0:
			gfxVar2 = 65536;
			break;
		case 100:
			gfxVar2 = 6553600;
			break;
		}
		this.gfx = gfx;
        gfxVar2 = 6553600;
		updateRequired = true;
		graphicsUpdateRequired = true;
	}

    /**
     * Face another entity.
     * 
     * @param slot
     *        the slot of the other entity to face.
     */
    public void faceEntity(int slot) {
        entity = slot;
        updateRequired = true;
        faceEntityUpdateRequired = true;
    }

    /**
     * Faces the specified coordinates.
     * 
     * @param x
     *        the x coordinate to face.
     * @param y
     *        the y coordinate to face.
     */
    public void facePosition(int x, int y) {
        faceX = 2 * x + 1;
        faceY = 2 * y + 1;
        updateRequired = true;
        facePositionUpdateRequired = true;
    }

    /**
     * Deals primary damage to this entity.
     * 
     * @param hit
     *        the damage to deal.
     */
    public void dealHit(Hit hit) {
        this.hit = hit.clone();
        updateRequired = true;
        hitUpdateRequired = true;

        if (this instanceof Npc) {
            ((Npc) this).HP -= hit.getDamage();
        } else if (this instanceof Player) {
            Player player = (Player) this;

            if (player.teleTimer <= 0) {
                player.playerLevel[3] -= hit.getDamage();
                player.getPA().refreshSkill(3);
            }
        }
    }

    /**
     * Deals secondary damage to this entity.
     * 
     * @param hit
     *        the damage to deal.
     */
    public void dealSecondaryHit(Hit hit) {
        secondaryHit = hit.clone();
        updateRequired = true;
        secondaryHitUpdateRequired = true;

        if (this instanceof Npc) {
            ((Npc) this).HP -= hit.getDamage();
        } else if (this instanceof Player) {
            Player player = (Player) this;

            if (player.teleTimer <= 0) {
                player.playerLevel[3] -= hit.getDamage();
                player.getPA().refreshSkill(3);
            }
        }
    }
}
