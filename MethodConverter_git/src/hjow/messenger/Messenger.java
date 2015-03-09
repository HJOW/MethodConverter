package hjow.messenger;

import hjow.methodconverter.Controller;
import hjow.methodconverter.swingconverter.SwingMessengerInstance;

/**
 * <p>This object run messenger.</p>
 * 
 * <p>이 객체는 메신저를 실행합니다.</p>
 * 
 * @author HJOW
 *
 */
public class Messenger
{
	private MessengerInstance instances;
	public void runMain(boolean gui)
	{
		if(gui)
		{
			instances = new SwingMessengerInstance();
		}
		else
		{
			instances = new MessengerInstance();
		}
		Controller.insertThreadObject(instances);
		instances.start();
	}
}
