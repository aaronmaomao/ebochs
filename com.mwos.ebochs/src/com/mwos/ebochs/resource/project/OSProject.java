package com.mwos.ebochs.resource.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class OSProject {
	private OSProject() {

	}

	public static IProject create(String name) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		IProjectDescription des = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());
		String natures[] = { OSProjectNature.NatureId };
		des.setNatureIds(natures);
		project.create(des,null);
		project.open(null);
		project.getFolder("src").create(false, true, null);
		project.getFolder("inc").create(false, true, null);
		return project;
	}
	
	public static IProject getProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		return project;
	}
}
