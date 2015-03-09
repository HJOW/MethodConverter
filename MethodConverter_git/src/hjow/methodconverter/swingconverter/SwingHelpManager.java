package hjow.methodconverter.swingconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.HelpManager;
import hjow.methodconverter.ui.UIStatics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * <p>Swing based help dialog object.</p>
 * 
 * <p>Swing 기반 도움말 대화 상자 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingHelpManager extends HelpManager implements ListSelectionListener
{
	private JList moduleList;
	private TransparentTextArea area;
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton closeButton;
	private JPanel downPanel;
	private JScrollPane moduleListScroll;
	private JScrollPane areaScroll;
	private JTabbedPane areaPanel;
	private TransparentEditorArea webPanel;
	private JScrollPane webScroll;
	
	/**
	 * <p>Initialize AWT objects.</p>
	 * 
	 * <p>AWT 객체들을 초기화합니다.</p>
	 * 
	 * @param frame : Frame or Dialog object
	 */
	public SwingHelpManager(Window frame)
	{
		window = new JDialog(frame);
		((JDialog) window).setTitle(Controller.getString("Help"));
		window.addWindowListener(this);
		window.setSize(600, 420);
		window.setLayout(new BorderLayout());		
		
		UIStatics.locateCenter(window);
		
		area = new TransparentTextArea();
		area.setEditable(false);
		areaScroll = new JScrollPane(area);
		webPanel = new TransparentEditorArea();
		webPanel.setEditable(false);
		webScroll = new JScrollPane(webPanel);
		moduleList = new JList();
		moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		moduleListScroll = new JScrollPane(moduleList);
		
		mainPanel = new JPanel();
		centerPanel = new JPanel();
		downPanel = new JPanel();
		
		areaPanel = new JTabbedPane();	
		
		closeButton = new JButton(Controller.getString("Close"));
		
		window.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());				
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(moduleListScroll, BorderLayout.WEST);
		centerPanel.add(areaPanel, BorderLayout.CENTER);
		
		areaPanel.add(Controller.getString("Local"), areaScroll);
		areaPanel.add(Controller.getString("Web"), webScroll);
		
		moduleList.addListSelectionListener(this);	
		
		downPanel.setLayout(new FlowLayout());		
		
		closeButton.addActionListener(this);
		downPanel.add(closeButton);
		
		try
		{
			FontSetting.prepareFont();
			FontSetting.setFontRecursively(frame, FontSetting.usingFont);
		}
		catch(Exception e)
		{
			System.out.println(Controller.getString("Fail to load font") + " : " + e.getMessage());
			// e.printStackTrace();
		}
		
		setList();
	}
	
	@Override
	public void setHelpText(String text)
	{
		area.setText(text);
		area.setCaretPosition(0);
	}

	@Override
	public void open()
	{
		window.setVisible(true);
	}

	@Override
	public void close()
	{
		window.setVisible(false);
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
	protected void setListText(java.util.List<String> lists)
	{
		moduleList.removeAll();
		Vector<String> moduleNames = new Vector<String>();
		for(int i=0; i<lists.size(); i++)
		{
			moduleNames.add(lists.get(i));
		}
		moduleList.setListData(moduleNames);
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		Object ob = e.getSource();
		if(ob == moduleList)
		{
			int indexs = moduleList.getSelectedIndex();
			if(indexs < 0) return;
			showHelp(modules.get(indexs));
			if(! (modules.get(indexs).getUrl() == null || modules.get(indexs).getUrl().equals("")))
			{
				try
				{
					webPanel.setPage(modules.get(indexs).getUrl());
				}
				catch (IOException e1)
				{
					webPanel.setText(modules.get(indexs).getHelps());
				}
			}
			else
			{
				webPanel.setText(modules.get(indexs).getHelps());
			}
		}
		
	}
}
