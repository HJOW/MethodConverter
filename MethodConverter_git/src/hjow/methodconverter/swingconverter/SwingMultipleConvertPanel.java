package hjow.methodconverter.swingconverter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.MultipleConvertPanel;

/**
 * <p>This Swing component can be used for convert text via multiple modules.</p>
 * 
 * <p>이 Swing 컴포넌트는 여러 모듈을 사용해 텍스트를 변환하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingMultipleConvertPanel extends MultipleConvertPanel
{
	private JPanel upPanel;
	private JPanel centerPanel;
	private JPanel downPanel;
	private JTextArea orderArea;
	private JScrollPane orderScroll;
	private JButton convertButton;
	private JSplitPane splits;
	private JTextArea area;
	private JScrollPane scrolls;

	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 */
	public SwingMultipleConvertPanel()
	{
		super();
		
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
		
		splits = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		orderArea = new JTextArea("");
		orderScroll = new JScrollPane(orderArea);		
		splits.setTopComponent(orderScroll);
		
		area = new JTextArea("");
		scrolls = new JScrollPane(area);
		splits.setBottomComponent(scrolls);
		
		centerPanel.add(splits, BorderLayout.CENTER);
		
		downPanel.setLayout(new FlowLayout());
		
		convertButton = new JButton(Controller.getString("Convert"));
		convertButton.addActionListener(this);
		
		downPanel.add(convertButton);		
		setOrderText("# " + Controller.getString("Input orders here\n"
				+ "# " + Controller.getString("moduleName, --param1 param1Value --param2 param2Value ...")));
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == convertButton)
		{
			convert();
		}
	}

	@Override
	public String getOrderText()
	{
		return orderArea.getText();
	}

	@Override
	public void setOrderText(String text)
	{
		orderArea.setText(text);
	}

	@Override
	public String getContentText()
	{
		return area.getText();
	}

	@Override
	public void setContentText(String text)
	{
		area.setText(text);
	}
}
