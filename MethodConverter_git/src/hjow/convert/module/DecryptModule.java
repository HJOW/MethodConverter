package hjow.convert.module;

import hjow.methodconverter.ByteConverter;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Padder;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kisa.seed.SEED_KISA;

/**
 * <p>This module can decrypt text.</p>
 * 
 * <p>이 모듈은 텍스트를 복호화하는 데 사용됩니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class DecryptModule implements SecurityModule
{
	private static final long serialVersionUID = 8310626723161835401L;
	

	public DecryptModule()
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
			return "복호화";
		}
		else
		{
			return "Decrypt";
		}
	}
	
	/**
	 * <p>Decrypt bytes.</p>
	 * 
	 * <p>바이트 배열을 복호화합니다.</p>
	 * 
	 * <p>
	 * Available algorithm : DES, AES, DESede<br>
	 * Available keypadMethod : zero, special 
	 * </p>
	 * 
	 * @param befores : original bytes
	 * @param key : password
	 * @param algorithm : How to decrypt
	 * @param keypadMethod : How to pad key.
	 * @return bytes
	 */
	public byte[] convert(byte[] befores, String key, String algorithm, String keypadMethod)
	{
		return convert(befores, key, algorithm, keypadMethod, null);
	}
	/**
	 * <p>Decrypt bytes.</p>
	 * 
	 * <p>바이트 배열을 복호화합니다.</p>
	 * 
	 * <p>
	 * Available algorithm : DES, AES, DESede<br>
	 * Available keypadMethod : zero, special 
	 * </p>
	 * 
	 * @param befores : original bytes
	 * @param key : password
	 * @param algorithm : How to decrypt
	 * @param keypadMethod : How to pad key.
	 * @param charset : Character set
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
		
		int padType = 0;
		
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
				cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
			}
			else
			{
				cipher.init(Cipher.DECRYPT_MODE, keySpec);
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
	 * <p>Decrypt texts.</p>
	 * 
	 * <p>텍스트를 복호화합니다.</p>
	 * 
	 * @param before : encrypted text
	 * @param parameters : parameters (option, key, base64, keypadding are available)
	 * @return original text
	 */
	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(null, null, before, null, null, 20, parameters);
	}
	
	/**
	 * <p>Decrypt texts.</p>
	 * 
	 * <p>텍스트를 복호화합니다.</p>
	 * 
	 * @param before : Encrypted text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param algorithm : How to decrypt the text
	 * @param key : Password of decryption
	 * @param keypadding : How to pad key
	 * @param byteArrayEncoding : How to decrypt bytes
	 * @return original text
	 */
	@Override
	public String convert(String before, long threadTerm, String algorithm, String key, String keypadding, String byteArrayEncoding)
	{
		return convert(before, threadTerm, algorithm, key, keypadding, byteArrayEncoding, null);
	}
	
	/**
	 * <p>Decrypt texts.</p>
	 * 
	 * <p>텍스트를 복호화합니다.</p>
	 * 
	 * @param before : Encrypted text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param algorithm : How to decrypt the text
	 * @param key : Password of decryption
	 * @param keypadding : How to pad key
	 * @param byteArrayEncoding : How to decrypt bytes
	 * @param charset : Character set
	 * @return original text
	 */
	public String convert(String before, long threadTerm, String algorithm, String key, String keypadding, String byteArrayEncoding, String charset)
	{
		return convert(before, threadTerm, algorithm, key, keypadding, byteArrayEncoding, charset, new Hashtable<String, String>());
	}
	
	/**
	 * <p>Decrypt texts.</p>
	 * 
	 * <p>텍스트를 복호화합니다.</p>
	 * 
	 * @param before : Encrypted text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param algorithm : How to decrypt the text
	 * @param key : Password of decryption
	 * @param keypadding : How to pad key
	 * @param byteArrayEncoding : How to decrypt bytes
	 * @param charset : Character set
	 * @param parameters : Parameters
	 * @return original text
	 */
	public String convert(String before, long threadTerm, String algorithm, String key, String keypadding, String byteArrayEncoding, String charset, Map<String, String> parameters)
	{
		if(before == null) return null;
		String target = new String(before.trim());
		String option = algorithm;
		String keyParam = key;
				
		if(keyParam == null) return null;
		
		String apply_charset = "UTF-8";
		if(charset != null) apply_charset = charset;
				
		byte[] inputs;
		
		boolean useBase64 = false;
		try
		{
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
			
			if(useBase64)
			{
				inputs = new sun.misc.BASE64Decoder().decodeBuffer(target);
			}
			else
			{
				inputs = ByteConverter.stringToBytes(target);
			}		
			
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			Controller.alert(Controller.getString("Error") + " : " + e1.getMessage());
			return null;
		}
		
		byte[] keyByte;
		byte[] ivBytes;
		byte[] outputs;
		int need_keySize = 128;
		boolean useIv = true;
		
		try
		{
			keyByte = keyParam.getBytes(apply_charset);
		}
		catch(Exception e)
		{
			keyByte = keyParam.getBytes();
		}
		
		if(option == null) option = "DES";
		else option = option.trim();
		
		String paddings = "";
		boolean isSeed = false;
		boolean useSpecialPad = true;
		
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
		
		if(option.equalsIgnoreCase("DES"))
		{
			paddings = "DES/CBC/PKCS5Padding";
			need_keySize = 8;
			useIv = true;
		}
		else if(option.equalsIgnoreCase("DESede"))
		{
			paddings = "TripleDES/ECB/PKCS5Padding";
			need_keySize = 168;
			useIv = true;
		}
		else if(option.equalsIgnoreCase("AES"))
		{
			paddings = "AES";
			need_keySize = 16;
			useIv = false;
		}
		else if(option.equalsIgnoreCase("SEED"))
		{
			isSeed = true;
		}
		else return null;
		
		Padder padder = null;
		if(useSpecialPad) padder = new Padder();
		
		byte[] checkKeyByte = new byte[need_keySize];
		ivBytes = new byte[checkKeyByte.length];
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
		
		if(isSeed)
		{
			return Statics.applyScript(this, parameters, SEED_KISA.decrypt(target, keyParam, useBase64));
		}
		else
		{
			SecretKeySpec keySpec = new SecretKeySpec(keyByte, option);
			IvParameterSpec ivSpec = null;
			if(useIv) ivSpec = new IvParameterSpec(ivBytes);
			
			Cipher cipher = null;
			
			
			try
			{
				cipher = Cipher.getInstance(paddings);
				if(useIv)
				{
					cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
				}
				else
				{
					cipher.init(Cipher.DECRYPT_MODE, keySpec);
				}
				outputs = new byte[cipher.getOutputSize(inputs.length)];
				for(int i=0; i<outputs.length; i++)
				{
					outputs[i] = 0;
				}
				int enc_len = cipher.update(inputs, 0, inputs.length, outputs, 0);
				enc_len = enc_len + cipher.doFinal(outputs, enc_len);			
				
				return Statics.applyScript(this, parameters, new String(outputs, apply_charset));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
				return before;
			}
		}
	}
	/**
	 * <p>Decrypt texts.</p>
	 * 
	 * <p>텍스트를 복호화합니다.</p>
	 * 
	 * @param stringTable : string table (null)
	 * @param syntax : syntax table (null)
	 * @param before : encrypted text
	 * @param statusViewer : object which is used to show the process is alive
	 * @param statusField : object which is used to show text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param parameters : parameters (option, key, base64, keypadding are available)
	 * @return original text
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
			byteArrayEncoding = null;
		}
		if(byteArrayEncoding == null)
		{
			byteArrayEncoding = parameters.get("byteencode");
		}
		
		String charset = parameters.get("charset");
		
		return convert(before, threadTerm, parameters.get("option"), keyParam, keypadding, byteArrayEncoding, charset, parameters);
	}
	
	/**
	 * <p>Return available decryption algorithms.</p>
	 * 
	 * <p>복호화 알고리즘들을 반환합니다.</p>
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
			results = results + "key : 복호화에 쓰일 비밀번호" + "\n\n";
			results = results + "keypadding : 비밀번호를 정해진 길이에 맞추는 방법, zero, special, MD5, SHA-512 사용 가능" + "\n\n";
			results = results + "byteencode : 복호화 이전 텍스트를 바이트 데이터로 변환할 방법, longcode 또는 base64 사용 가능" + "\n\n";
		}
		else
		{
			results = results + "Available keys : key, keypadding, byteencode" + "\n\n";
			results = results + "key : Password of decryption" + "\n\n";
			results = results + "keypadding : How to pad the password (Availables : zero, special, MD5, SHA-512)" + "\n\n";
			results = results + "byteencode : How to convert text into bytes before decryption (Availables : longcode, base64)" + "\n\n";
		}
		return results;
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
	public byte[] convert(byte[] befores, Map<String, String> parameters,
			StatusBar statusBar, StatusViewer statusViewer)
	{
		return convert(befores, parameters);
	}
	@Override
	public boolean isEncryptingModule()
	{
		return false;
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
	public boolean isOptionEditable()
	{
		return false;
	}
	@Override
	public String getDefinitionName()
	{
		return "Decrypt";
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
}
