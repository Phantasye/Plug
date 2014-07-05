package server.world.entity.player.content.commands.impl;

import server.util.exceptions.IndenticalObjectException;
import server.util.exceptions.InsufficientRightsException;
import server.util.exceptions.InvalidCommandException;
import server.util.exceptions.NonMemberException;
import server.util.exceptions.PlayerNotFoundException;
import server.world.entity.player.Player;
import server.world.entity.player.PlayerHandler;
import server.world.entity.player.PlayerRights;
import server.world.entity.player.content.commands.Command;

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
