package hjow.methodconverter;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * <p>Programming-Language Syntax table</p>
 * 
 * <p>프로그래밍 언어 문법 테이블 객체를 생성할 때 사용되는 클래스입니다.</p>
 * 
 * @author HJOW
 */
public class Syntax extends Hashtable<String, String> implements Serializable
{
	private static final long serialVersionUID = -4759341339753585285L;
	
	/**
	 * <p>Create empty table</p>
	 * 
	 * <p>빈 테이블을 만듭니다.</p>
	 */
	public Syntax()
	{
		super();
	}
	
	/**
	 * 
	 * <p>Read programming-language syntax table from String data</p>
	 * 
	 * <p>문자열로부터 프로그래밍 언어 문법 테이블을 만듭니다.</p>
	 * 
	 * @param data String text 문자열
	 */
	public Syntax(String data)
	{
		super();
		StringTokenizer lineTokenizer = new StringTokenizer(data, ";");
		StringTokenizer colonToken;
		String lineToken, keys, values;
		while(lineTokenizer.hasMoreTokens())
		{
			lineToken = lineTokenizer.nextToken().trim();
			colonToken = new StringTokenizer(lineToken, Controller.delimiter());
			keys = colonToken.nextToken().trim();
			values = colonToken.nextToken();
			while(colonToken.hasMoreTokens())
			{
				try
				{
					values = values + Controller.delimiter() +  colonToken.nextToken().trim();
					//System.out.println(values);
				}
				catch(Exception e)
				{
					
				}
			}
			this.put(keys, values);
		}
	}
	/**
	 * <p>Copy other Syntax object</p>
	 * 
	 * <p>기존의 문법 객체로부터 새로운 객체를 만들 때 사용됩니다.
	 * 필요한 값이 복사됩니다.</p>
	 * 
	 * @param other object 다른 객체
	 */
	public Syntax(Syntax other)
	{
		super();
		put("name", other.get("name"));
		put("firstString", other.get("firstString"));
		put("lastString", other.get("lastString"));
		
		put("classDeclarator", other.get("classDeclarator"));
		put("blockStarter", other.get("blockStarter"));
		put("blockEachLine", other.get("blockEachLine"));
		put("blockEnder", other.get("blockEnder"));
		put("methodStarter", other.get("methodStarter"));
		put("methodReturnType", other.get("methodReturnType"));
		put("returnStarter", other.get("returnStarter"));
		put("returnEnder", other.get("returnEnder"));
		put("lineEnder", other.get("lineEnder"));
		put("substitutor", other.get("substitutor"));
		put("quotes", other.get("quotes"));
		
		put("lineSkip", other.get("lineSkip"));
		put("concatStarter", other.get("concatStarter"));
		put("concatEnder", other.get("concatEnder"));
		put("functionParameterStarter", other.get("functionParameterStarter"));
		put("functionParameterEnder", other.get("functionParameterEnder"));
		put("variableDeclare", other.get("variableDeclare"));
		
		put("callByReferenceParameterType", other.get("callByReferenceParameterType"));
		put("callByReferenceParameter", other.get("callByReferenceParameter"));
		put("strcpys", other.get("strcpys"));
		put("variableDeclare", other.get("variableDeclare"));
		put("newStringInit", other.get("newStringInit"));
		put("returnGetter", other.get("returnGetter"));
		
		put("callByReference", String.valueOf(other.get("callByReference")));
		put("concat_type", String.valueOf(other.get("concat_type")));
		put("create_class", String.valueOf(other.get("create_class")));		
		put("escape_need_normal", String.valueOf(other.get("escape_need_normal")));
		put("escape_need_double", String.valueOf(other.get("escape_need_double")));
	}
	/**
	 * <p>Create Syntax object with the number of already-declared language syntax.</p>
	 * 
	 * <p>이미 준비되어 있는 문법으로 테이블을 만듭니다.
	 * 프로그래밍 언어를 나타내는 상수를 사용합니다.</p>
	 * 
	 * <p>Constant values that are available<br>
	 * StringToMethodConverter.SYNTAX_JAVA : Java<br>
	 * StringToMethodConverter.SYNTAX_JAVA_BUFFER : Java (StringBuffer)<br>
	 * StringToMethodConverter.SYNTAX_JAVASCRIPT : Javascript</p>
	 * 
	 * @param already_declared constant 상수값
	 */
	public Syntax(int already_declared)
	{		
		this(Statics.basicSyntax(already_declared));
	}
	/**
	 * <p>Input all values as parameter.</p>
	 * 
	 * <p>매개변수로 모든 값을 넣어 테이블을 만듭니다.</p>
	 */
	public Syntax(String name, String firstString, String lastString, String classDeclarator, String blockStarter, String blockEachLine, String blockEnder
			, String methodStarter, String methodReturnType, String returnStarter, String returnEnder, String lineEnder, String substitutor, String quotes
			, String lineSkip, String concatStarter, String concatEnder, String functionParameterStarter, String functionParameterEnder, 
			String callByReferenceParameterType, String callByReferenceParameter, String strcpys, String variableDeclare, String returnGetter, String newStringInit, boolean callByReference, int concat_type
			, boolean create_class, boolean escape_need_normal, boolean escape_need_double)
	{
		super();
		
		put("name", name);
		
		put("firstString", firstString);
		put("lastString", lastString);
		put("classDeclarator", classDeclarator);
		put("blockStarter", blockStarter);
		put("blockEachLine", blockEachLine);
		put("blockEnder", blockEnder);
		put("methodStarter", methodStarter);
		put("methodReturnType", methodReturnType);
		put("returnStarter", returnStarter);
		put("returnEnder", returnEnder);
		put("lineEnder", lineEnder);
		put("substitutor", substitutor);
		put("quotes", quotes);
		
		put("lineSkip", lineSkip);
		put("concatStarter", concatStarter);
		put("concatEnder", concatEnder);
		put("functionParameterStarter", functionParameterStarter);
		put("functionParameterEnder", functionParameterEnder);
		put("variableDeclare", variableDeclare);
		
		put("callByReferenceParameterType", callByReferenceParameterType);
		put("callByReferenceParameter", callByReferenceParameter);
		put("strcpys", strcpys);
		put("variableDeclare", variableDeclare);
		put("newStringInit", newStringInit);
		put("returnGetter", returnGetter);
		
		put("callByReference", String.valueOf(callByReference));
		put("concat_type", String.valueOf(concat_type));
		put("create_class", String.valueOf(create_class));		
		put("escape_need_normal", String.valueOf(escape_need_normal));
		put("escape_need_double", String.valueOf(escape_need_double));
	}
	
	/**
	 * 
	 * <p>Return all data as String.</p>
	 * 
	 * <p>모든 데이터를 문자열로 반환합니다.
	 * 이 문자열을 생성자에 넣어 다시 객체화시킬 수 있습니다.</p>
	 * 
	 * @return String 문자열
	 */
	public String toString()
	{
		return toString(Controller.delimiter());
	}
	/**
	 * 
	 * <p>Return all data as String.</p>
	 * 
	 * <p>모든 데이터를 문자열로 반환합니다.
	 * 구분자까지 지정할 수 있습니다.</p>
	 * 
	 * @param delimiter 구분자
	 * @return String 문자열
	 */
	public String toString(String delimiter)
	{
		StringBuffer strings = new StringBuffer("  ;\n");
		
		Set<String> keys = this.keySet();
		for(String s : keys)
		{
			strings = strings.append(s + delimiter + this.get(s) + "\n");
		}
		
		return strings.toString();
	}
}
