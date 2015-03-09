package hjow.network;
import hjow.methodconverter.Controller;
import hjow.methodconverter.ThreadRunner;
import hjow.methodconverter.ui.StatusViewer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * <p>This object can help you for communication with each other as 1:1.</p>
 * 
 * <p>이 객체는 다른 이와 1:1 통신할 때 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class Communicator implements ThreadRunner
{
	private long id = ((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long)(Math.random() * (Long.MAX_VALUE / 100000)));
	private String target = null;
	private int port = Controller.default_tcp_receiver_port;
	private volatile Socket socket = null;
	private volatile ServerSocket server = null;
	private volatile int status = NET_NORMAL;
	private InputStream inputStream = null;
	private ObjectInputStream objectInputStream = null;
	private BufferedInputStream bufferedInputStream = null;
	private OutputStream outputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	private BufferedOutputStream bufferedOutputStream = null;
	
	private StatusViewer statusViewer = null;
	
	private List<NetworkPackage> queues = new Vector<NetworkPackage>();
	private boolean threadRun = false;
	private volatile boolean threadStatus = false;
	private volatile boolean threadWorking = false;
	private long threadTerm = 50;
	
	/**
	 * <p>This status value means no one connected with this.</p>
	 * 
	 * <p>이 상태는 아무와도 접속되지 않았음을 나타냅니다.</p>
	 */
	public static final int NET_NORMAL = 0;
	
	/**
	 * <p>This status value means you are trying connect with other as client.</p>
	 * 
	 * <p>이 상태는 누군가와 클라이언트로서 접속 시도 중임을 나타냅니다.</p>
	 */
	public static final int NET_TRY_CONNECT = 1;
	
	/**
	 * <p>This status value means you are connected with other as client.</p>
	 * 
	 * <p>이 상태는 누군가와 클라이언트로서 접속되었음을 나타냅니다.</p>
	 */
	public static final int NET_CONNECTED = 2;
	
	/**
	 * <p>This status value means you are waiting as server.</p>
	 * 
	 * <p>이 상태는 서버로서 다른 이의 접속을 대기하고 있음을 나타냅니다.</p>
	 */
	public static final int NET_SERVER_WAITING = 3;
	
	/**
	 * <p>This status value means you are allow other's connection.</p>
	 * 
	 * <p>이 상태는 누군가와 서버로서 접속 허가되었음을 나타냅니다.</p>
	 */
	public static final int NET_SERVER_ACCEPTED = 4;
	
	/**
	 * <p>This status value means you are connected with other as server.</p>
	 * 
	 * <p>이 상태는 누군가와 서버로서 접속되었음을 나타냅니다.</p>
	 */
	public static final int NET_SERVER_CONNECTED = 5;
	
	/**
	 * <p>Create Communicator object.</p>
	 * 
	 * <p>Communicator 객체를 만듭니다.</p>
	 */
	public Communicator()
	{
		
	}
	/**
	 * <p>Return this object status.</p>
	 * 
	 * <p>이 객체의 현재 상태를 반환합니다.</p>
	 * 
	 * <p>Constants</p>
	 * 
	 * <p>
	 * 0 : normal status<br>
	 * 1 : connected as client<br>
	 * 2 : waiting as server<br>
	 * 3 : connected as server
	 * </p>
	 * 
	 * @return now status as constant
	 */
	public synchronized int getStatus()
	{
		return status;
	}
	
	/**
	 * <p>Send package</p>
	 * 
	 * <p>패키지를 보냅니다.</p>
	 * 
	 * @param title : title of message
	 * @param message : message, can be String object
	 * @throws Exception
	 */
	public void send(String title, Serializable message) throws Exception
	{
		send(title, message, null);
	}
	/**
	 * <p>Send package</p>
	 * 
	 * <p>패키지를 보냅니다.</p>
	 * 
	 * @param title : title of message
	 * @param message : message, can be String object
	 * @param nickname : nickname
	 * @throws Exception
	 */
	public void send(String title, Serializable message, String nickname) throws Exception
	{
		if(getStatus() == NET_NORMAL || getStatus() == NET_SERVER_WAITING)
		{
			throw new DoNotConnectedException("Please connect first to send package.");
		}
		NetworkPackage newPackage;
		if(message instanceof String) newPackage = new StringPackage((String) message, title);
		else newPackage = new ObjectPackage(message, title);
		if(nickname != null) newPackage.setEncryptedInput(nickname);
		sendPackage(newPackage);
	}
	
	/**
	 * <p>Take a package from the queue.</p>
	 * 
	 * <p>전송받아 대기열에 있는 패키지 하나를 꺼냅니다.</p>
	 * 
	 * @return package
	 */
	public NetworkPackage receive()
	{		
		if(getStatus() == NET_CONNECTED || getStatus() == NET_SERVER_CONNECTED)
		{
			int preventUnlimited = 0;
			while(threadWorking)
			{
				if(! threadWorking) break;
				preventUnlimited++;
				if(preventUnlimited >= 10000) break;
				try
				{
					Thread.sleep(threadTerm);
				}
				catch(Exception e)
				{
					
				}
			}
			if(queues.size() <= 0) return null;
			NetworkPackage gets = queues.get(0);
			queues.remove(0);
			return gets;
		}
		return null;
	}
	/**
	 * <p>Return how many packages on the queue.</p>
	 * 
	 * <p>대기열에 패키지가 얼마나 쌓여 있는지를 반환합니다.</p>
	 * 
	 * @return package count on the queue
	 */
	public int getReceivedPackageCount()
	{
		return queues.size();
	}
	
	/**
	 * <p>Send package</p>
	 * 
	 * <p>패키지를 보냅니다.</p>
	 * 
	 * @param ob : package object
	 * @throws Exception
	 */
	public void sendPackage(NetworkPackage ob) throws Exception
	{
		if(getStatus() == NET_NORMAL || getStatus() == NET_SERVER_WAITING) throw new DoNotConnectedException("Please connect first to send package.");
		ob.setSender(InetAddress.getLocalHost().toString());
		objectOutputStream.writeObject(ob.serialize());
	}
	
	/**
	 * <p>Accept request of someone to connect.<br>
	 * This method is automatically, and repeatly called when the server is prepared.</p>
	 * 
	 * <p>서버로서 접속 요청을 받아들여 연결을 성사시킵니다.<br>
	 * 서버가 준비되면 쓰레드에서 자동으로 주기적으로 호출됩니다.</p> 
	 * 
	 * @throws Exception
	 */
	public void accept() throws Exception
	{		
		socket = server.accept();
		status = NET_SERVER_ACCEPTED;
		prepareStream();
	}
	
	/**
	 * <p>Prepare to make connection as server.</p>
	 * 
	 * <p>서버로서 연결을 준비합니다.</p>
	 * 
	 * @param port : port number, this should be unique.
	 * @throws Exception
	 */
	public void prepareServer(int port) throws Exception
	{
		close();
		this.port = port;
		this.target = null;
		server = new ServerSocket(port);
		status = NET_SERVER_WAITING;
	}
	
	/**
	 * <p>Prepare to make connection as client.</p>
	 * 
	 * <p>클라이언트로서 연결을 준비합니다.</p>
	 * 
	 * @param target : IP address
	 * @param port : port number
	 * @throws Exception
	 */
	public void prepareSocket(String target, int port) throws Exception
	{
		close();
		this.target = target;
		this.port = port;
		socket = new Socket(target, port);
		status = NET_TRY_CONNECT;
		prepareStream();
	}
	
	/**
	 * <p>Prepare IO streams from sockets.</p>
	 * 
	 * <p>IO 스트림을 준비합니다.</p>
	 * 
	 * @throws Exception
	 */
	private void prepareStream() throws Exception
	{
		if(threadStatus)
		{
			throw new ThreadStillAliveException("Old thread is still alive. Please try later.");
		}
		
		inputStream = socket.getInputStream();
		bufferedInputStream = new BufferedInputStream(inputStream);
		objectInputStream = new ObjectInputStream(bufferedInputStream);
		
		outputStream = socket.getOutputStream();
		bufferedOutputStream = new BufferedOutputStream(outputStream);
		objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
		
		try
		{
			queues.clear();
		}
		catch(Exception e)
		{
			
		}
		if(status == NET_SERVER_ACCEPTED)
		{
			status = NET_SERVER_CONNECTED;
		}
		else if(status == NET_TRY_CONNECT)
		{
			status = NET_CONNECTED;
		}
		threadStatus = true;
		threadRun = true;
		
		Thread createThread = new Thread(this);
		Controller.insertThreadObject(createThread);
		createThread.start();
	}
	
	/**
	 * <p>Return target IP address.</p>
	 * 
	 * <p>상대의 IP 주소를 반환합니다.</p>
	 * 
	 * @return IP address
	 */
	public String getTarget()
	{
		return target;
	}
	
	/**
	 * <p>Return target port number at client mode.<br>
	 * Return server port number at server mode.
	 * </p>
	 * 
	 * <p>클라이언트인 경우 상대의 포트 번호를 반환합니다.<br>
	 * 서버인 경우 현재 서버 대기 포트 번호를 반환합니다.</p>
	 * 
	 * @return
	 */
	public int getPort()
	{
		return port;
	}
	
	/**
	 * <p>Set status viewer object which is used to show the process is alive.</p>
	 * 
	 * <p>프로세스가 살아있음을 보이는 데 사용되는 객체를 설정합니다.</p>
	 * 
	 * @param viewer : status viewer object
	 */
	public void setStatusViewer(StatusViewer viewer)
	{
		this.statusViewer = viewer;
	}
	
	/**
	 * <p>Close all connections and streams.</p>
	 * 
	 * <p>이 객체에 대한 접속과 스트림을 모두 닫습니다.</p>
	 */
	public void close()
	{
		try
		{
			threadRun = false;
		}
		catch(Exception e)
		{
			
		}
		try
		{
			sendPackage(new ClosePackage());
		}
		catch(Exception e)
		{
			
		}
		try
		{
			objectInputStream.close();
		}
		catch(Exception e)
		{
			Controller.println("InputStream close message : " + e.getMessage());
		}
		try
		{
			objectOutputStream.close();
		}
		catch(Exception e)
		{
			Controller.println("OutputStream close message : " + e.getMessage());
		}
		try
		{
			bufferedInputStream.close();
		}
		catch(Exception e)
		{
			Controller.println("InputStream close message : " + e.getMessage());
		}
		try
		{
			bufferedOutputStream.close();
		}
		catch(Exception e)
		{
			Controller.println("OutputStream close message : " + e.getMessage());
		}
		try
		{
			inputStream.close();
		}
		catch(Exception e)
		{
			Controller.println("InputStream close message : " + e.getMessage());
		}
		try
		{
			outputStream.close();
		}
		catch(Exception e)
		{
			Controller.println("OutputStream close message : " + e.getMessage());
		}
		try
		{
			socket.close();
		}
		catch(Exception e)
		{
			Controller.println("Network close message : " + e.getMessage());
		}
		try
		{
			server.close();
		}
		catch(Exception e)
		{
			Controller.println("Server close message : " + e.getMessage());
		}
		server = null;
		socket = null;
		outputStream = null;
		inputStream = null;
		bufferedInputStream = null;
		bufferedOutputStream = null;
		objectInputStream = null;
		objectOutputStream = null;		
		status = NET_NORMAL;
	}
	@Override
	public void run()
	{
		while(threadRun)
		{
			if(statusViewer != null) statusViewer.nextStatus();
			if(getStatus() == NET_CONNECTED || getStatus() == NET_SERVER_CONNECTED)
			{
				try
				{
					threadWorking = true;					
					NetworkPackage takes = NetworkPackage.toPackage((String) objectInputStream.readObject());
					if(takes instanceof ClosePackage)
					{
						close();
						break;
					}
					else
					{
						queues.add(takes);
						Collections.sort(queues);
					}
					threadWorking = false;
						
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(getStatus() == NET_SERVER_WAITING)
			{
				try
				{
					threadWorking = true;
					accept();
					threadWorking = false;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			try
			{
				Thread.sleep(threadTerm);
			}
			catch(Exception e)
			{
				
			}
		}		
		threadStatus = false;
		threadWorking = false;
	}
	@Override
	public boolean isAlive()
	{
		return threadRun;
	}
	@Override
	public void start()
	{
		try
		{
			prepareStream();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public String getThreadName()
	{
		return this.getClass().getCanonicalName();
	}
	@Override
	public long getId()
	{
		return id;
	}	
}
