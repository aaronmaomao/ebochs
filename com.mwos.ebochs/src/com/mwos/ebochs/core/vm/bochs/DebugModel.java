package com.mwos.ebochs.core.vm.bochs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.model.BP;
import com.mwos.ebochs.core.model.BPModel;
import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.InfoCenter;
import com.mwos.ebochs.core.model.cmd.Cmd;
import com.mwos.ebochs.core.model.cmd.CmdFactory;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class DebugModel implements IBreakpointListener, IInfoListener {
	private Process process;
	private OSConfig config;
	private BPModel bp;

	public DebugModel(Process process, OSConfig config) {
		this.process = process;
		this.config = config;
		new Thread(new HandlerErr()).start();
		try {
			recFromVM();
			bp = new BPModel(config);
			List<BP> bps = bp.getAllBp();
			for (BP b : bps) {
				this.sendToVM(new Cmd("b", b.getAddress()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
	}

	public synchronized String sendToVM(Cmd cmd) {
		try {
			process.getOutputStream().write((cmd + "\r\n").getBytes());
			process.getOutputStream().flush();
			return recFromVM();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.destory();
		}
		return null;
	}

	private synchronized String recFromVM() {
		BochsReader br = new BochsReader(process.getInputStream());
		String res;
		try {
			res = br.readResult();
			ConsoleFactory.outMsg(res + "\r\n", config.getProject());
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.destory();
		}
		return null;
	}

	private class HandlerErr implements Runnable {

		@Override
		public void run() {
			InputStream err = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(err));
			String line = "";
			try {
				while ((line = br.readLine()) != null) {
					ConsoleFactory.outErrMsg(line + "\r\n", config.getProject());
				}
				err.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		if (breakpoint instanceof CLineBreakpoint && breakpoint.getMarker().getResource().getProject() == config.getProject()) {
			CLineBreakpoint temp = (CLineBreakpoint) breakpoint;
			String addr;
			try {
				addr = bp.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber());
				if (addr != null) {
					this.sendToVM(new Cmd("b", addr));
					this.sendToCenter(new Cmd("AddBP"));
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		if (breakpoint instanceof CLineBreakpoint && breakpoint.getMarker().getResource().getProject() == config.getProject()) {
			CLineBreakpoint temp = (CLineBreakpoint) breakpoint;
			String addr;
			try {
				addr = bp.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber());
				if (addr != null) {
					this.sendToVM(new Cmd("del", addr));
					this.sendToCenter(new Cmd("DelBP"));
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		if (breakpoint instanceof CLineBreakpoint && breakpoint.getMarker().getResource().getProject() == config.getProject()) {
			CLineBreakpoint temp = (CLineBreakpoint) breakpoint;
			String addr;
			try {
				addr = bp.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber());
				if (addr != null) {
					if (temp.isEnabled())
						this.sendToVM(new Cmd("bpe", addr));
					else
						this.sendToVM(new Cmd("bpd", addr));
				}
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.sendToCenter(new Cmd("ChangedBp"));
		}

	}

	private void destory() {
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
	}

	@Override
	public void notify(Object info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notify(String cmd, Object info) {
		// TODO Auto-generated method stub

	}
}
