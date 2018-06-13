package com.mwos.ebochs.core.build;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;

public class Builder {
	private IProject project;

	public Builder(IProject project) {
		this.project = project;
	}
	
	public void buildC(String file) {
		IFile iFile = project.getFile(file);
		
	}
	
	public void buildAsm(String file) {}
	
	public void buildProject() {}
}
