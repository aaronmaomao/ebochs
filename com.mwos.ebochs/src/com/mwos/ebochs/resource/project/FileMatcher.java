package com.mwos.ebochs.resource.project;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.filtermatchers.AbstractFileInfoMatcher;
import org.eclipse.core.runtime.CoreException;

public class FileMatcher extends AbstractFileInfoMatcher {

	private IProject project;

	public FileMatcher() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(IProject project, Object arguments) throws CoreException {
		this.project = project;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean matches(IContainer parent, IFileInfo fileInfo) throws CoreException {
		if (parent.getFullPath().toString().startsWith(project.getFullPath() + "/src")) {
			String file = fileInfo.getName();
			if (file.endsWith(".c") || file.endsWith(".h") || file.endsWith(".asm")) {
				return true;
			} else {
				return false;
			}
		}
		if (parent.getFullPath().toString().startsWith(project.getFullPath() + "/inc")) {
			String file = fileInfo.getName();
			if (file.endsWith(".c") || file.endsWith(".h") || file.endsWith(".asm")) {
				return true;
			} else {
				return false;
			}
		}
		if (parent.getFullPath().toString().startsWith(project.getFullPath() + "/res"))
			return true;
		if (parent.getFullPath().toString().equals(project.getFullPath().toString()) && fileInfo.getName().equals("src"))
			return true;
		if (parent.getFullPath().toString().equals(project.getFullPath().toString()) && fileInfo.getName().equals("inc"))
			return true;
		if (parent.getFullPath().toString().equals(project.getFullPath().toString()) && fileInfo.getName().equals("res"))
			return true;
		return false;
	}

}
