package com.mwos.ebochs.core.exe;

import java.io.File;
import java.io.IOException;

public class EXERunner {

	public static RunResult run(String exe, String dir) throws IOException, InterruptedException {

		Process process = Runtime.getRuntime().exec(exe,new String[] {},new File(dir));
		RunnerHandler normal = new RunnerHandler(process.getInputStream());
		RunnerHandler err = new RunnerHandler(process.getErrorStream());
		new Thread(normal).start();
		new Thread(err).start();
		process.getOutputStream().close();
		process.waitFor();
		return new RunResult() {

			@Override
			public String getNormalInfo() {
				// TODO Auto-generated method stub
				return normal.getMsg();
			}

			@Override
			public String getInfo() {
				// TODO Auto-generated method stub
				return err.getMsg() + normal.getMsg();
			}

			@Override
			public String getErrorInfo() {
				// TODO Auto-generated method stub
				return err.getMsg();
			}

			@Override
			public int exitValue() {
				// TODO Auto-generated method stub
				return process.exitValue();
			}
		};
	}

	public static void runNoResult(String exe, String dir) throws IOException, InterruptedException {
		Runtime.getRuntime().exec(exe,new String[] {},new File(dir));
	}
}
