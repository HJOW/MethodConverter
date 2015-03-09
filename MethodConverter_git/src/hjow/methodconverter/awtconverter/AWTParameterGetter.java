package hjow.methodconverter.awtconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.GUIParameterGetter;
import hjow.methodconverter.ui.Manager;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * <p>This class help getting parameters from user by AWT API.</p>
 * 
 * <p>이 클래스는 사용자로부터 AWT 기반 GUI 형태로 매개 변수를 입력받도록 돕습니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTParameterGetter extends GUIParameterGetter
{
	private Panel mainPanel;
	private Panel upPanel;
	private Label keyLabel;
	private TextField keyField;
	private Label valueLabel;
	private TextField valueField;
	private Panel downPanel;
	private Button addButton;
	private Button closeButton;
	private Panel centerPanel;
	private TextArea area;

	public AWTParameterGetter(Manager upper)
	{
		super(upper);
		dialog = new Dialog(upper.getWindow());
		((Dialog) dialog).setTitle(Controller.getString("Parameter"));
		dialog.setSize(350, 250);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.setLayout(new BorderLayout());
		dialog.addWindowListener(this);
		
		mainPanel = new Panel();
		dialog.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new Panel();
		upPanel = new Panel();
		downPanel = new Panel();
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		upPanel.setLayout(new FlowLayout());
		downPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new BorderLayout());
		
		area = new TextArea("", 10, 5, TextArea.SCROLLBARS_VERTICAL_ONLY);
		area.setEditable(false);
		centerPanel.add(area, BorderLayout.CENTER);
		
		keyLabel = new Label(Controller.getString("Key"));
		keyField = new TextField(5);
		valueLabel = new Label(Controller.getString("Value"));
		valueField = new TextField(10);
	
		upPanel.add(keyLabel);
		upPanel.add(keyField);
		upPanel.add(valueLabel);
		upPanel.add(valueField);
		
		addButton = new Button(Controller.getString("Add"));
		addButton.addActionListener(this);
		closeButton = new Button(Controller.getString("Close"));
		closeButton.addActionListener(this);
		downPanel.add(addButton);
		downPanel.add(closeButton);
	}	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{		
		Object ob = e.getSource();
		if(ob == addButton)
		{
			Map<String, String> nowParameter = toParameter(upper.getParameterFieldText());
			if(keyField.getText().trim().equals("")) return;
			String values = valueField.getText().trim();
			if(values.equals("")) values = "true";
			nowParameter.put(keyField.getText().trim(), values);
			
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
		String getHelps = upper.getSelectedModuleParamHelp(); 
				//Controller.getModules().get(Controller.getSelectedMode()).getParameterHelp();
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
		keyField.setText("");
		valueField.setText("");
		keyField.requestFocus();
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