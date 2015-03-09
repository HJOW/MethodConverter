package hjow.daemon;

import hjow.methodconverter.Controller;

/**
 * <p>This exception will be occured when the client try to run script which has to need high privilege.</p>
 * 
 * <p>이 예외는 어떤 사용자가 높은 권한이 필요한 스크립트를 실행하려 할 때 발생합니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class NotAllowedException extends SecurityException
{
	private static final long serialVersionUID = -198235236404902490L;

	public NotAllowedException()
	{
		super(Controller.getString("You don't have enough privilege"));
	}
	public NotAllowedException(String details)
	{
		super(Controller.getString("You don't have enough privilege") + " : " + details);
	}
	public NotAllowedException(String msg, String details)
	{
		super(Controller.getString(msg) + " : " + details);
	}
}
