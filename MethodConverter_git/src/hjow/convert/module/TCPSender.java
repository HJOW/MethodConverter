package hjow.convert.module;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;
import hjow.network.NetworkPackage;
import hjow.network.Sender;
import hjow.network.StringPackage;

/**
 * <p>This object can send package with TCP to other user.</p>
 * 
 * <p>이 객체는 패키지를 TCP 를 통해 다른 사용자에게 보내는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class TCPSender extends Sender
{
	private static final long serialVersionUID = -4858113360484168410L;
	private Socket socket;
	private String connectedIP;
	private int connectedPort = getDefaultTargetPort();
	private OutputStream output;
	private ObjectOutputStream objectStream;

	/**
	 * <p>Create sender object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @throws Exception
	 */
	public TCPSender() throws Exception
	{
		super();
	}
	@Override
	public void init() throws Exception
	{
		super.init();
		socket = null;
		System.out.println("Sender is initialized.");
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
			return "패키지 보내기 (TCP)";
		}
		else
		{
			return "Send package (TCP)";
		}
	}
	@Override
	public void send(String text, String title, String ipaddress, String nickname, int portNumber, int timelimit, StatusViewer statusViewer, StatusBar statusField, long threadTerm) throws Exception
	{
		send(text, title, ipaddress, nickname, portNumber, timelimit, statusViewer, statusField, threadTerm, true);
	}
	
	/**
	 * <p>Send package.</p>
	 * 
	 * <p>패키지를 보냅니다.</p>
	 * 
	 * @param text : Text 보낼 텍스트
	 * @param title : Title of package 제목
	 * @param ipaddress : Target IP address 받는 이 IP 주소
	 * @param nickname : Nickname 닉네임
	 * @param portNumber : Port number 포트 번호
	 * @param timelimit : Time limit 시간 제한
	 * @param statusViewer : StatusViewer object 상태 변화 객체 (프로세스가 살아 있음을 알릴 때 사용) Can be null
	 * @param statusField : StatusBar object 상태바 객체 (상태를 텍스트로 출력할 때 사용) Can be null
	 * @param threadTerm : Thread-sleep term 쓰레드가 대기 상태에 있는 시간, 짧으면 작업이 빨리 끝나지만 너무 짧으면 시스템이 불안정해질 수 있습니다.
	 * @param serializePackage : If this is true, package will be serialized before sended 이 항목이 true이면 패키지가 보내지기 전 텍스트로 직렬화됩니다.
	 * @throws Exception
	 */
	public void send(String text, String title, String ipaddress, String nickname, int portNumber, int timelimit, StatusViewer statusViewer, StatusBar statusField, long threadTerm, boolean serializePackage) throws Exception
	{
		NetworkPackage newPackage;
		newPackage = new StringPackage(text, title);
		if(nickname != null) newPackage.setEncryptedInput(nickname);
		
		send(newPackage, ipaddress, portNumber, serializePackage);
	}
	
	/**
	 * <p>Send package.</p>
	 * 
	 * <p>패키지를 보냅니다.</p>
	 * 
	 * @param pack : Package object 패키지 객체
	 * @param ipaddress : Target IP address 받는 이 IP 주소
	 * @param serializePackage : If this is true, package will be serialized before sended 이 항목이 true이면 패키지가 보내지기 전 텍스트로 직렬화됩니다.
	 * @throws Exception
	 */
	public void send(NetworkPackage pack, String ipaddress, boolean serializePackage) throws Exception
	{
		send(pack, ipaddress, Controller.default_tcp_sender_port, serializePackage);
	}
	
	/**
	 * <p>Send package.</p>
	 * 
	 * <p>패키지를 보냅니다.</p>
	 * 
	 * @param pack : Package object 패키지 객체
	 * @param ipaddress : Target IP address 받는 이 IP 주소
	 * @param portNumber : Port number 포트 번호
	 * @param serializePackage : If this is true, package will be serialized before sended 이 항목이 true이면 패키지가 보내지기 전 텍스트로 직렬화됩니다.
	 * @throws Exception
	 */
	public void send(NetworkPackage pack, String ipaddress, int portNumber, boolean serializePackage) throws Exception
	{
		if(socket == null)
		{
			socket = new Socket(ipaddress, portNumber);
			this.connectedIP = ipaddress;
			this.connectedPort = portNumber;
			output = socket.getOutputStream();
			objectStream = new ObjectOutputStream(output);
		}
		else if(! (connectedIP.equals(ipaddress) && connectedPort == portNumber))
		{
			close();
			socket = new Socket(ipaddress, portNumber);
			this.connectedIP = ipaddress;
			this.connectedPort = portNumber;
			output = socket.getOutputStream();
			objectStream = new ObjectOutputStream(output);
		}
		if(serializePackage) objectStream.writeObject(pack.serialize());
		else objectStream.writeObject(pack);
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
	public void close()
	{
		super.close();
		try
		{
			objectStream.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			output.close();
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
		Controller.println("Sender is closed.");
	}
	@Override
	public boolean isOptionEditable()
	{
		return false;
	}
	@Override
	public String getDefinitionName()
	{
		return "TCPSender";
	}
}
