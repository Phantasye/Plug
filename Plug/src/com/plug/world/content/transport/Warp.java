package com.plug.world.content.transport;

import java.util.HashMap;
import java.util.Map;

import com.plug.Game;
import com.plug.core.event.Task;
import com.plug.util.Colors;
import com.plug.util.exceptions.InsufficientRightsException;
import com.plug.util.exceptions.NoRunesException;
import com.plug.util.exceptions.NonMemberException;
import com.plug.world.entity.player.Player;

public abstract class Warp extends Teleport {

	@Override
	public void execute(final Player player) {
		try {
			if (canTeleport(player)) {
				player.startAnimation(getStartAnimation());
				player.performGfx(getMask(), getGfx());
				Game.getTaskScheduler().addEvent(
						new Task(getDelay(), false) {
							@Override
							protected void execute() {
								processTeleport(player);
								player.sendMessage(getWarpMessage());
								this.stop();
							}
						});
			}
		} catch (InsufficientRightsException | NoRunesException | NonMemberException e) {
			player.sendMessage(e.getMessage(), Colors.RED);
		}
	}
	
	@Override
	public int getMask() {
		return 100;
	}

	@Override
	public int getStartAnimation() {
		return 714;
	}

	@Override
	public int getEndAnimation() {
		return 715;
	}

	@Override
	public int getGfx() {
		return 308;
	}

	@Override
	public int getDelay() {
		return 2;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public int getXP() {
		return 0;
	}

	public static void load(Warp warp) {
		warps.put(warp.getLocationName(), warp);
	}

	public abstract String getLocationName();

	public abstract String getWarpMessage();

	public static Map<String, Warp> warps = new HashMap<>();

	public static void displayWarps(Player c) {
		for (String key : warps.keySet()) {
			c.sendMessage("::warp " + warps.get(key).getLocationName());
		}
	}
}
