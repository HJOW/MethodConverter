package hjow.convert.javamethods;

import hjow.daemon.ClientData;
import hjow.daemon.Daemon;
import hjow.daemon.NotAllowedException;

/**
 * <p>This object is used in script. In script, you can use this object named account.<br>
 *    Only work on the daemon console.</p>
 *  
 * 
 * <p>이 객체는 스크립트 내에서 사용됩니다. account 라는 이름으로 사용할 수 있습니다.<br>
 *    오직 데몬 상에서에서만 사용할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class AccountContainer
{
	private Daemon daemon;
	private ClientData allocated;
	
	public AccountContainer(Daemon daemon, ClientData allocated)
	{
		this.daemon = daemon;
		this.allocated = allocated;
	}
	public void insertAccount(String id, String pw, int level) throws Exception
	{
		if(allocated.getLevel() >= 9)
		{
			daemon.insertAccount(id, pw, level);
		}
		else
		{
			throw new NotAllowedException("You don't have enough privilege", "Create Account");
		}
	}
	public void removeAccount(String id)
	{		
		if(allocated.getLevel() >= 9)
		{
			daemon.removeAccount(id);
		}
		else
		{
			throw new NotAllowedException("You don't have enough privilege", "Remove Account");
		}
	}
	public void close()
	{
		daemon = null;
		allocated = null;
	}
}