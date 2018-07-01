package com.mwos.ebochs.core.vm.vbox;

import com.mwos.ebochs.core.vm.AbstractVM;
import com.mwos.ebochs.core.vm.IVMProfile;

public class Vbox extends AbstractVM {

	public Vbox(String path, IVMProfile profile) {
		super(path, profile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Process run() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Process debug() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "VirtualBox";
	}

}
