package hjow.methodconverter.ui;

import hjow.convert.module.DaemonClient;

import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * <p>This class object can access daemon server on the internet with GUI environment.</p>
 * 
 * <p>이 클래스 객체는 GUI 기반 환경에서 인터넷 상의 다른 데몬 서버에 접근할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class GUIDaemonClient extends DaemonClient implements ActionListener, ItemListener, WindowListener
{
	protected Window window;
	protected Window loginManager;
	public GUIDaemonClient()
	{
		super();
		init();
	}
	public void init()
	{
		
	}
	
	public abstract void open();

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

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		
		
	}
}
