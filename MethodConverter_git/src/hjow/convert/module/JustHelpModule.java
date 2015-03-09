package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;

import java.util.List;
import java.util.Vector;


/**
 * <p>This module can only have help message.</p>
 * 
 * <p>이 모듈은 도움말 메시지만을 가지고 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class JustHelpModule implements Module
{
	private static final long serialVersionUID = 788931036999858881L;

	@Override
	public String getName()
	{		
		return Controller.TITLES;
	}

	@Override
	public String getName(String locale)
	{		
		return Controller.getString(Controller.TITLES);
	}

	@Override
	public List<String> optionList()
	{		
		return new Vector<String>();
	}

	@Override
	public List<String> parameterKeyList()
	{		
		return new Vector<String>();
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
	public String getParameterHelp()
	{
		
		return "";
	}

	@Override
	public String getDefinitionName()
	{
		
		return "Help";
	}

	@Override
	public boolean isOptionEditable()
	{
		
		return false;
	}

	@Override
	public String getHelps()
	{
		String results = "";
		if(Statics.isKoreanLocale())
		{
			results = results + Controller.getString(Controller.TITLES) + "\n";
			results = results + "" + "\n";
			results = results + "이 프로그램은 텍스트를 다른 텍스트로 변환하는 데 사용됩니다." + "\n";
			results = results + "선택한 모듈에 따라 변환하는 목적과 방식이 달라집니다." + "\n";
			results = results + "" + "\n";
			results = results + "변환을 위해, 변환할 텍스트를 화면 중앙의 영역에 입력하고," + "\n";
			results = results + "그 영역 위쪽의 \"매개변수\" 란 오른쪽에 있는 버튼을 눌러 필요한 데이터를 넣습니다." + "\n";
			results = results + "" + "\n";
			results = results + "예를 들어, 텍스트를 암호화해 비밀번호 없이는 다른 사람이 알아볼 수 없게 만들거나," + "\n";
			results = results + "그 반대로 원래의 텍스트로 바꿀 수 있습니다." + "\n";
			results = results + "이 때 매개변수로는 암호화에 쓰일 비밀번호와 암호화 방식을 지정할 수 있습니다." + "\n";
			results = results + "" + "\n";
			results = results + "여러가지 모듈이 기본적으로 탑재되어 있습니다." + "\n";
			results = results + "좌측 리스트에서 모듈에 대한 도움말을 보실 수 있습니다." + "\n";
		}
		else
		{
			results = results + Controller.getString(Controller.TITLES) + "\n";
			results = results + "" + "\n";
			results = results + "" + "\n";
			results = results + "" + "\n";
			results = results + "You can select module to see their helps." + "\n";
		}
		return results + "\n\n" + Controller.getString(Controller.licenseMessage);
	}

}
