package hjow.methodconverter.awtconverter;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.SettingManager;

/**
 * 
 * <p>This setting manager is consisted by AWT library.</p>
 * 
 * <p>AWT 라이브러리로 구현된 설정 매니저입니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTSettingManager extends SettingManager
{
	private Dialog dialog;
	private Panel mainPanel;
	private Panel centerPanel;
	private Panel downPanel;
	private Panel[] optionPanels;
	private String[] themeLabels, themeValues;
	private Label themeLabel;
	private Choice themeSelector;
	private Button closeButton;
	private Button saveButton;
	private Label needRestartLabel;
	private Label localeLabel;
	private Choice localeSelector;
	private String[] localeLabels;
	private String[] localeValues;
	private Checkbox betaChecker;
	private Checkbox onlineChecker;
	private Label securityLevelLabel;
	private TextField securityLevelSelector;
	private Label autoSaveLabel;
	private TextField autoSaveLevelSelector;
	
	/**
	 * <p>Create setting manager.
	 * Also, after the object is created by this constructor, init() method will be called.</p>
	 * 
	 * <p>설정 매니저 객체를 생성합니다.</p>
	 * 
	 * @param frame : Frame object, may be AWTConverter object 
	 */
	public AWTSettingManager(Frame frame)
	{
		super(frame);
		init();
		setLanguage();
	}
	
	@Override
	protected void init()
	{
		if(dialog == null) dialog = new Dialog((Frame) window);
		dialog.setSize(400, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.setLayout(new BorderLayout());
		dialog.addWindowListener(this);
		
		mainPanel = new Panel();
		dialog.add(mainPanel, BorderLayout.CENTER);		
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new Panel();
		downPanel = new Panel();
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		downPanel.setLayout(new FlowLayout());
		
		optionPanels = new Panel[7];
		centerPanel.setLayout(new GridLayout(optionPanels.length, 1));
		for(int i=0; i<optionPanels.length; i++)
		{
			optionPanels[i] = new Panel();
			optionPanels[i].setLayout(new FlowLayout());
			centerPanel.add(optionPanels[i]);
		}
		
		themeLabels = new String[3];
		themeValues = new String[themeLabels.length];		
		themeLabels[0] = System.getProperty("os.name") + " Classic";
		themeValues[0] = "original";
		themeLabels[1] = "Metal";
		themeValues[1] = "javax.swing.plaf.metal.MetalLookAndFeel";
		themeLabels[2] = "Nimbus";
		themeValues[2] = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		
		themeLabel = new Label("Theme");
		themeSelector = new Choice();
		for(int i=0; i<themeLabels.length; i++)
		{
			themeSelector.addItem(themeLabels[i]);
		}
		optionPanels[0].add(themeLabel);
		optionPanels[0].add(themeSelector);
		
		
		List<String> stringtableList = new Vector<String>();
		stringtableList.addAll(Controller.getStringTableKeys());
		
		localeLabels = new String[stringtableList.size()];
		localeValues = new String[localeLabels.length];
		
		for(int i=0; i<stringtableList.size(); i++)
		{
			localeLabels[i] = Controller.getStringTableFormalName(stringtableList.get(i));
			localeValues[i] = stringtableList.get(i);
		}
		
		localeLabel = new Label("Locale");
		localeSelector = new Choice();
		for(int i=0; i<localeLabels.length; i++)
		{
			localeSelector.addItem(localeLabels[i]);
		}
		optionPanels[1].add(localeLabel);
		optionPanels[1].add(localeSelector);
		
		betaChecker = new Checkbox("Beta");
		optionPanels[2].add(betaChecker);
		
		onlineChecker = new Checkbox("Online");
		optionPanels[3].add(onlineChecker);
		
		securityLevelLabel = new Label("Security Level");
		securityLevelSelector = new TextField(1);
		optionPanels[4].add(securityLevelLabel);
		optionPanels[4].add(securityLevelSelector);
		
		autoSaveLabel = new Label("Level of saving options and packages");
		autoSaveLevelSelector = new TextField(1);
		optionPanels[5].add(autoSaveLabel);
		optionPanels[5].add(autoSaveLevelSelector);
		
		needRestartLabel = new Label();
		optionPanels[optionPanels.length - 1].add(needRestartLabel);
		
		closeButton = new Button("Close");
		closeButton.addActionListener(this);
		saveButton = new Button("Save");
		saveButton.addActionListener(this);
		
		downPanel.add(saveButton);
		downPanel.add(closeButton);
		
		reset();
	}
	@Override
	public void open()
	{
		reset();
		dialog.setVisible(true);
	}
	@Override
	public void close()
	{
		dialog.setVisible(false);
	}
	@Override
	public void save()
	{
		try
		{
			Controller.setOption("theme", themeValues[themeSelector.getSelectedIndex()]);
			Controller.setOption("locale", localeValues[localeSelector.getSelectedIndex()]);
			Controller.setOption("useBeta", String.valueOf(betaChecker.getState()));
			Controller.setOption("useOnline", String.valueOf(onlineChecker.getState()));
			Controller.setOption("securityLevel", String.valueOf(securityLevelSelector.getText()));
			Controller.setOption("autoSave", String.valueOf(autoSaveLevelSelector.getText()));
			//System.out.println(StringToMethodConverter.getOption("theme"));
			Controller.saveOption();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void reset()
	{
		Controller.loadOption();
		
		int selectValue = -1;
		String nowOptionValue = Controller.getOption("theme");
		if(nowOptionValue != null)
		{
			for(int i=0; i<themeLabels.length; i++)
			{
				if(nowOptionValue.equalsIgnoreCase(themeLabels[i]))
				{
					selectValue = i;
					break;
				}
			}
			if(selectValue < 0)
			{
				for(int i=0; i<themeValues.length; i++)
				{
					if(nowOptionValue.equalsIgnoreCase(themeValues[i]))
					{
						selectValue = i;
						break;
					}
				}
			}
		}
		if(selectValue < 0) selectValue = 1;
		themeSelector.select(selectValue);
		
		selectValue = -1;
		nowOptionValue = Controller.getOption("locale");
		try
		{
			if(nowOptionValue == null) nowOptionValue = System.getProperty("user.language");
		}
		catch (Exception e)
		{
			nowOptionValue = null;
		}
		if(nowOptionValue != null)
		{
			for(int i=0; i<localeLabels.length; i++)
			{
				if(nowOptionValue.equalsIgnoreCase(localeLabels[i]))
				{
					selectValue = i;
					break;
				}
			}
			if(selectValue < 0)
			{
				for(int i=0; i<localeValues.length; i++)
				{
					if(nowOptionValue.equalsIgnoreCase(localeValues[i]))
					{
						selectValue = i;
						break;
					}
				}
			}
		}
		if(selectValue < 0) selectValue = 1;
		localeSelector.select(selectValue);
		
		betaChecker.setState(Statics.useUntestedFunction());
		onlineChecker.setState(Statics.useOnlineContent());	
		
		securityLevelSelector.setText(String.valueOf(Controller.getSecurityLevel()));
		autoSaveLevelSelector.setText(String.valueOf(Statics.autoSavingLevel()));
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == saveButton)
		{
			save();
		}
		else if(ob == closeButton)
		{
			close();
		}
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		close();
	}
	@Override
	public void setLanguage()
	{
		dialog.setTitle(Controller.getString("Preference"));
		themeLabel.setText(Controller.getString("Theme"));
		saveButton.setLabel(Controller.getString("Save"));
		closeButton.setLabel(Controller.getString("Close"));
		needRestartLabel.setText(Controller.getString("Save and restart this program to apply changes."));
		localeLabel.setText(Controller.getString("Locale"));
		betaChecker.setLabel(Controller.getString("Use test functions"));
		onlineChecker.setLabel(Controller.getString("Use online contents"));
		autoSaveLabel.setText(Controller.getString("Level of saving options and packages"));
	}
}
