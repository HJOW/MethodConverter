package hjow.methodconverter.awtconverter;

import hjow.methodconverter.Controller;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

/**
 * <p>This dialog object show alert message to user.</p>
 * 
 * <p>이 대화 상자 객체는 사용자에게 경고 메시지를 보여 줍니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class AWTAlert extends JDialog implements ActionListener, WindowListener
{
	private static final long serialVersionUID = 2634792221800303700L;
	private Panel mainPanel;
	private Label textLabel;
	private Panel textPanel;
	private Panel buttonPanel;
	private Button closeButton;
	private Button yesButton;
	
	public String inputs = null;
	public boolean selection = false;
	private TextField inputField;
	
	/**
	 * <p>Create new alert dialog object.</p>
	 * 
	 * <p>경고 대화 상자 객체를 만듭니다.</p>
	 * 
	 * @param frame : Frame object
	 */
	public AWTAlert(Frame frame)
	{
		super(frame, true);
		init();
	}
	
	/**
	 * <p>Create new alert dialog object.</p>
	 * 
	 * <p>경고 대화 상자 객체를 만듭니다.</p>
	 * 
	 * @param dialog : Dialog object
	 */
	public AWTAlert(Dialog dialog)
	{
		super(dialog, true);
		init();
	}
	
	private void init()
	{
		this.setSize(400, 150);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(scrSize.getWidth()/2 - this.getWidth()/2), (int)(scrSize.getHeight()/2 - this.getHeight()/2));
		this.setLayout(new BorderLayout());
		mainPanel = new Panel();
		this.add(mainPanel);
		this.addWindowListener(this);
		mainPanel.setLayout(new BorderLayout());
		textLabel = new Label();
		textPanel = new Panel();
		mainPanel.add(textPanel, BorderLayout.CENTER);
		textPanel.setLayout(new FlowLayout());
		textPanel.add(textLabel);
		closeButton = new Button(Controller.getString("Close"));
		closeButton.addActionListener(this);
		buttonPanel = new Panel();
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());		
		yesButton = new Button(Controller.getString("Yes"));
		yesButton.addActionListener(this);
		buttonPanel.add(yesButton);
		yesButton.setVisible(false);
		inputField = new TextField(10);
		buttonPanel.add(inputField);
		inputField.setVisible(false);		
		buttonPanel.add(closeButton);
	}
	/**
	 * <p>Open alert dialog for showing message.</p>
	 * 
	 * <p>메시지를 보이는 용도로 대화 상자를 엽니다.</p>
	 * 
	 * @param message : Message
	 */
	public void alert(String message)
	{
		setMessage(message);
		yesButton.setVisible(false);
		inputField.setVisible(false);
		inputs = null;
		selection = false;
		this.setVisible(true);
	}
	/**
	 * <p>Open alert dialog for selected yes or no.</p>
	 * 
	 * <p>예, 아니오를 선택받는 용도로 대화 상자를 엽니다.</p>
	 * 
	 * @param message : Message
	 */
	public void requestSelection(String message)
	{
		setMessage(message);
		yesButton.setVisible(true);
		inputField.setVisible(false);
		inputs = null;
		selection = false;
		this.setVisible(true);
	}
	/**
	 * <p>Open alert dialog for input text.</p>
	 * 
	 * <p>텍스트를 입력 받는 대화 상자를 엽니다.</p>
	 * 
	 * @param message : Message
	 */
	public void requestInput(String message)
	{
		setMessage(message);
		yesButton.setVisible(false);
		inputField.setVisible(true);
		inputs = null;
		selection = false;
		this.setVisible(true);
	}
	/**
	 * <p>Input message.</p>
	 * 
	 * <p>보여 줄 메시지를 입력합니다.</p>
	 * 
	 * @param message : Message
	 */
	public void setMessage(String message)
	{
		textLabel.setText(message);
	}
	/**
	 * <p>Change the close button text.</p>
	 * 
	 * <p>닫기 버튼에 보이는 텍스트를 바꿉니다.</p>
	 * 
	 * @param text : close button text
	 */
	public void setButtonText(String text)
	{
		closeButton.setLabel(text);
	}
	/**
	 * <p>Close this dialog.</p>
	 * 
	 * <p>대화 상자를 닫습니다.</p>
	 */
	public void close()
	{
		this.setVisible(false);
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
	public void windowClosing(WindowEvent e)
	{		
		close();
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
	@Override
	public void actionPerformed(ActionEvent e)
	{		
		close();
	}	
}
