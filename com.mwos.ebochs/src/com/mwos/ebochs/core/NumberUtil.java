package com.mwos.ebochs.core;

public class NumberUtil {
	public static long parseHex(String hex) {
		hex = hex.toLowerCase();
		if (hex.startsWith("0x"))
			hex = hex.substring(2, hex.length());
		long res = 0;
		for (int i = 0; i < hex.length(); i++) {
			int n = 0;
			switch (hex.charAt(i)) {
			case 'a':
				n = 10;
				break;
			case 'b':
				n = 11;
				break;
			case 'c':
				n = 12;
				break;
			case 'd':
				n = 13;
			case 'e':
				n = 14;
				break;
			case 'f':
				n = 15;
				break;
			default:
				n = (hex.charAt(i) - 48);
			}
			res += (n * Math.pow(16, hex.length() - i - 1));
		}
		return res;
	}

	public static boolean isFun(char c) {

		if (c == '_')
			return true;
		if (c == 'L')
			return false;
		if (c >= 97 && c <= 122)
			return true;
		if (c >= 65 && c <= 90)
			return true;
		return false;

	}

	public static String hex8(String addr) {
		if (addr.length() < 10) {
			int len = 10 - addr.length();
			String t = "0x";
			for (int i = 0; i < len; i++) {
				t += "0";
			}
			addr = addr.replace("0x", t);
		}
		return addr;
	}

	public static String toHexStr(long l) {
		return "0x" + Long.toHexString(l);
	}

	public static void main(String[] args) {
		parseHex("00000000");
	}
}
