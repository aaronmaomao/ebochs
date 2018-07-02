package com.mwos.ebochs.core.vm;

import java.io.File;

import com.mwos.ebochs.resource.config.entity.OSConfig;

public interface IVMProfile {
	public void init();

	public File localize();
	
	public OSConfig getConfig();
}
