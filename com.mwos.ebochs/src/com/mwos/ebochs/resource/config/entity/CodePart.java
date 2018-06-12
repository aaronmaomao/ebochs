package com.mwos.ebochs.resource.config.entity;

import java.util.ArrayList;
import java.util.List;

public class CodePart {
	private String type;
	private String obj;
	private String location;
	private OSConfig config;
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

	public Code getCode(String name) {
		for (Code c : codes) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	public void addCode(Code c) {
		for (Code temp : codes) {
			if (temp.getName().equals(c.getName())) {
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

	public String getObjPath() {
		return this.getLocation() + "/" + obj;
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
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean equal(Code old) {
			if (!this.name.equals(old.name))
				return false;
			return true;
		}

	}

}
