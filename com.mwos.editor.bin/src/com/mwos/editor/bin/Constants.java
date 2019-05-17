package com.mwos.editor.bin;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class Constants {
	
	public static Color getColor(int id) {
		return Display.getCurrent().getSystemColor(id);
	}
}
