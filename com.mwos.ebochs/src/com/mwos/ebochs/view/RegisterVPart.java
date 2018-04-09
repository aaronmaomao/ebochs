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

public class RegisterVPart extends ViewPart {
	public static final String ID = "com.mwos.ebochs.view.registerVPart"; //$NON-NLS-1$
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Text text_9;
	private Table table;
	private Table table_1;
	private Text text_10;
	private Text text_11;
	private Text text_12;
	private Text text_13;
	private Text text_14;
	private Text text_15;
	private Text text_16;
	private Text text_17;

	public RegisterVPart() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new GridLayout(1, false));
		{
			Group group = new Group(composite, SWT.NONE);
			group.setText("\u901A\u7528\u5BC4\u5B58\u5668");
			GridData gd_group = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_group.minimumWidth = 350;
			gd_group.heightHint = 255;
			group.setLayoutData(gd_group);
			{
				Label lblNewLabel = new Label(group, SWT.NONE);
				lblNewLabel.setBounds(10, 22, 30, 15);
				lblNewLabel.setText("EAX");
			}
			
			text = new Text(group, SWT.BORDER);
			text.setBounds(45, 20, 100, 23);
			
			Label lblEbp = new Label(group, SWT.NONE);
			lblEbp.setText("EBP");
			lblEbp.setBounds(200, 22, 30, 15);
			
			text_1 = new Text(group, SWT.BORDER);
			text_1.setBounds(235, 20, 100, 23);
			
			Label lblEbx = new Label(group, SWT.NONE);
			lblEbx.setText("EBX");
			lblEbx.setBounds(10, 51, 30, 15);
			
			text_2 = new Text(group, SWT.BORDER);
			text_2.setBounds(45, 49, 100, 23);
			
			Label lblEcx_1 = new Label(group, SWT.NONE);
			lblEcx_1.setText("ECX");
			lblEcx_1.setBounds(10, 80, 30, 15);
			
			text_3 = new Text(group, SWT.BORDER);
			text_3.setBounds(45, 78, 100, 23);
			
			Label lblEcx = new Label(group, SWT.NONE);
			lblEcx.setText("EDX");
			lblEcx.setBounds(10, 109, 30, 15);
			
			text_4 = new Text(group, SWT.BORDER);
			text_4.setBounds(45, 107, 100, 23);
			
			Label lblEsp = new Label(group, SWT.NONE);
			lblEsp.setText("ESP");
			lblEsp.setBounds(200, 51, 30, 15);
			
			text_5 = new Text(group, SWT.BORDER);
			text_5.setBounds(235, 49, 100, 23);
			
			Label lblEdi = new Label(group, SWT.NONE);
			lblEdi.setText("EDI");
			lblEdi.setBounds(200, 80, 30, 15);
			
			text_6 = new Text(group, SWT.BORDER);
			text_6.setBounds(235, 78, 100, 23);
			
			Label lblEsi = new Label(group, SWT.NONE);
			lblEsi.setText("ESI");
			lblEsi.setBounds(200, 109, 30, 15);
			
			text_7 = new Text(group, SWT.BORDER);
			text_7.setBounds(235, 107, 100, 23);
			
			Label lblEflag = new Label(group, SWT.NONE);
			lblEflag.setText("EFLAGS");
			lblEflag.setBounds(10, 139, 50, 15);
			
			text_8 = new Text(group, SWT.BORDER);
			text_8.setBounds(62, 136, 83, 23);
			
			Label lblEip = new Label(group, SWT.NONE);
			lblEip.setText("EIP");
			lblEip.setBounds(200, 138, 30, 15);
			
			text_9 = new Text(group, SWT.BORDER);
			text_9.setBounds(235, 136, 100, 23);
			
			table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			table.setBounds(10, 166, 325, 45);
			
			TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
			tblclmnNewColumn.setWidth(40);
			tblclmnNewColumn.setText("--");
			
			TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
			tableColumn_1.setWidth(40);
			tableColumn_1.setText("--");
			
			TableColumn tblclmnAc = new TableColumn(table, SWT.NONE);
			tblclmnAc.setWidth(40);
			tblclmnAc.setText("AC");
			
			TableColumn tblclmnNm = new TableColumn(table, SWT.NONE);
			tblclmnNm.setWidth(40);
			tblclmnNm.setText("VM");
			
			TableColumn tblclmnIopl = new TableColumn(table, SWT.NONE);
			tblclmnIopl.setWidth(40);
			tblclmnIopl.setText("RF");
			
			TableColumn tblclmnNt = new TableColumn(table, SWT.NONE);
			tblclmnNt.setWidth(40);
			tblclmnNt.setText("NT");
			
			TableColumn tblclmnIopl_1 = new TableColumn(table, SWT.NONE);
			tblclmnIopl_1.setWidth(40);
			tblclmnIopl_1.setText("IOPL");
			
			TableColumn tblclmnOf = new TableColumn(table, SWT.NONE);
			tblclmnOf.setWidth(40);
			tblclmnOf.setText("OF");
			
			table_1 = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
			table_1.setLinesVisible(true);
			table_1.setHeaderVisible(true);
			table_1.setBounds(10, 210, 325, 45);
			
			TableColumn tblclmnDf = new TableColumn(table_1, SWT.NONE);
			tblclmnDf.setWidth(40);
			tblclmnDf.setText("DF");
			
			TableColumn tblclmnIf = new TableColumn(table_1, SWT.NONE);
			tblclmnIf.setWidth(40);
			tblclmnIf.setText("IF");
			
			TableColumn tblclmnTf = new TableColumn(table_1, SWT.NONE);
			tblclmnTf.setWidth(40);
			tblclmnTf.setText("TF");
			
			TableColumn tblclmnSf = new TableColumn(table_1, SWT.NONE);
			tblclmnSf.setWidth(40);
			tblclmnSf.setText("SF");
			
			TableColumn tblclmnZf = new TableColumn(table_1, SWT.NONE);
			tblclmnZf.setWidth(40);
			tblclmnZf.setText("ZF");
			
			TableColumn tblclmnAf = new TableColumn(table_1, SWT.NONE);
			tblclmnAf.setWidth(40);
			tblclmnAf.setText("AF");
			
			TableColumn tblclmnPf = new TableColumn(table_1, SWT.NONE);
			tblclmnPf.setWidth(40);
			tblclmnPf.setText("PF");
			
			TableColumn tableColumn_12 = new TableColumn(table_1, SWT.NONE);
			tableColumn_12.setWidth(40);
			tableColumn_12.setText("CF");
		}
		
		Group group = new Group(composite, SWT.NONE);
		GridData gd_group = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_group.minimumWidth = 350;
		gd_group.heightHint = 130;
		group.setLayoutData(gd_group);
		group.setText("\u6BB5\u5BC4\u5B58\u5668");
		
		Label lblCs = new Label(group, SWT.NONE);
		lblCs.setText("CS");
		lblCs.setBounds(10, 22, 30, 15);
		
		text_10 = new Text(group, SWT.BORDER);
		text_10.setBounds(45, 20, 100, 23);
		
		Label lblFs = new Label(group, SWT.NONE);
		lblFs.setText("EIP");
		lblFs.setBounds(200, 22, 30, 15);
		
		text_11 = new Text(group, SWT.BORDER);
		text_11.setBounds(235, 20, 100, 23);
		
		Label lblDs = new Label(group, SWT.NONE);
		lblDs.setText("SS");
		lblDs.setBounds(10, 51, 30, 15);
		
		text_12 = new Text(group, SWT.BORDER);
		text_12.setBounds(45, 49, 100, 23);
		
		Label lblSs = new Label(group, SWT.NONE);
		lblSs.setText("DS");
		lblSs.setBounds(10, 80, 30, 15);
		
		text_13 = new Text(group, SWT.BORDER);
		text_13.setBounds(45, 78, 100, 23);
		
		Label lblEs = new Label(group, SWT.NONE);
		lblEs.setText("ES");
		lblEs.setBounds(10, 109, 30, 15);
		
		text_14 = new Text(group, SWT.BORDER);
		text_14.setBounds(45, 107, 100, 23);
		
		Label lblGs = new Label(group, SWT.NONE);
		lblGs.setText("ESP");
		lblGs.setBounds(200, 51, 30, 15);
		
		text_15 = new Text(group, SWT.BORDER);
		text_15.setBounds(235, 49, 100, 23);
		
		Label lblFs_1 = new Label(group, SWT.NONE);
		lblFs_1.setText("FS");
		lblFs_1.setBounds(200, 80, 30, 15);
		
		text_16 = new Text(group, SWT.BORDER);
		text_16.setBounds(235, 78, 100, 23);
		
		Label lblGs_1 = new Label(group, SWT.NONE);
		lblGs_1.setText("GS");
		lblGs_1.setBounds(200, 109, 30, 15);
		
		text_17 = new Text(group, SWT.BORDER);
		text_17.setBounds(235, 107, 100, 23);
		
		Label label = new Label(group, SWT.NONE);
		label.setText("-----");
		label.setBounds(160, 22, 30, 15);
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setText("-----");
		label_1.setBounds(160, 51, 30, 15);

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
