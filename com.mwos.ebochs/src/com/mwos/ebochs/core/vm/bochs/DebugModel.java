package com.mwos.ebochs.core.vm.bochs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class DebugModel {
	private Process process;
	private OSConfig config;

	public DebugModel(Process process, OSConfig config) {
		this.process = process;
		this.config = config;
		new Thread(new HandlerErr()).start();
		try {
			rec();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized String send(String cmd) throws IOException {
		process.getOutputStream().write((cmd + "\r\n").getBytes());
		process.getOutputStream().flush();
		return rec();
	}

	public synchronized String rec() throws IOException {
		BochsReader br = new BochsReader(process.getInputStream());
		String res;
		res = br.readResult();
		ConsoleFactory.outMsg(res + "\r\n", config.getProject());
		return res;
	}


	private class HandlerErr implements Runnable {

		@Override
		public void run() {
			InputStream err = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(err));
			String line = "";
			try {
				while ((line = br.readLine()) != null) {
					ConsoleFactory.outErrMsg(line + "\r\n", config.getProject());
				}
				err.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public OSConfig getConfig() {
		return config;
	}

	public void setConfig(OSConfig config) {
		this.config = config;
	}
}
