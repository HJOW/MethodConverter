package hjow.methodconverter.ui;

import java.awt.Component;

/**
 * <p>The object which implements this interface will be able to browse the web.</p>
 * 
 * <p>이 인터페이스를 구현하는 객체는 웹을 탐색할 수 있습니다.</p>
 * 
 * @author eduncom_cyk
 *
 */
public interface CanBeSurfWeb
{
	
	/**
	 * <p>Return component object that can be added on the AWT or Swing containers.</p>
	 * 
	 * <p>AWT, Swing 컨테이너에 붙을 수 있는 컴포넌트 객체를 반환합니다.
	 * 
	 * @return component object
	 */
	public Component getComponent();
	
	/**
	 * <p>Load page and show.</p>
	 * 
	 * <p>페이지를 불러오고 보입니다.</p>
	 * 
	 * @param url : URL
	 * @exception Exception
	 */
	public void goPage(String url) throws Exception;
	
	/**
	 * <p>Go forward.</p>
	 * 
	 * <p>앞으로 이동합니다.</p>
	 * 
	 * @exception Exception
	 */
	public void goForward() throws Exception;
	
	/**
	 * <p>Go back.</p>
	 * 
	 * <p>뒤로 이동합니다.</p>
	 * 
	 * @exception Exception
	 */
	public void goBack() throws Exception;
}
