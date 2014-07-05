package com.plug.plugins.impl;

import com.plug.plugins.PluginHandler;
import com.plug.util.Log;
import com.plug.util.exceptions.IndenticalObjectException;
import com.plug.util.exceptions.InsufficientRightsException;
import com.plug.util.exceptions.InvalidCommandException;
import com.plug.util.exceptions.NonMemberException;
import com.plug.util.exceptions.NotInClanException;
import com.plug.util.exceptions.PlayerNotFoundException;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.content.commands.Command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

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
