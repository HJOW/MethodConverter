package hjow.convert.module;

import hjow.daemon.Daemon;
import hjow.methodconverter.Controller;
import hjow.methodconverter.ParameterGetter;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ThreadRunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * <p>This class object can access daemon server on the internet.</p>
 * 
 * <p>이 클래스 객체는 인터넷 상의 다른 데몬 서버에 접근할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class DaemonClient implements ThreadRunner
{
	private Thread thread;
	private boolean threadSwitch = true;
	private long term = 20;
	private EncryptModule encryptor = new EncryptModule();
	private DecryptModule decryptor = new DecryptModule();
	private HashModule hash = new HashModule();
	private Socket socket = null;
	private InputStream inputs;
	private OutputStream outputs;
	private InputStreamReader convertInputs;
	private OutputStreamWriter convertOutputs;
	private BufferedReader reader;
	private BufferedWriter writer;
	protected int login_timeout = 0;
	protected boolean logined = false;
	private String settedDefaultKey = Controller.DEFAULT_DAEMON_KEY;
	private String id = "root", pw = "";
	private String ip = "127.0.0.1";
	private int port = Daemon.default_port;
	protected static int default_login_timeout = 1000;
	
	public DaemonClient()
	{
		Controller.insertThreadObject(this);
		thread = new Thread(this);
	}
	public void connect(String id, String pw, String ip, int port) throws Exception
	{
		connect(id, pw, ip, port, null);
	}
	public void connect(String id, String pw, String ip, int port, String daemonKey) throws Exception
	{
		if(logined)
		{
			try
			{
				close();
			}
			catch (Exception e)
			{
				
			}
		}
		if(daemonKey != null) this.settedDefaultKey = daemonKey;
		this.id = id;				
		this.pw = hash.encrypt(pw, "SHA-512");
		this.ip = ip;
		this.port = port;
		start();
	}
	protected void threadRun() throws Exception
	{		
		String gets = reader.readLine();
		login_refresh();
		
		if(gets == null) return;
		gets = decrypt(gets);
		
		Controller.print("Get message", true);
		Controller.println("...\n" + String.valueOf(gets) + "\n..." + Controller.getString("end"));
		
		if(! threadSwitch) return;
		if(gets == null) return;
				
		else if(gets.equalsIgnoreCase("bye") || gets.equalsIgnoreCase("exit") || gets.equalsIgnoreCase("logout"))
		{
			close();
		}
		else if(gets.equalsIgnoreCase("login_success"))
		{
			logined = true;
			login_timeout = 0;
		}
		else
		{
			takeResult(gets);
		}
	}
	protected void login_refresh() throws Exception
	{		
		if((! logined) && (login_timeout >= 1))
		{
			login_timeout--;
		}
		else if((! logined) && (login_timeout <= 0))
		{
			Controller.print("Login fail", true);
			Controller.print(" : ");
			Controller.println("timeout", true);
			close();
		}
	}
	protected void takeResult(String gets)
	{
		Controller.println(Controller.getString("From server") + " : " + gets);
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
		try
		{
			send("bye");
		}
		catch(Exception e)
		{
			
		}
		try
		{
			writer.close();
		}
		catch (Exception e)
		{
			
		}
		try
		{
			reader.close();
		}
		catch (Exception e)
		{
			
		}
		try
		{
			convertInputs.close();
		}
		catch (Exception e)
		{
			
		}
		try
		{
			convertOutputs.close();
		}
		catch (Exception e)
		{
			
		}
		try
		{
			inputs.close();
		}
		catch (Exception e)
		{
			
		}
		try
		{
			outputs.close();
		}
		catch (Exception e)
		{
			
		}
		try
		{
			socket.close();
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
			hash.close();
		}
		catch(Exception e)
		{
			
		}
		
		int limits = 0;
		while(thread != null && thread.isAlive())
		{
			limits++;
			try
			{
				Thread.sleep(10);
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
			socket = new Socket(ip, port);
			inputs = socket.getInputStream();
			outputs = socket.getOutputStream();
			convertInputs = new InputStreamReader(inputs);
			convertOutputs = new OutputStreamWriter(outputs);
			reader = new BufferedReader(convertInputs);
			writer = new BufferedWriter(convertOutputs);
			
			Map<String, String> loginData = new Hashtable<String, String>();
			loginData.put("id", id);
			loginData.put("pw", pw);
			
			String sends = ParameterGetter.toParameterString(loginData);
			
			login_timeout = default_login_timeout;
			logined = false;
			send(sends);
			
			threadSwitch = true;
			thread.start();
			
			System.out.println("started");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.println(Statics.fullErrorMessage(e));
			try
			{
				close();
			}
			catch (Exception e1)
			{
				
			}
		}
	}
	public void send(String str) throws Exception
	{
		String encrypted = encrypt(str);
		StringTokenizer lineToken = new StringTokenizer(encrypted, "\n");
		Controller.print("Try to send", true);
		Controller.println("...\n" + str + "\n..." + Controller.getString("end"));
		Controller.print("Encrypted", true);
		Controller.println("...\n" + encrypted + "\n..." + Controller.getString("end"));
		while(lineToken.hasMoreTokens())
		{
			writer.write(lineToken.nextToken());
		}
	}
	
	private String encrypt(String str)
	{
		Map<String, String> params = new Hashtable<String, String>();
		if(logined) params.put("key", pw);
		else params.put("key", Daemon.defaultPassword(settedDefaultKey));
		params.put("option", "AES");
		params.put("keypadding", "special");
		params.put("byteencode", "longcode");
		
		return encryptor.convert(str, params);
	}
	private String decrypt(String str)
	{
		Map<String, String> params = new Hashtable<String, String>();
		if(logined) params.put("key", pw);
		else params.put("key", Daemon.defaultPassword(settedDefaultKey));
		params.put("option", "AES");
		params.put("keypadding", "special");
		params.put("byteencode", "longcode");
		
		return decryptor.convert(str, params);
	}

	@Override
	public String getThreadName()
	{
		return String.valueOf(ip) + " " + Controller.getString("Access");
	}

	@Override
	public long getId()
	{
		return thread.getId();
	}
	
	protected void println(Object str)
	{
		Controller.println(String.valueOf(str));
	}
	protected void print(Object str)
	{
		Controller.print(String.valueOf(str));
	}
}
