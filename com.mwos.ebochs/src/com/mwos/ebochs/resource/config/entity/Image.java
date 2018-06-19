package com.mwos.ebochs.resource.config.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.build.AbstractBuilder;

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

	public List<CodePart> getCP() {
		List<CodePart> cps = new ArrayList<>();
		CodePart cp = null;
		cp = config.getCodePart(this.getMbr());
		if (cp != null) {
			cps.add(cp);
		}
		for (ImgFile imgf : imgFiles) {
			cp = config.getCodePart(imgf.getSrc());
			if (cp != null) {
				cps.add(cp);
			}
			for (ImgFile sub : imgf.getSubs()) {
				cp = config.getCodePart(sub.getSrc());
				if (cp != null) {
					cps.add(cp);
				}
			}
		}

		return cps;
	}

	public void setMbr(String mbr) {
		if (!mbr.contains("/") && !mbr.contains(":")) {
			mbr = "/obj/" + mbr;
		}
		this.mbr = mbr;
	}

	public List<ImgFile> getImgFiles() {
		return imgFiles;
	}

	public void addImgFile(ImgFile r) {
		for (ImgFile temp : imgFiles) {
			if (temp.getSrc().equals(r.getSrc()))
				return;
		}
		imgFiles.add(r);
	}

	public OSConfig getConfig() {
		return config;
	}

	public File build(AbstractBuilder builder) {
		File img = null;
		try {
			img = FileUtil.makeFile(this.getConfig().getProject().getLocationURI().getPath() + "/obj/image/" + this.name, this.size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
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
