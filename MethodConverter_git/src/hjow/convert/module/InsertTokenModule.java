package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p>This module can insert words in tokens.</p>
 * 
 * <p>이 모듈은 텍스트에 원하는 단어들을 원하는 토큰 부분에 삽입할 때 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class InsertTokenModule implements ConvertModule
{
	private static final long serialVersionUID = -3092870416961786168L;

	@Override
	public String getName()
	{
		return getName("en");
	}
	@Override
	public String getName(String locale)
	{
		if(Statics.isKoreanLocale(locale))
		{
			return "토큰 삽입";
		}
		else
		{
			return "Token insert";
		}
	}

	@Override
	public String convert(StringTable stringTable, Map<String, String> syntax,
			String before, StatusViewer statusViewer, StatusBar statusField,
			long threadTerm, Map<String, String> parameters)
	{
		String insertQuote = "";
		String tokenDelims = ",";
		String area = "?";
		String specialParameterSurrounds = null;
		String tokens = parameters.get("defaultoption");;
		String locationIndexStart = "{";
		String locationIndexEnd = "}";
		boolean needTrim = false;
		boolean hasLocationIndex = false;
		boolean useSplit = false;
		
		String optionVal = parameters.get("option");
		int checkLevel = 0;
		
		if(optionVal == null)
		{
			checkLevel = 0;
		}
		else
		{
			if(optionVal.equals(""))
			{
				checkLevel = 0;
			}
			else if(optionVal.equals(Controller.getString("Level 0 : No check")))
			{
				checkLevel = 0;
			}
			else if(optionVal.equals(Controller.getString("Level 1 : Only check area counts")))
			{
				checkLevel = 1;
			}
			else if(optionVal.equals(Controller.getString("Level 2 : Check all")))
			{
				checkLevel = 2;
			}
			else if(optionVal.equals(Controller.getString("Make SQL query from JDBC log")))
			{
				checkLevel = 2;
				insertQuote = "'";
				tokenDelims = ",";
				area = "?";
				specialParameterSurrounds = "#";
				needTrim = true;
				
				if(tokens.charAt(0) == '[' && tokens.charAt(tokens.length() - 1) == ']')
				{
					tokens = tokens.substring(1, tokens.length() - 1);
				}
			}
			else if(optionVal.equals(Controller.getString("C# Parameters")))
			{
				checkLevel = 2;
				insertQuote = "";
				tokenDelims = ",";
				area = "?";
				specialParameterSurrounds = "#";
				needTrim = true;
				hasLocationIndex = true;
				locationIndexStart = "{";
				locationIndexEnd = "}";
				
				
				if(tokens.charAt(0) == '[' && tokens.charAt(tokens.length() - 1) == ']')
				{
					tokens = tokens.substring(1, tokens.length() - 1);
				}
			}
		}
		
		try
		{
		
			if(parameters.get("area") != null && (! parameters.get("area").equals("")))
			{
				area = parameters.get("area");
			}
			
			if(parameters.get("delimiter") != null && (! parameters.get("delimiter").equals("")))
			{
				tokenDelims = parameters.get("delimiter");
			}		
			
			if(parameters.get("hasLocationIndex") != null && (! parameters.get("hasLocationIndex").equals("")))
			{
				hasLocationIndex = Statics.parseBoolean(parameters.get("hasLocationIndex"));
			}
			
			if(parameters.get("indexBraceStart") != null && (! parameters.get("indexBraceStart").equals("")))
			{
				locationIndexStart = parameters.get("indexBraceStart");
			}
			if(parameters.get("indexBraceEnd") != null && (! parameters.get("indexBraceEnd").equals("")))
			{
				locationIndexEnd = parameters.get("indexBraceEnd");
			}
			
			if(parameters.get("trim_token") != null)
			{
				needTrim = Statics.parseBoolean(parameters.get("trim_token"));
			}	
			
			if(parameters.get("quote") != null)
			{
				insertQuote = parameters.get("quote");
			}
			
			if(parameters.get("tokens") != null && (! parameters.get("tokens").equals("")))
			{
				tokens = parameters.get("tokens");
			}
			
			if(parameters.get("specialParameterSurrounds") != null && (! parameters.get("specialParameterSurrounds").equals("")))
			{
				specialParameterSurrounds = parameters.get("specialParameterSurrounds");
			}
			if(specialParameterSurrounds != null && specialParameterSurrounds.equals("")) specialParameterSurrounds = null;
			
			if(parameters.get("check_level") != null && (! parameters.get("check_level").equals("")))
			{			
				checkLevel = Integer.parseInt(parameters.get("check_level"));
			}		
			
			if(parameters.get("expression") != null && (! parameters.get("expression").equals("")))
			{
				useSplit = Statics.parseBoolean(parameters.get("expression"));
			}
			
			Controller.print("area", true);
			Controller.println(" : " + area);
			Controller.print("delimiter", true);
			Controller.println(" : " + tokenDelims);
			Controller.print("needTrim", true);
			Controller.println(" : " + needTrim);
			Controller.print("quote", true);
			Controller.println(" : " + insertQuote);
			Controller.print("tokens", true);
			Controller.println(" : " + tokens);
			Controller.print("check_level", true);
			Controller.println(" : " + checkLevel);
			
			List<String> takeTokens = new Vector<String>();
			StringTokenizer tokenTokenizer = new StringTokenizer(tokens, tokenDelims);
			String target = null;
			int statusJumps = 0;
			while(tokenTokenizer.hasMoreTokens())
			{
				target = tokenTokenizer.nextToken();
				if(target == null) break;
				if(needTrim) takeTokens.add(target.trim());
				else takeTokens.add(target);
				
				statusJumps++;
				if(statusJumps >= 100)
				{				
					if(statusViewer != null) statusViewer.nextStatus();
					Controller.print("token processing", true);
					Controller.println(" : " + String.valueOf(target) + ", " + takeTokens.size());
					statusJumps = 0;
				}
			}
			
			Controller.print("tokens find", true);
			Controller.println(" : " + String.valueOf(takeTokens));
			
			StringBuffer buffer = new StringBuffer("");
			String temp = null;
			target = null;
			statusJumps = 0;
			int indexOfToken = 0;
			
			String beforeContents = before;
					
			if(specialParameterSurrounds != null)
			{
				char specialParamChar = specialParameterSurrounds.charAt(0);
				char[] chars = beforeContents.toCharArray();
				char quotes = ' ';
				boolean inParamArea = false;
				StringBuffer paramChanged = new StringBuffer("");
				statusJumps = 0;
				for(int i=0; i<chars.length; i++)
				{
					if(inParamArea)
					{
						if(chars[i] == specialParamChar)
						{
							paramChanged = paramChanged.append(String.valueOf(area));
							inParamArea = false;						
						}
					}
					else
					{
						if(quotes == ' ')
						{
							if(chars[i] == specialParamChar)
							{
								inParamArea = true;
							}
							else if(chars[i] == '\'' || chars[i] == '"')
							{
								quotes = chars[i];
								paramChanged = paramChanged.append(String.valueOf(chars[i]));
							}
							else
							{
								paramChanged = paramChanged.append(String.valueOf(chars[i]));
							}
						}
						else
						{
							if(quotes == chars[i])
							{
								quotes = ' ';
							}
							paramChanged = paramChanged.append(String.valueOf(chars[i]));
						}
						
					}
					
					statusJumps++;
					if(statusJumps >= 100)
					{				
						if(statusViewer != null) statusViewer.nextStatus();
						Controller.print("parameter processing", true);
						Controller.println(" : " + String.valueOf(target) + ", " + takeTokens.size());
						statusJumps = 0;
					}
				}
				beforeContents = paramChanged.toString();
			}
			
			if(hasLocationIndex)
			{				
				return Statics.applyScript(this, parameters, 
						String.valueOf(tokenInsert(locationIndexStart, locationIndexEnd, beforeContents, takeTokens)));
			}
			else
			{
				if(useSplit)
				{
					String[] splitted = (beforeContents + " ").split(area);
					for(int i=0; i<splitted.length; i++)
					{
						target = splitted[i];
						buffer = buffer.append(target);
						
						if(i < splitted.length - 1)
						{
							if(indexOfToken >= takeTokens.size())
							{
								if(checkLevel >= 1)
								{
									Controller.alert(Controller.getString("Error") + " : " 
											+ Controller.getString("more token areas") + " (" + String.valueOf(indexOfToken + 1) + "), " 
											+ Controller.getString("lack of tokens") + " (" + takeTokens.size() + ")");
									return before;
								}
								else indexOfToken = 0;
							}
								
							temp = takeTokens.get(indexOfToken);
							buffer = buffer.append(insertQuote);
							buffer = buffer.append(temp);
							buffer = buffer.append(insertQuote);
							indexOfToken++;
						}
						
						statusJumps++;
						if(statusJumps >= 100)
						{				
							if(statusViewer != null) statusViewer.nextStatus();
							Controller.print("insert processing", true);
							Controller.println(" : " + String.valueOf(temp) + ", " + buffer.length());
							statusJumps = 0;
						}
					}
				}
				else
				{
					StringTokenizer tokenizer = new StringTokenizer(beforeContents + " ", area);	
					while(tokenizer.hasMoreTokens())
					{			
						target = tokenizer.nextToken();
						if(target == null) break;
						buffer = buffer.append(target);
						
						if(tokenizer.hasMoreTokens())
						{
							if(indexOfToken >= takeTokens.size())
							{
								if(checkLevel >= 1)
								{
									Controller.alert(Controller.getString("Error") + " : " 
											+ Controller.getString("more token areas") + " (" + String.valueOf(indexOfToken + 1) + "), " 
											+ Controller.getString("lack of tokens") + " (" + takeTokens.size() + ")");
									return before;
								}
								else indexOfToken = 0;
							}
								
							temp = takeTokens.get(indexOfToken);
							buffer = buffer.append(insertQuote);
							buffer = buffer.append(temp);
							buffer = buffer.append(insertQuote);
							indexOfToken++;
						}
						
						statusJumps++;
						if(statusJumps >= 100)
						{				
							if(statusViewer != null) statusViewer.nextStatus();
							Controller.print("insert processing", true);
							Controller.println(" : " + String.valueOf(temp) + ", " + buffer.length());
							statusJumps = 0;
						}
					}	
				}	
				if(checkLevel >= 2)
				{
					if(indexOfToken != takeTokens.size())
					{
						Controller.alert(Controller.getString("Error") + " : " 
								+ Controller.getString("more tokens")
								+ "(" + String.valueOf(indexOfToken) + "), " + Controller.getString("lack of token areas") + "(" + takeTokens.size() + ")");
						return before;
					}
				}
			}
			return Statics.applyScript(this, parameters, String.valueOf(buffer.toString().substring(0, buffer.length() - 1)));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.println(Statics.fullErrorMessage(e));
			return before;
		}		
		
	}
	
	
	protected String tokenInsert(String braceStartSymbol, String braceEndSymbol, String text, List<String> params)
	{
		BraceData braceInfo = new BraceData();
		String takenStr = text;
		
		while(braceInfo != null)
		{
			braceInfo = includeBraces(braceStartSymbol, braceEndSymbol, takenStr);			
			if(braceInfo == null) break;
			
			takenStr = replaceOneBrace(braceStartSymbol, braceEndSymbol, takenStr, braceInfo, params);
		}
		
		return takenStr;
	}
	
	private BraceData includeBraces(String braceStartSymbol, String braceEndSymbol, String text)
	{
		return includeBraces(braceStartSymbol, braceEndSymbol, text, 0);
	}
	private BraceData includeBraces(String braceStartSymbol, String braceEndSymbol, String text, int startIndex)
	{
		int braceIndex = -1;
		int braceEndIndex = -1;
		String numbersText = "";
		int numbers = -1;
		
		if(startIndex >= text.length()) return null;
		
		try
		{
			braceIndex = text.indexOf(braceStartSymbol, startIndex);
			braceEndIndex = text.indexOf(braceEndSymbol, braceIndex);
			
			if(braceIndex < 0) return null;
			if(braceEndIndex < 0) return null;		
			
			try
			{
				numbersText = text.substring(braceIndex + 1, braceEndIndex);
				numbers = Integer.parseInt(numbersText);
			}
			catch(Exception e)
			{
				return includeBraces(braceStartSymbol, braceEndSymbol, text, startIndex + 1);
			}
			
			return new BraceData(braceIndex, braceEndIndex, numbers);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.println(Statics.fullErrorMessage(e));
			return null;
		}
	}
	private String replaceOneBrace(String braceStartSymbol, String braceEndSymbol, String text, BraceData braceInfo, List<String> params)
	{
		StringBuffer results = new StringBuffer("");
		String target = text + " ";
		
		results = new StringBuffer(target.substring(0, braceInfo.getBraceIndex()));
		results = results.append(params.get(braceInfo.getNumbers()));
		results = results.append(target.substring(braceInfo.getBraceEndIndex() + 1, target.length()));
		
		String toStrings = results.toString();
		String lastElemented = toStrings.substring(0, toStrings.length() - 1);
		
		return lastElemented;
	}
	
	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(Controller.getStringTable(), null, before, null, null, 20, parameters);
	}

	@Override
	public List<String> optionList()
	{
		List<String> results = new Vector<String>();
		if(Controller.DEFAULT_MODULE_NAME.equals("JDBC"))
		{
			results.add(Controller.getString("Make SQL query from JDBC log"));
			results.add(Controller.getString("C# Parameters"));
			results.add(Controller.getString("Level 0 : No check"));
			results.add(Controller.getString("Level 1 : Only check area counts"));
			results.add(Controller.getString("Level 2 : Check all"));			
		}
		else
		{
			results.add(Controller.getString("Level 0 : No check"));
			results.add(Controller.getString("Level 1 : Only check area counts"));
			results.add(Controller.getString("Level 2 : Check all"));
			results.add(Controller.getString("Make SQL query from JDBC log"));
		}
		
		return results;
	}

	@Override
	public List<String> parameterKeyList()
	{
		List<String> results = new Vector<String>();
		
		results.add("tokens");
		results.add("area");
		results.add("delimiter");
		results.add("trim_token");		
		results.add("check_level");
		results.add("quote");
		results.add("expression");
		results.add("hasLocationIndex");
		results.add("indexBraceStart");
		results.add("indexBraceEnd");
		
		return results;
	}

	@Override
	public String defaultParameterText()
	{
		return "";
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
	public String getParameterHelp()
	{
		String results = "";		
		
		if(Statics.isKoreanLocale())
		{ // hasLocationIndex, indexBraceStart, indexBraceEnd
			results = results + "사용 가능한 키 : area, delimiter, trim_token, tokens, check_level, quote, expression" + "\n\n";
			results = results + "tokens : 토큰" + "\n\n";
			results = results + "area : 토큰이 삽입될 장소를 나타내는 구분자" + "\n\n";
			results = results + "delimiter : 토큰들을 구분할 구분자이며 기본값은 콤마(,)" + "\n\n";
			results = results + "trim_token : true 입력 시 토큰에 있는 공백을 제거하여 삽입합니다." + "\n\n";			
			results = results + "check_level : 검사 수준, 토큰 갯수와 삽입될 장소 갯수가 맞는지 여부 확인 정도" + "\n\n";
			results = results + "quote : 토큰 앞뒤로 삽입할 특수 기호" + "\n\n";
			results = results + "expression : true 입력 시 area 구분자에 정규 표현식을 사용할 수 있게 됩니다." + "\n\n";
			results = results + "추가 기능 : Brace Token" + "\n\n";
			results = results + "토큰을 원하는 위치에 삽입합니다. C# 등의 프로그래밍 언어에서 사용하면 유용합니다." + "\n\n";
			results = results + "hasLocationIndex : true 입력 시 Brace Token 기능으로 동작합니다." + "\n\n";
			results = results + "indexBraceStart : 매개 변수 구분 시작기호, 기본값은 { " + "\n\n";
			results = results + "indexBraceEnd : 매개 변수 구분 끝 기호, 기본값은 }" + "\n\n";
		}
		else
		{
			results = results + "Available keys : area, delimiter, trim_token, tokens, check_level, quote, expression" + "\n\n";
			results = results + "tokens : Tokens" + "\n\n";
			results = results + "area : Where to insert tokens" + "\n\n";
			results = results + "delimiter : Token delimiter, default is comma (,)" + "\n\n";
			results = results + "trim_token : If this is true, tokens will be trimmed." + "\n\n";			
			results = results + "check_level : If this is high value, the program will check how many tokens and how many areas." + "\n\n";
			results = results + "quote : Quote symbol (such as \") will be inserted beside each tokens." + "\n\n";
			results = results + "expression : If this is true, you can use regular expression as area delimiter." + "\n\n";
		}
		return results;
	}

	@Override
	public void close()
	{
		
	}
	@Override
	public boolean isOptionEditable()
	{
		return false;
	}
	@Override
	public String getDefinitionName()
	{
		return "InsertToken";
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
/**
 * <p>This class objects are need to find and replace braces.</p>
 * 
 * <p>이 클래스 객체들은 중괄호를 파라미터로 교체하기 위해 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
class BraceData implements Serializable
{
	private static final long serialVersionUID = 8325008029683666976L;
	private int braceIndex, braceEndIndex, numbers;
	
	public BraceData()
	{
		
	}
	public BraceData(int braceIndex, int braceEndIndex, int numbers)
	{
		super();
		this.braceIndex = braceIndex;
		this.braceEndIndex = braceEndIndex;
		this.numbers = numbers;
	}
	public int getBraceIndex()
	{
		return braceIndex;
	}
	public void setBraceIndex(int braceIndex)
	{
		this.braceIndex = braceIndex;
	}
	public int getBraceEndIndex()
	{
		return braceEndIndex;
	}
	public void setBraceEndIndex(int braceEndIndex)
	{
		this.braceEndIndex = braceEndIndex;
	}
	public int getNumbers()
	{
		return numbers;
	}
	public void setNumbers(int numbers)
	{
		this.numbers = numbers;
	}
}	
