package com.mwos.ebochs.ui.launch;

import java.io.File;
import java.io.InputStream;

import org.eclipse.cdt.utils.spawner.ProcessFactory;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IProcessFactory;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.core.BochsBxrc;
import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.core.build.DefaultBuilder;
import com.mwos.ebochs.resource.config.OSConfigFactory;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public class LaunchType implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		String prj = configuration.getAttribute(MainTab.prj, "");

		IProject project = Activator.getOSProject(prj);
		String prjPath = FileUtil.formatPath(project.getLocationURI().getPath());
		try {
			OSConfig config = OSConfigFactory.getConfig(project);
			boolean res = config.build(AbstractBuilder.getBuilder(DefaultBuilder.class));
			if (!res)
				return;
			String bochs = OSDevPreference.getValue(OSDevPreference.BOCHS);
			InputStream bochsTemp = Activator.getDefault().getBundle().getResource("com/mwos/ebochs/core/bochsrc.bxrc").openStream();
			BochsBxrc bochsBxrc = new BochsBxrc(bochsTemp, bochs);
			bochsTemp.close();

			bochsBxrc.setBoot(config.getImages().get(0).getDevice());
			bochsBxrc.setFloppya(prjPath + "/obj/images/" + config.getImages().get(0).getName());
			File out = new File(prjPath + "/obj/bochs.bxrc");
			bochsBxrc.localize(out);

			String exe = bochs + "/bochsdbg.exe -q -f " + prjPath + "/obj/bochs.bxrc";
			Process process = Runtime.getRuntime().exec(exe, new String[] {}, new File(prjPath));
			IProcess iProcess = DebugPlugin.newProcess(launch, process, null);
			iProcess.getStreamsProxy().getOutputStreamMonitor().addListener(new IStreamListener() {

				@Override
				public void streamAppended(String text, IStreamMonitor monitor) {
					System.out.println(text);
				}
			});
			
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
