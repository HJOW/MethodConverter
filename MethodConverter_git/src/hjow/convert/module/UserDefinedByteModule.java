package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.Map;

/**
 * <p>This module will be created by someone.<br>
 * This object can include JavaScript code.</p>
 * 
 * <p>이 모듈은 누군가에 의해 만들어질 것입니다.<br>
 * 이 모듈 객체는 자바스크립트 코드를 내장할 수 있으며, 변환 작업 시 해당 스크립트가 동작합니다.</p>
 * 
 * @author HJOW
 *
 */
public class UserDefinedByteModule extends UserDefinedModule implements BothModule
{
	private static final long serialVersionUID = 246686530249870407L;

	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 * 
	 * @throws Exception
	 */
	public UserDefinedByteModule() throws Exception
	{
		super();	
	}
	
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 * 
	 * @param callInit : If this is true, init() method will be called.
	 * @throws Exception
	 */
	public UserDefinedByteModule(boolean callInit) throws Exception
	{
		super(callInit);
	}
		
	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters)
	{
		try
		{
			return convert(befores, parameters, null, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters,
			StatusBar statusBar, StatusViewer statusViewer)
	{
		try
		{
			insertObject("originalBytes", befores);
			insertObject("parameters", parameters);
			
			insertObject("stringTable", Controller.getLanguage());
			insertObject("syntaxTable", null);
			insertObject("original", new String(befores, "UTF-8"));
			insertObject("statusViewer", null);
			insertObject("statusField", null);
			insertObject("threadTerm", new Long(20));
			
			Controller.println("Run following scripts in this module.");
			Controller.println(declareStatements);
			
			eval(declareStatements);
			
			return (byte[]) eval("convertByte()");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public boolean isEncryptingModule()
	{
		return false;
	}
	
	/**
	 * <p>Return helps about creating function with default statements.</p>
	 * 
	 * <p>함수를 만드는 데 필요한 도움말과 함께 기본 선언문을 반환합니다.</p>
	 * 
	 * @return helps
	 */
	public static String defaultDeclareByteString()
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
		    r = r.append("입력받은 바이트 데이터를 변환해 반환하는 convertByte() 함수를 만드세요. \n");
		    r = r.append("만드려는 모듈이 바이트 데이터를 처리할 필요가 없다면, null 를 반환하도록 만들면 됩니다. \n\n");
		    r = r.append("이 함수를 만들기 위해 다음과 같이 이미 선언된 변수를 사용할 수 있습니다.\n\n");
		    r = r.append("originalBytes : 변환할 바이트 데이터\n");
		    r = r.append("parameters : 키와 값이 모두 String 인 Hashtable 객체.\n");
		    r = r.append("          (특별한 키 : defaultoption, option)\n");		    
		    r = r.append("*/\n\n");
		    r = r.append("function convertByte()\n");
		    r = r.append("{\n");
		    r = r.append("    var results = originalBytes;\n\n");
		    r = r.append("    // 스크립트를 이 곳에 삽입\n\n");
		    r = r.append("    return results;\n");
		    r = r.append("}\n");
		}
		else
		{
			r = r.append("  \n/*\n");
		    r = r.append("Create convertByte() function which is convert and return bytes. \n");
		    r = r.append("If you don't want to make your new module can convert binary, make this function returns null. \n\n");
		    r = r.append("You can use following variables to make convertByte() function.\n\n");
		    r = r.append("originalBytes : Original bytes. byte[] type.\n");
		    r = r.append("parameters : Hashtable object that both key and variables are String.\n");
		    r = r.append("          (Special available keys : defaultoption, option)\n");		   
		    r = r.append("*/\n\n");
		    r = r.append("function convertByte()\n");
		    r = r.append("{\n");
		    r = r.append("    var results = originalBytes;\n\n");
		    r = r.append("    // Input script here\n\n");
		    r = r.append("    return results;\n");
		    r = r.append("}\n");
		}
		
	    return r.toString();
	}
	
	@Override
	public void setAll(UserDefinedModule other) throws Exception
	{
		super.setAll(other);
	}
}
