package com.mwos.ebochs.core;

public interface CmdableUI {
	public void ackData(Object data);
	public void notifyUI(Command cmd, Object notifyData);
}
