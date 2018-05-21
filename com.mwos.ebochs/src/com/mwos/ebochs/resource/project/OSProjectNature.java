package com.mwos.ebochs.resource.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.mwos.ebochs.core.build.ProjectBuilder;

public class OSProjectNature implements IProjectNature {

	private IProject project;
	public static final String NatureId = "com.mwos.ebochs.osnature";

	@Override
	public void configure() throws CoreException {
		IProjectDescription des = project.getDescription();
		ICommand newCommand = des.newCommand();
		newCommand.setBuilderName(ProjectBuilder.ID);
		des.setBuildSpec(new ICommand[] { newCommand });
		project.setDescription(des, null);
	}

	@Override
	public void deconfigure() throws CoreException {
		IProjectDescription des = project.getDescription();
		ICommand[] commands = des.getBuildSpec();
		List<ICommand> cmds = new ArrayList<>();
		for (ICommand command : commands) {
			if (!command.getBuilderName().equals(ProjectBuilder.ID)) {
				cmds.add(command);
			}
		}
		des.setBuildSpec((ICommand[]) cmds.toArray());
		project.setDescription(des, null);
	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return this.project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
}
