package hjow.messenger;

import java.util.HashMap;

/**
 * <p>This class object manage messenger services.</p>
 * 
 * <p>이 클래스 객체는 메신저 서비스들을 관리합니다.</p>
 * 
 * @author HJOW
 *
 */
public class MessengerInstance extends Thread
{
	private boolean threadSwitch = true;
	protected int terms = 20;
	protected MessengerProperties props;
	/**
	 * <p>Create new property object and initialize.</p>
	 * 
	 * <p>속성 객체를 생성하고 초기화 메소드를 호출합니다.</p>
	 */
	public MessengerInstance()
	{
		props = new MessengerProperties(new HashMap<String, String>());
		init();
	}
	/**
	 * <p>Initialize.</p>
	 * 
	 * <p>초기화하는 메소드입니다.</p>
	 */
	public void init()
	{
		
	}
	/**
	 * <p>This method will be run on thread.</p>
	 * 
	 * <p>이 메소드는 쓰레드에서 실행됩니다.</p>
	 */
	public void act()
	{
		
	}
	/**
	 * <p>Stop thread.</p>
	 * 
	 * <p>쓰레드를 닫습니다.</p>
	 */
	public void close()
	{
		threadSwitch = false;
	}
	@Override
	public void run()
	{
		while(threadSwitch)
		{
			try
			{
				act();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(terms);
			}
			catch(Exception e)
			{
				
			}
		}
	}
}
