package com.mwos.ebochs.core.model.cmd;

public class Cmd {
	private String cmd = "";
	private String[] args;

	public Cmd(String cmd) {
		this.cmd = cmd;
	}

	public Cmd(String cmd, String[] args) {
		super();
		this.cmd = cmd;
		this.args = args;
	}

	public Cmd(String cmd, String arg) {
		super();
		this.cmd = cmd;
		this.args = new String[] { arg };
	}

	@Override
	public String toString() {
		String str = cmd;
		if (args != null) {
			for (String arg : args) {
				cmd += (" " + arg);
			}
		}
		return str;
	}

	public String getCmd() {
		return cmd;
	}
}
