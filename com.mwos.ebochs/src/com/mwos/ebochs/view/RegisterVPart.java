package com.mwos.ebochs.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.custom.StyledText;

public class RegisterVPart extends ViewPart {
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
	private Text text;

	public RegisterVPart() {
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
		
		Label label_3 = new Label(composite_1, SWT.RIGHT);
		label_3.setText("EAX");
		label_3.setBounds(10, 42, 30, 15);
		
		text_19 = new Text(composite_1, SWT.BORDER);
		text_19.setBounds(50, 39, 100, 23);
		
		text_20 = new Text(composite_1, SWT.BORDER);
		text_20.setBounds(50, 68, 100, 23);
		
		text_21 = new Text(composite_1, SWT.BORDER);
		text_21.setBounds(50, 97, 100, 23);
		
		Label label_4 = new Label(composite_1, SWT.RIGHT);
		label_4.setText("EAX");
		label_4.setBounds(10, 71, 30, 15);
		
		Label label_5 = new Label(composite_1, SWT.RIGHT);
		label_5.setText("EAX");
		label_5.setBounds(10, 100, 30, 15);
		
		Label label_6 = new Label(composite_1, SWT.RIGHT);
		label_6.setText("EAX");
		label_6.setBounds(10, 129, 30, 15);
		
		text_22 = new Text(composite_1, SWT.BORDER);
		text_22.setBounds(50, 126, 100, 23);
		
		Label label_7 = new Label(composite_1, SWT.RIGHT);
		label_7.setText("EAX");
		label_7.setBounds(195, 13, 30, 15);
		
		text_23 = new Text(composite_1, SWT.BORDER);
		text_23.setBounds(233, 10, 100, 23);
		
		Label label_8 = new Label(composite_1, SWT.RIGHT);
		label_8.setText("EAX");
		label_8.setBounds(195, 42, 30, 15);
		
		Label label_9 = new Label(composite_1, SWT.RIGHT);
		label_9.setText("EAX");
		label_9.setBounds(195, 71, 30, 15);
		
		text_24 = new Text(composite_1, SWT.BORDER);
		text_24.setBounds(233, 39, 100, 23);
		
		text_25 = new Text(composite_1, SWT.BORDER);
		text_25.setBounds(233, 68, 100, 23);
		
		text_26 = new Text(composite_1, SWT.BORDER);
		text_26.setBounds(233, 97, 100, 23);
		
		text_27 = new Text(composite_1, SWT.BORDER);
		text_27.setBounds(233, 126, 100, 23);
		
		Label label_10 = new Label(composite_1, SWT.RIGHT);
		label_10.setText("EAX");
		label_10.setBounds(195, 100, 30, 15);
		
		Label label_11 = new Label(composite_1, SWT.RIGHT);
		label_11.setText("EAX");
		label_11.setBounds(195, 129, 30, 15);
		
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
		xpndtmNewExpanditem_1.setHeight(150);
		composite_2.setLayout(null);
		
		Label label_12 = new Label(composite_2, SWT.RIGHT);
		label_12.setText("EAX");
		label_12.setBounds(10, 13, 30, 15);
		
		text_28 = new Text(composite_2, SWT.BORDER);
		text_28.setBounds(50, 10, 100, 23);
		
		Label label_13 = new Label(composite_2, SWT.RIGHT);
		label_13.setText("EAX");
		label_13.setBounds(10, 42, 30, 15);
		
		text_29 = new Text(composite_2, SWT.BORDER);
		text_29.setBounds(50, 39, 100, 23);
		
		Label label_14 = new Label(composite_2, SWT.RIGHT);
		label_14.setText("EAX");
		label_14.setBounds(10, 71, 30, 15);
		
		text_30 = new Text(composite_2, SWT.BORDER);
		text_30.setBounds(50, 68, 100, 23);
		
		Label label_15 = new Label(composite_2, SWT.RIGHT);
		label_15.setText("EAX");
		label_15.setBounds(10, 100, 30, 15);
		
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
		
		Label label_16 = new Label(composite_2, SWT.RIGHT);
		label_16.setText("EAX");
		label_16.setBounds(193, 13, 30, 15);
		
		Label label_17 = new Label(composite_2, SWT.RIGHT);
		label_17.setText("EAX");
		label_17.setBounds(193, 42, 30, 15);
		
		Label label_18 = new Label(composite_2, SWT.RIGHT);
		label_18.setText("EAX");
		label_18.setBounds(193, 71, 30, 15);
		
		Label label_19 = new Label(composite_2, SWT.RIGHT);
		label_19.setText("EAX");
		label_19.setBounds(193, 100, 30, 15);
		
		ExpandItem xpndtmNewExpanditem_2 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_2.setExpanded(true);
		xpndtmNewExpanditem_2.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/register1.png"));
		xpndtmNewExpanditem_2.setText("\u63A7\u5236\u5BC4\u5B58\u5668");
		
		Composite composite_3 = new Composite(expandBar, SWT.BORDER);
		xpndtmNewExpanditem_2.setControl(composite_3);
		composite_3.setLayout(null);
		
		Label label = new Label(composite_3, SWT.RIGHT);
		label.setText("EAX");
		label.setBounds(10, 13, 30, 15);
		
		text = new Text(composite_3, SWT.BORDER);
		text.setBounds(50, 10, 100, 23);
		xpndtmNewExpanditem_2.setHeight(150);
		scrolledComposite.setContent(expandBar);

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
}
