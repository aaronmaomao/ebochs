package com.mwos.ebochs.resource.project;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.ResourceManager;

import com.mwos.ebochs.Activator;

public class OSPrjLabelDecorator implements ILabelDecorator {

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

	// 更改文件夹图标
	@Override
	public Image decorateImage(Image image, Object element) {
		if (element instanceof Folder && ((Folder) element).getName().equals("Library")) {
			return Activator.getImage("resource/icons/basket_go.png");
		}
		return null;
	}

	@Override
	public String decorateText(String text, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

}
