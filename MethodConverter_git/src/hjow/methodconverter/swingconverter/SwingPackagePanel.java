package hjow.methodconverter.swingconverter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ui.Manager;
import hjow.methodconverter.ui.PackageViewer;

/**
 * <p>This class object can show packages received. Made with Swing library.</p>
 * 
 * <p>이 클래스는 받은 패키지를 보여 주는 데 사용됩니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class SwingPackagePanel extends PackageViewer implements ListSelectionListener
{
	private JPanel upPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JList lists;
	private JScrollPane listScroll;
	private JEditorPane area;
	private JScrollPane areaScroll;
	private String[] packNames;	
	private JButton refreshButton;
	private JPanel buttonPanel;
	private JPanel upStatusPanel;
	private JCheckBox aliveBox;
	private JButton viewButton;
	private JButton saveButton;
	private JFileChooser fileChooser;
	private JPanel listPanel;
	private JSplitPane splitPane;

	/**
	 * <p>Create the object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 */
	public SwingPackagePanel()
	{
		super();
		init(null);
	}
	/**
	 * <p>Create the object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 * 
	 * @param upper : Manager object
	 */
	public SwingPackagePanel(Object upper)
	{
		super();
		init(upper);
	}
	
	@Override
	public void refresh()
	{
		packages = getPackageList();
		packNames = new String[packages.size()];
		for(int i=0; i<packNames.length; i++)
		{
			packNames[i] = packages.get(i).getName();
		}
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				lists.setListData(packNames);
				// System.out.println(Converter.isReceiverAlive());
				aliveBox.setSelected(Controller.isReceiverAlive());
			}}
		);		
		
		SwingUtilities.invokeLater(new Runnable()
		{				
			@Override
			public void run()
			{
				aliveBox.setSelected(Controller.isReceiverAlive());					
			}
		});	
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == refreshButton)
		{
			((Manager) super.upper).refreshPackageList();
		}
		else if(ob == viewButton)
		{
			((Manager) super.upper).setTextOnArea(area.getText());
			try
			{
				((SwingManager) super.upper).selectConvertTextTab();
			}
			catch(Exception e1)
			{
				
			}
		}
		else if(ob == saveButton)
		{
			int selection;
			int selectedIndex = lists.getSelectedIndex();
			if(selectedIndex <= 0)
			{
				try
				{
					if(upper instanceof SwingManager) JOptionPane.showMessageDialog(((SwingManager) upper).getFrame(), Controller.getString("Error") + " : " + Controller.getString("Please select package and retry."));					
				}
				catch(Exception e2)
				{
					e2.printStackTrace();
				}
				return;
			}
			if(upper instanceof JDialog)
			{
				selection = fileChooser.showSaveDialog((JDialog) upper);
			}
			else if(upper instanceof JFrame)
			{
				selection = fileChooser.showSaveDialog((JFrame) upper);
			}
			else
			{
				selection = fileChooser.showSaveDialog(null);
			}
			if(selection == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					packages.get(selectedIndex).saveContents(fileChooser.getSelectedFile());
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
					try
					{
						if(upper instanceof SwingManager) JOptionPane.showMessageDialog(((SwingManager) upper).getFrame(), Statics.fullErrorMessage(e1));						
					}
					catch(Exception e2)
					{
						
					}
				}			
			}
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object ob = e.getSource();
		if(ob == aliveBox)
		{
			aliveBoxChanged();		
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		Object ob = e.getSource();
		if(ob == lists)
		{
			SwingUtilities.invokeLater(new Runnable()
			{				
				@Override
				public void run()
				{
					try
					{
						String areaText = "";
						areaText = areaText + packages.get(lists.getSelectedIndex()).toString();
						area.setText(areaText);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			});
			
		}
	}

	@Override
	public void init(Object upper)
	{
		super.upper = upper;
		
		JPanel mainPanel = new JPanel();
		component = mainPanel;
		
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new JPanel();
		centerPanel = new JPanel();
		downPanel = new JPanel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());
		
		splitPane = new JSplitPane();
		centerPanel.add(splitPane, BorderLayout.CENTER);
		
		listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		
		lists = new JList();
		lists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lists.addListSelectionListener(this);
		listScroll = new JScrollPane(lists);
		
		listPanel.add(listScroll, BorderLayout.CENTER);
		
		splitPane.setLeftComponent(listPanel);
		
		area = new JEditorPane();
		area.setEditable(false);
		areaScroll = new JScrollPane(area);
		
		splitPane.setRightComponent(areaScroll);
		
		upPanel.setLayout(new BorderLayout());
		
		buttonPanel = new JPanel();
		upStatusPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		upStatusPanel.setLayout(new FlowLayout());
		
		upPanel.add(buttonPanel, BorderLayout.WEST);
		upPanel.add(upStatusPanel, BorderLayout.EAST);
		
		refreshButton = new JButton(Controller.getString("Refresh"));
		refreshButton.addActionListener(this);
		buttonPanel.add(refreshButton);
		
		viewButton = new JButton(Controller.getString("Send to the convert area"));
		viewButton.addActionListener(this);
		buttonPanel.add(viewButton);
		
		saveButton = new JButton(Controller.getString("Save"));
		saveButton.addActionListener(this);
		buttonPanel.add(saveButton);
		
		fileChooser = new JFileChooser();		
				
		aliveBox = new JCheckBox(Controller.getString("Receiving realtime"));
		aliveBox.addItemListener(this);
		// aliveBox.setEnabled(false);
		upStatusPanel.add(aliveBox);
		
		refresh();
	}
	
	@Override
	public void close()
	{
		super.upper = null;
	}
	@Override
	public boolean isAliveChecked()
	{
		return aliveBox.isSelected();
	}
	@Override
	public void aliveBoxChanged()
	{
		if(aliveBox.isSelected())
		{
			if(! (Controller.isReceiverAlive()))
			{
				Controller.prepareReceiver();
			}
		}
		else
		{
			Controller.closeReceiver();
		}
		SwingUtilities.invokeLater(new Runnable()
		{				
			@Override
			public void run()
			{
				aliveBox.setSelected(Controller.isReceiverAlive());					
			}
		});	
	}
}
