package hjow.network;

import hjow.convert.module.ConvertModule;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <p>This object can send package to other user.</p>
 * 
 * <p>이 객체는 패키지를 다른 사용자에게 보내는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class Sender implements ConvertModule
{	
	private static final long serialVersionUID = -4928715395612240033L;
	protected String ip = "127.0.0.1";
	protected int port = Controller.default_tcp_sender_port;
	
	/**
	 * <p>Create sender object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @throws Exception
	 */
	public Sender() throws Exception
	{
		init();
	}
	
	/**
	 * <p>Create sender object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param callInit : If this is true, call init() method after the object is created.
	 * @throws Exception
	 */
	public Sender(boolean callInit) throws Exception
	{
		if(callInit) init();
	}
	/**
	 * <p>Initialize sender object.</p>
	 * 
	 * <p>객체를 초기화합니다.</p>
	 * 
	 * @throws Exception 
	 */
	public void init() throws Exception
	{
		close();
		Controller.println("Sender is initializing.");
	}
	/**
	 * <p>Return name if this object is used as module.</p>
	 * 
	 * <p>이 객체가 모듈로 사용되는 것을 위하여 존재하는 메소드입니다.</p>
	 * 
	 * @param locale : System locale setting
	 * @return name of module
	 */
	@Override
	public String getName(String locale)
	{
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			return "패키지 보내기";
		}
		else
		{
			return "Send package";
		}
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
		return Controller.default_tcp_sender_port;
	}
	
	/**
	 * <p>Return default target port number.</p>
	 * 
	 * <p>보낼 대상의 포트 번호 기본값을 반환합니다.</p>
	 * 
	 * @return default target port number
	 */
	protected int getDefaultTargetPort()
	{
		return Controller.default_tcp_receiver_port;
	}
	
	/**
	 * <p>Send package. This method is exist because object can be used as module.</p>
	 * 
	 * <p>패키지를 보냅니다. 이 객체가 모듈로 사용되는 것을 위하여 존재하는 메소드입니다.</p>
	 */
	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		if(before == null) return null;
		String target = new String(before.trim());
		String title = parameters.get("title");
		String ipaddress = parameters.get("ip");
		String nickname = parameters.get("nickname");		
		
		if(nickname == null) nickname = parameters.get("defaultoption");
		if(title == null) title = parameters.get("defaultoption");
		int limitTime = Controller.default_timelimit;
		int portNumber = Controller.default_tcp_receiver_port;
		
		try
		{
			String portGet = parameters.get("port");
			try
			{
				if(portGet != null) portNumber = Integer.parseInt(parameters.get("port"));
				else portNumber = getDefaultTargetPort();
			}
			catch(Exception e)
			{
				portNumber = getDefaultTargetPort();
			}
			try
			{
				limitTime = Integer.parseInt(parameters.get("timelimit"));
			}
			catch(Exception e)
			{
				limitTime = Controller.default_timelimit;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			send(target, title, ipaddress, nickname, portNumber, limitTime, statusViewer, statusField, threadTerm);
			return Statics.applyScript(this, parameters, getSuccessMessage());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			Controller.print("Error", true);
			Controller.println(" : " + e1.getMessage());
			return before;
		}		
	}
	
	/**
	 * <p>Return success-message.</p>
	 * 
	 * <p>성공 메시지를 반환합니다.</p>
	 * 
	 * @return success message
	 */
	public String getSuccessMessage()
	{
		return Controller.getString("Success to send !");
	}
	
	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(Controller.getStringTable(), null, before, null, null, 20, parameters);
	}
	
	@Override
	public List<String> optionList()
	{
		List<String> newList = new Vector<String>();
		newList.add("Send");
		return newList;
	}
	
	@Override
	public String defaultParameterText()
	{
		return "--title \"_input_title_here_\" --ip \"127.0.0.1\" --nickname \"_input_your_nickname_here_\"";
	}
	@Override
	public boolean isAuthorized()
	{
		return true;
	}
	@Override
	public boolean isAuthCode(String input_auths)
	{
		return true;
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
	 * @throws Exception
	 */
	public abstract void send(String text, String title, String ipaddress, String nickname, int portNumber, int timelimit, StatusViewer statusViewer, StatusBar statusField, long threadTerm) throws Exception;
	
	/**
	 * <p>Close sender.</p>
	 * 
	 * <p>보내는 네트워크를 닫습니다.</p>
	 */
	@Override
	public void close()
	{
		Controller.println("Sender is closing.");
	}
	
	@Override
	public String getParameterHelp()
	{
		// port, title, ip, nickname
		String results = "";
		String locale = Controller.getSystemLocale();
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			results = results + "사용 가능한 키 : title, nickname, ip, port" + "\n\n";
			results = results + "title : 패키지 제목" + "\n\n";
			results = results + "nickname : 닉네임, 전송될 때에는 닉네임 대신 " + Controller.getNicknameEncoding() + " 으로 변환된 해시값이 전송됩니다." + "\n\n";
			results = results + "ip : 상대방 IP 주소" + "\n\n";
			results = results + "port : 상대방 Port 번호, 반드시 입력할 필요는 없습니다." + "\n\n";
		}
		else
		{
			results = results + "Available keys : title, nickname, ip, port" + "\n\n";
			results = results + "title : Title of package." + "\n\n";
			results = results + "nickname : Your nickname, this will be converted with " + Controller.getNicknameEncoding() + " algorithm." + "\n\n";
			results = results + "ip : Target IP address." + "\n\n";
			results = results + "port : Target port number, this is not necessary parameter." + "\n\n";
		}
		return results;
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
}
