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
		{
			IFolderLayout folderLayout = layout.createFolder("folder_1", IPageLayout.TOP, 0.5f, IPageLayout.ID_EDITOR_AREA);
			folderLayout.addView("com.mwos.ebochs.ui.view.HostView");
		}
		layout.addView("org.eclipse.ui.navigator.ProjectExplorer", IPageLayout.LEFT, 0.23f, "folder_1");
		layout.addView("com.mwos.ebochs.ui.view.RegisterView", IPageLayout.RIGHT, 0.49f, "com.mwos.ebochs.ui.view.HostView");
		{
			IFolderLayout folderLayout = layout.createFolder("folder", IPageLayout.RIGHT, 0.5f, "com.mwos.ebochs.ui.view.RegisterView");
			folderLayout.addView("com.mwos.ebochs.ui.view.GILDTView");
			folderLayout.addView("com.mwos.ebochs.ui.view.BreakPointView");
		}
		layout.addView("org.eclipse.ui.console.ConsoleView", IPageLayout.RIGHT, 0.61f, IPageLayout.ID_EDITOR_AREA);
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
