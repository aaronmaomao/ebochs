package com.mwos.ebochs.resource.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.mwos.ebochs.core.build.ProjectBuilder;

public class OSProjectNature implements IProjectNature {

	private IProject project;
	public static final String NatureId = "com.mwos.ebochs.osnature";

	@Override
	public void configure() throws CoreException {
	}

	@Override
	public void deconfigure() throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return this.project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;

	}
}
