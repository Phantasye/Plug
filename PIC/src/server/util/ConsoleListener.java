package server.util;

import core.util.ConsoleInputListener;
import core.util.Input;

public class ConsoleListener implements ConsoleInputListener {

	@Override
	public void execute(Input input) {
		MessageHandler.sendCustomGlobalMessage(Colors.BLUE, "[SERVER]", input.getInput());
	}
}