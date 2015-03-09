package hjow.methodconverter.swingconverter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.Manager;
import hjow.methodconverter.ui.MemoryManager;

/**
 * <p>This class can help to show the memory status to user with Swing API.</p>
 * 
 * <p>이 클래스는 사용자에게 Swing API를 통해 메모리 상태를 보여주는 데 도움을 줍니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingMemoryManager extends MemoryManager implements ListSelectionListener
{
	private JPanel upPanel;
	private JPanel downPanel;
	private JPanel centerPanel;
	private JLabel memoryLabel;
	private JProgressBar gageBar;
	private JButton clearButton;
	private JList centerList;
	private JScrollPane centerListScroll;
	private JButton closeButton;	
	private Manager upper;
	private JPanel mainPanel;
	private JSplitPane centerSplit;
	private JTextArea centerArea;
	private JScrollPane centerAreaScroll;

	public SwingMemoryManager(Manager upper)
	{
		super();
		this.upper = upper;
		JPanel panel = new JPanel();
		component = panel;
		
		panel.setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		panel.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new JPanel();
		downPanel = new JPanel();
		centerPanel = new JPanel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		upPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new BorderLayout());
		downPanel.setLayout(new FlowLayout());
		
		memoryLabel = new JLabel();
		gageBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		clearButton = new JButton(Controller.getString("Clear"));
		clearButton.addActionListener(this);
		
		upPanel.add(memoryLabel);
		upPanel.add(gageBar);
		upPanel.add(clearButton);
		
		centerSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		centerPanel.add(centerSplit, BorderLayout.CENTER);
		
		centerList = new JList();
		centerList.addListSelectionListener(this);
		centerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		centerListScroll = new JScrollPane(centerList);
		
		centerArea = new JTextArea();
		centerArea.setLineWrap(true);
		centerArea.setEditable(false);
		centerAreaScroll = new JScrollPane(centerArea);
		
		centerSplit.setTopComponent(centerAreaScroll);
		centerSplit.setBottomComponent(centerListScroll);
		centerSplit.setDividerLocation(0.5);
		
		closeButton = new JButton(Controller.getString("Close selected thread"));
		closeButton.addActionListener(this);
		downPanel.add(closeButton);
	}
	
	/**
	 * <p>Make splitter center.</p>
	 * 
	 * <p>창 사이 구분자를 중앙에 위치합니다.</p>
	 */
	public void setDividerCenter()
	{
		SwingUtilities.invokeLater(new Runnable()
		{			
			@Override
			public void run()
			{
				centerSplit.setDividerLocation(0.5);
			}
		});
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
				gageBar.setValue((int)Math.round(getMemoryStatus() * 100));
				memoryLabel.setText(dataSizeString((totalMemory() - freeMemory()), 3) + " / " + dataSizeString(totalMemory(), 3));
			}
		});
	}
	
	@Override
	protected void setStackTraceText(String text)
	{
		super.setStackTraceText(text);
		if(centerArea != null) centerArea.setText(text);
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
				Vector<String> names = new Vector<String>();
				for(int i=0; i<threadList.size(); i++)
				{
					names.add(Controller.getThreadName(threadList.get(i).longValue()) + " (" + String.valueOf(threadList.get(i)) + ")");
				}
				if(centerList != null) centerList.setListData(names);
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
