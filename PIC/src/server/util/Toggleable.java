package server.util;

import server.world.entity.player.Player;

public interface Toggleable extends PermissionsManager {
	
	public void on(Player player);
	
	public void off(Player player);

}
