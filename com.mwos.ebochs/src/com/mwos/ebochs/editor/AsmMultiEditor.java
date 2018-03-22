package com.mwos.ebochs.editor;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.mwos.ebochs.Activator;

public class AsmMultiEditor extends MultiPageEditorPart implements IResourceChangeListener{

	public static final String ID = "com.mwos.ebochs.editor.AsmMultiEditor"; //$NON-NLS-1$

	public AsmMultiEditor() {
	}

	@Override
	protected void createPages() {

	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
