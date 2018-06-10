package com.mwos.ebochs.ui.launch.run;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mwos.ebochs.resource.project.OSProject;
import com.mwos.ebochs.ui.preference.OSDevPreference;
import org.eclipse.swt.widgets.Combo;

public class MainTabRun extends AbstractLaunchConfigurationTab {
	private Text textBochs;
	private Text textVbox;
	private Text textBxrc;

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

			Combo comboProject = new Combo(groupPrj, SWT.READ_ONLY);
			
			List<IProject> projects = OSProject.getOSProject();
			for(IProject p:projects) {
				comboProject.add(p.getName());
			}
			if(comboProject.getItemCount()>0)
				comboProject.select(0);
			
			comboProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			Group groupPlatform = new Group(container, SWT.NONE);
			groupPlatform.setLayout(new GridLayout(3, false));
			groupPlatform.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			groupPlatform.setText("运行平台");

			Button btnBochs = new Button(groupPlatform, SWT.RADIO);

			btnBochs.setSelection(true);
			btnBochs.setText("Bochs");

			textBochs = new Text(groupPlatform, SWT.BORDER);
			textBochs.setEditable(false);
			textBochs.setText(OSDevPreference.getValue(OSDevPreference.BOCHS));
			GridData gd_textBochs = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_textBochs.widthHint = 596;
			textBochs.setLayoutData(gd_textBochs);
			new Label(groupPlatform, SWT.NONE);

			Label lblNewLabel = new Label(groupPlatform, SWT.NONE);
			lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblNewLabel.setText("bxrc  ");

			textBxrc = new Text(groupPlatform, SWT.BORDER);
			textBxrc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			Button btnBxrc = new Button(groupPlatform, SWT.NONE);
			btnBxrc.setText("浏览...");

			Button btnVbox = new Button(groupPlatform, SWT.RADIO);
			btnVbox.setText("VBox");

			textVbox = new Text(groupPlatform, SWT.BORDER);
			textVbox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			Button btnSelectVbox = new Button(groupPlatform, SWT.NONE);
			btnSelectVbox.setText("浏览...");

			btnBochs.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnBochs.getSelection()) {
						textBochs.setEnabled(true);
						textBxrc.setEnabled(true);
						btnBxrc.setEnabled(true);
						textVbox.setEnabled(false);
						btnSelectVbox.setEnabled(false);
					}
				}
			});

			btnVbox.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnVbox.getSelection()) {
						textBochs.setEnabled(false);
						textBxrc.setEnabled(false);
						btnBxrc.setEnabled(false);
						textVbox.setEnabled(true);
						btnSelectVbox.setEnabled(true);
					}
				}
			});
		}
		this.setControl(container);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return "Main";
	}
}
