package com.plug.world.entity.player.content.minigames;

import com.plug.util.PermissionsManager;

public interface MinigameManager extends PermissionsManager {
	
	public void load(Minigame game);
	
	public void process();
	
	public void close();

}
