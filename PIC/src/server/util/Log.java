package server.util;

import java.awt.Color;

import core.ui.components.Console;

public class Log {

//	private static final String PATH = "./data/serverData/console/errors.txt";
//	private static Date date = new Date();
//	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
//			"E yyyy.MM.dd 'at' hh:mm:ss a zzz");

	public static void printInfo(Object info) {
		Console.writeToConsole(info, Color.GREEN);
	}

	public static void printError(Object error) {
//		writeError(error);
		Console.writeToConsole(error, Color.RED);
	}

	public static void printWarning(Object warning) {
		Console.writeToConsole(warning, Color.YELLOW);
	}

//	private static void writeError(String error) {
//		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
//				PATH, true))) {
//			String formattedError = dateFormat.format(date) + " - " + error;
//			bufferedWriter.write(formattedError);
//			bufferedWriter.newLine();
//		} catch (FileNotFoundException ex) {
//			printError(ex.getMessage() + " in class " + ex.getClass());
//		} catch (IOException ex) {
//			printError(ex.getMessage() + " in class " + ex.getClass());
//		}
//	}
}
