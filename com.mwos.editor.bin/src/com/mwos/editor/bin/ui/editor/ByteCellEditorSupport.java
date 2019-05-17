package com.mwos.editor.bin.ui.editor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.mwos.editor.bin.model.Byte16Item;
import com.mwos.editor.bin.service.Utils;

public class ByteCellEditorSupport extends EditingSupport {

	private TextCellEditor cellEditor;
	private TableViewerColumn column;
	private int byteIndex;

	public ByteCellEditorSupport(TableViewerColumn column) {
		super(column.getViewer());
		this.column = column;
		this.cellEditor = new TextCellEditor((Composite) this.getViewer().getControl(), SWT.NONE);
		((Text) cellEditor.getControl()).setTextLimit(2);
		byteIndex = getIndex();
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		if (element instanceof Byte16Item) {
			Byte value = ((Byte16Item) element).getByte(byteIndex);
			if (value != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof Byte16Item) {
			Byte value = ((Byte16Item) element).getByte(byteIndex);
			if (value != null) {
				return Utils.getHexString(value & 0x00ff, 2);
			} else {
				return "--";
			}
		}
		return "";
	}

	@Override
	protected void initializeCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
		super.initializeCellEditorValue(cellEditor, cell);
	}

	@Override
	protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
		if (cell.getElement() instanceof Byte16Item) {
			Byte16Item item = (Byte16Item) cell.getElement();
			try {
				Byte val = (byte) Integer.parseUnsignedInt(cellEditor.getValue().toString().trim(), 16);
				if (val.byteValue() != item.getByte(byteIndex).byteValue()) {
					item.setValue(val, byteIndex);
					
					Event event = new Event();
					event.data = item;
					event.index = byteIndex;
					this.getViewer().getControl().notifyListeners(SWT.Modify, event);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void setValue(Object element, Object value) {
	}

	private int getIndex() {
		switch (column.getColumn().getText()) {
		case "0":
			return 0;
		case "1":
			return 1;

		case "2":
			return 2;

		case "3":
			return 3;

		case "4":
			return 4;

		case "5":
			return 5;

		case "6":
			return 6;

		case "7":
			return 7;

		case "8":
			return 8;

		case "9":
			return 9;

		case "A":
			return 10;

		case "B":
			return 11;

		case "C":
			return 12;

		case "D":
			return 13;

		case "E":
			return 14;

		case "F":
			return 15;

		default:
			return 0;
		}
	}

}
