package com.mwos.ebochs.resource.config.entity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.utils.PathUtil;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.resource.project.OSProject;

public class Image {
	private String name;
	private long size;
	private String device;
	private String format;
	private String mbr;
	private OSConfig config;
	private List<ImgFile> imgFiles;

	public Image(OSConfig config) {
		this.config = config;
		imgFiles = new ArrayList<>();
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

	public List<ImgFile> getImgFiles() {
		return imgFiles;
	}

	public void addImgFile(ImgFile r) {
		for (ImgFile temp : imgFiles) {
			if (temp.getFilePath().equals(r.getFilePath()))
				return;
		}
		imgFiles.add(r);
	}

	public OSConfig getConfig() {
		return config;
	}

	public boolean isResInclude(String path) {
		if (!path.contains(":")) {
			if (!path.startsWith("/"))
				path = "/" + path;
			path = this.config.getProject().getLocationURI().getPath() + path;
		}

		for (ImgFile temp : imgFiles) {
			if (FileUtil.equalPath(temp.getFilePath(), path))
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
		if (this.imgFiles.size() != old.imgFiles.size())
			return false;

		for (int i = 0; i < imgFiles.size(); i++) {
			if (!imgFiles.get(i).equal(old.getImgFiles().get(i)))
				return false;
		}
		return true;
	}
}
