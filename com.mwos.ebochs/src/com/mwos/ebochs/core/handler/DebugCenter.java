package com.mwos.ebochs.core.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;

public  class DebugCenter implements IStreamListener {
	public List<IVMListener> listeners;
	private String info = "";
	private IProcess process;

	public DebugCenter(IProcess p) throws Exception {
		this.process = p;
		listeners = new ArrayList<>();
		p.getStreamsProxy().getOutputStreamMonitor().addListener(this);
	}

	public void addListener(IVMListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IVMListener listener) {
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

	public void asynSend(IVMListener sender, String cmd) {

	}

	public void asynSend(IVMListener sender, String cmd, String[] others) {

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
	
}
