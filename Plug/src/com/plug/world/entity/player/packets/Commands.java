package com.plug.world.entity.player.packets;

import com.plug.util.Colors;
import com.plug.util.Log;
import com.plug.util.exceptions.IndenticalObjectException;
import com.plug.util.exceptions.InsufficientRightsException;
import com.plug.util.exceptions.InvalidCommandException;
import com.plug.util.exceptions.NonMemberException;
import com.plug.util.exceptions.NotInClanException;
import com.plug.util.exceptions.PlayerNotFoundException;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.content.commands.Command;

/**
 * @author Quinton (Phantasye)
 * 
 */
public class Commands implements com.plug.core.net.protocol.packet.PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		String realPlayerCommand = c.getInStream().readString().toLowerCase();
		String playerCommand = realPlayerCommand.toLowerCase();
		String[] args = playerCommand.split(" ");
		System.out.println(playerCommand);
		try {
//			if (playerCommand.startsWith("/")) {
//				Command.commands.get("/").execute(c, playerCommand);
//			}
			if (Command.commands.get(args[0]).canExecute(c, args[0])) {
				Command.commands.get(args[0]).execute(c, playerCommand);
			}
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			c.sendMessage(Command.commands.get(args[0]).getUsage(), Colors.RED);
		} catch (InvalidCommandException | NonMemberException
				| InsufficientRightsException | IndenticalObjectException
				| NotInClanException e) {
			c.sendMessage(e.getMessage(), Colors.RED);
		} catch (NullPointerException e) {
			if (playerCommand.startsWith("/"))
				return;
			if (!Command.commands.containsKey(args[0])) {
				new InvalidCommandException(args[0]).checkCommand(c);
			} else {
				c.sendMessage(new PlayerNotFoundException().getMessage(),
						Colors.RED);
			}
		} catch (Exception e) {
			Log.printError("Something went wrong with a command "
					+ e.getCause());
			c.sendMessage("Something went wrong try again.", Colors.RED);
		}

	}
}