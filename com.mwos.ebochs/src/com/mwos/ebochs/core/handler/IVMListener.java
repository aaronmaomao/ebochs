package com.mwos.ebochs.core.handler;

public interface IVMListener {
	public void notify(String rec);

	public void notify(String cmd, String rec);
}
