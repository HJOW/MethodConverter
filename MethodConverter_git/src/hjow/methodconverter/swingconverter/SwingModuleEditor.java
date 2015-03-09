package hjow.methodconverter.swingconverter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Element;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hjow.convert.module.UserDefinedModule;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.ModuleEditor;

/**
 * <p>Module-Editor object based on Swing</p>
 * 
 * <p>Swing 기반 모듈 편집기 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingModuleEditor extends ModuleEditor
{
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JPanel[] contentPanels;
	private JPanel upPanel;
	private JLabel nameLabel;
	private JTextField nameField;
	private JComboBox scriptTypeSelector;
	private JLabel optionLabel;
	private JTextField optionField;
	private JLabel finalizeCallLabel;
	private JTextField finalizeCallField;
	private JLabel authLabel;
	private JTextField auth1Field;
	private JTextField auth2Field;
	private JTextArea centerArea;
	private JScrollPane centerScroll;
	private JButton saveButton;
	private JButton loadButton;
	private JButton closeButton;
	private JButton newButton;
	private JFileChooser fileChooser;
	private FileFilter fileFilter;
	private FileFilter fileFilter2;
	private FileFilter fileFilter3;
	private FileFilter fileFilter4;
	private FileFilter fileFilter5;
	private JCheckBox optionEditableCheck;
	private JTextArea lineNumberPanel;
	
	
	public SwingModuleEditor(Object upper)
	{
		super(upper);
	}
	
	@Override
	public void init(Object upper)
	{
		if(upper instanceof JFrame) dialog = new JDialog((JFrame) upper);
		else if(upper instanceof Frame) dialog = new JDialog((Frame) upper);
		else throw new NullPointerException("Insert Frame or JFrame object.");
		
		dialog.setSize(500, 400);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.addWindowListener(this);
		
		dialog.setLayout(new BorderLayout());
		mainPanel = new JPanel();
		dialog.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new JPanel();
		centerPanel = new JPanel();
		downPanel = new JPanel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		contentPanels = new JPanel[4];
		upPanel.setLayout(new GridLayout(contentPanels.length, 1));
		for(int i=0; i<contentPanels.length; i++)
		{
			contentPanels[i] = new JPanel();
			contentPanels[i].setLayout(new FlowLayout());
			upPanel.add(contentPanels[i]);
		}
		// name, scriptType, finalizeCall, auth, addauth, options
		
		nameLabel = new JLabel();
		nameField = new JTextField(10);
		contentPanels[0].add(nameLabel);
		contentPanels[0].add(nameField);
		
		scriptTypeSelector = new JComboBox();
		scriptTypeSelector.addItem("JavaScript");
		scriptTypeSelector.setEditable(true);
		contentPanels[0].add(scriptTypeSelector);
		
		optionLabel = new JLabel();
		optionField = new JTextField(20);
		optionEditableCheck = new JCheckBox();
		contentPanels[1].add(optionLabel);
		contentPanels[1].add(optionField);
		contentPanels[1].add(optionEditableCheck);
		
		finalizeCallLabel = new JLabel();
		finalizeCallField = new JTextField(20);
		contentPanels[2].add(finalizeCallLabel);
		contentPanels[2].add(finalizeCallField);
		
		authLabel = new JLabel();
		auth1Field = new JTextField(5);
		auth2Field = new JTextField(5);
		contentPanels[3].add(authLabel);
		contentPanels[3].add(auth1Field);
		contentPanels[3].add(auth2Field);
		
		centerPanel.setLayout(new BorderLayout());
		
		centerArea = new JTextArea();
		centerScroll = new JScrollPane(centerArea);
		
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
			centerArea = new JTextArea();	
			centerScroll = new JScrollPane();
			
			lineNumberPanel = new JTextArea();
			lineNumberPanel.setEditable(false);
			
			Color textPanelColor = centerArea.getBackground();
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
			
			textPanelColor = centerArea.getForeground();
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
			
			centerArea.getDocument().addDocumentListener(new DocumentListener()
			{			
				private String getText()
				{
					int caretPos = centerArea.getDocument().getLength();
					Element el = centerArea.getDocument().getDefaultRootElement();
					
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
			
			centerScroll.getViewport().add(centerArea);
			centerScroll.setRowHeaderView(lineNumberPanel);
			centerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		else
		{
			centerArea = new JTextArea();	
			centerScroll = new JScrollPane(centerArea);
		}
		
		centerPanel.add(centerScroll, BorderLayout.CENTER);
		
		downPanel.setLayout(new FlowLayout());
		newButton = new JButton();
		saveButton = new JButton();
		loadButton = new JButton();
		closeButton = new JButton();
		newButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		closeButton.addActionListener(this);
		downPanel.add(newButton);
		downPanel.add(saveButton);
		downPanel.add(loadButton);
		downPanel.add(closeButton);
		
		setLanguage(Controller.getStringTable());
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
	public void setLanguage(StringTable stringTable)
	{
		dialog.setTitle(stringTable.get("Module") + " " + stringTable.get("Editor"));
		newButton.setText(stringTable.get("New"));
		saveButton.setText(stringTable.get("Save"));
		loadButton.setText(stringTable.get("Load"));
		closeButton.setText(stringTable.get("Close"));
		
		nameLabel.setText(stringTable.get("Name"));
		optionLabel.setText(stringTable.get("Option"));
		finalizeCallLabel.setText(stringTable.get("Code executed at closing"));
		authLabel.setText(stringTable.get("Authority code (Optional)"));		
		optionEditableCheck.setText(stringTable.get("User can input own option"));
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
	private void prepareChooser()
	{
		File defaultFile = new File(Controller.getDefaultPath());
		try
		{
			if(! defaultFile.exists())
			{
				defaultFile.mkdir();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if(fileChooser == null)	fileChooser = new JFileChooser(defaultFile);
		if(fileFilter == null)
		{
			fileFilter = new FileFilter()
			{			
				@Override
				public boolean accept(File pathname)
				{
					if(pathname != null)
					{
						if(pathname.isDirectory()) return false;
						if(pathname.getAbsolutePath().endsWith(".module")) return true;
					}
					return false;
				}
				@Override
				public String getDescription()
				{					
					return Controller.getString("Converting Module (.module)");
				}
			};
			fileFilter2 = new FileFilter()
			{			
				@Override
				public boolean accept(File pathname)
				{
					if(pathname != null)
					{
						if(pathname.isDirectory()) return false;
						if(pathname.getAbsolutePath().endsWith(".bmodule")) return true;
					}
					return false;
				}
				@Override
				public String getDescription()
				{					
					return Controller.getString("Converting Module binary file (.bmodule)");
				}
			};
			fileFilter3 = new FileFilter()
			{			
				@Override
				public boolean accept(File pathname)
				{
					if(pathname != null)
					{
						if(pathname.isDirectory()) return false;
						if(pathname.getAbsolutePath().endsWith(".xmodule")) return true;
					}
					return false;
				}
				@Override
				public String getDescription()
				{					
					return Controller.getString("Converting Module xml file (.xmodule)");
				}
			};
			fileFilter4 = new FileFilter()
			{			
				@Override
				public boolean accept(File pathname)
				{
					if(pathname != null)
					{
						if(pathname.isDirectory()) return false;
						if(pathname.getAbsolutePath().endsWith(".bzmodule")) return true;
					}
					return false;
				}
				@Override
				public String getDescription()
				{					
					return Controller.getString("Converting Module binary compressed file (.bzmodule)");
				}
			};
			fileFilter5 = new FileFilter()
			{			
				@Override
				public boolean accept(File pathname)
				{
					if(pathname != null)
					{
						if(pathname.isDirectory()) return false;
						if(pathname.getAbsolutePath().endsWith(".xmodule")) return true;
					}
					return false;
				}
				@Override
				public String getDescription()
				{					
					return Controller.getString("Converting Module xml compressed file (.xzmodule)");
				}
			};
			fileChooser.setFileFilter(fileFilter);
			fileChooser.addChoosableFileFilter(fileFilter2);
			if(Statics.useUntestedFunction()) fileChooser.addChoosableFileFilter(fileFilter3);
			fileChooser.addChoosableFileFilter(fileFilter4);
			if(Statics.useUntestedFunction()) fileChooser.addChoosableFileFilter(fileFilter5);
		}
	}
	@Override
	public void save()
	{	
		try
		{
			fieldToObject();
			
			prepareChooser();
			int selects = fileChooser.showSaveDialog(dialog);
			if(selects == JFileChooser.APPROVE_OPTION)
			{
				// String gets = target.toString();
				// Controller.saveFile(fileChooser.getSelectedFile(), gets, null);
				target.save(fileChooser.getSelectedFile());
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(dialog, Controller.getString("Error") + " : " + e.getMessage());
		}		
	}
	@Override
	public void load()
	{		
		try
		{			
			prepareChooser();
			int selects = fileChooser.showOpenDialog(dialog);
			if(selects == JFileChooser.APPROVE_OPTION)
			{
				// String gets = Controller.readFile(fileChooser.getSelectedFile(), 20, null);
				target = new UserDefinedModule(fileChooser.getSelectedFile());
				objectToField();
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(dialog, Controller.getString("Error") + " : " + e.getMessage());
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
		return (String) scriptTypeSelector.getSelectedItem();
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
		List<String> items = new Vector<String>();
		for(int i=0; i<scriptTypeSelector.getItemCount(); i++)
		{
			items.add((String) scriptTypeSelector.getItemAt(i));
		}
		
		boolean exists = false;
		for(int i=0; i<items.size(); i++)
		{
			if(items.get(i).equals(value))
			{
				exists = true;
				scriptTypeSelector.setSelectedIndex(i);
				break;
			}
		}
		if(exists) return;
		else
		{
			scriptTypeSelector.addItem(value);
			scriptTypeSelector.setSelectedIndex(scriptTypeSelector.getItemCount() - 1);
		}
	}

	@Override
	public boolean isOptionEditable()
	{
		return optionEditableCheck.isSelected();
	}

	@Override
	public void setOptionEditable(boolean editables)
	{
		optionEditableCheck.setSelected(editables);
	}
}
