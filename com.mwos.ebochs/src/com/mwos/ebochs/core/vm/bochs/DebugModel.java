package com.mwos.ebochs.core.vm.bochs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.cdt.ui.CDTUITools;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.NumberUtil;
import com.mwos.ebochs.core.model.BP;
import com.mwos.ebochs.core.model.BPModel;
import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.cmd.Cmd;
import com.mwos.ebochs.core.model.cmd.CmdStr;
import com.mwos.ebochs.core.model.cmd.DCmd;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class DebugModel implements IBreakpointListener, IInfoListener {
	private Process process;
	private OSConfig config;
	private BPModel bm;
	private int Status = 0;// 0 terminal,1 suspend 2, run

	IMarker mark = null;

	public DebugModel(Process process, OSConfig config) {
		this.process = process;
		this.config = config;
		new Thread(new HandlerErr()).start();
		try {
			recFromVM();
			bm = new BPModel(config);
			List<BP> bps = bm.getAllBp();
			for (BP b : bps) {
				this.sendToVM(new DCmd(CmdStr.b, NumberUtil.toHexStr(b.getAddress())));
			}

			this.sendToVM(new DCmd(CmdStr.b, "0x7c00"));
			this.sendToVM(new DCmd(CmdStr.c));

			String local = bm.getLocal(0x7c00);
			adjustMark(local.split(":")[0], local.split(":")[1]);

		} catch (Exception e) {
			e.printStackTrace();
		}

		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);

	}

	public synchronized String sendToVM(Cmd cmd) {
		try {
			process.getOutputStream().write((cmd.toString() + "\r\n").getBytes());
			process.getOutputStream().flush();
			String rec = recFromVM();
			this.sendToCenter(cmd.getCmd(), rec);
			return rec;
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
				Long addr = bm.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber());
				if (addr == null) {
					breakpoint.delete();
					return;
				}
				bp.setAddress(addr);
				this.sendToVM(new DCmd(CmdStr.b, NumberUtil.toHexStr(bp.getAddress())));
				this.sendToCenter(CmdStr.AddBP, bp);
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
				Long addr = bm.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber());
				if (addr == null) {
					breakpoint.delete();
					return;
				}
				this.sendToVM(new DCmd(CmdStr.del, NumberUtil.toHexStr(bp.getAddress())));
				this.sendToCenter(CmdStr.DelBP, bp);
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
				Long addr = bm.getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber());
				if (addr == null) {
					breakpoint.delete();
					return;
				}
				if (temp.isEnabled())
					this.sendToVM(new DCmd(CmdStr.bpe, NumberUtil.toHexStr(bp.getAddress())));
				else
					this.sendToVM(new DCmd(CmdStr.bpd, NumberUtil.toHexStr(bp.getAddress())));
				this.sendToCenter(CmdStr.ChangedBp, bp);
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private void destory() {
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		if (mark != null) {
			try {
				mark.delete();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sendToCenter(CmdStr.DestoryDM, this);
	}

	@Override
	public void notify(Object info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notify(String cmd, Object info) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		return config.getName();
	}

	public int getStatus() {
		return Status;
	}

	public String stepOver() {
		String rec = this.sendToVM(new DCmd(CmdStr.n));
		if (rec != null) {
			if (rec.contains("Next at t=")) {
				String debugRec = rec.substring(rec.indexOf("(0) [0x"), rec.indexOf("(unk.")).trim();
				String addr = debugRec.substring(5, debugRec.indexOf("]"));
				String local = bm.getLocal(NumberUtil.parseHex((addr)));
				if (local != null) {
					adjustMark(local.split(":")[0], local.split(":")[1]);
				}
			}
		}
		return rec;
	}

	public String terminal() {
		String rec = this.sendToVM(new DCmd(CmdStr.Terminate));
		if (rec != null) {
			if (this.mark != null) {
				try {
					mark.delete();
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return rec;
	}

	public String contin() {
		String rec = this.sendToVM(new DCmd(CmdStr.c));
		if (rec != null) {
			if (rec.contains("Next at t=")) {
				String debugRec = rec.substring(rec.indexOf("(0) [0x"), rec.indexOf("(unk.")).trim();
				String addr = debugRec.substring(5, debugRec.indexOf("]"));
				String local = bm.getLocal(NumberUtil.parseHex((addr)));
				if (local != null) {
					adjustMark(local.split(":")[0], local.split(":")[1]);
				}
			}
		}
		return rec;
	}

	public String stepInto() {
		String rec = this.sendToVM(new DCmd(CmdStr.s));
		if (rec != null) {
			if (rec.contains("Next at t=")) {
				String debugRec = rec.substring(rec.indexOf("(0) [0x"), rec.indexOf("(unk.")).trim();
				String addr = debugRec.substring(5, debugRec.indexOf("]"));
				String local = bm.getLocal(NumberUtil.parseHex((addr)));
				if (local != null) {
					adjustMark(local.split(":")[0], local.split(":")[1]);
				}
			}
		}
		return rec;
	}

	private void adjustMark(String file, String line) {
		try {
			if (mark != null) {
				mark.delete();
				mark = null;
			}
			mark = config.getProject().getFile(file).createMarker("com.ebochs.DebugMarker");

			ICProject cproject = CoreModel.getDefault().getCModel().getCProject(config.getProject().getName());
			ICElement element = cproject.findElement(config.getProject().getFile(file).getProjectRelativePath());
			CDTUITools.openInEditor(element);

			mark.setAttribute(IMarker.LINE_NUMBER, Integer.valueOf(line));
			// mark.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<String> getCare() {
		// TODO Auto-generated method stub
		return null;
	}
}
