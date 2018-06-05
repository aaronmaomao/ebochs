package com.mwos.ebochs.ui.view;

import org.eclipse.cdt.core.resources.IConsole;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.cdt.ui.IBuildConsoleManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsoleManager;

public class ConsoleFactory {

	public static void outMsg(String msg,IProject project) {
		IBuildConsoleManager manager = CUIPlugin.getDefault().getConsoleManager();
		IConsole console =manager.getProjectConsole(project);
		try {
			
			console.getOutputStream().write(msg.getBytes());
			console.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void outErrMsg(String msg,IProject project) {
		IBuildConsoleManager manager = CUIPlugin.getDefault().getConsoleManager();
		IConsole console =manager.getConsole(project);
		try {
			console.getErrorStream().write(msg.getBytes());
			console.getErrorStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void outInfoMsg(String msg,IProject project) {
		IBuildConsoleManager manager = CUIPlugin.getDefault().getConsoleManager();
		IConsole console =manager.getConsole(project);
		try {
			console.getInfoStream().write(msg.getBytes());
			console.getInfoStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
