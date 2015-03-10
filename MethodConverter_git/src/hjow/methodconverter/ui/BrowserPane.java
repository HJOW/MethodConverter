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
public interface BrowserPane
{
	
	/**
	 * <p>Return component object that can be added on the AWT or Swing containers.</p>
	 * 
	 * <p>AWT, Swing 컨테이너에 붙을 수 있는 컴포넌트 객체를 반환합니다.
	 * 
	 * @return component object
	 */
	public Component getComponent();
}
