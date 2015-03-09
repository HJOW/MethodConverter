package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <p>This module can convert text charset.</p>
 * 
 * <p>이 모듈은 텍스트의 인코딩을 변환하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class CharEncoder implements ConvertModule, CanConvertByte
{
	private static final long serialVersionUID = -6496066945074895225L;

	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters)
	{
		try
		{
			String beforeEncoding = parameters.get("before");
			String afterEncoding = parameters.get("after");
			if(beforeEncoding == null) beforeEncoding = "EUC-KR";
			if(afterEncoding == null) afterEncoding = "UTF-8";
			return new String(befores, beforeEncoding).getBytes(afterEncoding);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.println(Controller.getString("error") + " : " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean isEncryptingModule()
	{
		return false;
	}
	@Override
	public String getName()
	{
		return getName("en");
	}
	@Override
	public String getName(String locale)
	{
		if(Statics.isKoreanLocale())
		{
			return "문자 셋 변환기";
		}
		else
		{
			return "Charset converter";
		}
	}

	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{		
		return convert(before, parameters);
	}

	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		try
		{
			String beforeEncoding = parameters.get("before");
			String afterEncoding = parameters.get("after");
			if(beforeEncoding == null) beforeEncoding = "EUC-KR";
			if(afterEncoding == null) afterEncoding = "UTF-8";
			return new String(before.getBytes(beforeEncoding), afterEncoding);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.println(Controller.getString("error") + " : " + e.getMessage());
			return null;
		}
	}
	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters,
			StatusBar statusBar, StatusViewer statusViewer)
	{
		return convert(befores, parameters);
	}
	@Override
	public List<String> optionList()
	{
		List<String> results = new Vector<String>();
		results.add("normal");
		return results;
	}

	@Override
	public List<String> parameterKeyList()
	{
		Vector<String> keys = new Vector<String>();
		keys.add("before");
		keys.add("after");
		return keys;
	}

	@Override
	public String defaultParameterText()
	{
		return "--before \"EUC-KR\" --after \"UTF-8\"";
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
		{
			results = results + "사용 가능한 키 : before, after" + "\n\n";
			results = results + "before : 현재 입력하는 텍스트의 인코딩 방식" + "\n\n";
			results = results + "after : 변환하려는 목표 인코딩 방식" + "\n\n";
			results = results + "주의 ! 이 모듈은 바이트 데이터 변환만을 공식 지원합니다.";
		}
		else
		{
			results = results + "Availables : before, after" + "\n\n";
			results = results + "before : Now character set" + "\n\n";
			results = results + "after : Character set that the text will be." + "\n\n";
			results = results + "Notice ! This module is only for byte data." + "\n\n";
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
		return "CharEncoder";
	}

	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
}
