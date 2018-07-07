package com.mwos.ebochs.core.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.NumberUtil;
import com.mwos.ebochs.resource.config.entity.OSConfig;

public class AsmAdapt {

	private List<_AsmFun> _asmFuns;
	private List<AsmFun> asmFuns;

	private String src;
	private OSConfig config;
	private DomMap domMap;

	private Map<String, String> _map;

	public AsmAdapt(String src, OSConfig config, DomMap domMap) {
		this.src = src;
		this.config = config;
		this.domMap = domMap;
		_asmFuns = new ArrayList<>();
		asmFuns = new ArrayList<>();
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws IOException {
		String name = FileUtil.getFileName(src, false);
		BufferedReader br1 = new BufferedReader(new FileReader(new File(config.getProject().getLocationURI().getPath() + "/obj/" + name + ".lst")));
		String line1 = "";
		while ((line1 = br1.readLine()) != null) {
			if (line1.contains("\tGLOBAL\t_")) {
				AsmFun asmFun = new AsmFun(br1, domMap);
				asmFuns.add(asmFun);
			}

		}

		if (!src.endsWith(".asm")) {

			BufferedReader br2 = new BufferedReader(new FileReader(new File(config.getProject().getLocationURI().getPath() + "/obj/" + name + "._asm")));
			String line2 = "";

			while ((line2 = br2.readLine()) != null) {
				if (line2.startsWith(".globl _")) {
					_AsmFun _asmFun = new _AsmFun(br2);
					_asmFuns.add(_asmFun);
				}
			}
		}
	}

	public Map<String, String> getMap() {
		if (_map == null) {
			_map = new LinkedHashMap<>();
			if (src.endsWith(".asm")) {
				for (AsmFun asmFun : asmFuns) {
					for (String str : asmFun.getAddr()) {
						String temp[] = str.split(":");
						_map.put(temp[0], temp[1]);
					}
				}
			} else {
				for (int i = 0; i < asmFuns.size(); i++) {
					_AsmFun _asmFun = _asmFuns.get(i);
					AsmFun asmFun = asmFuns.get(i);
					if (asmFun.getAbsAddr() == 0)
						break;

					for (int j = 0; j < asmFun.getAddr().size(); j++) {
						String addr_line = asmFun.getAddr().get(j);
						Integer cLine = _asmFun.getLineNum().get(j);
						_map.put(addr_line.split(":")[0], "" + cLine);
					}

				}
			}
		}
		return _map;
	}

}

class AsmFun {
	String funName;
	List<String> addr_line;
	private long relAddr;
	private long absAddr;
	DomMap domMap;

	public AsmFun(BufferedReader br1, DomMap domMap) {
		addr_line = new ArrayList<>();
		this.domMap = domMap;
		init(br1);
	}

	private void init(BufferedReader br1) {
		try {
			String temp = "";
			temp = br1.readLine();
			funName = temp.substring(49, temp.lastIndexOf(":"));
			relAddr = NumberUtil.parseHex(temp.substring(8, 16));
			if (domMap.getAddr(funName) == null)
				absAddr = 0;
			else
				absAddr = NumberUtil.parseHex(domMap.getAddr(funName));
			while ((temp = br1.readLine()) != null) {
				if (temp.contains("ALIGN\t2")) {
					break;
				}
				long lineAddr = NumberUtil.parseHex(temp.substring(8, 16)) - relAddr + absAddr;
				addr_line.add("0x" + lineAddr + ":" + temp.substring(0, 7).trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getAddr() {
		return addr_line;
	}

	public long getAbsAddr() {
		return absAddr;
	}
}

class _AsmFun {
	String funName;
	List<Integer> lineNum;
	private int funLine;

	public _AsmFun(BufferedReader br2) {
		// TODO Auto-generated constructor stub
		lineNum = new ArrayList<>();
		init(br2);
	}

	private void init(BufferedReader br2) {
		int lineN;
		try {
			String temp = "";
			temp = br2.readLine();
			funName = temp.substring(0, temp.indexOf(":"));
			temp = br2.readLine();
			funLine = Integer.parseInt(temp.substring(57, temp.lastIndexOf(":"))) - 1;
			lineN = funLine;
			while (!(temp = br2.readLine()).startsWith("\t.def\t.ef;")) {
				if (temp.startsWith("\t.ln")) {
					lineN = Integer.parseInt(temp.substring(7, temp.length())) + funLine;
					continue;
				}
				if (temp.startsWith("\t."))
					continue;
				lineNum.add(lineN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Integer> getLineNum() {
		return lineNum;
	}
}
