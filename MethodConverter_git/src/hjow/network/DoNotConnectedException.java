package hjow.network;

/**
 * 
 * <p>This exception is occured when the communicator object try to send package without any connection.</p>
 * 
 * <p>이 예외는 연결이 되지 않은 상태에서 패키지를 보내려 할 때 발생합니다.</p>
 * 
 * @author HJOW
 *
 */
public class DoNotConnectedException extends Exception
{
	private static final long serialVersionUID = -9177503200742091815L;
	public DoNotConnectedException()
	{
		super();
	}
	public DoNotConnectedException(String message)
	{
		super(message);
	}
}
