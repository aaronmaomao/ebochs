package com.mwos.ebochs.ui.wizard;

import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.wb.swt.ResourceManager;

public class NewOSWizardPage extends WizardPage {
	private Text projtext;
	private Text locationText;
	private Text bochsText;
	private Text bochsdbgText;

	/**
	 * Create the wizard.
	 */
	public NewOSWizardPage() {
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/image/window.png"));
		setPageComplete(false);
		setTitle("OS Project");
		setDescription("Create new Operation System Project");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		final boolean canNext[] = { false, true, false, false };
																
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		GridLayout gl_container = new GridLayout(4, false);
		gl_container.verticalSpacing = 2;
		container.setLayout(gl_container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 77;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("Project name");

		projtext = new Text(container, SWT.BORDER);
		projtext.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				String text = projtext.getText();
				if (!text.trim().equals("")) {
					canNext[0] = true;
				} else {
					canNext[0] = false;
				}
				setCanNext(canNext);
			}
		});
		projtext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Button btnCheckButton = new Button(container, SWT.CHECK);
		btnCheckButton.setSelection(true);

		GridData gd_btnCheckButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_btnCheckButton.verticalIndent = 10;
		gd_btnCheckButton.heightHint = 16;
		btnCheckButton.setLayoutData(gd_btnCheckButton);
		btnCheckButton.setText("Use default location");

		Label lblLocation = new Label(container, SWT.NONE);
		lblLocation.setEnabled(false);
		lblLocation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLocation.setText("location");

		locationText = new Text(container, SWT.BORDER);
		locationText.setEnabled(false);
		locationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.setEnabled(false);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog folderdlg = new DirectoryDialog(container.getShell());
				folderdlg.setText("�ļ�ѡ��");
				folderdlg.setFilterPath("SystemDrive");
				folderdlg.setMessage("��ѡ����Ӧ���ļ���");
				String selecteddir = folderdlg.open();
				if (selecteddir != null) {
					locationText.setText(selecteddir);
				}
				setCanNext(canNext);
			}
		});
		btnNewButton.setText("Browse...");

		Group grpBochsSetting = new Group(container, SWT.NONE);
		grpBochsSetting.setText("Bochs setting");
		GridLayout gl_grpBochsSetting = new GridLayout(3, false);
		gl_grpBochsSetting.horizontalSpacing = 4;
		grpBochsSetting.setLayout(gl_grpBochsSetting);
		GridData gd_grpBochsSetting = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1);
		gd_grpBochsSetting.heightHint = 77;
		gd_grpBochsSetting.widthHint = 564;
		grpBochsSetting.setLayoutData(gd_grpBochsSetting);

		Label lblNewLabel_1 = new Label(grpBochsSetting, SWT.NONE);
		lblNewLabel_1.setText(" bochs");

		bochsText = new Text(grpBochsSetting, SWT.BORDER);
		bochsText.setEditable(false);
		GridData gd_bochsText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_bochsText.horizontalIndent = 7;
		bochsText.setLayoutData(gd_bochsText);

		Button btnNewButton_1 = new Button(grpBochsSetting, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog filedlg = new FileDialog(container.getShell(), SWT.OPEN);
				filedlg.setText("ѡ��bochs.exe");
				filedlg.setFilterPath("SystemRoot");
				filedlg.setFileName("bochs.exe");
				filedlg.setFilterExtensions(new String[] { "bochs.exe" });
				String selected = filedlg.open();
				if (selected != null) {
					File file = new File(selected);
					if (file.exists()) {
						bochsText.setText(selected);
						canNext[2] = true;
						selected = selected.replace("bochs.exe", "") + "bochsdbg.exe";
						file = new File(selected);
						if (file.exists()) {
							bochsdbgText.setText(selected);
							canNext[3] = true;
						}
					} else {
						bochsText.setText("");
						canNext[2] = false;
					}
				}
				setCanNext(canNext);
			}
		});
		btnNewButton_1.setText("Browse...");

		Label lblBochsdbg = new Label(grpBochsSetting, SWT.NONE);
		lblBochsdbg.setText(" bochsdbg");

		bochsdbgText = new Text(grpBochsSetting, SWT.BORDER);
		bochsdbgText.setEditable(false);
		GridData gd_bochsdbgText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_bochsdbgText.horizontalIndent = 7;
		bochsdbgText.setLayoutData(gd_bochsdbgText);

		Button button = new Button(grpBochsSetting, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog filedlg = new FileDialog(container.getShell(), SWT.OPEN);
				filedlg.setText("ѡ��bochsdbg.exe");
				filedlg.setFilterPath("SystemRoot");
				filedlg.setFileName("bochsdbg.exe");
				filedlg.setFilterExtensions(new String[] { "bochsdbg.exe" });
				String selected = filedlg.open();
				if (selected != null) {
					File file = new File(selected);
					if (file.exists()) {
						bochsdbgText.setText(selected);
						canNext[3] = true;
					} else {
						bochsdbgText.setText("");
						canNext[3] = false;
					}
				}
				setCanNext(canNext);
			}
		});
		button.setText("Browse...");
		new Label(grpBochsSetting, SWT.NONE);
		new Label(grpBochsSetting, SWT.NONE);
		new Label(grpBochsSetting, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnCheckButton.getSelection()) {
					lblLocation.setEnabled(false);
					locationText.setEnabled(false);
					btnNewButton.setEnabled(false);
				} else {
					lblLocation.setEnabled(true);
					locationText.setEnabled(true);
					btnNewButton.setEnabled(true);
				}
			}
		});
	}

	private void setCanNext(boolean can[]) {
		boolean r = true;
		for (boolean b : can) {
			r &= b;
		}
		setPageComplete(r);
	}
}
