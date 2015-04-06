package hjow.msq.ui;

import hjow.msq.control.LogControl;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogPanel extends LogControl
{
	private JTextArea area;
	private JScrollPane scroll;
	
	public LogPanel()
	{
		super();
		
		area = new JTextArea();
		area.setEditable(false);
		area.setLineWrap(true);
		scroll = new JScrollPane(area);
	}
	public void print(Object ob)
	{
		int position = area.getDocument().getLength() - 1;
		if(position < 0) position = 0;
		
		area.append(String.valueOf(ob));		
		area.setCaretPosition(position);
		
		super.print(ob);
	}
	public void println()
	{
		int position = area.getDocument().getLength() - 1;
		if(position < 0) position = 0;
		
		area.append("\n");
		area.setCaretPosition(position);
		
		super.println();
	}
	public void println(Object ob)
	{
		int position = area.getDocument().getLength() - 1;
		if(position < 0) position = 0;
		
		area.append(String.valueOf(ob) + "\n");
		area.setCaretPosition(position);
		
		super.println(ob);
	}
	public Component getComponent()
	{
		return scroll;
	}
}
