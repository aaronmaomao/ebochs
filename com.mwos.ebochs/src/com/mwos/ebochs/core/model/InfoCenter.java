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

	public String send(Cmd cmd) {
		return cmd.getCmd();
	}

	public String send(Cmd cmd, Object object) {
		if (cmd.getCmd().equals(CmdFactory.removeListener.getCmd())) {
			this.removeListener((IInfoListener) object);
			return "";
		}

		if (cmd.getCmd().equals(CmdFactory.updateCare.getCmd())) {
			updateCare((IInfoListener) object);
			return "";
		}
		return cmd.getCmd();
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
