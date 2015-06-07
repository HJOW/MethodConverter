package hjow.convert.javamethods;

import hjow.convert.module.DecryptModule;
import hjow.convert.module.EncryptModule;
import hjow.methodconverter.ByteConverter;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Hashtable;

/**
 * <p>This object is used in script. In script, you can use this object named javaobject.</p>
 * <p>This class can be used as a library.</p>
 *  
 * 
 * <p>이 객체는 스크립트 내에서 사용됩니다. javaobject 라는 이름으로 사용할 수 있습니다.</p>
 * <p>라이브러리로 사용할 수도 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class MethodContainer implements Serializable
{
	private static final long serialVersionUID = 7268627655021226492L;
	/**
	 * <p>Constructor of javaobject.</p>
	 */
	public MethodContainer()
	{
		
	}
	/**
	 * <p>Return version of this program.</p>
	 * 
	 * <p>이 프로그램의 버전 번호를 반환합니다.</p>
	 * 
	 * @return version
	 */
	public String ver()
	{
		return ver(false);
	}
	/**
	 * <p>Return version of this program.</p>
	 * 
	 * <p>이 프로그램의 버전 번호를 반환합니다.</p>
	 * 
	 * @param detail : If this is true, return build numbers.
	 * @return version
	 */
	public String ver(boolean detail)
	{
		String results = "";
		
		for(int i=0; i<Controller.versions.length; i++)
		{
			results = results + String.valueOf(Controller.versions[i]);
			if(i < Controller.versions.length - 1) results = results + ".";
		}
		
		try
		{
			if(detail)
			{
				results = results + " " + String.valueOf(Controller.buildNumber);
			}
		}
		catch(Exception e)
		{
			
		}
		
		return results;
	}
	
	/**
	 * <p>Calculate power.</p>
	 * 
	 * <p>지수를 계산합니다.</p>
	 * 
	 * @param v1 : value
	 * @param v2 : exponent
	 * @return powered value
	 */
	public double pow(double v1, double v2)
	{
		return Math.pow(v1, v2);
	}
	
	/**
	 * <p>Return random number in 0 ~ max value</p>
	 * 
	 * <p>0과 max 값 사이의 임의의 값을 반환합니다.</p>
	 * 
	 * @param max : Max value of random number
	 * @return random number
	 */
	public double random(double max)
	{
		return Math.random() * max;
	}
	
	/**
	 * <p>Round real-number.</p>
	 * 
	 * <p>실수를 반올림해 정수화합니다.</p>
	 * 
	 * @param value : real number
	 * @return rounded integer
	 */
	public long round(double value)
	{
		return Math.round(value);
	}
	
	/**
	 * <p>Flooring real-number.</p>
	 * 
	 * <p>실수를 내림해 정수화합니다.</p>
	 * 
	 * @param value : real number
	 * @return floored integer
	 */
	public long floor(double value)
	{
		return (long) Math.floor(value);
	}
	
	/**
	 * <p>Ceiling real-number.</p>
	 * 
	 * <p>실수를 올림해 정수화합니다.</p>
	 * 
	 * @param value : real number
	 * @return ceiled integer
	 */
	public long ceil(double value)
	{
		return (long) Math.ceil(value);
	}
	
	/**
	 * <p>Calculate absolute value.</p>
	 * 
	 * <p>절대값을 구합니다.</p>
	 * 
	 * @param v1 : original value
	 * @return : absolute value
	 */
	public double abs(double v1)
	{
		return Math.abs(v1);
	}
	/**
	 * <p>Calculate sine.</p>
	 * 
	 * <p>Sine 삼각함수를 계산합니다.</p>
	 * 
	 * @param v1 : original value
	 * @return : sine value
	 */
	public double sin(double v1)
	{
		return Math.sin(v1);
	}
	/**
	 * <p>Calculate cosine.</p>
	 * 
	 * <p>Cosine 삼각함수를 계산합니다.</p>
	 * 
	 * @param v1 : original value
	 * @return : cosine value
	 */
	public double cos(double v1)
	{
		return Math.cos(v1);
	}
	/**
	 * <p>Calculate tangent.</p>
	 * 
	 * <p>Tangent 삼각함수를 계산합니다.</p>
	 * 
	 * @param v1 : original value
	 * @return : tan value
	 */
	public double tan(double v1)
	{
		return Math.tan(v1);
	}
	/**
	 * <p>Calculate arc-sine.</p>
	 * 
	 * <p>Arc-sine 함수를 계산합니다.</p>
	 * 
	 * @param v1 : original value
	 * @return : arc-sine value
	 */
	public double asin(double v1)
	{
		return Math.asin(v1);
	}
	/**
	 * <p>Calculate arc-cosine.</p>
	 * 
	 * <p>Arc-cosine 함수를 계산합니다.</p>
	 * 
	 * @param v1 : original value
	 * @return : arc-cosine value
	 */
	public double acos(double v1)
	{
		return Math.acos(v1);
	}
	/**
	 * <p>Calculate arc-tangent.</p>
	 * 
	 * <p>Arc-tangent 함수를 계산합니다.</p>
	 * 
	 * @param v1 : original value
	 * @return : arc-tangent value
	 */
	public double atan(double v1)
	{
		return Math.atan(v1);
	}
	/**
	 * <p>Convert radian value into degree.</p>
	 * 
	 * <p>라디안 값을 360도 단위로 변환합니다.</p>
	 * 
	 * @param v1 : radian value
	 * @return : degree value
	 */
	public double toDegrees(double v1)
	{
		return Math.toDegrees(v1);
	}
	/**
	 * <p>Convert degree value into radians.</p>
	 * 
	 * <p>360도 단위 값을 라디안 단위로 변환합니다.</p>
	 * 
	 * @param v1 : degree value
	 * @return : radian value
	 */
	public double toRadians(double v1)
	{
		return Math.toRadians(v1);
	}
	/**
	 * 
	 * <p>Get PI value.</p>
	 * 
	 * <p>PI 값을 구합니다.</p>
	 * 
	 * @return PI (3.141592...)
	 */
	public double pi()
	{
		return Math.PI;
	}
	/**
	 * <p>Get E value.</p>
	 * 
	 * <p>E 값을 구합니다.</p>
	 * 
	 * @return E (2.7182818...)
	 */
	public double e()
	{
		return Math.E;
	}
	/**
	 * <p>Create new BigInteger object which can have infinite-scale integer.</p>
	 * 
	 * <p>무한대의 자리수의 정수를 사용할 수 있는 BigInteger 객체를 만듭니다.</p>
	 * 
	 * @param value : integer
	 * @return BigInteger object
	 */
	public BigInteger bigint(long value)
	{
		return new BigInteger(String.valueOf(value));
	}
	/**
	 * <p>Create new BigInteger object which can have infinite-scale integer.</p>
	 * 
	 * <p>무한대의 자리수의 정수를 사용할 수 있는 BigInteger 객체를 만듭니다.</p>
	 * 
	 * @param value : may be string which can be parsed to integer
	 * @return BigInteger object
	 */
	public BigInteger bigint(Object value)
	{
		return new BigInteger(String.valueOf(value));
	}
	/**
	 * <p>Create new BigDecimal object which can have infinite-scale floating number.</p>
	 * 
	 * <p>무한대의 자리수의 실수를 사용할 수 있는 BigDecimal 객체를 만듭니다.</p>
	 * 
	 * @param value : double value
	 * @return BigInteger object
	 */
	public BigDecimal bigdec(double value)
	{
		return new BigDecimal(String.valueOf(value));
	}
	/**
	 * <p>Create new BigDecimal object which can have infinite-scale floating number.</p>
	 * 
	 * <p>무한대의 자리수의 실수를 사용할 수 있는 BigDecimal 객체를 만듭니다.</p>
	 * 
	 * @param value : may be string which can be parsed to integer
	 * @return BigInteger object
	 */
	public BigDecimal bigdec(Object value)
	{
		return new BigDecimal(String.valueOf(value));
	}
	/**
	 * <p>Show text on the status bar.</p>
	 * 
	 * <p>상태바에 글자를 출력합니다.</p>
	 * 
	 * @param text : text which will be shown at the status bar.
	 */
	public void setStatusText(String text)
	{
		Controller.setStatusText(text);		
	}
	/**
	 * <p>Get option value.</p>
	 * 
	 * <p>옵션 값을 반환합니다.</p>
	 * 
	 * @param option : option key
	 * @return option value
	 */
	public String getOption(String option)
	{
		return Controller.getOption(option);
	}
	/**
	 * <p>Get option value.</p>
	 * 
	 * <p>옵션 값을 반환합니다.</p>
	 * 
	 * @param index : option index
	 * @return option value
	 */
	public String getOption(int index)
	{
		return Controller.getOption(index);
	}
	/**
	 * <p>Parse text which can be parsed to real-number.</p>
	 * 
	 * <p>텍스트를 실수 값으로 변환합니다.</p>
	 * 
	 * 
	 * @param original : text
	 * @return double value
	 */
	public double parseDouble(String original)
	{		
		return Double.parseDouble(original);
	}
	/**
	 * <p>Parse text which can be parsed to boolean.</p>
	 * 
	 * <p>텍스트를 논리 값으로 변환합니다.</p>
	 * 
	 * 
	 * @param original : text
	 * @return boolean value
	 */
	public boolean parseBoolean(String original)
	{
		return Statics.parseBoolean(original);
	}
	/**
	 * <p>Parse text which can be parsed to integer.</p>
	 * 
	 * <p>텍스트를 정수 값으로 변환합니다.</p>
	 * 
	 * 
	 * @param original : text
	 * @return integer value
	 */
	public long parseInt(String original)
	{
		return Long.parseLong(original);
	}
	/**
	 * <p>Parse text which can be parsed to integer.</p>
	 * 
	 * <p>텍스트를 정수 값으로 변환합니다.</p>
	 * 
	 * 
	 * @param original : text
	 * @param radix : Radix, default is 10. For example, use 16 if you want to use hexadecimal value.
	 * @return integer value
	 */
	public long parseInt(String original, long radix)
	{
		return Long.parseLong(original, (int) radix);
	}
	
	/**
	 * <p>Create new String object.</p>
	 * 
	 * <p>String 객체를 만듭니다.</p>
	 * 
	 * @return String object
	 */
	public String newJavaString()
	{
		return new String();
	}
	
	/**
	 * <p>Create new String object.</p>
	 * 
	 * <p>String 객체를 만듭니다.</p>
	 * 
	 * @param byteArray : byte array
	 * @return String object
	 */
	public String newJavaString(byte[] byteArray)
	{
		return new String(byteArray);
	}
	
	/**
	 * <p>Create new String object.</p>
	 * 
	 * <p>String 객체를 만듭니다.</p>
	 * 
	 * @param charArray : char array
	 * @return String object
	 */
	public String newJavaString(char[] charArray)
	{
		return new String(charArray);
	}
	
	/**
	 * <p>Create new String object.</p>
	 * 
	 * <p>String 객체를 만듭니다.</p>
	 * 
	 * @param byteArray : byte array
	 * @param charset : character set
	 * @return String object
	 */
	public String newJavaString(byte[] byteArray, String charset) throws Exception
	{
		return new String(byteArray, charset);
	}
	
	/**
	 * <p>Parse any objects into the text.</p>
	 * 
	 * <p>객체를 텍스트로 변환합니다.</p>
	 * 
	 * @param object : object which have toString() method.
	 * @return text
	 */
	public String toJavaString(Object object)
	{
		return String.valueOf(object);
	}
	/**
	 * <p>Parse integer into the text.</p>
	 * 
	 * <p>정수를 텍스트로 변환합니다.</p>
	 * 
	 * @param value : Integer value
	 * @return text
	 */
	public String toJavaString(long value)
	{
		return String.valueOf(value);
	}
	/**
	 * <p>Parse real number into the text.</p>
	 * 
	 * <p>실수를 텍스트로 변환합니다.</p>
	 * 
	 * @param value : Double value
	 * @return text
	 */
	public String toJavaString(double value)
	{
		return String.valueOf(value);
	}
	/**
	 * <p>Parse boolean into the text.</p>
	 * 
	 * <p>논리값을 텍스트로 변환합니다.</p>
	 * 
	 * @param value : boolean value
	 * @return text
	 */
	public String toJavaString(boolean value)
	{
		return String.valueOf(value);
	}
	/**
	 * <p>Replace some pattern into whatever you want.</p>
	 * 
	 * <p>텍스트의 특정 패턴을 지정한 텍스트로 전부 바꿉니다.</p>
	 * 
	 * 
	 * @param original : original text
	 * @param part : target pattern
	 * @param newOne : new text
	 * @return replaced text
	 */
	public String replace(String original, String part, String newOne)
	{
		return original.replace(part, newOne);
	}
	/**
	 * <p>Eliminate spaces from starts and ends of the text.</p>
	 * 
	 * <p>텍스트의 시작과 끝 부분의 공백을 제거합니다.</p> 
	 * 
	 * @param original : original text
	 * @return trimmed text
	 */
	public String trim(String original)
	{
		return original.trim();
	}
	/**
	 * <p>Create new StringTokenizer object which can be used to split.</p>
	 * 
	 * <p>StringTokenizer 객체를 반환합니다. 이 객체는 특정 구분자를 통해 여러 단어로 텍스트를 나눌 때 사용됩니다.</p>
	 * 
	 * @param original : original text
	 * @param delim : delimiter
	 * @return StringTokenizer object
	 */
	public StringTokenizer stringTokenizer(String original, String delim)
	{
		return new StringTokenizer(original, delim);
	}
	/**
	 * <p>Substring.</p>
	 * 
	 * <p>부분 문자열을 구합니다.</p>
	 * 
	 * @param original : original text
	 * @param startLocation : start index
	 * @return substringed text
	 */
	public String substring(String original, int startLocation)
	{
		return original.substring(startLocation);
	}
	/**
	 * <p>Substring.</p>
	 * 
	 * <p>부분 문자열을 구합니다.</p>
	 * 
	 * @param original : original text
	 * @param startLocation : start index
	 * @param endLocation : end index
	 * @return substringed text
	 */
	public String substring(String original, int startLocation, int endLocation)
	{
		return original.substring(startLocation, endLocation);
	}
	
	/**
	 * <p>Create new list object.</p>
	 * 
	 * <p>리스트 객체를 만듭니다.</p>
	 * 
	 * @return List object
	 */
	public List<Object> newList()
	{
		return new LinkedList<Object>();
	}
	
	/**
	 * <p>Create new map object.</p>
	 * 
	 * <p>맵 객체를 만듭니다.</p>
	 * 
	 * @return Map object
	 */
	public Map<Object, Object> newMap()
	{
		return new Hashtable<Object, Object>();
	}
		
	/**
	 * <p>Create new array.</p>
	 * 
	 * <p>배열을 만듭니다.</p>
	 * 
	 * @param size : Array size
	 * @return array
	 */
	public Object[] newArray(int size)
	{
		return new Object[size];
	}
	
	/**
	 * <p>Create new array.</p>
	 * 
	 * <p>배열을 만듭니다.</p>
	 * 
	 * @param size : Array size
	 * @return array
	 */
	public byte[] newByteArray(int size)
	{
		return new byte[size];
	}
	
	/**
	 * <p>Create new array.</p>
	 * 
	 * <p>배열을 만듭니다.</p>
	 * 
	 * @param size : Array size
	 * @return array
	 */
	public long[] newIntegerArray(int size)
	{
		return new long[size];
	}
	
	/**
	 * <p>Create new array.</p>
	 * 
	 * <p>배열을 만듭니다.</p>
	 * 
	 * @param size : Array size
	 * @return array
	 */
	public char[] newCharacterArray(int size)
	{
		return new char[size];
	}
	
	/**
	 * <p>Check if the object is exception object.</p>
	 * 
	 * <p>객체가 예외 객체인지 확인합니다.</p>
	 * 
	 * 
	 * @param object : Object which can be exception object
	 * @return true if the object is exception object.
	 */
	public boolean isException(Object object)
	{
		return (object instanceof Throwable);
	}
	
	/**
	 * <p>Convert long-code text into byte array.</p>
	 * 
	 * <p>긴 코드 텍스트를 바이트 배열로 변환합니다.</p>
	 * 
	 * @param digits : long-code text
	 * @return byte array
	 */
	public byte[] stringToBytes(String digits)
	{
		return ByteConverter.stringToBytes(digits);
	}
	
	/**
	 * <p>Convert byte array into long-code text.</p>
	 * 
	 * <p>바이트 배열을 긴 코드 텍스트로 변환합니다.</p>
	 * 
	 * @param bytes : byte array
	 * @return long-code text
	 */
	public String bytesToString(byte[] bytes)
	{
		return ByteConverter.bytesToString(bytes);
	}
	
	/**
	 * <p>Remove special symbol "\65279" which is created when the user save text as UTF-8 at Windows OS.</p> 
	 * 
	 * <p>"\65279" 특수기호를 제거합니다. 이 기호는 윈도우 상에서 텍스트를 UTF-8로 저장 시 생성됩니다.</p>
	 * 
	 * @param target : original text
	 * @return symbol removed text
	 */
	public String r65279(String target)
	{
		char[] targets = target.toCharArray();
		String results = "";
		
		for(int i=0; i<targets.length; i++)
		{
			if(((int)targets[i]) != 65279)
			{
				results = results + String.valueOf(targets[i]);
			}
		}
		return results;
	}	
	/**
	 * <p>텍스트를 암호화합니다.</p>
	 * 
	 * <p>Encrypt text</p>
	 * 
	 * 
	 * @param befores : original text
	 * @param keys : password of encryption
	 * @param types : encryption type, algorithm. (AES, DES are available)
	 * @return encrypted text
	 */
	public String encrypt(String befores, String keys, String types)
	{
		EncryptModule encryptor = new EncryptModule();
		Hashtable<String, String> parameters = new Hashtable<String, String>();
		parameters.put("key", keys);
		parameters.put("option", types);
		return encryptor.convert(befores, parameters);
	}
	
	/**
	 * <p>텍스트를 암호화합니다.</p>
	 * 
	 * <p>Encrypt text</p>
	 * 
	 * @param befores : original bytes
	 * @param keys : password of encryption
	 * @param types : encryption type, algorithm. (AES, DES are available)
	 * @return encrypted bytes
	 */
	public byte[] encrypt(byte[] befores, String keys, String types)
	{
		EncryptModule encryptor = new EncryptModule();
		return encryptor.convert(befores, keys, types, "zero");
	}
	
	/**
	 * <p>텍스트를 복호화합니다.</p>
	 * 
	 * <p>Decrypt text</p>
	 * 
	 * @param befores : encrypted bytes
	 * @param keys : password of decryption
	 * @param types : decryption type, algorithm. (AES, DES are available)
	 * @return original bytes
	 */
	public byte[] decrypt(byte[] befores, String keys, String types)
	{
		DecryptModule decryptor = new DecryptModule();
		return decryptor.convert(befores, keys, types, "zero");
	}
	
	/**
	 * <p>텍스트를 복호화합니다.</p>
	 * 
	 * <p>Decrypt text</p>
	 * 
	 * 
	 * @param befores : encrypted text
	 * @param keys : password of decryption
	 * @param types : decryption type, algorithm. (AES, DES are available)
	 * @return original text
	 */
	public String decrypt(String befores, String keys, String types)
	{
		DecryptModule decryptor = new DecryptModule();
		Hashtable<String, String> parameters = new Hashtable<String, String>();
		parameters.put("key", keys);
		parameters.put("option", types);
		return decryptor.convert(befores, parameters);
	}
	
	/**
	 * <p>Go to sleep the thread.</p>
	 * 
	 * <p>이 메소드가 실행되는 쓰레드를 잠시 멈춥니다.</p>
	 * 
	 * @param threadTerm : Sleeping time
	 * @throws Exception
	 */
	public void sleep(long threadTerm) throws Exception
	{
		Thread.sleep(threadTerm);
	}
	
	/**
	 * <p>Print out the text on the default console.</p>
	 * 
	 * <p>텍스트를 기본 콘솔에 출력합니다.</p>
	 * 
	 * @param str : text
	 */
	public void print(String str)
	{
		Controller.print(str);
	}
	
	/**
	 * <p>Jump to the new line on the default console.</p>
	 * 
	 * <p>기본 콘솔에서 줄을 띕니다.</p>
	 * 
	 */
	public void println()
	{
		Controller.println();
	}
	
	/**
	 * <p>Print out the text and jump to the new line on the default console.</p>
	 * 
	 * <p>텍스트를 기본 콘솔에 출력하고 줄을 띕니다.</p>
	 * 
	 * @param str : text
	 */
	public void println(String str)
	{
		Controller.println(str);
	}
}
