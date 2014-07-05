package com.plug.world.entity.player.content.commands;


import com.plug.util.PermissionsManager;
import com.plug.util.exceptions.IndenticalObjectException;
import com.plug.util.exceptions.InsufficientRightsException;
import com.plug.util.exceptions.InvalidCommandException;
import com.plug.util.exceptions.NonMemberException;
import com.plug.util.exceptions.NotInClanException;
import com.plug.util.exceptions.PlayerNotFoundException;
import com.plug.world.entity.player.Player;

/**
 * Handles all commands
 * 
 * @author Quinton
 * 
 */
public interface CommandManager extends PermissionsManager {

	/**
	 * executes the player command
	 * 
	 * @param player
	 *            the player executing command
	 * @param command
	 *            the command being executed
	 * @throws NonMemberException
	 *             thrown if a non member player is attempting to execute a
	 *             member command
	 * @throws InsufficientRightsException
	 *             thrown if the player does not have privelage to do command
	 * @throws InvalidCommandException
	 *             thrown if no such command exists
	 * @throws ArrayIndexOutOfBoundsException
	 *             thrown if the player does not provide the correct number of
	 *             arguments for the command
	 * @throws PlayerNotFoundException
	 *             thrown if the player is attempting to use a command on an
	 *             offline or non-existent player
	 * @throws IndenticalObjectException
	 *             thrown if the objects being effected are identical
	 * @throws NotInClanException 
	 */
	public void execute(Player player, String command)
			throws NonMemberException, InsufficientRightsException,
			InvalidCommandException, ArrayIndexOutOfBoundsException,
			PlayerNotFoundException, IndenticalObjectException, NotInClanException;

	/**
	 * returns the command arguments
	 * 
	 * @param command
	 *            the command
	 * @return
	 */
	public String[] getArgs(String command);

	/**
	 * returns the command name
	 * 
	 * @return
	 */
	public String getCommand();

	/**
	 * returns a formatted player name
	 * 
	 * @param name
	 *            the unformatted player's name
	 * @return
	 */
	public String getFormattedName(String name);

	/**
	 * returns the commands usage
	 * 
	 * @return
	 */
	public String getUsage();

	public boolean canExecute(Player player, String command)
			throws NonMemberException, InsufficientRightsException,
			NullPointerException, InvalidCommandException;

}

