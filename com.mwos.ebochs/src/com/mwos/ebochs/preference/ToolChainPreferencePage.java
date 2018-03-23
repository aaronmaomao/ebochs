package com.mwos.ebochs.preference;

import java.io.File;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.mwos.ebochs.Activator;

public class ToolChainPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	private Text c2gasText;
	private Text gas2asmText;
	private Text asm2binText;
	private Text bin2bimText;
	private Text bim2mweText;

	String c2gas, gas2asm, asm2bin, bin2bim, bim2mwe;

	public ToolChainPreferencePage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ToolChainPreferencePage(String title) {
		super(title);
		setTitle("\u5DE5\u5177\u94FE");
		// TODO Auto-generated constructor stub
	}

	public ToolChainPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench) {
		c2gas = Activator.getDefault().getPreferenceStore().getString("c2gas_path");
		gas2asm = Activator.getDefault().getPreferenceStore().getString("gas2asm_path");
		asm2bin = Activator.getDefault().getPreferenceStore().getString("asm2bin_path");
		bin2bim = Activator.getDefault().getPreferenceStore().getString("bin2bim_path");
		bim2mwe = Activator.getDefault().getPreferenceStore().getString("bim2mwe_path");
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));

		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group.setText("\u5DE5\u5177\u94FE\u8BBE\u7F6E");
		group.setLayout(new GridLayout(3, false));

		Label label = new Label(group, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("C to gas");

		c2gasText = new Text(group, SWT.BORDER);
		c2gasText.setToolTipText("\u5C06.c\u6E90\u6587\u4EF6\u7F16\u8BD1\u4E3Agas\u6C47\u7F16");
		GridData gd_c2gasText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_c2gasText.minimumWidth = 200;
		c2gasText.setLayoutData(gd_c2gasText);
		c2gasText.setText(c2gas);

		Button c2gasBtn = new Button(group, SWT.NONE);
		c2gasBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String path = chooseFile("cc1.exe", container);
				c2gasText.setText(path);
				String toossPath = path.replace("cc1.exe", "");
				if (new File(toossPath + "gas2nask.exe").exists()) {
					gas2asmText.setText(toossPath + "gas2nask.exe");
				}
				if (new File(toossPath + "nask.exe").exists()) {
					asm2binText.setText(toossPath + "nask.exe");
				}
				if (new File(toossPath + "obj2bim.exe").exists()) {
					bin2bimText.setText(toossPath + "obj2bim.exe");
				}
				if (new File(toossPath + "bim2hrb.exe").exists()) {
					bim2mweText.setText(toossPath + "bim2hrb.exe");
				}
			}
		});
		c2gasBtn.setText("Browse...");

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("gas to asm");

		gas2asmText = new Text(group, SWT.BORDER);
		gas2asmText.setToolTipText("#\u5C06gas\u6C47\u7F16\u8F6C\u4E3Aasm\u6C47\u7F16");
		gas2asmText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gas2asmText.setText(gas2asm);

		Button gas2asmBtn = new Button(group, SWT.NONE);
		gas2asmBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		gas2asmBtn.setText("Browse...");

		Label label_2 = new Label(group, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("asm to bin");

		asm2binText = new Text(group, SWT.BORDER);
		asm2binText.setToolTipText("\u628Aasm\u6C47\u7F16\u4E3A\u4E8C\u8FDB\u5236\u76EE\u6807\u6587\u4EF6");
		asm2binText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		asm2binText.setText(asm2bin);

		Button asm2binBtn = new Button(group, SWT.NONE);
		asm2binBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		asm2binBtn.setText("Browse...");

		Label label_3 = new Label(group, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("bin to bim");

		bin2bimText = new Text(group, SWT.BORDER);
		bin2bimText.setToolTipText(
				"\u5C06\u76EE\u6807\u6587\u4EF6\u94FE\u63A5\u4E3A\u4E8C\u8FDB\u5236\u955C\u50CF\u6587\u4EF6");
		bin2bimText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		bin2bimText.setText(bin2bim);

		Button bin2bimBtn = new Button(group, SWT.NONE);
		bin2bimBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		bin2bimBtn.setText("Browse...");

		Label label_4 = new Label(group, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_4.setText("bim to mwe");

		bim2mweText = new Text(group, SWT.BORDER);
		bim2mweText.setToolTipText(
				"\u4E8C\u8FDB\u5236\u955C\u50CF\u6587\u4EF6\u6253\u5305\u4E3A\u53EF\u6267\u884C\u6587\u4EF6");
		bim2mweText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		bim2mweText.setText(bim2mwe);

		Button bim2mweBtn = new Button(group, SWT.NONE);
		bim2mweBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		bim2mweBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		bim2mweBtn.setText("Browse...");

		return container;
	}

	@Override
	public boolean performOk() {
		performApply();
		return true;
	}

	@Override
	public boolean performCancel() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void performDefaults() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void performApply() {
		Activator.getDefault().getPreferenceStore().setValue("c2gas_path", c2gasText.getText());
		Activator.getDefault().getPreferenceStore().setValue("gas2asm_path", gas2asmText.getText());
		Activator.getDefault().getPreferenceStore().setValue("asm2bin_path", asm2binText.getText());
		Activator.getDefault().getPreferenceStore().setValue("bin2bim_path", bin2bimText.getText());
		Activator.getDefault().getPreferenceStore().setValue("bim2mwe_path", bim2mweText.getText());
	}

	private String chooseFile(String name, Composite container) {
		FileDialog filedlg = new FileDialog(container.getShell(), SWT.OPEN);
		filedlg.setText("Ñ¡Ôñ" + name);
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
