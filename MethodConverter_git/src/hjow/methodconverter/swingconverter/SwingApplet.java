package hjow.methodconverter.swingconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.AdvancedManager;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.MultipleConvertPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.undo.UndoManager;

/**
 * <p>This class makes this program can be run as Applet.</p>
 * 
 * <p>이 클래스는 이 프로그램이 애플릿으로 실행될 수 있게 해 줍니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingApplet extends JApplet
{
	private static final long serialVersionUID = 8582503257910923681L;
	private SwingAppletInnerPanel inners;
	@Override
	public void init()
	{
		inners = new SwingAppletInnerPanel(this);
		this.setLayout(new BorderLayout());
		this.add(inners.getComponent());
	}
}
/**
 * <p>This class object can presents many components in Applet.</p>
 * 
 * <p>이 클래스 객체는 애플릿에 컴포넌트들을 배치하고 보여 줍니다.</p>
 * 
 * @author HJOW
 *
 */
class SwingAppletInnerPanel extends AdvancedManager implements ChangeListener
{
	private static final long serialVersionUID = -3483133547553698477L;
	
	/**
	 * <p>Set default applet theme.</p>
	 * 
	 * <p>애플릿의 테마를 지정합니다.</p>
	 * 
	 * <p>Availables : Metal, Nimbus, OS, Optimized</p>
	 */
	private static String APPLET_THEME = "Optimized";
	
	private TransparentPanel superPanel;
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
	private JTextArea textPanel;
	private JScrollPane textScroll;
	private TransparentPanel controlPanel;
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
	private JTextArea lineNumberPanel;
	private UndoManager undoManager;
	private boolean useUndoManager = false;
	
	/**
	 * <p>Constructor</p>
	 * 
	 * <p>생성자입니다.</p>
	 */
	public SwingAppletInnerPanel(JApplet applet)
	{
		super();
		applet.setJMenuBar(menuBar);
	}
	
	/**
	 * <p>Return component which has many components.</p>
	 * 
	 * <p>여러 컴포넌트가 부착된 상위 컴포넌트 하나를 반환합니다.</p>
	 * 
	 * @return component which can be attached on the Applet.</p>
	 */
	public Component getComponent()
	{
		return superPanel;
	}
	
	/**
	 * <p>Initialize components.</p>
	 * 
	 * <p>컴포넌트들을 초기화합니다.</p>
	 */
	protected void init()
	{		
		Controller.prepareOptions(false);
		
		superPanel = new TransparentPanel();
		superPanel.setLayout(new BorderLayout());
				
		Controller.prepareModules(true);
		
		try
		{
			Controller.setLanguage(Statics.defaultLanguages().get(System.getProperty("user.language")));
		}
		catch(Exception e)
		{
			
		}
		
		if(APPLET_THEME != null)
		{
			if(APPLET_THEME.equalsIgnoreCase("Optimized"))
			{
				
			}
			else
			{
				if(APPLET_THEME.equalsIgnoreCase("Nimbus"))
				{
					APPLET_THEME = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
				}
				else if(APPLET_THEME.equalsIgnoreCase("Metal"))
				{
					APPLET_THEME = "javax.swing.plaf.metal.MetalLookAndFeel";
				}
				else if(APPLET_THEME.equalsIgnoreCase("OS"))
				{
					APPLET_THEME = UIManager.getSystemLookAndFeelClassName();
				}
				
				if(! (APPLET_THEME.equalsIgnoreCase("Optimized")))
				{
					Controller.setOption("theme", APPLET_THEME);
				}
			}
		}
		
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
		
		aboutDialog = new SwingAboutDialog(superPanel);		
				
		menuBar = new JMenuBar();		
		
		menuFile = new JMenu(Controller.getString("File"));
		menuBar.add(menuFile);
				
		menuFileConvert = new JMenuItem(Controller.getString("Convert"));
		menuFileConvert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, KeyEvent.CTRL_MASK));
		menuFileConvert.addActionListener(this);
		menuFile.add(menuFileConvert);		
		
		menuTool = new JMenu(Controller.getString("Tool"));
		menuBar.add(menuTool);
		
		menuToolUndo = new JMenuItem(Controller.getString("Undo"));
		menuToolUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		menuToolUndo.addActionListener(this);
		menuTool.add(menuToolUndo);
		
		menuHelp = new JMenu(Controller.getString("Help"));
		menuBar.add(menuHelp);
		
		menuHelpAbout = new JMenuItem(Controller.getString("About"));
		menuHelpAbout.addActionListener(this);
		menuHelp.add(menuHelpAbout);
		
		superPanel.setLayout(new BorderLayout());
		mainPanel = new TransparentPanel();
		mainPanel.setLayout(new BorderLayout());
		superPanel.add(mainPanel, BorderLayout.CENTER);
				
		mainTab = new JTabbedPane();
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
			useLineNumbers = false;
		}
		
		if(useUndoManager) undoManager = new UndoManager();
		
		if(useLineNumbers)
		{
			textPanel = new JTextArea();	
			textScroll = new JScrollPane();
			
			lineNumberPanel = new JTextArea();
			lineNumberPanel.setEditable(false);
			
			Color textPanelColor = textPanel.getBackground();
			int maxColorValue = 0;
			double ratio = 1.2;
			if(maxColorValue < textPanelColor.getRed()) maxColorValue = textPanelColor.getRed();
			if(maxColorValue < textPanelColor.getGreen()) maxColorValue = textPanelColor.getGreen();
			if(maxColorValue < textPanelColor.getBlue()) maxColorValue = textPanelColor.getBlue();
			if(((double) maxColorValue) > (255.0 / 2.0))
			{
				lineNumberPanel.setBackground(new Color((int)(textPanelColor.getRed()/ratio)
						, (int)(textPanelColor.getGreen()/ratio), (int)(textPanelColor.getBlue()/ratio)));
			}
			else
			{
				lineNumberPanel.setBackground(new Color((int)(textPanelColor.getRed()*ratio)
						, (int)(textPanelColor.getGreen()*ratio), (int)(textPanelColor.getBlue()*ratio)));
			}
			
			textPanelColor = textPanel.getForeground();
			maxColorValue = 0;
			ratio = 1.2;
			if(maxColorValue < textPanelColor.getRed()) maxColorValue = textPanelColor.getRed();
			if(maxColorValue < textPanelColor.getGreen()) maxColorValue = textPanelColor.getGreen();
			if(maxColorValue < textPanelColor.getBlue()) maxColorValue = textPanelColor.getBlue();
			if(((double) maxColorValue) > (255.0 / 2.0))
			{
				lineNumberPanel.setForeground(new Color((int)(textPanelColor.getRed()/ratio)
						, (int)(textPanelColor.getGreen()/ratio), (int)(textPanelColor.getBlue()/ratio)));
			}
			else
			{
				lineNumberPanel.setForeground(new Color((int)(textPanelColor.getRed()*ratio)
						, (int)(textPanelColor.getGreen()*ratio), (int)(textPanelColor.getBlue()*ratio)));
			}
			
			textPanel.getDocument().addDocumentListener(new DocumentListener()
			{			
				private String getText()
				{
					int caretPos = textPanel.getDocument().getLength();
					Element el = textPanel.getDocument().getDefaultRootElement();
					
					StringBuffer text = new StringBuffer("1\n");
					for(int i=2; i<el.getElementIndex(caretPos) + 2; i++)
					{
						text = text.append(String.valueOf(i));
						text = text.append("\n");
					}
					return text.toString();
				}
				@Override
				public void removeUpdate(DocumentEvent e)
				{
					lineNumberPanel.setText(getText());				
				}
				
				@Override
				public void insertUpdate(DocumentEvent e)
				{
					lineNumberPanel.setText(getText());				
				}
				
				@Override
				public void changedUpdate(DocumentEvent e)
				{
					lineNumberPanel.setText(getText());				
				}
			});
			
			textScroll.getViewport().add(textPanel);
			textScroll.setRowHeaderView(lineNumberPanel);
			textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		else
		{
			textPanel = new JTextArea();	
			textScroll = new JScrollPane(textPanel);
		}
		
		converterPanel.add(textScroll, BorderLayout.CENTER);
		if(useUndoManager) textPanel.getDocument().addUndoableEditListener(undoManager);
		
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
		
		convertButton = new JButton(Controller.getString("Convert"));
		convertButton.addActionListener(this);	
		clearButton = new JButton(Controller.getString("Clear"));
		clearButton.addActionListener(this);	
		copyButton = new JButton(Controller.getString("Copy"));
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
			FontSetting.setFontRecursively(superPanel, FontSetting.usingFont);
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
			boolean noMoreNeeded = false;
			if(APPLET_THEME != null)
			{
				if(APPLET_THEME.equals("Optimized"))
				{
					try
					{						
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					}
					catch(Exception e)
					{
						try
						{
							UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
						}
						catch(Exception e1)
						{
							try
							{
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							}
							catch(Exception e2)
							{
								
							}
						}
					}
					noMoreNeeded = true;
				}
			}
			
			if(! noMoreNeeded)
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
			}
		});		
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == superPanel)
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
		saveLabel.setText(Controller.getString("Save from..."));
		saveButton.setText(Controller.getString("Save"));
		saveFileSelect.setText(Controller.getString("..."));
		loadLabel.setText(Controller.getString("Load from..."));
		loadButton.setText(Controller.getString("Load"));
		loadFileSelect.setText(Controller.getString("..."));
		clearButton.setText(Controller.getString("Clear"));
		copyButton.setText(Controller.getString("Copy"));
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
		
		superPanel.setVisible(true);
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
			int fileSelect = loadDialog.showOpenDialog(superPanel);
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
			int fileSelect = saveDialog.showSaveDialog(superPanel);
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
		else if(ob == shadeButton)
		{
			reverseHiding();
		}
		else if(ob == menuToolUndo)
		{
			if(useUndoManager) undoManager.undo();
			else
			{
				if(! (Controller.getContent().equals(getTextOnArea())))
				{
					Controller.setContent(getTextOnArea());
				}
				Controller.undo();
				setTextOnArea(Controller.getContent());
			}
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
		JOptionPane.showMessageDialog(superPanel, str);		
	}
	
	@Override
	public boolean requestYes(String message)
	{
		int selection = JOptionPane.showConfirmDialog(superPanel, message, message, JOptionPane.YES_NO_OPTION);
		if(selection == JOptionPane.YES_OPTION) return true;
		return false; 
	}
	@Override
	public String requestInput(String message)
	{
		return JOptionPane.showInputDialog(superPanel, message);
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
	protected void setVisible(boolean visible)
	{
		superPanel.setVisible(visible);		
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
	public Window getWindow()
	{
		return null;
	}

}