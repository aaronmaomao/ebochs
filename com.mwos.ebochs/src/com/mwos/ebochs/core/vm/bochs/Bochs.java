package com.mwos.ebochs.core.vm.bochs;

import com.mwos.ebochs.resource.config.entity.OSConfig;

public class Bochs extends BaseBochsVM {
	private OSConfig config;

	public Bochs(String dir,OSConfig config) {
		this.config = config;
	}

	private void init() {

	}

}
