package com.mwos.ebochs.resource.config.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.core.build.BuildResult;
import com.mwos.ebochs.ui.view.ConsoleFactory;

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
		if (!mbr.contains("/") && !mbr.contains(":")) {
			mbr = "/obj/" + mbr;
		}
		this.mbr = mbr;
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
		for (ImgFile f : this.imgFiles) {
			if (f.build(builder) == null) {
				return null;
			}
		}
		FileUtil.fillFile(this.getConfig().getProject().getLocationURI().getPath() + this.getMbr(), size);
		try {
			BuildResult res = builder.buildImg(this);
			if (!res.isSuccess()) {
				ConsoleFactory.outErrMsg("----- 镜像构建失败:\t" + this.getName() + "\r\n" + res.getAllMsg() + "\r\n", this.getConfig().getProject());
				return null;
			} else {
				ConsoleFactory.outMsg("----- 镜像构建成功:\t" + this.getName() + "\r\n" + res.getAllMsg() + "\r\n", this.getConfig().getProject());
				return new File(this.getConfig().getProject().getLocationURI().getPath() + "/images/" + this.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleFactory.outErrMsg("----- 系统错误:\t" + "\r\n", this.getConfig().getProject());
			return null;
		}
	}
	
	public void clean() {
		new File(this.getConfig().getProject().getLocationURI().getPath()+"/obj/images/"+this.getName()).delete();
		for(ImgFile imgF:imgFiles) {
			imgF.clean();
		}
	}
}
