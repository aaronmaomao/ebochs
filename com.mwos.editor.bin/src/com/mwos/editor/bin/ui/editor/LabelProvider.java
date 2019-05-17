package com.mwos.editor.bin.ui.editor;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;

import com.mwos.editor.bin.Constants;
import com.mwos.editor.bin.model.Byte16Item;
import com.mwos.editor.bin.service.Utils;

public class LabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
		Byte16Item element = (Byte16Item) cell.getElement();
		int colmn = cell.getColumnIndex();
		if (colmn == 0) {
			if (element.getOffset() < 0) {
				cell.setText("----------");
			} else {
				cell.setText("0x" + Utils.getHexString(element.getOffset(), 8));
			}
		} else if (colmn >= 1 && colmn <= 16) {
			Byte value = element.getByte(colmn - 1);
			if (value != null) {
				cell.setText(Utils.getHexString(value & 0x00ff, 2));
			} else {
				cell.setText("--");
			}
			cell.setForeground(element.isDirty(colmn-1)?Constants.getColor(SWT.COLOR_RED):Constants.getColor(SWT.COLOR_BLACK));
		} else {
			cell.setText(element.toString());
		}

		super.update(cell);
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		if (property.equals("valueChanged")) {
			return true;
		}
		return super.isLabelProperty(element, property);
	}
}
