package com.mwos.ebochs.core.build;

import com.mwos.ebochs.core.exe.RunResult;

public class BuildResult {
	private RunResult runResult;
	private boolean result = false;;
	private String msg = "";

	public BuildResult(boolean result, String msg) {
		this.result = result;
		this.msg = msg;
		// TODO Auto-generated constructor stub
	}

	public BuildResult(RunResult runResult) {
		this.runResult = runResult;
		this.result = runResult.exitValue() == 0 ? true : false;
		this.msg = result ? runResult.getNormalInfo() : runResult.getErrorInfo();
	}

	public boolean isSuccess() {
		return result;
	}

	public String getMsg() {
		return msg;
	}

	public String getAllMsg() {
		return runResult == null ? msg : runResult.getInfo();
	}

	public BuildResult merge(BuildResult r) {
		this.result &= r.result;
		this.msg += ("\r\n" + r.msg);
		return this;
	}

	@Override
	public String toString() {
		return this.msg;
	}
}
