package com.mwos.editor.bin.service;

import java.io.File;
import java.io.FileInputStream;

public class Utils {
	/**
	 * 返回较小的数
	 */
	public static int smaller(int n1, int n2) {
		if (n1 < n2) {
			return n1;
		} else {
			return n2;
		}
	}

	/**
	 * 返回较大的
	 */
	public static int bigger(int n1, int n2) {
		if (n1 > n2) {
			return n1;
		} else {
			return n2;
		}
	}

	public static String getHexString(int value, int length) {
		String temp = Integer.toHexString(value);
		if (temp.length() < length) {
			int temLen = temp.length();
			for (int i = 0; i < (length - temLen); i++) {
				temp = ("0" + temp);
			}
		}
		return temp.toUpperCase();
	}
	
	

	public static byte[] readFile(File path) {
		return readFile(path, 0, (int) path.length());
	}

	public static byte[] readFile(File path, int offset, int length) {
		if (path.length() < (offset + length)) {
			length = (int) (path.length() - offset);
		}
		byte data[] = new byte[length];
		try {
			FileInputStream fi = new FileInputStream(path);
			fi.skip(offset);
			fi.read(data);
			fi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static void main(String[] args) {
		String a="asdasd";
	}
}
