package com.mwos.ebochs.resource.project;

import java.io.File;

import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.core.resources.FileInfoMatcherDescription;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceFilterDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class OSProject {
	private OSProject() {

	}

	public static IProject create(String name) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		project.create(null);
		project.open(null);
		IProjectDescription des = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());
		String natures[] = { OSProjectNature.NatureId, CProjectNature.C_NATURE_ID };
		des.setNatureIds(natures);
		project.setDescription(des, IResource.BACKGROUND_REFRESH, null);
		CProjectNature.addCNature(project, null);

		FileInfoMatcherDescription matcher = new FileInfoMatcherDescription("com.mwos.ebochs.filterMatcher", null);

		IResourceFilterDescription filter = project.createFilter(IResourceFilterDescription.FILES | IResourceFilterDescription.INCLUDE_ONLY | IResourceFilterDescription.FOLDERS,
				matcher, IResource.BACKGROUND_REFRESH, null);
		filter.getResource();
		// ICProjectDescriptionManager mgr =
		// CoreModel.getDefault().getProjectDescriptionManager();
		// ICProjectDescription cdes = mgr.getProjectDescription(project, true);
		// if (cdes != null)
		// return true; // C project description already exists
		// ICProjectDescription cdes = mgr.createProjectDescription(project, true);
		// cdes.setCdtProjectCreated();
		// cdes.useDefaultConfigurationRelations();
		// mgr.setProjectDescription(project, cdes);
		// project.getWorkspace().addResourceChangeListener(new
		// IResourceChangeListener() {
		// @Override
		// public void resourceChanged(IResourceChangeEvent event) {
		// }
		// }, IResourceChangeEvent.POST_CHANGE);
		project.getFolder("src").create(false, true, null);
		project.getFolder("inc").create(false, true, null);
		project.getFolder("res").create(false, true, null);
		File obj = new File(project.getLocationURI().getPath() + "\\obj");
		obj.mkdir();
		return project;
	}

	public static IProject getProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		return project;
	}
}
