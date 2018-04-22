package com.mwos.ebochs.ui.handler;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.e4.compatibility.SelectionService;

public class NewOSHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SelectionService selectionService = (SelectionService) Workbench.getInstance().getActiveWorkbenchWindow()
				.getSelectionService();
		ISelection selection = selectionService.getSelection();
		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof Project) {
				Project p = (Project) element;
				File src = new File(p.getLocationURI().getPath() + "/src");
				if (!src.exists())
					src.mkdirs();
				File inc = new File(p.getLocationURI().getPath() + "/inc");
				if (!inc.exists())
					inc.mkdir();
				try {
					p.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		SelectionService selectionService = (SelectionService) Workbench.getInstance().getActiveWorkbenchWindow()
				.getSelectionService();
		ISelection selection = selectionService.getSelection();
		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof Project) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
