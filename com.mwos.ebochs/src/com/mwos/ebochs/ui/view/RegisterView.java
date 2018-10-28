package com.mwos.ebochs.ui.view;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;

import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.cmd.CmdStr;
import com.mwos.ebochs.core.model.cmd.DCmd;
import com.mwos.ebochs.core.vm.bochs.DebugModel;
import com.mwos.ebochs.ui.view.model.register.RegisterParse;
import org.eclipse.wb.swt.SWTResourceManager;

public class RegisterView extends ViewPart implements IInfoListener {

	public static final String ID = "com.mwos.ebochs.ui.view.RegisterView"; //$NON-NLS-1$
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Action linkAction;

	private Text txtEBP;
	private Text txtESP;
	private Text txtEAX;
	private Text txtECX;
	private Text txtEDX;
	private Text txtESI;
	private Text txtEDI;
	private Text txtEBX;
	private Text txtCS;
	private Text txtSS;
	private Text txtDS;
	private Text txtFS;
	private Text txtEIP;
	private Text txtESP2;
	private Text txtES;
	private Text txtGS;
	private Text txtEFLAGS;
	private Action refAction;

	private DebugModel dm = null;

	private Set<String> cares = new HashSet<>();

	public RegisterView() {
		this.cares.add(CmdStr.SelectDM);
		this.cares.add(CmdStr.DisSelectDM);
		this.cares.add(CmdStr.s);
		this.cares.add(CmdStr.n);
		this.cares.add(CmdStr.c);
		IInfoListener.center.addListener(this);
	}

	/**
	 * Create contents of the view part.
	 * 
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
			regSec1.setText("ͨ�üĴ���");
			Composite cmpGReg = formToolkit.createComposite(regSec1);
			regSec1.setClient(cmpGReg);
			{

				GridLayout gl_cmpGReg = new GridLayout(4, false);
				gl_cmpGReg.verticalSpacing = 0;
				gl_cmpGReg.marginWidth = 0;
				gl_cmpGReg.marginHeight = 0;
				gl_cmpGReg.horizontalSpacing = 0;
				cmpGReg.setLayout(gl_cmpGReg);

				Label lblReg1 = new Label(cmpGReg, SWT.NONE);
				GridData gd_lblReg1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblReg1.widthHint = 50;
				lblReg1.setLayoutData(gd_lblReg1);
				lblReg1.setText("�Ĵ���");

				Label lblVal1 = new Label(cmpGReg, SWT.NONE);
				GridData gd_lblVal1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblVal1.widthHint = 120;
				lblVal1.setLayoutData(gd_lblVal1);
				lblVal1.setText("ֵ");

				Label lblReg2 = new Label(cmpGReg, SWT.NONE);
				GridData gd_lblReg2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblReg2.widthHint = 50;
				lblReg2.setLayoutData(gd_lblReg2);
				lblReg2.setText("�Ĵ���");

				Label lblVal2 = new Label(cmpGReg, SWT.NONE);
				GridData gd_lblVal2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblVal2.widthHint = 120;
				lblVal2.setLayoutData(gd_lblVal2);
				lblVal2.setText("ֵ");

				Label lblEAX = new Label(cmpGReg, SWT.NONE);
				lblEAX.setText("EAX");

				txtEAX = new Text(cmpGReg, SWT.NONE);
				txtEAX.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_txtEAX = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtEAX.widthHint = 120;
				txtEAX.setLayoutData(gd_txtEAX);
				txtEAX.setText("0x00000000");
				txtEAX.setEditable(false);

				Label lblEBP = new Label(cmpGReg, SWT.NONE);
				GridData gd_lblEBP = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblEBP.widthHint = 50;
				lblEBP.setLayoutData(gd_lblEBP);
				lblEBP.setText("EBP");

				txtEBP = new Text(cmpGReg, SWT.NONE);
				txtEBP.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtEBP.setText("0x00000000");
				GridData gd_txtEBP = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtEBP.widthHint = 120;
				txtEBP.setLayoutData(gd_txtEBP);
				txtEBP.setEditable(false);

				Label lblECX = new Label(cmpGReg, SWT.NONE);
				lblECX.setText("ECX");

				txtECX = new Text(cmpGReg, SWT.BORDER);
				txtECX.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtECX.setText("0x00000000");
				GridData gd_txtECX = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtECX.widthHint = 120;
				txtECX.setLayoutData(gd_txtECX);
				txtECX.setEditable(false);

				Label lblESP = new Label(cmpGReg, SWT.NONE);
				GridData gd_lblESP = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblESP.widthHint = 50;
				lblESP.setLayoutData(gd_lblESP);
				lblESP.setText("ESP");

				txtESP = new Text(cmpGReg, SWT.BORDER);
				txtESP.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtESP.setText("0x00000000");
				GridData gd_txtESP = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtESP.widthHint = 120;
				txtESP.setLayoutData(gd_txtESP);
				txtESP.setEditable(false);

				Label lblEDX = new Label(cmpGReg, SWT.NONE);
				lblEDX.setText("EDX");

				txtEDX = new Text(cmpGReg, SWT.BORDER);
				txtEDX.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtEDX.setText("0x00000000");
				txtEDX.setEditable(false);
				GridData gd_txtEDX = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtEDX.widthHint = 120;
				txtEDX.setLayoutData(gd_txtEDX);

				Label lblESI = new Label(cmpGReg, SWT.NONE);
				lblESI.setText("ESI");

				txtESI = new Text(cmpGReg, SWT.BORDER);
				txtESI.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_txtESI = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtESI.widthHint = 120;
				txtESI.setLayoutData(gd_txtESI);
				txtESI.setText("0x00000000");
				txtESI.setEditable(false);

				Label lblEBX = new Label(cmpGReg, SWT.NONE);
				lblEBX.setText("EBX");

				txtEBX = new Text(cmpGReg, SWT.BORDER);
				txtEBX.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtEBX.setTouchEnabled(true);
				GridData gd_txtEBX = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtEBX.widthHint = 120;
				txtEBX.setLayoutData(gd_txtEBX);
				txtEBX.setText("0x00000000");
				txtEBX.setEditable(false);

				Label lblEDI = new Label(cmpGReg, SWT.NONE);
				lblEDI.setText("EDI");

				txtEDI = new Text(cmpGReg, SWT.BORDER);
				txtEDI.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_txtEDI = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtEDI.widthHint = 120;
				txtEDI.setLayoutData(gd_txtEDI);
				txtEDI.setText("0x00000000");
				txtEDI.setEditable(false);
				new Label(cmpGReg, SWT.NONE);
				new Label(cmpGReg, SWT.NONE);
				new Label(cmpGReg, SWT.NONE);
				new Label(cmpGReg, SWT.NONE);

				Label lblCS = new Label(cmpGReg, SWT.NONE);
				lblCS.setText("CS");

				txtCS = new Text(cmpGReg, SWT.BORDER);
				txtCS.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_txtCS = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtCS.widthHint = 120;
				txtCS.setLayoutData(gd_txtCS);
				txtCS.setText("0x0000");
				txtCS.setEditable(false);

				Label lblEIP = new Label(cmpGReg, SWT.NONE);
				lblEIP.setText("EIP");

				txtEIP = new Text(cmpGReg, SWT.BORDER);
				txtEIP.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_txtEIP = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtEIP.widthHint = 120;
				txtEIP.setLayoutData(gd_txtEIP);
				txtEIP.setText("0x00000000");
				txtEIP.setEditable(false);

				Label lblSS = new Label(cmpGReg, SWT.NONE);
				lblSS.setText("SS");

				txtSS = new Text(cmpGReg, SWT.BORDER);
				txtSS.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtSS.setText("0x0000");
				txtSS.setEditable(false);
				GridData gd_txtSS = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtSS.widthHint = 120;
				txtSS.setLayoutData(gd_txtSS);

				Label lblESP2 = new Label(cmpGReg, SWT.NONE);
				lblESP2.setText("ESP");

				txtESP2 = new Text(cmpGReg, SWT.BORDER);
				txtESP2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_txtESP2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtESP2.widthHint = 120;
				txtESP2.setLayoutData(gd_txtESP2);
				txtESP2.setText("0x00000000");
				txtESP2.setEditable(false);

				Label lblDS = new Label(cmpGReg, SWT.NONE);
				lblDS.setText("DS");

				txtDS = new Text(cmpGReg, SWT.BORDER);
				txtDS.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtDS.setText("0x0000");
				txtDS.setEditable(false);
				GridData gd_txtDS = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtDS.widthHint = 120;
				txtDS.setLayoutData(gd_txtDS);

				Label lblES = new Label(cmpGReg, SWT.NONE);
				lblES.setText("ES");

				txtES = new Text(cmpGReg, SWT.BORDER);
				txtES.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_txtES = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtES.widthHint = 120;
				txtES.setLayoutData(gd_txtES);
				txtES.setText("0x0000");
				txtES.setEditable(false);

				Label lblFS = new Label(cmpGReg, SWT.NONE);
				lblFS.setText("FS");

				txtFS = new Text(cmpGReg, SWT.BORDER);
				txtFS.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtFS.setText("0x0000");
				txtFS.setEditable(false);
				GridData gd_txtFS = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtFS.widthHint = 120;
				txtFS.setLayoutData(gd_txtFS);

				Label lblGS = new Label(cmpGReg, SWT.NONE);
				lblGS.setText("GS");

				txtGS = new Text(cmpGReg, SWT.BORDER);
				txtGS.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				GridData gd_txtGS = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_txtGS.widthHint = 120;
				txtGS.setLayoutData(gd_txtGS);
				txtGS.setText("0x0000");
				txtGS.setEditable(false);

				Label lblEFLAGS = new Label(cmpGReg, SWT.NONE);
				lblEFLAGS.setText("EFLAGE");

				txtEFLAGS = new Text(cmpGReg, SWT.BORDER);
				txtEFLAGS.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				txtEFLAGS.setText("0x00000000");
				txtEFLAGS.setEditable(false);
				GridData gd_txtEFLAGS = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
				gd_txtEFLAGS.widthHint = 300;
				txtEFLAGS.setLayoutData(gd_txtEFLAGS);

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
			regSec2.setText("���ƼĴ���");
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
			linkAction = new Action("link") {
			};
			linkAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/icons/anchor.png"));
			linkAction.setChecked(true);
			linkAction.setChecked(false);
			linkAction.setEnabled(dm != null);
		}
		{
			refAction = new Action("ref") {
				@Override
				public void run() {
					refresh();
				}
			};
			refAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/icons/table_refresh.png"));
			refAction.setEnabled(dm != null);
		}
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
			toolbarManager.add(linkAction);
		toolbarManager.add(refAction);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void notify(String cmd, Object info) {
		if (cmd.equals(CmdStr.SelectDM)) {
			dm = (DebugModel) info;
			this.refAction.setEnabled(true);
			this.linkAction.setEnabled(true);
		} else if (cmd.equals(CmdStr.DisSelectDM)) {
			this.refAction.setEnabled(false);
			this.linkAction.setEnabled(false);
		} else if (cmd.equals("s") || cmd.equals("n")) {
			if (this.linkAction.isChecked()) {
				refresh();
			}
		}

	}

	private void refresh() {
		String rec = dm.sendToVM(new DCmd(CmdStr.reg));
		Map<String, String> _map = RegisterParse.parseReg(rec);
		this.txtEAX.setText(_map.get("eax"));
		this.txtECX.setText(_map.get("ecx"));
		this.txtEDX.setText(_map.get("edx"));
		this.txtEBX.setText(_map.get("ebx"));
		this.txtESI.setText(_map.get("esi"));
		this.txtEDI.setText(_map.get("edi"));
		this.txtEBP.setText(_map.get("ebp"));
		this.txtESP.setText(_map.get("esp"));
		this.txtEIP.setText(_map.get("eip"));
		this.txtEFLAGS.setText(_map.get("eflags"));
//		rec = dm.sendToVM(new DCmd(CmdStr.sreg));
//		_map = RegisterParse.parseSReg(rec);
//		this.txtCS.setText(_map.get("cs"));
	}

	@Override
	public void dispose() {
		sendToCenter(CmdStr.RemoveListener, this);
		super.dispose();
	}

	@Override
	public Set<String> getCare() {
		return this.cares;
	}

}