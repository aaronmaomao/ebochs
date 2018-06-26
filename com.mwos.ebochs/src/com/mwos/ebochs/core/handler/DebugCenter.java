package com.mwos.ebochs.core.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;

public class DebugCenter implements IStreamListener{
	public List<IVMListener> listeners;
	private IStreamsProxy stream;
	
	public DebugCenter(IProcess process) {
		listeners = new ArrayList<>();
		stream = process.getStreamsProxy();
		stream.getOutputStreamMonitor().addListener(this);
	}

	public void addListener(IVMListener listener) {
		listeners.add(listener);
	}

	public void removeLis tener(IVMListener listener) {
		listeners.remove(listener);
	}

	public String synSend(String cmd) {
		try {
			stream.write(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cmd;
	}

	public String synSend(String cmd, String[] others) {
		return cmd;
	}

	public void asynSend(IVMListener sender, String cmd) {

	}

	public void asynSend(IVMListener sender, String cmd, String[] others) {

	}

	@Override
	public void streamAppended(String text, IStreamMonitor monitor) {
		
	}
}
