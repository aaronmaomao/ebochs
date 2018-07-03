package com.mwos.ebochs.core.vm.bochs;

import java.io.File;
import java.net.Socket;

import com.mwos.ebochs.core.vm.AbstractVM;
import com.mwos.ebochs.core.vm.IVMProfile;

public class Bochs extends AbstractVM {

	private Socket socket;

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
}
