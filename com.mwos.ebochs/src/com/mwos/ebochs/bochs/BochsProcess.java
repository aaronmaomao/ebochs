package com.mwos.ebochs.bochs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

public class BochsProcess {
	private Process process;
	private Thread inputThread;
	private OutputStream out;
	private InputStream in;

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
		if(ack!=null) {
			inputThread.suspend();
			runCmd(cmd);
			String content = getContent();
			inputThread.resume();
			ack.ackContents(content);
		}
		return false;
	}

	private void initProcess() {
		InputStream eis = process.getErrorStream();
		in = process.getInputStream();
		out = process.getOutputStream();
		inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				print(in, false);
			}
		});
		Thread errorThread = new Thread(new Runnable() {
			@Override
			public void run() {
				print(eis, true);
			}
		});
		inputThread.start();
		errorThread.start();
	}
	
	private String getContent() {
		String content="";
		Reader reader = new InputStreamReader(in, Charset.forName("utf-8"));
		BufferedReader br = new BufferedReader(reader);
		String line="";
		try {
			while(!(line = br.readLine()).equals("")) {
				content +=(line+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	private void sendCmd(String cmd) throws IOException {
		cmd += "\n";
		out.write(cmd.getBytes());
		out.flush();
	}
	
	private void print(InputStream in, boolean error) {
		Reader reader = new InputStreamReader(in, Charset.forName("utf-8"));
		BufferedReader br = new BufferedReader(reader);
		String line="";
		try {
			while(true) {
				line = br.readLine();
				if(error) {
					System.err.println(line);
				}else {
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}