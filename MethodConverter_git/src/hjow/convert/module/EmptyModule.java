package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <p>This module do nothing.</p>
 * 
 * <p>이 모듈은 아무 것도 하지 않습니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class EmptyModule implements BothModule
{
	private static final long serialVersionUID = -7425252706869352037L;
	private List<String> options = new Vector<String>();
	private List<String> parameterKeyList = new Vector<String>();
	
	public EmptyModule()
	{
		super();
		options.add("Nothing");
		parameterKeyList.add("Nothing");
	}

	@Override
	public String getName()
	{
		return getName("en");
	}
	@Override
	public String getName(String locale)
	{
		return Controller.getString("Empty");
	}

	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		return before;
	}

	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return before;
	}

	@Override
	public List<String> optionList()
	{		
		return options;
	}

	@Override
	public List<String> parameterKeyList()
	{
		return parameterKeyList;
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
	public byte[] convert(byte[] befores, Map<String, String> parameters)
	{
		return befores;
	}

	@Override
	public boolean isEncryptingModule()
	{
		return true;
	}

	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters,
			StatusBar statusBar, StatusViewer statusViewer)
	{
		return befores;
	}

	public List<String> getOptions()
	{
		return options;
	}

	public void setOptions(List<String> options)
	{
		this.options = options;
	}

	public List<String> getParameterKeyList()
	{
		return parameterKeyList;
	}

	public void setParameterKeyList(List<String> parameterKeyList)
	{
		this.parameterKeyList = parameterKeyList;
	}

	@Override
	public boolean isOptionEditable()
	{
		return true;
	}

	@Override
	public String getDefinitionName()
	{
		return null;
	}
	@Override
	public String getHelps()
	{
		return "";
	}
}
