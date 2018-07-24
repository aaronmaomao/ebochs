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
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Text text_9;
	private Text text_10;
	private Text text_11;
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
		
		Composite composite = new Composite(shell, SWT.NONE);
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.verticalSpacing = 0;
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		gl_composite.horizontalSpacing = 0;
		composite.setLayout(gl_composite);
		
		Label label = new Label(composite, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.widthHint = 50;
		label.setLayoutData(gd_label);
		label.setText("寄存器");
		
		
		Label label_1 = new Label(composite, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_1.widthHint = 120;
		label_1.setLayoutData(gd_label_1);
		label_1.setText("值");
		
		Label label_2 = new Label(composite, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.widthHint = 50;
		label_2.setLayoutData(gd_label_2);
		label_2.setText("寄存器");
		
		Label label_3 = new Label(composite, SWT.NONE);
		GridData gd_label_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_3.widthHint = 120;
		label_3.setLayoutData(gd_label_3);
		label_3.setText("值");
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("EAX");
		
		txtx = new Text(composite, SWT.BORDER);
		GridData gd_txtx = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx.widthHint = 120;
		txtx.setLayoutData(gd_txtx);
		txtx.setText("0x00000000");
		txtx.setEditable(false);
		
		
		Label lblEbp = new Label(composite, SWT.NONE);
		GridData gd_lblEbp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblEbp.widthHint = 50;
		lblEbp.setLayoutData(gd_lblEbp);
		lblEbp.setText("EBP");
		
		txtx_2 = new Text(composite, SWT.BORDER);
		txtx_2.setText("0x00000000");
		GridData gd_txtx_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_2.widthHint = 120;
		txtx_2.setLayoutData(gd_txtx_2);
		txtx_2.setEditable(false);
		
		Label lblEcx = new Label(composite, SWT.NONE);
		lblEcx.setText("ECX");
		
		txtx_1 = new Text(composite, SWT.BORDER);
		txtx_1.setText("0x00000000");
		GridData gd_txtx_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_1.widthHint = 120;
		txtx_1.setLayoutData(gd_txtx_1);
		txtx_1.setEditable(false);
		
		Label lblEsp = new Label(composite, SWT.NONE);
		GridData gd_lblEsp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblEsp.widthHint = 50;
		lblEsp.setLayoutData(gd_lblEsp);
		lblEsp.setText("ESP");
		
		txtx_3 = new Text(composite, SWT.BORDER);
		txtx_3.setText("0x00000000");
		GridData gd_txtx_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtx_3.widthHint = 120;
		txtx_3.setLayoutData(gd_txtx_3);
		txtx_3.setEditable(false);
		
		Label label_4 = new Label(composite, SWT.NONE);
		label_4.setText("ECX");
		
		text = new Text(composite, SWT.BORDER);
		text.setText("0x00000000");
		text.setEditable(false);
		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text.widthHint = 120;
		text.setLayoutData(gd_text);
		
		Label label_6 = new Label(composite, SWT.NONE);
		label_6.setText("ESP");
		
		text_1 = new Text(composite, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_1.widthHint = 120;
		text_1.setLayoutData(gd_text_1);
		text_1.setText("0x00000000");
		text_1.setEditable(false);
		
		Label label_5 = new Label(composite, SWT.NONE);
		label_5.setText("ECX");
		
		text_3 = new Text(composite, SWT.BORDER);
		text_3.setTouchEnabled(true);
		GridData gd_text_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_3.widthHint = 120;
		text_3.setLayoutData(gd_text_3);
		text_3.setText("0x00000000");
		text_3.setEditable(false);
		
		Label label_7 = new Label(composite, SWT.NONE);
		label_7.setText("ESP");
		
		text_2 = new Text(composite, SWT.BORDER);
		GridData gd_text_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_2.widthHint = 120;
		text_2.setLayoutData(gd_text_2);
		text_2.setText("0x00000000");
		text_2.setEditable(false);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label label_8 = new Label(composite, SWT.NONE);
		label_8.setText("EAX");
		
		text_4 = new Text(composite, SWT.BORDER);
		GridData gd_text_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_4.widthHint = 120;
		text_4.setLayoutData(gd_text_4);
		text_4.setText("0x00000000");
		text_4.setEditable(false);
		
		Label label_12 = new Label(composite, SWT.NONE);
		label_12.setText("ESP");
		
		text_8 = new Text(composite, SWT.BORDER);
		GridData gd_text_8 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_8.widthHint = 120;
		text_8.setLayoutData(gd_text_8);
		text_8.setText("0x00000000");
		text_8.setEditable(false);
		
		Label label_9 = new Label(composite, SWT.NONE);
		label_9.setText("EAX");
		
		text_5 = new Text(composite, SWT.BORDER);
		text_5.setText("0x00000000");
		text_5.setEditable(false);
		GridData gd_text_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_5.widthHint = 120;
		text_5.setLayoutData(gd_text_5);
		
		Label label_13 = new Label(composite, SWT.NONE);
		label_13.setText("ESP");
		
		text_9 = new Text(composite, SWT.BORDER);
		GridData gd_text_9 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_9.widthHint = 120;
		text_9.setLayoutData(gd_text_9);
		text_9.setText("0x00000000");
		text_9.setEditable(false);
		
		Label label_10 = new Label(composite, SWT.NONE);
		label_10.setText("EAX");
		
		text_6 = new Text(composite, SWT.BORDER);
		text_6.setText("0x00000000");
		text_6.setEditable(false);
		GridData gd_text_6 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_6.widthHint = 120;
		text_6.setLayoutData(gd_text_6);
		
		Label label_14 = new Label(composite, SWT.NONE);
		label_14.setText("ESP");
		
		text_10 = new Text(composite, SWT.BORDER);
		GridData gd_text_10 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_10.widthHint = 120;
		text_10.setLayoutData(gd_text_10);
		text_10.setText("0x00000000");
		text_10.setEditable(false);
		
		Label label_11 = new Label(composite, SWT.NONE);
		label_11.setText("EAX");
		
		text_7 = new Text(composite, SWT.BORDER);
		text_7.setText("0x00000000");
		text_7.setEditable(false);
		GridData gd_text_7 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_7.widthHint = 120;
		text_7.setLayoutData(gd_text_7);
		
		Label label_15 = new Label(composite, SWT.NONE);
		label_15.setText("ESP");
		
		text_11 = new Text(composite, SWT.BORDER);
		GridData gd_text_11 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_11.widthHint = 120;
		text_11.setLayoutData(gd_text_11);
		text_11.setText("0x00000000");
		text_11.setEditable(false);
		
		Label lblEflage = new Label(composite, SWT.NONE);
		lblEflage.setText("EFLAGE");
		
		text_12 = new Text(composite, SWT.BORDER);
		text_12.setText("0x00000000");
		text_12.setEditable(false);
		GridData gd_text_12 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_text_12.widthHint = 300;
		text_12.setLayoutData(gd_text_12);

	}
}
