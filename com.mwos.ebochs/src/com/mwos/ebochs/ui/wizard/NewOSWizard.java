package com.mwos.ebochs.ui.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.mwos.ebochs.resource.project.OSProject;
import com.mwos.ebochs.resource.project.OSProjectNature;

public class NewOSWizard extends Wizard implements INewWizard ,IWorkbenchActionConstants{

	private NewOSWizardPage newWizardPage;

	public NewOSWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public void addPages() {
		newWizardPage = new NewOSWizardPage();
		this.addPage(newWizardPage);
	}

	@Override
	public boolean performFinish() {
		try {
			OSProject.create("mwos");
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}
}
