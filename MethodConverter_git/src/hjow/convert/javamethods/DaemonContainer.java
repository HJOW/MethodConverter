package hjow.convert.javamethods;

import hjow.convert.module.ScriptModule;
import hjow.daemon.Daemon;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;

/**
 * <p>This object is used in script. In script, you can use this object named daemon.<br>
 *    Only work on the user-input-script console.</p>
 * 
 * <p>This class can be used as a library.</p>
 *  
 * 
 * <p>이 객체는 스크립트 내에서 사용됩니다. daemon 라는 이름으로 사용할 수 있습니다.<br>
 *    오직 사용자가 직접 입력하는 스크립트에서만 사용할 수 있습니다.</p>
 * 
 * <p>라이브러리로 사용할 수도 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class DaemonContainer
{
	private boolean guiBased = false;
	private Daemon daemon;
	
	public DaemonContainer(boolean guiBased)
	{
		this.guiBased = guiBased;
	}
	public void daemon(String rootId, String rootPw, String port, String loginKey) throws Exception
	{
		String getLoginKey = loginKey;
		if(loginKey == null) getLoginKey = Controller.DEFAULT_DAEMON_KEY;
		try
		{
			if(! Controller.requestYes(Controller.getString("Other user who know ID and password will be able to access here") 
					+ ".\n" + Controller.getString("Do you want to run daemon?")))
			{
				return;
			}
			if(daemon != null) daemon.close();
			if(port == null) daemon = new Daemon(new ScriptModule(false), rootId, rootPw, getLoginKey);
			else
			{
				try
				{
					daemon = new Daemon(new ScriptModule(false), Integer.parseInt(port), rootId, rootPw, getLoginKey);
				}
				catch (NumberFormatException e)
				{
					daemon = new Daemon(new ScriptModule(false), rootId, rootPw, getLoginKey);
				}
			}
			daemon.start();
			Controller.println("Daemon is started", true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.println(Statics.fullErrorMessage(e));
		}
	}
	public void stopDaemon()
	{
		try
		{
			daemon.close();
		}
		catch(Exception e)
		{
			Controller.println(Statics.fullErrorMessage(e));
		}
	}
	public void access()
	{
		Controller.runDaemonClient(guiBased);
	}
}
