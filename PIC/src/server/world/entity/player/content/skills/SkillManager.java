package server.world.entity.player.content.skills;

import server.world.entity.player.Player;

public interface SkillManager {
	
	public boolean canExecute(Player player);
	
	public void execute(Player player);

}
