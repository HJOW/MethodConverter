package hjow.messenger;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Hashtable;
import java.util.Vector;

public class MessengerProperties implements Map<String, String>, Serializable
{
	private static final long serialVersionUID = -627261778632515880L;
	protected Map<String, String> maps;
	protected List<MessengerUser> users = new Vector<MessengerUser>();
	
	public MessengerProperties()
	{
		maps = new Hashtable<String, String>();
	}
	public MessengerProperties(Map<String, String> preDatas)
	{
		maps = preDatas;
	}
	
	@Override
	public void clear()
	{
		maps.clear();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return maps.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return maps.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet()
	{
		return maps.entrySet();
	}

	@Override
	public String get(Object key)
	{
		return maps.get(key);
	}

	@Override
	public boolean isEmpty()
	{
		return maps.isEmpty();
	}

	@Override
	public Set<String> keySet()
	{
		return maps.keySet();
	}

	@Override
	public String put(String key, String value)
	{
		return maps.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m)
	{
		maps.putAll(m);
	}

	@Override
	public String remove(Object key)
	{
		return maps.remove(key);
	}

	@Override
	public int size()
	{
		return maps.size();
	}

	@Override
	public Collection<String> values()
	{
		return maps.values();
	}
	public Map<String, String> getMaps()
	{
		return maps;
	}
	public void setMaps(Map<String, String> maps)
	{
		this.maps = maps;
	}
	public List<MessengerUser> getUsers()
	{
		return users;
	}
	public void setUsers(List<MessengerUser> users)
	{
		this.users = users;
	}	
}
