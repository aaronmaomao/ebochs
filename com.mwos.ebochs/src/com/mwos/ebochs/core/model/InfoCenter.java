package com.mwos.ebochs.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.mwos.ebochs.core.model.handler.BPHandler;
import com.mwos.ebochs.core.vm.AbstractVM;

public class InfoCenter implements IStreamListener {

	private static InfoCenter infoCenter = new InfoCenter();

	private List<IInfoListener> listeners;
	private List<AbstractVM> vms;

	private BPHandler bpHandler;

	private InfoCenter() {
		listeners = new ArrayList<>();
		vms = new ArrayList<>();
	}

	public void addListener(IInfoListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IInfoListener listener) {
		listeners.remove(listener);
	}
	
	public void addVm(AbstractVM vm) {
		this.vms.add(vm);
	}
	
	public void removeVm(AbstractVM vm) {
		this.vms.remove(vm);
	}

//	public void active(AbstractVM vm, IProcess iProcess) {
//		try {
//			PlatformUI.getWorkbench().showPerspective("com.mwos.ebochs.perspective.OSDebugPerspective", PlatformUI.getWorkbench().getActiveWorkbenchWindow());
//			iProcess.getStreamsProxy().getOutputStreamMonitor().addListener(this);
//		} catch (WorkbenchException e) {
//			e.printStackTrace();
//		}
//		this.vm = vm;
//		bpHandler = new BPHandler(vm.getProfile().getConfig(), this);
//
//		for (IInfoListener infoListener : listeners) {
//			infoListener.notify(InfoCmd.Host_Changed);
//		}
//	}

//	public boolean isActive() {
//		return vm != null && vm.isAlive();
//	}
//
//	public synchronized Object synSend(String cmd) {
//		try {
//			if(cmd.equals(InfoCmd.BP_Get)) {
//				return bpHandler.handler(cmd);
//			}
//			
//			vm.getProcess().getOutputStream().write((cmd + "\r\n").getBytes());
//			vm.getProcess().getOutputStream().flush();
//			this.wait();
//			return getInfo();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Error";
//		}
//	}

	public synchronized Object synSend(String cmd, String[] others) {
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
//		info += text;
//		if (info.endsWith("> ")) {
//			this.notify();
//		}
	}

	private synchronized String getInfo() {
		return null;
//		try {
//			return info;
//		} finally {
//			info = "";
//		}
	}

	public void notify(String cmd, Object obj) {
		for (IInfoListener listener : listeners) {
			listener.notify(cmd, obj);
		}
	}

	private String handleCmd(String cmd) {
		return cmd;
//		switch (cmd) {
//		case InfoCmd.Host_Get:
//			return this.vm.toString();
//
//		default:
//			return null;
//		}
	}
}
