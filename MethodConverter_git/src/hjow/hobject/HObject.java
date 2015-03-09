package hjow.hobject;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

/**
 * <p>Many classes and interfaces in hjow.hobject package are not completed and not used in MethodConverter.</p>
 * 
 * <p>hjow.hobject 패키지 내의 클래스와 인터페이스는 아직 완성되지 않았으며 MethodConverter 에 사용되지 않았습니다.</p>
 * 
 * 
 * 
 * <p>This class instance can includes several properties of the object.</p>
 * 
 * <p>간단히 직렬화할 수 있는 클래스 객체들의 정보를 담을 수 있는 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public class HObject extends HInstance
{
	private static final long serialVersionUID = -9064093898438880098L;
	private Map<String, HInstance> property;
	
	public HObject()
	{
		property = new Hashtable<String, HInstance>();
	}
	
	public HObject(String serialized) throws Exception
	{
		try
		{
			property = new Hashtable<String, HInstance>();
			Stack<Integer> blackets = new Stack<Integer>();
			boolean quotes = false;
			char target;
			StringBuffer accumulator = null;
			String key = null;
			boolean keyMode = true;
			int otherObjectInside = -1;
			int otherArrayInside = -1;
			for(int i=0; i<serialized.length(); i++)
			{			
				target = serialized.charAt(i);

				System.out.println("Consisting at " + i + " : " + "key " + String.valueOf(key) + ", value " + String.valueOf(accumulator).replace("\n", "") + " : " + target);
				
				if(quotes)
				{
					if(target == '"')
					{
						quotes = false;
						keyMode = true;
						if(key == null) key = "null";
						property.put(key, new HText(String.valueOf(accumulator)));
						key = null;
					}
					else
					{
						if(keyMode)
						{
							if(key == null) key = "";
							key = key + String.valueOf(target);
						}
						else
						{
							if(accumulator == null) accumulator = new StringBuffer("");
							accumulator = accumulator.append(String.valueOf(target));
						}
					}
				}
				else
				{
					if(Statics.isAlphabet(target) || Statics.isNumber(target))
					{
						if(keyMode)
						{
							if(key == null) key = "";
							key = key + String.valueOf(target);
						}
						else
						{
							if(accumulator == null) accumulator = new StringBuffer("");
							accumulator = accumulator.append(String.valueOf(target));
						}
					}
					else if(Statics.isSpace(target))
					{
						if(accumulator == null) accumulator = new StringBuffer("");
						accumulator = accumulator.append(String.valueOf(target));
					}
					else if(Statics.isBlacket(target))
					{
						if(target == '(')
						{
							blackets.push(new Integer(1));
						}
						else if(target == ')')
						{
							if(blackets.peek().intValue() == 1)
							{
								blackets.pop();
							}
							else
							{
								throw new Exception(Controller.getString("Syntax") + " " + Controller.getString("Error")
										+ " : " + Controller.getString("Different blacket type"));
							}
						}
						else if(target == '{')
						{
							if(otherObjectInside >= 1)
							{
								if(accumulator == null) accumulator = new StringBuffer("");
								accumulator = accumulator.append(String.valueOf(target));
								otherObjectInside++;
							}
							else if(otherObjectInside == 0)
							{
								blackets.push(new Integer(2));
								accumulator = new StringBuffer("{");
								otherObjectInside++;
							}
							else
							{
								blackets.push(new Integer(2));
								accumulator = null;
								otherObjectInside++;
							}
						}
						else if(target == '}')
						{
							if(blackets.peek().intValue() == 2)
							{						
								otherObjectInside--;
								blackets.pop();
								
								if(otherObjectInside >= 0)
								{
									if(accumulator == null) accumulator = new StringBuffer("");
									accumulator = accumulator.append(String.valueOf(target));
									if(otherObjectInside <= 0)
									{
										HObject createOne = new HObject(accumulator.toString());
										property.put(key, createOne);
									}
								}
								else
								{
									break;
								}
								key = null;
								keyMode = true;
								accumulator = null;
							}					
							else
							{
								throw new Exception(Controller.getString("Syntax") + " " + Controller.getString("Error")
										+ " : " + Controller.getString("Different blacket type"));
							}
						}
						else if(target == '[')
						{
							if(otherArrayInside >= 1)
							{
								if(accumulator == null) accumulator = new StringBuffer("");
								accumulator = accumulator.append(String.valueOf(target));
								otherArrayInside++;
							}
							else if(otherArrayInside == 0)
							{
								blackets.push(new Integer(3));
								accumulator = new StringBuffer("{");
								otherArrayInside++;
							}
							else
							{
								blackets.push(new Integer(3));
								accumulator = null;
								otherArrayInside++;
							}
						}
						else if(target == ']')
						{
							if(blackets.peek().intValue() == 3)
							{						
								otherArrayInside--;
								blackets.pop();
								
								if(otherArrayInside >= 0)
								{
									if(accumulator == null) accumulator = new StringBuffer("");
									accumulator = accumulator.append(String.valueOf(target));
									if(otherArrayInside <= 0)
									{
										property.put(key, new HObject(accumulator.toString()));
									}
								}
								else
								{
									break;
								}
								key = null;
								keyMode = true;
								accumulator = null;
							}					
							else
							{
								throw new Exception(Controller.getString("Syntax") + " " + Controller.getString("Error")
										+ " : " + Controller.getString("Different blacket type"));
							}
						}
					}
					else if(target == ':')
					{
						keyMode = false;
						if(key == null) key = "null";
					}
					else if(target == ',')
					{
						keyMode = true;
						if(key == null) key = "null";
						property.put(key, new HText(String.valueOf(accumulator)));
						key = null;
					}
					else if(target == '"')
					{
						quotes = true;
					}
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Map<String, HInstance> getProperty()
	{
		return property;
	}

	public void setProperty(Map<String, HInstance> property)
	{
		this.property = property;
	}
	
	public HInstance get(String key)
	{
		return property.get(key);
	}
	
	public void put(String key, HInstance o)
	{
		property.put(key, o);
	}

	@Override
	public String serialize()
	{
		if(property == null) return "{}\n";
		StringBuffer results = new StringBuffer("");
		results = results.append("{\n");
		Set<String> keys = property.keySet();
		List<String> keyList = new Vector<String>();
		keyList.addAll(keys);
		for(int i=0; i<keyList.size(); i++)
		{
			results = results.append("  " + keyList.get(i) + ":");
			results = results.append(property.get(keyList.get(i)).serialize());
			if(i < keyList.size() - 1) results = results.append(",");
			results = results.append("\n");
		}
		results = results.append("}");
		return results.toString();
	}
}
