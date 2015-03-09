package hjow.methodconverter.awtconverter;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.SwingUtilities;

import hjow.methodconverter.Controller;
import hjow.methodconverter.swingconverter.SwingManager;
import hjow.methodconverter.ui.Manager;
import hjow.methodconverter.ui.PackageViewer;

/**
 * <p>This class object can show packages received. Made with AWT library.</p>
 * 
 * <p>이 클래스는 받은 패키지를 보여 주는 데 사용됩니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class AWTPackageDialog extends PackageViewer implements WindowListener
{
	private Dialog dialog;
	private Panel mainPanel;
	private Panel upPanel;
	private Panel centerPanel;
	private Panel downPanel;
	private Panel listPanel;
	private List lists;
	private TextArea area;
	private Panel buttonPanel;
	private Panel upStatusPanel;
	private Button refreshButton;
	private Button viewButton;
	private Button saveButton;
	private FileDialog fileChooser;
	private Checkbox aliveBox;
	public AWTPackageDialog(Frame frame)
	{
		super(frame);
	}
	
	@Override
	public void init(Object upper)
	{
		dialog = new Dialog((Frame) upper);
		dialog.setSize(500, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(screenSize.getWidth()/2 - dialog.getWidth()/2), (int)(screenSize.getHeight()/2 - dialog.getHeight()/2));
		dialog.setLayout(new BorderLayout());
		dialog.addWindowListener(this);
		
		mainPanel = new Panel();
		dialog.add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new Panel();
		centerPanel = new Panel();
		downPanel = new Panel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());
		
		
		listPanel = new Panel();
		listPanel.setLayout(new BorderLayout());
		
		lists = new java.awt.List(10, false);
		lists.addItemListener(this);
		
		listPanel.add(lists, BorderLayout.CENTER);
		
		centerPanel.add(listPanel, BorderLayout.WEST);
		
		area = new TextArea("", 5, 5, TextArea.SCROLLBARS_VERTICAL_ONLY);
		area.setEditable(false);
		
		centerPanel.add(area, BorderLayout.CENTER);
		
		upPanel.setLayout(new BorderLayout());
		
		buttonPanel = new Panel();
		upStatusPanel = new Panel();
		buttonPanel.setLayout(new FlowLayout());
		upStatusPanel.setLayout(new FlowLayout());
		
		upPanel.add(buttonPanel, BorderLayout.WEST);
		upPanel.add(upStatusPanel, BorderLayout.EAST);
		
		refreshButton = new Button(Controller.getString("Refresh"));
		refreshButton.addActionListener(this);
		buttonPanel.add(refreshButton);
		
		viewButton = new Button(Controller.getString("Send to the convert area"));
		viewButton.addActionListener(this);
		buttonPanel.add(viewButton);
		
		saveButton = new Button(Controller.getString("Save"));
		saveButton.addActionListener(this);
		buttonPanel.add(saveButton);
		
		fileChooser = new FileDialog(dialog, Controller.getString("Save"), FileDialog.SAVE);		
				
		aliveBox = new Checkbox(Controller.getString("Receiving realtime"));
		aliveBox.addItemListener(this);
		// aliveBox.setEnabled(false);
		upStatusPanel.add(aliveBox);
		
		refresh();		
		Controller.insertRefreshes(this);
	}
	
	@Override
	public void close()
	{
		super.close();
		dialog.setVisible(false);
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == dialog)
		{
			close();
		}
	}
	
	@Override
	public void refresh()
	{
		packages = getPackageList();
		lists.removeAll();
		for(int i=0; i<packages.size(); i++)
		{
			lists.add(packages.get(i).getName());
			aliveBox.setState(Controller.isReceiverAlive());
		}
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
			int selectedIndex = lists.getSelectedIndex();
			if(selectedIndex <= 0)
			{
				try
				{
					if(upper instanceof AWTManager) ((AWTManager) upper).alert(Controller.getString("Error") + " : " + Controller.getString("Please select package and retry."));					
				}
				catch(Exception e2)
				{
					e2.printStackTrace();
				}
				return;
			}
			fileChooser.setVisible(true);
			
			String fileName = fileChooser.getFile();
			if(fileName != null)
			{
				fileName = fileChooser.getDirectory() + fileName;
				try
				{
					packages.get(selectedIndex).saveContents(new File(fileName));
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
					((Manager) upper).alert(Controller.getString(e1.getMessage()));
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
		else if(ob == lists)
		{
			try
			{
				String areaText = "";
				areaText = areaText + packages.get(lists.getSelectedIndex()).toString();
				area.setText(areaText);
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}			
		}
	}
	
	@Override
	public boolean isAliveChecked()
	{
		return aliveBox.getState();
	}

	@Override
	public void aliveBoxChanged()
	{
		if(aliveBox.getState())
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
				aliveBox.setState(Controller.isReceiverAlive());					
			}
		});			
	}
	
	@Override
	public void windowActivated(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		
		
	}
}
