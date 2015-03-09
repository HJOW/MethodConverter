package hjow.methodconverter.awtconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.HelpManager;
import hjow.methodconverter.ui.UIStatics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;

/**
 * <p>AWT based help dialog object.</p>
 * 
 * <p>AWT 기반 도움말 대화 상자 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTHelpManager extends HelpManager
{
	private List moduleList;
	private TextArea area;
	private Panel mainPanel;
	private Panel centerPanel;
	private Button closeButton;
	private Panel downPanel;
	
	/**
	 * <p>Initialize AWT objects.</p>
	 * 
	 * <p>AWT 객체들을 초기화합니다.</p>
	 * 
	 * @param frame : Frame or Dialog object
	 */
	public AWTHelpManager(Window frame)
	{
		window = new Dialog(frame);
		((Dialog) window).setTitle(Controller.getString("Help"));
		window.addWindowListener(this);
		window.setSize(600, 420);
		window.setLayout(new BorderLayout());		
		
		UIStatics.locateCenter(window);
		
		area = new TextArea("", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);
		area.setEditable(false);
		moduleList = new List();
		moduleList.setMultipleMode(false);
		
		mainPanel = new Panel();
		centerPanel = new Panel();
		downPanel = new Panel();
		
		closeButton = new Button(Controller.getString("Close"));
		
		window.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());				
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(moduleList, BorderLayout.WEST);
		centerPanel.add(area, BorderLayout.CENTER);
		
		moduleList.addMouseListener(this);
		moduleList.addItemListener(this);	
		
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
	public void mousePressed(MouseEvent e)
	{
		Object ob = e.getSource();
		if(ob == moduleList)
		{
			int indexs = moduleList.getSelectedIndex();
			if(indexs >= moduleList.getItemCount()) return;
			if(indexs < 0) return;
			showHelp(modules.get(indexs));
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object ob = e.getSource();
		if(ob == moduleList)
		{
			int indexs = moduleList.getSelectedIndex();
			if(indexs >= moduleList.getItemCount()) return;
			if(indexs < 0) return;
			showHelp(modules.get(indexs));
		}
	}

	@Override
	protected void setListText(java.util.List<String> lists)
	{
		moduleList.removeAll();
		for(int i=0; i<lists.size(); i++)
		{
			moduleList.add(lists.get(i));
		}
	}
}
