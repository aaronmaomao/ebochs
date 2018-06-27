package com.mwos.ebochs.ui.debug.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

public class BPManager implements IBreakpointListener {
	private IProject project;
	private List<CLineBreakpoint> bps;

	public BPManager(IProject project) {
		this.project = project;
		bps = new ArrayList<>();
		IBreakpoint[] bs = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints("org.eclipse.cdt.debug.core");
		for (IBreakpoint bp : bs) {
			if (bp instanceof CLineBreakpoint) {
				bps.add((CLineBreakpoint) bp);
			}
		}
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {

	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

}
