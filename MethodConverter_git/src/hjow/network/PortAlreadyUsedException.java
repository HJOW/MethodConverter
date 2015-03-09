package hjow.network;

import java.net.BindException;

/**
 * <p>This exception is occured when the port is still using by another process.</p>
 * 
 * <p>이 예외는 다른 프로세스가 이미 포트를 사용하고 있어 충돌이 발생했을 때 발생합니다.</p>
 * 
 * @author HJOW
 *
 */
public class PortAlreadyUsedException extends BindException
{
	private static final long serialVersionUID = -5799526720288983854L;
	
	/**
	 * <p>Create exception.</p>
	 * 
	 * <p>예외를 생성합니다.</p>
	 */
	public PortAlreadyUsedException()
	{
		super();
	}
	/**
	 * <p>Create exception.</p>
	 * 
	 * <p>예외를 생성합니다.</p>
	 * 
	 * @param message : Exception message
	 */
	public PortAlreadyUsedException(String message)
	{
		super(message);
	}
	/**
	 * <p>Create exception.</p>
	 * 
	 * <p>예외를 생성합니다.</p>
	 * 
	 * @param message : Exception message
	 * @param stackTrace : Stack trace data
	 */
	public PortAlreadyUsedException(String message, StackTraceElement[] stackTrace)
	{
		super(message);
		setStackTrace(stackTrace);
	}
}
