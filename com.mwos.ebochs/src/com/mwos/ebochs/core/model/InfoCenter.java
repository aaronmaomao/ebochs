package com.mwos.ebochs.core.model;

import java.util.ArrayList;
import java.util.List;

import com.mwos.ebochs.core.vm.bochs.DebugModel;

public class InfoCenter {

	private static InfoCenter infoCenter = new InfoCenter();

	private List<IInfoListener> listeners;
	private List<DebugModel> debugs;
	private int current=-1;

	private InfoCenter() {
		listeners = new ArrayList<>();
		debugs = new ArrayList<>();
	}

	public void addListener(IInfoListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IInfoListener listener) {
		listeners.remove(listener);
	}
	
	public void addDebug(DebugModel debug) {
		debugs.add(debug);
	}

	public void removeDebug(DebugModel debug) {
		debugs.remove(debug);
	}

	public synchronized Object synSend(String cmd, String[] others) {
		return cmd;
	}

	public static InfoCenter getInfoCenter() {
		return infoCenter;
	}

	public String send(String cmd) {
		return cmd;
	}
}
