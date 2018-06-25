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
	public Process debug(String arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
