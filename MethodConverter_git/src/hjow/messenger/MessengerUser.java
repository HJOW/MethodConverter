package hjow.messenger;

import java.io.Serializable;

public class MessengerUser implements Serializable
{
	private static final long serialVersionUID = 5405516247772402468L;
	private String name = "";
	private String token = "";
	public MessengerUser()
	{
		
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getToken()
	{
		return token;
	}
	public void setToken(String token)
	{
		this.token = token;
	}
}
