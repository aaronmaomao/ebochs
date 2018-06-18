package com.mwos.ebochs.ui.wizard;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

public class NewOSWizardPage extends WizardPage {
	private Text textPrjName;
	private String projName;

	private int isVaild = 0x1;
	private static final int prjNameVaild = 0x1;

	/**
	 * Create the wizard.
	 */
	public NewOSWizardPage() {
		super("wizardPage");
		setMessage("创建x86操作系统工程");
		// setErrorMessage(" A project with this name already exists.");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/image/window.png"));
		setPageComplete(false);
		setTitle("OS 工程");
		setDescription("创建x86操作系统工程");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		GridLayout gl_container = new GridLayout(4, false);
		gl_container.verticalSpacing = 2;
		container.setLayout(gl_container);

		Label lblPrjName = new Label(container, SWT.NONE);
		GridData gd_lblPrjName = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblPrjName.widthHint = 77;
		lblPrjName.setLayoutData(gd_lblPrjName);
		lblPrjName.setText("工程名");

		textPrjName = new Text(container, SWT.BORDER);
		textPrjName.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				String text = textPrjName.getText().trim();
				if (!text.isEmpty()) {
					if (!ResourcesPlugin.getWorkspace().getRoot().getProject(text).exists()) {
						setMessage("创建x86操作系统工程");
						setErrorMessage(null);
						projName = text;
						vaild(prjNameVaild, true);
					} else {
						setErrorMessage("该名字的工程存在！");
						vaild(prjNameVaild, false);
					}
				} else {
					vaild(prjNameVaild, false);
				}
			}
		});
		textPrjName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
	}

	public String getProjName() {
		return this.projName;
	}

	private void vaild(int field, boolean b) {
		if (b)
			this.isVaild |= field;
		else
			this.isVaild &= ~field;
		if (isVaild == 0x1) {
			setPageComplete(true);
			this.setErrorMessage(null);
		} else {
			setPageComplete(false);
		}
	}
}
