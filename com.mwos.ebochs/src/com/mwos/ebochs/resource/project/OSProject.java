package com.mwos.ebochs.resource.project;

import java.util.Map;

import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.cdtvariables.ICdtVariablesContributor;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.settings.model.CConfigurationStatus;
import org.eclipse.cdt.core.settings.model.CSourceEntry;
import org.eclipse.cdt.core.settings.model.ICBuildSetting;
import org.eclipse.cdt.core.settings.model.ICConfigExtensionReference;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICExternalSetting;
import org.eclipse.cdt.core.settings.model.ICFileDescription;
import org.eclipse.cdt.core.settings.model.ICFolderDescription;
import org.eclipse.cdt.core.settings.model.ICLanguageSetting;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionManager;
import org.eclipse.cdt.core.settings.model.ICResourceDescription;
import org.eclipse.cdt.core.settings.model.ICSettingContainer;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.cdt.core.settings.model.ICSettingObject;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.core.settings.model.ICTargetPlatformSetting;
import org.eclipse.cdt.core.settings.model.WriteAccessException;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.eclipse.cdt.internal.core.model.CProject;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;

public class OSProject extends CProject {

	private OSProject(ICElement parent, IProject project) {
		super(parent, project);
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

		project.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				event.getType();
				IResourceDelta rootDelta = event.getDelta();
				IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {

					@Override
					public boolean visit(IResourceDelta delta) throws CoreException {
						return false;
					}
				};
			}
		});

		// // FileInfoMatcherDescription matcher1 = new
		// FileInfoMatcherDescription("com.mwos.ebochs.filterMatcher", "Project");
		// project.createFilter(
		// IResourceFilterDescription.EXCLUDE_ALL |
		// IResourceFilterDescription.INCLUDE_ONLY | IResourceFilterDescription.FILES |
		// IResourceFilterDescription.FOLDERS, matcher1,
		// IResource.BACKGROUND_REFRESH, null);

		IFolder inc = project.getFolder("inc");
		inc.create(true, true, null);
		IFolder src = project.getFolder("src");
		src.create(true, true, null);
		IFolder obj = project.getFolder("obj");
		obj.create(IFolder.HIDDEN, true, null);
		
		// File obj = new File(project.getLocationURI().getPath() + "\\obj");
		// obj.mkdir();

		// ICProjectDescriptionManager mgr =
		// CoreModel.getDefault().getProjectDescriptionManager();
		// ICProjectDescription cdes = mgr.getProjectDescription(project, true);
		// cdes = mgr.createProjectDescription(project, true);
		// ManagedBuildInfo info = ManagedBuildManager.createBuildInfo(project);
		// IProjectType projType =
		// ManagedBuildManager.getExtensionProjectType("com.mwos.ebochs.projectType1");
		// // or get projectType from UI
		// IToolChain toolChain =
		// ManagedBuildManager.getExtensionToolChain("com.mwos.ebochs.toolChain1"); //
		// or get toolChain from UI
		// ManagedProject mProj = new ManagedProject(project, projType);
		// info.setManagedProject(mProj);
		// IConfiguration[] configs =
		// ManagedBuildManager.getExtensionConfigurations(toolChain, projType);
		// for (IConfiguration icf : configs) {
		// if (!(icf instanceof Configuration)) {
		// continue;
		// }
		// Configuration cf = (Configuration) icf;
		//
		// String id = ManagedBuildManager.calculateChildId(cf.getId(), null);
		// Configuration config = new Configuration(mProj, cf, id, false, true);
		//
		// ICConfigurationDescription cfgDes =
		// cdes.createConfiguration(ManagedBuildManager.CFG_DATA_PROVIDER_ID,
		// config.getConfigurationData());
		// config.setConfigurationDescription(cfgDes);
		// config.exportArtifactInfo();
		//
		// IBuilder bld = config.getEditableBuilder();
		// if (bld != null) {
		// bld.setManagedBuildOn(true);
		// }
		//
		// config.setName(toolChain.getName());
		// config.setArtifactName(project.getName());
		//
		// }
		//
		// mgr.setProjectDescription(project, cdes);
		//
		// CProject croject = new CProject(CModelManager.getDefault().getCModel(),
		// project);

		project.refreshLocal(IProject.DEPTH_INFINITE, null);
		return project;
	}

	public static IProject getProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		return project;
	}
}
