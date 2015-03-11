package hjow.methodconverter.swingconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.AdvancedManager;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.HelpManager;
import hjow.methodconverter.ui.MultipleConvertPanel;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.TextAreaComponent;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * <p>GUI Manager create with Swing library.</p>
 * 
 * <p>Swing 라이브러리를 사용해 개발한 GUI 매니저입니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingManager extends AdvancedManager implements ChangeListener, CanBeTransparent
{
	private static final long serialVersionUID = -3483133547553698477L;
	private JFrame frame;	
	private TransparentPanel converterPanel;
	private TransparentPanel selectorPanel;
	private TransparentPanel upPanel;
	private TransparentPanel downPanel;
	private JLabel loadLabel;
	private JLabel saveLabel;
	private JTextField loadField;
	private JButton loadFileSelect;
	private JButton loadButton;
	private JTextField saveField;
	private JButton saveFileSelect;
	private JButton saveButton;
	private TextAreaComponent textPanel;
	private TransparentScrollPane textScroll;
	private TransparentPanel controlPanel;
	private JButton exitButton;
	private TransparentPanel thirdPanel;
	private JLabel classNameLabel;
	private JTextField classNameField;
	private JLabel methodNameLabel;
	private JTextField methodNameField;
	private JLabel variableNameLabel;
	private JTextField variableNameField;
	private JComboBox optionChoice;
	private JButton convertButton;
	private TransparentPanel bottomPanel;
	private TransparentPanel statusBar;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuFileExit;
	private JMenuItem menuFileSave;
	private JMenuItem menuFileLoad;
	private JMenuItem menuFileConvert;
	private JMenu menuTool;
	private JMenuItem menuToolSetting;
	private TransparentPanel parameterPanel;
	private CardLayout parameterLayout;
	private TransparentPanel modeSelectPanel;
	private JComboBox modeSelector;
	private TransparentPanel parameterInputPanel;
	private JTextField parameterField;
	private JMenu menuHelp;
	private JMenuItem menuHelpAbout;
	private JMenuItem menuToolEditor;	
	private JButton clearButton;
	private JButton copyButton;
	private TransparentPanel leftControlPanel;
	private TransparentPanel centerControlPanel;
	private TransparentPanel rightControlPanel;
	private JButton shadeButton;
	private TransparentPanel loadSavePanel;
	private TransparentPanel topPanel;
	private TransparentPanel shadePanel;
	private TransparentPanel mainPanel;
	private JTabbedPane mainTab;	
	private transient int refreshIndex;
	private JMenuItem menuToolUndo;	
	private JButton parameterButton;
	private JMenuItem menuToolBinary;
	private JCheckBox alignCheck;
	private MultipleConvertPanel multipleConverter = null;
	private JMenuItem menuToolDaemonClient;
	private JMenuItem menuToolDaemon;
	protected float transparency_opacity = Controller.DEFAULT_OPACITY_RATIO;
	private JMenuItem menuHelpHelp;
	private HelpManager helpManager;
	private TransparentPanel browserPane;
	private SimpleBrowserPane browserArea;
	
	/**
	 * <p>Constructor</p>
	 * 
	 * <p>생성자입니다.</p>
	 */
	public SwingManager()
	{
		super();
	}	
	
	protected void init()
	{		
		initTheme();
		
		int w = 550, h = 400;
		Dimension scrSize = null;
		boolean w_default = false;
		boolean h_default = false;
		try
		{
			scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		}
		catch(Exception e)
		{
			scrSize = null;
		}
		try
		{
			if(Controller.getOption("width") != null)
			{
				w = Integer.parseInt(Controller.getOption("width"));
			}
			else
			{
				w_default = true;
			}
		}
		catch(Exception e)
		{
			w_default = true;
		}
		try
		{
			if(Controller.getOption("height") != null)
			{
				h = Integer.parseInt(Controller.getOption("height"));
			}
			else
			{
				h_default = true;
			}
		}
		catch(Exception e)
		{
			h_default = true;
		}
		
		if(w_default)
		{
			if(scrSize == null) w = 550;
			else
			{
				w = (int) (scrSize.getWidth() - (scrSize.getWidth() / 4));
				if(w < 550) w = 550;
			}
		}
		if(h_default)
		{
			if(scrSize == null) h = 400;
			else
			{
				h = (int) (scrSize.getHeight() - (scrSize.getHeight() / 4));
				if(h < 550) h = 400;
			}
		}
		
		frame = new TransparentFrame();
		frame.setTitle(Controller.getString("Method Converter"));
		frame.setSize(w, h);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth()/2 - frame.getWidth()/2), (int)(screenSize.getHeight()/2 - frame.getHeight()/2));
		frame.addWindowListener(this);
		
		aboutDialog = new SwingAboutDialog(frame);
		
		
		try
		{
			setIcon(frame, new ImageIcon(getClass().getClassLoader().getResource("ico.png")).getImage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
				
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuFileSave = new JMenuItem(Controller.getString("Save"));
		menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.CTRL_MASK));	
		menuFileSave.addActionListener(this);
		menuFile.add(menuFileSave);		
		
		menuFileLoad = new JMenuItem(Controller.getString("Load"));
		menuFileLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, KeyEvent.CTRL_MASK));
		menuFileLoad.addActionListener(this);
		menuFile.add(menuFileLoad);	
		
		menuFile.addSeparator();
		
		menuFileConvert = new JMenuItem(Controller.getString("Convert"));
		menuFileConvert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, KeyEvent.CTRL_MASK));
		menuFileConvert.addActionListener(this);
		menuFile.add(menuFileConvert);	
		
		menuFile.addSeparator();
		
		menuFileExit = new JMenuItem(Controller.getString("Exit"));
		menuFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
		menuFileExit.addActionListener(this);
		menuFile.add(menuFileExit);	
		
		menuTool = new JMenu("Tool");
		menuBar.add(menuTool);
		
		menuToolUndo = new JMenuItem(Controller.getString("Undo"));
		menuToolUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		menuToolUndo.addActionListener(this);
		menuTool.add(menuToolUndo);
		
		menuTool.addSeparator();
		
		menuToolBinary = new JMenuItem(Controller.getString("Binary") + " " + Controller.getString("Converter"));
		menuToolBinary.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, KeyEvent.CTRL_MASK));
		menuToolBinary.addActionListener(this);
		menuTool.add(menuToolBinary);
		
		binaryConverter = new SwingBinaryConverter(frame);	
				
		menuToolSetting = new JMenuItem(Controller.getString("Preference"));
		menuToolSetting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, KeyEvent.CTRL_MASK));
		settingManager = new SwingSettingManager(frame);
		menuToolSetting.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				settingManager.open();				
			}			
		});
		menuTool.add(menuToolSetting);
		
		menuTool.addSeparator();
		
		menuToolEditor = new JMenuItem(Controller.getString("Module Editor"));
		moduleEditor = new SwingModuleEditor(frame);
		menuToolEditor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				moduleEditorOpenMenuSelected();				
			}			
		});
		menuTool.add(menuToolEditor);
		
		if(Statics.useUntestedFunction())
		{
			menuTool.addSeparator();
			menuToolDaemonClient = new JMenuItem(Controller.getString("Daemon Client"));
			menuToolDaemonClient.addActionListener(this);
			menuTool.add(menuToolDaemonClient);
			
			daemonClient = new SwingDaemonClient(frame);
			
			menuToolDaemon = new JMenuItem(Controller.getString("Daemon"));
			menuToolDaemon.addActionListener(this);
			menuTool.add(menuToolDaemon);
		}
		
		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		helpManager = new SwingHelpManager(frame);
		
		menuHelpHelp = new JMenuItem(Controller.getString("Help"));
		menuHelpHelp.addActionListener(this);
		menuHelp.add(menuHelpHelp);
		
		menuHelpAbout = new JMenuItem(Controller.getString("About"));
		menuHelpAbout.addActionListener(this);
		menuHelp.add(menuHelpAbout);
		
		frame.getContentPane().setLayout(new BorderLayout());
		mainPanel = new TransparentPanel();
		mainPanel.setLayout(new BorderLayout());
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
				
		mainTab = new TransparentTabbedPane();
		mainTab.addChangeListener(this);
		mainPanel.add(mainTab, BorderLayout.CENTER);
		
		converterPanel = new TransparentPanel();
		converterPanel.setLayout(new BorderLayout());
		
				
		prepareTabs();	
		
		
		selectorPanel = new TransparentPanel();
		converterPanel.add(selectorPanel, BorderLayout.NORTH);
		
		boolean useLineNumbers = false;
		
		try
		{
			useLineNumbers = Statics.parseBoolean(Controller.getOption("showLineNumbers"));			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			useLineNumbers = false;
		}
		
		if(useLineNumbers)
		{
			try
			{
				textPanel = new LineNumberTextArea();							
				converterPanel.add(textPanel.getComponent(), BorderLayout.CENTER);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
				textPanel = new TransparentTextArea();	
				textScroll = new TransparentScrollPane(textPanel.getComponent());
				
				converterPanel.add(textScroll, BorderLayout.CENTER);
			}
		}
		else
		{
			textPanel = new TransparentTextArea();	
			textScroll = new TransparentScrollPane(textPanel.getComponent());
			
			converterPanel.add(textScroll, BorderLayout.CENTER);
		}
		
		bottomPanel = new TransparentPanel();
		bottomPanel.setLayout(new BorderLayout());
		converterPanel.add(bottomPanel, BorderLayout.SOUTH);
		//mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		controlPanel = new TransparentPanel();
		controlPanel.setLayout(new BorderLayout());
		
		bottomPanel.add(controlPanel, BorderLayout.CENTER);
				
		statusBar = new TransparentPanel();
		statusBar.setLayout(new BorderLayout());
		mainPanel.add(statusBar, BorderLayout.SOUTH);
		//bottomPanel.add(statusBar, BorderLayout.SOUTH);
		
		
		Controller.setStatusBar(new SwingStatusBar());
		statusBar.add(Controller.getStatusBar(), BorderLayout.CENTER);	
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		convertButton = new JButton("Convert");
		convertButton.addActionListener(this);	
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);	
		copyButton = new JButton("Copy");
		copyButton.addActionListener(this);
		
		optionChoice = new JComboBox();
		optionChoice.addItemListener(this);
		
		leftControlPanel = new TransparentPanel();
		centerControlPanel = new TransparentPanel();
		rightControlPanel = new TransparentPanel();
		leftControlPanel.setLayout(new FlowLayout());
		centerControlPanel.setLayout(new FlowLayout());
		rightControlPanel.setLayout(new FlowLayout());
		
		controlPanel.add(leftControlPanel, BorderLayout.WEST);
		controlPanel.add(centerControlPanel, BorderLayout.CENTER);
		controlPanel.add(rightControlPanel, BorderLayout.EAST);
		
		centerControlPanel.add(optionChoice);
		centerControlPanel.add(convertButton);
		leftControlPanel.add(copyButton);
		leftControlPanel.add(clearButton);
		rightControlPanel.add(exitButton);				
		
		selectorPanel.setLayout(new BorderLayout());
		
		topPanel = new TransparentPanel();
		topPanel.setLayout(new BorderLayout());
		
		modeSelectPanel = new TransparentPanel();
		modeSelectPanel.setLayout(new FlowLayout());
		modeSelector = new JComboBox();
		modeSelector.addItemListener(this);
		modeSelectPanel.add(modeSelector);
		
		shadePanel = new TransparentPanel();
		shadePanel.setLayout(new FlowLayout());
		shadeButton = new JButton("▲");
		shadeButton.addActionListener(this);
		shadePanel.add(shadeButton);
		
		topPanel.add(modeSelectPanel, BorderLayout.CENTER);
		topPanel.add(shadePanel, BorderLayout.EAST);
		
		selectorPanel.add(topPanel, BorderLayout.NORTH);
		
		loadSavePanel = new TransparentPanel();
		loadSavePanel.setLayout(new GridLayout(2, 1));
		selectorPanel.add(loadSavePanel, BorderLayout.CENTER);
		
		upPanel = new TransparentPanel();
		downPanel = new TransparentPanel();
		thirdPanel = new TransparentPanel();
		
		loadSavePanel.add(upPanel);
		loadSavePanel.add(downPanel);
		
		parameterPanel = new TransparentPanel();
		parameterLayout = new CardLayout();
		parameterPanel.setLayout(parameterLayout);
		parameterPanel.add(thirdPanel, "return_method");
		selectorPanel.add(parameterPanel, BorderLayout.SOUTH);
		
		parameterInputPanel = new TransparentPanel();
		parameterInputPanel.setLayout(new BorderLayout());
		parameterField = new JTextField();
		parameterInputPanel.add(parameterField, BorderLayout.CENTER);
		parameterGetter = new SwingParameterGetter(this);
		parameterButton = new JButton(Controller.getString("Parameter Agent"));
		parameterButton.addActionListener(this);
		parameterInputPanel.add(parameterButton, BorderLayout.EAST);
		parameterPanel.add(parameterInputPanel, "parameter");
		
		upPanel.setLayout(new FlowLayout());
		downPanel.setLayout(new FlowLayout());
		thirdPanel.setLayout(new FlowLayout());
		
		classNameLabel = new JLabel(Controller.getString("Class"));
		classNameField = new JTextField(10);
		classNameField.setText("Converted");
		thirdPanel.add(classNameLabel);
		thirdPanel.add(classNameField);
		
		methodNameLabel = new JLabel(Controller.getString("Method"));
		methodNameField = new JTextField(10);
		methodNameField.setText("text");
		thirdPanel.add(methodNameLabel);
		thirdPanel.add(methodNameField);
		
		variableNameLabel = new JLabel(Controller.getString("Variable"));
		variableNameField = new JTextField(10);
		variableNameField.setText("r");
		thirdPanel.add(variableNameLabel);
		thirdPanel.add(variableNameField);
		
		alignCheck = new JCheckBox(Controller.getString("Align"));
		thirdPanel.add(alignCheck);
		
		parameterLayout.show(parameterPanel, "parameter");
		nowShowingParamPanelName = "parameter";
						
		
		loadLabel = new JLabel(Controller.getString("Load from..."));
		loadField = new JTextField(20);
		loadFileSelect = new JButton(Controller.getString("..."));
		loadButton = new JButton("Load");
		loadFileSelect.addActionListener(this);
		loadButton.addActionListener(this);
		
		upPanel.add(loadLabel);
		upPanel.add(loadField);
		upPanel.add(loadFileSelect);
		upPanel.add(loadButton);
		
		saveLabel = new JLabel(Controller.getString("Save from..."));
		saveField = new JTextField(20);
		saveFileSelect = new JButton(Controller.getString("..."));
		saveButton = new JButton(Controller.getString("Save"));
		saveFileSelect.addActionListener(this);
		saveButton.addActionListener(this);
		
		downPanel.add(saveLabel);
		downPanel.add(saveField);
		downPanel.add(saveFileSelect);
		downPanel.add(saveButton);
		
		try
		{
			binaryConverter.setStatusField((StatusBar) Controller.getStatusBar());
		}
		catch(Exception e)
		{
			
		}
		binaryConverter.setStatusViewer(status);
		
		loadStringTable();
		loadSyntaxes();
		
		try
		{
			status = new SwingGageBar();
			statusBar.add(status.toComponent(), BorderLayout.EAST);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		try
		{
			browserPane = new TransparentPanel();
			browserArea = new SimpleBrowserPane();
			browserPane.setLayout(new BorderLayout());
			browserPane.add(browserArea.getComponent(), BorderLayout.CENTER);
			browserArea.setStatusViewer(status);
			//mainTab.add(Controller.getString("e"), browserPane);
			//browserArea.setPage(Controller.getDefaultURL());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		Controller.resetSyntaxList();
		syntaxChoiceRefresh();
		refreshModule();
		
		reverseHiding();
		
		System.out.println("Method Converter initialize complete.");
		Controller.setStatusText(Controller.getFirstMessage());
		
		super.init();
		try
		{
			FontSetting.prepareFont();
			FontSetting.setFontRecursively(frame, FontSetting.usingFont);
		}
		catch(Exception e)
		{
			System.out.println(Controller.getString("Fail to load font") + " : " + e.getMessage());
			// e.printStackTrace();
		}
	}
	
	/**
	 * <p>Load and apply Swing look and feel.</p>
	 * 
	 * <p>Swing 룩앤필을 적용합니다.</p>
	 */
	public static void initTheme()
	{
		try		
		{			
			if(Controller.getOption("theme") == null)
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			else
			{
				String theme = Controller.getOption("theme");
				if(theme.equalsIgnoreCase("nimbus"))
				{
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");					
				}
				else UIManager.setLookAndFeel(theme);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}

	private void prepareTabs()
	{
		try
		{
			if(Statics.useOnlineContent() && packageView == null) packageView = new SwingPackagePanel(this);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			packageView = null;
		}
		
		try
		{
			if(memoryManager == null) memoryManager = new SwingMemoryManager(this);			
			memoryManager.open();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			memoryManager = null;
		}
		
		try
		{
			if(Controller.getGrade() >= 1 && moduleList == null) moduleList = new SwingModuleList(this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			moduleList = null;
		}
		
		try
		{
			if(Controller.getGrade() >= 2) multipleConverter = new SwingMultipleConvertPanel();
		}
		catch(Exception e)
		{
			
		}
		
		SwingUtilities.invokeLater(new Runnable()
		{			
			@Override
			public void run()
			{
				mainTab.removeAll();
				mainTab.add(converterPanel, Controller.getString("Convert"));		
				if(multipleConverter != null) mainTab.add(multipleConverter.getComponent(), Controller.getString("Convert") + " " + Controller.getString("Multiples"));
				if(packageView != null) mainTab.add(packageView.getComponent(), Controller.getString("Package") + " " + Controller.getString("Receive"));
				if(moduleList != null) mainTab.add(moduleList.getComponent(), Controller.getString("User defined") + " " + Controller.getString("Module list"));
				if(memoryManager != null) mainTab.add(memoryManager.getComponent(), Controller.getString("Memory"));
				if(Controller.getPrinterComponent() != null) mainTab.add(Controller.getPrinterComponent(), Controller.getString("Console"));				
			}
		});			
	}
	
	@Override
	public String getSelectedTab()
	{
		if(mainTab.getSelectedComponent() == converterPanel) 
			return AdvancedManager.CONVERT;
		else if(mainTab.getSelectedComponent() == multipleConverter.getComponent())
			return AdvancedManager.MULTIPLE_CONVERT;
		else if(packageView != null && mainTab.getSelectedComponent() == packageView.getComponent()) 
			return AdvancedManager.PACKAGE_RECEIVE;
		else if(moduleList != null && mainTab.getSelectedComponent() == moduleList.getComponent()) 
			return AdvancedManager.MODULE_LIST;
		else if(memoryManager != null && mainTab.getSelectedComponent() == memoryManager.getComponent()) 
			return AdvancedManager.MEMORY_MANAGER;
		else if(Controller.getPrinterComponent() != null && mainTab.getSelectedComponent() == Controller.getPrinterComponent()) 
			return AdvancedManager.PRINTER;
		return null;
	}

	/**
	 * <p>Refresh programming-language syntax list</p>
	 * 
	 * <p>프로그래밍 언어 문법 선택 리스트를 새로고침합니다.</p>
	 */
	@Override
	protected void syntaxChoiceRefresh()
	{			
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				optionChoice.removeAllItems();
				List<String> keys = Controller.getAvailableOptions();
				for(String s : keys)
				{
					optionChoice.addItem(s);
				}
				if(Statics.useUntestedFunction())
				{
					optionChoice.setEditable(true);
				}
				else optionChoice.setEditable(Controller.isOptionFieldEditable());
				
				List<String> pluginKeys = Controller.getPluginNames();
				for(String p : pluginKeys)
				{
					optionChoice.addItem(p);
				}
			}
		});		
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == frame)
		{
			close();
		}		
	}	
	
	/**
	 * 
	 * <p>Apply language (string table).</p>
	 * 
	 * <p>스트링 테이블을 적용합니다.</p>
	 * 
	 */
	@Override
	public void setLanguage()
	{
		frame.setTitle(Controller.getString("Method Converter"));		
		saveLabel.setText(Controller.getString("Save from..."));
		saveButton.setText(Controller.getString("Save"));
		saveFileSelect.setText(Controller.getString("..."));
		loadLabel.setText(Controller.getString("Load from..."));
		loadButton.setText(Controller.getString("Load"));
		loadFileSelect.setText(Controller.getString("..."));
		clearButton.setText(Controller.getString("Clear"));
		copyButton.setText(Controller.getString("Copy"));
		exitButton.setText(Controller.getString("Exit"));
		classNameLabel.setText(Controller.getString("Class"));
		methodNameLabel.setText(Controller.getString("Method"));
		variableNameLabel.setText(Controller.getString("Variable"));
		convertButton.setText(Controller.getString("Convert"));
		parameterButton.setText(Controller.getString("Add"));
		menuFile.setText(Controller.getString("File"));
		menuFileConvert.setText(Controller.getString("Convert"));		
		menuFileLoad.setText(Controller.getString("Load"));
		menuFileSave.setText(Controller.getString("Save"));
		menuFileExit.setText(Controller.getString("Exit"));
		menuTool.setText(Controller.getString("Tool"));
		menuToolUndo.setText(Controller.getString("Undo"));
		menuToolSetting.setText(Controller.getString("Preference"));
		menuToolEditor.setText(Controller.getString("Module") + " " + Controller.getString("Editor"));
		menuHelp.setText(Controller.getString("Help"));
		menuHelpAbout.setText(Controller.getString("About"));
		settingManager.setLanguage();
	}
	/**
	 * 
	 * <p>Enable, or disable all components.</p>
	 * 
	 * <p>모든 컴포넌트(버튼, 텍스트 필드 등)를 활성화하거나 비활성화합니다.</p>
	 * 
	 * @param l : If this is true, all components will be enabled. else, all components will be disabled.
	 */
	@Override
	public void enableAll(boolean l)
	{
		mainTab.setEnabled(l);
		saveFileSelect.setEnabled(l);
		loadFileSelect.setEnabled(l);
		saveButton.setEnabled(l);
		loadButton.setEnabled(l);
		saveField.setEnabled(l);
		loadField.setEnabled(l);
		classNameField.setEnabled(l);
		methodNameField.setEnabled(l);
		variableNameField.setEnabled(l);
		alignCheck.setEnabled(l);
		exitButton.setEnabled(l);
		clearButton.setEnabled(l);
		copyButton.setEnabled(l);
		optionChoice.setEnabled(l);
		modeSelector.setEnabled(l);
		menuBar.setEnabled(l);
		Controller.enablePrinter(l);
	}
	
	/**
	 * <p>Print loaded text on main screen</p>
	 * 
	 * <p>불러온 텍스트를 화면에 보입니다.</p>
	 */
	@Override
	public void setTextOnArea(String str)
	{
		textPanel.setText(str);
	}
	
	@Override
	public String getTextOnArea()
	{
		return textPanel.getText();
	}
	
	/**
	 * 
	 * <p>Open manager window.</p>
	 * 
	 * <p>매니저 창을 엽니다.</p>
	 * 
	 */
	@Override
	public void open()
	{		
		try
		{
			loadField.setText(Controller.getLoadFile().getAbsolutePath());
		}
		catch (Exception e)
		{
			
		}
		try
		{
			saveField.setText(Controller.getSaveFile().getAbsolutePath());
		}
		catch (Exception e)
		{
			
		}
		
		frame.setVisible(true);
		super.open();
		
		if((memoryManager != null) && (memoryManager instanceof SwingMemoryManager)) ((SwingMemoryManager) memoryManager).setDividerCenter();
	}
	
	
	/**
	 * 
	 * <p>Open file-open dialog to take file path.
	 * If loadAfterThis is true, load contents from selected file.
	 * If loadAfterThis is false, just open file-open dialog.
	 * 
	 * <p>불러오기 창을 열어 파일을 선택합니다.
	 * loadAfterThis 가 true 이면, 파일이 선택된 경우 불러오기 작업까지 수행합니다.
	 * false 이면, 파일을 선택만 합니다.
	 * 
	 * @param loadAfterThis : Decide to load after the file is selected
	 */
	@Override
	public void loadFileSelect(boolean loadAfterThis)
	{
		try
		{
			JFileChooser loadDialog = new JFileChooser(Controller.getDefaultPath());
			int fileSelect = loadDialog.showOpenDialog(frame);
			if(fileSelect == JFileChooser.APPROVE_OPTION)
			{
				Controller.setLoadFile(loadDialog.getSelectedFile());
				loadField.setText(Controller.getLoadFile().getAbsolutePath());
				if(loadAfterThis)
				{					
					load();
				}
			}
		}
		catch (Exception e1)
		{
			//e1.printStackTrace();
			//StringToMethodConverter.statusField.setText("There is a problem : " + e1.getMessage());
		}
	}
	/**
	 * 
	 * <p>Open file-save dialog to take file path.<br>
	 * If saveAfterThis is true, save contents from selected file.<br>
	 * If saveAfterThis is false, just open file-save dialog.</p>
	 * 
	 * <p>저장 창을 열어 파일을 선택합니다.<br>
	 * saveAfterThis 가 true 이면, 파일 선택 시 저장 작업을 수행합니다.<br>
	 * false 이면 파일 선택만 합니다.</p>
	 * 
	 * @param saveAfterThis : Decide to save after the file is selected
	 */
	@Override
	public void saveFileSelect(boolean saveAfterThis)
	{
		try
		{
			JFileChooser saveDialog = new JFileChooser(Controller.getDefaultPath());
			int fileSelect = saveDialog.showSaveDialog(frame);
			if(fileSelect == JFileChooser.APPROVE_OPTION)
			{				
				Controller.setSaveFile(saveDialog.getSelectedFile());
				saveField.setText(Controller.getSaveFile().getAbsolutePath());
				if(saveAfterThis)
				{					
					save();
				}
			}
		}
		catch (Exception e1)
		{
			//e1.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == loadButton)
		{
			enableAll(false);
			loadNextTime = true;			
		}
		else if(ob == saveButton)
		{
			enableAll(false);
			saveNextTime = true;
		}		
		else if(ob == loadFileSelect)
		{
			loadFileSelect();
		}
		else if(ob == saveFileSelect)
		{
			saveFileSelect();
		}
		else if(ob == exitButton || ob == menuFileExit)
		{
			close();
		}
		else if(ob == copyButton)
		{
			setClipboard(getTextOnArea());
			alert(Controller.getString("Text is copied to the clipboard."));
		}
		else if(ob == clearButton)
		{
			clearArea();
		}
		else if(ob == convertButton || ob == menuFileConvert)
		{
			convert();
		}
		else if(ob == menuFileLoad)
		{
			loadFileSelect(true);
		}
		else if(ob == menuFileSave)
		{
			saveFileSelect(true);
		}
		else if(ob == menuHelpAbout)
		{
			aboutDialog.open();
		}
		else if(ob == menuHelpHelp)
		{
			helpManager.open();
		}
		else if(ob == shadeButton)
		{
			reverseHiding();
		}
		else if(ob == menuToolUndo)
		{
			if(! (Controller.getContent().equals(getTextOnArea())))
			{
				Controller.setContent(getTextOnArea());
			}
			Controller.undo();
			setTextOnArea(Controller.getContent());
		}
		else if(ob == parameterButton)
		{
			parameterGetter.open();
		}
		else if(ob == menuToolBinary)
		{
			binaryConverter.open();
		}
		else if(ob == menuToolDaemonClient)
		{
			runDaemonClient();
		}
		else if(ob == menuToolDaemon)
		{
			runDaemon();
		}
	}	
	
	@Override
	protected String selectedSaveFile()
	{
		return saveField.getText();
	}
	@Override
	protected String selectedLoadFile()
	{
		return loadField.getText();
	}
	@Override
	protected String selectedSyntax()
	{
		return String.valueOf(optionChoice.getSelectedItem());
	}
	@Override
	protected String takeClassName()
	{
		return classNameField.getText();
	}
	@Override
	protected String takeMethodName()
	{
		return methodNameField.getText();
	}
	@Override
	protected String takeVariableName()
	{
		return variableNameField.getText();
	}
	
	@Override
	protected boolean takeAlignCheck()
	{
		return alignCheck.isSelected();
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object ob = e.getSource();
		if(ob == modeSelector)
		{
			selectModule(modeSelector.getSelectedIndex());
		}
	}
	
	/**
	 * <p>Return Frame or JFrame object.</p>
	 * 
	 * <p>프레임 객체를 반환합니다.</p>
	 * 
	 * @return JFrame object
	 */
	public JFrame getFrame()
	{
		return frame;
	}
	
	/**
	 * <p>Select "Convert" tab.</p>
	 * 
	 * <p>변환 탭을 선택합니다.</p>
	 */
	public void selectConvertTextTab()
	{
		try
		{
			SwingUtilities.invokeLater(new Runnable()
			{						
				@Override
				public void run()
				{
					mainTab.setSelectedComponent(converterPanel);					
				}
			});	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void refreshPackageList()
	{
		packageView.refresh();
		try
		{
			for(int i=0; i<mainTab.getComponentCount(); i++)
			{
				refreshIndex = i;
				if(mainTab.getComponentAt(i) == packageView.getComponent())
				{
					SwingUtilities.invokeLater(new Runnable()
					{						
						@Override
						public void run()
						{
							mainTab.setTitleAt(refreshIndex, Controller.getString("Package") + " " + Controller.getString("Receive") + " (" + String.valueOf(Controller.getReceivesCount()) + ")");							
						}
					});					
					break;
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void refreshModule()
	{
		modeSelector.removeAllItems();
		for(String s : Controller.getModuleList())
		{
			modeSelector.addItem(s);
		}
		modeSelector.setSelectedIndex(0);
	}
	@Override
	public void selectModule(int index)
	{		
		if(index < 0)
		{
			Controller.selectMode(0);
			return;
		}
		
		
		if(index == 0) 
		{
			parameterLayout.show(parameterPanel, "return_method");
			nowShowingParamPanelName = "return_method";
		}
		else
		{
			parameterLayout.show(parameterPanel, "parameter");
			nowShowingParamPanelName = "parameter";
		}
		
		try
		{
			String paramText = parameterField.getText();
			if(paramText != null) paramText = paramText.trim();
			if(paramText.equals("") || paramText.equals(Controller.getModules().get(Controller.getSelectedMode()).defaultParameterText()))
			{
				parameterField.setText(Controller.getModules().get(index).defaultParameterText());
			}
		}
		catch(Exception e)
		{
			
		}
		Controller.selectMode(index);
		syntaxChoiceRefresh();
	}
	@Override
	protected void setSelectModuleComboBox(int i)
	{
		modeSelector.setSelectedIndex(i);
		super.setSelectModuleComboBox(i);
	}

	@Override
	public String getParameterFieldText()
	{		
		return parameterField.getText();
	}

	@Override
	public int getSyntaxSelectedIndex()
	{
		return optionChoice.getSelectedIndex();
	}

	@Override
	public String getSyntaxSelectedItem()
	{
		return String.valueOf(optionChoice.getSelectedItem());
	}

	@Override
	public void alert(String str)
	{
		JOptionPane.showMessageDialog(frame, str);		
	}
	
	@Override
	public boolean requestYes(String message)
	{
		int selection = JOptionPane.showConfirmDialog(frame, message, message, JOptionPane.YES_NO_OPTION);
		if(selection == JOptionPane.YES_OPTION) return true;
		return false; 
	}
	@Override
	public String requestInput(String message)
	{
		return JOptionPane.showInputDialog(frame, message);
	}

	@Override
	public void reverseHiding()
	{
		SwingUtilities.invokeLater(new Runnable()
		{				
			@Override
			public void run()
			{
				if(loadSavePanel.isVisible())
				{
					loadSavePanel.setVisible(false);
					shadeButton.setText("▼");
				}
				else
				{
					loadSavePanel.setVisible(true);
					shadeButton.setText("▲");
				}
			}
		});
	}

	@Override
	public void setParameterFieldText(String params)
	{
		parameterField.setText(params);
	}

	@Override
	public Window getWindow()
	{
		return frame;
	}

	@Override
	protected void setVisible(boolean visible)
	{
		frame.setVisible(visible);		
	}
	
	@Override
	public void refreshObjects()
	{
		try
		{
			packageView.refresh();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			if(! memoryManager.isAlive())
			{
				SwingUtilities.invokeLater(new Runnable()
				{					
					@Override
					public void run()
					{
						try
						{
							mainTab.remove(memoryManager.getComponent());
						}
						catch(Exception e)
						{
							
						}					
					}
				});
			}
		}
		catch(Exception e)
		{
			
		}	
		try
		{
			System.out.println(Controller.getPrinterName());
			if(! (Controller.getPrinterName().equalsIgnoreCase("SwingPrinter")))
			{
				SwingUtilities.invokeLater(new Runnable()
				{					
					@Override
					public void run()
					{
						try
						{
							mainTab.removeAll();		
							prepareTabs();		
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}					
					}
				});
			}
		}
		catch(Exception e)
		{
			
		}	
		
		Controller.checkPrinter();
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		Object ob = e.getSource();
		if(ob == mainTab)
		{
			try
			{
				if(mainTab.getSelectedComponent() == Controller.getPrinterComponent())
				{
					Controller.focusOnPrinter();
				}
			}
			catch(Exception e1)
			{
				
			}
		}
	}
	
	/**
	 * <p>Set image on JFrame.</p>
	 * 
	 * <p>JFrame 객체에 이미지 아이콘을 입힙니다.</p>
	 * 
	 * @param frame : JFrame object
	 * @param image : Image object
	 */
	public static void setIcon(JFrame frame, Image image)
	{
		try
		{
			frame.setIconImage(image);
		}
		catch(Exception e)
		{
			// e.printStackTrace();
		}
	}
	
	/**
	 * <p>Set image on JFrame.</p>
	 * 
	 * <p>JFrame 객체에 이미지 아이콘을 입힙니다.</p>
	 * 
	 * @param frame : JFrame object
	 * @param name : Image file name with classpath
	 */
	public static void setIcon(JFrame frame, String name)
	{
		try
		{
			setIcon(frame, new ImageIcon(frame.getClass().getClassLoader().getResource(name)).getImage());
		}
		catch(Exception e)
		{
			// e.printStackTrace();
		}
	}
	
	/**
	 * <p>Set image on JDialog.</p>
	 * 
	 * <p>JDialog 객체에 이미지 아이콘을 입힙니다.</p>
	 * 
	 * @param frame : JDialog object
	 * @param image : Image object
	 */
	public static void setIcon(JDialog frame, Image image)
	{
		try
		{
			frame.setIconImage(image);
		}
		catch(Exception e)
		{
			// e.printStackTrace();
		}
	}
	
	/**
	 * <p>Set image on JDialog.</p>
	 * 
	 * <p>JDialog 객체에 이미지 아이콘을 입힙니다.</p>
	 * 
	 * @param frame : JDialog object
	 * @param name : Image file name with classpath
	 */
	public static void setIcon(JDialog frame, String name)
	{
		try
		{
			setIcon(frame, new ImageIcon(frame.getClass().getClassLoader().getResource(name)).getImage());
		}
		catch(Exception e)
		{
			// e.printStackTrace();
		}
	}
	
	@Override
	public void reinitializeAvailableComponents()
	{
		prepareTabs();
	}

	@Override
	public float getTransparency_opacity()
	{
		return transparency_opacity;
	}

	@Override
	public void setTransparency_opacity(float transparency_opacity)
	{
		this.transparency_opacity = transparency_opacity;
		for(int i=0; i<frame.getComponentCount(); i++)
		{
			try
			{
				if(frame.getComponent(i) instanceof CanBeTransparent)
				{
					((CanBeTransparent) (frame.getComponent(i))).setTransparency_opacity(transparency_opacity);
				}
			}
			catch(Exception e)
			{
				
			}
		}
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				frame.repaint();
			}			
		});
	}
}