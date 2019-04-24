package com.mwos.editor.bin.ui.editor;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import com.mwos.editor.bin.model.Byte16Line;
import com.mwos.editor.bin.service.Utils;

public class ByteLabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
		Byte16Line element = (Byte16Line) cell.getElement();
		int colmn = cell.getColumnIndex();
		if (colmn == 0) {
			cell.setText("0x"+Utils.getHexString(element.getOffset(), 8));
		} else if (colmn >= 1 && colmn <= 16) {
			Integer value = element.getValue(colmn-1);
			if(value!=null) {
				cell.setText(Utils.getHexString(value, 2));
			}else {
				cell.setText("");
			}
		}else {
			cell.setText(element.toString());
		}
		
		super.update(cell);
	}
}
