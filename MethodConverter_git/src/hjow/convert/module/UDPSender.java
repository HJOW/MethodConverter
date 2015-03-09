package hjow.convert.module;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Vector;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;
import hjow.network.NetworkPackage;
import hjow.network.PortAlreadyUsedException;
import hjow.network.Sender;
import hjow.network.StringPackage;

/**
 * <p>This object send package with UDP to others.</p>
 * 
 * <p>이 객체는 패키지를 UDP를 이용해 보냅니다.</p>
 * 
 * @author HJOW
 *
 */
public class UDPSender extends Sender
{
	private static final long serialVersionUID = 3902940381092909097L;
	private DatagramSocket socket = null;
	private DatagramPacket packet = null;
	private byte[] bytes = new byte[Controller.default_udp_buffer_size];
	
	/**
	 * <p>Create sender object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @throws Exception
	 */
	public UDPSender() throws Exception
	{
		super();
	}
	
	public UDPSender(DatagramSocket socket) throws Exception
	{
		super(false);
		init(socket);
	}
	
	@Override
	public void init() throws Exception
	{
		super.init();
		super.port = getDefaultSocketPort();
		try
		{
			socket = new DatagramSocket(super.port);
			System.out.println("Sender is initialized. Using port " + super.port);
		}
		catch(java.net.BindException e)
		{
			close();
			throw new PortAlreadyUsedException("Maybe, port " + super.port + " is still using.\n" + e.getMessage(), e.getStackTrace());
		}
		catch(Exception e)
		{
			close();
			throw e;
		}
	}
	
	/**
	 * <p>Initialize sender object.</p>
	 * 
	 * <p>객체를 초기화합니다.</p>
	 * 
	 * @param socket : UDP socket
	 * @throws Exception 
	 */
	public void init(DatagramSocket socket) throws Exception
	{
		try
		{
			if(socket == null || socket.isClosed()) init();
			else
			{
				this.socket = socket;
			}			
		}
		catch(java.net.BindException e)
		{
			close();
			throw new PortAlreadyUsedException("Maybe, port " + super.port + " is still using.\n" + e.getMessage(), e.getStackTrace());
		}
		catch(Exception e)
		{
			close();
			throw e;
		}
	}
	
	@Override
	protected int getDefaultSocketPort()
	{
		return Controller.default_udp_sender_port;
	}
	
	@Override
	protected int getDefaultTargetPort()
	{
		return Controller.default_udp_receiver_port;
	}
	
	@Override
	public String getName()
	{
		return getName("en");
	}
	@Override
	public String getName(String locale)
	{
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			return "패키지 보내기 (UDP)";
		}
		else
		{
			return "Send package (UDP)";
		}
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
		if(socket == null) return null;
		if(socket.isClosed()) return null;
		return socket;
	}

	@Override
	public void send(String text, String title, String ipaddress,
			String nickname, int portNumber, int timelimit,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm)
			throws Exception
	{
		if(socket == null) init();
		if(socket.isClosed()) init();
		NetworkPackage newPackage = new StringPackage(text, title);
		newPackage.setSender();
		newPackage.setEncryptedInput(nickname);
		newPackage.resetTime();
		send(newPackage, ip, portNumber);
	}
	
	/**
	 * <p>Send package.</p>
	 * 
	 * <p>패키지를 보냅니다.</p>
	 * 
	 * @param packages : target package 보낼 패키지
	 * @param ip : IP address IP 주소
	 * @param port : Port number 포트 번호
	 * @throws Exception
	 */
	public void send(NetworkPackage packages, String ip, int port) throws Exception
	{
		if(socket == null) init();
		if(socket.isClosed()) init();
		bytes = packages.getContentBytes();			
		InetAddress address = InetAddress.getByName(ip);
		packet = new DatagramPacket(bytes, bytes.length, address, port);
		socket.send(packet);
		clearBuffer();
	}
	@Override
	public List<String> parameterKeyList()
	{
		Vector<String> keys = new Vector<String>();
		keys.add("title");
		keys.add("ip");
		keys.add("nickname");
		keys.add("port");
		keys.add("timelimit");
		return keys;
	}
	@Override
	public String getSuccessMessage()
	{
		return super.getSuccessMessage() + " " + Controller.getString("UDP protocol do not check the message is successfully arrived.");
	}
	
	/**
	 * <p>Clear buffer.</p>
	 * 
	 * <p>버퍼를 비웁니다.</p>
	 */
	public void clearBuffer()
	{
		try
		{
			for(int i=0; i<bytes.length; i++)
			{
				bytes[i] = 0;
			}
		}
		catch(Exception e)
		{
			
		}
		try
		{
			packet = new DatagramPacket(bytes, bytes.length);
		}
		catch(Exception e)
		{
			
		}
	}
	@Override
	public void close()
	{
		super.close();
		try
		{
			socket.close();
		}
		catch(Exception e)
		{
			
		}
		socket = null;
		clearBuffer();
		System.out.println("Sender is closed.");
	}

	@Override
	public boolean isOptionEditable()
	{
		return false;
	}

	@Override
	public String getDefinitionName()
	{
		return "UDPSender";
	}	
}
