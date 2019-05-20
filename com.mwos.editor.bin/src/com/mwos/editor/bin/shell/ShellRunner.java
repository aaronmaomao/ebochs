package com.mwos.editor.bin.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public abstract class ShellRunner {

	protected ProcessBuilder processBuilder;
	protected String charset = "utf-8";

	public ShellRunner() {
		processBuilder = new ProcessBuilder();
		processBuilder.redirectErrorStream(true);
	}

	public ShellRunner(String[] cmds) {
		this();
		processBuilder = processBuilder.command(cmds);
	}

	public void addCmd(String cmd) {
		this.processBuilder.command().add(cmd);
	}

	public void addCmds(String[] cmds) {
		for (String cmd : cmds) {
			addCmd(cmd);
		}
	}

	public void setCmds(List<String> cmds) {
		this.processBuilder.command(cmds);
	}

	public void setCmds(String[] cmds) {
		this.processBuilder.command(cmds);
	}

	public String[] getCmd() {
		return this.processBuilder.command().toArray(new String[] {});
	}

	public void addEnvp(String var, String value) {
		this.processBuilder.environment().put(var, value);
	}

	public abstract void addPathEnvp(String value);

	public void setWorkDir(String dir) {
		this.processBuilder.directory(new File(dir));
	}

	public String getWorkDir() {
		return this.processBuilder.directory() == null ? System.getProperty("user.dir") : this.processBuilder.directory().getAbsolutePath();
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Process exec(RunnerOutput output, boolean wait) throws Exception {
		Process p = this.processBuilder.start();
		new Thread(() -> {
			InputStream in = p.getInputStream();
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
				String aline = "";
				while ((aline = br.readLine()) != null) {
					output.output(aline, false);
				}
				br.close();
				in.close();
				p.getInputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			InputStream errIn = p.getErrorStream();
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(errIn, "GBK"));
				String aline = "";
				while ((aline = br.readLine()) != null) {
					output.output(aline, true);
				}
				br.close();
				errIn.close();
				p.getErrorStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		if (wait) {
			p.waitFor();
		}
		return p;
	}

	public String getCmdline() {
		String cmdline = "";
		for (String cmd : this.processBuilder.command()) {
			cmdline += (cmd+" ");
		}
		return cmdline;
	}

	public static ShellRunner getRunner() {
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			return new WinRunner();
		} else {
			return new MacRunner();
		}
	}

	public abstract String getPathEnvp();

	public Map<String, String> getEnvp() {
		return this.processBuilder.environment();
	}

	public abstract String startCmd();
}
