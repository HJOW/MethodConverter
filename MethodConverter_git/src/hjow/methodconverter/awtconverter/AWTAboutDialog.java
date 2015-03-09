package hjow.methodconverter.awtconverter;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.AboutDialog;
import hjow.methodconverter.ui.RGB;

/**
 * <p>This about dialog is consisted with AWT library.</p>
 * 
 * <p>이 About 창은 AWT 라이브러리로 만들어졌습니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTAboutDialog extends AboutDialog
{
	private Panel mainPanel;
	private Panel centerPanel;
	private Panel downPanel;
	private Button closeButton;
	private Panel titlePanel;
	private Panel versionPanel;
	private Label titleLabel;
	private Label versionLabel;
	private Panel upPanel;
	private Panel serialPanel;
	private Label serialLabel;
	public AWTAboutDialog(Object superWindow)
	{
		super(superWindow);
	}
	@Override
	public void init(Object superWindow)
	{
		window = new Dialog((Frame) superWindow);
		window.setSize(250, 140);
		window.setLayout(new BorderLayout());
		mainPanel = new Panel();
		window.add(mainPanel);		
		window.addWindowListener(this);
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new Panel();
		downPanel = new Panel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		downPanel.setLayout(new FlowLayout());
		closeButton = new Button(Controller.getString("Close"));
		closeButton.addActionListener(this);
		downPanel.add(closeButton);
		
		centerPanel.setLayout(new BorderLayout());
		
		upPanel = new Panel();
		titlePanel = new Panel();
		versionPanel = new Panel();
		serialPanel = new Panel();
		
		upPanel.setLayout(new BorderLayout());
		upPanel.add(titlePanel, BorderLayout.CENTER);
		
		serialPanel.setLayout(new FlowLayout());
		serialLabel = new Label(Controller.getGradeName());
		RGB gradeRGB = Controller.getGradeRGB();
		Color gradeColor = new Color(gradeRGB.getR(), gradeRGB.getG(), gradeRGB.getB());
		serialLabel.setForeground(gradeColor);
		serialLabel.addMouseListener(this);
		serialColor = serialLabel.getForeground();
		serialPanel.add(serialLabel);
		
		centerPanel.add(upPanel, BorderLayout.CENTER);
		centerPanel.add(versionPanel, BorderLayout.SOUTH);
		
		titlePanel.setLayout(new FlowLayout());
		versionPanel.setLayout(new FlowLayout());
		
		titleLabel = new Label();
		Font labelFont = titleLabel.getFont();
		if(labelFont == null) labelFont = new Font(null, Font.PLAIN, 10);
		titleLabel.setFont(new Font(labelFont.getFamily(), labelFont.getStyle(), 20));
		titlePanel.add(titleLabel);
		setTitleText(Controller.getString("Method Converter"));
		
		versionLabel = new Label();
		versionPanel.add(versionLabel);
		String versions = "v";
		for(int i=0; i<Controller.versions.length; i++)
		{
			versions = versions + String.valueOf(Controller.versions[i]);
			if(i < Controller.versions.length - 1) versions = versions + ".";
		}
		if(Controller.showBuildNumber)
		{
			versions = versions + " (" + Controller.getString("build") + " " + String.valueOf(Controller.buildNumber) + ")";
		}
		versionLabel.setText(versions);
	}
	@Override
	public void setCloseText(String closeText)
	{
		closeButton.setLabel(closeText);
	}
	@Override
	public void setTitleText(String titles)
	{
		titleLabel.setText(titles);
		try
		{
			((Dialog) window).setTitle(titles);
		}
		catch(Exception e)
		{
			
		}
	}
	@Override
	public void setVersionText(String versions)
	{
		versionLabel.setText(versions);
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == window)
		{
			close();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == closeButton)
		{
			close();
		}
	}	
	@Override
	public void mouseExited(MouseEvent e)
	{
		Object ob = e.getSource();
		if(ob == serialLabel)
		{
			serialLabel.setForeground(serialColor);
		}
	}
	@Override
	public void mouseEntered(MouseEvent e)
	{
		Object ob = e.getSource();
		if(ob == serialLabel)
		{
			serialLabel.setForeground(new Color(serialColor.getRed() + ((255 -  serialColor.getRed()) / 2)
					, serialColor.getGreen() + ((255 -  serialColor.getGreen()) / 2)
					, serialColor.getBlue() + ((255 -  serialColor.getBlue()) / 2)));
		}
	}
	@Override
	public void mousePressed(MouseEvent e)
	{
		Object ob = e.getSource();
		if(ob == serialLabel)
		{
			if(serialDialog == null) serialDialog = new AWTSerialDialog(window);
			serialDialog.open();
		}
	}
}
