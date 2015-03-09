package hjow.convert.javamethods;

import hjow.convert.module.CharEncoder;
import hjow.convert.module.CompressModule;
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
import hjow.daemon.ClientData;
import hjow.daemon.Daemon;
import hjow.daemon.NotAllowedException;
import hjow.methodconverter.ByteConverter;
import hjow.methodconverter.Controller;
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
import java.io.Writer;

/**
 * <p>This object is used in script. In script, you can use this object named pvs, control.<br>
 *    Only work on the daemon console.</p>
 *  
 * 
 * <p>이 객체는 스크립트 내에서 사용됩니다. pvs, control 라는 이름으로 사용할 수 있습니다.<br>
 *    오직 데몬 상에서에서만 사용할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class PrivilegedContainer
{
	protected Daemon daemon;
	protected ClientData allocated;
	
	public PrivilegedContainer(Daemon daemon, ClientData allocated)
	{
		this.daemon = daemon;
		this.allocated = allocated;
	}
	public void close()
	{
		daemon = null;
		allocated = null;
	}
	/**
	 * <p>Create selected type object.</p>
	 * 
	 * <p>선택한 종류의 새 객체를 만듭니다.</p>
	 * 
	 * <p>Available types</p>
	 * <p>
	 * Communicator : You can use TCP/IP communication easily with this object. Security grade : 2<br>
	 * Package : Empty string package You can input text in this package. Security grade : 1<br>
	 * Sender : UDP package sender You can send package with UDP to the others. Security grade : 2<br>
	 * </p>
	 * 
	 * @param type : type name
	 * @param input_auth : nothing, can be null
	 * @return object
	 */
	public Object newObject(String type, String input_auth)
	{
		if(type == null) return null;
		if(type.equalsIgnoreCase("Communicator"))
		{
			return newObject(0, input_auth);
		}
		else if(type.equalsIgnoreCase("Package"))
		{
			return newObject(1, input_auth);
		}
		else if(type.equalsIgnoreCase("Sender"))
		{
			return newObject(2, input_auth);
		}
		else if(type.equalsIgnoreCase("CharEncoder"))
		{
			return newObject(10, input_auth);
		}
		else if(type.equalsIgnoreCase("CompressModule"))
		{
			return newObject(11, input_auth);
		}
		else if(type.equalsIgnoreCase("DecryptModule"))
		{
			return newObject(12, input_auth);
		}
		else if(type.equalsIgnoreCase("EncryptModule"))
		{
			return newObject(13, input_auth);
		}
		else if(type.equalsIgnoreCase("HashModule"))
		{
			return newObject(14, input_auth);
		}
		else if(type.equalsIgnoreCase("InsertTokenModule"))
		{
			return newObject(15, input_auth);
		}
		else if(type.equalsIgnoreCase("ReadWebModule"))
		{
			return newObject(16, input_auth);
		}
		else if(type.equalsIgnoreCase("ReturnSelfMethod"))
		{
			return newObject(17, input_auth);
		}
		else if(type.equalsIgnoreCase("ScriptModule"))
		{
			return newObject(18, input_auth);
		}
		else if(type.equalsIgnoreCase("SQL") || type.equalsIgnoreCase("SQLModule"))
		{
			return newObject(19, input_auth);
		}
		else if(type.equalsIgnoreCase("RemoveCommentModule"))
		{
			return newObject(20, input_auth);
		}
		else return null;
	}
	
	/**
	 * <p>Create selected type object.</p>
	 * 
	 * <p>선택한 종류의 새 객체를 만듭니다.</p>
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
	 * 18 : ScriptModule : ScriptModule module. Security grade : 2<br>
	 * 19 : SQLModule : SQLModule module. Security grade : 1<br>
	 * 20 : RemoveCommentModule : RemoveCommentModule module. Security grade : 0
	 * </p>
	 * 
	 * @param type : number
	 * @param input_auth : nothing, can be null
	 * @return object
	 */
	public Object newObject(int type, String input_auth)
	{
		try
		{
			switch(type)
			{
			case 0:
				if(allocated.getLevel() >= 2) return new hjow.network.Communicator();
				else throw new NotAllowedException("Create Communicator object");
			case 1:				
				if(allocated.getLevel() >= 1) return new StringPackage();
				else throw new NotAllowedException("Create StringPackage object");
			case 2:
				if(allocated.getLevel() >= 2) return new UDPSender();
				else throw new NotAllowedException("Create UDPSender object");
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
				if(allocated.getLevel() >= 2) return new ScriptModule(false);
				else throw new NotAllowedException("Create ScriptModule object");
			case 19:
				if(allocated.getLevel() >= 1) return new SQLModule();
				else throw new NotAllowedException("Create SQLModule object");
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
			return daemon.eval(code);
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
	 * <p>Return new File object.</p>
	 * <p>You can use File object to check the file is exist, to mkdir, or to create new file IO object.</p> 
	 * 
	 * <p>File 객체를 생성해 반환합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param path : file path if you want to create.
	 * @param input_auth : nothing, can be null
	 * @return : File object
	 */
	public File file(String path, String input_auth)
	{
		if(allocated.getLevel() >= 3) return new File(path);
		else throw new NotAllowedException("Create File object");
	}
	
	/**
	 * <p>Convert InputStream into Reader if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 InputStream 을 Reader 로 변환합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param stream : InputStream object
	 * @param input_auth : nothing, can be null
	 * @return Reader object
	 */
	public InputStreamReader inputStreamReader(InputStream stream, String input_auth)
	{
		if(allocated.getLevel() >= 3) return new InputStreamReader(stream);
		else throw new NotAllowedException("Convert inputs into reading stream");
	}
	
	/**
	 * <p>Convert OutputStream into Writer if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 OutputStream 을 Reader 로 변환합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param stream : OutputStream object
	 * input_auth : nothing, can be null
	 * @return Reader object
	 */
	public OutputStreamWriter outputStreamWriter(OutputStream stream, String input_auth)
	{
		if(allocated.getLevel() >= 3) return new OutputStreamWriter(stream);
		else throw new NotAllowedException("Convert outputs into writing stream");
	}
	
	/**
	 * <p>Convert Reader into BufferedReader if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 Reader 을 BufferedReader 로 변환합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param reader : BufferedReader object
	 * input_auth : nothing, can be null
	 * @return BufferedReader object
	 */
	public BufferedReader buffered(Reader reader, String input_auth)
	{
		if(allocated.getLevel() >= 3) return new BufferedReader(reader);
		else throw new NotAllowedException("Open buffered reading stream");
	}
	
	/**
	 * <p>Convert Writer into BufferedWriter if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 Writer 을 BufferedWriter 로 변환합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param writer : BufferedWriter object
	 * input_auth : nothing, can be null
	 * @return BufferedWriter object
	 */
	public BufferedWriter buffered(Writer writer, String input_auth)
	{
		if(allocated.getLevel() >= 3) return new BufferedWriter(writer);
		else throw new NotAllowedException("Open buffered writing stream");
	}
	
	/**
	 * <p>Return new FileReader object if input_auths is correct. This object should be closed at the end of usage.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 FileReader 객체를 반환합니다. 이 객체는 사용이 끝나면 close되어야 합니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param file : target File object
	 * input_auth : nothing, can be null
	 * @return FileReader object
	 * @throws Exception
	 */
	public FileReader fileReader(File file, String input_auth) throws Exception
	{
		if(allocated.getLevel() >= 3) return new FileReader(file);
		else throw new NotAllowedException("File reading");
	}
	
	/**
	 * <p>Return new FileWriter object if input_auths is correct. This object should be closed at the end of usage.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 FileWriter 객체를 반환합니다. 이 객체는 사용이 끝나면 close되어야 합니다.</p>
	 * 
	 * <p>Security grade : 5</p>
	 * 
	 * @param file : target File object
	 * input_auth : nothing, can be null
	 * @return FileWriter object
	 * @throws Exception
	 */
	public FileWriter fileWriter(File file, String input_auth) throws Exception
	{
		if(allocated.getLevel() >= 5) return new FileWriter(file);
		else throw new NotAllowedException("File saving");
	}
	
	/**
	 * <p>Read bytes from file if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 파일로부터 바이트를 읽습니다.</p>
	 * 
	 * <p>Security grade : 3</p>
	 * 
	 * @param file : File object
	 * @param input_auth : nothing, can be null
	 * @return bytes
	 */
	public byte[] readByte(File file, String input_auth)
	{
		if(allocated.getLevel() >= 3) return ByteConverter.load(file);
		else throw new NotAllowedException("File reading");
	}
	
	/**
	 * <p>Save bytes as file if input_auths is correct.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 파일에 바이트를 저장합니다.</p>
	 * 
	 * <p>Security grade : 5</p>
	 * 
	 * @param file : File object
	 * @param input_auth : nothing, can be null
	 * @param bytes : bytes
	 */
	public void saveByte(File file, byte[] bytes, String input_auth)
	{
		if(allocated.getLevel() >= 5) ByteConverter.save(file, bytes);
		else throw new NotAllowedException("File saving");
	}
	
	/**
	 * <p>Return opened InputStream object if input_auths is correct. This object should be closed at the end of usage.</p>
	 * 
	 * <p>input_auths 가 올바른 인증 코드이면 웹 스트림을 엽니다. 원하는 URL로부터 데이터를 받을 때 사용합니다. 이 객체는 사용이 끝나면 close되어야 합니다.</p>
	 * 
	 * <p>Security grade : 2</p>
	 * 
	 * @param url : target URL
	 * input_auth : nothing, can be null 
	 * @return InputStream object
	 * @throws Exception
	 */
	public InputStream openUrl(String url, String input_auth) throws Exception
	{
		if(allocated.getLevel() >= 2) return new java.net.URL(url).openStream();
		else throw new NotAllowedException("Open URL");
	}
}
