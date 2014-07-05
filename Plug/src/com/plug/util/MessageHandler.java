package com.plug.util;

import com.plug.Constants;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.PlayerHandler;

/**
 * Utility class for handling
 * different types of messages.
 * ie. server messages, and messaging others
 * @author Quinton
 *
 */
public class MessageHandler {

	/**
	 * sends a message to all players
	 * @param color
	 * 		color of message
	 * @param tag
	 * 		the message tag [EXAMPLE]
	 * @param message
	 * 		the message
	 */
	public static void sendCustomGlobalMessage(Colors color, String tag,
			String message) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Player allPlayers = (Player) PlayerHandler.players[j];
				allPlayers.sendMessage(color + " " + tag + " " + message);
			}
		}
	}

	/**
	 * sends a message to all players with the appropriate
	 * player rights
	 * @param color
	 * 		the message color
	 * @param tag
	 * 		the message tag [EXAMPLE]
	 * @param message
	 * 		the message
	 * @param rights
	 * 		the rights required to receive message
	 */
	public static void sendCustomGlobalMessage(Colors color, String tag,
			String message, int rights) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].rights.getRightsAsInteger() >= rights) {
					Player allPlayers = (Player) PlayerHandler.players[j];
					allPlayers.sendMessage(color + " " + tag + " " + message);
				}
			}
		}
	}

	/**
	 * sends a chat message to another player
	 * @param playerName
	 * 		the other player's name
	 * @param message
	 * 		the message
	 * @param color
	 * 		the color of the message
	 */
	public static void messageOtherPlayer(String playerName, String message,
			Colors color) {
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].playerName
						.equalsIgnoreCase(playerName)) {
					Player sender = (Player) PlayerHandler.players[i];
					sender.sendMessage(color + message);
				}
			}
		}
	}

	public static String getMatchedName(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

}
