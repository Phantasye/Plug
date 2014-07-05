package server.world.entity.player.content.minigames;

import server.util.PermissionsManager;

public interface MinigameManager extends PermissionsManager {
	
	public void load(Minigame game);
	
	public void process();
	
	public void close();

}
