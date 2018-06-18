package com.mwos.ebochs.ui.preference;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

public class OSDevPreference extends PreferencePage implements IWorkbenchPreferencePage {

	public static final String TOOLCHAIN = Activator.PLUGIN_ID + ":default_toolchain";
	public static final String BOCHS = Activator.PLUGIN_ID + ":bochs";

	private Text textToolchain;
	private Text textBochs;

	private int isVaild = 0x11;
	private static final int toolChainVaild = 0x01;
	private static final int bochsVaild = 0x10;

	/**
	 * @wbp.parser.constructor
	 */
	public OSDevPreference() {
		setErrorMessage(" ");
		setTitle("操作系统开发");
		// TODO Auto-generated constructor stub
	}

	public OSDevPreference(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public OSDevPreference(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));

		Group groupToolchain = new Group(container, SWT.NONE);
		groupToolchain.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		groupToolchain.setText("工具链设置");
		groupToolchain.setLayout(new GridLayout(3, false));

		Label labelToolchain = new Label(groupToolchain, SWT.NONE);
		labelToolchain.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelToolchain.setText("工具链");

		textToolchain = new Text(groupToolchain, SWT.BORDER);
		textToolchain.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (textToolchain.getText().trim().isEmpty())
					return;
				File f = new File(textToolchain.getText().trim());
				if (f.exists() && f.isDirectory()) {
					vaild(OSDevPreference.toolChainVaild, true);
				} else {
					setErrorMessage("请选择工具链");
					vaild(OSDevPreference.toolChainVaild, false);
				}
			}
		});
		textToolchain.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textToolchain.setText(getValue(OSDevPreference.TOOLCHAIN));

		Button btnToolchain = new Button(groupToolchain, SWT.NONE);
		btnToolchain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String toochain = chooseFolder("工具链", container);
				if (StringUtils.isNotEmpty(toochain)) {
					textToolchain.setText(toochain);
				}
			}
		});
		btnToolchain.setText("浏览...");

		Group groupDbg = new Group(container, SWT.NONE);
		groupDbg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		groupDbg.setText("调试器设置");
		groupDbg.setLayout(new GridLayout(3, false));

		Label lblBochs = new Label(groupDbg, SWT.NONE);
		lblBochs.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBochs.setText("bochs");

		textBochs = new Text(groupDbg, SWT.BORDER);
		textBochs.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (textBochs.getText().trim().isEmpty())
					return;
				File f = new File(textBochs.getText().trim());
				if (f.exists() && f.isDirectory()) {
					vaild(OSDevPreference.bochsVaild, true);
				} else {
					setErrorMessage("请选择bochs");
					vaild(OSDevPreference.bochsVaild, false);
				}
			}
		});
		textBochs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textBochs.setText(getValue(OSDevPreference.BOCHS));

		Button btnBochs = new Button(groupDbg, SWT.NONE);
		btnBochs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String bochs = chooseFolder("bochs", container);
				if (StringUtils.isNotEmpty(bochs)) {
					textBochs.setText(bochs);
				}
			}
		});
		btnBochs.setText("浏览...");

		return container;
	}

	@Override
	protected void performApply() {
		setValue(OSDevPreference.TOOLCHAIN, textToolchain.getText().trim());
		setValue(OSDevPreference.BOCHS, textBochs.getText().trim());
	}

	@Override
	public boolean performOk() {
		performApply();
		return true;
	}

	@Override
	protected void performDefaults() {
		textToolchain.setText(getValue(OSDevPreference.TOOLCHAIN));
		textBochs.setText(getValue(OSDevPreference.BOCHS));
	}

	private void vaild(int field, boolean b) {
		if (b)
			this.isVaild |= field;
		else
			this.isVaild &= ~field;
		if (isVaild == 0x11) {
			this.setValid(true);
			this.setErrorMessage(null);
		} else {
			this.setValid(false);
		}
	}

	public static String getValue(String key) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getString(key);
	}

	public static void setValue(String key, String value) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(key, value);
	}

	private String chooseFile(String name, Composite container) {
		FileDialog filedlg = new FileDialog(container.getShell(), SWT.OPEN);
		filedlg.setText("选择" + name);
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

	private String chooseFolder(String name, Composite container) {
		DirectoryDialog filedlg = new DirectoryDialog(container.getShell(), SWT.OPEN);
		filedlg.setText("选择" + name);
		filedlg.setFilterPath("SystemDrive");
		filedlg.setMessage("");
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
