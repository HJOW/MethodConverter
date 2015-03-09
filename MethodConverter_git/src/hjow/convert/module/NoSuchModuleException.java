package hjow.convert.module;

/**
 * <p>This exception is occured if there is no module which has a name like.</p>
 * 
 * <p>이 예외는 해당하는 이름을 가진 모듈이 없을 때 발생합니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class NoSuchModuleException extends RuntimeException
{
	private static final long serialVersionUID = 6262207855386318815L;
	/**
	 * <p>Create new exception.</p>
	 * 
	 * <p>예외 객체를 만듭니다.</p>
	 */
	public NoSuchModuleException()
	{
		super();
	}
	
	/**
	 * <p>Create new exception.</p>
	 * 
	 * <p>예외 객체를 만듭니다.</p>
	 * 
	 * @param message : exception message
	 */
	public NoSuchModuleException(String message)
	{
		super(message);
	}
}
