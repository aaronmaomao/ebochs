package com.mwos.ebochs.bochs;

import com.mwos.ebochs.Activator;

public class BochsConfigure {

	private static BochsConfigure config = null;

	private String bochsPath;
	private String bochsDbgPath;
	private String bxrcPath;

	public String getBochsPath() {
		return bochsPath;
	}

	public void setBochsPath(String bochsPath) {
		this.bochsPath = bochsPath;
	}

	public String getBochsDbgPath() {
		return bochsDbgPath;
	}

	public void setBochsDbgPath(String bochsDbgPath) {
		this.bochsDbgPath = bochsDbgPath;
	}

	public String getBxrcPath() {
		return bxrcPath;
	}

	public void setBxrcPath(String bxrcPath) {
		this.bxrcPath = bxrcPath;
	}

	private static BochsConfigure getDefault(String bxrcPath) {
		if (config == null) {
			config = new BochsConfigure();
			config.setBochsDbgPath(Activator.getDefault().getPreferenceStore().getString("bochsdbg_path"));
			config.setBochsPath(Activator.getDefault().getPreferenceStore().getString("bochs_path"));
			config.setBxrcPath(bxrcPath);
		}
		return config;
	}
}
