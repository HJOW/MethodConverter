package hjow.convert.module;

import hjow.methodconverter.Statics;
import hjow.methodconverter.Controller;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

public class ReturnSelfMethod implements ConvertModule
{
	private static final long serialVersionUID = 3536148979830424595L;
	
	public ReturnSelfMethod()
	{
		
	}
	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(null, null, before, null, null, 20, parameters);
	}
	/**
	 * <p>This module convert text into the method-declare statements which is return original text.</p>
	 * 
	 * <p>이 모듈은 원래의 텍스트를 반환하는 메소드의 선언문으로 변환합니다.</p>
	 * 
	 * 
	 * @param stringTable : String table 스트링 테이블
	 * @param syntax : Syntax table 문법 테이블
	 * @param before : Before text 실행 전 텍스트
	 * @param statusViewer : StatusViewer object 상태 변화 객체 (프로세스가 살아 있음을 알릴 때 사용)
	 * @param statusField : StatusBar object 상태바 객체 (상태를 텍스트로 출력할 때 사용)
	 * @param threadTerm : Thread-sleep term 쓰레드가 대기 상태에 있는 시간, 짧으면 작업이 빨리 끝나지만 너무 짧으면 시스템이 불안정해질 수 있습니다.
	 * @param className : Class name inside the converted statement. 변환된 메소드 선언문에 사용될 클래스 이름입니다.
	 * @param methodName : Method name inside the converted statement. 변환된 메소드 선언문에 사용될 메소드 이름입니다.
	 * @param variableName : Variable name inside the converted statement. 변환된 메소드 선언문에 사용될 변수 이름입니다.
	 * @param alignment : If this is true, right blacket location will be aligned. 우측 괄호 정렬 여부를 결정합니다.
	 * @return Converted string 변환된 텍스트
	*/
	public String convert(StringTable stringTable, Hashtable<String, String> syntax, String before, StatusViewer statusViewer, StatusBar statusField, long threadTerm, String className, String methodName, String variableName, boolean alignment)
	{
		Hashtable<String, String> parameter = new Hashtable<String, String>();
		if(className != null) parameter.put("class", className);
		else parameter.put("class", "Writer");
		if(methodName != null) parameter.put("method", methodName);
		else parameter.put("method", "write");
		if(variableName != null) parameter.put("variable", variableName);
		else parameter.put("variable", "r");
		parameter.put("align", "true");	
		
		return convert(stringTable, syntax, before, statusViewer, statusField, threadTerm, parameter);
	}
	/**
	 * <p>This module convert text into the method-declare statements which is return original text.</p>
	 * 
	 * <p>이 모듈은 원래의 텍스트를 반환하는 메소드의 선언문으로 변환합니다.</p>
	 * 
	 * <p>Parameter key : class, method, variable</p>
	 * 
	 * 
	 * @param stringTable : String table 스트링 테이블
	 * @param syntax : Syntax table 문법 테이블
	 * @param before : Before text 실행 전 텍스트
	 * @param statusViewer : StatusViewer object 상태 변화 객체 (프로세스가 살아 있음을 알릴 때 사용)
	 * @param statusField : StatusBar object 상태바 객체 (상태를 텍스트로 출력할 때 사용)
	 * @param threadTerm : Thread-sleep term 쓰레드가 대기 상태에 있는 시간, 짧으면 작업이 빨리 끝나지만 너무 짧으면 시스템이 불안정해질 수 있습니다.
	 * @param parameters : Parameters as Hashtable object to convert String. 변환에 사용되는 매개 변수
	 * @return Converted string 변환된 텍스트
	*/
	@Override
	public String convert(StringTable stringTable, Map<String, String> syntax, String before, StatusViewer statusViewer, StatusBar statusField, long threadTerm, Map<String, String> parameters)
	{
		if(before == null) return null;
		if(statusViewer != null) statusViewer.reset();
		if(statusField != null) statusField.setText(Controller.getString("Converting..."));
		
		String beforeArgs = new String(before);
		
		StringBuffer results = new StringBuffer("");
		
		beforeArgs = beforeArgs.replace("\n\n", "\n \n");
		beforeArgs = beforeArgs.replace("\\", "\\\\");
		
		
		StringTokenizer lineToken = new StringTokenizer(beforeArgs, "\n");
		String token = "";				
		
		System.out.println("Preparing to convert text...");
		
		String className = "Writer";
		String methodName = "write";
		String variableName = "r";
		
		boolean alignment = false;
		
		if(parameters != null)
		{
			if(parameters.get("class") != null) className = parameters.get("class");
			if(parameters.get("method") != null) methodName = parameters.get("method");
			if(parameters.get("variable") != null) variableName = parameters.get("variable");
			
			if(parameters.get("align") != null)
			{
				try
				{
					alignment = Statics.parseBoolean(parameters.get("align"));
				}
				catch(Exception e)
				{
					alignment = false;
				}
			}
		}
		
		String firstString = "";
		String lastString = "";
		String classDeclarator = "class";
		String blockStarter = "{";
		String blockEachLine = "   ";
		String blockEnder = "}";
		String methodStarter = "public static";
		String methodReturnType = "String";
		String returnStarter = "return ";
		String returnEnder = "";
		String newStringInit = "\"\"";
		String returnGetter = "";
		String lineEnder = ";";
		String substitutor = "=";
		String quotes = "\"";
		String lineSkip = "\\n";
		String concatStarter = " + ";
		String concatEnder = "";
		String functionParameterStarter = "(";
		String functionParameterEnder = ")";
		String callByReferenceParameterType = "";
		String callByReferenceParameter = "";
		String strcpys = "";
		String variableDeclare = "String";
		boolean callByReference = false;
		int concat_type = Controller.CONCAT_NORMAL;		
		boolean create_class = true;
		boolean escape_need_normal = false;
		boolean escape_need_double = true;
		
		if(syntax == null)
		{
			firstString = "";
			lastString = "";
			classDeclarator = "class";
			blockStarter = "{";
			blockEachLine = "   ";
			blockEnder = "}";
			newStringInit = "\"\"";
			methodStarter = "public static";
			methodReturnType = "String";
			returnStarter = "return ";
			returnGetter = "";
			returnEnder = "";
			lineEnder = ";";
			substitutor = "=";
			quotes = "\"";
			lineSkip = "\\n";
			concatStarter = " + ";
			concatEnder = "";
			functionParameterStarter = "(";
			functionParameterEnder = ")";
			callByReferenceParameterType = "";
			callByReferenceParameter = "";
			strcpys = "";
			variableDeclare = "String";
			callByReference = false;
			concat_type = Controller.CONCAT_NORMAL;
			create_class = true;
			escape_need_normal = false;
			escape_need_double = true;
		}
		else
		{
			firstString = syntax.get("firstString");
			lastString = syntax.get("lastString");
			classDeclarator = syntax.get("classDeclarator");
			blockStarter = syntax.get("blockStarter");
			blockEachLine = syntax.get("blockEachLine");
			blockEnder = syntax.get("blockEnder");
			newStringInit = syntax.get("newStringInit");
			methodStarter = syntax.get("methodStarter");
			methodReturnType = syntax.get("methodReturnType");
			returnStarter = syntax.get("returnStarter");
			returnEnder = syntax.get("returnEnder");
			lineEnder = syntax.get("lineEnder");
			substitutor = syntax.get("substitutor");
			quotes = syntax.get("quotes");
			lineSkip = syntax.get("lineSkip");
			concatStarter = syntax.get("concatStarter");
			concatEnder = syntax.get("concatEnder");
			functionParameterStarter = syntax.get("functionParameterStarter");
			functionParameterEnder = syntax.get("functionParameterEnder");
			callByReferenceParameterType = syntax.get("callByReferenceParameterType");
			callByReferenceParameter = syntax.get("callByReferenceParameter");
			variableDeclare = syntax.get("variableDeclare");
			strcpys = syntax.get("strcpys");
			returnGetter = syntax.get("returnGetter");
			callByReference = Statics.parseBoolean(syntax.get("callByReference"));
			try
			{
				concat_type = Integer.parseInt(syntax.get("concat_type"));
			}
			catch(NumberFormatException e)
			{
				String concat_type_input = syntax.get("concat_type");
				if(concat_type_input.equalsIgnoreCase("normal"))
				{
					concat_type = Controller.CONCAT_NORMAL;
				}
				else if(concat_type_input.equalsIgnoreCase("function"))
				{
					concat_type = Controller.CONCAT_FUNCTION;
				}
				else if(concat_type_input.equalsIgnoreCase("method"))
				{
					concat_type = Controller.CONCAT_METHOD;
				}
			}
			create_class = Statics.parseBoolean(syntax.get("create_class"));	
			escape_need_normal = Statics.parseBoolean(syntax.get("escape_need_normal"));
			escape_need_double = Statics.parseBoolean(syntax.get("escape_need_double"));
		}	
		
		results = results.append(firstString);
		results = results.append("\n");
		
		String real_eachLine = blockEachLine;
		if(create_class) real_eachLine = real_eachLine + blockEachLine;
		
		String paramSector = functionParameterStarter + callByReferenceParameterType + callByReferenceParameter + functionParameterEnder;
		
		if(create_class) results = results.append(classDeclarator + " " + className + "\n" + blockStarter + "\n");
		
		
		if(create_class) results = results.append(blockEachLine);
		results = results.append(methodStarter + " " + methodReturnType + " " + methodName + paramSector + "\n");
		if(create_class) results = results.append(blockEachLine);
		results = results.append(blockStarter + "\n");
		results = results.append(real_eachLine + variableDeclare + " " + variableName + " " + substitutor + " " + newStringInit + lineEnder);
		
		long max_closeBlacket = 0;
		String consistStr = "";
		StringBuffer insideResult = new StringBuffer("");
		while(lineToken.hasMoreTokens())
		{
			token = lineToken.nextToken();
			System.out.println("Converting : " + token);
			if(escape_need_normal)
			{
				token = Statics.replaceQuote(token, false);
			}
			if(escape_need_double)
			{
				token = Statics.replaceQuote(token, true);
			}
			if(concat_type == Controller.CONCAT_FUNCTION)
			{
				insideResult = insideResult.append("\n" + blockEachLine + concatStarter + functionParameterStarter + variableName + ", " + quotes + token + lineSkip + quotes + functionParameterEnder + concatEnder + lineEnder);
				consistStr = blockEachLine + concatStarter + functionParameterStarter + variableName + ", " + quotes + token + lineSkip + quotes;
			}
			else if(concat_type == Controller.CONCAT_METHOD)
			{
				insideResult = insideResult.append("\n" + real_eachLine + variableName + concatStarter + quotes + token + lineSkip + quotes + concatEnder + lineEnder);
				consistStr = real_eachLine + variableName + concatStarter + quotes + token + lineSkip + quotes;
			}
			else 
			{
				insideResult = insideResult.append("\n" + real_eachLine + variableName + " " + substitutor + " " + variableName + concatStarter + quotes + token + lineSkip + quotes + concatEnder + lineEnder);
				consistStr = real_eachLine + variableName + " " + substitutor + " " + variableName + concatStarter + quotes + token + lineSkip + quotes;
			}
			if(max_closeBlacket < consistStr.length()) max_closeBlacket = consistStr.length();
			if(threadTerm >= 1)
			{
				try
				{
					if(statusViewer != null) statusViewer.nextStatus();
					Thread.sleep(threadTerm);
				}
				catch(Exception e)
				{
					
				}
			}
		}
		
		
		if(alignment)
		{
			lineToken = new StringTokenizer(beforeArgs, "\n");
			StringBuffer empties = new StringBuffer("");
			insideResult = new StringBuffer("");
			while(lineToken.hasMoreTokens())
			{
				token = lineToken.nextToken();
				empties = new StringBuffer("");
				System.out.println("Reconverting : " + token);
				if(escape_need_normal)
				{
					token = Statics.replaceQuote(token, false);
				}
				if(escape_need_double)
				{
					token = Statics.replaceQuote(token, true);
				}
				if(concat_type == Controller.CONCAT_FUNCTION)
				{
					consistStr = blockEachLine + concatStarter + functionParameterStarter + variableName + ", " + quotes + token + lineSkip + quotes;
					for(int i=0; i<max_closeBlacket - consistStr.length(); i++)
					{
						empties = empties.append(" ");
					}
					insideResult = insideResult.append("\n" + blockEachLine + concatStarter + functionParameterStarter + variableName + ", " + quotes + token + lineSkip + quotes + String.valueOf(empties) + functionParameterEnder + concatEnder + lineEnder);
					
				}
				else if(concat_type == Controller.CONCAT_METHOD)
				{
					consistStr = real_eachLine + variableName + concatStarter + quotes + token + lineSkip + quotes;
					for(int i=0; i<max_closeBlacket - consistStr.length(); i++)
					{
						empties = empties.append(" ");
					}
					insideResult = insideResult.append("\n" + real_eachLine + variableName + concatStarter + quotes + token + lineSkip + quotes + String.valueOf(empties) + concatEnder + lineEnder);
					
				}
				else 
				{
					consistStr = real_eachLine + variableName + " " + substitutor + " " + variableName + concatStarter + quotes + token + lineSkip + quotes;
					for(int i=0; i<max_closeBlacket - consistStr.length(); i++)
					{
						empties = empties.append(" ");
					}
					insideResult = insideResult.append("\n" + real_eachLine + variableName + " " + substitutor + " " + variableName + concatStarter + quotes + token + lineSkip + quotes + String.valueOf(empties) + concatEnder + lineEnder);
					
				}
				if(threadTerm >= 1)
				{
					try
					{
						if(statusViewer != null) statusViewer.nextStatus();
						Thread.sleep(threadTerm);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		}
		results = results.append(String.valueOf(insideResult));
		
		if(callByReference) 
		{
			results = results.append("\n" + real_eachLine + strcpys + functionParameterStarter + callByReferenceParameter + ", " + variableName + returnGetter + functionParameterEnder + lineEnder);
		}
		else
		{
			results = results.append("\n" + real_eachLine + returnStarter + variableName + returnGetter + returnEnder + lineEnder);
		}
		
		results = results.append("\n");
		if(create_class) results = results.append(blockEachLine);
		results = results.append(blockEnder);
		if(create_class) results = results.append("\n" + blockEnder);
		
		results = results.append("\n");
		results = results.append(lastString);	
		return Statics.applyScript(this, parameters, results.toString());
	}
	@Override
	public List<String> parameterKeyList()
	{
		Vector<String> keys = new Vector<String>();
		keys.add("class");
		keys.add("method");
		keys.add("variable");
		return keys;
	}
	
	@Override
	public String getName()
	{
		return getName("en");
	}
	@Override
	public String getName(String locale)
	{
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			return "반환 메소드 선언문 생성";
		}
		else
		{
			return "Return-method declare statement";
		}
	}
	@Override
	public List<String> optionList()
	{
		List<String> results = new Vector<String>();
		results.addAll(Controller.getAvailableOptions());
		return results;		
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
	public void close()
	{
		
	}
	@Override
	public String defaultParameterText()
	{		
		return "--class \"TextMaker\" --method \"getText\" --variable \"text\"";
	}
	@Override
	public String getParameterHelp()
	{
		String locale = Controller.getSystemLocale();
		String results = "";
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			results = results + "사용 가능한 키 : class, method, variable" + "\n\n";
			results = results + "class : 클래스 이름" + "\n\n";
			results = results + "method : 메소드 이름" + "\n\n";
			results = results + "variable : 변수 이름" + "\n\n";
		}
		else
		{
			results = results + "Available keys : class, method, variable" + "\n\n";
			results = results + "class : Class name" + "\n\n";
			results = results + "method : Method name" + "\n\n";
			results = results + "variable : Variable name" + "\n\n";
		}
		return results;
	}
	@Override
	public boolean isOptionEditable()
	{
		return false;
	}
	@Override
	public String getDefinitionName()
	{
		return "ReturnSelf";
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
