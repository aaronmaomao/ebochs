package com.mwos.ebochs.ui.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.ui.PlatformUI;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.core.build.DefaultBuilder;
import com.mwos.ebochs.core.model.InfoCenter;
import com.mwos.ebochs.core.model.cmd.CmdStr;
import com.mwos.ebochs.core.vm.bochs.Bochs;
import com.mwos.ebochs.core.vm.bochs.Bxrc;
import com.mwos.ebochs.core.vm.bochs.DebugModel;
import com.mwos.ebochs.core.vm.vbox.PVbox;
import com.mwos.ebochs.core.vm.vbox.Vbox;
import com.mwos.ebochs.resource.config.OSConfigFactory;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public class LaunchType implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		try {
			String prj = configuration.getAttribute(MainTab.prj, "");
			if (prj.isEmpty())
				return;
			IProject project = Activator.getOSProject(prj);
			OSConfig config = OSConfigFactory.getConfig(project);
			boolean res = config.build(AbstractBuilder.getBuilder(DefaultBuilder.class));
			if (!res)
				return;

			if (mode.equals("run")) {
				// run bochs
				if (configuration.getAttribute(MainTab.bochsChk, false)) {
					String bochsDir = OSDevPreference.getValue(OSDevPreference.BOCHS);
					Bxrc bxrc = new Bxrc(config, bochsDir);
					Bochs bochs = new Bochs(bochsDir, bxrc);
					Process process = bochs.run();
					IProcess iProcess = DebugPlugin.newProcess(launch, process, "Run " + project.getName() + " on " + bochs.getName());
					iProcess.getStreamsProxy().getOutputStreamMonitor().addListener(new IStreamListener() {
						@Override
						public void streamAppended(String text, IStreamMonitor monitor) {
							System.out.println(text);
						}
					});
				}
				if(configuration.getAttribute(MainTab.vboxChk, false)) {
					String vboxDir = OSDevPreference.getValue(OSDevPreference.VBOX);
					PVbox pvbox = new PVbox(config, vboxDir);
					Vbox box = new Vbox(vboxDir, pvbox);
					Process process = box.run();
//					IProcess iProcess = DebugPlugin.newProcess(launch, process, "Run " + project.getName() + " on " + bochs.getName());
//					iProcess.getStreamsProxy().getOutputStreamMonitor().addListener(new IStreamListener() {
//						@Override
//						public void streamAppended(String text, IStreamMonitor monitor) {
//							System.out.println(text);
//						}
//					});
				}
			} else if (mode.equals("debug")) {
				String bochsDir = OSDevPreference.getValue(OSDevPreference.BOCHS);
				Bxrc bxrc = new Bxrc(config, bochsDir);
				Bochs bochs = new Bochs(bochsDir, bxrc);
				Process process = bochs.debug();
				DebugModel dm = new DebugModel(process, config);

				PlatformUI.getWorkbench().showPerspective("com.mwos.ebochs.ui.view.OSPerspective",
						PlatformUI.getWorkbench().getActiveWorkbenchWindow());

				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

					@Override
					public void run() {
						InfoCenter.getInfoCenter().send(CmdStr.AddDM, dm);

					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
