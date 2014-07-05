package com.plug.core;

import java.awt.Color;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.plug.Game;
import com.plug.core.event.CycleEventHandler;
import com.plug.util.Log;
import com.plug.world.entity.player.PlayerHandler;

import core.ui.components.Console;

/**
 * The 'heart' of the server that is ticked every 600ms by the
 * <code>gameExecutor</code>. We use an executor here rather than
 * traditionally starting a new thread because if for whatever reason the game
 * thread crashes, as long as another non-daemon thread is still running the
 * <code>gameExecutor</code> will automatically recreate a new game thread to
 * run the world.
 * 
 * @author lare96
 */
public class Rs2Engine implements Runnable {
	

    /** The logger for printing debugging info. */
    private static Logger logger;

    /** A scheduled executor service that will tick the world. */
    private static ScheduledExecutorService gameExecutor;

    /**
     * Starts the {@link Rs2Engine}.
     * 
     * @throws Exception
     *         if any errors occur during the initialization of the engine.
     */
    public static void init() throws Exception {

        /** Create the logger. */
        logger = Logger.getLogger(Rs2Engine.class.getSimpleName());

        /** Create the game executor. */
        gameExecutor = Executors.newSingleThreadScheduledExecutor(new GameThreadProvider());

        /** Begin ticking the world. */
        gameExecutor.scheduleAtFixedRate(new Rs2Engine(), 0, 600, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        try {
            Game.itemHandler.process();
            Game.playerHandler.process();
            Game.npcHandler.process();
            Game.shopHandler.process();
            CycleEventHandler.getSingleton().process();
        } catch (Exception e) {
            logger.warning("Error while ticking the game world!");
            PlayerHandler.saveAllPlayers();
            Log.printError(e);
        }
    }

	public static ScheduledExecutorService getGameExecutor() {
		return gameExecutor;
	}

	public static void setGameExecutor(ScheduledExecutorService gameExecutor) {
		Rs2Engine.gameExecutor = gameExecutor;
	}
}
