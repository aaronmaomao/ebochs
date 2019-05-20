package com.mwos.editor.bin.shell;

public class MacRunner extends ShellRunner {

	public void addPathEnvp(String value) {
		String path = processBuilder.environment().get("PATH");
		if (path == null) {
			path = "";
		}
		path += (":" + value);
		processBuilder.environment().put("PATH", path);
	}
	
	public String getPathEnvp(){
		return this.processBuilder.environment().get("PATH");
	}

	@Override
	public String startCmd() {
		// TODO Auto-generated method stub
		return null;
	}

}
