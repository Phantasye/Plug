package server.core.event.impl;

import server.core.event.CycleEvent;
import server.world.entity.player.Player;
import server.world.entity.player.PlayerHandler;

/**
 * A global event that restores stats for players.
 * 
 * @author lare96
 */
public class PrayerRestoreCycleEvent extends CycleEvent {

    /**
     * Create a new {@link PrayerRestoreCycleEvent}.
     */
    public PrayerRestoreCycleEvent() {
        super(null, 120);
    }

    @Override
    public void execute() {
        for (Player player : PlayerHandler.players) {
            if (player == null) {
                continue;
            }

            for (int level = 0; level < player.playerLevel.length; level++) {
                if (player.playerLevel[level] < player.getLevelForXP(player.playerXP[level])) {
                    if (level != 5) { // prayer doesn't restore
                        player.playerLevel[level] += 1;
                        player.getPA().setSkillLevel(level, player.playerLevel[level], player.playerXP[level]);
                        player.getPA().refreshSkill(level);
                    }
                } else if (player.playerLevel[level] > player.getLevelForXP(player.playerXP[level])) {
                    player.playerLevel[level] -= 1;
                    player.getPA().setSkillLevel(level, player.playerLevel[level], player.playerXP[level]);
                    player.getPA().refreshSkill(level);
                }
            }
        }
    }
}
