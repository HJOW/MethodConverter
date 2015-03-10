package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <p>This module get hash value of text</p>
 * 
 * <p>이 모듈은 텍스트의 해시값을 얻습니다.</p>
 * 
 * @author HJOW
 *
 */
public class HashModule implements BothModule
{
	private static final long serialVersionUID = -982584052552303365L;	

	public HashModule()
	{
		
	}
	/**
	 * <p>Get hash value of text.</p>
	 * 
	 * <p>해시값을 얻습니다.</p>
	 * 
	 * @param parameters : parameters (option is available)
	 * @return hash value
	 */
	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(null, null, before, null, null, 20, parameters);
	}
	/**
	 * <p>Get hash value of text.</p>
	 * 
	 * <p>해시값을 얻습니다.</p>
	 * 
	 * @param parameters : parameters (option is available)
	 * @return hash value
	 */
	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		String charset = "UTF-8";
		try
		{
			charset = parameters.get("charset");
			if(charset == null) charset = "UTF-8";
		}
		catch(Exception e)
		{
			charset = "UTF-8";			
		}
		
		try
		{
			return Statics.applyScript(this, parameters, encrypt(before, parameters.get("option"), charset));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Controller.alert(Statics.fullErrorMessage(e));
			return Statics.fullErrorMessage(e);
		}
	} 
	
	/**
	 * <p>This method get hash value of text.</p>
	 * 
	 * <p>이 메소드는 텍스트로부터 해시값을 얻습니다.</p>
	 * 
	 * @param before : Original text
	 * @param method : How to encrypt (MD5, SHA-1, ...)
	 * @return hash text
	 */
	public String encrypt(String before, String method)
	{
		return encrypt(before.getBytes(), method);
	}
	
	/**
	 * <p>This method get hash value of text.</p>
	 * 
	 * <p>이 메소드는 텍스트로부터 해시값을 얻습니다.</p>
	 * 
	 * @param before : Original text
	 * @param method : How to encrypt (MD5, SHA-1, ...)
	 * @return hash text
	 * @throws Exception (May be UnsupportedEncodingException)
	 */
	public String encrypt(String before, String method, String charset) throws Exception
	{
		return encrypt(before.getBytes(charset), method);
	}
	
	/**
	 * <p>Get hash value of bytes.</p>
	 * 
	 * <p>바이트 데이터로부터 해시값을 얻습니다.</p>
	 * 
	 * @param befores : bytes
	 * @param algorithm : hashing function
	 * @param statusViewer : status viewer which can show process is alive
	 * @param statusField : status bar which can show text
	 * @return hash value as bytes but this is text
	 */
	public byte[] convert(byte[] befores, String algorithm, StatusViewer statusViewer, StatusBar statusField)
	{
		MessageDigest digest = null;
		try
		{
			if(algorithm == null) digest = MessageDigest.getInstance("MD5");
			else digest = MessageDigest.getInstance(algorithm);
						
			byte[] textArray = digest.digest(befores);
									
			return textArray;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.alert(Statics.fullErrorMessage(e));
			return null;
		}
	}
	/**
	 * <p>This method get hash value of bytes.</p>
	 * 
	 * <p>이 메소드는 바이트로부터 해시값을 얻습니다.</p>
	 * 
	 * @param before : Original bytes
	 * @param method : How to encrypt (MD5, SHA-1, ...)
	 * @param statusViewer : status viewer which can show process is alive
	 * @param statusField : status bar which can show text
	 * @return hash text
	 */
	public String encrypt(byte[] before, String method, StatusViewer statusViewer, StatusBar statusField)
	{
		MessageDigest digest = null;
		try
		{
			if(method == null) digest = MessageDigest.getInstance("MD5");
			else digest = MessageDigest.getInstance(method);
						
			byte[] textArray = digest.digest(before);
			StringBuffer results = new StringBuffer("");
			
			for(int i=0; i<textArray.length; i++)
			{
				results.append( Integer.toString(((textArray[i] & 0xf0) >> 4), 16) );
				results.append(Integer.toString((textArray[i] & 0x0f), 16));
				if(statusViewer != null) statusViewer.nextStatus();
			}
									
			return String.valueOf(results);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * <p>This method get hash value of bytes.</p>
	 * 
	 * <p>이 메소드는 바이트로부터 해시값을 얻습니다.</p>
	 * 
	 * @param before : Original bytes
	 * @param method : How to encrypt (MD5, SHA-1, ...)
	 * @return hash text
	 */
	public String encrypt(byte[] before, String method)
	{
		return encrypt(before, method, null, null);
	}
	
	@Override
	public String defaultParameterText()
	{
		return null;
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
			return "해시";
		}
		else
		{
			return "Hash";
		}
	}
	/**
	 * <p>Return hashing algorithms.</p>
	 * 
	 * <p>해시 알고리즘들을 반환합니다.</p>
	 */
	@Override
	public List<String> optionList()
	{
		List<String> results = new Vector<String>();
		results.add("MD2");
		results.add("MD5");
		results.add("SHA-1");
		results.add("SHA-256");
		results.add("SHA-384");
		results.add("SHA-512");
		return results;
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
		String locale = Controller.getSystemLocale();
		String results = "";
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			results = results + "이 모듈 사용에는 매개 변수가 필요하지 않습니다." + "\n";
		}
		else
		{
			results = results + "No parameters are needed to use this module." + "\n";
		}
		return results;
	}
	
	/**
	 * <p>Get hash value of bytes.</p>
	 * 
	 * <p>바이트 데이터로부터 해시값을 얻습니다.</p>
	 * 
	 * @param befores : bytes
	 * @param algorithm : hashing function
	 * @return hash value as bytes but this is text
	 */
	public byte[] convert(byte[] befores, String algorithm)
	{
		try
		{
			return encrypt(befores, algorithm, null, null).getBytes("UTF-8");
		}
		catch (Exception e)
		{
			return encrypt(befores, algorithm).getBytes();
		}
	}
	
	
	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters,
			StatusBar statusBar, StatusViewer statusViewer)
	{
		String alg = parameters.get("defaultoption");
		if(parameters.get("method") != null) alg = parameters.get("algorithm");
		try
		{
			return encrypt(befores, alg, statusViewer, statusBar).getBytes("UTF-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<String> parameterKeyList()
	{
		Vector<String> keys = new Vector<String>();
		keys.add("charset");
		return keys;
	}
	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters)
	{
		String algorithm;
		algorithm = parameters.get("algorithm");
		return convert(befores, algorithm);
	}
	@Override
	public boolean isEncryptingModule()
	{
		return false;
	}
	@Override
	public boolean isOptionEditable()
	{
		return false;
	}
	@Override
	public String getDefinitionName()
	{
		return "Hash";
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
	@Override
	public String getUrl()
	{
		return Controller.getDefaultURL();
	}
}
