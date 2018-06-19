package com.mwos.ebochs.resource.config.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.cdt.utils.PathUtil;
import org.eclipse.core.runtime.Path;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.build.AbstractBuilder;

public class ImgFile {

	private String src;
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

	public File generateFile(AbstractBuilder builder) {
		File file = new File(this.getLocation());
		for(ImgFile sub:subs) {
			File subF = sub.generateFile(builder);
			if(subF==null)
				return null;
			FileUtil.concatfile, subF);
		}
		CodePart cp = config.getCodePart(this.getSrc());
		if(cp!=null) {
			if(cp.build(builder)==null) {
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

	public boolean equal(ImgFile old) {
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
