package hjow.convert.module;

import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <p>Multiple chained modules.</p>
 * 
 * <p>여러 모듈이 연결된 하나의 모듈.</p>
 * 
 * @author HJOW
 *
 */
public class MultipleModule implements ConvertModule
{
	private static final long serialVersionUID = -2418301882441198011L;
	
	private ConvertModule[] modules;
	private String[] parameters;
	private String url;
	
	/**
	 * <p>Create empty multiple module object.</p>
	 * 
	 * <p>모듈 객체를 생성합니다.</p>
	 */
	public MultipleModule()
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
		String names = "";
		for(int i=0; i<modules.length; i++)
		{
			names = names + modules[i];
			if(i < modules.length - 1) names = names + ", ";
		}
		
		return names.trim();
	}

	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		String results = before;
		
		Hashtable<String, String> resultParam = new Hashtable<String, String>();
		for(int i=0; i<modules.length; i++)
		{			
			Map<String, String> nowParam = null;
			try
			{
				if(i == 0)
				{
					if(parameters != null)
					{
						resultParam.putAll(parameters);
					}
				}
				if(this.parameters[i] == null)
				{
					if(parameters != null)
					{
						resultParam.putAll(parameters);
					}
				}
				else if(this.parameters[i].equalsIgnoreCase("override"))
				{
					nowParam = Statics.stringToHashtable(this.parameters[i]);
					if(nowParam != null)
					{
						resultParam.putAll(nowParam);
					}
				}
				else
				{
					resultParam.clear();
					nowParam = Statics.stringToHashtable(this.parameters[i]);
					if(nowParam != null)
					{
						resultParam.putAll(nowParam);
					}
				}				
			}
			catch(Exception e)
			{
				
			}
			if(parameters != null) resultParam.putAll(parameters);
			
			results = convert(stringTable, syntax, results, statusViewer, statusField, threadTerm, resultParam);
		}
		return results;
	}

	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(null, null, before, null, null, 20, parameters);
	}

	@Override
	public List<String> optionList()
	{
		Vector<String> lists = new Vector<String>();
		lists.add("Chain");
		return lists;
	}

	@Override
	public List<String> parameterKeyList()
	{
		List<String> results = new Vector<String>();
		for(int i=0; i<modules.length; i++)
		{
			results.addAll(modules[i].parameterKeyList());
			
		}
		return results;
	}

	@Override
	public String defaultParameterText()
	{
		StringBuffer results = new StringBuffer("");
		for(int i=0; i<modules.length; i++)
		{
			results = results.append(modules[i].defaultParameterText() + "\n\n");
		}
		return results.toString().trim();
	}

	@Override
	public boolean isAuthorized()
	{
		boolean authorized = true;
		for(int i=0; i<modules.length; i++)
		{
			if(! (modules[i].isAuthorized())) authorized = false;
		}
		return authorized;
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
		for(int i=0; i<modules.length; i++)
		{
			modules[i].close();
		}		
	}
	
	public void input(ConvertModule[] modules, String[] params)
	{
		this.modules = modules;
		this.parameters = params;
	}
	
	public void input(List<ConvertModule> modules, List<String> params)
	{
		this.modules = new ConvertModule[modules.size()];
		this.parameters = new String[params.size()];
		for(int i=0; i<modules.size(); i++)
		{
			this.modules[i] = modules.get(i);
		}
		for(int i=0; i<parameters.length; i++)
		{
			this.parameters[i] = params.get(i);
		}
	}

	/**
	 * <p>Return included modules.</p>
	 * 
	 * <p>포함된 모듈들을 반환합니다.</p>
	 * 
	 * @return included modules
	 */
	public ConvertModule[] getModules()
	{
		return modules;
	}

	/**
	 * <p>Includes module list.</p>
	 * 
	 * <p>모듈들을 삽입합니다.</p>
	 * 
	 * @param modules : module array
	 */
	public void setModules(ConvertModule[] modules)
	{
		this.modules = modules;
	}

	/**
	 * <p>Return included parameters.</p>
	 * 
	 * <p>포함된 매개변수들을 반환합니다.</p>
	 * 
	 * @return included parameters
	 */
	public String[] getParameters()
	{
		return parameters;
	}

	/**
	 * <p>Includes parameter list.</p>
	 * 
	 * <p>매개변수들을 삽입합니다.</p>
	 * 
	 * @param parameters : parameter array
	 */
	public void setParameters(String[] parameters)
	{
		this.parameters = parameters;
	}

	@Override
	public boolean isOptionEditable()
	{
		for(ConvertModule m : modules)
		{
			if(m.isOptionEditable()) return true;
		}
		return false;
	}

	@Override
	public String getDefinitionName()
	{
		String results = "";
		for(int i=0; i<modules.length; i++)
		{
			results = results + modules[i].getDefinitionName() + ", ";
		}
		return results.trim();
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		for(int i=0; i<modules.length; i++)
		{
			results = results + modules[i].getHelps() + "\n\n";
		}
		
		return results + "\n\n" + getParameterHelp();
	}

	@Override
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
