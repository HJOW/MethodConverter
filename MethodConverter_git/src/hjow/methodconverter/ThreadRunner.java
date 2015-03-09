package hjow.methodconverter;

import java.io.Closeable;

/**
 * <p>Thread object will implements this.</p>
 * 
 * <p>쓰레드 객체들이 이 인터페이스를 구현하게 될 것입니다.</p>
 * 
 * @author HJOW
 *
 */
public interface ThreadRunner extends Runnable, Closeable
{
	/**
	 * <p>Return true if this thread is alive.</p>
	 * 
	 * <p>이 쓰레드가 살아 있는지의 여부를 반환합니다.</p>
	 * 
	 * @return true if this thread is alive
	 */
	public boolean isAlive();
	/**
	 * <p>Run thread.</p>
	 * 
	 * <p>쓰레드를 실행합니다.</p>
	 */
	public void start();
	
	/**
	 * <p>Return thread name.</p>
	 * 
	 * <p>쓰레드 이름을 반환합니다.</p>
	 * 
	 * @return name
	 */
	public String getThreadName();
	
	/**
	 * <p>Return ID.</p>
	 * 
	 * <p>ID 값을 반환합니다.</p>
	 * 
	 * @return id
	 */
	public long getId();
}
