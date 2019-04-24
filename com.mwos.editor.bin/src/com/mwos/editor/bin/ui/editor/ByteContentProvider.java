package com.mwos.editor.bin.ui.editor;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.mwos.editor.bin.model.Byte16Line;

public class ByteContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Byte16Line[]) {
			return (Byte16Line[]) inputElement;
		}
		return null;
	}

}
