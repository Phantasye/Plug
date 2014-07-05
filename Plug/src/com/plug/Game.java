package com.plug;

import java.awt.Color;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.Collection;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import com.plug.core.BlockingServiceQueue;
import com.plug.core.Rs2Engine;
import com.plug.core.event.CycleEvent;
import com.plug.core.event.CycleEventHandler;
import com.plug.core.event.Task;
import com.plug.core.event.TaskScheduler;
import com.plug.core.event.impl.PoisonCycleEvent;
import com.plug.core.event.impl.PrayerRestoreCycleEvent;
import com.plug.core.event.impl.SpecialBarCycleEvent;
import com.plug.core.net.Connection;
import com.plug.core.net.NetworkHandler;
import com.plug.core.net.security.ConnectionThrottleFilter;
import com.plug.plugins.PluginHandler;
import com.plug.util.Colors;
import com.plug.util.ConsoleListener;
import com.plug.util.Log;
import com.plug.util.Memory;
import com.plug.util.MessageHandler;
import com.plug.util.Stopwatch;
import com.plug.util.io.Resource;
import com.plug.util.io.ServerSettings;
import com.plug.world.entity.npc.NpcDrops;
import com.plug.world.entity.npc.NpcHandler;
import com.plug.world.entity.player.PlayerHandler;
import com.plug.world.entity.player.content.commands.Command;
import com.plug.world.entity.player.content.commands.impl.Commands;
import com.plug.world.entity.player.content.commands.impl.Update;
import com.plug.world.item.ItemHandler;
import com.plug.world.object.ObjectHandler;
import com.plug.world.shop.ShopHandler;
import com.plug.world.virtual.WorldMap;

import core.connection.ConnectionManager;
import core.ui.GUI;
import core.ui.components.Console;

/**
 * Server.java
 * 
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 * @author lare96
 */

public class Game implements ConnectionManager {
	
	private static final File PLUGINS = new File("./plugins/");

	private static IoAcceptor acceptor = new SocketAcceptor();
	private static NetworkHandler connectionHandler;
	private static ConnectionThrottleFilter throttleFilter;
	public static ItemHandler itemHandler;
	public static PlayerHandler playerHandler = new PlayerHandler();
	public static NpcHandler npcHandler;
	public static ShopHandler shopHandler;
	public static ObjectHandler objectHandler;
	public static NpcDrops npcDrops = new NpcDrops();
	private static GUI gui = new GUI(new Game());

	public static void main(String args[]) {
		try {
			Stopwatch timer = new Stopwatch().reset();
			BlockingServiceQueue loader = new BlockingServiceQueue();
			loader.execute(new Runnable() {
				@Override
				public void run() {
					try {
						itemHandler = ItemHandler.class.newInstance();
						npcHandler = NpcHandler.class.newInstance();
						shopHandler = ShopHandler.class.newInstance();
						objectHandler = ObjectHandler.class.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			loader.execute(new Runnable() {
				@Override
				public void run() {
					try {
					Command.loadCommand(new Commands());
					Command.loadCommand(new Update());
					Console.getSingleton().setListener(new ConsoleListener());
					new Resource(new ServerSettings(), "./res/settings.txt");
					WorldMap.loadWorldMap();
					Connection.initialize();
					} catch(Exception e) {
						Log.printError(e);
					} finally {
						
					}
				}
			});
			loader.startAndAwait();
			loader.shutdown();
			PluginManager pm = PluginManagerFactory
					.createPluginManager();
			pm.addPluginsFrom(PLUGINS.toURI());
			Collection<PluginHandler> plugins = new PluginManagerUtil(
					pm).getPlugins(PluginHandler.class);
			for (PluginHandler plugin : plugins) {
				plugin.initialize();
			}
			Rs2Engine.init();
			connectionHandler = new NetworkHandler();

			SocketAcceptorConfig sac = new SocketAcceptorConfig();
			sac.getSessionConfig().setTcpNoDelay(false);
			sac.setReuseAddress(true);
			sac.setBacklog(100);

			throttleFilter = new ConnectionThrottleFilter(
					Constants.CONNECTION_DELAY);
			sac.getFilterChain().addFirst("throttleFilter", throttleFilter);
			acceptor.bind(new InetSocketAddress(Constants.PORT), connectionHandler, sac);
			Log.printInfo(Constants.SERVER_NAME + " is now online! [took "
					+ timer.elapsed() + " ms]");
			CycleEventHandler.getSingleton()
					.addBulkEvent(
							new CycleEvent[] { new PrayerRestoreCycleEvent(),
									new SpecialBarCycleEvent(),
									new PoisonCycleEvent() });
			Memory.handleMemoryUsage();
		} catch (Exception e) {
			Console.writeToConsole(e, Color.RED);
		}
	}

	private static void close() {
		Log.printInfo("Saving all players.");
		MessageHandler.sendCustomGlobalMessage(Colors.RED, "[SERVER]", "SHUTTING DOWN IN " + gui.getDelayInputField().getText() + " SECONDS.");
		getTaskScheduler().addEvent(new Task(2, true) {
			private int count = Integer.parseInt(gui.getDelayInputField().getText());
			@Override
			protected void execute() {
				if (count > 0) {	
					Log.printInfo("Shutting down in... " + count);
					count--;
				} else {
					PlayerHandler.saveAllPlayers();
					acceptor.unbindAll();
					Rs2Engine.getGameExecutor().shutdown();
					Thread t = new Thread("Rs2Engine");
					t.interrupt();
					Log.printInfo("Server successfully shutdown!");
					this.stop();
				}
			}
		});

	}

	public static ItemHandler getItemHandler() {
		return itemHandler;
	}

	public static PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	public static NpcHandler getNpcHandler() {
		return npcHandler;
	}

	public static ShopHandler getShopHandler() {
		return shopHandler;
	}

	public static ObjectHandler getObjectHandler() {
		return objectHandler;
	}

	public class Shutdown {

	}

	/**
	 * The task scheduler.
	 */
	private static final TaskScheduler scheduler = new TaskScheduler();

	/**
	 * Gets the task scheduler.
	 * 
	 * @return The task scheduler.
	 */
	public static TaskScheduler getTaskScheduler() {
		return scheduler;
	}

	@Override
	public void restart(String[] arg0) {
	}

	@Override
	public void start(String[] arg0) {
		main(arg0);
	}

	@Override
	public void stop() {
		close();
	}
}
