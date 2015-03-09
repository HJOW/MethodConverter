package hjow.methodconverter.swingconverter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.FontSetting;
import hjow.methodconverter.ui.GUIPrinter;
import hjow.methodconverter.ui.ScriptGUIConsole;

/**
 * <p>This class object show Swing based script console.</p>
 * 
 * <p>이 클래스 객체는 Swing 기반 스크립트 콘솔을 보여주는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingScriptConsole extends ScriptGUIConsole
{
	private JPanel mainPanel;
	private JTabbedPane mainTab;
	private JPanel scriptPanel;
	private JTextArea scriptArea;
	private JScrollPane scriptScroll;
	private JButton runButton;
	
	/**
	 * <p>Create objects and components.</p>
	 * 
	 * <p>객체와 컴포넌트들을 만듭니다.</p>
	 * 
	 * @param printer : GUIPrinter object, can be SwingPrinter, AWTPrinter
	 */
	public SwingScriptConsole(GUIPrinter printer)
	{
		super(printer);
	}
	@Override
	public void init(GUIPrinter printer)
	{		
		this.printer = printer;
		JFrame frame = new JFrame();
		
		SwingManager.initTheme();
		try
		{
			SwingManager.setIcon(frame, new ImageIcon(getClass().getClassLoader().getResource("ico.png")).getImage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		window = frame;
		frame.setSize(550, 400);
		frame.setLayout(new BorderLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth()/2 - frame.getWidth()/2), (int)(screenSize.getHeight()/2 - frame.getHeight()/2));
		frame.addWindowListener(this);
		frame.setTitle(Controller.getString("Script"));	
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		frame.add(mainPanel);	
		mainPanel.setLayout(new BorderLayout());
		
		mainTab = new JTabbedPane();
		mainPanel.add(mainTab);
		
		mainTab.add(Controller.getPrinterComponent(), Controller.getString("Console"));	
		
		scriptPanel = new JPanel();
		scriptPanel.setLayout(new BorderLayout());
		scriptArea = new JTextArea();
		scriptScroll = new JScrollPane(scriptArea);
		scriptPanel.add(scriptScroll, BorderLayout.CENTER);
		runButton = new JButton(Controller.getString("Run"));
		runButton.addActionListener(this);
		scriptPanel.add(runButton, BorderLayout.SOUTH);
		
		mainTab.add(scriptPanel, Controller.getString("Run"));
		
		try
		{
			FontSetting.prepareFont();
			FontSetting.setFontRecursively(window, FontSetting.usingFont);
		}
		catch(Exception e)
		{
			System.out.println(Controller.getString("Fail to load font") + " : " + e.getMessage());
			// e.printStackTrace();
		}
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
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == runButton)
		{
			printer.runScript(scriptArea.getText());
			mainTab.setSelectedComponent(Controller.getPrinterComponent());
		}
	}
}
