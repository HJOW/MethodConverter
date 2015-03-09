package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p>This module can remove comments in source codes.</p>
 * 
 * <p>이 모듈은 텍스트(프로그램 소스코드)에서 주석을 제거하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class RemoveCommentModule implements ConvertModule
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
			return "주석 제거";
		}
		else
		{
			return "Remove Comments";
		}
	}

	@Override
	public String convert(StringTable stringTable, Map<String, String> syntax,
			String before, StatusViewer statusViewer, StatusBar statusField,
			long threadTerm, Map<String, String> parameters)
	{
		String optionValue = parameters.get("option");
		String defaultParam = parameters.get("defaultoption");
		
		String singleLineCommentSymbol = null;
		String multiLineCommentStart = null;
		String multiLineCommentFinish = null;
		boolean exceptQuotes = false;
		
		if(optionValue != null)
		{
			if(optionValue.equalsIgnoreCase("JAVA")
					|| optionValue.equalsIgnoreCase("JavaScript without HTML")
					|| optionValue.equalsIgnoreCase("C/C++"))
			{
				singleLineCommentSymbol = "//";
				multiLineCommentStart = "/*";
				multiLineCommentFinish = "*/";
				exceptQuotes = true;
			}
			else if(optionValue.equalsIgnoreCase("Python")
					|| optionValue.equalsIgnoreCase("toParams"))
			{
				singleLineCommentSymbol = "#";
				multiLineCommentStart = null;
				multiLineCommentFinish = null;
				exceptQuotes = true;
			}
			else if(optionValue.equalsIgnoreCase("Ruby"))
			{
				singleLineCommentSymbol = "#";
				multiLineCommentStart = "=begin";
				multiLineCommentFinish = "=end";
				exceptQuotes = true;
			}
			else if(optionValue.equalsIgnoreCase("SQL"))
			{
				singleLineCommentSymbol = null;
				multiLineCommentStart = "/*";
				multiLineCommentFinish = "*/";
				exceptQuotes = true;
			}
			else if(optionValue.equalsIgnoreCase("Oracle-SQL"))
			{
				singleLineCommentSymbol = "--";
				multiLineCommentStart = "/*";
				multiLineCommentFinish = "*/";
				exceptQuotes = true;
			}
		}
		
		if(singleLineCommentSymbol == null)
		{
			singleLineCommentSymbol = defaultParam;
		}
		
		if(parameters.get("single_line") != null)
		{
			singleLineCommentSymbol = parameters.get("single_line");
		}
		if(parameters.get("multi_line_start") != null)
		{
			multiLineCommentStart = parameters.get("multi_line_start");
		}
		if(parameters.get("multi_line_finish") != null)
		{
			multiLineCommentFinish = parameters.get("multi_line_finish");
		}
		
		if(parameters.get("except_quotes") != null)
		{
			exceptQuotes = Statics.parseBoolean(parameters.get("except_quotes"));
		}
		
		Controller.print("Before", true);
		Controller.println("...");
		Controller.println(before);
		Controller.print("...");
		Controller.println("end", true);
		
		Controller.print("Prepare to remove comments", true);
		Controller.println("...");
		Controller.print("Single-line comment symbol", true);
		Controller.println(" : " + singleLineCommentSymbol);
		Controller.print("Multi-line comment start symbol", true);
		Controller.println(" : " + multiLineCommentStart);
		Controller.print("Multi-line comment end symbol", true);
		Controller.println(" : " + multiLineCommentFinish);
		Controller.print("Use quote exception", true);
		Controller.println(" : " + String.valueOf(exceptQuotes));
		
		long statusTerm = threadTerm;
		int nextStatusLimits = 0;
		String target = before;
		StringBuffer results = new StringBuffer("");
		StringBuffer temps = new StringBuffer("");
		
		if(statusTerm <= 2) statusTerm = 100;
		
		// Remove multi-line comments
		char[] chars = target.toCharArray();
		if((multiLineCommentStart != null) && (multiLineCommentFinish != null))
		{
			boolean inCommentArea = false;
			boolean accumulating = false;
			boolean doNormal = false;
			char quoteSymbol = ' ';
			for(int i=0; i<chars.length; i++)
			{
				if(inCommentArea)
				{
					if(chars[i] == multiLineCommentFinish.charAt(0))
					{
						if(! accumulating)
						{
							temps = new StringBuffer("");
						}
						accumulating = true;						
					}
					if(accumulating)
					{
						temps = temps.append(String.valueOf(chars[i]));						
					}
					if(temps.toString().equals(multiLineCommentFinish))
					{
						inCommentArea = false;
						temps = new StringBuffer("");
						accumulating = false;
					}
					if(temps.length() >= multiLineCommentFinish.length())
					{
						temps = new StringBuffer("");
						accumulating = false;
					}
				}
				else
				{
					doNormal = false;
					if((quoteSymbol == ' ') || (! exceptQuotes))
					{
						doNormal = true;
					}
					else if(quoteSymbol == '\'')
					{
						if(chars[i] == '\'')
						{
							quoteSymbol = ' ';
						}
						doNormal = false;
					}
					else if(quoteSymbol == '"')
					{
						if(chars[i] == '"')
						{
							quoteSymbol = ' ';
						}
						doNormal = false;
					}
					
					if(doNormal)
					{
						if(chars[i] == multiLineCommentStart.charAt(0))
						{
							if(! accumulating)
							{
								temps = new StringBuffer("");
							}
							accumulating = true;						
						}
						if(accumulating)
						{
							temps = temps.append(String.valueOf(chars[i]));
						}
						if(temps.toString().equals(multiLineCommentStart))
						{
							inCommentArea = true;
							temps = new StringBuffer("");
							accumulating = false;
						}
						else if(! accumulating)
						{
							results = results.append(String.valueOf(chars[i]));
							if((chars[i] == '\'') || (chars[i] == '"'))
							{
								quoteSymbol = chars[i];
							}
						}
						if(temps.length() >= multiLineCommentStart.length())
						{
							results = results.append(temps.toString());
							temps = new StringBuffer("");
							accumulating = false;
						}
					}
					else
					{
						results = results.append(String.valueOf(chars[i]));
					}
				}
				
				nextStatusLimits++;
				if(nextStatusLimits >= statusTerm)
				{
					if(statusViewer != null)
					{
						statusViewer.nextStatus();
					}
					nextStatusLimits = 0;
				}
			}
			
			Controller.print("Multi-line comments are removed", true);
			Controller.print("...\n");
			Controller.println(results.toString());
			Controller.print("...");
			Controller.println("end", true);
			
			target = results.toString();
		}
		else
		{
			
		}	
		
		results = new StringBuffer("");
		
		// Remove single-line comments
		if(singleLineCommentSymbol != null)
		{
			char[] charArray;
			StringTokenizer lineTokenizer = new StringTokenizer(target, "\n");
			boolean accumulates = false;
			boolean isComment = false;
			char quoteSymbol = ' ';
			boolean doNormal = false;
			
			if(lineTokenizer.countTokens() <= 0) return "";
			do
			{				
				isComment = false;
				accumulates = false;
				temps = new StringBuffer("");
				
				charArray = lineTokenizer.nextToken().toCharArray();
				
				for(int i=0; i<charArray.length; i++)
				{
					doNormal = false;
					if((quoteSymbol == ' ') || (! exceptQuotes))
					{
						doNormal = true;
					}
					else if(quoteSymbol == '\'')
					{
						if(charArray[i] == '\'')
						{
							quoteSymbol = ' ';
						}
						doNormal = false;
					}
					else if(quoteSymbol == '"')
					{
						if(charArray[i] == '"')
						{
							quoteSymbol = ' ';
						}
						doNormal = false;
					}
					if(doNormal)
					{
						if(! isComment)
						{
							if(charArray[i] == singleLineCommentSymbol.charAt(0))
							{
								if(! accumulates) 
								{
									temps = new StringBuffer("");
								}
								accumulates = true;
							}
							if(accumulates)
							{
								temps = temps.append(String.valueOf(charArray[i]));
							}						
							if(temps.toString().equals(singleLineCommentSymbol))
							{
								isComment = true;
								temps = new StringBuffer("");
								accumulates = false;
							}
							else
							{
								if(! accumulates) results = results.append(String.valueOf(charArray[i]));
								
								if((charArray[i] == '\'') || (charArray[i] == '"'))
								{
									quoteSymbol = charArray[i];
								}
							}
							if(temps.length() >= singleLineCommentSymbol.length())
							{
								results = results.append(temps.toString());
								temps = new StringBuffer("");
								accumulates = false;
							}
						}
						
						nextStatusLimits++;
						if(nextStatusLimits >= statusTerm)
						{
							if(statusViewer != null)
							{
								statusViewer.nextStatus();
							}
							nextStatusLimits = 0;
						}
					}
					else
					{
						results = results.append(String.valueOf(charArray[i]));
					}					
				}
				
				if(lineTokenizer.hasMoreTokens())
				{
					results = results.append("\n");
				}
			}
			while(lineTokenizer.hasMoreTokens());
			
			Controller.print("Single-line comments are removed", true);
			Controller.print("...\n");
			Controller.println(results.toString());
			Controller.print("...");
			Controller.println("end", true);
		}
		
		
		return Statics.applyScript(this, parameters, results.toString());
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
			results.add(Controller.getString("Oracle-SQL"));
			results.add(Controller.getString("SQL"));
			results.add(Controller.getString("JAVA"));
			results.add(Controller.getString("JavaScript without HTML"));
			results.add(Controller.getString("C/C++"));
			results.add(Controller.getString("Python"));						
		}
		else
		{
			results.add(Controller.getString("JAVA"));
			results.add(Controller.getString("JavaScript without HTML"));
			results.add(Controller.getString("C/C++/C#"));
			results.add(Controller.getString("Python"));
			results.add(Controller.getString("SQL"));
			results.add(Controller.getString("Oracle-SQL"));
		}
		
		return results;
	}

	@Override
	public List<String> parameterKeyList()
	{
		List<String> results = new Vector<String>();
		
		results.add("single_line");
		results.add("multi_line_start");
		results.add("multi_line_finish");
		results.add("except_quotes");
		
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
		
		if(Statics.isKoreanLocale()) // single_line multi_line_start multi_line_finish
		{
			results = results + "사용 가능한 키 : single_line, multi_line_start, multi_line_finish, except_quotes" + "\n\n";
			results = results + "single_line : 한 줄 주석 시작 기호" + "\n\n";
			results = results + "multi_line_start : 여러 줄 주석 시작 기호" + "\n\n";
			results = results + "multi_line_finish : 여러 줄 주석 끝 기호" + "\n\n";
			results = results + "except_quotes : 따옴표 안에 있는 주석 기호는 제거 작업에 반영 안 함" + "\n\n";
		}
		else
		{
			results = results + "Available keys : single_line, multi_line_start, multi_line_finish, except_quotes" + "\n\n";
			results = results + "single_line : Single-line comment start symbol" + "\n\n";
			results = results + "multi_line_start : Multi-line comment start symbol" + "\n\n";
			results = results + "multi_line_finish : Multi-line comment end symbol" + "\n\n";
			results = results + "except_quotes : Ignore comment symbols which are in quotes." + "\n\n";
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
		return true;
	}
	@Override
	public String getDefinitionName()
	{
		return "RemoveComment";
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
}
