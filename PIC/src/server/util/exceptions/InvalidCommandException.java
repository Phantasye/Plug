package server.util.exceptions;

import java.util.ArrayList;

import server.world.entity.player.Player;
import server.world.entity.player.content.commands.Command;
import server.util.Colors;

public class InvalidCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4463448540772986512L;

	private String failedCommand;

	public InvalidCommandException() {
		super();
	}

	public InvalidCommandException(String failedCommand) {
		super();
		this.failedCommand = failedCommand;
	}

	@Override
	public String getMessage() {
		return "Invalid command input.";
	}

	public void checkCommand(Player c) {
		ArrayList<String> list = new ArrayList<>();
		for (String key : Command.commands.keySet()) {
			if (c.rights.getRightsAsInteger() >= Command.commands.get(key)
					.getRights()) {
				if (key.contains(failedCommand)) {
					list.add(key);

				}
			}
		}
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				c.sendMessage("Did you mean ::" + list.get(i), Colors.RED);
			}
		} else {
			c.sendMessage(getMessage(), Colors.RED);
		}
	}

	@Override
	public Throwable getCause() {
		return this;
	}

}
