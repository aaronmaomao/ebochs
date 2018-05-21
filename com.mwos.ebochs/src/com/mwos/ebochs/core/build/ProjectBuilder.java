package com.mwos.ebochs.core.build;

import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class ProjectBuilder extends IncrementalProjectBuilder {

	public static final String ID = "com.mwos.ebochs.OSBuilder";

	public ProjectBuilder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		ICommand command = getCommand();
		getDelta(getProject());
		switch (kind) {
		case FULL_BUILD:
			break;
		case INCREMENTAL_BUILD:
			break;
		case CLEAN_BUILD:
			break;
		case AUTO_BUILD:
			break;
		default:
			IResourceDelta delta = getDelta(getProject());
			break;
		}
		return null;
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		super.clean(monitor);
	}
}
