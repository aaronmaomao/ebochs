package com.mwos.ebochs.core.vm.bochs;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.model.BochsReader;
import com.mwos.ebochs.core.model.InfoCmd;
import com.mwos.ebochs.core.model.handler.BP;
import com.mwos.ebochs.core.vm.AbstractVM;
import com.mwos.ebochs.core.vm.IVMProfile;

public class Bochs extends AbstractVM {

	private Socket socket;
	private List<BP> bps = new ArrayList<>();

	public Bochs(String path, IVMProfile profile) {
		super(path, profile);
	}

	@Override
	public Process run() throws Exception {
		File f = profile.localize();
		if (f == null)
			return null;
		String cmd[] = { path + "\\bochsrunner.exe", path + "/bochs.exe", "-q", "-f", f.getAbsolutePath(), "8886" };
		process = Runtime.getRuntime().exec(cmd);
		return process;
	}

	@Override
	public Process debug() throws Exception {
		File f = profile.localize();
		if (f == null)
			return null;
		String cmd[] = { path + "\\bochsrunner.exe", path + "\\bochsdbg.exe", "-q", "-f", f.getAbsolutePath(), "8886" };
		process = Runtime.getRuntime().exec(cmd);
		socket = new Socket("localhost", 8886);
		BochsReader br = new BochsReader(this.getSocket().getInputStream());
		br.readResult();
		return process;
	}

	@Override
	public String getName() {
		return "Bochs";
	}

	@Override
	public Socket getSocket() {
		if (socket != null && !socket.isClosed())
			return socket;
		return null;
	}

	@Override
	public void addBp(BP bp) {
		try {
			String res = this.sendCmd("bp " + bp.getAddress() + "\r\n");
			bps.add(bp);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<BP> getBp() {
		// TODO Auto-generated method stub
		return bps;
	}

	@Override
	public void removeBp(BP bp) {
		try {
			String res = this.sendCmd("bp " + bp.getAddress() + "\r\n");
			bps.add(bp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
