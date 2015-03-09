package hjow.convert.module;

import java.util.List;
import java.util.Vector;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;

/**
 * <p>This module can insert words in parameter fields.</p>
 * 
 * <p>이 모듈은 텍스트에 원하는 단어들을 원하는 파라미터 부분에 삽입할 때 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class InsertParameterModule extends InsertTokenModule
{
	private static final long serialVersionUID = -4547594687911558821L;
	@Override
	public String getName(String locale)
	{
		if(Statics.isKoreanLocale(locale))
		{
			return "파라미터 삽입";
		}
		else
		{
			return "Parameter insert";
		}
	}
	@Override
	public List<String> optionList()
	{
		List<String> results = new Vector<String>();
		if(Controller.DEFAULT_MODULE_NAME.equals("JDBC"))
		{
			results.add(Controller.getString("Make SQL query from JDBC log"));
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
		
		return results;
	}
	@Override
	public String getParameterHelp()
	{
		String results = "";		
		
		if(Statics.isKoreanLocale())
		{
			results = results + "사용 가능한 키 : area, delimiter, trim_token, tokens, check_level, quote, expression" + "\n\n";
			results = results + "tokens : 토큰" + "\n\n";
			results = results + "area : 토큰이 삽입될 장소를 나타내는 구분자" + "\n\n";
			results = results + "delimiter : 토큰들을 구분할 구분자이며 기본값은 콤마(,)" + "\n\n";
			results = results + "trim_token : true 입력 시 토큰에 있는 공백을 제거하여 삽입합니다." + "\n\n";			
			results = results + "check_level : 검사 수준, 토큰 갯수와 삽입될 장소 갯수가 맞는지 여부 확인 정도" + "\n\n";
			results = results + "quote : 토큰 앞뒤로 삽입할 특수 기호" + "\n\n";
			results = results + "expression : true 입력 시 area 구분자에 정규 표현식을 사용할 수 있게 됩니다." + "\n\n";
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
		return "InsertParam";
	}
}
