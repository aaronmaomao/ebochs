package com.mwos.ebochs.resource.project;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.filtermatchers.AbstractFileInfoMatcher;
import org.eclipse.core.runtime.CoreException;

public class FileMatcher extends AbstractFileInfoMatcher {

	private IProject project;
	private Object arguments;

	public FileMatcher() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(IProject project, Object arguments) throws CoreException {
		this.project = project;
		this.arguments = arguments;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean matches(IContainer parent, IFileInfo fileInfo) throws CoreException {

		switch (arguments.toString()) {
		case "Project":
			return matcheProject(parent, fileInfo);
		case "src":
			return matcheSrc(parent, fileInfo);
		case "inc":
			return matcheInc(parent, fileInfo);
		default:
			return false;
		}
	}

	private boolean matcheProject(IContainer parent, IFileInfo fileInfo) {
		if(fileInfo.isDirectory()) {
			if(fileInfo.getName().equals("src")||fileInfo.getName().equals("inc")) {
				return true;
			}
		}
		return false;
	}

	private boolean matcheSrc(IContainer parent, IFileInfo fileInfo) {
		return false;
	}

	private boolean matcheInc(IContainer parent, IFileInfo fileInfo) {
		return false;
	}
}
