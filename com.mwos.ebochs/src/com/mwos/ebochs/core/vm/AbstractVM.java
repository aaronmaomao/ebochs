package com.mwos.ebochs.core.vm;

public abstract class AbstractVM implements IRunVM {

	protected String dir;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	@Override
	public Process run(String arg) throws Exception {
		String exe = this.getDir() + "/bochs.exe -q -f " + arg;
		Process process = Runtime.getRuntime().exec(exe);
		return process;
	}

	public abstract String getName();
}
