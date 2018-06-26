package com.mwos.ebochs.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IFolderLayout;

public class OSDebugPerspective implements IPerspectiveFactory {

	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);
		layout.addView("com.mwos.ebochs.view.StackVPart", IPageLayout.RIGHT, 0.6f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("com.mwos.ebochs.view.MemVPart", IPageLayout.RIGHT, 0.5f, "com.mwos.ebochs.view.StackVPart");
		layout.addView("com.mwos.ebochs.ui.view.CPUView", IPageLayout.TOP, 0.5f, "com.mwos.ebochs.view.StackVPart");
		layout.addView("com.mwos.ebochs.view.GIdtVPart", IPageLayout.TOP, 0.52f, "com.mwos.ebochs.view.MemVPart");
		layout.addView("org.eclipse.ui.console.ConsoleView", IPageLayout.BOTTOM, 0.5f, "com.mwos.ebochs.view.GIdtVPart");
	}

	/**
	 * Add fast views to the perspective.
	 */
	private void addFastViews(IPageLayout layout) {
	}

	/**
	 * Add view shortcuts to the perspective.
	 */
	private void addViewShortcuts(IPageLayout layout) {
	}

	/**
	 * Add perspective shortcuts to the perspective.
	 */
	private void addPerspectiveShortcuts(IPageLayout layout) {
	}

}
