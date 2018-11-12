package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.mwos.ebochs2.model.toolchain.Tool;

public class ToolTableContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Tool[]) {
			Tool[] tools = (Tool[]) inputElement;
			return tools;
		}
		return new Tool[] {};
	}

}
