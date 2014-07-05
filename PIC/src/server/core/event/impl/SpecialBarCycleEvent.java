package server.core.event.impl;

import server.Config;
import server.core.event.CycleEvent;
import server.world.entity.player.Player;
import server.world.entity.player.PlayerHandler;

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
        super(null, Config.INCREASE_SPECIAL_AMOUNT);
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
