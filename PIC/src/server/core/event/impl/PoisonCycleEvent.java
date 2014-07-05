package server.core.event.impl;

import server.core.event.CycleEvent;
import server.world.entity.Hit;
import server.world.entity.Hit.HitType;
import server.world.entity.player.Player;
import server.world.entity.player.PlayerHandler;

/**
 * A global event that will apply poison damage to everyone who needs it.
 * 
 * @author lare96
 */
public class PoisonCycleEvent extends CycleEvent {

    /**
     * Create a new {@link PoisonCycleEvent}.
     */
    public PoisonCycleEvent() {
        super(null, 40);
    }

    @Override
    public void execute() {
        for (Player player : PlayerHandler.players) {
            if (player == null) {
                continue;
            }

            if (player.poisonDamage == -1) {
                continue;
            }

            int damage = player.poisonDamage / 2;

            if (damage > 0) {
                player.sendMessage("Applying poison damage.");
                if (!player.hitUpdateRequired) {
                    player.dealHit(new Hit(damage, HitType.POISON));
                } else if (!player.secondaryHitUpdateRequired) {
                    player.dealSecondaryHit(new Hit(damage, HitType.POISON));
                }
                player.poisonDamage--;
            } else {
                player.poisonDamage = -1;
                player.sendMessage("You are no longer poisoned.");
            }
        }
    }
}
