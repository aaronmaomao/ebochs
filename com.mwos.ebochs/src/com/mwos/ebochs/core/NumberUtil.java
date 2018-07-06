package com.mwos.ebochs.core;

public class NumberUtil {
	public static long parseHex(String hex) {
		hex = hex.toLowerCase().substring(2, hex.length());
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

	public static void main(String[] args) {
		int a = 'Z';
		System.out.println(a);
	}
}
