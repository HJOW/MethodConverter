package hjow.methodconverter.swingconverter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.Manager;
import hjow.methodconverter.ui.ModuleList;

/**
 * <p>This class helps to show unauthorized modules list with Swing API.</p>
 * 
 * <p>이 클래스는 사용자에게 비인증 모듈 리스트를 보여주는 것을 돕습니다. Swing API로 만들어졌습니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingModuleList extends ModuleList
{	
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel upPanel;
	private JPanel downPanel;
	private JList list;
	private JScrollPane scroll;
	private JTextField addField;
	private JButton addButton;
	private JLabel addLabel;
	private JLabel upLabel;
	private JComboBox binarySelection;
	private JButton sendButton;
	private JButton newButton;
	private JPanel labelPanel;
	private JPanel buttonPanel;
	/**
	 * <p>Create and initialize components.</p>
	 * 
	 * <p>컴포넌트들을 생성하고 초기화합니다.</p>
	 * 
	 * @param manager : Manager object
	 */
	public SwingModuleList(Manager manager)
	{
		super(manager);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new JPanel();
		centerPanel = new JPanel();
		downPanel = new JPanel();
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		upPanel.setLayout(new BorderLayout());
		centerPanel.setLayout(new BorderLayout());
		downPanel.setLayout(new FlowLayout());
		
		labelPanel = new JPanel();
		buttonPanel = new JPanel();
		upPanel.add(labelPanel, BorderLayout.CENTER);
		upPanel.add(buttonPanel, BorderLayout.EAST);
		
		labelPanel.setLayout(new FlowLayout());
		buttonPanel.setLayout(new FlowLayout());		
		
		upLabel = new JLabel(Controller.getString("User defined") + " " + Controller.getString("Module list"));
		labelPanel.add(upLabel);
		
		newButton = new JButton(Controller.getString("New"));
		newButton.addActionListener(this);
		buttonPanel.add(newButton);
		
		sendButton = new JButton(Controller.getString("Send"));
		sendButton.addActionListener(this);
		buttonPanel.add(sendButton);
		
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll = new JScrollPane(list);
		
		centerPanel.add(scroll, BorderLayout.CENTER);
		
		addLabel = new JLabel(Controller.getString("Input URL to download module"));
		addField = new JTextField(15);
		
		String[] binarySelects = new String[2];
		binarySelects[0] = Controller.getString("Binary");
		binarySelects[1] = Controller.getString("Text");
		binarySelection = new JComboBox(binarySelects);
		
		addButton = new JButton(Controller.getString("Add"));
		addButton.addActionListener(this);
		
		downPanel.add(addLabel);
		downPanel.add(addField);
		downPanel.add(binarySelection);
		downPanel.add(addButton);
	}
	@Override
	public boolean isBinaryMode()
	{
		if(Controller.getString("Text").equalsIgnoreCase(String.valueOf(binarySelection.getSelectedItem())))
		{
			return false;
		}
		else return true;
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == addButton)
		{
			addButtonPressed();
		}
		else if(ob == sendButton)
		{
			sendButtonPressed();
		}
		else if(ob == newButton)
		{
			newButtonPressed();
		}
	}
	@Override
	public Component getComponent()
	{
		return mainPanel;
	}

	@Override
	public void setList(String[] listData)
	{
		list.setListData(listData);
	}
	@Override
	public String getAddFieldText()
	{
		return addField.getText();
	}
	@Override
	public void setAddFieldText(String text)
	{
		addField.setText(text);		
	}
	@Override
	public int getSelectedModuleIndex()
	{
		return list.getSelectedIndex();
	}
}
