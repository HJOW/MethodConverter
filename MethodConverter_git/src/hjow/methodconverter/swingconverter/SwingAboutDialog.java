package hjow.methodconverter.swingconverter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.AboutDialog;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.RGB;

/**
 * <p>This about dialog is consisted with Swing library.</p>
 * 
 * <p>이 About 창은 Swing 라이브러리로 만들어졌습니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingAboutDialog extends AboutDialog
{
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JButton closeButton;
	private JPanel titlePanel;
	private JPanel versionPanel;
	private JLabel titleLabel;
	private JLabel versionLabel;
	private JEditorPane area;
	private JScrollPane areaScroll;
	private JPanel upPanel;
	private JPanel serialPanel;
	private JLabel serialLabel;
	public SwingAboutDialog(Object superWindow)
	{
		super(superWindow);
	}
	@Override
	public void init(Object superWindow)
	{
		boolean isApplet = false;
		if(superWindow instanceof JFrame) window = new JDialog((JFrame) superWindow);
		else if(superWindow instanceof Frame) window = new JDialog((Frame) superWindow);
		else if(superWindow instanceof JApplet)
		{
			isApplet = true;
			window = new JDialog(JOptionPane.getFrameForComponent((Component) superWindow));
		}
		else if(superWindow instanceof JPanel)
		{
			isApplet = true;
			window = new JDialog(JOptionPane.getFrameForComponent((Component) superWindow));
		}
		
		try
		{
			window.setSize(450, 300);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			window.setLocation((int)(screenSize.getWidth()/2 - window.getWidth()/2), (int)(screenSize.getHeight()/2 - window.getHeight()/2));
			window.setLayout(new BorderLayout());			
			window.addWindowListener(this);
		}
		catch(Exception e)
		{
			
		}
		
		mainPanel = new JPanel();
		window.add(mainPanel);		
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new JPanel();
		downPanel = new JPanel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		downPanel.setLayout(new FlowLayout());
		closeButton = new JButton(Controller.getString("Close"));
		closeButton.addActionListener(this);
		downPanel.add(closeButton);
		
		centerPanel.setLayout(new BorderLayout());
		
		upPanel = new JPanel();
		titlePanel = new JPanel();
		versionPanel = new JPanel();
		serialPanel = new JPanel();
		
		area = new JEditorPane();
		area.setEditable(false);
		areaScroll = new JScrollPane(area);
		
		try
		{
			area.setPage(new java.net.URL(Controller.getDefaultURL() + "notice.html"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		upPanel.setLayout(new BorderLayout());
		
		upPanel.add(titlePanel, BorderLayout.CENTER);
		upPanel.add(serialPanel, BorderLayout.SOUTH);
		
		centerPanel.add(upPanel, BorderLayout.NORTH);
		centerPanel.add(areaScroll, BorderLayout.CENTER);
		centerPanel.add(versionPanel, BorderLayout.SOUTH);
		
		Font labelFont = serialPanel.getFont();
		if(labelFont == null) labelFont = new Font(null, Font.PLAIN, 10);
		
		serialPanel.setLayout(new FlowLayout());
		serialLabel = new JLabel(Controller.getGradeName());
		RGB gradeRGB = Controller.getGradeRGB();
		Color gradeColor = new Color(gradeRGB.getR(), gradeRGB.getG(), gradeRGB.getB());
		serialLabel.setForeground(gradeColor);
		
		if(! isApplet) serialLabel.addMouseListener(this);
		serialColor = serialLabel.getForeground();
		serialPanel.add(serialLabel);
		serialPanel.setBackground(new Color(170, 170, 150));
		
		titlePanel.setLayout(new FlowLayout());
		versionPanel.setLayout(new FlowLayout());
		
		titleLabel = new JLabel();			
		
		titlePanel.add(titleLabel);
		titlePanel.setBackground(new Color(170, 170, 150));
		setTitleText(Controller.getString("Method Converter"));
		
		versionLabel = new JLabel();
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
		
		try
		{
			FontSetting.prepareFont();
			FontSetting.setFontRecursively(window, FontSetting.usingFont);
		}
		catch(Exception e)
		{
			System.out.println(Controller.getString("Fail to load font") + " : " + e.getMessage());
			// e.printStackTrace();
		}
		
		serialLabel.setFont(new Font(labelFont.getFamily(), Font.BOLD, labelFont.getSize()));
		titleLabel.setFont(new Font(labelFont.getFamily(), labelFont.getStyle(), 30));
	}
	@Override
	public void setCloseText(String closeText)
	{
		closeButton.setText(closeText);
	}
	@Override
	public void setTitleText(String titles)
	{
		titleLabel.setText(titles);
		try
		{
			((JDialog) window).setTitle(titles);
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
			if(serialDialog == null) serialDialog = new SwingSerialDialog(window);
			serialDialog.open();
		}
	}
}
