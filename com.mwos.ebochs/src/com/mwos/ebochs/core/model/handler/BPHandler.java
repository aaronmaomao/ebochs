package com.mwos.ebochs.core.model.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.model.InfoCenter;
import com.mwos.ebochs.core.model.InfoCmd;
import com.mwos.ebochs.resource.config.entity.OSConfig;

public class BPHandler {
	private OSConfig config;
	private InfoCenter infoCenter;

	public BPHandler(OSConfig config, InfoCenter infoCenter) {
		this.config = config;
		this.infoCenter = infoCenter;

		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(new IBreakpointListener() {

			@Override
			public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
				if (breakpoint.getMarker().getResource().getProject() == config.getProject()) {
					infoCenter.notify(InfoCmd.BP_Del, breakpoint);
				}

			}

			@Override
			public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
				// TODO Auto-generated method stub

			}

			@Override
			public void breakpointAdded(IBreakpoint breakpoint) {
				if (breakpoint.getMarker().getResource().getProject() == config.getProject()) {
					infoCenter.notify(InfoCmd.BP_Add, breakpoint);
				}
			}
		});
	}

	public Object handler(String cmd) {
		if (cmd.equals(InfoCmd.BP_Get)) {
			try {
				return getAllBp();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return cmd;
	}

	private List<String[]> getAllBp() throws CoreException {
		List<String[]> bps = new ArrayList<>();
		IBreakpoint[] bs = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints("org.eclipse.cdt.debug.core");
		for (IBreakpoint bp : bs) {
			if (bp instanceof CLineBreakpoint && bp.getMarker().getResource().getProject() == config.getProject()) {
				CLineBreakpoint temp = (CLineBreakpoint) bp;
				String[] b = new String[5];
				b[0] = "" + temp.isEnabled();
				b[1] = calculate(temp);
				b[2] = temp.getFileName() + "[" + temp.getLineNumber() + "L]";
				b[3] = temp.getFunction();
			}
		}
		return bps;
	}

	private String calculate(CLineBreakpoint breakpoint) throws CoreException {
		int line = breakpoint.getLineNumber();
		String function = breakpoint.getFunction();
		String fileName = breakpoint.getFileName();
		return null;
	}
}
