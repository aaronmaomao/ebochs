package com.mwos.ebochs.core.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.model.handler.BP;
import com.mwos.ebochs.resource.config.entity.OSConfig;

public class BPModel {
	private OSConfig config;
	private DomMap domMap;

	public BPModel(OSConfig config) {
		this.config = config;
		domMap = new DomMap(config.getProject().getLocationURI().getPath() + "/obj/core.map");
	}

	public void getAllBp() throws CoreException {
		List<String[]> bps = new ArrayList<>();
		IBreakpoint[] bs = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints("org.eclipse.cdt.debug.core");
		for (IBreakpoint bp : bs) {
			if (bp instanceof CLineBreakpoint && bp.getMarker().getResource().getProject() == config.getProject()) {
				CLineBreakpoint temp = (CLineBreakpoint) bp;
				if (temp.getFileName().equals("mbr.asm")) {
					BP b = new BP();
					b.setEnable(temp.isEnabled());
					b.setAddress(calculate("src/mbr.asm", "0x7c00", temp.getLineNumber()));
				}
			}
		}
	}

	public String calculate(String src, String baseAddr, int line) {
		String name = FileUtil.getFileName(src, false);
		if (src.endsWith(".asm")) {
			File lst = new File(config.getProject().getLocationURI().getPath() + "/obj/" + name + ".lst");
			File asm = new File(config.getProject().getLocationURI().getPath() + src);
		}
	}

}

class DomCSrc{
	private String src;
	private OSConfig config;
	private DomAsmSrc domAsm;
	
	private Map<String, String> _dom;
	
	public DomCSrc(String src,OSConfig config, DomMap domMap) {
		this.src = src;
		this.config = config;
		_dom = new LinkedHashMap<>();
		domAsm = new DomAsmSrc(src, config);
	}
	
	public void init() throws IOException {
		String name = FileUtil.getFileName(src, false);
		File _asm = new File(config.getProject().getLocationURI().getPath() + "/obj/" + name + "_.asm");
		BufferedReader br = new BufferedReader(new FileReader(_asm));
		String line = "";
		Map<String,String> _asmMap = new LinkedHashMap<>();
		while ((line = br.readLine()) != null) {
			if (line.trim().isEmpty())
				continue;
			if(line.indexOf(0)=='_') {
				
			}
		}	
		br.close();
	}
}

class DomAsmSrc {
	private String src;
	private OSConfig config;

	private Map<String, String> _dom;

	public DomAsmSrc(String src, OSConfig config) {
		// TODO Auto-generated constructor stub
		this.src = src;
		this.config = config;
		_dom = new LinkedHashMap<>();
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws IOException {
		String name = FileUtil.getFileName(src, false);
		if (src.endsWith(".asm")) {
			File lst = new File(config.getProject().getLocationURI().getPath() + "/obj/" + name + ".lst");
			BufferedReader br = new BufferedReader(new FileReader(lst));
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				String lineNum = line.substring(0, 7).trim();
				String relAddr = line.substring(8, 16).trim();
				if (lineNum.isEmpty() || relAddr.isEmpty())
					continue;
				_dom.put(relAddr, lineNum);
			}
			br.close();
		} else if (src.endsWith(".c")) {
			File lst = new File(config.getProject().getLocationURI().getPath() + "/obj/" + name + ".lst");
			BufferedReader br = new BufferedReader(new FileReader(lst));
			String line = "";
			String fun = "null";
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				String lineNum = line.substring(0, 7).trim();
				String relAddr = line.substring(8, 16).trim();
				if (lineNum.isEmpty() || relAddr.isEmpty())
					continue;
				if (line.indexOf(49) == 'L' || line.indexOf(49) == '_')
					fun = line.substring(49, line.length());
				_dom.put(relAddr, fun + ":" + lineNum);
			}
			br.close();
		}
	}

	public Map<String, String> get_dom() {
		return _dom;
	}

	public String getLineNum(String relAddr) {
		return _dom.get(relAddr);
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
