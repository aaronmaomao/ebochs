package com.mwos.ebochs.wizard;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class OSNewWizard extends Wizard implements INewWizard {

	private OSWizardPage firstPage = null;
	private ISelection selection;

	public OSNewWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public void addPages() {
		firstPage = new OSWizardPage(selection);
		addPage(firstPage);
		addPage(new OSWizardPage(selection));
	}

	@Override
	public boolean performFinish() {
		System.out.println("完成按钮按下");
		return true;
	}
}
