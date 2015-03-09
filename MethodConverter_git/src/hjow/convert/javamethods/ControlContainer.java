package hjow.convert.javamethods;
import hjow.convert.module.CharEncoder;
import hjow.convert.module.CompressModule;
import hjow.convert.module.ConvertModule;
import hjow.convert.module.DecryptModule;
import hjow.convert.module.EncryptModule;
import hjow.convert.module.HashModule;
import hjow.convert.module.InsertTokenModule;
import hjow.convert.module.ReadWebModule;
import hjow.convert.module.RemoveCommentModule;
import hjow.convert.module.ReturnSelfMethod;
import hjow.convert.module.SQLModule;
import hjow.convert.module.ScriptModule;
import hjow.convert.module.UDPSender;
import hjow.methodconverter.ByteConverter;
import hjow.methodconverter.Controller;
import hjow.methodconverter.ThreadRunner;
import hjow.methodconverter.ThreadTracer;
import hjow.network.StringPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

/**
 * <p>This object is used in script. In script, you can use this object named controlobject.</p>
 * 
 * <p>이 객체는 스크립트 내에서 사용됩니다. controlobject 라는 이름으로 사용할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class ControlContainer implements Serializable
{
	private static final long serialVersionUID = 3248955662936146095L;
	private transient Object engine = null;
	private transient ConvertModule module = null;

	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 */
	public ControlContainer()
	{
		
	}
	/**
	 * 
	 * <p>Create new object with script engine.</p>
	 * 
	 * <p>스크립트 엔진을 가지고 새 객체를 만듭니다.</p>
	 * 
	 * @param engine : script engine
	 * @param module : module self
	 */
	public ControlContainer(Object engine, ConvertModule module)
	{
		init(engine, module);
	}
	
	/**
	 * <p>Initialize the object.</p>
	 * 
	 * <p>객체를 초기화합니다.</p>
	 * 
	 * @param engine : script engine
	 * @param module : Module object includes this
	 */
	protected void init(Object engine, ConvertModule module)
	{
		this.engine = engine;
		this.module = module;
	}
	
	/**
	 * <p>Return system property.</p>
	 * 
	 * <p>시스템 속성을 반환합니다.</p>
	 * 
	 * 
	 * @param keys : property key
	 * @return property value
	 */
	public String getSystemProperty(String keys)
	{
		return System.getProperty(keys);
	}
	
	/**
	 * <p>Return current time as milliseconds.</p>
	 * 
	 * <p>현재 시각을 밀리초 단위로 반환합니다.</p>
	 * 
	 * @return current time
	 */
	public long currentTimeMills()
	{
		return System.currentTimeMillis();
	}
	/**
	 * <p>Return total memory.</p>
	 * 
	 * <p>전체 메모리량을 반환합니다.</p>
	 * 
	 * @return total memory
	 */
	public long totalMemory()
	{
		return Runtime.getRuntime().totalMemory();
	}
	/**
	 * <p>Return available memory.</p>
	 * 
	 * <p>사용 가능한 메모리량을 반환합니다.</p>
	 * 
	 * @return free memory
	 */
	public long freeMemory()
	{
		return Runtime.getRuntime().freeMemory();
	}
	/**
	 * <p>Return max memory.</p>
	 * 
	 * <p>최대 메모리량을 반환합니다.</p>
	 * 
	 * @return max memory
	 */
	public long maxMemory()
	{		
		return Runtime.getRuntime().maxMemory();
	}
	/**
	 * <p>Return how many processors available.</p>
	 * 
	 * <p>사용 가능한 프로세서 수를 반환합니다.</p>
	 * 
	 * @return available processors
	 */
	public long availableProcessors()
	{
		return Runtime.getRuntime().availableProcessors();
	}
	
	/**
	 * <p>Run script code and return results.</p>
	 * <p>If exception is occurs, exception object will be returned.</p>
	 * 
	 * <p>코드를 실행하고 결과를 반환합니다.</p>
	 * <p>실행 중 예외 발생 시 예외 객체가 반환됩니다.</p>
	 * 
	 * @param code : script code
	 * @return result of code execution.
	 */
	public Object eval(String code) 
	{
		try
		{
			return ((javax.script.ScriptEngine) engine).eval(code);
		}
		catch (Exception e)
		{
			return e;
		}
	}	
	
	/**
	 * <p>Check the object is Exception type.</p>
	 * 
	 * <p>어떤 객체가 예외 객체인지를 확인합니다.</p>
	 * 
	 * @param obj : object suspected
	 * @return true if obj is Exception object
	 */
	public boolean isException(Object obj)
	{
		return (obj instanceof Throwable);
	}
	/**
	 * <p>Return available option list.</p>
	 * 
	 * <p>옵션 리스트를 반환합니다.</p>
	 * 
	 * @return option list, List type.
	 */
	public List<String> optionList()
	{
		return module.optionList();
	}
	
	/**
	 * <p>Return selected option.</p>
	 * 
	 * <p>선택된 옵션을 반환합니다.</p>
	 * 
	 * @return selected option
	 */
	public String getSelectedOption()
	{
		return Controller.getSelectedSyntax();
	}
	
	/**
	 * <p>Return module name.</p>
	 * 
	 * <p>모듈 이름을 반환합니다.</p>
	 * 
	 * @return module name
	 */
	public String getModuleName()
	{
		return module.getName(null);
	}
	
	/**
	 * <p>Return true if input_auths is correct authority code.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 true를 반환합니다.</p>
	 * 
	 * @param input_auths : authority code user input.
	 * @return true if input_auths is correct.
	 */
	public boolean isAuthCode(String input_auths)
	{
		return module.isAuthCode(input_auths);
	}
	
	/**
	 * <p>Return true if input_auths is correct authority code. If the level is low value (such as 0), return true.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 true를 반환합니다. 보안 레벨에 따라 무조건 true 혹은 false를 반환하기도 합니다.</p>
	 * 
	 * <p>
	 * "level" parameter means the level of the function will need to check.<br>
	 * 
	 * If "level" parameter value is bigger than following formula results, then always return false.
	 * 
	 * 10 - ( SecurityLevel - 1)
	 * 
	 * SecurityLevel is setting of security level.
	 * </p>
	 * 
	 * @param input_auths : authority code user input.
	 * @param level : Security level
	 * @return true if input_auths is correct or the security level is low.
	 */
	public boolean isAvailable(String input_auths, int level)
	{		
		boolean needCheck = false;		
		
		if(level <= 0) return true;
		
		int settings = Controller.getSecurityLevel();
		if(settings <= 0) return true;
		
		if(module instanceof ScriptModule)
		{
			if(settings >= 9)
			{				
				if(level >= 2)
				{
					return false;
				}
				else if(level >= 1)
				{
					return Controller.requestYes("Some works needs to your allows.");
				}
				else return true;
			}
			else if(settings >= 7)
			{				
				if(level >= 3)
				{
					return false;
				}
				else if(level >= 1)
				{
					return Controller.requestYes("Some works needs to your allows.");
				}
				else return true;
			}
			else if(settings >= 5)
			{				
				if(level >= 5)
				{
					return false;
				}
				else if(level >= 1)
				{
					return Controller.requestYes("Some works needs to your allows.");
				}
				else return true;
			}
			else if(settings >= 3)
			{				
				if(level >= 7)
				{
					return false;
				}
				else if(level >= 1)
				{
					return Controller.requestYes("Some works needs to your allows.");
				}
				else return true;
			}
			else if(settings >= 2)
			{				
				if(level >= 7)
				{
					return false;
				}
				else if(level >= 2)
				{
					return Controller.requestYes("Some works needs to your allows.");
				}
				else return true;
			}
			else if(settings >= 1)
			{				
				if(level >= 7)
				{
					return false;
				}
				else if(level >= 3)
				{
					return Controller.requestYes("Some works needs to your allows.");
				}
				else return true;
			}
			else return true;
		}
		
		if(level == 1)
		{
			if(settings <= 1) return true;
			else
			{
				return false;
			}
		}
		else
		{
			if(level == settings) needCheck = true;
			else
			{
				int limits = 10;
				if(settings == 1) needCheck = true;
				else
				{
					limits = 10 - (settings - 1);
					
				}
				
				if(level >= limits) needCheck = false;
				else needCheck = true;
			}
		}		
		
		if(needCheck)
		{
			if(isAvailable(input_auths, 1)) return true;
			else return false;
		}
		else return false;
	}
	
	/**
	 * <p>Return new File object when input_auths is correct authority code.</p>
	 * <p>You can use File object to check the file is exist, to mkdir, or to create new file IO object.</p> 
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 File 객체를 생성해 반환합니다.</p>
	 * 
	 * <p>Security grade : 1</p>
	 * 
	 * @param path : file path if you want to create.
	 * @param input_auths : authority code
	 * @return : File object
	 */
	public File file(String path, String input_auths)
	{
		if(isAvailable(input_auths, 1)) return new File(path);
		else if(Controller.requestYes(Controller.getString("Do you want to allow creating File object?"))) return new File(path);
		else return null;
	}
	
	/**
	 * <p>Convert InputStream into Reader if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 InputStream 을 Reader 로 변환합니다.</p>
	 * 
	 * <p>Security grade : 1</p>
	 * 
	 * @param stream : InputStream object
	 * @param input_auths : authority code
	 * @return Reader object
	 */
	public InputStreamReader inputStreamReader(InputStream stream, String input_auths)
	{
		if(isAvailable(input_auths, 1)) return new InputStreamReader(stream);
		else if(Controller.requestYes(Controller.getString("Do you want to allow creating InputStreamReader object?"))) return new InputStreamReader(stream);
		else return null;
	}
	
	/**
	 * <p>Convert OutputStream into Writer if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 OutputStream 을 Reader 로 변환합니다.</p>
	 * 
	 * <p>Security grade : 1</p>
	 * 
	 * @param stream : OutputStream object
	 * @param input_auths : authority code
	 * @return Reader object
	 */
	public OutputStreamWriter outputStreamWriter(OutputStream stream, String input_auths)
	{
		if(isAvailable(input_auths, 1)) return new OutputStreamWriter(stream);
		else if(Controller.requestYes(Controller.getString("Do you want to allow creating OutputStreamWriter object?"))) return new OutputStreamWriter(stream);
		else return null;
	}
	
	/**
	 * <p>Convert Reader into BufferedReader if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 Reader 을 BufferedReader 로 변환합니다.</p>
	 * 
	 * <p>Security grade : 1</p>
	 * 
	 * @param reader : BufferedReader object
	 * @param input_auths : authority code
	 * @return BufferedReader object
	 */
	public BufferedReader buffered(Reader reader, String input_auths)
	{
		if(isAvailable(input_auths, 1)) return new BufferedReader(reader);
		else if(Controller.requestYes(Controller.getString("Do you want to allow creating OutputStreamWriter object?"))) return new BufferedReader(reader);
		else return null;
	}
	
	/**
	 * <p>Convert Writer into BufferedWriter if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 Writer 을 BufferedWriter 로 변환합니다.</p>
	 * 
	 * <p>Security grade : 1</p>
	 * 
	 * @param writer : BufferedWriter object
	 * @param input_auths : authority code
	 * @return BufferedWriter object
	 */
	public BufferedWriter buffered(Writer writer, String input_auths)
	{
		if(isAvailable(input_auths, 1)) return new BufferedWriter(writer);
		else if(Controller.requestYes(Controller.getString("Do you want to allow creating BufferedWriter object?"))) return new BufferedWriter(writer);
		else return null;
	}
	
	/**
	 * <p>Return new FileReader object if input_auths is correct. This object should be closed at the end of usage.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 FileReader 객체를 반환합니다. 이 객체는 사용이 끝나면 close되어야 합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param file : target File object
	 * @param input_auths : authority code
	 * @return FileReader object
	 * @throws Exception
	 */
	public FileReader fileReader(File file, String input_auths) throws Exception
	{
		if(isAvailable(input_auths, 3)) return new FileReader(file);
		else if(Controller.requestYes(Controller.getString("Do you want to allow to reading file from your disk?"))) return new FileReader(file);
		else return null;
	}
	
	/**
	 * <p>Return new FileWriter object if input_auths is correct. This object should be closed at the end of usage.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 FileWriter 객체를 반환합니다. 이 객체는 사용이 끝나면 close되어야 합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param file : target File object
	 * @param input_auths : authority code
	 * @return FileWriter object
	 * @throws Exception
	 */
	public FileWriter fileWriter(File file, String input_auths) throws Exception
	{
		if(isAvailable(input_auths, 3)) return new FileWriter(file);
		else if(Controller.requestYes(Controller.getString("Do you want to allow to save file into your disk?"))) return new FileWriter(file);
		else return null;
	}
	
	/**
	 * <p>Read bytes from file if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 파일로부터 바이트를 읽습니다.</p>
	 * 
	 * <p>Security grade : 2</p>
	 * 
	 * @param file : File object
	 * @return bytes
	 */
	public byte[] readByte(File file, String input_auths)
	{
		if(isAvailable(input_auths, 2)) return ByteConverter.load(file);
		else if(Controller.requestYes(Controller.getString("Do you want to allow to read file from your disk?"))) return ByteConverter.load(file);
		else return null;
	}
	
	/**
	 * <p>Save bytes as file if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 파일에 바이트를 저장합니다.</p>
	 * 
	 * <p>Security grade : 2</p>
	 * 
	 * @param file : File object
	 * @param bytes : bytes
	 */
	public void saveByte(File file, byte[] bytes, String input_auths)
	{
		if(isAvailable(input_auths, 2)) ByteConverter.save(file, bytes);
		else if(Controller.requestYes(Controller.getString("Do you want to allow to save file into your disk?"))) ByteConverter.save(file, bytes);
	}
	
	/**
	 * <p>Return opened InputStream object if input_auths is correct. This object should be closed at the end of usage.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 웹 스트림을 엽니다. 원하는 URL로부터 데이터를 받을 때 사용합니다. 이 객체는 사용이 끝나면 close되어야 합니다.</p>
	 * 
	 * <p>Security grade : 1</p>
	 * 
	 * @param url : target URL
	 * @param input_auths : authority code
	 * @return InputStream object
	 * @throws Exception
	 */
	public InputStream openUrl(String url, String input_auths) throws Exception
	{
		if(isAvailable(input_auths, 1)) return new java.net.URL(url).openStream();
		else if(Controller.requestYes(Controller.getString("Do you want to allow to read data from web?"))) return new java.net.URL(url).openStream();
		else return null;
	}
	
	/**
	 * <p>Create selected type object if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 선택한 종류의 새 객체를 만듭니다.</p>
	 * 
	 * <p>Available types</p>
	 * <p>
	 * Communicator : You can use TCP/IP communication easily with this object. Security grade : 2<br>
	 * Package : Empty string package You can input text in this package. Security grade : 1<br>
	 * Sender : UDP package sender You can send package with UDP to the others. Security grade : 2<br>
	 * </p>
	 * 
	 * @param type : type name
	 * @param input_auths : authority code
	 * @return object
	 */
	public Object newObject(String type, String input_auths)
	{
		if(type == null) return null;
		if(type.equalsIgnoreCase("Communicator"))
		{
			return newObject(0, input_auths);
		}
		else if(type.equalsIgnoreCase("Package"))
		{
			return newObject(1, input_auths);
		}
		else if(type.equalsIgnoreCase("Sender"))
		{
			return newObject(2, input_auths);
		}
		else if(type.equalsIgnoreCase("CharEncoder"))
		{
			return newObject(10, input_auths);
		}
		else if(type.equalsIgnoreCase("CompressModule"))
		{
			return newObject(11, input_auths);
		}
		else if(type.equalsIgnoreCase("DecryptModule"))
		{
			return newObject(12, input_auths);
		}
		else if(type.equalsIgnoreCase("EncryptModule"))
		{
			return newObject(13, input_auths);
		}
		else if(type.equalsIgnoreCase("HashModule"))
		{
			return newObject(14, input_auths);
		}
		else if(type.equalsIgnoreCase("InsertTokenModule"))
		{
			return newObject(15, input_auths);
		}
		else if(type.equalsIgnoreCase("ReadWebModule"))
		{
			return newObject(16, input_auths);
		}
		else if(type.equalsIgnoreCase("ReturnSelfMethod"))
		{
			return newObject(17, input_auths);
		}
		else if(type.equalsIgnoreCase("ScriptModule"))
		{
			return newObject(18, input_auths);
		}
		else if(type.equalsIgnoreCase("SQL") || type.equalsIgnoreCase("SQLModule"))
		{
			return newObject(19, input_auths);
		}
		else if(type.equalsIgnoreCase("RemoveCommentModule"))
		{
			return newObject(20, input_auths);
		}
		else return null;
	}
	
	/**
	 * <p>Create selected type object if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 선택한 종류의 새 객체를 만듭니다.</p>
	 * 
	 * <p>Available types</p>
	 * <p>
	 * 0 : Communicator : You can use TCP/IP communication easily with this object. Security grade : 2<br>
	 * 1 : StringPackage : Empty string package You can input text in this package. Security grade : 1<br>
	 * 2 : UDPSender : UDP package sender You can send package with UDP to the others. Security grade : 2<br>
	 * 10 : CharEncoder : CharEncoder module. Security grade : 0<br>
	 * 11 : CompressModule : CompressModule module. Security grade : 0<br>
	 * 12 : DecryptModule : DecryptModule module. Security grade : 0<br>
	 * 13 : EncryptModule : EncryptModule module. Security grade : 0<br>
	 * 14 : HashModule : HashModule module. Security grade : 0<br>
	 * 15 : InsertTokenModule : InsertTokenModule module. Security grade : 0<br>
	 * 16 : ReadWebModule : ReadWebModule module. Security grade : 0<br>
	 * 17 : ReturnSelfMethod : ReturnSelfMethod module. Security grade : 0<br>
	 * 18 : ScriptModule : ScriptModule module. Security grade : 0<br>
	 * 19 : SQLModule : SQLModule module. Security grade : 0<br>
	 * 20 : RemoveCommentModule : RemoveCommentModule module. Security grade : 0
	 * </p>
	 * 
	 * @param type : number
	 * @param input_auths : authority code
	 * @return object
	 */
	public Object newObject(int type, String input_auths)
	{
		try
		{
			switch(type)
			{
			case 0:				
				if(isAvailable(input_auths, 2))
				{
					return new hjow.network.Communicator();
				}
				else if(Controller.requestYes(Controller.getString("Do you want to allow to communicate to other PC with TCP?"))) return new hjow.network.Communicator();
				else return null;
			case 1:				
				if(isAvailable(input_auths, 1))
				{
					return new StringPackage();
				}
				else if(Controller.requestYes(Controller.getString("Do you want to allow to create new empty package?"))) return new StringPackage();
				else return null;
			case 2:
				if(isAvailable(input_auths, 2))
				{
					return new UDPSender();
				}
				else if(Controller.requestYes(Controller.getString("Do you want to allow to send packages to other PC with UDP?"))) return new UDPSender();
				else return null;
			case 10:
				return new CharEncoder();
			case 11:
				return new CompressModule();
			case 12:
				return new DecryptModule();
			case 13:
				return new EncryptModule();
			case 14:
				return new HashModule();
			case 15:
				return new InsertTokenModule();
			case 16:
				return new ReadWebModule();
			case 17:
				return new ReturnSelfMethod();
			case 18:
				return new ScriptModule(false);
			case 19:
				return new SQLModule();
			case 20:
				return new RemoveCommentModule();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;				
	}
	
	/**
	 * <p>Exit the program if the input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 인증 코드이면 프로그램을 종료합니다.</p>
	 * 
	 * <p>Security grade : 1</p>
	 * 
	 * @param input_auths : authority code
	 */
	public void exit(String input_auths)
	{
		if(isAvailable(input_auths, 1))
		{
			Controller.closeAll();
			System.exit(0);
		}
		else if(Controller.requestYes(Controller.getString("Do you want to exit this program?")))
		{
			Controller.closeAll();
			System.exit(0);
		}
	}
	
	/**
	 * <p>Run script code on the thread if the input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 인증 코드이면 쓰레드로 스크립트 코드를 실행합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param code : script code
	 * @param timeout : If this is negative value, code will be run infinity. Else, the thread will be stopped when the time is gone.
	 * @param threadGap : Thread gap
	 * @param input_auths : authority code
	 */
	public void runOnBackground(String code, long timeout, long threadGap, String input_auths)
	{
		if(isAvailable(input_auths, 3))
		{
			ScriptThread newThread = new ScriptThread(code, timeout, threadGap);
			Controller.insertThreadObject(newThread);
		}
		else if(Controller.requestYes(Controller.getString("Do you want to allow to creating new thread to run several scripts?")))
		{
			ScriptThread newThread = new ScriptThread(code, timeout, threadGap);
			Controller.insertThreadObject(newThread);
		}
	}
	
	/**
	 * <p>Return all thread status.</p>
	 * 
	 * <p>전체 쓰레드 상태를 반환합니다.</p>
	 * 
	 * @return all thread states
	 */
	public String getThreadState()
	{		
		return ThreadTracer.getThreadState();
	}
	
	/**
	 * <p>Stop to use this object. If user close this program, this method is called.</p>
	 * 
	 * <p>이 객체 사용을 중단합니다. 프로그램 종료 시 이 메소드가 자동 호출됩니다.</p>
	 */
	public void close()
	{
		engine = null;
		module = null;
	}
}

/**
 * <p>Script-running thread object.</p>
 * 
 * <p>스크립트 실행 객체</p>
 * 
 * @author HJOW
 *
 */
class ScriptThread implements ThreadRunner
{	
	private long id = ((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long)(Math.random() * (Long.MAX_VALUE / 100000)));
	private volatile boolean threadRun = true;
	private long threadGap = 50;
	private long timeout = -1;
	private long nowtime = 0;
	private String scripts = "";
	private Thread thread;
	
	/**
	 * <p>Create new thread.</p>
	 * 
	 * <p>쓰레드를 생성합니다.</p>
	 * 
	 * @param code : Script code which will be run on new thread
	 */
	public ScriptThread(String code)
	{
		this(code, -1, 50);
	}
	
	/**
	 * <p>Create new thread.</p>
	 * 
	 * <p>쓰레드를 생성합니다.</p>
	 * 
	 * @param code : Script code which will be run on new thread
	 * @param timeout : If this is -1, this thread runs infinity
	 */
	public ScriptThread(String code, long timeout)
	{
		this(code, timeout, 50);
	}
	/**
	 * <p>Create new thread.</p>
	 * 
	 * <p>쓰레드를 생성합니다.</p>
	 * 
	 * @param code : Script code which will be run on new thread
	 * @param timeout : If this is -1, this thread runs infinity
	 * @param threadGap : Thread frequency
	 */
	public ScriptThread(String code, long timeout, long threadGap)
	{
		this.scripts = new String(code);
		this.timeout = timeout;
		this.threadGap = threadGap;
		start();
	}
	@Override
	public void close()
	{
		threadRun = false;
		thread = null;
	}
	@Override
	public boolean isAlive()
	{
		return threadRun;
	}	
	@Override
	public void run()
	{
		while(threadRun)
		{
			try
			{
				Controller.runScript(scripts);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if(timeout >= 0) nowtime++;
				if(nowtime >= timeout) break;
			}
			catch(Exception e)
			{
				
			}
			try
			{
				Thread.sleep(threadGap);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	@Override
	public void start()
	{
		thread = new Thread(this);
		Controller.insertThreadObject(thread);
		thread.start();
	}

	@Override
	public String getThreadName()
	{
		return String.valueOf(thread);
	}

	@Override
	public long getId()
	{
		return id;
	}	
}
