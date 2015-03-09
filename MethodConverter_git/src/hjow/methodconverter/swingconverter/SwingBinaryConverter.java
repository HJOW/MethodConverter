package hjow.methodconverter.swingconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.GUIBinaryConverter;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * <p>This object helps to convert bytes as Swing API based GUI.</p>
 * 
 * <p>이 객체는 바이트로 된 데이터를 Swing 기반 GUI 환경에서 변환하는 것을 돕습니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class SwingBinaryConverter extends GUIBinaryConverter
{
	private Window dialog;
	private JPanel mainPanel;
	private JPanel encryptparamPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JFileChooser fileChooser;
	private JPanel loadSavePanel;
	private JPanel loadPanel;
	private JPanel savePanel;
	private JLabel loadLabel;
	private JTextField loadField;
	private JButton loadButton;
	private JLabel saveLabel;
	private JTextField saveField;
	private JButton saveButton;
	private JButton closeButton;
	private JButton convertButton;
	private JComboBox moduleSelector;
	private JComboBox algorithmSelector;
	private JComboBox keypadSelector;
	private JTextField keyField;
	private JLabel keyLabel;
	private JPanel upPanel;
	private CardLayout paramLayout;
	private JPanel paramPanel;
	private JTextField paramField;
	private JPanel paramGetPanel;
	private JButton addButton;
	
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 */
	public SwingBinaryConverter()
	{
		super();
		dialog = new JFrame();	
		((JFrame) dialog).setTitle(Controller.getString("Binary") + " " + Controller.getString("Converter"));
		SwingManager.setIcon((JFrame)(dialog), new ImageIcon(getClass().getClassLoader().getResource("ico.png")).getImage());
		
		initGUI();
	}
	
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param upper : JFrame object
	 */
	public SwingBinaryConverter(JFrame upper)
	{
		super();
		dialog = new JDialog(upper);	
		((JDialog) dialog).setTitle(Controller.getString("Binary") + " " + Controller.getString("Converter"));
		SwingManager.setIcon((JDialog)(dialog), new ImageIcon(getClass().getClassLoader().getResource("ico.png")).getImage());
		
		initGUI();
	}
	
	private void initGUI()	
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
		
		dialog.setSize(480, 200);
		dialog.setLayout(new BorderLayout());		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.addWindowListener(this);
		
		mainPanel = new JPanel();
		dialog.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new JPanel();
		centerPanel = new JPanel();
		downPanel = new JPanel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());
		
		fileChooser = new JFileChooser();
		
		loadSavePanel = new JPanel();
		centerPanel.add(loadSavePanel, BorderLayout.CENTER);
		loadSavePanel.setLayout(new GridLayout(2, 1));
		
		loadPanel = new JPanel();
		savePanel = new JPanel();
		
		loadSavePanel.add(loadPanel);
		loadSavePanel.add(savePanel);
		
		loadPanel.setLayout(new FlowLayout());
		savePanel.setLayout(new FlowLayout());
		
		loadLabel = new JLabel(Controller.getString("Load"));
		loadField = new JTextField(20);
		loadButton = new JButton(Controller.getString("..."));
		loadButton.addActionListener(this);
		loadPanel.add(loadLabel);
		loadPanel.add(loadField);
		loadPanel.add(loadButton);
		
		saveLabel = new JLabel(Controller.getString("Save"));
		saveField = new JTextField(20);
		saveButton = new JButton(Controller.getString("..."));
		saveButton.addActionListener(this);
		savePanel.add(saveLabel);
		savePanel.add(saveField);
		savePanel.add(saveButton);
		
		downPanel.setLayout(new FlowLayout());
		
		closeButton = new JButton(Controller.getString("Close"));
		closeButton.addActionListener(this);
		convertButton = new JButton(Controller.getString("Convert"));
		convertButton.addActionListener(this);
		
		downPanel.add(convertButton);
		downPanel.add(closeButton);
		
		
		upPanel.setLayout(new FlowLayout());
		
		moduleSelector = new JComboBox();
		moduleSelector.addItemListener(this);
		upPanel.add(moduleSelector);
		
		algorithmSelector = new JComboBox();
		upPanel.add(algorithmSelector);
		
		paramGetPanel = new JPanel();
		upPanel.add(paramGetPanel);
		
		paramLayout = new CardLayout();
		paramGetPanel.setLayout(paramLayout);
		
		encryptparamPanel = new JPanel();
		encryptparamPanel.setLayout(new FlowLayout());
		
		paramGetPanel.add(encryptparamPanel, "encrypt");
		
		paramPanel = new JPanel();
		paramPanel.setLayout(new FlowLayout());
		
		paramField = new JTextField(10);
		paramPanel.add(paramField);
		
		paramGetter = new SwingParameterGetter(this);
		
		addButton = new JButton(Controller.getString("Add"));
		addButton.addActionListener(this);
		paramPanel.add(addButton);
		
		paramGetPanel.add(paramPanel, "param");			
						
		keyLabel = new JLabel(Controller.getString("Key"));
		keyField = new JTextField(8);
		encryptparamPanel.add(keyLabel);
		encryptparamPanel.add(keyField);	
		
		String[] keypadMethods = new String[2];
		keypadMethods[0] = "zero";
		keypadMethods[1] = "special";
		keypadSelector = new JComboBox(keypadMethods);
		encryptparamPanel.add(keypadSelector);
		
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
		
		refreshModuleList();
	}	
	
	private void refreshModuleList()
	{
		init();
		Vector<String> moduleNames = new Vector<String>();
		for(int i=0; i<modules.size(); i++)
		{
			moduleNames.add(modules.get(i).getName(Controller.getSystemLocale()));
		}
		moduleSelector.removeAllItems();
		for(int i=0; i<moduleNames.size(); i++)
		{
			moduleSelector.addItem(moduleNames.get(i));
		}
		selectedModule = moduleSelector.getSelectedIndex();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == loadButton)
		{
			loadButtonPressed();
		}
		else if(ob == saveButton)
		{
			saveButtonPressed();
		}
		else if(ob == closeButton)
		{
			close();
		}
		else if(ob == convertButton)
		{
			convert();
		}
		else if(ob == addButton)
		{
			paramGetter.open();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object ob = e.getSource();
		if(ob == moduleSelector)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					if(moduleSelector.getSelectedIndex() >= 0)
					{
						selectedModule = moduleSelector.getSelectedIndex();
						List<String> options = modules.get(selectedModule).optionList();
						algorithmSelector.removeAllItems();
						for(int i=0; i<options.size(); i++)
						{
							algorithmSelector.addItem(options.get(i));
						}
						if(Statics.useUntestedFunction())
						{
							algorithmSelector.setEditable(true);
						}
						else algorithmSelector.setEditable(modules.get(selectedModule).isOptionEditable());
						if(modules.get(selectedModule).isEncryptingModule())
						{
							paramLayout.show(paramGetPanel, "encrypt");
						}
						else
						{
							paramLayout.show(paramGetPanel, "param");
						}
					}
				}
			});			
		}
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == dialog)
		{
			close();
		}
	}
	@Override
	public void loadButtonPressed()
	{
		int selection = fileChooser.showOpenDialog(dialog);
		if(selection == JFileChooser.APPROVE_OPTION)
		{
			setLoadFieldText(fileChooser.getSelectedFile().getAbsolutePath());
		}		
	}
	@Override
	public void saveButtonPressed()
	{
		int selection = fileChooser.showSaveDialog(dialog);
		if(selection == JFileChooser.APPROVE_OPTION)
		{
			setSaveFieldText(fileChooser.getSelectedFile().getAbsolutePath());
		}			
	}
	@Override
	public String getSaveFieldText()
	{
		return saveField.getText();
	}
	@Override
	public String getLoadFieldText()
	{
		return loadField.getText();
	}
	@Override
	public void setSaveFieldText(String text)
	{
		saveField.setText(text);
	}
	@Override
	public void setLoadFieldText(String text)
	{
		loadField.setText(text);
	}
	@Override
	public String getKeyFieldText()
	{
		return keyField.getText();
	}

	@Override
	public String getAlgorithmText()
	{
		return String.valueOf(algorithmSelector.getSelectedItem());
	}

	@Override
	public String getKeypadText()
	{
		return String.valueOf(keypadSelector.getSelectedItem());
	}

	@Override
	public void enableAll(boolean l)
	{
		loadButton.setEnabled(l);
		saveButton.setEnabled(l);
		loadField.setEditable(l);
		saveField.setEditable(l);
		convertButton.setEnabled(l);
		moduleSelector.setEnabled(l);
		algorithmSelector.setEnabled(l);
		keypadSelector.setEnabled(l);
		keyField.setEnabled(l);
	}
	@Override
	public void open()
	{
		dialog.setVisible(true);
	}
	@Override
	public void close()
	{
		dialog.setVisible(false);
		if(dialog instanceof JFrame)
		{
			try
			{
				Controller.closeAll();				
			}
			catch(Exception e)
			{
				
			}
			System.exit(0);
		}		
	}

	@Override
	public String getParameterText()
	{
		return paramField.getText();
	}

	@Override
	public void SetParameterText(String text)
	{
		paramField.setText(text);
	}

	@Override
	public String getParameterFieldText()
	{
		return getParameterText();
	}

	@Override
	public void setParameterFieldText(String text)
	{
		SetParameterText(text);
	}

	@Override
	public void alert(String text)
	{
		JOptionPane.showMessageDialog(dialog, text);
		
	}
	
	@Override
	public int getSelectedModule()
	{		
		return moduleSelector.getSelectedIndex();
	}

	@Override
	public Window getWindow()
	{
		return dialog;
	}	
}