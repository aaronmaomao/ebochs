package com.mwos.ebochs.resource.config.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;

public class OSConfig {
	private IProject project;
	private String name;
	private String bits;
	private String version;
	private Platform platform;
	private List<Image> images;
	private List<CodePart> codeParts;

	private boolean needBuild = true;

	public OSConfig(IProject project) {
		this.project = project;
		images = new ArrayList<>();
		codeParts = new ArrayList<CodePart>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBits() {
		return bits;
	}

	public void setBits(String bits) {
		this.bits = bits;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public List<Image> getImages() {
		return images;
	}

	public void addImage(Image image) {
		this.images.add(image);
	}

	public void addCodePart(CodePart cp) {
		for (CodePart temp : codeParts) {
			if (temp.getObj().equals(cp.getObj()))
				return;
		}

		codeParts.add(cp);
	}

	public List<CodePart> getCodePart(ImgFile imgFile) {
		List<CodePart> cps = new ArrayList<>();
		for (CodePart temp : codeParts) {
			if (temp.getObj().equals(imgFile.getName()) && FileUtil.equalPath(temp.getLocation(), imgFile.getLocation()))
				cps.add(temp);
		}
		
		for(ImgFile ifile:imgFile.getSubs()) {
			cps.addAll(getCodePart(ifile));
		}
		return cps;
	}

	public List<CodePart> getCodeParts() {
		return codeParts;
	}

	public IProject getProject() {
		return project;
	}

	public boolean checkNeedBuild(OSConfig old) {
		if (old == null) {
			needBuild = true;
			return needBuild;
		}

		for (Image image : this.images) {
			if (!new File(project.getLocationURI().getPath() + "\\obj\\" + image.getName()).exists()) {
				needBuild = true;
				return needBuild;
			}
		}
		if (!this.equal(old)) {
			needBuild = true;
		} else {
			needBuild = false;
		}
		return needBuild;
	}

	public boolean needBuild() {
		return needBuild;
	}

	private boolean equal(OSConfig old) {
		if (!this.name.equals(old.name))
			return false;
		if (this.images.size() != old.images.size())
			return false;
		for (int i = 0; i < this.images.size(); i++) {
			if (!this.images.get(i).equal(old.images.get(i)))
				return false;
		}

		// for (int i = 0; i < this.codeParts.size(); i++) {
		// if (!this.codeParts.get(i).equal(old.codeParts.get(i)))
		// return false;
		// }

		return true;
	}
}
