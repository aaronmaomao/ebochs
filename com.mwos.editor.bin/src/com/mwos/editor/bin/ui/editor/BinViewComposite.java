package com.mwos.editor.bin.ui.editor;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mwos.editor.bin.model.Byte16Item;
import com.mwos.editor.bin.service.BinService;
import com.mwos.editor.bin.service.Utils;

import swing2swt.layout.BorderLayout;

/**
 * 
 * @author maozhengjun
 * @time 2019年4月23日 下午7:17:42
 */
public class BinViewComposite extends Composite {
	private Table table;
	private Text startTxt;
	private Text endTxt;

	private BinService service;
	private TableViewer tableViewer;
	private Button nozeroBtn;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public BinViewComposite(byte[] data, int offset, Composite parent, int style) {
		this(new BinService(data, offset), parent, SWT.READ_ONLY);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public BinViewComposite(BinService service, Composite parent, int style) {
		super(parent, style);
		this.service = service;
		setLayout(new BorderLayout(0, 0));
		boolean readOnly = (style & SWT.READ_ONLY) != 0 ? true : false;

		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setTouchEnabled(true);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new GridLayout(6, false));

		startTxt = new Text(composite, SWT.BORDER | SWT.RIGHT);
		startTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String startText = startTxt.getText();
				String endText = endTxt.getText();
				if (startText.startsWith("0x")) {
					int start = Integer.parseUnsignedInt(startText.replaceAll("0x", ""), 16);
					int end = Integer.parseUnsignedInt(endText.replaceAll("0x", ""), 16);
					if (start < service.getOffset() || start > (service.getOffset() + service.getSize() - 1)) {
						startTxt.setText("0x" + Utils.getHexString(service.getOffset(), 8));
						return;
					} else if (start > end) {
						endTxt.setText("0x" + Utils.getHexString(service.getOffset() + service.getSize() - 1, 8));
						return;
					}
				} else {
					startTxt.setText("0x" + Utils.getHexString(service.getOffset(), 8));
				}

			}
		});
		startTxt.setText("0x" + Utils.getHexString(service.getOffset(), 8));
		startTxt.setMessage("<起始地址>");
		startTxt.setTextLimit(10);
		GridData gd_startTxt = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_startTxt.widthHint = 80;
		startTxt.setLayoutData(gd_startTxt);

		Label label = new Label(composite, SWT.HORIZONTAL);
		label.setText("-");
		GridData gd_label = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label.widthHint = 5;
		label.setLayoutData(gd_label);

		endTxt = new Text(composite, SWT.BORDER | SWT.RIGHT);
		endTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String endText = endTxt.getText();
				if (endText.startsWith("0x")) {
					int end = Integer.parseUnsignedInt(endText.replaceAll("0x", ""), 16);
					if (end > service.getOffset() + service.getSize() - 1 || end < service.getOffset()) {
						endTxt.setText("0x" + Utils.getHexString(service.getOffset() + service.getSize() - 1, 8));
						return;
					}
				} else {
					endTxt.setText("0x" + Utils.getHexString(service.getOffset() + service.getSize() - 1, 8));
				}
			}
		});
		endTxt.setText("0x" + Utils.getHexString(service.getOffset() + service.getSize() - 1, 8));
		endTxt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		endTxt.setMessage("<结束地址>");
		endTxt.setTextLimit(10);
		GridData gd_endTxt = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_endTxt.widthHint = 80;
		endTxt.setLayoutData(gd_endTxt);

		Button listBtn = new Button(composite, SWT.NONE);
		listBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int startOffset = Integer.parseUnsignedInt(startTxt.getText().replaceAll("0x", ""), 16);
				int endOffset = Integer.parseUnsignedInt(endTxt.getText().replaceAll("0x", ""), 16);
				tableViewer.setInput(service.getItems(startOffset, endOffset, nozeroBtn.getSelection()));
			}

		});
		listBtn.setText("列出");
		listBtn.setImage(null);
		new Label(composite, SWT.NONE);

		nozeroBtn = new Button(composite, SWT.CHECK);
		nozeroBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int startOffset = Integer.parseUnsignedInt(startTxt.getText().replaceAll("0x", ""), 16);
				int endOffset = Integer.parseUnsignedInt(endTxt.getText().replaceAll("0x", ""), 16);
				tableViewer.setInput(service.getItems(startOffset, endOffset, nozeroBtn.getSelection()));
			}
		});
		nozeroBtn.setSelection(true);
		nozeroBtn.setText("略零");

		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setFont(SWTResourceManager.getFont("Consolas", 10, SWT.NORMAL));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				// event.detail &= ~SWT.HOT;
				// if ((event.detail & SWT.SELECTED) == 0)
				// return;
				// int clientWidth = ((Composite) event.widget).getClientArea().width;
				// GC gc = event.gc;
				// Color oldForeground = gc.getForeground();
				// Color oldBackground = gc.getBackground();
				// gc.setBackground(event.display.getSystemColor(SWT.COLOR_YELLOW));
				// gc.setForeground(event.display.getSystemColor(SWT.COLOR_RED));
				// gc.fillRectangle(0, event.y, clientWidth, event.height);
				// gc.
				// gc.fillGradientRectangle(0, event.y, clientWidth, event.height, false);
				// gc.setBackground(oldBackground);
				// gc.setForeground(event.display.getSystemColor(SWT.COLOR_RED));
				// event.detail &= ~SWT.SELECTED;
			}
		});

		table.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(event.data instanceof Byte16Item) {
					Byte16Item item = (Byte16Item) event.data;
					tableViewer.refresh(item, true);
					notifyListeners(SWT.Modify, event);
				}
			}
		});

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn addrColumn = tableViewerColumn.getColumn();
		addrColumn.setWidth(72);
		addrColumn.setText("地址");

		TableViewerColumn tableViewerColumn_0 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset0Column = tableViewerColumn_0.getColumn();
		offset0Column.setAlignment(SWT.CENTER);
		offset0Column.setWidth(30);
		offset0Column.setText("0");
		tableViewerColumn_0.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_0));

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset1Column = tableViewerColumn_1.getColumn();
		offset1Column.setAlignment(SWT.CENTER);
		offset1Column.setWidth(30);
		offset1Column.setText("1");
		tableViewerColumn_1.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_1));

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset2Column = tableViewerColumn_2.getColumn();
		offset2Column.setAlignment(SWT.CENTER);
		offset2Column.setWidth(30);
		offset2Column.setText("2");
		tableViewerColumn_2.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_2));

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset3Column = tableViewerColumn_3.getColumn();
		offset3Column.setAlignment(SWT.CENTER);
		offset3Column.setWidth(30);
		offset3Column.setText("3");
		tableViewerColumn_3.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_3));

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset4Column = tableViewerColumn_4.getColumn();
		offset4Column.setAlignment(SWT.CENTER);
		offset4Column.setWidth(30);
		offset4Column.setText("4");
		tableViewerColumn_4.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_4));

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset5Column = tableViewerColumn_5.getColumn();
		offset5Column.setAlignment(SWT.CENTER);
		offset5Column.setWidth(30);
		offset5Column.setText("5");
		tableViewerColumn_5.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_5));

		TableViewerColumn tableViewerColumn_66 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset6Column = tableViewerColumn_66.getColumn();
		offset6Column.setAlignment(SWT.CENTER);
		offset6Column.setWidth(30);
		offset6Column.setText("6");
		tableViewerColumn_66.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_66));

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset7Column = tableViewerColumn_6.getColumn();
		offset7Column.setAlignment(SWT.CENTER);
		offset7Column.setWidth(30);
		offset7Column.setText("7");
		tableViewerColumn_6.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_6));

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset8Column = tableViewerColumn_7.getColumn();
		offset8Column.setAlignment(SWT.CENTER);
		offset8Column.setWidth(30);
		offset8Column.setText("8");
		tableViewerColumn_7.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_7));

		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offset9Column = tableViewerColumn_8.getColumn();
		offset9Column.setAlignment(SWT.CENTER);
		offset9Column.setWidth(30);
		offset9Column.setText("9");
		tableViewerColumn_8.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_8));

		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offsetAColumn = tableViewerColumn_9.getColumn();
		offsetAColumn.setAlignment(SWT.CENTER);
		offsetAColumn.setWidth(30);
		offsetAColumn.setText("A");
		tableViewerColumn_9.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_9));

		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offsetBColumn = tableViewerColumn_10.getColumn();
		offsetBColumn.setAlignment(SWT.CENTER);
		offsetBColumn.setWidth(30);
		offsetBColumn.setText("B");
		tableViewerColumn_10.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_10));

		TableViewerColumn tableViewerColumn_11 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offsetCColumn = tableViewerColumn_11.getColumn();
		offsetCColumn.setAlignment(SWT.CENTER);
		offsetCColumn.setWidth(30);
		offsetCColumn.setText("C");
		tableViewerColumn_11.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_11));

		TableViewerColumn tableViewerColumn_12 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offsetDColumn = tableViewerColumn_12.getColumn();
		offsetDColumn.setAlignment(SWT.CENTER);
		offsetDColumn.setWidth(30);
		offsetDColumn.setText("D");
		tableViewerColumn_12.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_12));

		TableViewerColumn tableViewerColumn_13 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offsetEColumn = tableViewerColumn_13.getColumn();
		offsetEColumn.setAlignment(SWT.CENTER);
		offsetEColumn.setWidth(30);
		offsetEColumn.setText("E");
		tableViewerColumn_13.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_13));

		TableViewerColumn tableViewerColumn_14 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn offsetFColumn = tableViewerColumn_14.getColumn();
		offsetFColumn.setAlignment(SWT.CENTER);
		offsetFColumn.setWidth(30);
		offsetFColumn.setText("F");
		tableViewerColumn_14.setEditingSupport(readOnly ? null : new ByteCellEditorSupport(tableViewerColumn_14));

		TableViewerColumn tableViewerColumn_15 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn asciiColumn = tableViewerColumn_15.getColumn();
		asciiColumn.setWidth(110);
		asciiColumn.setText("ASCII");

		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());

		int startOffset = Integer.parseUnsignedInt(startTxt.getText().replaceAll("0x", ""), 16);
		int endOffset = Integer.parseUnsignedInt(endTxt.getText().replaceAll("0x", ""), 16);
		tableViewer.setInput(service.getItems(startOffset, endOffset, nozeroBtn.getSelection()));
	}

	public BinService getService() {
		return this.service;
	}

	@Override
	protected void checkSubclass() {
	}
}
