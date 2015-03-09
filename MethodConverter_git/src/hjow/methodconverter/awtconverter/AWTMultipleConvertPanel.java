package hjow.methodconverter.awtconverter;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.MultipleConvertPanel;

/**
 * <p>This AWT component can be used for convert text via multiple modules.</p>
 * 
 * <p>이 AWT 컴포넌트는 여러 모듈을 사용해 텍스트를 변환하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTMultipleConvertPanel extends MultipleConvertPanel
{
	private Panel upPanel;
	private Panel centerPanel;
	private Panel downPanel;
	private TextArea orderArea;
	private Button convertButton;
	private TextArea area;

	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 */
	public AWTMultipleConvertPanel()
	{
		super();
		
		Panel mainPanel = new Panel();
		component = mainPanel;
		mainPanel.setLayout(new BorderLayout());
		
		upPanel = new Panel();
		centerPanel = new Panel();
		downPanel = new Panel();
		
		mainPanel.add(upPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(downPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new GridLayout(2, 1));
		
		orderArea = new TextArea("", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);			
		area = new TextArea("", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);
		
		centerPanel.add(orderArea);
		centerPanel.add(area);
		
		downPanel.setLayout(new FlowLayout());
		
		convertButton = new Button(Controller.getString("Convert"));
		convertButton.addActionListener(this);
		
		downPanel.add(convertButton);		
		setOrderText("# " + Controller.getString("Input orders here"));
		setOrderText("# " + Controller.getString("moduleName, --param1 param1Value, --param2 param2Value, ..."));
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
