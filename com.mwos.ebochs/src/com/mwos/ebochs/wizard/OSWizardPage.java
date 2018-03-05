package com.mwos.ebochs.wizard;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class OSWizardPage extends WizardPage {

	private ISelection selection;
	private String name;

	public OSWizardPage(ISelection selection) {
		super("");
		this.selection = selection;
		this.setTitle("新建OS工程");
		this.setDescription("描述");
		this.setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		Text text = new Text(composite, SWT.BORDER);
		text.setText("设定名称");
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				String t = text.getText();
				if (!t.equals("0") && !t.equals("")) {
					setPageComplete(true);
					setErrorMessage(null);
				} else {
					setPageComplete(false);
					setErrorMessage("不能为0");
				}
			}
		});
		setControl(composite);
	}
}
