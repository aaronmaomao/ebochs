package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;

public class ToolPathEditing extends EditingSupport {
	
	private DialogCellEditor cellEditor;

	public ToolPathEditing(ColumnViewer viewer) {
		super(viewer);
		cellEditor = new DialogCellEditor((Composite) viewer.getControl(),SWT.NONE) {
			
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				FileDialog fd = new FileDialog(cellEditorWindow.getShell(), SWT.NONE);
				String path = fd.open();
				return path;
			}
		};
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		// TODO Auto-generated method stub
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		

	}

}
