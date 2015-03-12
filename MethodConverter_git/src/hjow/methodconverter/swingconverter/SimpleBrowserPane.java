package hjow.methodconverter.swingconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.BrowserPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;

/**
 * <p>This component can surf the internet.</p>
 * 
 * <p>이 컴포넌트 객체는 인터넷에 접속할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class SimpleBrowserPane extends BrowserPane
{
	protected TransparentEditorArea area;
	protected JScrollPane scrollPane;
	private TransparentPanel panels;
	private TransparentPanel controlPane;
	private TransparentPanel controlRightPane;
	private TransparentPanel controlLeftPane;
	private JButton goButton;
	private JButton backButton;
	private JButton forwardButton;
	
	/**
	 * <p>Create new component object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 */
	public SimpleBrowserPane()
	{
		panels = new TransparentPanel();
		panels.setLayout(new BorderLayout());
		
		controlPane = new TransparentPanel();
		controlPane.setLayout(new BorderLayout());
		
		controlRightPane = new TransparentPanel();
		controlLeftPane = new TransparentPanel();
		controlRightPane.setLayout(new FlowLayout());
		controlLeftPane.setLayout(new FlowLayout());
		
		area = new TransparentEditorArea();
		
		goButton = new JButton(Controller.getString("Go"));
		backButton = new JButton(Controller.getString("Back"));
		forwardButton = new JButton(Controller.getString("Back"));
		
		area.setEditable(false);
		area.addHyperlinkListener(this);
		area.addMouseListener(this);
		area.addMouseMotionListener(this);
		scrollPane = new JScrollPane(area);
		
		addressArea = new TransparentTextField(50);
		addressArea.addActionListener(this);
		
		panels.add(scrollPane, BorderLayout.CENTER);
		panels.add(controlPane, BorderLayout.NORTH);
		
		controlPane.add(addressArea.getComponent(), BorderLayout.CENTER);
		controlPane.add(controlLeftPane, BorderLayout.WEST);
		controlPane.add(controlRightPane, BorderLayout.EAST);
		
		goButton.addActionListener(this);
		backButton.addActionListener(this);
		forwardButton.addActionListener(this);
		
		controlRightPane.add(goButton);
		
		controlLeftPane.add(backButton);
		controlLeftPane.add(forwardButton);
	}
	
	public void controlPanelVisible(boolean v)
	{
		controlPane.setVisible(v);
	}
	
	@Override
	protected void setPage(String url) throws Exception
	{
		area.setPage(url);
		addressArea.setText(url);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == goButton)
		{
			try
			{
				goPage(addressArea.getText());
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				showException(e1);
			}
		}
		else if(ob == backButton)
		{
			try
			{
				goBack();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				showException(e1);
			}
		}
		else if(ob == forwardButton)
		{
			try
			{
				goForward();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				showException(e1);
			}
		}
		else super.actionPerformed(e);
	}
	
	@Override
	public Component getRealComponent()
	{
		return area;
	}

	@Override
	public Component getComponent()
	{
		return panels;
	}
}
