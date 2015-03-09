package hjow.convert.module;

import hjow.methodconverter.ByteConverter;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Padder;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kisa.seed.SEED_KISA;

/**
 * <p>This module can encrypt text.</p>
 * 
 * <p>이 모듈은 텍스트를 암호화하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class EncryptModule implements SecurityModule
{
	private static final long serialVersionUID = 8310626723161835401L;
	

	/**
	 * <p>Create encrypt module object.</p>
	 * 
	 * <p>암호화 모듈 객체를 생성합니다.</p>
	 */
	public EncryptModule()
	{
		
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
			return "암호화";
		}
		else
		{
			return "Encrypt";
		}
	}
	
	/**
	 * <p>Encrypt bytes.</p>
	 * 
	 * <p>바이트 배열을 암호화합니다.</p>
	 * 
	 * <p>
	 * Available algorithm : DES, AES, DESede<br>
	 * Available keypadMethod : zero, special 
	 * </p>
	 * 
	 * @param befores : original bytes
	 * @param key : password
	 * @param algorithm : How to encrypt
	 * @param keypadMethod : How to pad key.
	 * @return bytes
	 */
	public byte[] convert(byte[] befores, String key, String algorithm, String keypadMethod)
	{
		return convert(befores, key, algorithm, keypadMethod);
	}
	
	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters,
			StatusBar statusBar, StatusViewer statusViewer)
	{
		String keyValue = parameters.get("defaultoption");
		if(parameters.get("key") != null) keyValue = parameters.get("key");
		return convert(befores, keyValue, null, null);
	}
	/**
	 * <p>Encrypt bytes.</p>
	 * 
	 * <p>바이트 배열을 암호화합니다.</p>
	 * 
	 * <p>
	 * Available algorithm : DES, AES, DESede<br>
	 * Available keypadMethod : zero, special 
	 * </p>
	 * 
	 * @param befores : original bytes
	 * @param key : password
	 * @param algorithm : How to encrypt
	 * @param keypadMethod : How to pad key.
	 * @param charset : Character Set
	 * @return bytes
	 */
	public byte[] convert(byte[] befores, String key, String algorithm, String keypadMethod, String charset)
	{
		String paddings = "";
		int need_keySize = 8;
		boolean useIv = true;
		byte[] keyByte;
		byte[] ivBytes;
		byte[] outputs;
		
		String apply_charset = "UTF-8";
		if(charset != null) apply_charset = charset;
		
		try
		{
			keyByte = key.getBytes(apply_charset);
		}
		catch(Exception e)
		{
			keyByte = key.getBytes();
		}
		
		int padType = 0;
		
		try
		{
			String specialPad = keypadMethod;
			if(specialPad == null) padType = 0;
			else
			{
				if(specialPad.equalsIgnoreCase("zero") || specialPad.equalsIgnoreCase("0"))
				{
					padType = 0;
				}
				else if(specialPad.equalsIgnoreCase("special"))
				{
					padType = 1;
				}
				else if(specialPad.equalsIgnoreCase("MD5"))
				{
					padType = 2;
				}
				else if(specialPad.equalsIgnoreCase("SHA-512"))
				{
					padType = 3;
				}
				else
				{
					padType = 0;
				}
			}
		}
		catch(Exception e)
		{
			padType = 0;
		}
		
		if(algorithm.equalsIgnoreCase("DES"))
		{
			paddings = "DES/CBC/PKCS5Padding";
			need_keySize = 8;
			useIv = true;
		}
		else if(algorithm.equalsIgnoreCase("DESede"))
		{
			paddings = "TripleDES/ECB/PKCS5Padding";
			need_keySize = 24;
			useIv = true;
		}
		else if(algorithm.equalsIgnoreCase("AES"))
		{
			paddings = "AES";
			need_keySize = 16;
			useIv = false;
		}
		else return null;
		
		byte[] checkKeyByte = new byte[need_keySize];
		ivBytes = new byte[checkKeyByte.length];
		
		Padder padder = null;
		if(padType == 1) padder = new Padder();
		else if(padType == 2 || padType == 3)
		{
			HashModule hashModule = new HashModule();
			if(padType == 2)
			{
				keyByte = hashModule.convert(keyByte, "MD5");
			}
			else
			{
				keyByte = hashModule.convert(keyByte, "SHA-512");
			}			
		}
		
		
		for(int i=0; i<checkKeyByte.length; i++)
		{
			if(i < keyByte.length)
			{
				checkKeyByte[i] = keyByte[i];
			}
			else
			{
				checkKeyByte[i] = 0;
				if(padType == 1) checkKeyByte[i] = padder.getPaddingByte(key.trim());
			}
		}
		keyByte = checkKeyByte;
		
		SecretKeySpec keySpec = new SecretKeySpec(keyByte, algorithm);
		IvParameterSpec ivSpec = null;
		if(useIv) ivSpec = new IvParameterSpec(ivBytes);
		
		Cipher cipher = null;
		
		try
		{
			cipher = Cipher.getInstance(paddings);
			if(useIv)
			{
				cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			}
			else
			{
				cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			}
			
			outputs = new byte[cipher.getOutputSize(befores.length)];
			for(int i=0; i<outputs.length; i++)
			{
				outputs[i] = 0;
			}
			int enc_len = cipher.update(befores, 0, befores.length, outputs, 0);
			enc_len = enc_len + cipher.doFinal(outputs, enc_len);
			
			return outputs;		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Encrypt texts.</p>
	 * 
	 * <p>텍스트를 암호화합니다.</p>
	 * 
	 * @param before : original text
	 * @param parameters : parameters (option, key, base64, keypadding are available)
	 * @return encrypted text
	 */
	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(null, null, before, null, null, 20, parameters);
	}
	
	/**
	 * <p>Encrypt texts.</p>
	 * 
	 * <p>텍스트를 암호화합니다.</p>
	 * 
	 * @param before : original text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param algorithm : How to encrypt text
	 * @param key : password of encryption
	 * @param keypadding : How to pad the password
	 * @param byteArrayEncoding : How to encode bytes after encryption
	 * @return encrypted text
	 */
	@Override
	public String convert(String before, long threadTerm, String algorithm, String key, String keypadding, String byteArrayEncoding)
	{
		return convert(before, threadTerm, algorithm, key, keypadding, byteArrayEncoding, null);		
	}
	
	/**
	 * <p>Encrypt texts.</p>
	 * 
	 * <p>텍스트를 암호화합니다.</p>
	 * 
	 * @param before : original text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param algorithm : How to encrypt text
	 * @param key : password of encryption
	 * @param keypadding : How to pad the password
	 * @param byteArrayEncoding : How to encode bytes after encryption
	 * @param charset : Character set
	 * @return encrypted text
	 */
	public String convert(String before, long threadTerm, String algorithm, String key, String keypadding, String byteArrayEncoding, String charset)
	{
		return convert(before, threadTerm, algorithm, key, keypadding, byteArrayEncoding, charset, new Hashtable<String, String>());
	}
	
	/**
	 * <p>Encrypt texts.</p>
	 * 
	 * <p>텍스트를 암호화합니다.</p>
	 * 
	 * @param before : original text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param algorithm : How to encrypt text
	 * @param key : password of encryption
	 * @param keypadding : How to pad the password
	 * @param byteArrayEncoding : How to encode bytes after encryption
	 * @param charset : Character set
	 * @param parameters : Parameters
	 * @return encrypted text
	 */
	@SuppressWarnings("restriction")
	public String convert(String before, long threadTerm, String algorithm, String key, String keypadding, String byteArrayEncoding, String charset, Map<String, String> parameters)
	{
		if(before == null) return null;
		String target = new String(before.trim());
		String keyParam = key;
				
		if(keyParam == null) return null;
				
		byte[] inputs;
		
		String apply_charset = "UTF-8";
		if(charset != null) apply_charset = charset;
			
		try
		{
			inputs = target.getBytes(apply_charset);
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
			return null;
		}
		
		byte[] keyByte;
		byte[] ivBytes;
		byte[] outputs;
		int need_keySize = 128;
		boolean useIv = true;
		boolean isSeed = false;
		boolean useSpecialPad = true;
		
		try
		{
			keyByte = key.getBytes(apply_charset);
		}
		catch(Exception e)
		{
			keyByte = key.getBytes();
		}
		
		try
		{
			String specialPad = keypadding;
			if(specialPad == null) useSpecialPad = true;
			else
			{
				if(specialPad.equalsIgnoreCase("zero") || specialPad.equalsIgnoreCase("0"))
				{
					useSpecialPad = false;
				}
				else if(specialPad.equalsIgnoreCase("special"))
				{
					useSpecialPad = true;
				}
			}
		}
		catch(Exception e)
		{
			useSpecialPad = true;
		}
		
		
		if(algorithm == null) algorithm = "DES";
		else algorithm = algorithm.trim();
		
		String paddings = "";
		
		if(algorithm.equalsIgnoreCase("DES"))
		{
			paddings = "DES/CBC/PKCS5Padding";
			need_keySize = 8;
			useIv = true;
		}
		else if(algorithm.equalsIgnoreCase("DESede"))
		{
			paddings = "TripleDES/ECB/PKCS5Padding";
			need_keySize = 24;
			useIv = true;
		}
		else if(algorithm.equalsIgnoreCase("AES"))
		{
			paddings = "AES";
			need_keySize = 16;
			useIv = false;
		}
		else if(algorithm.equalsIgnoreCase("SEED"))
		{
			isSeed = true;
		}
		else return null;
		
		byte[] checkKeyByte = new byte[need_keySize];
		ivBytes = new byte[checkKeyByte.length];
		
		Padder padder = null;
		if(useSpecialPad) padder = new Padder();
		
		for(int i=0; i<checkKeyByte.length; i++)
		{
			if(i < keyByte.length)
			{
				checkKeyByte[i] = keyByte[i];
			}
			else
			{
				checkKeyByte[i] = 0;
				if(useSpecialPad) checkKeyByte[i] = padder.getPaddingByte(keyParam.trim());
			}
		}
		keyByte = checkKeyByte;
		
		boolean useBase64 = false;
		String useBase64Str = byteArrayEncoding;
		
		if(useBase64Str != null)
		{
			try
			{
				useBase64 = Statics.parseBoolean(useBase64Str);
			}
			catch(Exception e)
			{
				if(useBase64Str.equalsIgnoreCase("base64"))
				{
					useBase64 = true;
				}
				else useBase64 = false;
			}
		}
		
		if(isSeed)
		{
			return Statics.applyScript(this, parameters, SEED_KISA.encrypt(target, keyParam, useBase64));
		}
		else
		{
			SecretKeySpec keySpec = new SecretKeySpec(keyByte, algorithm);
			IvParameterSpec ivSpec = null;
			if(useIv) ivSpec = new IvParameterSpec(ivBytes);
			
			Cipher cipher = null;
			
			try
			{
				cipher = Cipher.getInstance(paddings);
				if(useIv)
				{
					cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
				}
				else
				{
					cipher.init(Cipher.ENCRYPT_MODE, keySpec);
				}
				outputs = new byte[cipher.getOutputSize(inputs.length)];
				for(int i=0; i<outputs.length; i++)
				{
					outputs[i] = 0;
				}
				int enc_len = cipher.update(inputs, 0, inputs.length, outputs, 0);
				enc_len = enc_len + cipher.doFinal(outputs, enc_len);
				
							
				if(useBase64)
				{
					return Statics.applyScript(this, parameters, new sun.misc.BASE64Encoder().encode(outputs));
				}
				else
				{
					return Statics.applyScript(this, parameters, ByteConverter.bytesToString(outputs));
				}			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Controller.print("Error", true);
				Controller.println(" : " + e.getMessage());
				return before;
			}
		}
	}
		
	/**
	 * <p>Encrypt texts.</p>
	 * 
	 * <p>텍스트를 암호화합니다.</p>
	 * 
	 * @param stringTable : string table (null)
	 * @param syntax : syntax table (null)
	 * @param before : original text
	 * @param statusViewer : object which is used to show the process is alive
	 * @param statusField : object which is used to show text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param parameters : parameters (option, key, base64, keypadding are available)
	 * @return encrypted text
	 */
	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		String keyParam = parameters.get("key");
		if(keyParam == null)
		{
			keyParam = parameters.get("defaultoption");
		}
		
		if(keyParam == null) return null;
		
		String keypadding = parameters.get("keypadding");
		
		String byteArrayEncoding = null;
		
		try
		{
			byteArrayEncoding = String.valueOf(Statics.parseBoolean(parameters.get("base64")));
		}
		catch(Exception e)
		{
			// e.printStackTrace();
			byteArrayEncoding = null;
		}
		if(byteArrayEncoding == null)
		{
			byteArrayEncoding = parameters.get("byteencode");			
		}
		
		return convert(before, threadTerm, parameters.get("option"), keyParam, keypadding, byteArrayEncoding);
	}

	
	/**
	 * <p>Return available encryption algorithms.</p>
	 * 
	 * <p>암호화 알고리즘들을 반환합니다.</p>
	 */
	@Override
	public List<String> optionList()
	{
		List<String> results = new Vector<String>();
		results.add("DES");
		if(Statics.useUntestedFunction()) results.add("DESede");
		results.add("AES");		
		if(Statics.useUntestedFunction()) results.add("SEED");
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
	public String defaultParameterText()
	{		
		return "--key \"password1234\" --byteencode \"base64\"";
	}
	@Override
	public String getParameterHelp()
	{
		String locale = Controller.getSystemLocale();
		String results = "";
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			results = results + "사용 가능한 키 : key, keypadding, byteencode" + "\n\n";
			results = results + "key : 암호화에 쓰일 비밀번호" + "\n\n";
			results = results + "keypadding : 비밀번호를 정해진 길이에 맞추는 방법, zero 또는 special, MD5, SHA-512 사용 가능" + "\n\n";
			results = results + "byteencode : 암호화 결과로 만들어진 바이트 데이터를 텍스트로 변환할 방법, longcode 또는 base64 사용 가능" + "\n\n";
		}
		else
		{
			results = results + "Available keys : key, keypadding, byteencode" + "\n\n";
			results = results + "key : Password of encryption" + "\n\n";
			results = results + "keypadding : How to pad the password (Availables : zero, special, MD5, SHA-512)" + "\n\n";
			results = results + "byteencode : How to convert result data of encryption into the text (Availables : longcode, base64)" + "\n\n";
		}
		return results;
	}
	@Override
	public List<String> parameterKeyList()
	{
		Vector<String> keys = new Vector<String>();
		keys.add("key");
		keys.add("keypadding");
		keys.add("byteencode");
		keys.add("charset");
		return keys;
	}
	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters)
	{
		String key, algorithm, keypadMethod, charset;
		key = parameters.get("key");
		algorithm = parameters.get("option");
		keypadMethod = parameters.get("keypadding");
		charset = parameters.get("charset");
		return convert(befores, key, algorithm, keypadMethod, charset);
	}	
	@Override
	public boolean isEncryptingModule()
	{
		return true;
	}
	@Override
	public boolean isOptionEditable()
	{
		return false;
	}
	@Override
	public String getDefinitionName()
	{
		return "Encrypt";
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
