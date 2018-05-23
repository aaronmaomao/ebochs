package com.mwos.ebochs.core.build;

import java.io.File;
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
		case CLEAN_BUILD:
			break;
		case INCREMENTAL_BUILD:
		case AUTO_BUILD:
			String info = doBuilds(getDelta(getProject()).getAffectedChildren(IResourceDelta.ADDED | IResourceDelta.CHANGED));
			System.out.println(info);
			break;
		default:

			break;
		}
		return null;
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		super.clean(monitor);
	}

	private String doBuilds(IResourceDelta deltas[]) {
		String info = "";
		if (deltas != null) {
			for (IResourceDelta delta : deltas) {
				if (new File(delta.getResource().getLocationURI().getPath()).isFile()) {
					if (delta.getResource().getName().endsWith(".c"))
						info += doBuild(delta.getProjectRelativePath().toString());
				} else {
					info += doBuilds(delta.getAffectedChildren());
				}
			}
		}
		return info;
	}

	private String doBuild(String file) {
		try {
			String res = Compiler.compile(file, this.getProject());
			return res + "\r\n";
		} catch (Exception e) {
			e.printStackTrace();
			return file + ">编译异常\r\n";
		}
	}
}
