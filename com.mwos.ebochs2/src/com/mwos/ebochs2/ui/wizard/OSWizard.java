package com.mwos.ebochs2.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class OSWizard extends Wizard implements INewWizard{
	
	private WizardPage1 page1;
	private WizardPage2 page2;

	public OSWizard() {
		setWindowTitle("New Wizard");
		page1 = new WizardPage1();
		page2 = new WizardPage2();
	}

	@Override
	public void addPages() {
		this.addPage(page1);
		this.addPage(page2);
	}

	@Override
	public boolean performFinish() {
		return false;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
	}

}
