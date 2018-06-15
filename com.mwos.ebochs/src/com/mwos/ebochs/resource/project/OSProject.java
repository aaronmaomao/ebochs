package com.mwos.ebochs.resource.project;

import java.net.URL;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.IPathEntry;
import org.eclipse.cdt.core.parser.ExtendedScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfoProvider;
import org.eclipse.cdt.internal.ui.util.CoreUtility;
import org.eclipse.cdt.utils.PathUtil;
import org.eclipse.core.resources.IFile;
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
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.osgi.framework.Bundle;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public abstract class OSProject implements IProject {

	private OSProject(IProject p) {
	}

	public static OSProject create(String name) throws CoreException {
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
						return true;
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

		ICProject cp = CoreModel.getDefault().create(project);

		IFolder inc = project.getFolder("inc");
		// inc.create(true, true, null);
		IFolder src = project.getFolder("src");
		// src.create(true, true, null);
		IFolder obj = project.getFolder("obj");

		IPathEntry incEntry = CoreModel.newSourceEntry(inc.getFullPath());
		IPathEntry srcEntry = CoreModel.newSourceEntry(src.getFullPath());
		IPathEntry objEntry = CoreModel.newOutputEntry(obj.getFullPath());
		String OSlib = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN);
		IPathEntry pathEntry = null;
		if (!OSlib.isEmpty()) {
			pathEntry = CoreModel.newIncludeEntry(null, null, PathUtil.getWorkspaceRelativePath(OSlib + "/lib"), true);
		}

		BasicNewResourceWizard.selectAndReveal(inc, Activator.getDefault().getWorkbench().getActiveWorkbenchWindow());
		BasicNewResourceWizard.selectAndReveal(src, Activator.getDefault().getWorkbench().getActiveWorkbenchWindow());
		BasicNewResourceWizard.selectAndReveal(obj, Activator.getDefault().getWorkbench().getActiveWorkbenchWindow());

		CoreUtility.createFolder(inc, true, true, null);
		CoreUtility.createFolder(src, true, true, null);
		CoreUtility.createFolder(obj, true, true, null);

		try {
			IFile config = project.getFile("OS.xml");
			Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
			URL url = bundle.getResource("src/com/mwos/ebochs/resource/project/OSTemp.xml");
			config.create(url.openStream(), true, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		cp.setRawPathEntries(new IPathEntry[] { incEntry, srcEntry, objEntry, pathEntry }, null);

		// IFolder objs = project.getFolder("objs");

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
		OSProject p = (OSProject) project;
		return p;
	}

	public String getIncDir() {
		String incs = FileUtil.getIncStr(this.getLocationURI().getPath());
		IScannerInfoProvider provider = CCorePlugin.getDefault().getScannerInfoProvider(this);
		if (provider != null) {
			IScannerInfo info = provider.getScannerInformation(this);
			if (info instanceof ExtendedScannerInfo) {
				for (String inc : ((ExtendedScannerInfo) info).getIncludePaths()) {
					if (!incs.contains(inc))
						incs += (" -I " + inc.trim());
				}
			}
		}

		// ICProjectDescription projectDescription =
		// CoreModel.getDefault().getProjectDescription(project);
		// ICConfigurationDescription activeConfiguration =
		// projectDescription.getActiveConfiguration(); // or another config
		// ICFolderDescription folderDescription =
		// activeConfiguration.getRootFolderDescription(); // or use
		// getResourceDescription(IResource),
		// // or pick one from getFolderDescriptions()
		// ICLanguageSetting[] languageSettings =
		// folderDescription.getLanguageSettings();
		// for (ICLanguageSetting languageSetting : languageSettings) {
		// for (ICLanguageSettingEntry includePathSetting :
		// languageSetting.getSettingEntries(ICSettingEntry.INCLUDE_PATH)) {
		// includePathSetting.getValue();
		// if (!inc.isEmpty() && !incs.contains(inc)) {
		// incs += (" -I " + inc.trim());
		// }
		// }
		// }
		return incs;
	}
}
