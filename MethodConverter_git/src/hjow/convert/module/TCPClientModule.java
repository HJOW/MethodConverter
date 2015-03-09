package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;
import hjow.network.Communicator;
import hjow.network.DoNotConnectedException;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class TCPClientModule implements ConvertModule
{
	private static final long serialVersionUID = -8652911983036324708L;
	protected Communicator communicator;
	public TCPClientModule() throws Exception
	{
		super();
		this.communicator = new Communicator();
		Controller.addCommunicator(communicator);
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
			return "TCP Client";
		}
		else
		{
			return "TCP Client";
		}
	}

	
	/**
	 * <p>Send text.</p>
	 * 
	 * <p>텍스트를 보냅니다.</p>
	 * 
	 * @param title : Title of package : 패키지 제목
	 * @param texts : Message : 메시지
	 * @param nickname : Nickname (Can be null) : 닉네임
	 * @param ip : IP address : IP 주소
	 * @param timelimit : Time limit : 시간 제한
	 * @param statusViewer : Status viewer which show the process is alive (Can be null) : 프로세스가 살아 있음을 보여주는 객체
	 * @param statusField : Status bar which show text (Can be null) : 상태를 텍스트로 보여주는 객체
	 * @param threadTerm : Thread-sleep term 쓰레드가 대기 상태에 있는 시간, 짧으면 작업이 빨리 끝나지만 너무 짧으면 시스템이 불안정해질 수 있습니다.
	 * @return Results message 결과 메시지
	 */
	public String send(String title, String texts, String nickname, String ip, int timelimit, StatusViewer statusViewer, StatusBar statusField, long threadTerm)
	{
		return send(title, texts, nickname, ip, Controller.default_tcp_receiver_port, timelimit, statusViewer, statusField, threadTerm);
	}
	/**
	 * <p>Send text.</p>
	 * 
	 * <p>텍스트를 보냅니다.</p>
	 * 
	 * @param title : Title of package : 패키지 제목
	 * @param texts : Message : 메시지
	 * @param nickname : Nickname (Can be null) : 닉네임
	 * @param ip : IP address : IP 주소
	 * @param port : Port number : 포트 번호
	 * @param timelimit : Time limit : 시간 제한
	 * @param statusViewer : Status viewer which show the process is alive (Can be null) : 프로세스가 살아 있음을 보여주는 객체
	 * @param statusField : Status bar which show text (Can be null) : 상태를 텍스트로 보여주는 객체
	 * @param threadTerm : Thread-sleep term 쓰레드가 대기 상태에 있는 시간, 짧으면 작업이 빨리 끝나지만 너무 짧으면 시스템이 불안정해질 수 있습니다.
	 * @return Results message 결과 메시지
	 */
	public String send(String title, String texts, String nickname, String ip, int port, int timelimit, StatusViewer statusViewer, StatusBar statusField, long threadTerm)
	{
		boolean isAvailable = true;
		try
		{
			if(communicator == null) communicator = new Communicator();
			if((communicator.getPort() != port) || (communicator.getStatus() != Communicator.NET_CONNECTED))
			{
				isAvailable = false;
				communicator.close();
				communicator.prepareSocket(ip, port);
								
				int preventInfinite = 0;
				while(communicator.getStatus() == Communicator.NET_NORMAL)
				{
					preventInfinite++;
					if(statusViewer != null) statusViewer.nextStatus();
					if(preventInfinite >= timelimit) throw new DoNotConnectedException(Controller.getString("No one connect here long time."));
					try
					{
						Thread.sleep(threadTerm);
					}
					catch(Exception e)
					{
						
					}
				}
				isAvailable = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(! isAvailable)
		{
			if(statusField != null) statusField.setText(Controller.getString("Fail to send package."));
			return texts;
		}
		
		try
		{
			communicator.send(title, texts, nickname);
			try
			{
				communicator.close();
			}
			catch(Exception e)
			{
				
			}
			return Controller.getString("Success to send !");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public String defaultParameterText()
	{
		return "--title _input_title_here_ --ip _input_receivers_ip_here_ --nickname _input_your_nickname_here_";
	}
	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		if(before == null) return null;
		String target = new String(before.trim());
		//String option = parameters.get("option");
		String title = parameters.get("title");
		String ipaddress = parameters.get("ip");
		String nickname = parameters.get("nickname");
		
		if(title == null) title = parameters.get("defaultoption");
		int limitTime = Controller.default_timelimit;
		int portNumber = Controller.default_tcp_receiver_port;
		String getPortParam = parameters.get("port");
		
		try
		{
			if(getPortParam != null) portNumber = Integer.parseInt(parameters.get("port"));
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
		
		return Statics.applyScript(this, parameters, send(title, target, nickname, ipaddress, portNumber, limitTime, statusViewer, statusField, threadTerm));		
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
	public String convert(String before, Map<String, String> parameters)
	{		
		return convert(null, null, before, null, null, 20, parameters);
	}

	@Override
	public List<String> optionList()
	{
		List<String> newList = new Vector<String>();
		newList.add("Client");
		return newList;
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
	@Override
	public void close()
	{
		try
		{
			communicator.close();
		}
		catch(Exception e)
		{
			
		}
	}

	@Override
	public String getParameterHelp()
	{
		return "";
	}

	@Override
	public boolean isOptionEditable()
	{
		return false;
	}

	@Override
	public String getDefinitionName()
	{
		return "TCPClient";
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
	@Override
	public String getUrl()
	{
		return Controller.getDefaultURL();
	}
}
