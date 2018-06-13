package com.mwos.ebochs.core.build;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.resource.config.entity.CodePart;
import com.mwos.ebochs.resource.config.entity.CodePart.Code;
import com.mwos.ebochs.ui.view.ConsoleFactory;
import com.mwos.ebochs.resource.config.entity.Image;
import com.mwos.ebochs.resource.config.entity.ImgFile;

public class BuildImage {
	private Image image;
	private IProject p;

	public BuildImage(Image image) {
		this.image = image;
		p = image.getConfig().getProject();
	}

	public File getImageFile() {
		List<File> files = new ArrayList<>();
		File f = new File(image.getConfig().getProject().getLocationURI().getPath() + "/obj/" + image.getName());
		files.add(f);
		for (ImgFile imgf : image.getImgFiles()) {
			addFile(imgf, files);
		}
		return f;
	}

	private void addFile(ImgFile imgf, List<File> files) {
		File f = new File(imgf.getFilePath());
		files.add(f);
		CodePart cp = imgf.getConfig().getCodePart(imgf);
		if (cp != null) {
			buildCodePart(cp);
		}
		for (ImgFile sub : imgf.getSubs()) {
			addFile(sub, files);
		}
	}

	private boolean buildCodePart(CodePart cp) {
		boolean r = true;
		for (Code code : cp.getCodes()) {
			if (code.getName().toLowerCase().endsWith(".c")) {
			//	r &= BuildFactory.doBuildC(code.getName(), p);
			} else if (code.getName().toLowerCase().endsWith(".asm")) {
			//	r &= BuildFactory.doBuildAsm(code.getName(), p);
			} else {
				r &= false;
			}
			if(!r) {
				ConsoleFactory.outErrMsg(code.getName() + " ----- 编译异常\r\n", p);
				return false;
			}
		}
		return r;
	}
}
