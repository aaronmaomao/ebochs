package com.mwos.ebochs.resource.config.entity;

import java.util.ArrayList;
import java.util.List;

public class Image {
	private String name;
	private long size;
	private String device;
	private String format;
	private String mbr;
	private List<Resource> resources;

	public Image() {
		resources = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(String size) {
		size = size.trim().toLowerCase();
		if (size.endsWith("k") || size.endsWith("kb")) {
			this.size = Integer.parseInt((size.substring(0, size.indexOf("k")))) * 1024;
		} else if (size.endsWith("m") || size.endsWith("mb")) {
			this.size = Integer.parseInt((size.substring(0, size.indexOf("k")))) * 1024 * 1024;
		} else if (size.endsWith("g") || size.endsWith("gb")) {
			this.size = Integer.parseInt((size.substring(0, size.indexOf("k")))) * 1024 * 1024;
		} else {
			this.size = Integer.parseInt(size);
		}
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMbr() {
		return mbr;
	}

	public void setMbr(String mbr) {
		this.mbr = mbr;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void addResource(Resource r) {
		for (Resource temp : resources) {
			if (temp.getName().equals(r.getName()))
				return;
		}

		resources.add(r);
	}

	public boolean isResInclude(String name) {
		for (Resource temp : resources) {
			if (temp.getName().equals(name))
				return true;
		}

		return false;
	}

	public boolean equal(Image old) {
		if (!this.name.equals(old.name))
			return false;
		if (this.size != old.size)
			return false;
		if (!this.device.equals(old.device))
			return false;
		if (!this.format.equals(old.format))
			return false;
		if (!this.mbr.equals(old.mbr))
			return false;
		if (this.resources.size() != old.resources.size())
			return false;

		for (int i = 0; i < resources.size(); i++) {
			if (!resources.get(i).equal(old.getResources().get(i)))
				return false;
		}

		return true;
	}
}
