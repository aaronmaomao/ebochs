package com.mwos.ebochs.ui.launch;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.ui.CDTUITools;
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.core.build.DefaultBuilder;
import com.mwos.ebochs.core.model.InfoCenter;
import com.mwos.ebochs.core.vm.bochs.Bochs;
import com.mwos.ebochs.core.vm.bochs.Bxrc;
import com.mwos.ebochs.core.vm.bochs.DebugModel;
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
			} else if (mode.equals("debug")) {
				String bochsDir = OSDevPreference.getValue(OSDevPreference.BOCHS);
				Bxrc bxrc = new Bxrc(config, bochsDir);
				Bochs bochs = new Bochs(bochsDir, bxrc);
				Process process = bochs.debug();
				InfoCenter.getInfoCenter().setDebug(new DebugModel(process, config));
				// InfoCenter.getInfoCenter().addVm(bochs);
				PlatformUI.getWorkbench().showPerspective("com.mwos.ebochs.perspective.OSDebugPerspective",
						PlatformUI.getWorkbench().getActiveWorkbenchWindow());

				ICProject cproject = CoreModel.getDefault().getCModel().getCProject(project.getName());
				ICElement element = cproject.findElement(project.getFile("src/mbr.asm").getProjectRelativePath());
				IEditorPart ed = CDTUITools.openInEditor(element);
				ed.getEditorInput();
				// IMarker mark =
				// project.getFile("src/mbr.asm").createMarker("com.ebochs.DebugMarker");
				// mark.setAttribute(IMarker.LINE_NUMBER, 15);
				// mark.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
				// mark.setAttribute(IMarker, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
