package hjow.methodconverter.swingconverter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.SettingManager;

/**
 * 
 * <p>This setting manager is consisted by Swing library.</p>
 * 
 * <p>Swing 라이브러리로 구현된 설정 매니저입니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingSettingManager extends SettingManager
{
	private JDialog dialog;
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JPanel[] optionPanels;
	private String[] themeLabels, themeValues;
	private JLabel themeLabel;
	private JComboBox themeSelector;
	private JButton closeButton;
	private JButton saveButton;
	private JLabel needRestartLabel;
	private JLabel localeLabel;
	private JComboBox localeSelector;
	private String[] localeLabels;
	private String[] localeValues;
	private JCheckBox betaChecker;
	private JCheckBox onlineChecker;
	private JSpinner securityLevelSelector;
	private JLabel securityLevelLabel;
	private JLabel autoSaveLabel;
	private JSpinner autoSaveLevelSelector;
	
	/**
	 * <p>Create setting manager.
	 * Also, after the object is created by this constructor, init() method will be called.</p>
	 * 
	 * <p>설정 매니저 객체를 생성합니다.</p>
	 * 
	 * @param frame : JFrame object, may be SwingConverter object 
	 */
	public SwingSettingManager(JFrame frame)
	{
		super(frame);
		init();
		setLanguage();
	}
	
	@Override
	protected void init()
	{
		if(dialog == null) dialog = new JDialog((JFrame) window);
		dialog.setSize(400, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.addWindowListener(this);
		
		mainPanel = new JPanel();
		dialog.getContentPane().add(mainPanel, BorderLayout.CENTER);		
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new JPanel();
		downPanel = new JPanel();
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		downPanel.setLayout(new FlowLayout());
		
		optionPanels = new JPanel[7];
		centerPanel.setLayout(new GridLayout(optionPanels.length, 1));
		for(int i=0; i<optionPanels.length; i++)
		{
			optionPanels[i] = new JPanel();
			optionPanels[i].setLayout(new FlowLayout());
			centerPanel.add(optionPanels[i]);
		}
		
		themeLabels = new String[4];
		themeValues = new String[themeLabels.length];		
		themeLabels[0] = System.getProperty("os.name") + " Classic";
		themeValues[0] = "original";
		themeLabels[1] = System.getProperty("os.name");
		themeValues[1] = UIManager.getSystemLookAndFeelClassName();
		themeLabels[2] = "Metal";
		themeValues[2] = "javax.swing.plaf.metal.MetalLookAndFeel";
		themeLabels[3] = "Nimbus";
		themeValues[3] = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		
		themeLabel = new JLabel("Theme");
		themeSelector = new JComboBox(themeLabels);
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
		
		
		localeLabel = new JLabel("Locale");
		localeSelector = new JComboBox(localeLabels);
		optionPanels[1].add(localeLabel);
		optionPanels[1].add(localeSelector);
		
		betaChecker = new JCheckBox("Beta");
		optionPanels[2].add(betaChecker);
		
		onlineChecker = new JCheckBox("Online");
		optionPanels[3].add(onlineChecker);
		
		securityLevelLabel = new JLabel("Security Level");
		securityLevelSelector = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		optionPanels[4].add(securityLevelLabel);
		optionPanels[4].add(securityLevelSelector);
		
		autoSaveLabel = new JLabel("Level of saving options and packages");
		autoSaveLevelSelector = new JSpinner(new SpinnerNumberModel(0, 0, 4, 1));
		optionPanels[5].add(autoSaveLabel);
		optionPanels[5].add(autoSaveLevelSelector);
		
		needRestartLabel = new JLabel();
		optionPanels[optionPanels.length - 1].add(needRestartLabel);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		
		downPanel.add(saveButton);
		downPanel.add(closeButton);
		
		try
		{
			FontSetting.prepareFont();
			FontSetting.setFontRecursively(dialog, FontSetting.usingFont);
		}
		catch(Exception e)
		{
			System.out.println(Controller.getString("Fail to load font") + " : " + e.getMessage());
			// e.printStackTrace();
		}
		
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
			Controller.setOption("useBeta", String.valueOf(betaChecker.isSelected()));
			Controller.setOption("useOnline", String.valueOf(onlineChecker.isSelected()));
			Controller.setOption("securityLevel", String.valueOf(securityLevelSelector.getValue()));
			Controller.setOption("autoSave", String.valueOf(autoSaveLevelSelector.getValue()));
			//System.out.println(StringToMethodConverter.getOption("theme"));
			Controller.saveOption();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(dialog, Controller.getString("Error") + " : " + e.getMessage());
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
		themeSelector.setSelectedIndex(selectValue);
		
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
		localeSelector.setSelectedIndex(selectValue);
		
		betaChecker.setSelected(Statics.useUntestedFunction());
		onlineChecker.setSelected(Statics.useOnlineContent());
		
		securityLevelSelector.setValue(new Integer(Controller.getSecurityLevel()));
		autoSaveLevelSelector.setValue(new Integer(Statics.autoSavingLevel()));
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
		saveButton.setText(Controller.getString("Save"));
		closeButton.setText(Controller.getString("Close"));
		needRestartLabel.setText(Controller.getString("Save and restart this program to apply changes."));
		localeLabel.setText(Controller.getString("Locale"));
		betaChecker.setText(Controller.getString("Use test functions"));
		onlineChecker.setText(Controller.getString("Use online contents"));
		securityLevelLabel.setText(Controller.getString("Security Level"));
		autoSaveLabel.setText(Controller.getString("Level of saving options and packages"));
	}
}
