package com.mwos.ebochs.core;

public class Command {
	private String cmd;

	public String getCmd() {
		return cmd;
	}

	public CmdableUI getUi() {
		return ui;
	}

	private CmdableUI ui;

	public Command(CmdableUI ui, BochsCmdStr cmd) {
		this.cmd = cmd.toString();
		this.ui = ui;
	}

	public Command(CmdableUI ui, BochsCmdStr cmd, String[] args) {
		this.cmd = cmd.toString();
		this.ui = ui;
		if (args != null) {
			for (String arg : args) {
				this.cmd += (" " + arg);
			}
		}
	}

}
