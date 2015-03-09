package hjow.methodconverter.awtconverter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.GUIPrinter;
import hjow.methodconverter.ui.ScriptGUIConsole;

/**
 * <p>This class object show AWT based script console.</p>
 * 
 * <p>이 클래스 객체는 AWT 기반 스크립트 콘솔을 보여주는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class AWTScriptConsole extends ScriptGUIConsole
{
	private Panel mainPanel;
	
	/**
	 * <p>Create objects and components.</p>
	 * 
	 * <p>객체와 컴포넌트들을 만듭니다.</p>
	 * 
	 * @param printer : GUIPrinter object, can be SwingPrinter, AWTPrinter
	 */
	public AWTScriptConsole(GUIPrinter printer)
	{
		super(printer);
	}
	@Override
	public void init(GUIPrinter printer)
	{		
		this.printer = printer;
		Frame frame = new Frame();
		
		
		window = frame;
		frame.setSize(550, 400);
		frame.setLayout(new BorderLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth()/2 - frame.getWidth()/2), (int)(screenSize.getHeight()/2 - frame.getHeight()/2));
		frame.addWindowListener(this);
		frame.setTitle(Controller.getString("Script"));	
		
		mainPanel = new Panel();
		mainPanel.setLayout(new BorderLayout());
		
		frame.add(mainPanel);	
		mainPanel.setLayout(new BorderLayout());		
		
		mainPanel.add(Controller.getPrinterComponent());		
	}

	@Override
	public void open()
	{
		window.setVisible(true);
		Controller.clearPrinter();
		Controller.println(Controller.firstMessage, true);
		Controller.focusOnPrinter();
	}

	@Override
	public void close()
	{
		window.setVisible(false);
		printer = null;
		Controller.closeAll();
		System.exit(0);
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == window)
		{
			close();
		}
	}
}
