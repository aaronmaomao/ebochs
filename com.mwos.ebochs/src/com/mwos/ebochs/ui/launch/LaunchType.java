package com.mwos.ebochs.ui.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.core.build.DefaultBuilder;
import com.mwos.ebochs.resource.config.OSConfigFactory;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.resource.project.OSProject;

public class LaunchType implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		String prj = configuration.getAttribute(MainTab.prj, "");
		IProject project = Activator.getOSProject(prj);
		try {
			OSConfig config = OSConfigFactory.getConfig(project);
			boolean res = config.build(AbstractBuilder.getBuilder(DefaultBuilder.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
