package hjow.methodconverter.awtconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.Manager;
import hjow.methodconverter.ui.StatusBar;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;

/**
 * 
 * <p>GUI Manager create with AWT library.</p>
 * 
 * <p>AWT 라이브러리를 사용해 개발한 GUI 매니저입니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTManager extends Manager
{
	private static final long serialVersionUID = -3483133547553698477L;
	private Frame frame;
	private Panel mainPanel;
	private Panel centerPanel;
	private Panel upPanel;
	private Panel downPanel;
	private Label loadLabel;
	private Label saveLabel;
	private TextField loadField;
	private Button loadFileSelect;
	private Button loadButton;
	private TextField saveField;
	private Button saveFileSelect;
	private Button saveButton;
	private TextArea textPanel;
	private Panel controlPanel;
	private Button exitButton;
	private Panel thirdPanel;
	private Label classNameLabel;
	private TextField classNameField;
	private Label methodNameLabel;
	private TextField methodNameField;
	private Label variableNameLabel;
	private TextField variableNameField;
	private Choice syntaxChoice;
	private Button convertButton;
	private Panel bottomPanel;
	private Panel statusBar;
	private MenuBar menuBar;
	private Menu menuFile;
	private MenuItem menuFileExit;
	private MenuItem menuFileSave;
	private MenuItem menuFileLoad;
	private MenuItem menuFileConvert;
	private Menu menuTool;
	private MenuItem menuToolSetting;
	private Panel parameterPanel;
	private CardLayout parameterLayout;
	private Panel modeSelectPanel;
	private Choice modeSelector;
	private Panel parameterInputPanel;
	private TextField parameterField;
	private Menu menuHelp;
	private MenuItem menuHelpAbout;
	private MenuItem menuToolEditor;
	private Button clearButton;
	private AWTAlert alertDialog;
	private Button copyButton;
	private Panel leftControlPanel;
	private Panel rightControlPanel;
	private Panel centerControlPanel;
	private Button shadeButton;
	private Panel loadSavePanel;
	private MenuItem menuToolConsole;
	private AWTPrintDialog printerDialog;
	private Button parameterButton;
	private Checkbox alignCheck;
	
	/**
	 * <p>Constructor</p>
	 * 
	 * <p>생성자입니다.</p>
	 */
	public AWTManager()
	{
		super();		
	}	
	
	protected void init()
	{
		frame = new Frame();
		frame.setTitle(Controller.getString("Method Converter"));
		frame.setSize(550, 400);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth()/2 - frame.getWidth()/2), (int)(screenSize.getHeight()/2 - frame.getHeight()/2));
		frame.addWindowListener(this);
		
		aboutDialog = new AWTAboutDialog(frame);
		
		menuBar = new MenuBar();
		frame.setMenuBar(menuBar);
		
		menuFile = new Menu("File");
		menuBar.add(menuFile);
		
		menuFileSave = new MenuItem("Save");
		menuFileSave.addActionListener(this);
		menuFile.add(menuFileSave);		
		
		menuFileLoad = new MenuItem("Load");
		menuFileLoad.addActionListener(this);
		menuFile.add(menuFileLoad);	
		
		menuFile.addSeparator();
		
		menuFileConvert = new MenuItem("Convert");
		menuFileConvert.addActionListener(this);
		menuFile.add(menuFileConvert);	
		
		menuFile.addSeparator();
		
		menuFileExit = new MenuItem("Exit");
		menuFileExit.addActionListener(this);
		menuFile.add(menuFileExit);	
		
		menuTool = new Menu("Tool");
		menuBar.add(menuTool);
		
		menuToolSetting = new MenuItem("Preference");
		settingManager = new AWTSettingManager(frame);
		menuToolSetting.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				settingManager.open();				
			}			
		});
		menuTool.add(menuToolSetting);
		
		menuToolEditor = new MenuItem("Editor");
		moduleEditor = new AWTModuleEditor(frame);
		menuToolEditor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				moduleEditorOpenMenuSelected();			
			}			
		});
		menuTool.add(menuToolEditor);
		
		printerDialog = new AWTPrintDialog(frame);
		
		menuToolConsole = new MenuItem("Console");
		menuToolConsole.addActionListener(this);
		menuTool.add(menuToolConsole);
		
		menuHelp = new Menu("Help");
		menuBar.add(menuHelp);
		
		menuHelpAbout = new MenuItem("About");
		menuHelpAbout.addActionListener(this);
		menuHelp.add(menuHelpAbout);
		
		frame.setLayout(new BorderLayout());
		mainPanel = new Panel();
		frame.add(mainPanel, BorderLayout.CENTER);
				
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new Panel();
		mainPanel.add(centerPanel, BorderLayout.NORTH);
		
		textPanel = new TextArea();
		//textPanel.setEditable(false);
		mainPanel.add(textPanel, BorderLayout.CENTER);
		bottomPanel = new Panel();
		bottomPanel.setLayout(new BorderLayout());
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		controlPanel = new Panel();
		controlPanel.setLayout(new BorderLayout());
		
		bottomPanel.add(controlPanel, BorderLayout.CENTER);
		statusBar = new Panel();
		statusBar.setLayout(new BorderLayout());
		bottomPanel.add(statusBar, BorderLayout.SOUTH);
		
		
		Controller.setStatusBar(new AWTStatusBar());
		statusBar.add(Controller.getStatusBar(), BorderLayout.CENTER);	
		
		exitButton = new Button("Exit");
		exitButton.addActionListener(this);
		convertButton = new Button("Convert");
		convertButton.addActionListener(this);	
		clearButton = new Button("Clear");
		clearButton.addActionListener(this);
		copyButton = new Button("Copy");
		copyButton.addActionListener(this);
		
		syntaxChoice = new Choice();
		syntaxChoice.addItemListener(this);
		
		leftControlPanel = new Panel();
		centerControlPanel = new Panel();
		rightControlPanel = new Panel();
		leftControlPanel.setLayout(new FlowLayout());
		centerControlPanel.setLayout(new FlowLayout());
		rightControlPanel.setLayout(new FlowLayout());
		
		controlPanel.add(leftControlPanel, BorderLayout.WEST);
		controlPanel.add(centerControlPanel, BorderLayout.CENTER);
		controlPanel.add(rightControlPanel, BorderLayout.EAST);
		
		centerControlPanel.add(syntaxChoice);
		centerControlPanel.add(convertButton);
		leftControlPanel.add(copyButton);
		leftControlPanel.add(clearButton);
		rightControlPanel.add(exitButton);				
		
		centerPanel.setLayout(new BorderLayout());
		
		modeSelectPanel = new Panel();
		modeSelectPanel.setLayout(new FlowLayout());
		modeSelector = new Choice();
		modeSelector.addItemListener(this);
		modeSelectPanel.add(modeSelector);
		shadeButton = new Button("▲");
		shadeButton.addActionListener(this);
		modeSelectPanel.add(shadeButton);
		
		centerPanel.add(modeSelectPanel, BorderLayout.NORTH);		
		
		loadSavePanel = new Panel();
		loadSavePanel.setLayout(new GridLayout(2, 1));
		
		upPanel = new Panel();
		downPanel = new Panel();
		thirdPanel = new Panel();
		
		loadSavePanel.add(upPanel);
		loadSavePanel.add(downPanel);
		centerPanel.add(loadSavePanel, BorderLayout.CENTER);	
		
		parameterPanel = new Panel();
		parameterLayout = new CardLayout();
		parameterPanel.setLayout(parameterLayout);
		parameterPanel.add(thirdPanel, "return_method");
		centerPanel.add(parameterPanel, BorderLayout.SOUTH);
		
		parameterInputPanel = new Panel();
		parameterInputPanel.setLayout(new BorderLayout());
		parameterField = new TextField();
		parameterInputPanel.add(parameterField, BorderLayout.CENTER);
		parameterPanel.add(parameterField, "parameter");
		parameterGetter = new AWTParameterGetter(this);
		parameterButton = new Button(Controller.getString("Parameter Agent"));
		parameterButton.addActionListener(this);
		parameterInputPanel.add(parameterButton, BorderLayout.CENTER);
		
		upPanel.setLayout(new FlowLayout());
		downPanel.setLayout(new FlowLayout());
		thirdPanel.setLayout(new FlowLayout());
		
		classNameLabel = new Label("Class");
		classNameField = new TextField(10);
		classNameField.setText("Converted");
		thirdPanel.add(classNameLabel);
		thirdPanel.add(classNameField);
		
		methodNameLabel = new Label("Method");
		methodNameField = new TextField(10);
		methodNameField.setText("text");
		thirdPanel.add(methodNameLabel);
		thirdPanel.add(methodNameField);
		
		variableNameLabel = new Label("Variable");
		variableNameField = new TextField(10);
		variableNameField.setText("r");
		thirdPanel.add(variableNameLabel);
		thirdPanel.add(variableNameField);
		
		alignCheck = new Checkbox("Align");
		thirdPanel.add(alignCheck);
		
		loadLabel = new Label("Load from...");
		loadField = new TextField(30);
		loadFileSelect = new Button("...");
		loadButton = new Button("Load");
		loadFileSelect.addActionListener(this);
		loadButton.addActionListener(this);
		upPanel.add(loadLabel);
		upPanel.add(loadField);
		upPanel.add(loadFileSelect);
		upPanel.add(loadButton);
		
		saveLabel = new Label("Save from...");
		saveField = new TextField(30);
		saveFileSelect = new Button("...");
		saveButton = new Button("Save");
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
			status = new AWTGageBar();
			statusBar.add(status.toComponent(), BorderLayout.EAST);
		}
		catch(Exception e)
		{
			
		}
		
		Controller.resetSyntaxList();
		syntaxChoiceRefresh();
		refreshModule();
		
		reverseHiding();
		
		System.out.println("Method Converter initialize complete.");
		Controller.setStatusText(Controller.getFirstMessage());
		
		super.init();
	}
	/**
	 * <p>Return Frame or JFrame object.</p>
	 * 
	 * <p>프레임 객체를 반환합니다.</p>
	 * 
	 * @return JFrame object
	 */
	public Frame getFrame()
	{
		return frame;
	}
	/**
	 * <p>Refresh programming-language syntax list</p>
	 * 
	 * <p>프로그래밍 언어 문법 선택 리스트를 새로고침합니다.</p>
	 */
	@Override
	protected void syntaxChoiceRefresh()
	{
		syntaxChoice.removeAll();
		List<String> keys = Controller.getAvailableOptions();
		for(String s : keys)
		{
			syntaxChoice.add(s);
		}
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
		saveButton.setLabel(Controller.getString("Save"));
		saveFileSelect.setLabel(Controller.getString("..."));
		loadLabel.setText(Controller.getString("Load from..."));
		loadButton.setLabel(Controller.getString("Load"));
		loadFileSelect.setLabel(Controller.getString("..."));
		copyButton.setLabel(Controller.getString("Copy"));
		clearButton.setLabel(Controller.getString("Clear"));
		exitButton.setLabel(Controller.getString("Exit"));
		classNameLabel.setText(Controller.getString("Class"));
		methodNameLabel.setText(Controller.getString("Method"));
		variableNameLabel.setText(Controller.getString("Variable"));
		alignCheck.setLabel(Controller.getString("Align"));
		convertButton.setLabel(Controller.getString("Convert"));
		menuFile.setLabel(Controller.getString("File"));
		menuFileConvert.setLabel(Controller.getString("Convert"));		
		menuFileLoad.setLabel(Controller.getString("Load"));
		menuFileSave.setLabel(Controller.getString("Save"));
		menuFileExit.setLabel(Controller.getString("Exit"));
		menuTool.setLabel(Controller.getString("Tool"));
		menuToolSetting.setLabel(Controller.getString("Preference"));
		menuToolEditor.setLabel(Controller.getString("Module") + " " + Controller.getString("Editor"));
		menuToolConsole.setLabel(Controller.getString("Console"));
		menuHelp.setLabel(Controller.getString("Help"));
		menuHelpAbout.setLabel(Controller.getString("About"));
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
		syntaxChoice.setEnabled(l);
		modeSelector.setEnabled(l);
		exitButton.setEnabled(l);
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
	}
	
	
	/**
	 * 
	 * <p>Open file-open dialog to take file path.
	 * If loadAfterThis is true, load contents from selected file.
	 * If loadAfterThis is false, just open file-open dialog.</p>
	 * 
	 * <p>불러오기 창을 열어 파일을 선택합니다.
	 * loadAfterThis 가 true 이면, 파일이 선택된 경우 불러오기 작업까지 수행합니다.
	 * false 이면, 파일을 선택만 합니다.</p>
	 * 
	 * @param loadAfterThis : Decide to load after the file is selected
	 */
	@Override
	public void loadFileSelect(boolean loadAfterThis)
	{
		try
		{
			FileDialog loadDialog = new FileDialog(frame, "Load...", FileDialog.LOAD);
			loadDialog.setDirectory(System.getProperty("user.home"));
			loadDialog.setVisible(true);
			String selectedFile = loadDialog.getFile();			
			if(selectedFile != null)
			{
				selectedFile = loadDialog.getDirectory() + selectedFile;
				Controller.setLoadFile(new File(selectedFile));
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
			FileDialog loadDialog = new FileDialog(frame, "Save...", FileDialog.SAVE);
			loadDialog.setDirectory(System.getProperty("user.home"));
			loadDialog.setVisible(true);
			String selectedFile = loadDialog.getFile();		
			//System.out.println(selectedFile);
			if(selectedFile != null)
			{
				selectedFile = loadDialog.getDirectory() + selectedFile;
				Controller.setSaveFile(new File(selectedFile));
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
		else if(ob == clearButton)
		{
			clearArea();
		}
		else if(ob == copyButton)
		{
			setClipboard(getTextOnArea());
			alert(Controller.getString("Text is copied to the clipboard."));
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
		else if(ob == menuToolConsole)
		{
			printerDialog.open();
		}
		else if(ob == parameterButton)
		{
			parameterGetter.open();
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
		return syntaxChoice.getSelectedItem();
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
	public void itemStateChanged(ItemEvent e)
	{
		Object ob = e.getSource();
		if(ob == modeSelector)
		{
			selectModule(modeSelector.getSelectedIndex());
		}
	}
	@Override
	public void refreshModule()
	{
		modeSelector.removeAll();
		for(String s : Controller.getModuleList())
		{
			modeSelector.addItem(s);
		}
		modeSelector.select(0);
	}
	@Override
	public void selectModule(int index)
	{
		Controller.selectMode(index);
		if(index == 0) 
		{
			parameterLayout.show(parameterPanel, "return_method");
			nowShowingParamPanelName = "return_method";
			//syntaxChoice.setEnabled(true);
		}
		else
		{
			parameterLayout.show(parameterPanel, "parameter");
			nowShowingParamPanelName = "parameter";
			//syntaxChoice.setEnabled(false);
		}
		syntaxChoiceRefresh();
	}

	@Override
	public String getParameterFieldText()
	{
		return parameterField.getText();
	}
	@Override
	public int getSyntaxSelectedIndex()
	{
		return syntaxChoice.getSelectedIndex();
	}

	@Override
	public String getSyntaxSelectedItem()
	{
		return syntaxChoice.getSelectedItem();
	}

	@Override
	public void alert(String str)
	{
		if(alertDialog == null) alertDialog = new AWTAlert(frame);
		alertDialog.alert(str);
	}

	@Override
	public void reverseHiding()
	{
		if(loadSavePanel.isVisible())
		{
			loadSavePanel.setVisible(false);
			shadeButton.setLabel("▼");
		}
		else
		{
			loadSavePanel.setVisible(true);
			shadeButton.setLabel("▲");
		}
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
	public boolean requestYes(String message)
	{
		alertDialog.requestSelection(message);
		return alertDialog.selection;
	}

	@Override
	public String requestInput(String message)
	{
		alertDialog.requestInput(message);
		return alertDialog.inputs;
	}

	@Override
	protected boolean takeAlignCheck()
	{
		return alignCheck.getState();
	}
}
/**
 * <p>Status bar created with AWT library</p>
 * 
 * <p>AWT 기반의 상태 바 입니다.</p>
 * 
 * @author HJOW
 *
 */
class AWTStatusBar extends TextField implements StatusBar
{
	private static final long serialVersionUID = -2042349145818520934L;

	public AWTStatusBar()
	{
		super(10);
		setEditable(false);
	}
	@Override
	public Component toComponent()
	{
		return this;
	}
	@Override
	public void clear()
	{
		setText("");
	}
}

/**
 * <p>AWT based Printer dialog.</p>
 * 
 * <p>AWT 기반 Printer 대화 상자입니다.</p>
 * 
 * @author HJOW
 *
 */
class AWTPrintDialog extends Dialog implements WindowListener
{
	private static final long serialVersionUID = -50457014443187267L;

	/**
	 * <p>Create components.</p>
	 * 
	 * <p>컴포넌트들을 생성합니다.</p>
	 * 
	 * @param frame : Frame object
	 */
	public AWTPrintDialog(Frame frame)
	{
		super(frame);
		this.setSize(400, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.getWidth()/2 - this.getWidth()/2), (int)(screenSize.getHeight()/2 - this.getHeight()/2));
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		this.add(Controller.getPrinterComponent(), BorderLayout.CENTER);
	}
	public void open()
	{
		this.setVisible(true);
		Controller.focusOnPrinter();
	}
	@Override
	public void windowActivated(WindowEvent arg0)
	{
		
	}
	@Override
	public void windowClosed(WindowEvent arg0)
	{
		
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == this) setVisible(false);		
	}
	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
	}
	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
	}
	@Override
	public void windowIconified(WindowEvent e)
	{
		
	}
	@Override
	public void windowOpened(WindowEvent e)
	{
		
	}
}
