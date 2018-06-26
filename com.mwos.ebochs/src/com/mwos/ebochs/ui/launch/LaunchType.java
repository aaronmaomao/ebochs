package com.mwos.ebochs.ui.launch;

import java.io.File;
import java.io.InputStream;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.core.build.DefaultBuilder;
import com.mwos.ebochs.core.vm.bochs.Bochs;
import com.mwos.ebochs.core.vm.bochs.Bxrc;
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
			String prjPath = FileUtil.formatPath(project.getLocationURI().getPath());
			OSConfig config = OSConfigFactory.getConfig(project);
			boolean res = config.build(AbstractBuilder.getBuilder(DefaultBuilder.class));
			if (!res)
				return;

			if (mode.equals("run")) {
				// run bochs
				if (configuration.getAttribute(MainTab.bochsChk, false)) {
					String bochsDir = OSDevPreference.getValue(OSDevPreference.BOCHS);
					String bxrc = configuration.getAttribute(MainTab.bochsCfg, MainTab.default_bxrc);
					if (!bxrc.contains(":")) {
						InputStream bochsTemp = Activator.getDefault().getBundle().getResource("com/mwos/ebochs/core/default.bxrc").openStream();
						Bxrc bochsBxrc = new Bxrc(bochsTemp, bochsDir);
						bochsTemp.close();
						bochsBxrc.setBoot(config.getImages().get(0).getDevice());
						bochsBxrc.setFloppya(prjPath + "/obj/images/" + config.getImages().get(0).getName());
						File out = new File(prjPath + bxrc);
						bochsBxrc.localize(out);
						bxrc = out.getAbsolutePath();
					}
					Bochs bochs = new Bochs(bochsDir);
					Process process = bochs.run(bxrc);
					IProcess iProcess = DebugPlugin.newProcess(launch, process, "Run " + project.getName() + " on " + bochs.getName());
					iProcess.getStreamsProxy().getOutputStreamMonitor().addListener(new IStreamListener() {

						@Override
						public void streamAppended(String text, IStreamMonitor monitor) {
							System.out.println(text);
						}
					});
				}

			} else if (mode.equals("debug")) {
				String bochsDir = OSDevPreference.getValue(OSDevPreference.BOCHS);
				String bxrc = configuration.getAttribute(MainTab.bochsCfg, MainTab.default_bxrc);
				if (!bxrc.contains(":")) {
					InputStream bochsTemp = Activator.getDefault().getBundle().getResource("com/mwos/ebochs/core/default.bxrc").openStream();
					Bxrc bochsBxrc = new Bxrc(bochsTemp, bochsDir);
					bochsTemp.close();
					bochsBxrc.setBoot(config.getImages().get(0).getDevice());
					bochsBxrc.setFloppya(prjPath + "/obj/images/" + config.getImages().get(0).getName());
					File out = new File(prjPath + bxrc);
					bochsBxrc.localize(out);
					bxrc = out.getAbsolutePath();
				}
				Bochs bochs = new Bochs(bochsDir);
				Process process = bochs.debug(bxrc);
				IProcess iProcess = DebugPlugin.newProcess(launch, process, "Debug " + project.getName() + " on " + bochs.getName());
				iProcess.getStreamsProxy().getOutputStreamMonitor().addListener(new IStreamListener() {

					@Override
					public void streamAppended(String text, IStreamMonitor monitor) {
						System.out.println(text);
					}
				});
				
				
			}

			DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(new IBreakpointListener() {

				@Override
				public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
					// TODO Auto-generated method stub
				}

				@Override
				public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
					// TODO Auto-generated method stub

				}

				@Override
				public void breakpointAdded(IBreakpoint breakpoint) {
					// TODO Auto-generated method stub
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
