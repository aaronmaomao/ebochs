package com.mwos.ebochs.core.model.cmd;

public class CmdFactory {
	
	public static final Cmd RemoveListener = new Cmd("removeListener");
	public static final Cmd UpdateCare = new Cmd("updateCare");
	public static final Cmd Terminate = new Cmd("q\r\n");
	
	public static Cmd create(String cmd, String[] args) {
		return new Cmd(cmd, args);
	}

	public static Cmd create(String cmd) {
		return new Cmd(cmd);
	}
}
