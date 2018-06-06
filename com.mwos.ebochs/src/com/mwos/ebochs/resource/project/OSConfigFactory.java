package com.mwos.ebochs.resource.project;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class OSConfigFactory {
	private static Map<IProject, OSConfig> _map = new HashMap<>();

	private OSConfigFactory() {

	}

	public static OSConfig getConfig(IProject project) {
		OSConfig config = _map.get(project);
		if (config == null) {
			if (new File(project.getLocationURI().toString() + "\\OS.xml").exists()) {
				parse(project.getFile("OS.xml"));
			}
		}
		return config;
	}

	private static OSConfig parse(IFile osxml) {
		OSConfig config = new OSConfig();
		return config;
	}
}
