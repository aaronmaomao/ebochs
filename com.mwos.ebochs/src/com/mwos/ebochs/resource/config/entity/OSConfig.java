package com.mwos.ebochs.resource.config.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.resource.config.entity.CodePart.Code;

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
			if (temp.getOut().equals(cp.getOut()))
				return;
		}

		codeParts.add(cp);
	}

	public List<CodePart> getCodeParts() {
		return codeParts;
	}

	public CodePart getCodePart(String out) {
		for (CodePart cp : codeParts) {
			if (cp.getOut().equals(out)) {
				return cp;
			}
		}
		return null;
	}

	public CodePart[] getUsedCP() {
		Map<String, CodePart> cps = new LinkedHashMap<>();
		for (Image img : images) {
			for (CodePart cp : img.getCP()) {
				cps.put(cp.getOut(), cp);
			}
		}
		return cps.values().toArray(new CodePart[] {});
	}

	public boolean build(AbstractBuilder builder) {
		for (CodePart cp : getUsedCP()) {
			if (cp.build(builder) == null) {
				return false;
			}
		}
		for (Image image : images) {
			if (image.build(builder) == null) {
				return false;
			}
		}
		return true;
	}

	public IProject getProject() {
		return project;
	}

	public boolean needBuild() {
		return needBuild;
	}

	public Code getCode(String src) {
		for (CodePart cp : codeParts) {
			Code c = cp.getCode(src);
			if (c != null)
				return c;
		}
		return null;
	}

	public void clean() {
		for (Image img : images) {
			img.clean();
		}
	}

}
