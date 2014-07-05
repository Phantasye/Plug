package com.plug.core.event.impl;

import com.plug.Constants;
import com.plug.core.event.CycleEvent;
import com.plug.world.entity.player.Player;
import com.plug.world.entity.player.PlayerHandler;

/**
 * A global event that will increase the special bar for people that need it.
 * 
 * @author lare96
 */
public class SpecialBarCycleEvent extends CycleEvent {

    /**
     * Create a new {@link SpecialBarCycleEvent}.
     */
    public SpecialBarCycleEvent() {
        super(null, Constants.INCREASE_SPECIAL_AMOUNT);
    }

    @Override
    public void execute() {
        for (Player player : PlayerHandler.players) {
            if (player == null) {
                continue;
            }

            if (player.specAmount < 10) {
                player.specAmount += .5;
                if (player.specAmount > 10) {
                    player.specAmount = 10;
                }
                player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
            }
        }
    }
}
