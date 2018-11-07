package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.mwos.ebochs2.model.Toolchain;

public class TCTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		if (inputElement instanceof Toolchain[]) {
			Toolchain[] tcs = (Toolchain[]) inputElement;
			return tcs;
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Toolchain) {
			Toolchain toolchain = (Toolchain) parentElement;
			return toolchain.getTools().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		if (element instanceof Toolchain) {
			Toolchain toolchain = (Toolchain) element;
			if (toolchain.getTools().size() > 0) {
				return true;
			}
		}
		return false;
	}

}
