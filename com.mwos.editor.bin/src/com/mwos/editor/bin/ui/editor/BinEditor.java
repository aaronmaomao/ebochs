package com.mwos.editor.bin.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.mwos.editor.bin.service.BinService;

public class BinEditor extends MultiPageEditorPart {

	public static final String ID = "com.mwos.editor.bin.ui.editor.BinEditor"; //$NON-NLS-1$

	private ViewPage viewPage;
	private SourcePage sourcePage;
	private BinService service;

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

		viewPage = new ViewPage(service,this.getContainer(), SWT.NONE);
		int page0 = this.addPage(viewPage);
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
		// TODO Auto-generated method stub
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}
}
