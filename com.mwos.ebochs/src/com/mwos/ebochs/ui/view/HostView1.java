package com.mwos.ebochs.ui.view;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.GotoLineAction;
import org.eclipse.wb.swt.ResourceManager;

import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.cmd.CmdStr;
import com.mwos.ebochs.core.vm.bochs.DebugModel;
import com.mwos.ebochs.ui.view.model.host.HostNode;
import com.mwos.ebochs.ui.view.model.host.Node;

public class HostView extends ViewPart implements IInfoListener {

	public static final String ID = "com.mwos.ebochs.ui.view.HostView"; //$NON-NLS-1$

	private Tree hostTree;
	private Action actionStepOver;
	private Action actionStepInto;
	private Action actionStepReturn;
	private Action actionContinue;
	private Action actionTerminate;
	private Action actionBp;

	private Set<String> cares = new HashSet<>();

	public HostView() {
		this.cares.add(CmdStr.AddDM);
		this.cares.add(CmdStr.DestoryDM);
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
		hostTree.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.getSource() instanceof Tree) {
					if (hostTree.getSelection().length > 0) {
						sendToCenter(CmdStr.SelectDM, ((Node) hostTree.getSelection()[0]).getDm());
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
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
					run();
				}

				@Override
				public void run() {
					stepOver();
				}
			};
			actionStepOver.setId("actionStepOver");
			actionStepOver.setEnabled(false);
			actionStepOver.setAccelerator(SWT.F6);
			actionStepOver.setToolTipText("stepover");
			actionStepOver.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui", "/icons/full/elcl16/stepover_co.png"));

		}
		{
			actionStepInto = new Action("StepInto") {
				public void runWithEvent(Event event) {
					run();
				}

				@Override
				public void run() {
					stepInto();
				}
			};
			actionStepInto.setId("actionStepInto");
			actionStepInto.setEnabled(false);
			actionStepInto.setAccelerator(SWT.F7);
			actionStepInto.setToolTipText("StepInto");
			actionStepInto.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui", "/icons/full/elcl16/stepinto_co.png"));
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
			actionStepReturn.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui", "/icons/full/elcl16/stepreturn_co.png"));
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
					contin();
				}
			};
			actionContinue.setId("actionContinue");
			actionContinue.setEnabled(false);
			actionContinue
					.setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.egit.ui", "/icons/elcl16/continue.png"));
			actionContinue.setToolTipText("continue");
		}
		{
			actionTerminate = new Action("terminate") {
				public void runWithEvent(Event event) {
					run();
				}

				@Override
				public void run() {
					terminal();
				}
			};
			actionTerminate.setId("actionTerminate");
			actionTerminate.setEnabled(false);
			actionTerminate.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui", "/icons/full/elcl16/terminate_co.png"));
			actionTerminate.setToolTipText("terminate");
		}
		{
			actionBp = new Action("breakpoint") {
				public void runWithEvent(Event event) {
					// TODO Auto-generated method stub
					run();
				}

				@Override
				public void run() {
				}
			};
			actionBp.setToolTipText("breakpoint");
			actionBp.setEnabled(false);
			actionBp.setId("actionBp");
			actionBp.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("org.eclipse.debug.ui", "/icons/full/elcl16/skip_brkp.png"));
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
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void notify(Object info) {
	}

	@Override
	public void notify(String cmd, Object info) {
		if (cmd.equals(CmdStr.AddDM)) {
			DebugModel dm = (DebugModel) info;
			addHost(dm);
		} else if (cmd.equals(CmdStr.DestoryDM)) {
			DebugModel dm = (DebugModel) info;
			this.actionStepOver.setEnabled(false);
			this.actionTerminate.setEnabled(false);
			delHost(dm);
		}

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

	private void addHost(DebugModel dm) {
		HostNode hostNode = new HostNode(hostTree, SWT.NONE);
		hostNode.setDM(dm);
		hostNode.setText(dm.getName() + " " + dm.toString());
		hostTree.select(hostNode);

		this.actionStepOver.setEnabled(true);
		this.actionStepInto.setEnabled(true);
		this.actionTerminate.setEnabled(true);
		this.actionContinue.setEnabled(true);

		sendToCenter(CmdStr.SelectDM, dm);
	}

	private void delHost(DebugModel dm) {

		for (TreeItem item : hostTree.getItems()) {
			if (item.getText().equals(dm.getName() + " " + dm.toString())) {
				item.dispose();
				actionStepOver.setEnabled(false);
				actionTerminate.setEnabled(false);
				actionContinue.setEnabled(false);
			}
		}
		
		if(hostTree.getItems().length==0) {
			sendToCenter(CmdStr.DisSelectDM, null);
		}else {
			Node node = (Node) hostTree.getItems()[0];
			hostTree.select(node);
			sendToCenter(CmdStr.SelectDM, node.getDm());
		}

	}

	private void stepOver() {
		TreeItem ti = hostTree.getSelection()[0];
		if (ti instanceof Node) {
			String rec = ((Node) ti).getDm().stepOver();
		}
	}
	
	private void stepInto() {
		TreeItem ti = hostTree.getSelection()[0];
		if (ti instanceof Node) {
			String rec = ((Node) ti).getDm().stepInto();
		}
	}

	private void terminal() {
		TreeItem ti = hostTree.getSelection()[0];
		if (ti instanceof Node) {
			String rec = ((Node) ti).getDm().terminal();
		}
	}

	private void contin() {
		TreeItem ti = hostTree.getSelection()[0];
		if (ti instanceof Node) {
			String rec = ((Node) ti).getDm().contin();
		}
	}

	@Override
	public void dispose() {
		this.sendToCenter(CmdStr.RemoveListener, this);
		super.dispose();
	}

	@Override
	public Set<String> getCare() {
		// TODO Auto-generated method stub
		return this.cares;
	}

}
