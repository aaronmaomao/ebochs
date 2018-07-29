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
import java.util.Set;

import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.NumberUtil;
import com.mwos.ebochs.resource.config.entity.OSConfig;

public class BPModel implements IInfoListener {
	private OSConfig config;
	private DomMap domMap;

	private Map<Long, String> _mapMbr = new LinkedHashMap<>();
	private Map<Long, String> _mapCore = new LinkedHashMap<>();
	private Map<Long, String> _mapBootLoader = new LinkedHashMap<>();

	private List<String> hasParsed = new ArrayList<>();

	public BPModel(OSConfig config) {
		this.config = config;
		domMap = new DomMap(config.getProject().getLocationURI().getPath() + "/obj/core.map");
		parseMbrAsm();
		parseBootAsm();
	}

	public List<BP> getAllBp() throws CoreException {
		List<BP> bps = new ArrayList<>();
		IBreakpoint[] bs = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints("org.eclipse.cdt.debug.core");
		for (IBreakpoint bp : bs) {
			if (bp instanceof CLineBreakpoint && bp.getMarker().getResource().getProject() == config.getProject()) {
				CLineBreakpoint temp = (CLineBreakpoint) bp;

				Long addr = getAddr(temp.getMarker().getResource().getProjectRelativePath() + ":" + temp.getLineNumber());
				if (addr == null) {
					temp.delete();
					continue;
				}
				BP b = new BP(temp);
				b.setAddress(addr);
				bps.add(b);
			}
		}
		return bps;
	}

	public String getLocal(long addr) {
		if (addr >= 0x7c00 && addr < 0x7c00 + 512) {
			return _mapMbr.get(addr);
		}

		if (addr >= 0x280000) {
			return _mapCore.get(addr);
		}

		if (addr > 0xc200) {
			return _mapBootLoader.get(addr);
		}
		return null;
	}

	public Long getAddr(String local) {
		String temp[] = local.split(":");
		Map<Long, String> _source;
		if (temp[0].equals("src/mbr.asm")) {
			if (!hasParsed.contains(temp[0])) {
				parseMbrAsm();
			}
			_source = _mapMbr;
		} else if (temp[0].equals("src/bootloader.asm")) {
			if (!hasParsed.contains(temp[0])) {
				parseBootAsm();
			}
			_source = _mapBootLoader;
		} else {
			if (!hasParsed.contains(temp[0])) {
				parseCoreC(temp[0]);
			}
			_source = _mapCore;
		}

		for (Long addr : _source.keySet()) {
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
		for (String addr : mbr.getAddr_line().keySet()) {
			_mapMbr.put(NumberUtil.parseHex(addr), "src/mbr.asm:" + mbr.getAddr_line().get(addr));
		}
	}

	private void parseBootAsm() {
		DomAsmSrc boot = new DomAsmSrc("src/bootloader.asm", config);
		hasParsed.add("src/bootloader.asm");
		_mapBootLoader.clear();
		for (String addr : boot.getAddr_line().keySet()) {
			_mapBootLoader.put(NumberUtil.parseHex(addr), "src/bootloader.asm:" + boot.getAddr_line().get(addr));
		}
	}

	private void parseCoreC(String src) {
		AsmAdapt adapt = new AsmAdapt(src, config, domMap);
		hasParsed.add(src);
		Map<String, String> addrs = adapt.getMap();
		for (String addr : addrs.keySet()) {
			_mapCore.put(NumberUtil.parseHex(addr) + 0x280000, src + ":" + addrs.get(addr));
		}
	}

	@Override
	public void notify(Object info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notify(String cmd, Object info) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> getCare() {
		// TODO Auto-generated method stub
		return null;
	}

}

class DomAsmSrc {
	private String src;
	private OSConfig config;

	private Map<String, String> addr_line;

	public DomAsmSrc(String src, OSConfig config) {
		// TODO Auto-generated constructor stub
		this.src = src;
		this.config = config;
		addr_line = new LinkedHashMap<>();
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws IOException {
		String name = FileUtil.getFileName(src, false);
		String path = "";
		path = config.getProject().getLocationURI().getPath() + "/obj/" + name + ".lst";
		File lst = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(lst));
		String line = "";
		while ((line = br.readLine()) != null) {
			if (line.trim().isEmpty()) {
				continue;
			}
			String lineNum = line.substring(0, 6).trim();
			String relAddr = line.substring(7, 15).trim();
			if (lineNum.isEmpty() || relAddr.isEmpty())
				continue;
			addr_line.put("0x" + relAddr, lineNum);
		}
		br.close();
	}

	public Map<String, String> getAddr_line() {
		return addr_line;
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
				continue;
			String temp[] = line.split(":");
			if (temp.length != 2)
				continue;
			if (temp[0].trim().startsWith("0x") && temp[1].trim().startsWith("_")) {
				_dom.put(temp[1].trim(), temp[0].trim());
			}
		}
		br.close();
	}

	public String getAddr(String fun) {
		return _dom.get(fun);
	}
}
