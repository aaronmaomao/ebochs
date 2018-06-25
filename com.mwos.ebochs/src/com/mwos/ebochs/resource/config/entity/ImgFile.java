package com.mwos.ebochs.resource.config.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.utils.PathUtil;
import org.eclipse.core.runtime.Path;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class ImgFile {

	private String src="";
	private List<ImgFile> subs;
	private OSConfig config;

	public ImgFile(OSConfig config) {
		this.config = config;
		subs = new ArrayList<>();
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		if (!src.contains("/") && !src.contains("\\")) {
			src = "/obj/" + src;
		}
		this.src = src;
	}

	public String getLocation() {
		if (src.contains(":"))
			return src;
		else
			return config.getProject().getLocationURI().getPath() + src;
	}

	public OSConfig getConfig() {
		return config;
	}

	public void setConfig(OSConfig config) {
		this.config = config;
	}

	public List<ImgFile> getSubs() {
		return subs;
	}

	public File build(AbstractBuilder builder) {
		File file = new File(this.getLocation());
		if(subs.size()>0) {
			file.delete();
		}
		for (ImgFile sub : subs) {
			file = FileUtil.concat(file, new File[] { new File(sub.getLocation()) });
			if (file == null) {
				ConsoleFactory.outMsg("----- 文件构建错误:\t" + this.getSrc() + "\r\n", this.getConfig().getProject());
				return null;
			}
		}

		return file;
	}
	
	public void addSubFile(ImgFile f) {

		for (ImgFile file : subs) {
			if (PathUtil.equalPath(new Path(file.getLocation()), new Path(f.getLocation()))) {
				return;
			}
		}

		this.subs.add(f);
	}
	
	public void clean() {
		if(!this.getSrc().contains(":")) {
			new File(this.getLocation()).delete();
		}
	}
}
