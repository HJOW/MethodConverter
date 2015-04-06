package hjow.msq.ui;

import hjow.methodconverter.DefaultStringTable;
import hjow.methodconverter.StringTable;
import hjow.msq.control.ErrorControl;
import hjow.msq.control.MainControl;
import hjow.msq.query.FileRelatedQuery;
import hjow.msq.query.Query;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame implements ActionListener, ChangeListener
{
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JTabbedPane centerPanel;
	private JPanel upPanel;
	private JPanel downPanel;
	
	private MainListener listener = null;
	private StringTable stl =  new DefaultStringTable();
	private JPanel connectionPanel;
	private JPanel[] connectionPns;
	private JLabel[] connectionLbs;
	private String connectionTabLabel;
	private JComboBox urlField;
	
	private List<HasComponent> tabs = new Vector<HasComponent>();
	
	private transient HasComponent tempComp;
	private transient int tempInt;
	
	private JButton btConnect;
	private JButton btDisconnect;
	private JButton btExit;
	private JComboBox dbField;
	private JComboBox idField;
	private JPasswordField pwField;
	private JPanel controlPanel;
	private JButton btRun;
	
	public MainFrame()
	{
		int frameWidth = 600, frameHeight = 450;
		
		frame = new JFrame();
		listener = new MainListener(this);	
		
		frame.addWindowListener(listener);
				
		try
		{
			if(getOption("width") != null) frameWidth = Integer.parseInt(getOption("width"));
		}
		catch(Exception e)
		{
			
		}
		try
		{
			if(getOption("height") != null) frameHeight = Integer.parseInt(getOption("height"));
		}
		catch(Exception e)
		{
			
		}
		
		frame.setSize(frameWidth, frameHeight);
		
		Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenResolution.getWidth()/2 - frame.getWidth()/2), (int)(screenResolution.getHeight()/2 - frame.getHeight()/2));
		
		frame.getContentPane().setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel);
		
		mainPanel.setLayout(new BorderLayout());
		
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		centerPanel = new JTabbedPane();
		upPanel = new JPanel();
		downPanel = new JPanel();
		
		centerPanel.addChangeListener(this);
		
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(rightPanel, BorderLayout.EAST);
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		rightPanel.setLayout(new BorderLayout());
		
		MainControl.logControl = new LogPanel();
		rightPanel.add(((LogPanel) MainControl.logControl).getComponent());
		
		connectionPanel = new JPanel();		
		
		connectionPns = new JPanel[7];
		connectionLbs = new JLabel[connectionPns.length];
		connectionPanel.setLayout(new GridLayout(connectionPns.length, 1));
		
		for(int i=0; i<connectionPns.length; i++)
		{
			connectionPns[i] = new JPanel();
			connectionPns[i].setLayout(new FlowLayout());
			connectionLbs[i] = new JLabel();
			connectionPns[i].add(connectionLbs[i]);
			connectionPanel.add(connectionPns[i]);
		}
		
		connectionLbs[1].setText(stl.t("DB      "));
		connectionLbs[2].setText(stl.t("URL     "));
		connectionLbs[3].setText(stl.t("ID      "));
		connectionLbs[4].setText(stl.t("Password"));
		
		dbField = new JComboBox();
		dbField.setEditable(true);
		connectionPns[1].add(dbField);
		
		urlField = new JComboBox();
		urlField.setEditable(true);
		connectionPns[2].add(urlField);
		
		idField = new JComboBox();
		idField.setEditable(true);
		connectionPns[3].add(idField);
		
		pwField = new JPasswordField(10);		
		connectionPns[4].add(pwField);
		
		btConnect = new JButton(stl.t("Connect"));
		btConnect.addActionListener(this);
		connectionPns[6].add(btConnect);
		
		btDisconnect = new JButton(stl.t("Disconnect"));
		btDisconnect.addActionListener(this);
		connectionPns[6].add(btDisconnect);
		
		btExit = new JButton(stl.t("Exit"));
		btExit.addActionListener(this);
		connectionPns[6].add(btExit);
		
		connectionTabLabel = stl.t("Connection");
		centerPanel.addTab(connectionTabLabel, connectionPanel);
		
		upPanel.setLayout(new BorderLayout());
		
		controlPanel = new JPanel();
		upPanel.add(controlPanel, BorderLayout.CENTER);
		
		controlPanel.setLayout(new FlowLayout());
		
		btRun = new JButton(stl.t("▶"));
		btRun.addActionListener(this);
		controlPanel.add(btRun);
	}
	public void open()
	{
		frame.setVisible(true);
	}
	public void close()
	{
		frame.setVisible(false);
		MainControl.getControl().close();
		for(int i=0; i<tabs.size(); i++)
		{
			tabs.get(i).close();
		}
		listener.close();
		System.exit(0);
	}
	public void refreshConnection()
	{
		SwingUtilities.invokeLater(new Runnable()
		{	
			@Override
			public void run()
			{
				btDisconnect.setEnabled(MainControl.getControl().isConnected());
			}
		});
		
		connectionTabLabel = stl.t("Connected") + " : " + MainControl.getControl().getConnectionName();
		refreshTab();
	}
	public void refreshTab()
	{
		SwingUtilities.invokeLater(new Runnable()
		{	
			@Override
			public void run()
			{
				centerPanel.removeAll();
				
				centerPanel.addTab(connectionTabLabel, connectionPanel);
				
				for(int i=0; i<tabs.size(); i++)
				{
					centerPanel.addTab(tabs.get(i).getName(), tabs.get(i).getComponent());
				}
			}
		});		
		refreshTabSimply();
	}
	public void refreshTabSimply()
	{
		
	}
	
	public void newTab(Query query, String name)
	{	
		HasComponent comp = new QueryTab(query, name);
		((QueryTab) comp).setSuperComp(this);
		tabs.add(comp);
		
		tempComp = comp;
		
		SwingUtilities.invokeLater(new Runnable()
		{	
			@Override
			public void run()
			{	
				centerPanel.add(tempComp.getName(), tempComp.getComponent());
			}
		});
	}
	public void removeTab(HasComponent comp)
	{
		tabs.remove(comp.getComponent());
		tempComp = comp;
		
		if(comp instanceof QueryTab)
		{
			((QueryTab) comp).close();
		}
		
		boolean isExist = false;
		for(int i=0; i<tabs.size(); i++)
		{
			if(comp.getUniqueKey() == tabs.get(i).getUniqueKey())
			{
				tempInt = i;
				isExist = true;
			}
			try
			{
				tabs.get(i).refreshContent();
			}
			catch(Exception e)
			{
				ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
			}
		}
		
		if(isExist)
		{
			SwingUtilities.invokeLater(new Runnable()
			{	
				@Override
				public void run()
				{
					centerPanel.removeTabAt(tempInt);
				}
			});
		}
	}
	public void alert(String msg)
	{
		JOptionPane.showMessageDialog(frame, msg);
	}
	public String getOption(String k)
	{
		return MainControl.getOptionValue(k);
	}
	public Window getWindow()
	{
		return frame;
	}
	public static void addItemInComboBox(JComboBox box, String item)
	{
		boolean inFieldInside = false;
		for(int i=0; i<box.getItemCount(); i++)
		{
			if(box.getItemAt(i).equals(item))
			{
				inFieldInside = true;
				break;
			}
		}
		if(! inFieldInside)
		{
			box.addItem(item);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == btConnect)
		{			
			boolean connectResult = MainControl.getControl().connect(String.valueOf(dbField.getSelectedItem()), String.valueOf(urlField.getSelectedItem())
					, String.valueOf(idField.getSelectedItem()), new String(pwField.getPassword()));
			refreshConnection();
				
			addItemInComboBox(dbField, (String) dbField.getSelectedItem());
			addItemInComboBox(urlField, (String) urlField.getSelectedItem());
			addItemInComboBox(idField, (String) idField.getSelectedItem());
			
			if(connectResult)
			{
				newTab(new FileRelatedQuery(""), "empty_" + String.format("%02d", new Integer(tabs.size())));
			}
		}
		else if(ob == btDisconnect)
		{
			MainControl.getControl().close();
			refreshConnection();
		}
		else if(ob == btExit)
		{
			close();
		}
		else if(ob == btRun)
		{
			tabs.get(centerPanel.getSelectedIndex()).run();
		}
		else listener.actionPerformed(e);
	}
	@Override
	public void stateChanged(ChangeEvent e)
	{
		Object ob = e.getSource();
		if(ob == centerPanel)
		{			
			refreshTabSimply();
		}
		else listener.stateChanged(e);
	}
}
