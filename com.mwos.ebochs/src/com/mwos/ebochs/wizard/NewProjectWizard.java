package com.mwos.ebochs.wizard;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;

public class NewProjectWizard extends Wizard implements INewWizard ,IWorkbenchActionConstants{

	private NewWizardPage newWizardPage;
	private ISelection selection;

	public NewProjectWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public void addPages() {
		newWizardPage = new NewWizardPage();
		this.addPage(newWizardPage);
	}

	@Override
	public boolean performFinish() {
		return false;
	}

}
