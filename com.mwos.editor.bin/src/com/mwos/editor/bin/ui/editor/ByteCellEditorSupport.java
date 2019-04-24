package com.mwos.editor.bin.ui.editor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ByteCellEditorSupport extends EditingSupport{
	
	private TextCellEditor cellEditor;
	
	public ByteCellEditorSupport(ColumnViewer viewer) {
		super(viewer);
		cellEditor = new TextCellEditor((Composite) this.getViewer().getControl(), SWT.NONE);
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return element.toString();
	}

	@Override
	protected void setValue(Object element, Object value) {
		
	}

}
