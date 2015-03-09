package hjow.methodconverter.ui;

import java.awt.Component;

/**
 * <p>Objects which is implement this interface will be used to show status as text on screen.</p>
 * 
 * <p>이 인터페이스를 구현한 객체는 화면에 상태를 텍스트로 보이는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public interface StatusBar
{
	/**
	 * <p>Show text status on screen</p>
	 * 
	 * <p>텍스트를 화면에 출력합니다. </p>
	 * 
	 * @param str text 텍스트
	 */
	public void setText(String str);
	/**
	 * <p>Clean text</p>
	 * 
	 * <p>텍스트를 비웁니다.</p>
	 */
	public void clear();
	/**
	 * <p>Return self.</p>
	 * 
	 * <p>자기 자신을 GUI 컴포넌트로 반환합니다.</p>
	 * 
	 * @return Component 컴포넌트
	 */
	public Component toComponent();
}