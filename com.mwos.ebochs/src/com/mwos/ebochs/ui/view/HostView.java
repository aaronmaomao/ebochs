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
import com.mwos.ebochs.core.model.cmd.CmdFactory;
import com.mwos.ebochs.ui.view.model.host.HostNode;
import com.mwos.ebochs.ui.view.model.host.Node;

import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;

import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.jface.action.Separator;

public class HostView extends ViewPart implements IInfoListener {

	public static final String ID = "com.mwos.ebochs.ui.view.HostView"; //$NON-NLS-1$

	private boolean enable = false;
	private HostNode root = null;
	private Tree hostTree;
	private Action actionStepOver;
	private Action actionStepInto;
	private Action actionStepReturn;
	private Action actionContinue;
	private Action actionTerminate;
	private Action actionBp;

	public HostView() {
		center.addListener(this);
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
		{
			actionStepOver = new Action("stepover") {
				@Override
				public void runWithEvent(Event event) {
					// TODO Auto-generated method stub
					System.out.println(event);
					run();
				}

				@Override
				public void run() {
					// TODO Auto-generated method stub
				}
			};
			actionStepOver.setId("actionStepOver");
			actionStepOver.setEnabled(false);
			actionStepOver.setAccelerator(SWT.F6);
			actionStepOver.setToolTipText("stepover");
			actionStepOver.setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui",
					"/icons/full/elcl16/stepover_co.png"));

		}
		{
			actionStepInto = new Action("StepInto") {
				public void runWithEvent(Event event) {
					// TODO Auto-generated method stub
					System.out.println(event);
					run();
				}

				@Override
				public void run() {
					// TODO Auto-generated method stub
				}
			};
			actionStepInto.setId("actionStepInto");
			actionStepInto.setEnabled(false);
			actionStepInto.setAccelerator(SWT.F7);
			actionStepInto.setToolTipText("StepInto");
			actionStepInto.setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui",
					"/icons/full/elcl16/stepinto_co.png"));
		}
		{
			actionStepReturn = new Action("StepReturn") {
				public void runWithEvent(Event event) {
					// TODO Auto-generated method stub
					System.out.println(event);
					run();
				}

				@Override
				public void run() {
					// TODO Auto-generated method stub
				}
			};
			actionStepReturn.setId("actionStepReturn");
			actionStepReturn.setEnabled(false);
			actionStepReturn.setAccelerator(SWT.F8);
			actionStepReturn.setToolTipText("StepReturn");
			actionStepReturn.setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui",
					"/icons/full/elcl16/stepreturn_co.png"));
		}
		{
			actionContinue = new Action("continue") {
				public void runWithEvent(Event event) {
					// TODO Auto-generated method stub
					System.out.println(event);
					run();
				}

				@Override
				public void run() {
					// TODO Auto-generated method stub
				}
			};
			actionContinue.setId("actionContinue");
			actionContinue.setEnabled(false);
			actionContinue.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("org.eclipse.egit.ui", "/icons/elcl16/continue.png"));
			actionContinue.setToolTipText("continue");
		}
		{
			actionTerminate = new Action("terminate") {
				public void runWithEvent(Event event) {
					// TODO Auto-generated method stub
					System.out.println(event);
					run();
				}

				@Override
				public void run() {
					// TODO Auto-generated method stub
				}
			};
			actionTerminate.setId("actionTerminate");
			actionTerminate.setEnabled(false);
			actionTerminate.setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui",
					"/icons/full/elcl16/terminate_co.png"));
			actionTerminate.setToolTipText("terminate");
		}
		{
			actionBp = new Action("breakpoint") {
				public void runWithEvent(Event event) {
					// TODO Auto-generated method stub
					System.out.println(event);
					run();
				}

				@Override
				public void run() {
					this.setChecked(!this.isChecked());
				}
			};
			actionBp.setToolTipText("breakpoint");
			actionBp.setEnabled(false);
			actionBp.setId("actionBp");
			actionBp.setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui",
					"/icons/full/elcl16/skip_brkp.png"));
		}
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();

		toolbarManager.add(actionBp);
		toolbarManager.add(actionContinue);
		toolbarManager.add(actionTerminate);
		toolbarManager.add(new Separator());
		toolbarManager.add(actionStepOver);
		toolbarManager.add(actionStepInto);
		toolbarManager.add(actionStepReturn);

	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
		// menuManager.add(actionBp);
		// menuManager.add(actionStepInto);
		// menuManager.add(actionStepReturn);
		// menuManager.add(new Separator());
		// menuManager.add(actionContinue);
		// menuManager.add(actionTerminate);
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
		// hostTree.select(root);
		// if (!infoCenter.isActive()) {
		// root.setText("no vm run");
		// return;
		// }
		//
		// root.setText("Debug Bochs at:" + getHostInfo());
	}

	private void initTree(Node node) {

	}

	// private String getHostInfo() {
	// return (String) infoCenter.synSend(InfoCmd.Host_Get);
	// }

	@Override
	public void dispose() {
		this.send(CmdFactory.removeListener, this);
		super.dispose();
	}
}
