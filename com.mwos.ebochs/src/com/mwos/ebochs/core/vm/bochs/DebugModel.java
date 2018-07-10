package com.mwos.ebochs.core.vm.bochs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.model.BP;
import com.mwos.ebochs.core.model.BPModel;
import com.mwos.ebochs.core.model.InfoCenter;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class DebugModel implements  IBreakpointListener {
	private Process process;
	private OSConfig config;
	private BPModel bp;

	public DebugModel(Process process, OSConfig config) {
		this.process = process;
		this.config = config;
		new Thread(new HandlerErr()).start();
		try {
			rec();
			bp = new BPModel(config);
			List<BP> bps = bp.getAllBp();
			for(BP b:bps) {
				this.send("b "+b.getAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		InfoCenter.getInfoCenter().addDebug(this);

	}

	private synchronized String send(String cmd) {
		try {
			process.getOutputStream().write((cmd + "\r\n").getBytes());
			process.getOutputStream().flush();
			return rec();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.destory();
		}
		return null;
	}

	private synchronized String rec() {
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
		
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		
	}
	
	private void destory() {
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		InfoCenter.getInfoCenter().removeDebug(this);
	}
}
