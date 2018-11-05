package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mwos.ebochs2.model.Tool;
import com.mwos.ebochs2.model.Toolchain;

public class TCTreeLabelProvider implements ITableLabelProvider{

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
		if(element instanceof Toolchain) {
			Toolchain toolchain = (Toolchain) element;
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				return toolchain.getName();
			case 2:
				return toolchain.getLocation();
			default:
				return "";
			}
		}else if(element instanceof Tool) {
			Tool tool = (Tool) element;
			switch (columnIndex) {
			case 1:
				return tool.getName();
			case 2:
				return tool.getPath();
			default:
				return "";
			}
		}
		return null;
	}

}
