package com.mwos.ebochs.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import com.mwos.ebochs.core.CmdHandler;
import com.mwos.ebochs.core.CmdableUI;
import com.mwos.ebochs.core.Command;

public class RegisterVPart extends ViewPart implements CmdableUI {
	public static final String ID = "com.mwos.ebochs.view.registerVPart"; //$NON-NLS-1$
	private Text text_18;
	private Text text_19;
	private Text text_20;
	private Text text_21;
	private Text text_22;
	private Text text_23;
	private Text text_24;
	private Text text_25;
	private Text text_26;
	private Text text_27;
	private Table table_2;
	private Table table_3;
	private Text text_28;
	private Text text_29;
	private Text text_30;
	private Text text_31;
	private Text text_32;
	private Text text_33;
	private Text text_34;
	private Text text_35;

	private CmdHandler handler;
	
	public RegisterVPart() {
		handler = CmdHandler.getHandler();
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinWidth(370);
		
		ExpandBar expandBar = new ExpandBar(scrolledComposite, SWT.V_SCROLL);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setExpanded(true);
		xpndtmNewExpanditem.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/panda.png"));
		xpndtmNewExpanditem.setText("\u901A\u7528\u5BC4\u5B58\u5668");
		
		Composite composite_1 = new Composite(expandBar, SWT.BORDER);
		xpndtmNewExpanditem.setControl(composite_1);
		composite_1.setLayout(null);
		
		Label label_2 = new Label(composite_1, SWT.RIGHT);
		label_2.setText("EAX");
		label_2.setBounds(10, 13, 30, 15);
		
		text_18 = new Text(composite_1, SWT.BORDER);
		text_18.setBounds(50, 10, 100, 23);
		
		Label lblEbx = new Label(composite_1, SWT.RIGHT);
		lblEbx.setText("EBX");
		lblEbx.setBounds(10, 42, 30, 15);
		
		text_19 = new Text(composite_1, SWT.BORDER);
		text_19.setBounds(50, 39, 100, 23);
		
		text_20 = new Text(composite_1, SWT.BORDER);
		text_20.setBounds(50, 68, 100, 23);
		
		text_21 = new Text(composite_1, SWT.BORDER);
		text_21.setBounds(50, 97, 100, 23);
		
		Label lblEcx = new Label(composite_1, SWT.RIGHT);
		lblEcx.setText("ECX");
		lblEcx.setBounds(10, 71, 30, 15);
		
		Label lblEdx = new Label(composite_1, SWT.RIGHT);
		lblEdx.setText("EDX");
		lblEdx.setBounds(10, 100, 30, 15);
		
		Label lblEflags = new Label(composite_1, SWT.RIGHT);
		lblEflags.setText("EFLAGS");
		lblEflags.setBounds(10, 129, 43, 15);
		
		text_22 = new Text(composite_1, SWT.BORDER);
		text_22.setBounds(60, 126, 90, 23);
		
		Label lblEbp = new Label(composite_1, SWT.RIGHT);
		lblEbp.setText("EBP");
		lblEbp.setBounds(195, 13, 30, 15);
		
		text_23 = new Text(composite_1, SWT.BORDER);
		text_23.setBounds(233, 10, 100, 23);
		
		Label lblEsp = new Label(composite_1, SWT.RIGHT);
		lblEsp.setText("ESP");
		lblEsp.setBounds(195, 42, 30, 15);
		
		Label lblEdi = new Label(composite_1, SWT.RIGHT);
		lblEdi.setText("EDI");
		lblEdi.setBounds(195, 71, 30, 15);
		
		text_24 = new Text(composite_1, SWT.BORDER);
		text_24.setBounds(233, 39, 100, 23);
		
		text_25 = new Text(composite_1, SWT.BORDER);
		text_25.setBounds(233, 68, 100, 23);
		
		text_26 = new Text(composite_1, SWT.BORDER);
		text_26.setBounds(233, 97, 100, 23);
		
		text_27 = new Text(composite_1, SWT.BORDER);
		text_27.setBounds(233, 126, 100, 23);
		
		Label lblEsi = new Label(composite_1, SWT.RIGHT);
		lblEsi.setText("ESI");
		lblEsi.setBounds(195, 100, 30, 15);
		
		Label lblEip = new Label(composite_1, SWT.RIGHT);
		lblEip.setText("EIP");
		lblEip.setBounds(195, 129, 30, 15);
		
		table_2 = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_2.setLinesVisible(true);
		table_2.setHeaderVisible(true);
		table_2.setBounds(10, 155, 325, 45);
		
		TableColumn tableColumn = new TableColumn(table_2, SWT.NONE);
		tableColumn.setWidth(40);
		tableColumn.setText("--");
		
		TableColumn tableColumn_1 = new TableColumn(table_2, SWT.NONE);
		tableColumn_1.setWidth(40);
		tableColumn_1.setText("--");
		
		TableColumn tableColumn_2 = new TableColumn(table_2, SWT.NONE);
		tableColumn_2.setWidth(40);
		tableColumn_2.setText("AC");
		
		TableColumn tableColumn_3 = new TableColumn(table_2, SWT.NONE);
		tableColumn_3.setWidth(40);
		tableColumn_3.setText("VM");
		
		TableColumn tableColumn_4 = new TableColumn(table_2, SWT.NONE);
		tableColumn_4.setWidth(40);
		tableColumn_4.setText("RF");
		
		TableColumn tableColumn_5 = new TableColumn(table_2, SWT.NONE);
		tableColumn_5.setWidth(40);
		tableColumn_5.setText("NT");
		
		TableColumn tableColumn_6 = new TableColumn(table_2, SWT.NONE);
		tableColumn_6.setWidth(40);
		tableColumn_6.setText("IOPL");
		
		TableColumn tableColumn_7 = new TableColumn(table_2, SWT.NONE);
		tableColumn_7.setWidth(40);
		tableColumn_7.setText("OF");
		
		table_3 = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_3.setLinesVisible(true);
		table_3.setHeaderVisible(true);
		table_3.setBounds(10, 199, 325, 45);
		
		TableColumn tableColumn_8 = new TableColumn(table_3, SWT.NONE);
		tableColumn_8.setWidth(40);
		tableColumn_8.setText("DF");
		
		TableColumn tableColumn_9 = new TableColumn(table_3, SWT.NONE);
		tableColumn_9.setWidth(40);
		tableColumn_9.setText("IF");
		
		TableColumn tableColumn_10 = new TableColumn(table_3, SWT.NONE);
		tableColumn_10.setWidth(40);
		tableColumn_10.setText("TF");
		
		TableColumn tableColumn_11 = new TableColumn(table_3, SWT.NONE);
		tableColumn_11.setWidth(40);
		tableColumn_11.setText("SF");
		
		TableColumn tableColumn_12 = new TableColumn(table_3, SWT.NONE);
		tableColumn_12.setWidth(40);
		tableColumn_12.setText("ZF");
		
		TableColumn tableColumn_13 = new TableColumn(table_3, SWT.NONE);
		tableColumn_13.setWidth(40);
		tableColumn_13.setText("AF");
		
		TableColumn tableColumn_14 = new TableColumn(table_3, SWT.NONE);
		tableColumn_14.setWidth(40);
		tableColumn_14.setText("PF");
		
		TableColumn tableColumn_15 = new TableColumn(table_3, SWT.NONE);
		tableColumn_15.setWidth(40);
		tableColumn_15.setText("CF");
		xpndtmNewExpanditem.setHeight(265);
		
		ExpandItem xpndtmNewExpanditem_1 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_1.setExpanded(true);
		xpndtmNewExpanditem_1.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/register1.png"));
		xpndtmNewExpanditem_1.setText("\u6BB5\u5BC4\u5B58\u5668");
		
		Composite composite_2 = new Composite(expandBar, SWT.BORDER);
		xpndtmNewExpanditem_1.setControl(composite_2);
		xpndtmNewExpanditem_1.setHeight(130);
		composite_2.setLayout(null);
		
		Label lblCs = new Label(composite_2, SWT.RIGHT);
		lblCs.setText("CS");
		lblCs.setBounds(10, 13, 30, 15);
		
		text_28 = new Text(composite_2, SWT.BORDER);
		text_28.setBounds(50, 10, 100, 23);
		
		Label lblSs = new Label(composite_2, SWT.RIGHT);
		lblSs.setText("SS");
		lblSs.setBounds(10, 42, 30, 15);
		
		text_29 = new Text(composite_2, SWT.BORDER);
		text_29.setBounds(50, 39, 100, 23);
		
		Label lblDs = new Label(composite_2, SWT.RIGHT);
		lblDs.setText("DS");
		lblDs.setBounds(10, 71, 30, 15);
		
		text_30 = new Text(composite_2, SWT.BORDER);
		text_30.setBounds(50, 68, 100, 23);
		
		Label lblEs = new Label(composite_2, SWT.RIGHT);
		lblEs.setText("ES");
		lblEs.setBounds(10, 100, 30, 15);
		
		text_31 = new Text(composite_2, SWT.BORDER);
		text_31.setBounds(50, 97, 100, 23);
		
		text_32 = new Text(composite_2, SWT.BORDER);
		text_32.setBounds(233, 10, 100, 23);
		
		text_33 = new Text(composite_2, SWT.BORDER);
		text_33.setBounds(233, 97, 100, 23);
		
		text_34 = new Text(composite_2, SWT.BORDER);
		text_34.setBounds(233, 39, 100, 23);
		
		text_35 = new Text(composite_2, SWT.BORDER);
		text_35.setBounds(233, 68, 100, 23);
		
		Label lblEip_1 = new Label(composite_2, SWT.RIGHT);
		lblEip_1.setText("EIP");
		lblEip_1.setBounds(193, 13, 30, 15);
		
		Label lblEsp_1 = new Label(composite_2, SWT.RIGHT);
		lblEsp_1.setText("ESP");
		lblEsp_1.setBounds(193, 42, 30, 15);
		
		Label lblFs = new Label(composite_2, SWT.RIGHT);
		lblFs.setText("FS");
		lblFs.setBounds(193, 71, 30, 15);
		
		Label lblGs = new Label(composite_2, SWT.RIGHT);
		lblGs.setText("GS");
		lblGs.setBounds(193, 100, 30, 15);
		
		Label lblNewLabel = new Label(composite_2, SWT.NONE);
		lblNewLabel.setBounds(167, 13, 30, 17);
		lblNewLabel.setText("-----");
		
		Label label_1 = new Label(composite_2, SWT.NONE);
		label_1.setText("-----");
		label_1.setBounds(167, 42, 30, 17);
		
		ExpandItem xpndtmNewExpanditem_2 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_2.setExpanded(true);
		xpndtmNewExpanditem_2.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/register1.png"));
		xpndtmNewExpanditem_2.setText("\u63A7\u5236\u5BC4\u5B58\u5668");
		
		Composite composite_3 = new Composite(expandBar, SWT.BORDER);
		xpndtmNewExpanditem_2.setControl(composite_3);
		composite_3.setLayout(null);
		xpndtmNewExpanditem_2.setHeight(200);
		scrolledComposite.setContent(expandBar);

		createActions();
		initializeToolBar();
		initializeMenu();
	}
	
	@Override
	public void dispose() {
		handler.dispose(this);
		super.dispose();
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
	public void ackData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyUI(Command cmd, Object notifyData) {
		// TODO Auto-generated method stub
		
	}
}
