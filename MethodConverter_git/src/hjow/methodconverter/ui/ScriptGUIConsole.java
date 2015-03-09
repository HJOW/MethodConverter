package hjow.methodconverter.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * <p>This class object show GUI based script console.</p>
 * 
 * <p>이 클래스 객체는 GUI 기반 스크립트 콘솔을 보여주는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class ScriptGUIConsole implements ActionListener, WindowListener
{
	protected Window window;
	protected GUIPrinter printer;
	
	/**
	 * <p>Create objects and components.</p>
	 * 
	 * <p>객체와 컴포넌트들을 만듭니다.</p>
	 * 
	 * @param printer : GUIPrinter object, can be SwingPrinter, AWTPrinter
	 */
	public ScriptGUIConsole(GUIPrinter printer)
	{
		init(printer);
	}
	
	/**
	 * <p>Initialize objects and components.</p>
	 * 
	 * <p>객체와 컴포넌트들을 초기화합니다.</p>
	 * 
	 * @param printer : GUIPrinter object, can be SwingPrinter, AWTPrinter
	 */
	public abstract void init(GUIPrinter printer);
	
	/**
	 * <p>Open window.</p>
	 * 
	 * <p>창을 엽니다.</p>
	 */
	public abstract void open();
	
	/**
	 * <p>Close window and exit the program.</p>
	 * 
	 * <p>창을 닫고 프로그램을 종료합니다.</p>
	 */
	public abstract void close();

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
		
		
	}
}
