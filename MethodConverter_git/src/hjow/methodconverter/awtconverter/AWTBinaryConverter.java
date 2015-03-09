package hjow.methodconverter.awtconverter;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;


import hjow.methodconverter.Controller;
import hjow.methodconverter.swingconverter.SwingParameterGetter;
import hjow.methodconverter.ui.GUIBinaryConverter;

public class AWTBinaryConverter extends GUIBinaryConverter
{
	private Window dialog;
	private Panel mainPanel;
	private Panel encryptparamPanel;
	private Panel centerPanel;
	private Panel downPanel;
	private FileDialog fileLoader, fileSaver;
	private Panel loadSavePanel;
	private Panel loadPanel;
	private Panel savePanel;
	private Label loadLabel;
	private TextField loadField;
	private Button loadButton;
	private Label saveLabel;
	private TextField saveField;
	private Button saveButton;
	private Button closeButton;
	private Button convertButton;
	//private JComboBox moduleSelector;
	//private JComboBox algorithmSelector;
	//private JComboBox keypadSelector;
	private Choice moduleSelector, algorithmSelector, keypadSelector;
	private TextField keyField;
	private Label keyLabel;
	private Panel upPanel;
	private CardLayout paramLayout;
	private Panel paramPanel;
	private TextField paramField;
	private Panel paramGetPanel;
	private Button addButton;
	private AWTAlert alerter;
	
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 */
	public AWTBinaryConverter()
	{
		super();
		dialog = new Frame();
		alerter = new AWTAlert((Frame) dialog);
		fileLoader = new FileDialog((Frame) dialog, Controller.getString("..."), FileDialog.LOAD);
		fileSaver = new FileDialog((Frame) dialog, Controller.getString("..."), FileDialog.SAVE);
				
		initGUI();
	}
	
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param upper : JFrame object
	 */
	public AWTBinaryConverter(Frame upper)
	{
		super();
		dialog = new Dialog(upper);
		alerter = new AWTAlert((Dialog) dialog);
		fileLoader = new FileDialog((Dialog) dialog, Controller.getString("..."), FileDialog.LOAD);
		fileSaver = new FileDialog((Dialog) dialog, Controller.getString("..."), FileDialog.SAVE);
				
		initGUI();
	}
	
	private void initGUI()	
	{		
		dialog.setSize(450, 200);
		dialog.setLayout(new BorderLayout());		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.addWindowListener(this);
		
		mainPanel = new Panel();
		dialog.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new Panel();
		centerPanel = new Panel();
		downPanel = new Panel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());		
		
		loadSavePanel = new Panel();
		centerPanel.add(loadSavePanel, BorderLayout.CENTER);
		loadSavePanel.setLayout(new GridLayout(2, 1));
		
		loadPanel = new Panel();
		savePanel = new Panel();
		
		loadSavePanel.add(loadPanel);
		loadSavePanel.add(savePanel);
		
		loadPanel.setLayout(new FlowLayout());
		savePanel.setLayout(new FlowLayout());
		
		loadLabel = new Label(Controller.getString("Load"));
		loadField = new TextField(20);
		loadButton = new Button(Controller.getString("..."));
		loadButton.addActionListener(this);
		loadPanel.add(loadLabel);
		loadPanel.add(loadField);
		loadPanel.add(loadButton);
		
		saveLabel = new Label(Controller.getString("Save"));
		saveField = new TextField(20);
		saveButton = new Button(Controller.getString("..."));
		saveButton.addActionListener(this);
		savePanel.add(saveLabel);
		savePanel.add(saveField);
		savePanel.add(saveButton);
		
		downPanel.setLayout(new FlowLayout());
		
		closeButton = new Button(Controller.getString("Close"));
		closeButton.addActionListener(this);
		convertButton = new Button(Controller.getString("Convert"));
		convertButton.addActionListener(this);
		
		downPanel.add(convertButton);
		downPanel.add(closeButton);
		
		
		upPanel.setLayout(new FlowLayout());
		
		moduleSelector = new Choice();
		moduleSelector.addItemListener(this);
		upPanel.add(moduleSelector);
		
		algorithmSelector = new Choice();
		upPanel.add(algorithmSelector);
		
		paramGetPanel = new Panel();
		upPanel.add(paramGetPanel);
		
		paramLayout = new CardLayout();
		paramGetPanel.setLayout(paramLayout);
		
		encryptparamPanel = new Panel();
		encryptparamPanel.setLayout(new FlowLayout());
		
		paramGetPanel.add(encryptparamPanel, "encrypt");
		
		paramPanel = new Panel();
		paramPanel.setLayout(new FlowLayout());
		
		paramField = new TextField(10);
		paramPanel.add(paramField);
		
		paramGetter = new SwingParameterGetter(this);
		
		addButton = new Button(Controller.getString("Add"));
		addButton.addActionListener(this);
		paramPanel.add(addButton);
		
		paramGetPanel.add(paramPanel, "param");			
						
		keyLabel = new Label(Controller.getString("Key"));
		keyField = new TextField(8);
		encryptparamPanel.add(keyLabel);
		encryptparamPanel.add(keyField);	
		
		String[] keypadMethods = new String[2];
		keypadMethods[0] = "zero";
		keypadMethods[1] = "special";
		keypadSelector = new Choice();
		for(int i=0; i<keypadMethods.length; i++)
		{
			keypadSelector.addItem(keypadMethods[i]);
		}
		encryptparamPanel.add(keypadSelector);
		
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
		moduleSelector.removeAll();
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
			if(moduleSelector.getSelectedIndex() >= 0)
			{
				selectedModule = moduleSelector.getSelectedIndex();
				List<String> options = modules.get(selectedModule).optionList();
				algorithmSelector.removeAll();
				for(int i=0; i<options.size(); i++)
				{
					algorithmSelector.addItem(options.get(i));
				}
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
		fileLoader.setVisible(true);
		String fileName = fileLoader.getFile();
		if(fileName != null)
		{
			setLoadFieldText(fileLoader.getDirectory() + fileName);			
		}
	}
	@Override
	public void saveButtonPressed()
	{		
		fileSaver.setVisible(true);
		String fileName = fileSaver.getFile();
		if(fileName != null)
		{
			setSaveFieldText(fileSaver.getDirectory() + fileName);			
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
		if(dialog instanceof Frame)
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
		alerter.setMessage(text);
		alerter.setVisible(true);
	}

	@Override
	public Window getWindow()
	{
		return dialog;
	}
}
