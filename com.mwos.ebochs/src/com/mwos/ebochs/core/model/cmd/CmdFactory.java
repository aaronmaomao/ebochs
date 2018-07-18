package com.mwos.ebochs.core.model.cmd;

public class CmdFactory {

	public static final Cmd RemoveListener = new NCmd(CmdStr.RemoveListener);
	public static final Cmd UpdateCare = new NCmd(CmdStr.UpdateCare);
	public static final Cmd Terminate = new NCmd(CmdStr.Terminate);

	public static Cmd createN(String cmd, String[] args) {
		return new NCmd(cmd, args);
	}

	public static Cmd createN(String cmd) {
		return new NCmd(cmd);
	}
	
	public static Cmd createD(String cmd, String[] args) {
		return new DCmd(cmd, args);
	}

	public static Cmd createD(String cmd) {
		return new DCmd(cmd);
	}
}
