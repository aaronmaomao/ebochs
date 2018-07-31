package com.mwos.ebochs.ui.view.model.register;

import java.util.HashMap;
import java.util.Map;

public class RegisterParse {
	public static Map<String, String> parseReg(String rec) {
		Map<String, String> _map = new HashMap<>();
		_map.put("eax", "0x" + rec.substring(rec.indexOf("rax") + 14, rec.indexOf("rax") + 22));
		_map.put("ecx", "0x" + rec.substring(rec.indexOf("rcx") + 14, rec.indexOf("rcx") + 22));
		_map.put("edx", "0x" + rec.substring(rec.indexOf("rdx") + 14, rec.indexOf("rdx") + 22));
		_map.put("ebx", "0x" + rec.substring(rec.indexOf("rbx") + 14, rec.indexOf("rbx") + 22));
		_map.put("esp", "0x" + rec.substring(rec.indexOf("rsp") + 14, rec.indexOf("rsp") + 22));
		_map.put("ebp", "0x" + rec.substring(rec.indexOf("rbp") + 14, rec.indexOf("rbp") + 22));
		_map.put("esi", "0x" + rec.substring(rec.indexOf("rsi") + 14, rec.indexOf("rsi") + 22));
		_map.put("edi", "0x" + rec.substring(rec.indexOf("rdi") + 14, rec.indexOf("rdi") + 22));
		_map.put("eip", "0x" + rec.substring(rec.indexOf("rip") + 14, rec.indexOf("rip") + 22));
		_map.put("eflags", "0x" + rec.substring(rec.indexOf("eflags") + 7, rec.indexOf("eflags") + 17));
		return _map;
	}
	
	public static Map<String, String> parseSReg(String rec) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public static void main(String[] args) {
		String rec = "";
		parseReg(rec);
	}



	
}
