package com.mwos.ebochs.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mwos.ebochs.core.model.cmd.Cmd;
import com.mwos.ebochs.core.model.cmd.CmdFactory;
import com.mwos.ebochs.core.vm.bochs.DebugModel;

public class InfoCenter {

	private static InfoCenter infoCenter = new InfoCenter();

	private List<IInfoListener> listeners;
	public Map<String, Set<IInfoListener>> cares;
	private DebugModel debug = null;

	private InfoCenter() {
		listeners = new ArrayList<>();
		cares = new HashMap<>();
	}

	public void addListener(IInfoListener listener) {
		listeners.add(listener);
		updateCare(listener);
	}

	public void removeListener(IInfoListener listener) {
		listeners.remove(listener);
		removeCare(listener);
	}

	public void setDebug(DebugModel debug) {
		this.debug = debug;
		this.addListener(debug);
	}

	public void removeDebug(DebugModel debug) {
		if (this.debug == debug) {
			this.debug = null;
		}
		removeListener(debug);

	}

	public synchronized Object synSend(String cmd, Object object) {
		return cmd;
	}

	public Object send(String cmd, Object object) {
		return cmd;
	}

	public String send(Cmd cmd) {
		return this.send(cmd, null);
	}

	public String send(Cmd cmd, Object object) {
		if (cmd.getCmd().equals(CmdFactory.RemoveListener.getCmd())) {
			this.removeListener((IInfoListener) object);
			return "";
		}

		if (cmd.getCmd().equals(CmdFactory.UpdateCare.getCmd())) {
			updateCare((IInfoListener) object);
			return "";
		}

		String rec = debug.sendToVM(cmd);
		notifyLis(cmd.getCmd(), rec);
		return rec;
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

	private void notifyLis(String cmd, String cont) {
		if (this.cares.get(cmd) == null)
			return;
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (IInfoListener listener : cares.get(cmd)) {
					listener.notify(cmd, cont);
				}
			}
		}).start();
	}

	private void updateCare(IInfoListener listener) {
		removeCare(listener);
		for (String care : listener.getCare()) {
			if (cares.get(care) == null) {
				cares.put(care, new HashSet<>());
			}
			cares.get(care).add(listener);
		}
	}

	private void removeCare(IInfoListener listener) {
		for (String care : listener.getCare()) {
			if (cares.get(care) != null) {
				cares.get(care).remove(listener);
			}
		}
	}
}
