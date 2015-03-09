package hjow.methodconverter.ui;

import java.awt.Component;

/**
 * 
 * <p>Interface implemented by the status object that will be used to show that the process is alive.</p>
 * 
 * <p>이 인터페이스를 구현한 객체는 화면에 프로세스가 살아 있는지 여부를 보이는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public interface StatusViewer
{
	/**
	 * <p>Close thread which is used to change status value.</p>
	 * 
	 * <p>상태 값을 변화시키는 쓰레드를 닫습니다.</p>
	 */
	public void close();
	/**
	 * <p>Set status value.
	 * This change will be applied at screen.</p>
	 * 
	 * <p>현재 상태 값을 변경합니다.
	 * 실제 화면에 보이는 값이 변경됩니다.</p>
	 * 
	 * @param v status value 상태 값
	 */
	public void setValue(int v);
	/**
	 * <p>Increase status value</p>
	 * 
	 * <p>상태 값을 증가시킵니다.</p>
	 */
	public void nextStatus();
	/**
	 * <p>Return self.</p>
	 * 
	 * <p>자기 자신을 반환합니다.</p>
	 * 
	 * @return Component 컴포넌트
	 */
	public Component toComponent();
	/**
	 * <p>Reset status</p>
	 * 
	 * <p>상태를 초기화합니다.</p>
	 */
	public void reset();
}
