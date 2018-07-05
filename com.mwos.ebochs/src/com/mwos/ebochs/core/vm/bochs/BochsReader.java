package com.mwos.ebochs.core.vm.bochs;

import java.io.IOException;
import java.io.InputStream;

public class BochsReader {
	private InputStream inputStream;

	public BochsReader(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String readResult() throws IOException {
		String str = "";
		int len = -1;
		while (true) {
			len = inputStream.read();
			if (len < 0)
				break;
			str += (char) len;
			if(str.matches("[\\s\\S]*\\<bochs\\:\\d+\\>\\s$")) {
				return str;
			}
		}
		return str;
	}
	
//	public static void main(String[] args) {
//		System.out.println("eflags 0x00000002: id vip vif ac vm rf nt IOPL=0 of df if tf sf zf af pf cf\r\n<bochs:3> ".matches("[\\s\\S]*\\<bochs\\:\\d+\\>\\s$"));
//	}
}
