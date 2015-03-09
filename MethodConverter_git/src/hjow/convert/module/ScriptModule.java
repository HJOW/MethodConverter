package hjow.convert.module;

import hjow.convert.javamethods.ConsoleContainer;
import hjow.convert.javamethods.DaemonContainer;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <p>This module run script.</p>
 * 
 * <p>이 모듈은 스크립트를 실행합니다.</p>
 * 
 * @author HJOW
 *
 */
public class ScriptModule extends UserDefinedModule
{
	private static final long serialVersionUID = -4691876671873492145L;
	private ConsoleContainer console;
	private boolean guiBased = false;
	private boolean needMasterDaemonContainer = true;
	private int authLevel = 10;

	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param guiBased : This should be true if the program is run as GUI mode
	 * @throws Exception
	 */
	public ScriptModule(boolean guiBased) throws Exception
	{
		super();
		this.guiBased = guiBased;
		afterInit();		
	}
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param guiBased : This should be true if the program is run as GUI mode
	 * @param authLevel : Authority level of this object
	 * @throws Exception
	 */
	public ScriptModule(boolean guiBased, int authLevel) throws Exception
	{
		super(false);
		this.guiBased = guiBased;
		this.authLevel = authLevel;
		init();
		afterInit();		
	}
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param scriptType : Script language name
	 * @param guiBased : This should be true if the program is run as GUI mode
	 * @throws Exception
	 */
	public ScriptModule(String scriptType, boolean guiBased) throws Exception
	{		
		super(false);
		this.guiBased = guiBased;
		setScriptType(scriptType);
		init();
		afterInit();
	}
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param scriptType : Script language name
	 * @param guiBased : This should be true if the program is run as GUI mode
	 * @param authLevel : Authority level of this object
	 * @throws Exception
	 */
	public ScriptModule(String scriptType, boolean guiBased, int authLevel) throws Exception
	{		
		super(false);
		this.guiBased = guiBased;
		this.authLevel = authLevel;
		setScriptType(scriptType);
		init();
		afterInit();
	}
	
	protected void afterInit()
	{		
		
	}
	
	@Override
	public void init() throws Exception
	{
		super.init();
		if(authLevel >= 3)
		{
			console = new ConsoleContainer();		
			insertObject("console", console);
			insertObject("cs", console);
		}
		if(authLevel <= 0)
		{
			insertObject("controlobject", "removed");
			insertObject("co", "removed");
		}
		StringBuffer defaultFunctions = new StringBuffer("function ls(){return console.ls()}");
		defaultFunctions = defaultFunctions.append("\nfunction pwd(){return console.pwd()}");
		defaultFunctions = defaultFunctions.append("\nfunction cd(a){return console.cd(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction run(a){return console.run(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction runCommand(a){return console.runCommand(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction mkdir(a){console.mkdir(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction rm(a){console.remove(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction remove(a){console.remove(a)}");
		
		try
		{
			eval(String.valueOf(defaultFunctions));
		}
		catch(Exception e)
		{
			
		}
	}
	/**
	 * <p>Always return true.</p>
	 * 
	 * <p>항상 true를 반환합니다.</p>
	 */
	@Override
	public boolean isAuthCode(String input_auths)
	{
		return true;
	}
	@Override
	public void setScriptType(String scriptType)
	{		
		super.setScriptType(scriptType);
		close();
		try
		{
			init();
			System.gc();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public List<String> optionList()
	{
		List<String> options = new Vector<String>();
		
		options.add("JavaScript");
		
		return options;
	}
	
	protected void beforeRunCode()
	{	
		if(! needMasterDaemonContainer) return;
		try
		{
			if(authLevel >= 5)
			{
				DaemonContainer daemons = new DaemonContainer(guiBased);
				insertObject("daemon", daemons);
				insertObject("dm", daemons);
			}
			
			StringBuffer defaultFunctions = new StringBuffer("function startDaemon(a, b, c){daemon.daemon(a, b, c)}");
			defaultFunctions = defaultFunctions.append("\nfunction stopDaemon(){daemon.stopDaemon()}");
			eval(String.valueOf(defaultFunctions));
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Turn on/off master daemon container.</p>
	 * 
	 * <p>마스터 데몬 객체를 활성화/비활성화합니다.</p>
	 * 
	 * @param en : true / false
	 */
	public void setMasterDaemonContainerEnabled(boolean en)
	{
		if(en)
		{
			beforeRunCode();
		}
		else
		{
			try
			{
				insertObject("daemon", "");
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	/**
	 * <p>Run script code.</p>
	 * 
	 * <p>스크립트를 실행합니다.</p>
	 * 
	 * @param codes : script code
	 * @return result of execution
	 * @throws Exception
	 */
	public Object runCode(String codes) throws Exception
	{
		beforeRunCode();
		return super.eval(codes);
	}
	
	/**
	 * <p>Run script code.</p>
	 * 
	 * <p>스크립트를 실행합니다.</p>
	 * 
	 * @param codes : script code
	 * @param level : privilege level
	 * @return result of execution
	 * @throws Exception
	 */
	public Object runCode(String codes, int level) throws Exception
	{
		beforeRunCode();
		return super.eval(codes);
	}
	
	/**
	 * <p>Run script.</p>
	 * 
	 * <p>스크립트를 실행합니다.</p>
	 */
	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		try
		{
			setScriptType(Controller.getSelectedSyntax());
			
			if(engine == null) init();
			if(stringTable != null) insertObject("stringTable", stringTable);
			if(syntax != null) insertObject("syntaxTable", syntax);
			if(statusViewer != null) insertObject("statusViewer", statusViewer);
			if(statusField != null) insertObject("statusField", statusField);
			insertObject("parameters", parameters);
			insertObject("threadTerm", new Long(threadTerm));
			eval(getDeclareStatements());
			
			return Statics.applyScript(this, parameters, String.valueOf(eval(before)));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
			if(statusField != null) statusField.setText(stringTable.get("Error") + " : " + e.getMessage());
			return null;
		}
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
			return "스크립트 실행";
		}
		else
		{
			return "Run Script";
		}
	}
	@Override
	public String getDefinitionName()
	{
		return "Script";
	}
	@Override
	public String getHelps()
	{
		String results = getName() + "\n\n";
		
		results = results + ScriptHelp.getHelp() + "\n\n" + getParameterHelp();
		
		return results;
	}
	@Override
	public String getUrl()
	{
		return Controller.getDefaultURL();
	}
}
