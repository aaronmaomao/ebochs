package com.mwos.editor.bin.shell;

public class WinRunner extends ShellRunner {

	
	public void addPathEnvp(String value) {
		String path = processBuilder.environment().get("Path");
		if (path == null) {
			path = "";
		}
		path += (";" + value + ";");
		processBuilder.environment().put("Path", path);
	}
	
	public String getPathEnvp(){
		return this.processBuilder.environment().get("Path");
	}
	
	@Override
	public String startCmd() {
		return null;
	}
}
