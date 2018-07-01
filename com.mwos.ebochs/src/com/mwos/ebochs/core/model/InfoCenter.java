package com.mwos.ebochs.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.mwos.ebochs.core.vm.AbstractVM;

public class InfoCenter implements IStreamListener {

	private static InfoCenter infoCenter = new InfoCenter();

	private List<IInfoListener> listeners;
	private AbstractVM vm;
	private String info = "";

	private InfoCenter() {
		listeners = new ArrayList<>();
	}

	public void addListener(IInfoListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IInfoListener listener) {
		listeners.remove(listener);
	}

	public void active(AbstractVM vm) {
		try {
			PlatformUI.getWorkbench().showPerspective("com.mwos.ebochs.perspective.OSDebugPerspective",
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			e.printStackTrace();
		}
		this.vm = vm;
	}

	public boolean isActive() {
		return vm != null && vm.isAlive();
	}

	public synchronized String synSend(String cmd) {
		try {
			info = "";
			// process.getStreamsProxy().write(cmd + "\r\n");
			this.wait();
			return getInfo();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}

	public synchronized String synSend(String cmd, String[] others) {
		return cmd;
	}

	public void asynSend(IInfoListener sender, String cmd) {

	}

	public void asynSend(IInfoListener sender, String cmd, String[] others) {

	}

	public static InfoCenter getInfoCenter() {
		return infoCenter;
	}

	@Override
	public synchronized void streamAppended(String text, IStreamMonitor monitor) {
		info += text;
		if (info.endsWith("> ")) {
			this.notify();
		}
	}

	private synchronized String getInfo() {
		try {
			return info;
		} finally {
			info = "";
		}
	}
}
