package hjow.daemon;

import hjow.convert.module.DecryptModule;
import hjow.convert.module.EncryptModule;
import hjow.convert.module.HashModule;
import hjow.convert.module.ScriptModule;
import hjow.methodconverter.Controller;
import hjow.methodconverter.ParameterGetter;
import hjow.methodconverter.ThreadRunner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

/**
 * <p>Daemon class which can be run as a server.</p>
 * 
 * <p>서버로 동작할 수 있는 데몬 클래스입니다.</p>
 * 
 * @author HJOW
 *
 */
public class Daemon implements ThreadRunner
{
	public static final int default_port = 52102;
	
	private Thread thread;
	private boolean threadSwitch = true;
	protected ScriptModule scriptModule;
	protected int port = default_port;
	protected long term = 20;
	private String settedDefaultKey = Controller.DEFAULT_DAEMON_KEY;
	
	private ServerSocket server = null;
	private List<ClientData> clients = new Vector<ClientData>();
	private EncryptModule encryptor = new EncryptModule();
	private DecryptModule decryptor = new DecryptModule();
	
	protected List<ScriptPrivilege> prevs = new Vector<ScriptPrivilege>();
	
	
	public Daemon(ScriptModule module, String rootId, String rootPw)
	{
		super();
		this.scriptModule = module;
		ScriptPrivilege root = new ScriptPrivilege(rootId, rootPw, 10);
		prevs.add(root);
		init();
		Controller.insertThreadObject(this);
	}
	public Daemon(ScriptModule module, String rootId, String rootPw, String daemonKey)
	{
		super();
		this.scriptModule = module;
		this.scriptModule.setMasterDaemonContainerEnabled(false);
		this.settedDefaultKey = daemonKey;
		ScriptPrivilege root = new ScriptPrivilege(rootId, rootPw, 10);
		prevs.add(root);
		init();
		Controller.insertThreadObject(this);
	}
	public Daemon(ScriptModule module, int port, String rootId, String rootPw)
	{
		super();
		this.scriptModule = module;
		this.scriptModule.setMasterDaemonContainerEnabled(false);
		this.port = port;
		ScriptPrivilege root = new ScriptPrivilege(rootId, rootPw, 10);
		prevs.add(root);
		init();
		Controller.insertThreadObject(this);
	}
	public Daemon(ScriptModule module, int port, String rootId, String rootPw, String daemonKey)
	{
		super();
		this.scriptModule = module;
		this.scriptModule.setMasterDaemonContainerEnabled(false);
		this.port = port;
		this.settedDefaultKey = daemonKey;
		ScriptPrivilege root = new ScriptPrivilege(rootId, rootPw, 10);
		prevs.add(root);
		init();
		Controller.insertThreadObject(this);
	}
	
	public void init()
	{
		thread = new Thread(this);
	}
	public void insertAccount(String id, String pw, int level)
	{
		String enc_pw = "";
		HashModule hash = new HashModule();
		enc_pw = hash.encrypt(pw, "SHA-512");
		prevs.add(new ScriptPrivilege(id, enc_pw, level));
	}
	public void removeAccount(String id)
	{
		for(int i=0; i<prevs.size(); i++)
		{
			if(prevs.get(i).getId().equalsIgnoreCase(id))
			{
				prevs.remove(prevs.get(i));
				break;
			}
		}
	}
	void takeReturns(String scripts, ClientData client) throws Exception
	{
		if(client.getPrev() == null)
		{
			Map<String, String> loginData = ParameterGetter.toParameter(scripts);
			boolean isExists = false;
			for(ScriptPrivilege p : prevs)
			{
				if(p.getId().equalsIgnoreCase(loginData.get("id")) && p.getPw().equals(loginData.get("pw")))
				{
					isExists = true;
					client.send("login_success");
					client.setPrev(p);					
					break;
				}
			}
			if(! isExists)
			{				
				try
				{
					client.send("Login failed");
				}
				catch(Exception e)
				{
					
				}
				client.close();
				clients.remove(client);
			}
		}
		else if(scripts.equalsIgnoreCase("logout") || scripts.equalsIgnoreCase("exit") || scripts.equalsIgnoreCase("bye"))
		{
			client.close();
			clients.remove(client);
		}
		else
		{
			Controller.println(Controller.getString("Following scripts asked by") + " " + client.getClientIp() + "...\n" + scripts + "...end");
			try
			{
				scriptModule.insertObject("account", client.getAccountContainer());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				scriptModule.insertObject("pvs", client.getPrivilegedContainer());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				scriptModule.insertObject("controlobject", client.getPrivilegedContainer());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				scriptModule.insertObject("co", client.getPrivilegedContainer());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				scriptModule.insertObject("daemon", client.getPrivilegedContainer());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			String returnMsg = Controller.getString("Received") + "...\n" + scripts + "\n..." + Controller.getString("end");
			Controller.println(returnMsg);
			client.send(returnMsg);
			client.send(String.valueOf(eval(scripts)));			
		}
	}
	public Object eval(String scripts) throws Exception
	{
		return scriptModule.runCode(scripts);
	}
	protected void threadRun() throws Exception
	{
		if(threadSwitch)
		{
			Socket access = server.accept();
			clients.add(new ClientData(access, this));
		}
		
		int i = 0;
		while(true)
		{
			if(i >= clients.size()) break;
			if(! clients.get(i).isAlive())
			{
				Controller.print("Client", true);
				Controller.print(" " + clients.get(i).getClientIp() + " ");
				Controller.print("is not alive.", true);
				clients.get(i).close();
				clients.remove(i);
				i = 0;
			}
		}
	}
	@Override
	public void run()
	{
		while(threadSwitch)
		{
			try
			{
				threadRun();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(term);
			}
			catch(Exception e)
			{
				
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void close() throws IOException
	{
		threadSwitch = false;
		
		for(ClientData s : clients)
		{
			try
			{
				s.close();
			}
			catch(Exception e)
			{
				
			}
		}
		try
		{
			server.close();
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			encryptor.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			decryptor.close();
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			scriptModule.init();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		scriptModule = null;
		
		int limits = 0;
		while(thread != null && thread.isAlive())
		{
			limits++;
			try
			{
				Thread.sleep(100);
			}
			catch(Exception e)
			{
				
			}
			if(limits >= 100)
			{
				try
				{
					Thread.sleep(1000);
					thread.stop();
				}
				catch(Exception e)
				{
					
				}
				break;
			}
		}
		Controller.println("Daemon is closed", true);
	}

	@Override
	public boolean isAlive()
	{
		return threadSwitch;
	}

	@Override
	public void start()
	{
		try
		{
			close();
		}
		catch (Exception e)
		{
			
		}
		try
		{			
			server = new ServerSocket(port);
			threadSwitch = true;
			thread.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.println(Controller.getString("Error") + " : " + e.getMessage());
		}
	}

	@Override
	public String getThreadName()
	{
		return "ScriptDaemon";
	}

	@Override
	public long getId()
	{
		return thread.getId();
	}	
	
	String encrypt(String str, ClientData clients)
	{
		Map<String, String> params = new Hashtable<String, String>();
		if(clients.getPrev() == null) params.put("key", String.valueOf(Daemon.defaultPassword(settedDefaultKey)));
		else params.put("key", clients.getPrev().getPw());
		params.put("option", "AES");
		params.put("keypadding", "special");
		params.put("byteencode", "longcode");
		
		return encryptor.convert(str, params);
	}
	String decrypt(String str, ClientData clients)
	{
		Map<String, String> params = new Hashtable<String, String>();
		if(clients.getPrev() == null) params.put("key", String.valueOf(Daemon.defaultPassword(settedDefaultKey)));
		else params.put("key", clients.getPrev().getPw());
		params.put("option", "AES");
		params.put("keypadding", "special");
		params.put("byteencode", "longcode");
		
		return decryptor.convert(str, params);
	}
	public static String defaultPassword(String daemonKey)
	{
		String results = "";
		
		Calendar cal = Calendar.getInstance(new Locale("ko"));
		results = results + String.valueOf("93275");
		results = results + String.valueOf(cal.get(Calendar.YEAR));
		results = results + String.valueOf(cal.get(Calendar.MONTH));
		results = results + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		results = results + String.valueOf("28592");
		if(daemonKey == null)
		{
			if(Controller.getOption("defaultDaemonKey") == null) results = results + Controller.DEFAULT_DAEMON_KEY;
			else results = results + Controller.getOption("defaultDaemonKey");
		}
		else results = results + daemonKey;
		
		HashModule hash = new HashModule();
		results = hash.encrypt(results, "SHA-512");
		hash.close();
		
		return results;
	}
}

