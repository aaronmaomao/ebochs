package com.mwos.ebochs.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;

public  class InfoCenter implements IStreamListener {
	
	private static InfoCenter current = new InfoCenter();
	
	private List<IInfoListener> listeners;
	private String info = "";
	private IProcess process;

	public InfoCenter() throws Exception {
		listeners = new ArrayList<>();
	}

	public void addListener(IInfoListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IInfoListener listener) {
		listeners.remove(listener);
	}

	public synchronized String synSend(String cmd) {
		try {
			info = "";
			process.getStreamsProxy().write(cmd + "\r\n");
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

	@Override
	public synchronized void streamAppended(String text, IStreamMonitor monitor) {
		info += text;
		if(info.endsWith("> ")) {
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
	
	public static InfoCenter getCurrentInfoCenter() {
		if(current==null && infoCenters.size()!=0) {
			current = infoCenters.get(0);
		}
		return current;
	}
}
