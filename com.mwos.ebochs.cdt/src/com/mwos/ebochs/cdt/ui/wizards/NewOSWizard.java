package com.mwos.ebochs.cdt.ui.wizards;

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
		if(!project.exists()) {
			IProjectDescription description = workspace.newProjectDescription(project.getName());
			try {
				project.create(null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
