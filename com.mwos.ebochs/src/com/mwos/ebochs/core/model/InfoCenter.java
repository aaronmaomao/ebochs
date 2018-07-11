package com.mwos.ebochs.core.model;

import java.util.ArrayList;
import java.util.List;

import com.mwos.ebochs.core.vm.bochs.DebugModel;

public class InfoCenter {

	private static InfoCenter infoCenter = new InfoCenter();

	private List<IInfoListener> listeners;
	private DebugModel debug = null;

	private InfoCenter() {
		listeners = new ArrayList<>();
	}

	public void addListener(IInfoListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IInfoListener listener) {
		listeners.remove(listener);
	}

	public void setDebug(DebugModel debug) {
		this.debug = debug;
	}

	public synchronized Object synSend(String cmd, Object object) {
		return cmd;
	}

	public Object send(String cmd, Object object) {
		return cmd;
	}

	public void asynSend(String cmd, Object object) {
		new Thread(new Runnable() {
			@Override
			public void run() {

			}
		}).start();
	}

	public String send(String cmd) {
		return cmd;
	}

	public static InfoCenter getInfoCenter() {
		return infoCenter;
	}

	public DebugModel getDebug() {
		return debug;
	}

	public boolean isVaild() {
		return debug == null ? false : true;
	}

	public void notity(Class<IInfoListener> lisClass, String cmd, Object cont) {
		for (IInfoListener listener : this.listeners) {
			if (listener.getClass().equals(lisClass)) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						listener.notify(cmd, cont);

					}
				}).start();
			}
		}
	}
}
