package hjow.methodconverter.awtconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.GUIPrinter;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>This is Printer class with AWT API.</p>
 * 
 * <p>이 클래스는 AWT API를 이용한 Printer 클래스입니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTPrinter extends GUIPrinter implements ActionListener
{
	private Panel mainPanel;
	private Panel centerPanel;
	private Panel downPanel;
	private TextArea area;
	private TextField field;
	private Button runScript;
	
	/**
	 * <p>Create components.</p>
	 * 
	 * <p>컴포넌트들을 생성합니다.</p>
	 */
	public AWTPrinter()
	{
		super();
		mainPanel = new Panel();
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new Panel();
		downPanel = new Panel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());
		downPanel.setLayout(new BorderLayout());
		
		area = new TextArea("", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);
		centerPanel.add(area, BorderLayout.CENTER);
		
		field = new TextField();
		field.addActionListener(this);
		runScript = new Button(Controller.getString("Run"));
		runScript.addActionListener(this);
		downPanel.add(field, BorderLayout.CENTER);
		downPanel.add(runScript, BorderLayout.EAST);
	}
	@Override
	public void clear()
	{
		area.setText("");
	}
	@Override
	public void print(String str)
	{
		if(printOnTerminal) System.out.print(str);
		area.append(str);
	}
	@Override
	public void newLine()
	{
		if(printOnTerminal) System.out.println();
		area.append("\n");
	}
	@Override
	public Component getComponent()
	{
		return mainPanel;
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == runScript || ob == field)
		{
			runScript(field.getText());
		}		
	}
	@Override
	public String getFieldText()
	{
		return field.getText();
	}
	@Override
	public void setFieldText(String str)
	{
		field.setText(str);
	}
	@Override
	public void enableAll(boolean l)
	{
		field.setEnabled(l);
		runScript.setEnabled(l);
	}	
}
