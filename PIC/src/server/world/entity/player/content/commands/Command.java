package server.world.entity.player.content.commands;

import java.util.HashMap;
import java.util.Map;

import server.util.exceptions.InsufficientRightsException;
import server.util.exceptions.InvalidCommandException;
import server.util.exceptions.NonMemberException;
import server.world.entity.player.Player;
import server.world.entity.player.PlayerRights;



public abstract class Command implements CommandManager {

	/**
	 * stores the command data
	 */
	public static Map<String, Command> commands = new HashMap<>();
	
	@Override
	public boolean isMembers() {
		return false;
	}

	@Override
	public int getRights() {
		return PlayerRights.PLAYER.getRightsAsInteger();
	}

	@Override
	public boolean canExecute(Player player, String command)
			throws NonMemberException, InsufficientRightsException,
			InvalidCommandException {
		if (commands.get(command).isMembers() && !player.isMember) {
			throw new NonMemberException();
		} else if (commands.get(command).getRights() > player.rights.getRightsAsInteger()) {
			throw new InsufficientRightsException();
		}
		if (!command.startsWith("/")) {
//			player.getPA().writeCommandLog(command);
		}
		return true;
	}
	
	@Override
	public String[] getArgs(String command) {
		return command.split(" ");
	}
	
	@Override
	public String getFormattedName(String name) {
		return name.replaceAll("_", " ");
	}

	public static void loadCommand(Command command) {
		commands.put(command.getCommand(), command);
	}

}
