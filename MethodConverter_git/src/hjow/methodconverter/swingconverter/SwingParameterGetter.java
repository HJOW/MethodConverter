package hjow.methodconverter.swingconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.GUIParameterGetter;
import hjow.methodconverter.ui.HasParameterText;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * <p>This class help getting parameters from user by Swing API.</p>
 * 
 * <p>이 클래스는 사용자로부터 Swing 기반 GUI 형태로 매개 변수를 입력받도록 돕습니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingParameterGetter extends GUIParameterGetter
{
	private JPanel mainPanel;
	private JPanel upPanel;
	private JLabel keyLabel;
	private JComboBox keyField;
	private JLabel valueLabel;
	private JTextField valueField;
	private JPanel downPanel;
	private JButton addButton;
	private JButton closeButton;
	private JPanel centerPanel;
	private JTextArea area;
	private JScrollPane scroll;

	public SwingParameterGetter(HasParameterText upper)
	{
		super(upper);
		dialog = new JDialog(upper.getWindow());
		
		((JDialog) dialog).setTitle(Controller.getString("Parameter"));
		dialog.setSize(450, 350);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.setLayout(new BorderLayout());
		dialog.addWindowListener(this);
		
		mainPanel = new JPanel();
		dialog.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new JPanel();
		upPanel = new JPanel();
		downPanel = new JPanel();
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		
		centerPanel.setLayout(new BorderLayout());
		upPanel.setLayout(new FlowLayout());
		downPanel.setLayout(new FlowLayout());
		
		area = new JTextArea();
		area.setLineWrap(true);
		area.setEditable(false);
		scroll = new JScrollPane(area);
		centerPanel.add(scroll, BorderLayout.CENTER);
		
		keyLabel = new JLabel(Controller.getString("Key"));
		keyField = new JComboBox();
		keyField.setEditable(true);
		valueLabel = new JLabel(Controller.getString("Value"));
		valueField = new JTextField(10);
		
		upPanel.add(keyLabel);
		upPanel.add(keyField);
		upPanel.add(valueLabel);
		upPanel.add(valueField);
		
		addButton = new JButton(Controller.getString("Add"));
		addButton.addActionListener(this);
		closeButton = new JButton(Controller.getString("Close"));
		closeButton.addActionListener(this);
		downPanel.add(addButton);
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
	}	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{		
		Object ob = e.getSource();
		if(ob == addButton)
		{
			Map<String, String> nowParameter = toParameter(upper.getParameterFieldText());
			if(String.valueOf(keyField.getSelectedItem()).trim().equals("")) return;
			String values = valueField.getText().trim();
			if(values.equals("")) values = "true";
			nowParameter.put(String.valueOf(keyField.getSelectedItem()).trim(), values);
			
			upper.setParameterFieldText(toParameterString(nowParameter));
			upper.alert(Controller.getString("New parameter is added."));
			
			try
			{
				String paramAddCloseOption = Controller.getOption("CloseAfterParamAdded");
				if(paramAddCloseOption == null)
				{
					close(false);
				}
				else if(Statics.parseBoolean(paramAddCloseOption))
				{
					close(false);
				}
			}
			catch(Exception e1)
			{
				close(false);
			}
		}
		else if(ob == closeButton)
		{
			close(false);
		}
	}
	
	/**
	 * <p>Open dialog.</p>
	 * 
	 * <p>대화 상자를 엽니다.</p>
	 */
	@Override
	public void open()
	{
		super.open();
		dialog.setVisible(true);
		refreshHelps();
		refreshKeyList();
		valueField.setText("");
		keyField.requestFocus();
	}
	
	@Override
	public void refreshKeyList()
	{
		SwingUtilities.invokeLater(new Runnable()
		{			
			@Override
			public void run()
			{
				List<String> options = upper.getSelectedModuleParameterKeys();
				keyField.removeAllItems();
				
				if(options != null && options.size() >= 1)
				{
					for(int i=0; i<options.size(); i++)
					{
						keyField.addItem(options.get(i));
					}
				}
			}
		});		
	}
	
	@Override
	public void refreshHelps()
	{
		String getHelps;//  = Controller.getModules().get(Controller.getSelectedMode()).getParameterHelp();
		getHelps = upper.getSelectedModuleParamHelp();
		if(getHelps == null)
		{
			getHelps = Controller.getString("There is no helps about this module.");
		}
		else if(getHelps.trim().equals(""))
		{
			getHelps = Controller.getString("There is no helps about this module.");
		}
		else
		{
			String moduleName = "";
			try
			{
				moduleName = String.valueOf(upper.getSelectedModuleName());
			}
			catch(Exception e)
			{
				moduleName = "";
			}
			getHelps = getDefaultParamHelp() + "\n" + Controller.getString("Module") + " : " + moduleName + "\n\n" + getHelps;
		}
		area.setText(getHelps);
		area.setCaretPosition(0);
	}
	
	/**
	 * <p>Close dialog.</p>
	 * 
	 * <p>대화 상자를 닫습니다.</p>
	 */
	@Override
	public void close(boolean closeFinally)
	{
		super.close(closeFinally);
		dialog.setVisible(false);
	}
}
