package com.mwos.editor.bin.ui.editor;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.mwos.editor.bin.model.Byte16Item;

public class ContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Byte16Item[]) {
			return (Byte16Item[]) inputElement;
		}
		return null;
	}

}
