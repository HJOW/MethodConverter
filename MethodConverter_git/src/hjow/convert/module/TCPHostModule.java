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

public class TCPHostModule implements ConvertModule
{
	private static final long serialVersionUID = -8652911983036324708L;
	private Communicator communicator;
	public TCPHostModule() throws Exception
	{
		super();
		this.communicator = new Communicator();
		Controller.addCommunicator(communicator);
		communicator.prepareServer(5001);
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
			return "TCP Host";
		}
		else
		{
			return "TCP Host";
		}
	}

	@Override
	public String defaultParameterText()
	{
		return "--title _input_title_here_ --nickname _input_your_nickname_here_";
	}
	@Override
	public List<String> parameterKeyList()
	{
		Vector<String> keys = new Vector<String>();
		keys.add("title");
		keys.add("port");
		keys.add("timelimit");
		return keys;
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
		if(title == null) title = parameters.get("defaultoption");
		int limitTime = Controller.default_timelimit;
		boolean isAvailable = true;
		
		try
		{
			int portNumber = Controller.default_tcp_receiver_port;
			if(parameters.get("port") != null) portNumber = Integer.parseInt(parameters.get("port"));
			try
			{
				limitTime = Integer.parseInt(parameters.get("timelimit"));
			}
			catch(Exception e)
			{
				limitTime = Controller.default_timelimit;
			}
			if(communicator.getPort() != portNumber)
			{
				isAvailable = false;
				communicator.close();
				communicator.prepareServer(portNumber);
				
				int preventInfinite = 0;
				while(communicator.getStatus() == Communicator.NET_SERVER_WAITING)
				{
					preventInfinite++;
					if(statusViewer != null) statusViewer.nextStatus();
					if(preventInfinite >= limitTime) throw new DoNotConnectedException(Controller.getString("No one connect here long time."));
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
			return before;
		}
		
		try
		{
			communicator.send(title, target);
			return Statics.applyScript(this, parameters, Controller.getString("Success to send !"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return before;
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
		newList.add("Host");
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
		return "TCPHost";
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
