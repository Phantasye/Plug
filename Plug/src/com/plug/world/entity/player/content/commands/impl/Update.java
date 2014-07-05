package com.plug.world.entity.player.content.commands.impl;

import com.plug.util.exceptions.IndenticalObjectException;
import com.plug.util.exceptions.InsufficientRightsException;
import com.plug.util.exceptions.InvalidCommandException;
import com.plug.util.exceptions.NonMemberException;
import com.plug.util.exceptions.PlayerNotFoundException;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.PlayerHandler;
import com.plug.world.entity.player.PlayerRights;
import com.plug.world.entity.player.content.commands.Command;

public class Update extends Command {

	@Override
	public int getRights() {
		return PlayerRights.OWNER.getRightsAsInteger();
	}

	@Override
	public void execute(Player player, String command)
			throws NonMemberException, InsufficientRightsException,
			InvalidCommandException, ArrayIndexOutOfBoundsException,
			PlayerNotFoundException, IndenticalObjectException {
		int a = Integer.parseInt(getArgs(command)[1]);
		PlayerHandler.updateSeconds = a;
		PlayerHandler.updateAnnounced = false;
		PlayerHandler.updateRunning = true;
		PlayerHandler.updateStartTime = System.currentTimeMillis();
	}

	@Override
	public String getCommand() {
		return "update";
	}

	@Override
	public String getUsage() {
		return "Use as ::" + getCommand() + " timeInSeconds";
	}

}
