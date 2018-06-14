package com.mwos.ebochs.core.build;

import java.io.File;
import java.io.IOException;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.resource.config.entity.Image;

public class ImageFactory {

	public static File makeImage(Image image) {

		File file = null;
		try {
			file = FileUtil.makeFile(image.getConfig().getProject().getProjectRelativePath().toString() + "/obj/image/" + image.getName(), image.getSize());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
}
