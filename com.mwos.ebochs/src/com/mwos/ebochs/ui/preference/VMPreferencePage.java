package com.mwos.ebochs.ui.preference;

import java.io.File;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.mwos.ebochs.Activator;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class VMPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	private Text bochsText;
	private Text bochsdbgText;

	private String bochs, bochsdbg;

	/**
	 * @wbp.parser.constructor
	 */
	public VMPreferencePage() {
		// TODO Auto-generated constructor stub
	}

	public VMPreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public VMPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench) {
		bochs = Activator.getDefault().getPreferenceStore().getString("bochs_path");
		bochsdbg = Activator.getDefault().getPreferenceStore().getString("bochsdbg_path");
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));

		Group grpBochs = new Group(container, SWT.NONE);
		grpBochs.setText("bochs\u8BBE\u7F6E");
		grpBochs.setLayout(new GridLayout(3, false));
		grpBochs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel = new Label(grpBochs, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("bochs.exe");

		bochsText = new Text(grpBochs, SWT.BORDER);
		bochsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		bochsText.setText(bochs);

		Button bochsBtn = new Button(grpBochs, SWT.NONE);
		bochsBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String path = chooseFile("bochs.exe", container);
				bochsText.setText(path);
				String toolsPath = path.replace("bochs.exe", "");
				if (new File(toolsPath + "bochsdbg.exe").exists()) {
					bochsdbgText.setText(toolsPath + "bochsdbg.exe");
				}
			}
		});
		bochsBtn.setText("Browse...");

		Label lblNewLabel_1 = new Label(grpBochs, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("bochsdbg.exe");

		bochsdbgText = new Text(grpBochs, SWT.BORDER);
		bochsdbgText.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));
		bochsdbgText.setText(bochsdbg);

		Button bochsdbgBtn = new Button(grpBochs, SWT.NONE);
		bochsdbgBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		bochsdbgBtn.setText("Browse...");

		return container;
	}

	@Override
	protected void performApply() {
		Activator.getDefault().getPreferenceStore().setValue("bochs_path", bochsText.getText());
		Activator.getDefault().getPreferenceStore().setValue("bochsdbg_path", bochsdbgText.getText());
	}

	private String chooseFile(String name, Composite container) {
		FileDialog filedlg = new FileDialog(container.getShell(), SWT.OPEN);
		filedlg.setText("ѡ��" + name);
		filedlg.setFilterPath("SystemRoot");
		filedlg.setFileName(name);
		filedlg.setFilterExtensions(new String[] { name });
		String selected = filedlg.open();
		if (selected != null) {
			File file = new File(selected);
			if (file.exists()) {
				return selected;
			}
		}
		return null;
	}
}
