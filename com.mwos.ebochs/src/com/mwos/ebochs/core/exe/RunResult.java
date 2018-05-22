package com.mwos.ebochs.core.exe;

public interface RunResult {
	public int exitValue();

	public String getNormalInfo();

	public String getErrorInfo();

	public String getInfo();
}
