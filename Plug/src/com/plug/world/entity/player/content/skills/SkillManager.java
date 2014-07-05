package com.plug.world.entity.player.content.skills;

import com.plug.world.entity.player.Player;

public interface SkillManager {
	
	public boolean canExecute(Player player);
	
	public void execute(Player player);

}
