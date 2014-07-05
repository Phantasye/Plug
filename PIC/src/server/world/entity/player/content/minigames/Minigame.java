package server.world.entity.player.content.minigames;

import server.world.entity.player.PlayerRights;

public abstract class Minigame implements MinigameManager {
	
	public abstract int[] getPlayerLimits();
	public abstract String getName();
	

	@Override
	public void load(Minigame game) {
	}

	@Override
	public void process() {
	}

	@Override
	public void close() {
	}
	
	@Override
	public boolean isMembers() {
		return false;
	}
	
	@Override
	public int getRights() {
		return PlayerRights.PLAYER.getRightsAsInteger();
	}

}
