package com.mwos.ebochs.cdt.ui.wizards;


import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionManager;
import org.eclipse.cdt.managedbuilder.core.IBuilder;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IProjectType;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.internal.core.Configuration;
import org.eclipse.cdt.managedbuilder.internal.core.ManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.internal.core.ManagedProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewOSWizard extends Wizard implements INewWizard {

	public NewOSWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		NewOSWizardPage page = new NewOSWizardPage();
		this.addPage(page);
	}

	@Override
	public boolean performFinish() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject("mwos");
		if (!project.exists()) {
			IProjectDescription description = workspace.newProjectDescription(project.getName());
			description.setNatureIds(new String[] { "com.mwos.ebochs.cdt.osnature" });
			try {
				project.create(description, null);
				project.open(null);
				project.getFolder("src").create(true, true, null);
				project.getFolder("inc").create(true, true, null);
				
				CProjectNature.addCNature(project, null);
				ICProjectDescriptionManager mgr = CoreModel.getDefault().getProjectDescriptionManager();
				ICProjectDescription des = mgr.getProjectDescription(project, true);
				if (des != null)
					return true; // C project description already exists
				des = mgr.createProjectDescription(project, true);
				ManagedBuildInfo info = ManagedBuildManager.createBuildInfo(project);
				IProjectType projType = ManagedBuildManager.getExtensionProjectType("my.project.type"); // or get projectType from UI
				IToolChain toolChain = (IToolChain) ManagedBuildManager.getExtensionToolChain("my.toolchain"); // or get toolChain from UI

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

					ICConfigurationDescription cfgDes = des.createConfiguration(
							ManagedBuildManager.CFG_DATA_PROVIDER_ID, config.getConfigurationData());
					config.setConfigurationDescription(cfgDes);
					config.exportArtifactInfo();

					IBuilder bld = config.getEditableBuilder();
					if (bld != null) {
						bld.setManagedBuildOn(true);
					}

					config.setName(toolChain.getName());
					config.setArtifactName(project.getName());

				}

				mgr.setProjectDescription(project, des);

				
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
}
