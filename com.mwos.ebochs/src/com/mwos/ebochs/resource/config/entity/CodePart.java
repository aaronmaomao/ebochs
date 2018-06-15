package com.mwos.ebochs.resource.config.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mwos.ebochs.core.FileUtil;

public class CodePart {
	private String type;
	private String obj;
	private String location;
	private OSConfig config;
	private boolean link = true;
	private String src="";
	private List<Code> codes;

	public CodePart(OSConfig config) {
		this.config = config;
		this.location = config.getProject().getLocationURI().getPath() + "/obj";
		codes = new ArrayList<>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public boolean isLink() {
		return link;
	}

	public void setLink(String link) {
		if (link.trim().equals("false"))
			this.link = false;
		else
			this.link = true;
	}

	public Code getCode(String name) {
		for (Code c : codes) {
			if (c.getSrc().equals(name)) {
				return c;
			}
		}
		return null;
	}

	public void addCode(Code c) {
		for (Code temp : codes) {
			if (temp.getSrc().equals(c.getSrc())) {
				return;
			}
		}
		this.codes.add(c);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		if (location.contains(":")) {
			this.location = location;
		} else if (location.startsWith("/")) {
			this.location = config.getProject().getLocationURI().getPath() + location;
		}
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		link=false;
		this.src = src;
	}

	public String getObjPath() {
		return this.getLocation() + "/" + obj;
	}
	
	public File getObj() {
		
	}

	public boolean equal(CodePart old) {
		if (!this.type.equals(old.type))
			return false;
		if (!this.obj.equals(old.obj))
			return false;
		if (!this.getObjPath().equals(old.getObjPath()))
			return false;
		if (this.codes.size() != old.codes.size())
			return false;

		for (int i = 0; i < this.codes.size(); i++) {
			if (!this.codes.get(i).equal(old.codes.get(i)))
				return false;
		}

		return true;
	}

	public class Code {
		private String src;

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public boolean equal(Code old) {
			if (!this.src.equals(old.src))
				return false;
			return true;
		}
		
		public String getObj() {
			String obj="obj/";
			String name = FileUtil.getFileName(src, false);
			return obj;
		}
	}

}
