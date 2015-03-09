package hjow.methodconverter.swingconverter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Document;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.GUIPrinter;
import hjow.methodconverter.ui.TextAreaComponent;

/**
 * <p>This is Printer class with Swing API.</p>
 * 
 * <p>이 클래스는 Swing API를 이용한 Printer 클래스입니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingPrinter extends GUIPrinter implements ActionListener, TextAreaComponent
{
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JTextArea area;
	private JScrollPane scroll;
	private JTextField field;
	private JButton runScript;	
	
	/**
	 * <p>Create components.</p>
	 * 
	 * <p>컴포넌트들을 생성합니다.</p>
	 */
	public SwingPrinter()
	{
		super();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new JPanel();
		downPanel = new JPanel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());
		downPanel.setLayout(new BorderLayout());
		
		area = new JTextArea();
		area.setLineWrap(true);
		area.setEditable(false);
		scroll = new JScrollPane(area);
		centerPanel.add(scroll, BorderLayout.CENTER);
		
		field = new JTextField();
		field.addActionListener(this);
		runScript = new JButton(Controller.getString("Run"));
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
		try
		{
			if(printOnTerminal) System.out.print(str);
			area.append(str);	
			area.setCaretPosition(area.getDocument().getLength() - 1);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Override
	public void newLine()
	{
		if(printOnTerminal) System.out.println();
		area.append("\n");
		area.setCaretPosition(area.getDocument().getLength() - 1);
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
	public void focus()
	{
		super.focus();
		field.requestFocus();
	}
	@Override
	public void enableAll(boolean l)
	{
		field.setEnabled(l);
		runScript.setEnabled(l);
	}
	@Override
	public void setText(String str)
	{
		area.setText(str);
	}
	@Override
	public String getText()
	{
		return area.getText();
	}
	@Override
	public Color getBackground()
	{
		return area.getBackground();
	}
	@Override
	public Color getForeground()
	{
		return area.getForeground();
	}
	@Override
	public void setBackground(Color color)
	{
		area.setBackground(color);
		
	}
	@Override
	public void setForeground(Color color)
	{
		area.setForeground(color);
	}
	@Override
	public Document getDocument()
	{
		return area.getDocument();
	}
	@Override
	public void setDocument(Document doc)
	{
		area.setDocument(doc);
	}	
}
