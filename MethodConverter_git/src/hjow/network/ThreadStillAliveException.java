package hjow.network;

/**
 * <p>This exception is occured when the old thread is still alive.<br>
 * Try it later, or restart the program to solve this problem.</p>
 * 
 * <p>이 예외는 이전에 사용하던 쓰레드가 아직 살아 있어 새로운 연결을 만들 수 없어 발생합니다.<br>
 * 나중에 다시 시도하거나, 프로그램을 재시작하여 문제를 해결할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class ThreadStillAliveException extends Exception
{
	private static final long serialVersionUID = -6505319906725172174L;
	public ThreadStillAliveException()
	{
		super();
	}
	public ThreadStillAliveException(String message)
	{
		super(message);
	}
}
