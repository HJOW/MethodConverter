package hjow.daemon;

import hjow.convert.javamethods.AccountContainer;
import hjow.convert.javamethods.PrivilegedContainer;
import hjow.methodconverter.Controller;
import hjow.methodconverter.ThreadRunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;

public class ClientData implements ThreadRunner
{
	private Socket socket;
	private InputStream inputs;
	private OutputStream outputs;
	private InputStreamReader convertInputs;
	private OutputStreamWriter convertOutputs;
	private BufferedReader reader;
	private BufferedWriter writer;
	private Thread thread;
	private boolean threadSwitch = true;
	private long term = 20;
	private Daemon daemon = null;
	private ScriptPrivilege prev = null;
	private transient AccountContainer accountContainer = null;
	private transient PrivilegedContainer privilegedContainer = null;
	
	private long uniqueId = new Random().nextLong();
	public ClientData(Socket socket, Daemon daemon)
	{
		this.socket = socket;
		this.daemon = daemon;
		accountContainer = new AccountContainer(daemon, this);
		privilegedContainer = new PrivilegedContainer(daemon, this);
		thread = new Thread(this);
		start();
	}
	public void send(String str) throws Exception
	{
		String encrypted = daemon.encrypt(str, this);
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
	protected void threadRun() throws Exception
	{
		String gets = reader.readLine();
		if(gets == null) return;
		gets = daemon.decrypt(reader.readLine(), this);
		
		Controller.print("Get message", true);
		Controller.print(" ");
		Controller.print("from", true);
		Controller.print(" ");
		Controller.print(String.valueOf(getClientIp()));
		Controller.println("...\n" + String.valueOf(gets) + "\n..." + Controller.getString("end"));
		
		if(gets == null) return;
		daemon.takeReturns(gets, this);
	}
	@Override
	public void close()
	{
		close(true);
	}	
	@SuppressWarnings("deprecation")
	private void close(boolean nullDaemon)
	{	
		threadSwitch = false;
		prev = null;
		accountContainer.close();
		accountContainer = null;
		privilegedContainer.close();
		privilegedContainer = null;
		
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
		catch (Exception e)
		{
			
		}
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
		if(nullDaemon)
		{
			daemon = null;
		}
	}
	public Socket getSocket()
	{
		return socket;
	}
	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}
	public InputStream getInputs()
	{
		return inputs;
	}
	public void setInputs(InputStream inputs)
	{
		this.inputs = inputs;
	}
	public OutputStream getOutputs()
	{
		return outputs;
	}
	public void setOutputs(OutputStream outputs)
	{
		this.outputs = outputs;
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
	@Override
	public boolean isAlive()
	{
		return thread.isAlive();
	}
	@Override
	public void start()
	{
		try
		{
			close(false);
		}
		catch (Exception e)
		{
			
		}
		try
		{
			inputs = socket.getInputStream();
			outputs = socket.getOutputStream();
			
			convertInputs = new InputStreamReader(inputs);
			reader = new BufferedReader(convertInputs);
			
			convertOutputs = new OutputStreamWriter(outputs);
			writer = new BufferedWriter(writer);
			
			threadSwitch = true;
			thread.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				close(false);
			}
			catch (Exception e1)
			{
				
			}
		}
	}
	@Override
	public String getThreadName()
	{
		return String.valueOf(socket.getInetAddress());
	}
	@Override
	public long getId()
	{
		return thread.getId();
	}
	public String getClientIp()
	{
		return socket.getInetAddress().toString();
	}
	public ScriptPrivilege getPrev()
	{
		return prev;
	}
	public void setPrev(ScriptPrivilege prev)
	{
		this.prev = prev;
	}
	public int getLevel()
	{
		if(prev == null) return 0;
		else return prev.getLevel();
	}
	public long getUniqueId()
	{
		return uniqueId;
	}
	public void setUniqueId(long uniqueId)
	{
		this.uniqueId = uniqueId;
	}
	public AccountContainer getAccountContainer()
	{
		return accountContainer;
	}
	public void setAccountContainer(AccountContainer accountContainer)
	{
		this.accountContainer = accountContainer;
	}
	public PrivilegedContainer getPrivilegedContainer()
	{
		return privilegedContainer;
	}
	public void setPrivilegedContainer(PrivilegedContainer privilegedContainer)
	{
		this.privilegedContainer = privilegedContainer;
	}
}
