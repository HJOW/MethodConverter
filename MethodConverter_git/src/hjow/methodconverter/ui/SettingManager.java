package hjow.methodconverter.ui;

import hjow.methodconverter.Controller;
import hjow.methodconverter.StringTable;

import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * <p>Setting manager class</p>
 * 
 * <p>설정 매니저 클래스입니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public abstract class SettingManager implements ActionListener, WindowListener 
{
	protected Window window;
	
	/**
	 * <p>Create setting manager.
	 * Also, after the object is created by this constructor, init() method will be called.</p>
	 * 
	 * <p>설정 매니저 객체를 생성합니다.</p>
	 * 
	 * @param window : Window object, such as Frame, JFrame, Dialog, JDialog
	 */
	public SettingManager(Window window)
	{
		this.window = window;
		init();
	}
	
	/**
	 * <p>Initialize object.
	 * It is good idea to override this method instead of constructor.</p>
	 * 
	 * <p>객체를 초기화합니다.
	 * 생성자 대신 이 메소드를 오버라이드해 사용하는 것이 좋습니다.</p>
	 */
	protected abstract void init();
	
	/**
	 * <p>Open setting manager.</p>
	 * 
	 * <p>설정 매니저 창을 엽니다.</p>
	 */
	public abstract void open();
	
	/**
	 * <p>Close setting manager.</p>
	 * 
	 * <p>설정 매니저 창을 닫습니다.</p>
	 */
	public abstract void close();
	
	/**
	 * <p>This method is called when user click Save button.</p>
	 * 
	 * <p>이 메소드는 사용자가 저장 버튼을 클릭했을 때 실행됩니다.</p>
	 */
	public abstract void save();
	
	/**
	 * <p>Reset components.</p>
	 * 
	 * <p>컴포넌트들을 재설정합니다.
	 * 현재 적용되어 있는 옵션에 따라 컴포넌트의 선택 상태를 변경합니다.</p>
	 */
	public abstract void reset();
	
	/**
	 * <p>Apply string table value on components.</p>
	 * 
	 * <p>스트링 테이블을 컴포넌트에 적용합니다.</p>
	 */
	public abstract void setLanguage();
	
	/**
	 * <p>Apply string table value on components.</p>
	 * 
	 * <p>스트링 테이블을 컴포넌트에 적용합니다.</p>
	 * 
	 * @param stringTable : String table
	 */
	public void setLanguage(StringTable stringTable)
	{
		Controller.setLanguage(stringTable);
		setLanguage();
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
}
