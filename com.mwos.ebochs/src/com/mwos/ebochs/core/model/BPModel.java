package com.mwos.ebochs.core.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.NumberUtil;
import com.mwos.ebochs.core.model.DomAsmSrc.AsmFun;
import com.mwos.ebochs.core.model.handler.BP;
import com.mwos.ebochs.resource.config.entity.OSConfig;

public class BPModel {
	private OSConfig config;
	private DomMap domMap;

	private Map<String, String> _mapMbr = new HashMap<>();
	private Map<String, String> _mapCore = new HashMap<>();
	private Map<String, String> _mapBootLoader = new HashMap<>();

	private List<String> hasParsed = new ArrayList<>();

	public BPModel(OSConfig config) {
		this.config = config;
		domMap = new DomMap(config.getProject().getLocationURI().getPath() + "/obj/core.map");
	}

	public List<BP> getAllBp() throws CoreException {
		List<BP> bps = new ArrayList<>();
		IBreakpoint[] bs = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints("org.eclipse.cdt.debug.core");
		for (IBreakpoint bp : bs) {
			if (bp instanceof CLineBreakpoint && bp.getMarker().getResource().getProject() == config.getProject()) {
				CLineBreakpoint temp = (CLineBreakpoint) bp;
				BP b = new BP();
				b.setEnable(temp.isEnabled());
				b.setAddress(getAddr(temp.getFileName() + ":" + temp.getLineNumber()));
				b.setLocaltion(temp.getFileName() + ":" + temp.getLineNumber());
				b.setFunction(temp.getFunction());
				bps.add(b);
			}
		}
		return bps;
	}

	public String getLocal(String addr) {
		long a = NumberUtil.parseHex(addr);
		if (a >= 0x7c00 && a < 0x7c00 + 512) {
			return _mapMbr.get(addr);
		}

		if (a >= 0x280000) {
			return _mapCore.get(addr);
		}

		if (a > 0xc200) {
			return _mapBootLoader.get(addr);
		}
		return null;
	}

	public String getAddr(String local) {
		String temp[] = local.split(":");
		Map<String, String> _source;
		if (temp[0].equals("src/mbr.asm")) {
			if (!hasParsed.contains(temp[0])) {
				parseMbrAsm();
			}
			_source = _mapMbr;
		} else if (temp[0].equals("src/bootloader.asm")) {
			if (!hasParsed.contains(temp[0])) {
				parseMbrAsm();
			}
			_source = _mapBootLoader;
		} else {
			if (!hasParsed.contains(temp[0])) {
				parseCoreC(temp[0]);
			}
			_source = _mapCore;
		}

		for (String addr : _source.keySet()) {
			String loc = _source.get(addr);
			if (loc.equals(local))
				return addr;
		}
		return null;
	}

	private void parseMbrAsm() {
		DomAsmSrc mbr = new DomAsmSrc("src/mbr.asm", config);
		hasParsed.add("src/mbr.asm");
		_mapMbr.clear();
		for (AsmFun fun : mbr.getFuns()) {
			for (String addr : fun.getAddr_line().keySet()) {
				_mapMbr.put(addr, "src/mbr.asm:" + fun.getAddr_line().get(addr));
			}
		}
	}

	private void parseBootAsm() {
		DomAsmSrc boot = new DomAsmSrc("src/bootloader.asm", config);
		hasParsed.add("src/bootloader.asm");
		_mapBootLoader.clear();
		for (AsmFun fun : boot.getFuns()) {
			for (String addr : fun.getAddr_line().keySet()) {
				_mapBootLoader.put(addr, "src/mbr.asm:" + fun.getAddr_line().get(addr));
			}
		}
	}

	private void parseCoreC(String src) {
		AsmAdapt adapt = new AsmAdapt(src, config, domMap);
		hasParsed.add(src);
		Map<String, String> addrs = adapt.getMap();
		for (String addr : addrs.keySet()) {
			_mapCore.put(addr, src + ":" + addrs.get(addr));
		}

	}

}

class DomCSrc {
	private String src;
	private OSConfig config;
	private DomAsmSrc domAsm;

	private Map<String, String> _dom;

	public DomCSrc(String src, OSConfig config, DomMap domMap) {
		this.src = src;
		this.config = config;
		_dom = new LinkedHashMap<>();
		domAsm = new DomAsmSrc(src, config);
	}

	private void init() {

	}

}

class DomAsmSrc {
	private String src;
	private OSConfig config;

	private List<AsmFun> funs;

	public List<AsmFun> getFuns() {
		return funs;
	}

	public DomAsmSrc(String src, OSConfig config) {
		// TODO Auto-generated constructor stub
		this.src = src;
		this.config = config;
		funs = new ArrayList<>();
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws IOException {
		String name = FileUtil.getFileName(src, false);
		String path = "";
		if (src.endsWith(".asm")) {
			path = config.getProject().getLocationURI().getPath() + "/obj/" + name + ".lst";
		} else if (src.endsWith(".c")) {
			path = config.getProject().getLocationURI().getPath() + "/obj/" + name + ".lst";
		}
		File lst = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(lst));
		String line = "";
		AsmFun fun = new AsmFun("null", "0x00000000", "0");
		while ((line = br.readLine()) != null) {
			if (line.trim().isEmpty())
				continue;
			if (NumberUtil.isFun(line.charAt(49))) {
				fun = new AsmFun(line.substring(49, line.length()), line.substring(8, 16).trim(), line.substring(8, 16).trim());
				continue;
			}

			String lineNum = line.substring(0, 7).trim();
			String relAddr = line.substring(8, 16).trim();
			if (lineNum.isEmpty() || relAddr.isEmpty())
				continue;
			fun.add(relAddr, lineNum);
		}
		br.close();
	}

	class AsmFun {
		private String name = "null";
		private String addr;
		private String line;

		private Map<String, String> addr_line;

		public AsmFun(String name, String addr, String line) {
			// TODO Auto-generated constructor stub
			this.name = name;
			this.addr = addr;
			this.line = line;
			addr_line = new LinkedHashMap<>();
		}

		public void add(String addr, String line) {
			this.addr_line.put(addr, line);
		}

		public Map<String, String> getAddr_line() {
			return addr_line;
		}

	}
}

class DomMap {
	private String file;
	private Map<String, String> _dom;

	public DomMap(String file) {
		this.file = file;
		_dom = new LinkedHashMap<>();
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(file)));
		String line = "";
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.isEmpty())
				break;
			String temp[] = line.split(":");
			if (temp.length != 2)
				break;
			if (temp[0].startsWith("0x") && temp[1].startsWith("_")) {
				_dom.put(temp[1], temp[0]);
			}
		}
		br.close();
	}

	public String getAddr(String fun) {
		return _dom.get(fun);
	}
}
