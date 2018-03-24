package com.mwos.ebochs.bochs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;

public class BochsProcess {
	private Process process;
	private Thread inputThread;
	private OutputStream out;

	public BochsProcess(Process process) {
		this.process = process;
		initProcess();
	}

	public boolean runCmd(String cmd) {
		try {
			sendCmd(cmd);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean runCmd(String cmd, BochsAck ack) {
		return false;
	}

	private void initProcess() {
		InputStream eis = process.getErrorStream();
		InputStream ris = process.getInputStream();
		out = process.getOutputStream();
		inputThread = new Thread(new Runnable() {
			@Override
			public void run() {

			}
		});
		Thread errorThread = new Thread(new Runnable() {

			@Override
			public void run() {

			}
		});
	}

	private void sendCmd(String cmd) throws IOException {
		cmd += "\n";
		out.write(cmd.getBytes());
		out.flush();
	}
}