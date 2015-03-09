package hjow.methodconverter;

import hjow.methodconverter.ui.HasParameterText;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <p>This class help getting parameters from user.</p>
 * 
 * <p>이 클래스는 사용자로부터 매개 변수를 입력받도록 돕습니다.</p>
 * 
 * @author HJOW
 *
 */
public class ParameterGetter
{
	protected HasParameterText upper;
	
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param upper
	 */
	public ParameterGetter(HasParameterText upper)
	{
		this.upper = upper;
	}
	/**
	 * <p>Open the object.</p>
	 * 
	 * <p>객체를 엽니다.</p>
	 */
	public void open()
	{
		
	}
	/**
	 * <p>Close the object.</p>
	 * 
	 * <p>객체를 닫습니다.</p>
	 * 
	 * @param closeFinaly : If this is true, set null to cut circular reference.
	 */
	public void close(boolean closeFinaly)
	{
		if(closeFinaly) upper = null;
	}
	
	/**
	 * <p>Close the object.</p>
	 * 
	 * <p>객체를 닫습니다.</p>
	 * 
	 */
	public void close()
	{
		close(false);
	}
	
	/**
	 * <p>Convert Hashtable into text which is formed as --key value.</p>
	 * 
	 * <p>Hashtable 를 텍스트로 변환합니다.</p>
	 * 
	 * @param params : Hashtable object
	 * @return text
	 */
	public static String toParameterString(Map<String, String> params)
	{
		return toParameterString(params, false);
	}
	
	private static String addEscapeQuote(String befores)
	{
		String results = befores.replaceAll("\"", "\\\"");
		return results;
	}
	
	/**
	 * <p>Convert Hashtable into text which is formed as --key value.</p>
	 * 
	 * <p>Hashtable 를 텍스트로 변환합니다.</p>
	 * 
	 * @param params : Hashtable object
	 * @param useEsc : Use \n to escape quotes and \ symbols
	 * @return text
	 */
	public static String toParameterString(Map<String, String> params, boolean useEsc)
	{
		String results = "";
		Vector<String> keys = new Vector<String>();
		keys.addAll(params.keySet());
		
		// Check defaultoption is exist
		boolean defaultExist = false;
		for(int i=0; i<keys.size(); i++)
		{
			if(keys.get(i).equalsIgnoreCase("defaultoption"))
			{
				defaultExist = true;
				break;
			}
		}
		
		// Add defaultoption if exist
		if(defaultExist)
		{
			results = results + params.get("defaultoption");
			keys.remove("defaultoption");
			if(keys.size() >= 1) results = results + " ";
		}
		
		// Add other options
		for(int i=0; i<keys.size(); i++)
		{
			if(useEsc) results = results + "--" + keys.get(i) + " \"" + addEscapeQuote(params.get(keys.get(i))) + "\"";
			else results = results + "--" + keys.get(i) + " \"" + params.get(keys.get(i)) + "\"";
			if(i < keys.size() - 1) results = results + " ";
		}
		
		return results;
	}
	
	/**
	 * <p>Read keys and values from text and make Hashtable.</p>
	 * 
	 * <p>텍스트로부터 Hashtable 를 읽습니다.</p>
	 * 
	 * @param text : original text
	 * @return Hashtable object
	 */
	public static Map<String, String> toParameter(String text)
	{
		return toParameter(text, false);
	}
	
	/**
	 * <p>Read keys and values from text and make Hashtable.</p>
	 * 
	 * <p>텍스트로부터 Hashtable 를 읽습니다.</p>
	 * 
	 * @param text : original text
	 * @param useEsc : Use \n to escape quotes and \ symbols
	 * @return Hashtable object
	 */
	public static Map<String, String> toParameter(String text, boolean useEsc)
	{
		if(text == null) return null;
		Hashtable<String, String> results = new Hashtable<String, String>();
		List<String> blocks = new Vector<String>();
		
		String targetText = Statics.removeComment(text);
		
		// Get characters
		char[] textArray = targetText.trim().toCharArray();
		String accums = "";
		boolean quotes = false;
		boolean escaped = false;
		
		// Divide and get blocks from characters
		for(int i=0; i<textArray.length; i++)
		{
			if(quotes)
			{
				if(textArray[i] == '"')
				{
					if(escaped)
					{
						accums = accums + String.valueOf(textArray[i]);
						escaped = false;
					}
					else
					{
						quotes = false;
						blocks.add(accums);
						accums = "";
					}					
				}
				else if(textArray[i] == '\\')
				{
					if(escaped)
					{						
						accums = accums + String.valueOf(textArray[i]);
						escaped = false;
					}
					else
					{
						if(useEsc)
						{
							escaped = true;
						}
						else
						{
							accums = accums + String.valueOf(textArray[i]);
						}
					}					
				}
				else
				{
					accums = accums + String.valueOf(textArray[i]);
				}
			}
			else
			{
				if(textArray[i] == '"')
				{
					if(escaped)
					{
						accums = accums + String.valueOf(textArray[i]);
						escaped = false;
					}
					else
					{
						quotes = true;
					}					
				}
				else if(textArray[i] == ' ')
				{
					if(! (accums.equals("")))
					{
						blocks.add(accums);
						accums = "";
					}					
				}
				else if(textArray[i] == '\\')
				{
					if(escaped)
					{						
						accums = accums + String.valueOf(textArray[i]);
						escaped = false;
					}
					else
					{
						if(useEsc)
						{
							escaped = true;
						}
						else
						{
							accums = accums + String.valueOf(textArray[i]);
						}
					}					
				}
				else
				{
					accums = accums + String.valueOf(textArray[i]);
					if(i >= textArray.length - 1) 
					{
						blocks.add(accums);
						accums = "";
					}
				}
			}
		}
		
		String keys = null;
		boolean beforeIsKey = false;
		boolean firstTime = true;
		
		// Use blocks to create a Hashtable
		for(int i=0; i<blocks.size(); i++)
		{			
			if(blocks.get(i).startsWith("--"))
			{
				if(keys != null)
				{
					results.put(keys, "true");
				}
				keys = blocks.get(i).substring(2);
				beforeIsKey = true;
			}
			else
			{				
				if(firstTime)
				{
					results.put("defaultoption", blocks.get(i));					
				}
				else
				{
					if(beforeIsKey)
					{
						results.put(keys, blocks.get(i));					
					}					
				}
				beforeIsKey = false;
				keys = null;
			}
			firstTime = false;
		}
		
		return results;
	}
	
	/**
	 * <p>Return default parameter helps</p>
	 * 
	 * <p>기본 매개변수 도움말을 반환합니다.</p>
	 * 
	 * @return default parameter helps
	 */
	public static String getDefaultParamHelp()
	{
		String locale = Controller.getSystemLocale();
		String results = "";
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			results = results + "각 매개변수 별로 키와 값이 필요합니다. 이 모듈에서 사용 가능한 키와 그 설명을 볼 수 있습니다." + "\n";
		}
		else
		{
			results = results + "Each parameters have a key and its value. You can see available keys and its manuals." + "\n";
		}
		return results;
	}
}
