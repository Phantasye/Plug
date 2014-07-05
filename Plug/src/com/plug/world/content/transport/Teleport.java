package com.plug.world.content.transport;

import com.plug.Constants;
import com.plug.util.Misc;
import com.plug.util.exceptions.InsufficientRightsException;
import com.plug.util.exceptions.NoRunesException;
import com.plug.util.exceptions.NonMemberException;
import com.plug.world.entity.player.Player;


public abstract class Teleport implements TeleportManager {
	
	/**
	 * returns the teleportation start animation
	 * @return
	 */
	public abstract int getStartAnimation();
	
	/**
	 * returns the ending animation
	 * @return
	 */
	public abstract int getEndAnimation();
	
	/**
	 * returns the GFX used for the teleport
	 * @return
	 */
	public abstract int getGfx();
	
	/**
	 * returns the teleport delay (normally around 1-2)
	 * @return
	 */
	public abstract int getDelay();
	
	/**
	 * returns the teleport x location
	 * @return
	 */
	public abstract int getX();
	
	/**
	 * returns the teleports y location
	 * @return
	 */
	public abstract int getY();
	
	/**
	 * returns the level required to teleport
	 * @return
	 */
	public abstract int getLevel();
	
	/**
	 * returns the xp earned for teleporting
	 * @return
	 */
	public abstract int getXP();
	
	/**
	 * the mask used for the performGfx method
	 * currently supports 0 and 100
	 * @return
	 */
	public abstract int getMask();

	@Override
	public boolean isMembers() {
		return false;
	}

	@Override
	public int getRights() {
		return 0;
	}
	
	@Override
	public boolean canTeleport(Player c) throws InsufficientRightsException, NoRunesException, NonMemberException {
		if(c.playerLevel[6] < getLevel()) {
			c.sendMessage("You need a Magic level of at least " + getLevel() + " to do that.");
			return false;
		}
		if(c.rights.getRightsAsInteger() < getRights()) {
			throw new InsufficientRightsException();
		}
		if(isMembers() && !c.isMember) {
			throw new NonMemberException();
		}
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return false;
		}
		if (c.inFightCaves()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return false;
		}
		if (c.inWild() && c.wildLevel > Constants.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "
					+ Constants.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return false;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return false;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0) {
				c.getCombat().resetPlayerAttack();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * processes the teleport event
	 * resets the necessary values
	 * and appends XP if necessary
	 * Teleports the player in a 9 block
	 * radius of the X and Y
	 * @param player
	 * 		the player teleporting
	 */
	protected void processTeleport(Player player) {
		if(getLevel() > 0)
			player.getPA().addSkillXP(getXP(),
					6);
		player.teleHeight = 0;
		player.npcIndex = 0;
		player.playerIndex = 0;
//		player.appendFaceUpdate(0);
		player.teleportToX = getX() + Misc.random(3);
		player.teleportToY = getY() + Misc.random(3);
		player.startAnimation(getEndAnimation());
	}

}
