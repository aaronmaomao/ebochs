package com.mwos.ebochs.view.provider;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mwos.ebochs.view.entity.Memory;

public class MemLabelProvider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Memory) {
			Memory mem = (Memory) element;
			switch (columnIndex) {
			case 0:
				return String.format("%08x", mem.getAddr()).toUpperCase();
			case 1:
				return String.format("%02x", mem.getM0()).toUpperCase();
			case 2:
				return String.format("%02x", mem.getM1()).toUpperCase();
			case 3:
				return String.format("%02x", mem.getM2()).toUpperCase();
			case 4:
				return String.format("%02x", mem.getM3()).toUpperCase();
			case 5:
				return String.format("%02x", mem.getM4()).toUpperCase();
			case 6:
				return String.format("%02x", mem.getM5()).toUpperCase();
			case 7:
				return String.format("%02x", mem.getM6()).toUpperCase();
			case 8:
				return String.format("%02x", mem.getM7()).toUpperCase();
			case 9:
				return String.format("%02x", mem.getM8()).toUpperCase();
			case 10:
				return String.format("%02x", mem.getM9()).toUpperCase();
			case 11:
				return String.format("%02x", mem.getMa()).toUpperCase();
			case 12:
				return String.format("%02x", mem.getMb()).toUpperCase();
			case 13:
				return String.format("%02x", mem.getMc()).toUpperCase();
			case 14:
				return String.format("%02x", mem.getMd()).toUpperCase();
			case 15:
				return String.format("%02x", mem.getMe()).toUpperCase();
			case 16:
				return String.format("%02x", mem.getMf()).toUpperCase();
			default:
				return null;
			}
		}
		return null;
	}

}
