package hjow.convert.module;

import hjow.convert.javamethods.ControlContainer;
import hjow.convert.javamethods.MethodContainer;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;
import hjow.network.SerializableObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p>This module will be created by someone.<br>
 * This object can include JavaScript code.</p>
 * 
 * <p>이 모듈은 누군가에 의해 만들어질 것입니다.<br>
 * 이 모듈 객체는 자바스크립트 코드를 내장할 수 있으며, 변환 작업 시 해당 스크립트가 동작합니다.</p>
 * 
 * <p>Available options<br>
 * name, scriptType, finalizeCall, auth, addauth, options<br><br>
 * name : Module name.<br>
 * scriptType : Type of the script. JavaScript.<br>
 * finalizeCall : Script code which will be executed when the program is closing.<br>
 * auth : First authority code.<br>
 * addauth : Second authority code.<br>
 * options : Available options user can select.
 * </p>
 * 
 * @author HJOW
 *
 */
public class UserDefinedModule implements ConvertModule, SerializableObject
{
	private static final long serialVersionUID = -4532870657210005227L;
	protected transient Object engine = null;
	private String name = "";
	private String scriptType = "JavaScript";
	private List<String> options = new Vector<String>();
	protected String declareStatements = defaultDeclareString();
	private String declareHelps = "";
	private String auths = "";
	private String addauth = "";
	private String finalizeCall = "close()";
	private String defaultParameterText = null;
	private Boolean optionEditable = new Boolean(true);
	protected transient ControlContainer controlContainer = null;
	private Long definitionId = null;
	
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 * 
	 * @throws Exception
	 */
	public UserDefinedModule() throws Exception
	{
		super();
		definitionId = new Long(new Random().nextLong());
		init();		
	}
		
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 * 
	 * @param callInit : If this is true, init() method will be called.
	 * @throws Exception
	 */
	public UserDefinedModule(boolean callInit) throws Exception
	{
		super();
		definitionId = new Long(new Random().nextLong());
		if(callInit) init();
	}
	/**
	 * <p>Create module from text.</p>
	 * <p>The line which is started with # will be eliminated.</p>
	 * <p>Else, key:value; form. Key can be name, options, ...</p>
	 * <p>The line which is started with * is starting line of script codes.</p>
	 * 
	 * <p>텍스트로부터 모듈 객체를 만듭니다.</p>
	 * <p>#으로 시작하는 줄은 제거됩니다.</p>
	 * <p>그 외에는 key:value; 형식을 사용합니다. 키에는 name, options 가 올 수 있습니다.</p>
	 * <p>*으로 시작하는 줄부터 스크립트 코드로 인식합니다.</p>
	 * 
	 * @param contents : text
	 * @throws Exception
	 */
	public UserDefinedModule(String contents) throws Exception
	{
		super();
		StringTokenizer lineToken, keyToken;
		String lines, keys, values, reaccum = "";
		String contentPart = "";
		boolean startContent = false;
		
		Hashtable<String, String> getTables = new Hashtable<String, String>();
		
		lineToken = new StringTokenizer(contents, "\n");
		while(lineToken.hasMoreTokens())
		{
			lines = lineToken.nextToken();
			lines = lines.trim();
			if(lines.startsWith("#")) continue;
			if(lines.startsWith("*"))
			{
				startContent = true;
			}
			if(! startContent) reaccum = reaccum + lines + "\n";
			else contentPart = contentPart + lines + "\n";
		}
		
		declareStatements = new String(contentPart);
		
		lineToken = new StringTokenizer(reaccum, ";");
		while(lineToken.hasMoreTokens())
		{
			lines = lineToken.nextToken();
			lines = lines.trim();
			if(lines.equals("")) continue;
			
			keyToken = new StringTokenizer(lines, Controller.delimiter());
			keys = keyToken.nextToken();
			if(keyToken.hasMoreTokens())
			{
				values = keyToken.nextToken().trim();
				while(keyToken.hasMoreTokens())
				{
					values = values + Controller.delimiter() + keyToken.nextToken();
				}
			}
			else
			{
				values = "true";
			}
			getTables.put(keys, values);
		}
		
		
		name = getTables.get("name");
		
		if(getTables.get("scriptType") != null)
		{
			scriptType = getTables.get("scriptType");
		}	
		if(getTables.get("finalizeCall") != null)
		{
			finalizeCall = getTables.get("finalizeCall");
		}	
		
		if(getTables.get("auth") != null)
		{
			auths = getTables.get("auth");
		}	
		if(getTables.get("addauth") != null)
		{
			addauth = getTables.get("addauth");
		}
		
		if(getTables.get("definition") != null)
		{
			try
			{
				definitionId = new Long(getTables.get("definition"));
			}
			catch (Exception e)
			{
				
			}
		}
		
		keyToken = new StringTokenizer(getTables.get("options"), ",");
		while(keyToken.hasMoreTokens())
		{
			options.add(keyToken.nextToken().trim());
		}
		
		init();
	}
	
	/**
	 * <p>Return all data of this module as text.</p>
	 * 
	 * <p>이 모듈의 모든 데이터를 텍스트로 반환합니다.</p>
	 * 
	 * @return text
	 */
	@Override
	public String toString()
	{
		StringBuffer results = new StringBuffer(" \n");
		results = results.append("name" + Controller.delimiter() + name + ";\n");
		results = results.append("scriptType" + Controller.delimiter() + scriptType + ";\n");
		results = results.append("finalizeCall" + Controller.delimiter() + finalizeCall + ";\n");
		results = results.append("auth" + Controller.delimiter() + auths + ";\n");
		results = results.append("addauth" + Controller.delimiter() + addauth + ";\n");
		results = results.append("option" + Controller.delimiter());
		
		for(int i=0; i<options.size(); i++)
		{
			results = results.append(options.get(i));
			if(i < options.size() - 1) results = results.append(",");
		}
		results = results.append(";\n");
		results = results.append("*statements\n");
		results = results.append(declareStatements);
		
		return String.valueOf(results);
	}
	
	/**
	 * <p>Create module from file.</p>
	 * 
	 * <p>파일로부터 모듈을 만듭니다.</p>
	 * 
	 * @param file : File object
	 * @throws Exception 
	 */
	public UserDefinedModule(File file) throws Exception
	{
		if(file == null)
		{
			throw new NullPointerException();
		}
		if(! file.exists())
		{
			throw new FileNotFoundException();
		}
		
		UserDefinedModule getModule = load(file);		
		setAll(getModule);	
	}
	
	/**
	 * <p>Input all data from old object.</p>
	 * 
	 * <p>이전의 객체로부터 데이터를 모두 옮겨와 담습니다.</p>
	 * 
	 * @param other
	 * @throws Exception
	 */
	public UserDefinedModule(UserDefinedModule other) throws Exception
	{
		super();
		setAll(other);
	}
	
	/**
	 * <p>Input all data from old object.</p>
	 * 
	 * <p>이전의 객체로부터 데이터를 모두 옮겨와 담습니다.</p>
	 * 
	 * @param other
	 * @throws Exception
	 */
	public void setAll(UserDefinedModule other) throws Exception
	{
		setAddauth(other.getAddauth());
		setAuths(other.getAuths());
		setDeclareHelps(other.getDeclareHelps());
		setDeclareStatements(other.getDeclareStatements());
		setDefaultParameterText(other.getDefaultParameterText());
		setFinalizeCall(other.getFinalizeCall());
		setName(other.getName());
		setOptions(other.getOptions());
		setScriptType(other.getScriptType());
		init();
	}
	
	/**
	 * <p>Initialize the object and prepare script engine.</p>
	 * 
	 * <p>객체를 초기화하고 스크립트 엔진을 준비합니다.</p>
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception
	{
		javax.script.ScriptEngineManager scriptEngine = new javax.script.ScriptEngineManager();
		engine = scriptEngine.getEngineByName(scriptType);
		if(controlContainer == null) controlContainer  = new ControlContainer(engine, this);
		
		ScriptHelp helps = new ScriptHelp();
		MethodContainer joCont = new MethodContainer();		
		
		try
		{
			insertObject("javaobject", joCont);
			insertObject("controlobject", controlContainer);
			insertObject("jo", joCont);	
			insertObject("co", controlContainer);
			insertObject("helpobject", helps);
			
			eval(Controller.getLoadedScript());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.println(Controller.getString("Error occurs while scripts which is load from js files are run") + " : " + e.getMessage());
		}
		
		try
		{		
			insertObject("javaobject", joCont);
			insertObject("controlobject", controlContainer);
			insertObject("jo", joCont);	
			insertObject("co", controlContainer);
			insertObject("helpobject", helps);
		}
		catch(Exception e)
		{
			
		}
		
		StringBuffer defaultFunctions = new StringBuffer("function pow(a, b){return jo.pow(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction random(a){return jo.random(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction round(a){return jo.round(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction floor(a){return jo.floor(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction ceil(a){return jo.ceil(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction abs(a){return jo.abs(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction sin(a){return jo.sin(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction cos(a){return jo.cos(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction tan(a){return jo.tan(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction asin(a){return jo.asin(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction acos(a){return jo.acos(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction atan(a){return jo.atan(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction toDegrees(a){return jo.toDegrees(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction toRadians(a){return jo.toRadians(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction pi(){return jo.pi()}");
		defaultFunctions = defaultFunctions.append("\nfunction e(){return jo.e()}");
		defaultFunctions = defaultFunctions.append("\nfunction bigint(a){return jo.bigint(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction bigdec(a){return jo.bigdec(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction setStatusText(a){jo.setStatusText(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction getOption(a){return jo.getOption(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction parseDouble(a){return jo.parseDouble(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction parseBoolean(a){return jo.parseBoolean(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction parseInt(a){return jo.parseInt(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction newJavaString(a, b){return jo.newJavaString(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction newJavaString(a){return jo.newJavaString(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction replace(a, b, c){return jo.replace(a, b, c)}");
		defaultFunctions = defaultFunctions.append("\nfunction trim(a){return jo.trim(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction stringTokenizer(a, b){return jo.stringTokenizer(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction substring(a, b){return jo.substring(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction substring(a, b, c){return jo.substring(a, b, c)}");
		defaultFunctions = defaultFunctions.append("\nfunction newList(){return jo.newList()}");
		defaultFunctions = defaultFunctions.append("\nfunction newArray(a){return jo.newArray(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction newByteArray(a){return jo.newByteArray(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction newIntegerArray(a){return jo.newIntegerArray(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction newCharacterArray(a){return jo.newCharacterArray(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction isException(a){return jo.isException(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction stringToBytes(a){return jo.stringToBytes(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction bytesToString(a){return jo.bytesToString(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction r65279(a){return jo.r65279(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction encrypt(a, b, c){return jo.encrypt(a, b, c)}");
		defaultFunctions = defaultFunctions.append("\nfunction decrypt(a, b, c){return jo.decrypt(a, b, c)}");
		defaultFunctions = defaultFunctions.append("\nfunction sleep(a){jo.sleep(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction getSystemProperty(a){return co.getSystemProperty(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction currentTimeMills(){return co.currentTimeMills()}");
		defaultFunctions = defaultFunctions.append("\nfunction totalMemory(){return co.totalMemory()}");
		defaultFunctions = defaultFunctions.append("\nfunction freeMemory(){return co.freeMemory()}");
		defaultFunctions = defaultFunctions.append("\nfunction maxMemory(){return co.maxMemory()}");
		defaultFunctions = defaultFunctions.append("\nfunction availableProcessors(){return co.availableProcessors()}");
		defaultFunctions = defaultFunctions.append("\nfunction eval(a){return co.eval(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction optionList(){return co.optionList()}");
		defaultFunctions = defaultFunctions.append("\nfunction getSelectedOption(){return co.getSelectedOption()}");
		defaultFunctions = defaultFunctions.append("\nfunction getModuleName(){return co.getModuleName()}");
		defaultFunctions = defaultFunctions.append("\nfunction isAuthCode(a){return co.isAuthCode(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction isAvailable(a, b){return co.isAvailable(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction file(a, b){return co.file(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction inputStreamReader(a, b){return co.inputStreamReader(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction outputStreamWriter(a, b){return co.outputStreamWriter(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction buffered(a, b){return co.buffered(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction fileReader(a, b){return co.fileReader(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction fileWriter(a, b){return co.fileWriter(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction readByte(a, b){return co.readByte(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction saveByte(a, b, c){co.saveByte(a, b, c)}");
		defaultFunctions = defaultFunctions.append("\nfunction openUrl(a, b){return co.openUrl(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction newObject(a, b){return co.newObject(a, b)}");
		defaultFunctions = defaultFunctions.append("\nfunction exit(a){co.exit(a)}");
		defaultFunctions = defaultFunctions.append("\nfunction runOnBackground(a, b, c, d){co.runOnBackground(a, b, c, d)}");
		defaultFunctions = defaultFunctions.append("\nfunction getThreadState(){return co.getThreadState()}");
		
		
		try
		{
			eval(String.valueOf(defaultFunctions));
		}
		catch(Exception e)
		{
			
		}
		try
		{
			eval("function help(){ helpobject.help(); }");
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			eval(declareHelps);
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Save module as file.</p>
	 * 
	 * <p>모듈을 파일로 저장합니다.</p>
	 * 
	 * @param file : File object
	 * @throws Exception
	 */
	public void save(File file) throws Exception
	{
		int saveMode = 0;
		boolean gzipping = false;
		
		if(file.getAbsolutePath().endsWith(".bmodule") || file.getAbsolutePath().endsWith(".BMODULE"))
		{
			saveMode = 1;
			gzipping = false;
		}
		else if(file.getAbsolutePath().endsWith(".xmodule") || file.getAbsolutePath().endsWith(".XMODULE"))
		{
			saveMode = 2;
			gzipping = false;
		}
		else if(file.getAbsolutePath().endsWith(".bzmodule") || file.getAbsolutePath().endsWith(".BZMODULE"))
		{
			saveMode = 1;
			gzipping = true;
		}		
		else if(file.getAbsolutePath().endsWith(".xzmodule") || file.getAbsolutePath().endsWith(".XZMODULE"))
		{
			saveMode = 2;
			gzipping = true;
		}
		else if(file.getAbsolutePath().endsWith(".xml") || file.getAbsolutePath().endsWith(".XML"))
		{
			saveMode = 2;
			gzipping = false;
		}
		else
		{
			saveMode = 0;
			gzipping = false;
		}
		
		if(saveMode == 0)
		{
			FileWriter writer = null;
			BufferedWriter buffered = null;
			try
			{
				writer = new FileWriter(file);
				buffered = new BufferedWriter(writer);
				
				String saves = toString();
				saves = saves.replace("\n\n", "\n \n");
				StringTokenizer lineToken = new StringTokenizer(saves, "\n");
				while(lineToken.hasMoreTokens())
				{
					buffered.write(lineToken.nextToken());
					buffered.newLine();
				}
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{				
				try
				{
					buffered.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					writer.close();
				}
				catch(Exception e)
				{
					
				}
			}
		}
		else if(saveMode == 2)
		{
			FileOutputStream outputStream = null;
			java.beans.XMLEncoder xmlOutput = null;
			OutputStream zipper = null;
			try
			{
				outputStream = new FileOutputStream(file);
				if(gzipping)
				{
					zipper = new java.util.zip.GZIPOutputStream(outputStream);
					xmlOutput = new java.beans.XMLEncoder(zipper);
				}
				else
				{
					xmlOutput = new java.beans.XMLEncoder(outputStream);
				}				
				
				xmlOutput.writeObject(this);
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				try
				{
					xmlOutput.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					zipper.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					outputStream.close();
				}
				catch(Exception e)
				{
					
				}
			}
		}
		else
		{
			FileOutputStream outputStream = null;
			ObjectOutputStream objectOutput = null;
			OutputStream zipper = null;
			try
			{
				outputStream = new FileOutputStream(file);
				
				if(gzipping)
				{
					zipper = new java.util.zip.GZIPOutputStream(outputStream);
					objectOutput = new ObjectOutputStream(zipper);
				}
				else
				{
					objectOutput = new ObjectOutputStream(outputStream);
				}	
				
				objectOutput.writeObject(this);
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				try
				{
					objectOutput.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					zipper.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					outputStream.close();
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}
	
	/**
	 * <p>Run script code.</p>
	 * 
	 * <p>스크립트 코드를 실행합니다.</p>
	 * 
	 * 
	 * @param code : script code
	 * @return result of script run
	 * @throws Exception
	 */
	protected Object eval(String code) throws Exception
	{
		//Controller.println(code);
		return ((javax.script.ScriptEngine) engine).eval(code);
	}
	
	/**
	 * <p>Insert object in script scope.</p>
	 * 
	 * <p>스크립트 범위에 객체를 삽입합니다. 스크립트 내에서 사용할 수 있게 됩니다.</p>
	 * 
	 * @param keys : variable name
	 * @param object : object
	 * @throws Exception
	 */
	public void insertObject(String keys, Object object) throws Exception
	{
		((javax.script.ScriptEngine) engine).put(keys, object);
	}
	
	@Override
	public String getName(String locale)
	{
		return name;
	}
	
	/**
	 * <p>Return true if this module can convert byte data.</p>
	 * 
	 * <p>이 모듈이 바이트 데이터를 변환할 수 있는지의 여부를 반환합니다.</p>
	 * 
	 * @return true if this module can convert byte data
	 */
	public boolean canConvertByte()
	{
		try
		{
			Object ob = eval("convertByte()");
			if(ob == null) return false;
			else if(ob instanceof byte[]) return true;
			else return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(null, null, before, null, null, 20, parameters);
	}
	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		try
		{
			if(engine == null) init();
			if(stringTable != null) insertObject("stringTable", stringTable);
			if(syntax != null) insertObject("syntaxTable", syntax);
			insertObject("original", before);
			if(statusViewer != null) insertObject("statusViewer", statusViewer);
			if(statusField != null) insertObject("statusField", statusField);
			insertObject("parameters", parameters);
			insertObject("originalBytes", null);
			insertObject("threadTerm", new Long(threadTerm));
			
			Controller.println("Run following scripts in this module.");
			Controller.println(declareStatements);
			
			eval(declareStatements);
			
			return Statics.applyScript(this, parameters, String.valueOf(eval("convert()")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
			if(statusField != null) statusField.setText(stringTable.get("Error") + " : " + e.getMessage());
			return before;
		}
	}
	@Override
	public List<String> optionList()
	{
		return options;
	}

	

	/**
	 * <p>Return this modules name.</p>
	 * 
	 * <p>모듈 이름을 반환합니다.</p>
	 * 
	 * @return modules name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * <p>Modify this modules name.</p>
	 * 
	 * <p>모듈 이름을 변경합니다.</p>
	 * 
	 * @param name : new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * <p>Return script code included.</p>
	 * 
	 * <p>내장된 스크립트 내용을 반환합니다.</p>
	 * 
	 * @return script codes
	 */
	public String getDeclareStatements()
	{
		return declareStatements;
	}

	/**
	 * <p>스크립트 내용을 변경합니다.</p>
	 * 
	 * <p>Modify script code.</p>
	 * 
	 * @param declareStatements : new script code
	 */
	public void setDeclareStatements(String declareStatements)
	{
		this.declareStatements = declareStatements;
	}

	/**
	 * <p>Return available options of this module.</p>
	 * 
	 * <p>사용 가능한 옵션들을 반환합니다.</p>
	 * 
	 * @return options
	 */
	public List<String> getOptions()
	{
		return options;
	}

	/**
	 * <p>Set available option list.</p>
	 * 
	 * <p>사용 가능 옵션 리스트를 변경합니다.</p>
	 * 
	 * @param options : new option list
	 */
	public void setOptions(List<String> options)
	{
		this.options = options;
	}
	/**
	 * <p>Return script type.</p>
	 * 
	 * <p>스크립트 타입을 반환합니다.</p>
	 * 
	 * @return type of script
	 */
	public String getScriptType()
	{
		return scriptType;
	}
	/**
	 * <p>Modify script type. Default value is JavaScript. Python, Ruby may be available with those additional library.</p>
	 * 
	 * <p>스크립트 타입을 변경합니다. 기본값은 JavaScript 입니다.</p>
	 * 
	 * 
	 * @param scriptType : new script type
	 */
	public void setScriptType(String scriptType)
	{
		this.scriptType = scriptType;
	}
	
	
	
	/**
	 * <p>Return authorize code inside the object.</p>
	 * 
	 * <p>이 객체의 인증 코드를 반환합니다.</p>
	 * 
	 * 
	 * @return authorize code
	 */
	public String getAuths()
	{
		return auths;
	}
	
	/**
	 * <p>Input encoded authorize code.</p>
	 * 
	 * <p>인증 코드를 입력합니다.</p>
	 * 
	 * @param auths : authorize code
	 */
	public void setAuths(String auths)
	{
		this.auths = auths;
	}
	
	/**
	 * <p>Return additional auth code.</p>
	 * 
	 * <p>추가 인증 코드를 반환합니다.</p>
	 * 
	 * @return additional authorize code
	 */
	public String getAddauth()
	{
		return addauth;
	}

	/**
	 * <p>Set additional auth code.</p>
	 * 
	 * <p>추가 인증 코드를 변경합니다.</p>
	 * 
	 * @param addauth : additional authorize code
	 */
	public void setAddauth(String addauth)
	{
		this.addauth = addauth;
	}
	/**
	 * <p>This is true if this module is authorized.</p>
	 * 
	 * <p>인증된 모듈인지 여부를 반환합니다.</p>
	 * 
	 * @return true if this module is authorized.
	 */
	public boolean isAuthorized()
	{
		if(auths == null) return false;
		return convertAuth(auths + addauth).trim().equalsIgnoreCase("bba7188336c1c7df33cb105fc12392171914bfd5");
	}
	
	private String convertAuth(String auth)
	{
		MessageDigest digest = null;
		try
		{
			digest = MessageDigest.getInstance("SHA-1");
						
			byte[] textArray = digest.digest(auth.trim().getBytes());
			StringBuffer results = new StringBuffer("");
			
			for(int i=0; i<textArray.length; i++)
			{
				results.append( Integer.toString(((textArray[i] & 0xf0) >> 4), 16) );
				results.append(Integer.toString((textArray[i] & 0x0f), 16));
			}
									
			return String.valueOf(results);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public boolean isAuthCode(String input_auths)
	{
		int final_auth = 0;
		final_auth = final_auth + 3952;
		String converted_auths = convertAuth(auths + addauth + String.valueOf(final_auth));			
		return converted_auths.equals(input_auths);
	}


	@Override
	public void close()
	{
		try
		{
			eval(finalizeCall);
		}
		catch(Exception e)
		{
			
		}
		
		engine = null;
		try
		{
			controlContainer.close();
		}
		catch(Exception e)
		{
			
		}
	}

	/**
	 * <p>Returns finalizeCall option.</p>
	 * 
	 * <p>finalizeCall 옵션을 반환합니다.</p>
	 * 
	 * @return finalizeCall option value
	 */
	public String getFinalizeCall()
	{
		return finalizeCall;
	}

	/**
	 * <p>Modify finalizeCall option.</p>
	 * 
	 * <p>finalizeCall 옵션을 변경합니다.</p>
	 * 
	 * @param finalizeCall : finalizeCall option value
	 */
	public void setFinalizeCall(String finalizeCall)
	{
		this.finalizeCall = finalizeCall;
	}
	
	/**
	 * <p>Return helps about creating function with default statements.</p>
	 * 
	 * <p>함수를 만드는 데 필요한 도움말과 함께 기본 선언문을 반환합니다.</p>
	 * 
	 * @return helps
	 */
	public static String defaultDeclareString()
	{
		String locale = "";
		try
		{
			locale = Controller.getOption("locale");
			if(locale == null) locale = System.getProperty("user.language");
			if(locale == null) locale = "";
		}
		catch(Exception e)
		{
			
		}
		
		StringBuffer r = new StringBuffer("");
		
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{	    
		    r = r.append("  \n/*\n");
		    r = r.append("입력받은 텍스트를 변환해 반환하는 convert() 함수를 만드세요. \n");
		    r = r.append("이 함수를 만들기 위해 다음과 같이 이미 선언된 변수를 사용할 수 있습니다.\n\n");
		    r = r.append("original : 사용자가 입력한 텍스트(String)가 들어 있는 변수\n");
		    r = r.append("parameters : 키와 값이 모두 String 인 Hashtable 객체.\n");
		    r = r.append("          (특별한 키 : defaultoption, option)\n");
		    r = r.append("threadTerm : 쓰레드 간격 설정값\n");
		    r = r.append("statusViewer : 프로세스 상태를 알려주는 상태바 객체\n");
		    r = r.append("statusField : 텍스트로 상태를 알려주는 상태바 객체\n");
		    r = r.append("stringTable : 키와 값이 모두 String 인 Hashtable 객체. 시스템 언어에 따라 값이 다름.\n");
		    r = r.append("*/\n\n");
		    r = r.append("function convert()\n");
		    r = r.append("{\n");
		    r = r.append("    var results = original;\n\n");
		    r = r.append("    // 스크립트를 이 곳에 삽입\n\n");
		    r = r.append("    return results;\n");
		    r = r.append("}\n");
		    r = r.append("\n\n");
		    r = r.append("/*\n");
		    r = r.append("파라미터 입력 창에 나타나는 도움말을 반환하는 getParameterHelps() 함수를 만드세요.\n");
		    r = r.append("필요하지 않다면 내용이 없는 함수로 그대로 두셔도 됩니다.\n");
		    r = r.append("*/\n");
		    r = r.append("function getParameterHelps()\n");
		    r = r.append("{\n");
		    r = r.append("    return \'\';\n");
		    r = r.append("}\n");
		    r = r.append("\n\n");
		    r = r.append("/*\n");
		    r = r.append("도움말 창에 나타나는 도움말을 반환하는 getHelps() 함수를 만드세요.\n");
		    r = r.append("필요하지 않다면 내용이 없는 함수로 그대로 두셔도 됩니다.\n");
		    r = r.append("*/\n");
		    r = r.append("function getHelps()\n");
		    r = r.append("{\n");
		    r = r.append("    return \'\';\n");
		    r = r.append("}\n");
		    r = r.append("\n\n");
		    r = r.append("/*\n");
		    r = r.append("프로그램이 종료될 때 실행될 함수 close() 를 만드세요.\n");
		    r = r.append("필요하지 않다면 내용이 없는 함수로 그대로 두셔도 됩니다.\n");
		    r = r.append("*/\n\n");
		    r = r.append("function close()\n");
		    r = r.append("{\n");
		    r = r.append("    \n");
		    r = r.append("}\n");
		    
		}
		else
		{
			r = r.append("  \n/*\n");
		    r = r.append("Create convert() function which is convert and return text. \n");
		    r = r.append("You can use following variables to make convert() function.\n\n");
		    r = r.append("original : Original string user input. String type\n");
		    r = r.append("parameters : Hashtable object that both key and variables are String.\n");
		    r = r.append("          (Special available keys : defaultoption, option)\n");
		    r = r.append("threadTerm : Thread term setting value, int type.\n");
		    r = r.append("statusViewer : Status viewer object, shown as progress bar.\n");
		    r = r.append("statusField : Status field object, shown as status bar.\n");
		    r = r.append("stringTable : Hashtable object that both key and variables are String. Language setting.\n");
		    r = r.append("*/\n\n");
		    r = r.append("function convert()\n");
		    r = r.append("{\n");
		    r = r.append("    var results = original;\n\n");
		    r = r.append("    // Input script here\n\n");
		    r = r.append("    return results;\n");
		    r = r.append("}\n");
		    r = r.append("\n\n");
		    r = r.append("/*\n");
		    r = r.append("Create getParameterHelps() function which return help message about parameters.");
		    r = r.append("If you don't make this function, just make this function empty.\n");
		    r = r.append("*/\n");
		    r = r.append("function getParameterHelps()\n");
		    r = r.append("{\n");
		    r = r.append("    return \'\';\n");
		    r = r.append("}\n");
		    r = r.append("\n\n");
		    r = r.append("/*\n");
		    r = r.append("Create getHelps() function which return help message.");
		    r = r.append("If you don't make this function, just make this function empty.\n");
		    r = r.append("*/\n");
		    r = r.append("function getHelps()\n");
		    r = r.append("{\n");
		    r = r.append("    return \'\';\n");
		    r = r.append("}\n");
		    r = r.append("\n\n");
		    r = r.append("/*\n");
		    r = r.append("Create close() function which is called when the program is closing.\n");
		    r = r.append("If you don't make this function, just make this function empty.\n");
		    r = r.append("*/\n\n");
		    r = r.append("function close()\n");
		    r = r.append("{\n");
		    r = r.append("    \n");
		    r = r.append("}\n");
		}
		
	    return r.toString() + "\n" + UserDefinedByteModule.defaultDeclareByteString();
	}

	@Override
	public String defaultParameterText()
	{
		return defaultParameterText;
	}

	/**
	 * <p>Return default parameter text.</p>
	 * 
	 * <p>매개 변수 텍스트 기본값을 반환합니다.</p>
	 * 
	 * @return default parameter text
	 */
	public String getDefaultParameterText()
	{
		return defaultParameterText;
	}

	/**
	 * <p>Set default parameter text.</p>
	 * 
	 * <p>매개 변수 텍스트 기본값을 설정합니다.</p>
	 * 
	 * @param defaultParameterText : default parameter text
	 */
	public void setDefaultParameterText(String defaultParameterText)
	{
		this.defaultParameterText = defaultParameterText;
	}

	@Override
	public String getParameterHelp()
	{
		try
		{
			eval(declareStatements);
		}
		catch(Exception e)
		{
			
		}
		try
		{
			return String.valueOf(eval("getParameterHelps()"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * <p>Return function-declare statement which return parameter helps.</p>
	 * 
	 * <p>매개 변수 도움말을 반환하는 함수 선언문을 반환합니다.</p>
	 * 
	 * @return function-declare statement
	 */
	public String getDeclareHelps()
	{
		return declareHelps;
	}

	/**
	 * <p>Set function-declare statement which return parameter helps.</p>
	 * 
	 * <p>매개 변수 도움말을 반환하는 함수 선언문을 설정합니다.</p>
	 * 
	 * @param declareHelps : function-declare statement
	 */
	public void setDeclareHelps(String declareHelps)
	{
		this.declareHelps = declareHelps;
	}
	
	
	/**
	 * <p>Read module from file.</p>
	 * 
	 * <p>파일로부터 모듈을 읽습니다.</p>
	 * 
	 * @param file : File object
	 * @return module
	 * @throws Exception
	 */
	public static UserDefinedModule load(File file) throws Exception
	{
		UserDefinedModule module = null;
		
		if(file == null)
		{
			throw new NullPointerException();
		}
		if(! file.exists())
		{
			throw new FileNotFoundException();
		}
		
		FileInputStream inputStream = null;
		ObjectInputStream objectStream = null;
		InputStream zipper = null;
		
		int mode = 0;
		boolean zipping = false;
		if(file.getAbsolutePath().endsWith(".bmodule") || file.getAbsolutePath().endsWith(".BMODULE")) 
		{
			mode = 1;
			zipping = false;
		}
		else if(file.getAbsolutePath().endsWith(".bzmodule") || file.getAbsolutePath().endsWith(".BZMODULE")) 
		{
			mode = 1;
			zipping = true;
		}
		else if(file.getAbsolutePath().endsWith(".xmodule") || file.getAbsolutePath().endsWith(".XMODULE")) 
		{
			mode = 2;
			zipping = false;
		}
		else if(file.getAbsolutePath().endsWith(".xzmodule") || file.getAbsolutePath().endsWith(".XZMODULE")) 
		{
			mode = 2;
			zipping = true;
		}
		else if(file.getAbsolutePath().endsWith(".xml") || file.getAbsolutePath().endsWith(".XML")) 
		{
			mode = 2;
			zipping = false;
		}
		else
		{
			mode = 0;
			zipping = false;
		}
		
		if(mode == 1)
		{
			try
			{
				inputStream = new FileInputStream(file);
				if(zipping)
				{
					zipper = new java.util.zip.GZIPInputStream(inputStream);
					objectStream = new ObjectInputStream(zipper);
				}
				else
				{
					objectStream = new ObjectInputStream(inputStream);
				}			
				
				module = (UserDefinedModule) objectStream.readObject();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					objectStream.close();
				}
				catch(Exception e1)
				{
					
				}
				try
				{
					zipper.close();
				}
				catch(Exception e1)
				{
					
				}
				try
				{
					inputStream.close();
				}
				catch(Exception e1)
				{
					
				}
			}
		}
		if(mode == 2)
		{
			java.beans.XMLDecoder xmlInput = null;
			try
			{
				inputStream = new FileInputStream(file);
				if(zipping)
				{
					zipper = new java.util.zip.GZIPInputStream(inputStream);
					xmlInput = new java.beans.XMLDecoder(zipper);
				}
				else
				{
					xmlInput = new java.beans.XMLDecoder(inputStream);
				}			
				
				module = (UserDefinedModule) xmlInput.readObject();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					xmlInput.close();
				}
				catch(Exception e1)
				{
					
				}
				try
				{
					zipper.close();
				}
				catch(Exception e1)
				{
					
				}
				try
				{
					inputStream.close();
				}
				catch(Exception e1)
				{
					
				}
			}
		}
		else
		{
			try
			{
				String reads = Controller.readFile(file, 20, null);
				UserDefinedModule newModule = new UserDefinedModule(reads);
				if(newModule.canConvertByte())
				{
					module = new UserDefinedByteModule();
					module.setAll(newModule);
				}
				else
				{
					module = newModule;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return module;
	}

	@Override
	public String serialize()
	{
		return toString();
	}

	@Override
	public List<String> parameterKeyList()
	{
		return new Vector<String>();
	}

	@Override
	public boolean isOptionEditable()
	{
		return optionEditable.booleanValue();
	}

	public Boolean getOptionEditable()
	{
		return optionEditable;
	}

	public void setOptionEditable(Boolean optionEditable)
	{
		this.optionEditable = optionEditable;
	}

	public Long getDefinitionId()
	{
		return definitionId;
	}

	public void setDefinitionId(Long definitionId)
	{
		this.definitionId = definitionId;
	}

	@Override
	public String getDefinitionName()
	{
		return name + " (" + String.valueOf(definitionId) + ")";
	}

	@Override
	public String getHelps()
	{
		String results = "";
		
		try
		{
			results = String.valueOf(eval("getHelps()"));
		}
		catch(Exception e)
		{
			results = getParameterHelp();
		}
		
		return results;
	}
	@Override
	public String getUrl()
	{		
		try
		{
			return String.valueOf(eval("getHomepage()"));
		}
		catch(Exception e)
		{
			return null;
		}
	}
}