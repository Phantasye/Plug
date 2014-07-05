package com.plug.world.entity.player.content.commands.impl;

import com.plug.util.Colors;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.content.commands.Command;

public class Commands extends Command {

	@Override
	public void execute(Player player, String command) {
		for (String key : commands.keySet()) {
			if (player.rights.getRightsAsInteger() >= commands.get(key)
					.getRights()) {
				if(commands.get(key).isMembers()) {
				player.sendMessage("Your rank can do @blu@::" + commands.get(key).getCommand() + " (@red@MEMBERS@blu@)",
						Colors.BLACK);
				} else {
					player.sendMessage("Your rank can do @blu@::" + commands.get(key).getCommand(),
							Colors.BLACK);
				}
			}
		}
	}

	@Override
	public String getCommand() {
		return "commands";
	}

	@Override
	public String getUsage() {
		return "Use as ::" + getCommand() + "";
	}

}
