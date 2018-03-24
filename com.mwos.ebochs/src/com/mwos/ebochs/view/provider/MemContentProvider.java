package com.mwos.ebochs.view.provider;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.mwos.ebochs.view.entity.Memory;

public class MemContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List<?>) {
			return ((List<Memory>) inputElement).toArray();
		}
		return new Object[0];
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		IStructuredContentProvider.super.dispose();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		IStructuredContentProvider.super.inputChanged(viewer, oldInput, newInput);
	}

}
