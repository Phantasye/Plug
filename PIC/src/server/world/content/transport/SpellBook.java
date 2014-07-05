package server.world.content.transport;

import server.Config;
import server.Server;
import server.core.event.Task;
import server.util.Colors;
import server.util.exceptions.InsufficientRightsException;
import server.util.exceptions.NoRunesException;
import server.util.exceptions.NonMemberException;
import server.world.entity.player.Player;

public abstract class SpellBook extends Teleport {

	@Override
	public void execute(final Player player) {
		try {
			if (canTeleport(player)) {
				deleteRunes(player);
				player.startAnimation(getStartAnimation());
				player.performGfx(getMask(), getGfx());
				Server.getTaskScheduler().addEvent(
						new Task(getDelay(), false) {
							@Override
							protected void execute() {
								processTeleport(player);
								this.stop();
							}
				});	
			}
		} catch (InsufficientRightsException | NoRunesException | NonMemberException e) {
			player.sendMessage(e.getMessage(), Colors.RED);
		}
	}

	protected void deleteRunes(Player player) throws NoRunesException {
		if (Config.RUNES_REQUIRED) {
			for (int i = 0; i < getRunes().length; i++) {
				if (!player.getItems().playerHasItem(getRunes()[i][0],
						getRunes()[i][1])) {
					throw new NoRunesException();
				}
				player.getItems().deleteItem2(getRunes()[i][0],
						getRunes()[i][1]);
			}
		}
	}

	public abstract int[][] getRunes();

}
