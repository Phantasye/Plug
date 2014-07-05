package server.world.content.transport;

import java.util.HashMap;
import java.util.Map;

import server.util.PermissionsManager;
import server.util.exceptions.InsufficientRightsException;
import server.util.exceptions.NoRunesException;
import server.util.exceptions.NonMemberException;
import server.world.entity.player.Player;

/**
 * manages all teleportation
 * @author Quinton
 *
 */
public interface TeleportManager extends PermissionsManager {
	
	/**
	 * executes the teleportation task
	 * @param player
	 * 		the player teleporting
	 * @throws NoRunesException
	 * 		thrown if the player does not have the right runes
	 * @throws InsufficientRightsException
	 * 		thrown if the player does not have the rights
	 * @throws NonMemberException
	 * 		thrown if the player is f2p attempting to use a member teleport
	 */
	public void execute(Player player) throws NoRunesException, InsufficientRightsException, NonMemberException;
	
	/**
	 * checks if the player can teleport
	 * @param c
	 * 		the player
	 * @return
	 * 		returns true if all requirements are met
	 * @throws InsufficientRightsException
	 * 		thrown if the player does not have the rights
	 * @throws NoRunesException
	 * 		thrown if the player does not have the runes
	 * @throws NonMemberException 
	 */
	public boolean canTeleport(Player c) throws InsufficientRightsException, NoRunesException, NonMemberException;
	
	public static Map<String, Teleport> teleportMap = new HashMap<>();

}
