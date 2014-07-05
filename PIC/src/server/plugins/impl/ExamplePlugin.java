package server.plugins.impl;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import server.plugins.PluginHandler;
import server.util.Log;
import server.util.exceptions.IndenticalObjectException;
import server.util.exceptions.InsufficientRightsException;
import server.util.exceptions.InvalidCommandException;
import server.util.exceptions.NonMemberException;
import server.util.exceptions.NotInClanException;
import server.util.exceptions.PlayerNotFoundException;
import server.world.entity.player.Player;
import server.world.entity.player.content.commands.Command;

@PluginImplementation
public class ExamplePlugin extends Command implements PluginHandler {

	@Override
	public void execute(Player player, String command)
			throws NonMemberException, InsufficientRightsException,
			InvalidCommandException, ArrayIndexOutOfBoundsException,
			PlayerNotFoundException, IndenticalObjectException,
			NotInClanException {
		player.sendMessage("Sent from a plugin!");
	}

	@Override
	public String getCommand() {
		return "example";
	}

	@Override
	public String getUsage() {
		return "Use as ::example";
	}

	@Override
	public void initialize() {
		Log.printInfo("Example plugin loaded");
		loadCommand(this);
	}

}
