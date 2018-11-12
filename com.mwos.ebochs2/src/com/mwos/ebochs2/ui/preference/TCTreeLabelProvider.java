package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mwos.ebochs2.model.toolchain.Tool;
import com.mwos.ebochs2.model.toolchain.Toolchain;

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
				return toolchain.getName();
			case 1:
				return toolchain.getLocation();
			default:
				return "";
			}
		}else if(element instanceof Tool) {
			Tool tool = (Tool) element;
			switch (columnIndex) {
			case 0:
				return tool.getName();
			case 1:
				return tool.getPath();
			default:
				return "";
			}
		}
		return null;
	}

}
