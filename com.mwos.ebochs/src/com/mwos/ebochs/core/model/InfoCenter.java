package com.mwos.ebochs.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
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
import com.mwos.ebochs.resource.config.entity.CodePart.Code;
import com.mwos.ebochs.resource.config.entity.OSConfig;

public class InfoCenter {

	private static InfoCenter infoCenter = new InfoCenter();

	private List<IInfoListener> listeners;
	private List<AbstractVM> vms;
	private AbstractVM current;

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
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(new IBreakpointListener() {

			@Override
			public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
				if (breakpoint.getMarker().getResource().getProject() == vm.getProfile().getConfig().getProject()) {
					infoCenter.notify(InfoCmd.BP_Del, breakpoint);
				}
			}

			@Override
			public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
				// TODO Auto-generated method stub

			}

			@Override
			public void breakpointAdded(IBreakpoint breakpoint) {
				if (breakpoint.getMarker().getResource().getProject() == vm.getProfile().getConfig().getProject()) {
					infoCenter.notify(InfoCmd.BP_Add, breakpoint);
				}
			}
		});
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

	public boolean isActive() {
		return current != null && current.isAlive();
	}
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
	
	private void initBp(AbstractVM vm) {
		
	}
	
//	private void getAllBp(AbstractVM vm) {
//		List<String[]> bps = new ArrayList<>();
//		IBreakpoint[] bs = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints("org.eclipse.cdt.debug.core");
//		for (IBreakpoint bp : bs) {
//			if (bp instanceof CLineBreakpoint && bp.getMarker().getResource().getProject() == config.getProject()) {
//				CLineBreakpoint temp = (CLineBreakpoint) bp;
//				String[] b = new String[5];
//				b[0] = "" + temp.isEnabled();
//				b[1] = calculate(temp);
//				b[2] = temp.getFileName() + "[" + temp.getLineNumber() + "L]";
//				b[3] = temp.getFunction();
//			}
//		}
//		return bps;
//	}
//	
//	private String calculate(CLineBreakpoint bp) throws CoreException {
//		int line = bp.getLineNumber();
//		String function = bp.getFunction();
//		String fileName = bp.getFileName();
//	}
}
