package hjow.methodconverter.ui;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * <p>About dialog class.</p>
 * 
 * <p>이 프로그램은... 대화 상자 클래스입니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class AboutDialog implements WindowListener, ActionListener, MouseListener
{
	protected Window window;
	protected SerialDialog serialDialog;
	protected Color serialColor;
	public AboutDialog(Object superWindow)
	{
		init(superWindow);
	}
	public abstract void init(Object superWindow);
	public void open()
	{
		window.setVisible(true);
	}
	public void close()
	{
		window.setVisible(false);
	}
	public abstract void setCloseText(String closeText);
	public abstract void setTitleText(String titles);
	public abstract void setVersionText(String versionText);
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
	public void mouseClicked(MouseEvent e)
	{
		
		
	}	
	@Override
	public void mouseExited(MouseEvent e)
	{
		
		
	}
	@Override
	public void mousePressed(MouseEvent e)
	{
		
		
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{
		
		
	}
	@Override
	public void mouseEntered(MouseEvent e)
	{
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		
		
	}
}
