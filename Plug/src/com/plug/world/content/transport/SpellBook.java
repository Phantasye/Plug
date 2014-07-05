package com.plug.world.content.transport;

import com.plug.Constants;
import com.plug.Game;
import com.plug.core.event.Task;
import com.plug.util.Colors;
import com.plug.util.exceptions.InsufficientRightsException;
import com.plug.util.exceptions.NoRunesException;
import com.plug.util.exceptions.NonMemberException;
import com.plug.world.entity.player.Player;

public abstract class SpellBook extends Teleport {

	@Override
	public void execute(final Player player) {
		try {
			if (canTeleport(player)) {
				deleteRunes(player);
				player.startAnimation(getStartAnimation());
				player.performGfx(getMask(), getGfx());
				Game.getTaskScheduler().addEvent(
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
		if (Constants.RUNES_REQUIRED) {
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
