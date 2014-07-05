package com.plug.world.entity.player.content.skills;

import java.util.HashMap;
import java.util.Map;

import com.plug.util.PermissionsManager;
import com.plug.world.entity.player.PlayerRights;


public abstract class Skill implements SkillManager, PermissionsManager {

	public abstract SkillType getType();
	public abstract String getName();
	public abstract int getXPRate();
	
	public static Map<SkillType, Skill> skillMap = new HashMap<>();
	
	@Override
	public boolean isMembers() {
		return false;
	}
	
	@Override
	public int getRights() {
		return PlayerRights.PLAYER.getRightsAsInteger();
	}
	
	public static void loadSkill(Skill skill) {
		skillMap.put(skill.getType(), skill);
	}
}
