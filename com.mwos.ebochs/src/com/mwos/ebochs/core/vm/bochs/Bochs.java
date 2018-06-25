package com.mwos.ebochs.core.vm.bochs;

import com.mwos.ebochs.core.vm.AbstractVM;
import com.mwos.ebochs.core.vm.IDebugVM;

public class Bochs extends AbstractVM implements IDebugVM{

	public Bochs(String dir) {
		this.setDir(dir);
	}
	
	@Override
	public String getName() {
		return "Bochs";
	}

	@Override
	public Process debug(String arg) throws Exception {
		String exe = this.getDir() + "/bochsdbg.exe -q -f " + arg;
		Process process = Runtime.getRuntime().exec(exe);
		return process;
	}

}
