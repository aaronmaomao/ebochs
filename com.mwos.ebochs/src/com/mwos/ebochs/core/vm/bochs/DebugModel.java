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
import com.mwos.ebochs.core.model.cmd.Cmd;
import com.mwos.ebochs.core.model.cmd.CmdStr;
import com.mwos.ebochs.core.model.cmd.DCmd;
import com.mwos.ebochs.core.model.cmd.NCmd;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class DebugModel implements IBreakpointListener, IInfoListener {
	private Process process;
	private OSConfig config;
	private BPModel bm;

	public DebugModel(Process process, OSConfig config) {
		this.process = process;
		this.config = config;
		new Thread(new HandlerErr()).start();
		try {
			recFromVM();
			bm = new BPModel(config);
			List<BP> bps = bm.getAllBp();
			for (BP b : bps) {
				this.sendToVM(new DCmd(CmdStr.b, b.getAddress()));
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
				destory();
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
			try {
				BP bp = new BP(temp);
				bp.setAddress(bm.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber()));
				if (!bp.getAddress().isEmpty()) {
					this.sendToVM(new DCmd(CmdStr.b, bp.getAddress()));
					this.sendToCenter(new NCmd(CmdStr.AddBP), bp);
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
			try {
				BP bp = new BP(temp);
				bp.setAddress(bm.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber()));
				if (!bp.getAddress().isEmpty()) {
					this.sendToVM(new DCmd(CmdStr.del, bp.getAddress()));
					this.sendToCenter(new NCmd(CmdStr.DelBP), bp);
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
			try {
				BP bp = new BP(temp);
				bp.setAddress(bm.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber()));
				if (!bp.getAddress().isEmpty()) {
					if (temp.isEnabled())
						this.sendToVM(new DCmd(CmdStr.bpe, bp.getAddress()));
					else
						this.sendToVM(new DCmd(CmdStr.bpd, bp.getAddress()));
					this.sendToCenter(new NCmd(CmdStr.ChangedBp), bp);
				}
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.sendToCenter(new NCmd(CmdStr.ChangedBp));
		}

	}

	private void destory() {
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		this.center.removeDebug(this);
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
