package hjow.methodconverter.awtconverter;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.MemoryManager;

/**
 * <p>This class can help to show the memory status to user with AWT API.</p>
 * 
 * <p>이 클래스는 사용자에게 AWT API를 통해 메모리 상태를 보여주는 데 도움을 줍니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTMemoryManager extends MemoryManager implements ListSelectionListener
{
	private Panel upPanel;
	private Panel downPanel;
	private Panel centerPanel;
	private Label memoryLabel;
	private TextField gageBar;
	private Button clearButton;
	private java.awt.List centerList;
	private Button closeButton;	
	private AWTManager upper;
	private Panel mainPanel;

	public AWTMemoryManager(AWTManager upper)
	{
		super();
		this.upper = upper;
		Panel panel = new Panel();
		component = panel;
		
		panel.setLayout(new BorderLayout());
		
		mainPanel = new Panel();
		panel.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new Panel();
		downPanel = new Panel();
		centerPanel = new Panel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		upPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new BorderLayout());
		downPanel.setLayout(new FlowLayout());
		
		memoryLabel = new Label();
		gageBar = new TextField(20);
		clearButton = new Button(Controller.getString("Clear"));
		clearButton.addActionListener(this);
		
		upPanel.add(memoryLabel);
		upPanel.add(gageBar);
		upPanel.add(clearButton);
		
		centerList = new java.awt.List(10, false);
		centerPanel.add(centerList, BorderLayout.CENTER);
		
		closeButton = new Button(Controller.getString("Close selected thread"));
		closeButton.addActionListener(this);
		downPanel.add(closeButton);
	}
	
	@Override
	public synchronized void threadWork()
	{
		super.threadWork();
		SwingUtilities.invokeLater(new Runnable()
		{			
			@Override
			public void run()
			{
				gageBar.setText(getMemoryBar());
				memoryLabel.setText(dataSizeString((totalMemory() - freeMemory()), 3) + " / " + dataSizeString(totalMemory(), 3));
			}
		});
	}
	
	@Override
	public synchronized void refresh()
	{
		super.refresh();
		
		SwingUtilities.invokeLater(new Runnable()
		{			
			@Override
			public void run()
			{
				if(centerList != null)
				{
					centerList.removeAll();
					for(int i=0; i<threadList.size(); i++)
					{
						centerList.add(Controller.getThreadName(threadList.get(i).longValue()) + " (" + String.valueOf(threadList.get(i)) + ")");
					}
				}
			}
		});			
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object ob = e.getSource();
		if(ob == clearButton)
		{
			clean();
		}
		else if(ob == closeButton)
		{
			int selected = centerList.getSelectedIndex();
			if(selected >= 0)
			{
				Controller.closeThread(threadList.get(selected));
				if(upper != null) upper.refreshObjects();
				refreshList();
			}
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		Object ob = e.getSource();
		if(ob == centerList)
		{
			
		}
	}
	@Override
	public void close()
	{
		super.close();
		
		SwingUtilities.invokeLater(new Runnable()
		{			
			@Override
			public void run()
			{
				mainPanel.removeAll();				
			}
		});
		upper.refreshObjects();
		upper = null;
	}
}
