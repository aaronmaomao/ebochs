package com.mwos.editor.bin.ui.editor;

import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Composite;

public class SourcePage extends SourceViewer{

	public SourcePage(Composite parent, IVerticalRuler ruler, int styles) {
		super(parent, ruler, styles);
	}
	
}
