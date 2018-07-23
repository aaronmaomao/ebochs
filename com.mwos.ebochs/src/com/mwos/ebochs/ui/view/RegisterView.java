package com.mwos.ebochs.ui.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.jface.action.Action;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.layout.FillLayout;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mwos.ebochs.core.model.IInfoListener;

import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;

public class RegisterView extends ViewPart implements IInfoListener{

	public static final String ID = "com.mwos.ebochs.ui.view.RegisterView"; //$NON-NLS-1$
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Table table;
	private Action actionRefresh;

	public RegisterView() {
		this.center.addListener(this);
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginWidth = 0;
		gl_container.marginHeight = 0;
		container.setLayout(gl_container);
		{
			Section regSec1 = formToolkit.createSection(container, Section.TWISTIE | Section.TITLE_BAR);
			regSec1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			formToolkit.paintBordersFor(regSec1);
			regSec1.setText("通用寄存器");
			Composite cs = formToolkit.createComposite(regSec1);
			regSec1.setClient(cs);
			cs.setLayout(new GridLayout(1, false));
			{
				
				TableViewer tableViewer = new TableViewer(cs, SWT.BORDER | SWT.FULL_SELECTION);
				table = tableViewer.getTable();
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				table.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
				
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				tableViewerColumn.setEditingSupport(new EditingSupport(tableViewer) {
					protected boolean canEdit(Object element) {
						// TODO Auto-generated method stub
						return false;
					}
					protected CellEditor getCellEditor(Object element) {
						// TODO Auto-generated method stub
						return null;
					}
					protected Object getValue(Object element) {
						// TODO Auto-generated method stub
						return null;
					}
					protected void setValue(Object element, Object value) {
						// TODO Auto-generated method stub
					}
				});
				TableColumn tcReg1 = tableViewerColumn.getColumn();
				tcReg1.setWidth(60);
				tcReg1.setText("寄存器");
				
				TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
				
				TableColumn tcValue1 = tableViewerColumn_1.getColumn();
				tcValue1.setWidth(100);
				tcValue1.setText("值");
				
				TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn tcReg2 = tableViewerColumn_2.getColumn();
				tcReg2.setWidth(60);
				tcReg2.setText("寄存器");
				
				TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn tcValue2 = tableViewerColumn_3.getColumn();
				tcValue2.setWidth(100);
				tcValue2.setText("值");
				
				TableItem tiEax = new TableItem(table, SWT.NONE);
				tiEax.setText(new String[] {"eax", "0x00000000", "ebp", "0x00000000"});
				
				TableItem tiEcx = new TableItem(table, 0);
				tiEcx.setText(new String[] {"ecx", "0x00000000", "esp", "0x00000000"});
				
				TableItem tiEdx = new TableItem(table, 0);
				tiEdx.setText(new String[] {"edx", "0x00000000", "esi", "0x00000000"});
				
				TableItem tiEbx = new TableItem(table, 0);
				tiEbx.setText(new String[] {"ebx", "0x00000000", "edi", "0x00000000"});
				
				TableItem tableItem_4 = new TableItem(table, 0);
				tableItem_4.setText(new String[] {});
				
				TableItem tiCs = new TableItem(table, 0);
				tiCs.setText(new String[] {"cs", "0x0000", "eip", "0x00000000"});
				
				TableItem tiSs = new TableItem(table, 0);
				tiSs.setText(new String[] {"ss", "0x0000", "esp", "0x00000000"});
				
				TableItem tiDs = new TableItem(table, 0);
				tiDs.setText(new String[] {"ds", "0x0000", "es", "0x0000"});
				
				TableItem tiFs = new TableItem(table, 0);
				tiFs.setText(new String[] {"fs", "0x0000", "gs", "0x0000"});
			}
			
			Composite composite = new Composite(regSec1, SWT.NONE);
			composite.setSize(10, 10);
			regSec1.setTextClient(composite);
			composite.setLayout(new FillLayout(SWT.HORIZONTAL));
			
			Label lblNewLabel_1 = new Label(composite, SWT.HORIZONTAL | SWT.CENTER);
			lblNewLabel_1.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
				}
			});
			lblNewLabel_1.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/icons/add.png"));
			
		}
		{
			Section regSec2 = formToolkit.createSection(container, Section.TWISTIE | Section.TITLE_BAR);
			regSec2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			formToolkit.paintBordersFor(regSec2);
			regSec2.setText("控制寄存器");
		}

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
			actionRefresh = new Action("refresh") {
			};
			actionRefresh.setImageDescriptor(ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/icons/arrow_refresh_small.png"));
		}
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		toolbarManager.add(actionRefresh);
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
		menuManager.add(actionRefresh);
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void notify(Object info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notify(String cmd, Object info) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose() {
		this.center.removeListener(this);
		super.dispose();
	}
}
