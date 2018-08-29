package com.mwos.ebochs.core.vm.vbox;

import java.io.File;
import java.net.Socket;
import java.util.List;

import com.mwos.ebochs.core.model.BP;
import com.mwos.ebochs.core.vm.AbstractVM;
import com.mwos.ebochs.core.vm.IVMProfile;

public class Vbox extends AbstractVM {

	public Vbox(String path, IVMProfile profile) {
		super(path, profile);
	}

	@Override
	public Process run() throws Exception {
		File f = profile.localize();
		if (f == null)
			return null;
		String cmd[] = { path + "\\VirtualBox.exe", f.getAbsolutePath() };
		process = Runtime.getRuntime().exec(cmd);
		return process;
	}

	@Override
	public Process debug() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket getSocket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBp(BP bp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBp(BP bp) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BP> getBp() {
		// TODO Auto-generated method stub
		return null;
	}

}
