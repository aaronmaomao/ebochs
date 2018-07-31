package com.mwos.ebochs.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mwos.ebochs.core.model.cmd.CmdStr;
import com.mwos.ebochs.core.vm.bochs.DebugModel;

public class InfoCenter {

	private static InfoCenter infoCenter = new InfoCenter();

	private List<IInfoListener> listeners;
	public Map<String, Set<IInfoListener>> cares;
	private DebugModel dm;

	private InfoCenter() {
		listeners = new ArrayList<>();
		cares = new HashMap<>();
	}

	public void addListener(IInfoListener listener) {
		listeners.add(listener);
		updateCare(listener);
	}

	public String send(String cmd, Object object) {

		if (cmd.equals(CmdStr.RemoveListener)) {
			listeners.remove(object);
			removeCare((IInfoListener) object);
		}

		if (cmd.equals(CmdStr.UpdateCare)) {
			updateCare((IInfoListener) object);
		}
		
		if(cmd.equals(CmdStr.DestoryDM)) {
			dm = null;
			listeners.remove(object);
			removeCare((IInfoListener) object);
		}
		
		if(cmd.equals(CmdStr.AddDM)) {
			dm = (DebugModel) object;
			this.addListener((IInfoListener) object);
		}

		notifyLis(cmd, object);
		return null;
	}

	public static InfoCenter getInfoCenter() {
		return infoCenter;
	}

	public DebugModel getDebug() {
		return dm;
	}

	public boolean isVaild() {
		return dm == null ? false : true;
	}

	private void notifyLis(String cmd, Object cont) {
		if (this.cares.get(cmd) == null)
			return;

		for (IInfoListener listener : cares.get(cmd)) {
			listener.notify(cmd, cont);
		}

	}

	private void updateCare(IInfoListener listener) {
		removeCare(listener);
		if (listener.getCare() == null)
			return;
		for (String care : listener.getCare()) {
			if (cares.get(care) == null) {
				cares.put(care, new HashSet<>());
			}
			cares.get(care).add(listener);
		}
	}

	private void removeCare(IInfoListener listener) {
		for (String care : cares.keySet()) {
			cares.get(care).remove(listener);
		}
	}
}
