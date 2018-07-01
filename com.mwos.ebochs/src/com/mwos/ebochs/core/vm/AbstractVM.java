package com.mwos.ebochs.core.vm;

public abstract class AbstractVM {

	protected String path;
	protected Process process;
	protected IVMProfile profile;

	public AbstractVM(String path, IVMProfile profile) {
		this.path = path;
		this.profile = profile;
	}

	public String getPath() {
		return path;
	}

	public Process getProcess() {
		return process;
	}

	public abstract Process run() throws Exception;

	public abstract Process debug() throws Exception;

	public boolean isAlive() {
		if (process != null && process.isAlive()) {
			return true;
		}
		return false;
	}

	public abstract String getName();
}
