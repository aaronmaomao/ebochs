package com.mwos.ebochs.resource.project;

import java.net.URL;
import java.util.List;

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
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.resource.config.OSConfigFactory;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public class OSProject {

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
		
		IFolder img = project.getFolder("obj/images");

		IPathEntry incEntry = CoreModel.newSourceEntry(inc.getFullPath());
		IPathEntry srcEntry = CoreModel.newSourceEntry(src.getFullPath());
		IPathEntry objEntry = CoreModel.newOutputEntry(obj.getFullPath());
		String OSlib = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN);
		IPathEntry pathEntry = null;
		if (!OSlib.isEmpty()) {
			pathEntry = CoreModel.newIncludeEntry(null, null, PathUtil.getWorkspaceRelativePath(OSlib + "/lib"), true);
			cp.setRawPathEntries(new IPathEntry[] { pathEntry }, null);
		}

		BasicNewResourceWizard.selectAndReveal(inc, Activator.getDefault().getWorkbench().getActiveWorkbenchWindow());
		BasicNewResourceWizard.selectAndReveal(src, Activator.getDefault().getWorkbench().getActiveWorkbenchWindow());
		BasicNewResourceWizard.selectAndReveal(obj, Activator.getDefault().getWorkbench().getActiveWorkbenchWindow());

		CoreUtility.createFolder(inc, true, true, null);
		CoreUtility.createFolder(src, true, true, null);
		CoreUtility.createFolder(obj, true, true, null);
		
		img.create(true, true, null);

		try {
			IFile config = project.getFile("OS.xml");
			Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
			URL url = bundle.getResource("resource/OSTemp.xml");
			config.create(url.openStream(), true, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (pathEntry != null) {
			cp.setRawPathEntries(new IPathEntry[] { incEntry, srcEntry, objEntry, pathEntry }, null);
		} else {
			cp.setRawPathEntries(new IPathEntry[] { incEntry, srcEntry, objEntry }, null);
		}

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
		return project;
	}

	public static List<String> getIncDirs(IProject p) {
		List<String> incDirs = FileUtil.getIncStr(p.getLocationURI().getPath());
		IScannerInfoProvider provider = CCorePlugin.getDefault().getScannerInfoProvider(p);
		if (provider != null) {
			IScannerInfo info = provider.getScannerInformation(p);
			if (info instanceof ExtendedScannerInfo) {
				for (String inc : ((ExtendedScannerInfo) info).getIncludePaths()) {
					if (!incDirs.contains(inc))
						incDirs.add(inc);
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
		return incDirs;
	}
	
	public static boolean build(IProject p, AbstractBuilder builder) {
		try {
			 return OSConfigFactory.getConfig(p).build(builder);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
