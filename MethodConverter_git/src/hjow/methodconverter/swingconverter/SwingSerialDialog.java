package hjow.methodconverter.swingconverter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.SerialDialog;

/**
 * <p>This dialog can get serial code from user input.</p>
 * 
 * <p>이 대화 상자는 사용자에게 시리얼 코드 입력을 받습니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingSerialDialog extends SerialDialog
{
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JPanel upPanel;
	private JTextField[] serialFields;
	private JLabel[] serialLabels;
	private JButton acceptButton;
	private JButton closeButton;
	private JEditorPane area;
	private JScrollPane scroll;

	/**
	 * <p>Create and initialize dialog.</p>
	 * 
	 * <p>대화 상자를 생성하고 초기화합니다.</p>
	 * 
	 * @param frame : JDialog object
	 */
	public SwingSerialDialog(Window frame)
	{
		dialog = new JDialog(frame);
		dialog.addWindowListener(this);
		dialog.setSize((eachBlockSize * 20) * serialBlockCount + 100, 200);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)(scrSize.getWidth()/2 - dialog.getWidth()/2), (int)(scrSize.getHeight()/2 - dialog.getHeight()/2));
		
		mainPanel = new JPanel();
		dialog.setLayout(new BorderLayout());
		dialog.add(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new JPanel();
		centerPanel = new JPanel();
		downPanel = new JPanel();
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new BorderLayout());	
		area = new JEditorPane();
		area.setEditable(false);
		scroll = new JScrollPane(area);
		centerPanel.add(scroll);
		try
		{
			area.setPage(new java.net.URL(Controller.getDefaultURL() + "serial.html"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		downPanel.setLayout(new FlowLayout());
		serialFields = new JTextField[serialBlockCount];
		serialLabels = new JLabel[serialBlockCount - 1];
		for(int i=0; i<serialBlockCount; i++)
		{
			serialFields[i] = new JTextField(eachBlockSize);
			downPanel.add(serialFields[i]);
			if(i < serialBlockCount - 1)
			{
				serialLabels[i] = new JLabel("-");
				downPanel.add(serialLabels[i]);
			}
		}
		acceptButton = new JButton(Controller.getString("Accept"));
		closeButton = new JButton(Controller.getString("Close"));
		acceptButton.addActionListener(this);
		closeButton.addActionListener(this);
		downPanel.add(acceptButton);
		downPanel.add(closeButton);
		
		upPanel.setLayout(new BorderLayout());
		
		try
		{
			FontSetting.prepareFont();
			FontSetting.setFontRecursively(dialog, FontSetting.usingFont);
		}
		catch(Exception e)
		{
			System.out.println(Controller.getString("Fail to load font") + " : " + e.getMessage());
			// e.printStackTrace();
		}
	}
	
	@Override
	public String getSerialText(int index)
	{
		return serialFields[index].getText();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == closeButton)
		{
			close();
		}
		else if(ob == acceptButton)
		{
			input();
		}
		else super.actionPerformed(e);
	}
}
