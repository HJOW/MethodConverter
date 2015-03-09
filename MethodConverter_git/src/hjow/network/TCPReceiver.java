package hjow.network;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ThreadRunner;

/**
 * <p>Default package-receive with TCP class</p>
 * 
 * <p>TCP로 패키지를 받는 클래스</p>
 * 
 * @author HJOW
 *
 */
public class TCPReceiver extends Receiver implements ThreadRunner
{
	private long id = ((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long)(Math.random() * (Long.MAX_VALUE / 100000)));
	private ServerSocket servers = null;
	private boolean threadRun = true;
	private List<SocketStream> streams = new Vector<SocketStream>();
	
	/**
	 * <p>Create new receiver object.</p>
	 * 
	 * <p>패키지를 받는 객체를 생성합니다.</p>
	 * 
	 * @throws Exception
	 */
	public TCPReceiver() throws Exception
	{
		init(getDefaultSocketPort());
	}
	
	@Override
	public boolean isAlive()
	{
		return (servers != null);
	}
	
	@Override
	public void init(int port) throws Exception
	{
		super.init(port);
		try
		{
			String ports = Controller.getOption("port");
			if(ports == null) ports = String.valueOf(getDefaultSocketPort());
			Controller.println("Initializing TCP package receiver, port : " + ports, true);
			servers = new ServerSocket(Integer.parseInt(ports));
			threadRun = true;
			Thread createThread = new Thread(this);
			Controller.insertThreadObject(createThread);
			Controller.insertThreadObject(this);
			createThread.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			close();
		}
	}
	
	@Override
	protected int getDefaultSocketPort()
	{
		return Controller.default_tcp_receiver_port;
	}
	
	@Override
	public void close()
	{
		super.close();
		threadRun = false;
		try
		{
			for(int i=0; i<streams.size(); i++)
			{
				try
				{
					streams.get(i).send(new ClosePackage());
					streams.get(i).close();
				}
				catch(Exception e)
				{
					
				}
			}
			streams.clear();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			servers.close();
		}
		catch(Exception e)
		{
			
		}
		servers = null;
	}
	
	@Override
	public void run()
	{
		while(threadRun)
		{
			try
			{
				int index = 0;
				while(true)
				{
					if(index >= streams.size())
					{
						break;
					}
					if(streams.get(index).isAlive())
					{
						index++;					
					}
					else
					{
						streams.get(index).close();
						streams.remove(index);
						index = 0;
					}
				}
			}
			catch(Exception e)
			{
				
			}
			try
			{
				if(servers == null)
				{
					
				}
				else
				{
					if(! servers.isClosed())
					{
						try
						{
							Socket newSocket = servers.accept();
							Controller.println("Socket " + String.valueOf(newSocket.getInetAddress()) + " is accepted.");
							SocketStream newSockStream = new SocketStream(newSocket);
							Controller.println("SocketStream " + String.valueOf(newSockStream) + " is added on the list.");
							streams.add(newSockStream);
						}
						catch(java.net.SocketException e1)
						{
							
						}
					}					
				}
			}			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
			try
			{
				Thread.sleep(100 + (int)(Math.random() * 5));
			}
			catch(Exception e)
			{
				
			}
		}
	}

	@Override
	public void start()
	{
		try
		{
			init(getDefaultSocketPort());
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
/**
 * <p>SocketStream which is connected to clients as 1:1.</p>
 * 
 * <p>이 소켓 스트림은 클라이언트와 객체 하나 당 1 클라이언트씩 1:1 매칭됩니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
class SocketStream implements ThreadRunner
{
	private Socket socket;
	private InputStream inputStream;
	private ObjectInputStream objectInput;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutput;
	private boolean threadRun = true;
	private long id = (long) Math.random() * (Long.MAX_VALUE / 2);
	public SocketStream(Socket socket) throws Exception
	{
		this.socket = socket;
		init();
	}
	/**
	 * <p>Initialize and active this object.</p>
	 * 
	 * <p>초기화 및 활성화합니다.</p>
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception
	{
		Controller.println("Receiver socket stream for " + String.valueOf(socket.getInetAddress()) + " will be ready..");
		inputStream = socket.getInputStream();
		System.out.println("1");
		outputStream = socket.getOutputStream();
		System.out.println("2");
		objectInput = new ObjectInputStream(inputStream);
		System.out.println("3");
		objectOutput = new ObjectOutputStream(outputStream);
		Controller.println("Receiver socket stream for " + String.valueOf(socket.getInetAddress()) + " is initialized.");
		start();
		Controller.println("Receiver socket stream for " + String.valueOf(socket.getInetAddress()) + " is working.");
	}
	/**
	 * <p>Matched client IP address.</p>
	 * 
	 * <p>클라이언트의 IP 주소를 알아 냅니다.</p>
	 * 
	 * @return ip
	 */
	public String getTargetIP()
	{
		return socket.getInetAddress().toString();
	}
	/**
	 * <p>Receive the package.</p>
	 * 
	 * <p>패키지를 전송받습니다.</p>
	 * 
	 * @return received package
	 * @throws Exception
	 */
	public NetworkPackage receive() throws Exception
	{
		return (NetworkPackage) objectInput.readObject();
	}
	/**
	 * <p>Send the package.</p>
	 * 
	 * <p>패키지를 보냅니다.</p>
	 * 
	 * @param pack : package
	 * @throws Exception
	 */
	public void send(NetworkPackage pack) throws Exception
	{
		objectOutput.writeObject(pack);
	}
	
	/**
	 * <p>Disable this object and close all streams in this object.</p>
	 * 
	 * <p>이 객체를 비활성화하고, 이 객체에 열려 있는 스트림을 모두 닫습니다.</p>
	 */
	public void close()
	{
		threadRun = false;
		try
		{
			objectInput.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			objectOutput.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			inputStream.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			outputStream.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			socket.close();
		}
		catch(Exception e)
		{
			
		}
		Controller.println("Receiver is closed.");
	}
	/**
	 * <p>Return true if this thread is alive.</p>
	 * 
	 * <p>이 쓰레드가 살아 있는지 여부를 반환합니다.</p>
	 * 
	 * @return true if this thread is alive
	 */
	public boolean isAlive()
	{
		return threadRun;
	}
	@Override
	public void run()
	{
		while(threadRun)
		{			
			try
			{
				try
				{					
					//NetworkPackage newPack = (NetworkPackage) objectInput.readObject();
					Object getObject = objectInput.readObject();
					NetworkPackage newPack;
					try
					{
						if(getObject instanceof String)
						{
							newPack = NetworkPackage.toPackage((String) getObject);
						}
						else if(getObject instanceof NetworkPackage)
						{
							newPack = (NetworkPackage) getObject;
						}
						else
						{
							newPack = NetworkPackage.toPackage(String.valueOf(getObject));
						}
					}
					catch (Exception e)
					{
						newPack = NetworkPackage.toPackage(String.valueOf(getObject));
					}
					if(newPack instanceof ClosePackage)
					{
						close();
						Controller.println("Socket " + String.valueOf(socket.getInetAddress()) + " will be disconnected.");
					}
					else Receiver.receive(newPack);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				Thread.sleep(20 + ((int)(Math.random() * 90)));
			}
			catch(Exception e)
			{
				
			}
		}		
	}
	@Override
	public void start()
	{
		threadRun = true;
		Thread newThread = new Thread(this);
		if(! (Controller.existInList(newThread))) Controller.insertThreadObject(newThread);		
		Controller.insertThreadObject(this);
		newThread.start();
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

