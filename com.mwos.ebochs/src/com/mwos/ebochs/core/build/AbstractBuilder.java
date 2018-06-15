package com.mwos.ebochs.core.build;

import com.mwos.ebochs.resource.project.OSProject;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public abstract class AbstractBuilder {

	protected OSProject project;

	public AbstractBuilder(OSProject project, String toolchain) throws Exception {
		// TODO Auto-generated constructor stub
		if (OSDevPreference.getValue(toolchain).isEmpty()) {
			throw new Exception("未找到工具链："+toolchain);
		}
		this.project = project;
	}

	public abstract BuildResult compileC(String src, String obj, String inc) ;
}
