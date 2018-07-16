package com.mwos.ebochs.core.model.cmd;

public class CmdFactory {
	
	public static final Cmd removeListener = new Cmd("removeListener");
	public static final Cmd updateCare = new Cmd("updateCare");
	
	public static Cmd create(String cmd, String[] args) {
		return new Cmd(cmd, args);
	}

	public static Cmd create(String cmd) {
		return new Cmd(cmd);
	}
}
