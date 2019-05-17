package com.mwos.editor.bin.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.mwos.editor.bin.service.BinService;

public class BinEditor extends MultiPageEditorPart{

	public static final String ID = "com.mwos.editor.bin.ui.editor.BinEditor"; //$NON-NLS-1$

	private BinViewComposite viewPage;
	private SourcePage sourcePage;
	private BinService service;
	
	private boolean isDirty;

	public BinEditor() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		if (input instanceof FileEditorInput) {
			service = new BinService(((FileEditorInput) input).getPath().toFile());
		}
		this.setPartName(input.getName());
	}

	@Override
	protected void createPages() {
		
		int page0 = this.addPage(createBinViewPage());
		this.setPageText(page0, "视图");

		sourcePage = new SourcePage(this.getContainer(), null, SWT.NONE);
		int page1 = this.addPage(sourcePage.getTextWidget());
		this.setPageText(page1, "文本");
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		boolean result = service.save();
		if(result) {
			isDirty = false;
			firePropertyChange(PROP_DIRTY);
		}
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean isDirty() {
		if(isDirty) {
			return true;
		}
		return super.isDirty();
	}
	
	private Composite createBinViewPage() {
		viewPage = new BinViewComposite(service,this.getContainer(), SWT.NONE);
		viewPage.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event event) {
				isDirty = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		});

		return viewPage;
	}
}
