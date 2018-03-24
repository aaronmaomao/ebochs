package com.mwos.ebochs.view;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

public class ConsoleFactory implements IConsoleFactory {
	private static MessageConsole console = new MessageConsole("", null);
	private static boolean exist = false;

	@Override
	public void openConsole() {
		showConsole();

	}

	public static void showConsole() {
		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		IConsole[] existing = consoleManager.getConsoles();
		exist = ArrayUtils.contains(existing, console);
		if (!exist) {
			consoleManager.addConsoles(new IConsole[] { console });
		}
	}

	public static void closeConsole() {
		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		if (console != null) {
			consoleManager.removeConsoles(new IConsole[] { console });
		}
	}

	public static MessageConsole getConsole() {
		showConsole();
		return console;
	}
}
