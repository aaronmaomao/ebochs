package com.mwos.ebochs.resource.project;

import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionManager;
import org.eclipse.cdt.internal.core.model.CModelManager;
import org.eclipse.cdt.internal.core.model.CProject;
import org.eclipse.cdt.managedbuilder.core.IBuilder;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IProjectType;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.internal.core.Configuration;
import org.eclipse.cdt.managedbuilder.internal.core.ManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.internal.core.ManagedProject;
import org.eclipse.core.resources.FileInfoMatcherDescription;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceFilterDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class OSProject extends CProject {

	private OSProject(ICElement parent, IProject project) {
		super(parent, project);
	}

	@SuppressWarnings("restriction")
	public static CProject create(String name) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		project.create(null);
		project.open(null);
		IProjectDescription des = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());
		String natures[] = { OSProjectNature.NatureId, CProjectNature.C_NATURE_ID };
		des.setNatureIds(natures);
		project.setDescription(des, IResource.BACKGROUND_REFRESH, null);
		CProjectNature.addCNature(project, null);

		FileInfoMatcherDescription matcher = new FileInfoMatcherDescription("com.mwos.ebochs.filterMatcher", "");

		IResourceFilterDescription filter = project.createFilter(IResourceFilterDescription.FILES | IResourceFilterDescription.INCLUDE_ONLY | IResourceFilterDescription.FOLDERS,
				matcher, IResource.BACKGROUND_REFRESH, null);

		project.getFolder("src").create(false, true, null);
		project.getFolder("inc").create(false, true, null);
		// File obj = new File(project.getLocationURI().getPath() + "\\obj");
		// obj.mkdir();

		ICProjectDescriptionManager mgr = CoreModel.getDefault().getProjectDescriptionManager();
		ICProjectDescription cdes = mgr.getProjectDescription(project, true);
		cdes = mgr.createProjectDescription(project, true);
		ManagedBuildInfo info = ManagedBuildManager.createBuildInfo(project);
		IProjectType projType = ManagedBuildManager.getExtensionProjectType("com.mwos.ebochs.projectType1"); // or get projectType from UI
		IToolChain toolChain = ManagedBuildManager.getExtensionToolChain("com.mwos.ebochs.toolChain"); // or get toolChain from UI
		ManagedProject mProj = new ManagedProject(project, projType);
		info.setManagedProject(mProj);
		IConfiguration[] configs = ManagedBuildManager.getExtensionConfigurations(toolChain, projType);
		for (IConfiguration icf : configs) {
			if (!(icf instanceof Configuration)) {
				continue;
			}
			Configuration cf = (Configuration) icf;

			String id = ManagedBuildManager.calculateChildId(cf.getId(), null);
			Configuration config = new Configuration(mProj, cf, id, false, true);

			ICConfigurationDescription cfgDes = cdes.createConfiguration(ManagedBuildManager.CFG_DATA_PROVIDER_ID, config.getConfigurationData());
			config.setConfigurationDescription(cfgDes);
			config.exportArtifactInfo();

			IBuilder bld = config.getEditableBuilder();
			if (bld != null) {
				bld.setManagedBuildOn(true);
			}

			config.setName(toolChain.getName());
			config.setArtifactName(project.getName());

		}

		mgr.setProjectDescription(project, cdes);

		CProject croject = new CProject(CModelManager.getDefault().getCModel(), project);

		return croject;
	}

	public static IProject getProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		return project;
	}
}
