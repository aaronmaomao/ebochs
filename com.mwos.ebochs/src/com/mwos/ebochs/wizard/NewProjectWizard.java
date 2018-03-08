package com.mwos.ebochs.wizard;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wb.swt.ResourceManager;

public class NewProjectWizard extends Wizard implements INewWizard {

	private NewWizardPage newWizardPage;
	private ISelection selection;

	public NewProjectWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		IProject p = (IProject) selection.getFirstElement();
		try {
			p.getProject().members();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
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
