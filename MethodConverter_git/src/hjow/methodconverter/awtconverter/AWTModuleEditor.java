package hjow.methodconverter.awtconverter;

import hjow.convert.module.UserDefinedModule;
import hjow.methodconverter.Controller;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.ModuleEditor;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;

public class AWTModuleEditor extends ModuleEditor
{
	private Panel mainPanel;
	private Panel centerPanel;
	private Panel downPanel;
	private Panel[] contentPanels;
	private Panel upPanel;
	private Label nameLabel;
	private TextField nameField;
	private TextField scriptTypeSelector;
	private Label optionLabel;
	private TextField optionField;
	private Label finalizeCallLabel;
	private TextField finalizeCallField;
	private Label authLabel;
	private TextField auth1Field;
	private TextField auth2Field;
	private TextArea centerArea;
	private Button saveButton;
	private Button loadButton;
	private Button closeButton;
	private Button newButton;
	private FileDialog fileChooser;
	private Checkbox optionEditableCheck;
	
	
	public AWTModuleEditor(Object upper)
	{
		super(upper);
	}
	
	@Override
	public void init(Object upper)
	{
		if(upper instanceof Frame) dialog = new Dialog((Frame) upper);
		else throw new NullPointerException("Insert Frame or JFrame object.");
		
		dialog.setSize(500, 400);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.addWindowListener(this);
		
		dialog.setLayout(new BorderLayout());
		mainPanel = new Panel();
		dialog.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new Panel();
		centerPanel = new Panel();
		downPanel = new Panel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		contentPanels = new Panel[4];
		upPanel.setLayout(new GridLayout(contentPanels.length, 1));
		for(int i=0; i<contentPanels.length; i++)
		{
			contentPanels[i] = new Panel();
			contentPanels[i].setLayout(new FlowLayout());
			upPanel.add(contentPanels[i]);
		}
		// name, scriptType, finalizeCall, auth, addauth, options
		
		nameLabel = new Label();
		nameField = new TextField(10);
		contentPanels[0].add(nameLabel);
		contentPanels[0].add(nameField);
		
		scriptTypeSelector = new TextField(10);
		scriptTypeSelector.setText("JavaScript");
		contentPanels[0].add(scriptTypeSelector);
		
		optionLabel = new Label();
		optionField = new TextField(20);
		optionEditableCheck = new Checkbox();
		contentPanels[1].add(optionLabel);
		contentPanels[1].add(optionField);
		contentPanels[1].add(optionEditableCheck);
		
		finalizeCallLabel = new Label();
		finalizeCallField = new TextField(20);
		contentPanels[2].add(finalizeCallLabel);
		contentPanels[2].add(finalizeCallField);
		
		authLabel = new Label();
		auth1Field = new TextField(5);
		auth2Field = new TextField(5);
		contentPanels[3].add(authLabel);
		contentPanels[3].add(auth1Field);
		contentPanels[3].add(auth2Field);
		
		centerPanel.setLayout(new BorderLayout());
		
		centerArea = new TextArea();
		centerPanel.add(centerArea, BorderLayout.CENTER);
		
		downPanel.setLayout(new FlowLayout());
		newButton = new Button();
		saveButton = new Button();
		loadButton = new Button();
		closeButton = new Button();
		newButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		closeButton.addActionListener(this);
		downPanel.add(newButton);
		downPanel.add(saveButton);
		downPanel.add(loadButton);
		downPanel.add(closeButton);
		
		setLanguage(Controller.getStringTable());
		reset();
	}
	@Override
	public void setLanguage(StringTable stringTable)
	{
		dialog.setTitle(stringTable.get("Module") + " " + stringTable.get("Editor"));
		newButton.setLabel(stringTable.get("New"));
		saveButton.setLabel(stringTable.get("Save"));
		loadButton.setLabel(stringTable.get("Load"));
		closeButton.setLabel(stringTable.get("Close"));
		
		nameLabel.setText(stringTable.get("Name"));
		optionLabel.setText(stringTable.get("Option"));
		finalizeCallLabel.setText(stringTable.get("Code executed at closing"));
		authLabel.setText(stringTable.get("Authority code (Optional)"));
		optionEditableCheck.setLabel(stringTable.get("User can input own option"));
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == newButton)
		{
			newone();
		}
		else if(ob == loadButton)
		{
			load();
		}
		else if(ob == saveButton)
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
		Object ob = e.getSource();
		if(ob == dialog)
		{
			close();
		}
		
	}	
	
	@Override
	public void save()
	{	
		try
		{
			fieldToObject();
			
			fileChooser = new FileDialog(dialog, "", FileDialog.SAVE);
			fileChooser.setVisible(true);
			
			String selectedDir = fileChooser.getDirectory();
			String selectedFile = fileChooser.getFile();
			
			if(selectedDir != null && selectedFile != null)
			{
				//String gets = target.toString();
				//Controller.saveFile(new File(selectedDir + selectedFile), gets, null);
				target.save(new File(selectedDir + selectedFile));
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	@Override
	public void load()
	{		
		try
		{			
			fileChooser = new FileDialog(dialog, "", FileDialog.SAVE);
			fileChooser.setVisible(true);
			
			String selectedDir = fileChooser.getDirectory();
			String selectedFile = fileChooser.getFile();
			
			if(selectedDir != null && selectedFile != null)
			{
				//String gets = Controller.readFile(new File(selectedDir + selectedFile), 20, null);
				//target = new UserDefinedModule(gets);
				target = new UserDefinedModule(new File(selectedDir + selectedFile));
				objectToField();
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}				
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
	}
	@Override
	public void reset()
	{
		if(target == null)
		{
			try
			{
				target = new UserDefinedModule();				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			objectToField();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void newone()
	{
		try
		{
			target = new UserDefinedModule();
			reset();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	@Override
	public String getCenterText()
	{		
		return centerArea.getText();
	}
	@Override
	public String getNameText()
	{		
		return nameField.getText();
	}
	@Override
	public String getOptionText()
	{		
		return optionField.getText();
	}
	@Override
	public String getFinalizeCallText()
	{		
		return finalizeCallField.getText();
	}
	@Override
	public String getAuth1Text()
	{		
		return auth1Field.getText();
	}
	@Override
	public String getAuth2Text()
	{		
		return auth2Field.getText();
	}
	@Override
	public String getScriptTypeText()
	{
		return scriptTypeSelector.getText();
	}

	@Override
	public void setCenterText(String value)
	{
		centerArea.setText(value);		
	}

	@Override
	public void setNameText(String name)
	{
		nameField.setText(name);
		
	}

	@Override
	public void setOptionText(String value)
	{
		optionField.setText(value);
		
	}

	@Override
	public void setFinalizeCallText(String value)
	{
		finalizeCallField.setText(value);
		
	}

	@Override
	public void setAuth1Text(String value)
	{
		auth1Field.setText(value);
		
	}

	@Override
	public void setAuth2Text(String value)
	{
		auth2Field.setText(value);
		
	}

	@Override
	public void setScriptTypeText(String value)
	{
		scriptTypeSelector.setText(value);		
	}

	@Override
	public boolean isOptionEditable()
	{
		return optionEditableCheck.getState();
	}

	@Override
	public void setOptionEditable(boolean editables)
	{
		optionEditableCheck.setState(editables);
	}
}
