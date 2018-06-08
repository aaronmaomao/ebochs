package com.mwos.ebochs.core;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	// ��ȡ�쳣��Ϣ
	public static String getMsg(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
