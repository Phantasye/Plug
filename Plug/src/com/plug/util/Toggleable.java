package com.plug.util;

import com.plug.world.entity.player.Player;

public interface Toggleable extends PermissionsManager {
	
	public void on(Player player);
	
	public void off(Player player);

}
