package com.mwos.ebochs.ui.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Text;

public class A {

	protected Shell shell;
	private Text txtx_2;
	private Text txtx_3;
	private Text txtx;
	private Text txtx_1;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text txtx_7;
	private Text txtx_6;
	private Text txtx_5;
	private Text txtx_4;
	private Text text_8;
	private Text text_9;
	private Text txtx_8;
	private Text txtx_9;
	private Text text_12;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			A window = new A();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/icons/arrow_refresh_small.png"));
		shell.setSize(543, 561);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite cs = new Composite(shell, SWT.NONE);
		GridLayout gl_cs = new GridLayout(4, false);
		gl_cs.verticalSpacing = 0;
		gl_cs.marginWidth = 0;
		gl_cs.marginHeight = 0;
		gl_cs.horizontalSpacing = 0;
		cs.setLayout(gl_cs);
		
		Label label = new Label(cs, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.widthHint = 50;
		label.setLayoutData(gd_label);
		label.setText("寄存器");
		
		
		Label label_1 = new Label(cs, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_1.widthHint = 120;
		label_1.setLayoutData(gd_label_1);
		label_1.setText("值");
		
		Label label_2 = new Label(cs, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.widthHint = 50;
		label_2.setLayoutData(gd_label_2);
		label_2.setText("寄存器");
		
		Label label_3 = new Label(cs, SWT.NONE);
		GridData gd_label_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_3.widthHint = 120;
		label_3.setLayoutData(gd_label_3);
		label_3.setText("值");
		
		Label lblNewLabel = new Label(cs, SWT.NONE);
		lblNewLabel.setText("EAX");
		
		txtx = new Text(cs, SWT.BORDER);
		GridData gd_txtx = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx.widthHint = 120;
		txtx.setLayoutData(gd_txtx);
		txtx.setText("0x00000000");
		txtx.setEditable(false);
		
		
		Label lblEbp = new Label(cs, SWT.NONE);
		GridData gd_lblEbp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblEbp.widthHint = 50;
		lblEbp.setLayoutData(gd_lblEbp);
		lblEbp.setText("EBP");
		
		txtx_2 = new Text(cs, SWT.BORDER);
		txtx_2.setText("0x00000000");
		GridData gd_txtx_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_2.widthHint = 120;
		txtx_2.setLayoutData(gd_txtx_2);
		txtx_2.setEditable(false);
		
		Label lblEcx = new Label(cs, SWT.NONE);
		lblEcx.setText("ECX");
		
		txtx_1 = new Text(cs, SWT.BORDER);
		txtx_1.setText("0x00000000");
		GridData gd_txtx_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_1.widthHint = 120;
		txtx_1.setLayoutData(gd_txtx_1);
		txtx_1.setEditable(false);
		
		Label lblEsp = new Label(cs, SWT.NONE);
		GridData gd_lblEsp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblEsp.widthHint = 50;
		lblEsp.setLayoutData(gd_lblEsp);
		lblEsp.setText("ESP");
		
		txtx_3 = new Text(cs, SWT.BORDER);
		txtx_3.setText("0x00000000");
		GridData gd_txtx_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_3.widthHint = 120;
		txtx_3.setLayoutData(gd_txtx_3);
		txtx_3.setEditable(false);
		
		Label lblEdx = new Label(cs, SWT.NONE);
		lblEdx.setText("EDX");
		
		text = new Text(cs, SWT.BORDER);
		text.setText("0x00000000");
		text.setEditable(false);
		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text.widthHint = 120;
		text.setLayoutData(gd_text);
		
		Label lblEsi = new Label(cs, SWT.NONE);
		lblEsi.setText("ESI");
		
		text_1 = new Text(cs, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_1.widthHint = 120;
		text_1.setLayoutData(gd_text_1);
		text_1.setText("0x00000000");
		text_1.setEditable(false);
		
		Label lblEbx = new Label(cs, SWT.NONE);
		lblEbx.setText("EBX");
		
		text_3 = new Text(cs, SWT.BORDER);
		text_3.setTouchEnabled(true);
		GridData gd_text_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_3.widthHint = 120;
		text_3.setLayoutData(gd_text_3);
		text_3.setText("0x00000000");
		text_3.setEditable(false);
		
		Label lblEdi = new Label(cs, SWT.NONE);
		lblEdi.setText("EDI");
		
		text_2 = new Text(cs, SWT.BORDER);
		GridData gd_text_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_2.widthHint = 120;
		text_2.setLayoutData(gd_text_2);
		text_2.setText("0x00000000");
		text_2.setEditable(false);
		new Label(cs, SWT.NONE);
		new Label(cs, SWT.NONE);
		new Label(cs, SWT.NONE);
		new Label(cs, SWT.NONE);
		
		Label lblCs = new Label(cs, SWT.NONE);
		lblCs.setText("CS");
		
		txtx_7 = new Text(cs, SWT.BORDER);
		GridData gd_txtx_7 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_7.widthHint = 120;
		txtx_7.setLayoutData(gd_txtx_7);
		txtx_7.setText("0x0000");
		txtx_7.setEditable(false);
		
		Label lblEip = new Label(cs, SWT.NONE);
		lblEip.setText("EIP");
		
		text_8 = new Text(cs, SWT.BORDER);
		GridData gd_text_8 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_8.widthHint = 120;
		text_8.setLayoutData(gd_text_8);
		text_8.setText("0x00000000");
		text_8.setEditable(false);
		
		Label lblSs = new Label(cs, SWT.NONE);
		lblSs.setText("SS");
		
		txtx_6 = new Text(cs, SWT.BORDER);
		txtx_6.setText("0x0000");
		txtx_6.setEditable(false);
		GridData gd_txtx_6 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_6.widthHint = 120;
		txtx_6.setLayoutData(gd_txtx_6);
		
		Label label_13 = new Label(cs, SWT.NONE);
		label_13.setText("ESP");
		
		text_9 = new Text(cs, SWT.BORDER);
		GridData gd_text_9 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_9.widthHint = 120;
		text_9.setLayoutData(gd_text_9);
		text_9.setText("0x00000000");
		text_9.setEditable(false);
		
		Label lblDs = new Label(cs, SWT.NONE);
		lblDs.setText("DS");
		
		txtx_5 = new Text(cs, SWT.BORDER);
		txtx_5.setText("0x0000");
		txtx_5.setEditable(false);
		GridData gd_txtx_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_5.widthHint = 120;
		txtx_5.setLayoutData(gd_txtx_5);
		
		Label lblEs = new Label(cs, SWT.NONE);
		lblEs.setText("ES");
		
		txtx_8 = new Text(cs, SWT.BORDER);
		GridData gd_txtx_8 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_8.widthHint = 120;
		txtx_8.setLayoutData(gd_txtx_8);
		txtx_8.setText("0x0000");
		txtx_8.setEditable(false);
		
		Label lblFs = new Label(cs, SWT.NONE);
		lblFs.setText("FS");
		
		txtx_4 = new Text(cs, SWT.BORDER);
		txtx_4.setText("0x0000");
		txtx_4.setEditable(false);
		GridData gd_txtx_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_4.widthHint = 120;
		txtx_4.setLayoutData(gd_txtx_4);
		
		Label lblGs = new Label(cs, SWT.NONE);
		lblGs.setText("GS");
		
		txtx_9 = new Text(cs, SWT.BORDER);
		GridData gd_txtx_9 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_9.widthHint = 120;
		txtx_9.setLayoutData(gd_txtx_9);
		txtx_9.setText("0x0000");
		txtx_9.setEditable(false);
		
		Label lblEflage = new Label(cs, SWT.NONE);
		lblEflage.setText("EFLAGE");
		
		text_12 = new Text(cs, SWT.BORDER);
		text_12.setText("0x00000000");
		text_12.setEditable(false);
		GridData gd_text_12 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_text_12.widthHint = 300;
		text_12.setLayoutData(gd_text_12);

	}
}
