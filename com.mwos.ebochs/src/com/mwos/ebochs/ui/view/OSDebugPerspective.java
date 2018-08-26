package com.mwos.ebochs.ui.view;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IFolderLayout;

public class OSDebugPerspective implements IPerspectiveFactory {

	public OSDebugPerspective() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);
		layout.addView("com.mwos.ebochs.ui.view.DebugView", IPageLayout.RIGHT, 0.38f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("org.eclipse.ui.navigator.ProjectExplorer", IPageLayout.TOP, 0.37f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("org.eclipse.ui.console.ConsoleView", IPageLayout.RIGHT, 0.5f, "org.eclipse.ui.navigator.ProjectExplorer");
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
