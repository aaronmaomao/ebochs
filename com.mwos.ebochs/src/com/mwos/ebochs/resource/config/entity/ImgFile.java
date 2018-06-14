package com.mwos.ebochs.resource.config.entity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.utils.PathUtil;
import org.eclipse.core.runtime.Path;

public class ImgFile {

	private String name;
	private String location;
	private List<ImgFile> subs;
	

	private OSConfig config;

	public ImgFile(OSConfig config) {
		this.config = config;
		this.location = config.getProject().getLocationURI().getPath() + "/obj";
		subs = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OSConfig getConfig() {
		return config;
	}

	public void setConfig(OSConfig config) {
		this.config = config;
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

	public String getFilePath() {
		return this.getLocation() + "/" + name;
	}

	public List<ImgFile> getSubs() {
		return subs;
	}

	public void addSubFile(ImgFile f) {

		for (ImgFile file : subs) {
			if (PathUtil.equalPath(new Path(file.getFilePath()), new Path(f.getFilePath()))) {
				return;
			}
		}

		this.subs.add(f);
	}
	
	public boolean equal(ImgFile old) {
		if (!this.name.equals(old.name))
			return false;
		if (!this.getFilePath().equals(old.getFilePath()))
			return false;
		if (this.subs.size() != old.subs.size())
			return false;

		for (int i = 0; i < this.subs.size(); i++) {
			if (!this.subs.get(i).equal(old.subs.get(i))) {
				return false;
			}
		}
		return true;
	}
}
