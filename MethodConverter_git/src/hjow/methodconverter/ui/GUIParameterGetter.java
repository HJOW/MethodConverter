package hjow.methodconverter.ui;

import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import hjow.methodconverter.ParameterGetter;

/**
 * <p>This class help getting parameters from user by GUI.</p>
 * 
 * <p>이 클래스는 사용자로부터 GUI 형태로 매개 변수를 입력받도록 돕습니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class GUIParameterGetter extends ParameterGetter implements WindowListener, ActionListener
{
	protected Window dialog;
	
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param upper
	 */
	public GUIParameterGetter(HasParameterText upper)
	{
		super(upper);
	}

	/**
	 * <p>Refresh key list.</p>
	 * 
	 * <p>키 리스트를 새로 고칩니다.</p>
	 */
	public void refreshKeyList()
	{
		
	}
	
	/**
	 * <p>Refresh help message.</p>
	 * 
	 * <p>도움말을 새로 고칩니다.</p>
	 */
	public void refreshHelps()
	{
		
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		
		
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		close(false);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		
		
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		
		
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		
		
	} 

}
