package com.mwos.ebochs.core.vm.bochs;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
		String cmd[] = { path + "\\bochs.exe", "-q", "-f", f.getAbsolutePath() };
		process = Runtime.getRuntime().exec(cmd);
		return process;
	}

	@Override
	public Process debug() throws Exception {
		File f = profile.localize();
		if (f == null)
			return null;
		String cmd[] = { path + "\\bochsdbg.exe", "-q", "-f", f.getAbsolutePath() };
		process = Runtime.getRuntime().exec(cmd);
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
