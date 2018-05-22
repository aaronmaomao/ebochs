package com.mwos.ebochs.core.exe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunnerHandler implements Runnable{

	private InputStream io;
	private String msg="";
	
	public RunnerHandler(InputStream io) {
		// TODO Auto-generated constructor stub
		this.io=io;
	}
	
	@Override
	public void run() {
		BufferedReader br = new BufferedReader(new  InputStreamReader(io));
		String line="";
		
		try {
			while((line=br.readLine())!=null) {
				msg+=(line+"\r\n");
			}
			br.close();
			io.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMsg() {
		return msg;
	}

}
