package hjow.methodconverter.awtconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.SerialDialog;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;

public class AWTSerialDialog extends SerialDialog
{
	private Panel mainPanel;
	private Panel centerPanel;
	private Panel downPanel;
	private Panel upPanel;
	private TextField[] serialFields;
	private Label[] serialLabels;
	private Button acceptButton;
	private Button closeButton;

	public AWTSerialDialog(Window frame)
	{
		dialog = new Dialog(frame);
		dialog.addWindowListener(this);
		dialog.setSize((eachBlockSize * 20) * serialBlockCount + 100, 150);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(scrSize.getWidth()/2 - dialog.getWidth()/2), (int)(scrSize.getHeight()/2 - dialog.getHeight()/2));
		
		mainPanel = new Panel();
		dialog.setLayout(new BorderLayout());
		dialog.add(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new Panel();
		centerPanel = new Panel();
		downPanel = new Panel();
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());	
		
		
		downPanel.setLayout(new FlowLayout());
		serialFields = new TextField[serialBlockCount];
		serialLabels = new Label[serialBlockCount - 1];
		for(int i=0; i<serialBlockCount; i++)
		{
			serialFields[i] = new TextField(eachBlockSize);
			downPanel.add(serialFields[i]);
			if(i < serialBlockCount - 1)
			{
				serialLabels[i] = new Label("-");
				downPanel.add(serialLabels[i]);
			}
		}
		acceptButton = new Button(Controller.getString("Accept"));
		closeButton = new Button(Controller.getString("Close"));
		acceptButton.addActionListener(this);
		closeButton.addActionListener(this);
		downPanel.add(acceptButton);
		downPanel.add(closeButton);
		
		upPanel.setLayout(new BorderLayout());		
	}
	
	@Override
	public String getSerialText(int index)
	{
		return serialFields[index].getText();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == closeButton)
		{
			close();
		}
		else if(ob == acceptButton)
		{
			input();
		}
		else super.actionPerformed(e);
	}
}
