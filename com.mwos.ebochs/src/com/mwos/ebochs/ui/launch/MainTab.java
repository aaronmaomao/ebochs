package com.mwos.ebochs.ui.launch;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.ui.UiUtil;
import com.mwos.ebochs.ui.preference.OSDevPreference;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class MainTab extends AbstractLaunchConfigurationTab {
	public static final String bochsCfg = "bochsCfg";
	public static final String vboxCfg = "vboxCfg";
	public static final String bochsChk = "bochsChk";
	public static final String vboxChk = "vboxChk";
	public static final String prj = "prj";

	private Text textBochs;
	private Text textVBox;
	private boolean hasBochs = false;
	private boolean hasVbox = false;
	private Button chkBochs;
	private Button chkVBox;
	private Button btnBochs;
	private Button btnVBox;
	private Combo comboProject;

	private boolean isDebug;

	public MainTab(boolean isDebug) {
		// TODO Auto-generated constructor stub
		this.isDebug = isDebug;
		hasBochs = StringUtils.isNotEmpty(OSDevPreference.getValue(OSDevPreference.BOCHS));
		hasVbox = StringUtils.isNotEmpty(OSDevPreference.getValue(OSDevPreference.VBOX));
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			comboProject.setText(configuration.getAttribute(MainTab.prj, ""));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		if (chkBochs.getEnabled()) {
			try {
				chkBochs.setSelection((configuration.getAttribute(MainTab.bochsChk, false)));
				textBochs.setEnabled(chkBochs.getSelection());
				textBochs.setText(configuration.getAttribute(MainTab.bochsCfg, "<default>"));
				btnBochs.setEnabled(chkBochs.getSelection());
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}
		if (chkVBox.getEnabled()) {
			try {
				chkVBox.setSelection((configuration.getAttribute(MainTab.vboxChk, false)));
				textVBox.setEnabled(chkVBox.getSelection());
				textVBox.setText(configuration.getAttribute(MainTab.vboxCfg, "<default>"));
				btnVBox.setEnabled(chkVBox.getSelection());
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		{
			Group groupPrj = new Group(container, SWT.NONE);
			groupPrj.setText("选择工程");
			groupPrj.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			groupPrj.setLayout(new GridLayout(2, false));
			{
				Label labelPrj = new Label(groupPrj, SWT.NONE);
				labelPrj.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				labelPrj.setText("工程");
			}

			comboProject = new Combo(groupPrj, SWT.READ_ONLY);

			List<IProject> projects = Activator.getOSProject();
			for (IProject p : projects) {
				comboProject.add(p.getName());
			}
			comboProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		this.setControl(container);

		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group.setText("运行平台");
		group.setLayout(new GridLayout(3, false));

		chkBochs = new Button(group, SWT.CHECK);
		chkBochs.setText("Bochs");
		chkBochs.setEnabled(hasBochs);

		textBochs = new Text(group, SWT.BORDER);
		MainTab tab = this;
		textBochs.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setDirty(true);
				tab.updateLaunchConfigurationDialog();
			}
		});
		textBochs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnBochs = new Button(group, SWT.NONE);
		btnBochs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String bochsCfg = UiUtil.chooseFile(".bxrc", container);
				if (StringUtils.isNotEmpty(bochsCfg)) {
					textBochs.setText(bochsCfg);
				}
			}
		});
		btnBochs.setText("浏览...");
		chkBochs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textBochs.setEnabled(chkBochs.getSelection());
				btnBochs.setEnabled(chkBochs.getSelection());
			}
		});

		chkVBox = new Button(group, SWT.CHECK);
		chkVBox.setText("VBox ");
		chkVBox.setEnabled(hasVbox && !isDebug);

		textVBox = new Text(group, SWT.BORDER);
		textVBox.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setDirty(true);
				tab.updateLaunchConfigurationDialog();
			}
		});
		textVBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnVBox = new Button(group, SWT.NONE);
		btnVBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String vboxCfg = UiUtil.chooseFile(".vbox", container);
				if (StringUtils.isNotEmpty(vboxCfg)) {
					textVBox.setText(vboxCfg);
				}
			}
		});
		btnVBox.setText("浏览...");
		chkVBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textVBox.setEnabled(chkVBox.getSelection());
				btnVBox.setEnabled(chkVBox.getSelection());
			}
		});

	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(MainTab.bochsChk, chkBochs.getSelection());
		configuration.setAttribute(MainTab.vboxChk, chkVBox.getSelection());
		configuration.setAttribute(MainTab.bochsCfg, textBochs.getText());
		configuration.setAttribute(MainTab.vboxCfg, textVBox.getText());
		configuration.setAttribute(MainTab.prj, comboProject.getText());
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		return true;
	}

	@Override
	public String getName() {
		return "Main";
	}
}
