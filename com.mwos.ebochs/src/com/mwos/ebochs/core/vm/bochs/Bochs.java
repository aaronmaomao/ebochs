package com.mwos.ebochs.core.vm.bochs;

import java.io.File;

import com.mwos.ebochs.core.vm.AbstractVM;
import com.mwos.ebochs.core.vm.IVMProfile;

public class Bochs extends AbstractVM {

	public Bochs(String path, IVMProfile profile) {
		super(path, profile);
	}

	@Override
	public Process run() throws Exception {
		File f = profile.localize();
		if(f==null)
			return null;
		String exe = this.path + "/bochs.exe -q -f " + f.getAbsolutePath();
		process = Runtime.getRuntime().exec(exe);
		return process;
	}

	@Override
	public Process debug() throws Exception {
		File f = profile.localize();
		if(f==null)
			return null;
		String exe = this.path + "/bochsdbg.exe -q -f " + f.getAbsolutePath();
		process = Runtime.getRuntime().exec(exe);
		return process;
	}

	@Override
	public String getName() {
		return "Bochs";
	}
}
