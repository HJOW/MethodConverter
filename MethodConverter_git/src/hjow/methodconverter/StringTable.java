package hjow.methodconverter;

import hjow.network.SerializableObject;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * <p>String table object.</p>
 * 
 * <p>스트링 테이블 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class StringTable implements Map<String, String>, SerializableObject
{
	private static final long serialVersionUID = -255883006445216444L;
	protected Map<String, String> table;
	
	/**
	 * <p>Return name of language as two letters.</p>
	 * 
	 * <p>언어 이름을 두 글자로 반환합니다.</p>
	 * 
	 * @return name of language
	 */
	public String getName()
	{
		return table.get("localename");
	}
	
	/**
	 * <p>Return name of language.</p>
	 * 
	 * <p>언어 이름을 반환합니다.</p>
	 * 
	 * @return name of language
	 */
	public String getFormalName()
	{
		return table.get("localename_formal");
	}
	@Override
	public void clear()
	{
		table.clear();
	}
	@Override
	public boolean containsKey(Object key)
	{
		return table.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value)
	{
		return table.containsValue(value);
	}
	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet()
	{
		return table.entrySet();
	}
	@Override
	public String get(Object key)
	{
		return table.get(key);
	}
	@Override
	public boolean isEmpty()
	{
		return table.isEmpty();
	}
	@Override
	public Set<String> keySet()
	{
		return table.keySet();
	}
	@Override
	public String put(String key, String value)
	{
		return table.put(key, value);
	}
	@Override
	public void putAll(Map<? extends String, ? extends String> m)
	{
		table.putAll(m);		
	}
	@Override
	public String remove(Object key)
	{
		return table.remove(key);
	}
	@Override
	public int size()
	{
		return table.size();
	}
	@Override
	public Collection<String> values()
	{
		return table.values();
	}
	@Override
	public String serialize()
	{
		return Statics.hashtableToString(table);
	}
}
