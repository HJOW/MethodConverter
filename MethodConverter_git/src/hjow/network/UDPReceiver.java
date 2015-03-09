package hjow.network;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ThreadRunner;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * <p>Receive packages with UDP.</p>
 * 
 * <p>UDP 프로토콜로 패키지를 받는 클래스입니다.</p>
 * 
 * @author HJOW
 *
 */
public class UDPReceiver extends Receiver implements ThreadRunner
{
	private long id = ((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long)(Math.random() * (Long.MAX_VALUE / 100000)));
	private volatile boolean threadRun = true;
	private volatile DatagramSocket udpSocket = null;
	private byte[] buffers = new byte[Controller.default_udp_buffer_size];
	private DatagramPacket packet = null;
	
	/**
	 * <p>Create UDP Receiver object.</p>
	 * 
	 * <p>UDP 수신 객체를 만듭니다.</p>
	 * 
	 * @throws Exception
	 */
	public UDPReceiver() throws Exception
	{
		init(getDefaultSocketPort());
	}	
	/**
	 * <p>Create UDP Receiver object.</p>
	 * 
	 * <p>UDP 수신 객체를 만듭니다.</p>
	 * 
	 * @param port : Port number
	 * @throws Exception
	 */
	public UDPReceiver(int port) throws Exception
	{
		init(port);
	}
	/**
	 * <p>Create UDP Receiver object.</p>
	 * 
	 * <p>UDP 수신 객체를 만듭니다.</p>
	 * 
	 * @param port : Port number
	 * @param bufferSize : Buffer size
	 * @throws Exception
	 */
	public UDPReceiver(int port, int bufferSize) throws Exception
	{
		buffers = new byte[bufferSize];
		init(port);
	}
	
	/**
	 * <p>Initialize receiver.</p>
	 * 
	 * <p>수신 객체를 초기화합니다.</p>
	 * 
	 * @param port : port number
	 * @param socket : UDP socket
	 */
	public void init(int port, DatagramSocket socket) throws Exception
	{
		if(socket == null) init(port);
		else
		{
			udpSocket = socket;
			threadRun = true;
			Thread createThread = new Thread(this);
			Controller.insertThreadObject(createThread);
			Controller.insertThreadObject(this);
			createThread.start();
			Controller.println("Receiver is initialized. Using port " + port);
		}
	}
	
	@Override
	public void init(int port) throws Exception
	{
		super.init(port);
		try
		{
			udpSocket = new DatagramSocket(port);
			threadRun = true;
			Thread createThread = new Thread(this);
			Controller.insertThreadObject(createThread);
			Controller.insertThreadObject(this);
			createThread.start();
			Controller.println("Receiver is initialized. Using port " + port);
		}
		catch(Exception e)
		{
			close();
			throw e;
		}
	}
	/**
	 * <p>Clear buffer.</p>
	 * 
	 * <p>버퍼를 비웁니다.</p>
	 */
	public void clearBuffer()
	{
		for(int i=0; i<buffers.length; i++)
		{
			buffers[i] = 0;
		}
		packet = new DatagramPacket(buffers, buffers.length);
	}
	@Override
	public synchronized boolean isAlive()
	{
		if(udpSocket == null) return false;
		else if(! threadRun) return false;
		else return (! udpSocket.isClosed());
	}
	
	@Override
	protected int getDefaultSocketPort()
	{
		return Controller.default_udp_receiver_port;
	}
	
	/**
	 * <p>Return socket if it is exist and it is alive.</p>
	 * 
	 * <p>UDP 소켓이 존재하고 살아 있으면 소켓 객체를 반환합니다.</p>
	 * 
	 * @return udp socket
	 */
	public DatagramSocket getSocketIfExist()
	{
		if(udpSocket == null) return null;
		if(udpSocket.isClosed()) return null;
		return udpSocket;
	}
	
	@Override
	public synchronized void close()
	{	
		super.close();
		threadRun = false;
		try
		{
			udpSocket.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			clearBuffer();
		}
		catch(Exception e)
		{
			
		}	
		Controller.println("Receiver is closed.");
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
					if(udpSocket.isClosed()) close();
					if(isAlive())
					{
						clearBuffer();
						udpSocket.receive(packet);
						if(packet.getData() != null && packet.getData().length >= 2)
						{
							NetworkPackage received = NetworkPackage.toPackage(packet.getData());
							if(received != null)
							{
								receive(received);
								if(! (received instanceof SuccessReceivedPackage))
								{
									try
									{
										byte[] successfullyReceivedMessage = new SuccessReceivedPackage(received.getPackId()).getContentBytes();
										udpSocket.send(new DatagramPacket(successfullyReceivedMessage, successfullyReceivedMessage.length
												, InetAddress.getByName(received.getSender()), Controller.default_udp_receiver_port));
									}
									catch(Exception e)
									{
										
									}
								}
							}
						}
						clearBuffer();
					}
					else break;
				}
				catch(java.net.SocketException e)
				{
					if(e.getMessage().equalsIgnoreCase("socket closed"))
					{
						Controller.println("Receiver will be closed.");
						close();
					}
					else e.printStackTrace();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				Thread.sleep(15 + (int)(Math.random() * 80));
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
			init(Controller.default_udp_receiver_port);
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
