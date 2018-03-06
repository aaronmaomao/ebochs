package com.mwos.ebochs.wizard;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wb.swt.ResourceManager;

public class OSNewWizard extends Wizard implements INewWizard {

	private ISelection selection;
	private OSNewWizardPage newProjectPage;
	
	public OSNewWizard() {
		setNeedsProgressMonitor(true);
		this.setWindowTitle("Create OS Project");
		this.setTitleBarColor(new RGB(215, 225, 232));
		this.setDefaultPageImageDescriptor(
				ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/image/window.png"));
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public void addPages() {
		newProjectPage = new OSNewWizardPage();
		addPage(newProjectPage);
	}

	@Override
	public boolean performFinish() {
		System.out.println("完成按钮按下");
		return true;
	}
}
