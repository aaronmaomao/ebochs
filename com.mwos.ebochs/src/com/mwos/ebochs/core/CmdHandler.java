package com.mwos.ebochs.core;

public class CmdHandler {
	private static CmdHandler handler = new CmdHandler();
	private CmdHandler() {
	}
	
	public static CmdHandler getHandler() {
		return handler;
	}

	public int run(Command cmd, CmdableUI ui) {
		return 0;
	}
	
	public void dispose(CmdableUI ui) {
		
	}
}
