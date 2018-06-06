package com.mwos.ebochs.ui.launch.debug;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import com.mwos.ebochs.ui.launch.run.MainTabRun;

public class LaunchTabGroupDebug extends AbstractLaunchConfigurationTabGroup {

	public LaunchTabGroupDebug() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		MainTabRun main = new MainTabRun();
		this.setTabs(new ILaunchConfigurationTab[] { main });
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub
		super.initializeFrom(configuration);
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
		super.performApply(configuration);
	}

}
