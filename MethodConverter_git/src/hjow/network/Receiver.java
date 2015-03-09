package hjow.network;

import hjow.methodconverter.Controller;

/**
 * <p>Default package-receive class</p>
 * 
 * <p>패키지를 받는 클래스</p>
 * 
 * @author HJOW
 *
 */
public abstract class Receiver
{
	/**
	 * <p>Close receiver object.</p>
	 * 
	 * <p>수신 객체를 닫습니다.</p>
	 */
	public void close()
	{
		Controller.println("Receiver is closing.");
	}
	
	/**
	 * <p>Initialize receiver.</p>
	 * 
	 * <p>수신 객체를 초기화합니다.</p>
	 * 
	 * @param port : port number
	 */
	public void init(int port) throws Exception
	{
		close();
		Controller.println("Receiver is initializing. Using port " + port);
	}
	
	/**
	 * <p>Return default socket port number.</p>
	 * 
	 * <p>소켓 포트 번호 기본값을 반환합니다.</p>
	 * 
	 * @return default socket port number
	 */
	protected int getDefaultSocketPort()
	{
		return Controller.default_tcp_receiver_port;
	}
	
	/**
	 * <p>Return true if the receiver thread is alive.</p>
	 * 
	 * <p>수신 쓰레드가 살아있는지 여부를 반환합니다.</p>
	 * 
	 * @return true if the receiver thread is alive
	 */
	public abstract boolean isAlive();
	/**
	 * 
	 * <p>Receive package and add on the default list.</p>
	 * 
	 * <p>패키지를 받아 기본 리스트에 넣습니다.</p>
	 * 
	 * @param pack : package
	 */
	public static void receive(NetworkPackage pack)
	{
		if(pack instanceof ModulePackage)
		{
			if(Controller.requestYes(Controller.getString("Do you want to use received module?") 
					+ " " + Controller.getString("Name") + " : " 
					+ ((ModulePackage) pack).getModule().getName())) Controller.insertModule(((ModulePackage) pack).getModule());
			return;
		}
		Controller.insertPackage(pack);
		Controller.refreshAll();
	}
}
