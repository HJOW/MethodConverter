package hjow.daemon;

import hjow.convert.module.HashModule;

import java.io.Serializable;
import java.util.Random;

public class ScriptPrivilege implements Serializable
{
	private static final long serialVersionUID = 6310840568644889887L;
	private long uniqueId = new Random().nextLong();
	private String id, pw;
	private int level = 0;
	public ScriptPrivilege()
	{
		
	}
	public ScriptPrivilege(String id, String pw, int level)
	{
		super();
		this.id = id;		
		this.level = level;
		
		HashModule encryptor = new HashModule();
		this.pw = encryptor.encrypt(pw, "SHA-512");
		encryptor.close();
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getPw()
	{
		return pw;
	}
	public void setPw(String pw)
	{
		this.pw = pw;
	}
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
	public long getUniqueId()
	{
		return uniqueId;
	}
	public void setUniqueId(long uniqueId)
	{
		this.uniqueId = uniqueId;
	}
}
