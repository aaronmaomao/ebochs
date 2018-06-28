package com.mwos.ebochs.ui.debug.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.model.InfoCenter;

public class BPManager implements IBreakpointListener {
	private IProject project;
	private List<CLineBreakpoint> bps;
	private InfoCenter center;
	
	public BPManager(IProject project, InfoCenter center) {
		this.project = project;
		bps = new ArrayList<>();
		IBreakpoint[] bs = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints("org.eclipse.cdt.debug.core");
		for (IBreakpoint bp : bs) {
			if (bp instanceof CLineBreakpoint && bp.getMarker().getResource().getProject() == project) {
				bps.add((CLineBreakpoint) bp);
			}
		}
		this.center = center;
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
	}

	@Override
	public void breakpointAdded(IBreakpoint bp) {
		if (bp instanceof CLineBreakpoint && bp.getMarker().getResource().getProject() == project) {
			bps.add((CLineBreakpoint) bp);
		}
	}

	@Override
	public void breakpointRemoved(IBreakpoint bp, IMarkerDelta delta) {
		if (bp instanceof CLineBreakpoint && bp.getMarker().getResource().getProject() == project) {
			bps.remove((CLineBreakpoint) bp);
		}

	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {

	}

	public List<CLineBreakpoint> getBps() {
		return bps;
	}
	
}
