package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ReadWebModule implements ConvertModule
{
	private static final long serialVersionUID = -4244909677285292550L;
	
	public ReadWebModule()
	{
		super();
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
			return "웹 읽기";
		}
		else
		{
			return "Read web";
		}
	}

	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		try
		{
			String gets = parameters.get("url");
			if(gets == null) gets = parameters.get("defaultoption");
			return Statics.applyScript(this, parameters, Controller.readWeb(gets, threadTerm, statusViewer, false, parameters.get("charset")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
			return Controller.getString("Error") + " : " + e.getMessage();
		}
	}

	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(null, null, null, null, null, 20, parameters);
	}

	@Override
	public List<String> optionList()
	{
		Vector<String> lists = new Vector<String>();
		lists.add(Controller.getString("WEB"));
		return lists;
	}

	@Override
	public List<String> parameterKeyList()
	{
		Vector<String> lists = new Vector<String>();
		lists.add(Controller.getString("url"));
		lists.add("charset");
		return lists;
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
		return "";
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
		return "ReadWeb";
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
}
