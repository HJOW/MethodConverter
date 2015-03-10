package hjow.methodconverter.swingconverter;

import hjow.daemon.Daemon;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.GUIDaemonClient;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SwingDaemonClient extends GUIDaemonClient
{
	private JPanel mainPanel;
	private JPanel upPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JTextArea area;
	private JScrollPane scroll;
	private JTextField field;
	private JButton actButton;
	private JPanel l_mainPanel;
	private JTabbedPane tabPanel;
	private JPanel l_upPanel;
	private JPanel l_centerPanel;
	private JPanel l_downPanel;
	private JButton l_login;
	private JButton l_close;
	private JPanel l_contentsPanel;
	private JPanel[] l_contents;
	private JLabel[] l_lbs;
	private JTextField[] l_fields;
	private JButton logoutButton;
	private JPanel areaPanel;
	private JPanel multiplePanel;
	private JTextArea multipleArea;
	private JScrollPane mScroll;
	private JButton mRun;
	public SwingDaemonClient()
	{
		super();
		window = new JFrame();
		((JFrame) window).setTitle(Controller.getString("Daemon Client"));
		initFrame();
	}
	public SwingDaemonClient(JFrame frame)
	{
		super();
		window = new JDialog(frame);
		((JDialog) window).setTitle(Controller.getString("Daemon Client"));
		initFrame();
	}
	public SwingDaemonClient(JDialog frame)
	{
		super();
		window = new JDialog(frame);
		((JDialog) window).setTitle(Controller.getString("Daemon Client"));
		initFrame();
	}
	public SwingDaemonClient(Frame frame)
	{
		super();
		window = new JDialog(frame);
		((JDialog) window).setTitle(Controller.getString("Daemon Client"));
		initFrame();
	}
	public SwingDaemonClient(Dialog frame)
	{
		super();
		window = new JDialog(frame);
		((JDialog) window).setTitle(Controller.getString("Daemon Client"));
		initFrame();
	}
	public void initFrame()
	{
		window.addWindowListener(this);
		window.setSize(500, 400);
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((int)(scrSize.getWidth()/2 - window.getWidth()/2), (int)(scrSize.getHeight()/2 - window.getHeight()/2));
		
		window.setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		window.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new JPanel();
		centerPanel = new JPanel();
		downPanel = new JPanel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());
		
		tabPanel = new JTabbedPane();
		centerPanel.add(tabPanel, BorderLayout.CENTER);
		
		areaPanel = new JPanel();
		areaPanel.setLayout(new BorderLayout());
		area = new JTextArea();
		area.setLineWrap(true);
		area.setEditable(false);
		scroll = new JScrollPane(area);
		areaPanel.add(scroll, BorderLayout.CENTER);
		tabPanel.add(areaPanel, Controller.getString("Console"));
		
		multiplePanel = new JPanel();
		multiplePanel.setLayout(new BorderLayout());
		multipleArea = new JTextArea();
		multipleArea.setLineWrap(true);
		mScroll = new JScrollPane(multipleArea);
		multiplePanel.add(mScroll, BorderLayout.CENTER);
		mRun = new JButton(Controller.getString("Run"));
		mRun.addActionListener(this);
		multiplePanel.add(mRun, BorderLayout.SOUTH);
		tabPanel.add(multiplePanel, Controller.getString("Multiple Line"));
		
		downPanel.setLayout(new BorderLayout());
		
		field = new JTextField();
		actButton = new JButton(Controller.getString("Run"));
		actButton.addActionListener(this);
		
		downPanel.add(field, BorderLayout.CENTER);
		downPanel.add(actButton, BorderLayout.EAST);
		
		upPanel.setLayout(new FlowLayout());
		logoutButton = new JButton(Controller.getString("Disconnect"));
		logoutButton.addActionListener(this);
		upPanel.add(logoutButton);
		
		if(window instanceof JFrame) loginManager = new JDialog((JFrame) window, true);
		else if(window instanceof Frame) loginManager = new JDialog((Frame) window, true);
		else loginManager = new JDialog(window);
		if(loginManager instanceof JDialog) ((JDialog) loginManager).setTitle(Controller.getString("Login"));
		loginManager.setSize(300, 230);
		loginManager.setLocation((int)(scrSize.getWidth()/2 - loginManager.getWidth()/2), (int)(scrSize.getHeight()/2 - loginManager.getHeight()/2));
		loginManager.addWindowListener(this);
		loginManager.setLayout(new BorderLayout());
		
		l_mainPanel = new JPanel();
		loginManager.add(l_mainPanel, BorderLayout.CENTER);
		
		l_mainPanel.setLayout(new BorderLayout());
		
		l_upPanel = new JPanel();
		l_centerPanel = new JPanel();
		l_downPanel = new JPanel();
		
		l_mainPanel.add(l_upPanel, BorderLayout.NORTH);
		l_mainPanel.add(l_centerPanel, BorderLayout.CENTER);
		l_mainPanel.add(l_downPanel, BorderLayout.SOUTH);
		
		l_contentsPanel = new JPanel();
		l_centerPanel.setLayout(new BorderLayout());
		l_centerPanel.add(l_contentsPanel, BorderLayout.CENTER);
		
		l_contents = new JPanel[5];
		l_contentsPanel.setLayout(new GridLayout(l_contents.length, 1));
		l_lbs = new JLabel[l_contents.length];
		l_fields = new JTextField[l_contents.length];
		for(int i=0; i<l_contents.length; i++)
		{
			l_contents[i] = new JPanel();
			l_contentsPanel.add(l_contents[i]);
			l_contents[i].setLayout(new FlowLayout());
			l_lbs[i] = new JLabel();
			if(i == 3) l_fields[i] = new JPasswordField(10);
			else l_fields[i] = new JTextField(10);
			l_contents[i].add(l_lbs[i]);
			l_contents[i].add(l_fields[i]);
		}
		l_lbs[0].setText(Controller.getString("IP"));
		l_lbs[1].setText(Controller.getString("Port"));
		l_lbs[2].setText(Controller.getString("ID"));
		l_lbs[3].setText(Controller.getString("Password"));
		l_lbs[4].setText(Controller.getString("Login Key"));
		
		l_fields[1].setText(String.valueOf(Daemon.default_port));
		l_fields[4].setText(Controller.DEFAULT_DAEMON_KEY);
		
		l_downPanel.setLayout(new FlowLayout());
		l_login = new JButton(Controller.getString("Login"));
		l_close = new JButton(Controller.getString("Close"));
		l_login.addActionListener(this);
		l_close.addActionListener(this);
		l_downPanel.add(l_login);
		l_downPanel.add(l_close);
		try
		{
			FontSetting.setFontRecursively(window, FontSetting.usingFont);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void open()
	{
		window.setVisible(true);
		if(! logined)
		{
			loginManager.setVisible(true);
		}
		else
		{
			loginManager.setVisible(false);
		}
	}
	public void logout()
	{
		try
		{
			super.close();			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		loginManager.setVisible(true);
	}
	@Override
	public void close()
	{
		try
		{
			window.setVisible(false);
		}
		catch (Exception e1)
		{
			
		}
		try
		{
			super.close();
		}
		catch (Exception e1)
		{
			
		}
		if((window instanceof JFrame) || window instanceof Frame)
		{
			Controller.closeAll();
			System.exit(0);
		}
	}
	@Override
	protected void takeResult(String gets)
	{
		super.takeResult(gets);
		println(Controller.getString("From server") + " : " + gets);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == actButton)
		{
			try
			{
				area.append(Controller.getString("Try to send") + "...\n" + field.getText() + "\n..." + Controller.getString("end"));
				send(field.getText());				
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
				area.append(Statics.fullErrorMessage(e1));
			}
			field.setText("");
		}
		else if(ob == mRun)
		{
			try
			{
				area.append(Controller.getString("Try to send") + "...\n" + multipleArea.getText() + "\n..." + Controller.getString("end"));
				send(multipleArea.getText());				
				tabPanel.setSelectedComponent(areaPanel);
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
				area.append(Statics.fullErrorMessage(e1));
			}
		}
		else if(ob == logoutButton)
		{
			logout();			
		}
		else if(ob == l_login)
		{
			try
			{
				if(l_fields[4].getText().equals("")) connect(l_fields[2].getText(), l_fields[3].getText(), l_fields[0].getText(), Integer.parseInt(l_fields[1].getText()));
				else connect(l_fields[2].getText(), l_fields[3].getText(), l_fields[0].getText(), Integer.parseInt(l_fields[1].getText()), l_fields[4].getText());
				if(logined) loginManager.setVisible(false);
				else loginManager.setVisible(true);
			}
			catch (Exception e1)
			{
				JOptionPane.showMessageDialog(loginManager, Statics.fullErrorMessage(e1));
				e1.printStackTrace();
			}
		}		
		else if(ob == l_close)
		{
			close();
		}
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == window)
		{
			close();
		}
		else if(ob == loginManager)
		{
			if(logined)
			{
				loginManager.setVisible(false);
			}
			else
			{
				close();
			}
		}
	}
	
	@Override
	protected void login_refresh() throws Exception
	{
		super.login_refresh();
		if(logined)
		{
			if(loginManager.isVisible()) loginManager.setVisible(false);
		}
		if((! logined) && (login_timeout >= 1))
		{
			if(! loginManager.isVisible()) loginManager.setVisible(true);
			if(l_login.isEnabled()) l_login.setEnabled(false);
		}
		else if((! logined) && (login_timeout <= 0))
		{
			if(! loginManager.isVisible()) loginManager.setVisible(true);
			if(! l_login.isEnabled()) l_login.setEnabled(true);
		}
	}
	@Override
	protected void println(Object str)
	{
		area.append(String.valueOf(str));
		Controller.println(String.valueOf(str));
	}
	@Override
	protected void print(Object str)
	{
		area.append(String.valueOf(str) + "\n");
		Controller.print(String.valueOf(str));
	}
}
