package com.mwos.ebochs2.ui.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class OSProject {
	public static IProject create(String name) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		project.create(null);
		project.open(null);

		IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());
		String[] natureIds = { OSNature.OSNature };
		description.setNatureIds(natureIds);
		project.setDescription(description, null);
		project.refreshLocal(IProject.BACKGROUND_REFRESH, null);

		return project;
	}
}
