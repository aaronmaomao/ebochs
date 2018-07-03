package com.mwos.ebochs.ui.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.part.ViewPart;

import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.InfoCenter;
import com.mwos.ebochs.core.model.InfoCmd;
import com.mwos.ebochs.ui.view.model.host.HostNode;
import com.mwos.ebochs.ui.view.model.host.Node;
import org.eclipse.swt.events.MouseAdapter;

public class HostView extends ViewPart implements IInfoListener {

	public static final String ID = "com.mwos.ebochs.ui.view.HostView"; //$NON-NLS-1$

	private InfoCenter infoCenter = InfoCenter.getInfoCenter();
	private boolean enable = false;
	private HostNode root = null;
	private Tree hostTree;

	public HostView() {
		infoCenter.addListener(this);
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		hostTree = new Tree(container, SWT.BORDER);
		hostTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {
					if (hostTree.getSelection().length > 0) {
						Node node = (Node) hostTree.getSelection()[0];
						node.showMenu();
					}
				}
			}
		});
		hostTree.setLinesVisible(true);
		root = new HostNode(hostTree, SWT.NONE);
		// root = new HostNode(hostTree, SWT.NONE);

		init();
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void notify(Object info) {
		if (info.equals("HostChanged")) {
			init();
		}
	}

	@Override
	public void notify(String cmd, Object info) {
		// TODO Auto-generated method stub

	}

	public void init() {
//		hostTree.select(root);
//		if (!infoCenter.isActive()) {
//			root.setText("no vm run");
//			return;
//		}
//
//		root.setText("Debug Bochs at:" + getHostInfo());
	}

	private void initTree(Node node) {
	}

//	private String getHostInfo() {
//		return (String) infoCenter.synSend(InfoCmd.Host_Get);
//	}

	@Override
	public void dispose() {
		super.dispose();
		infoCenter.removeListener(this);
	}
}
