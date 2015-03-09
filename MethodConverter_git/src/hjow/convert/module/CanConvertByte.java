package hjow.convert.module;

import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.Map;

/**
 * <p>Object which implements this interface can convert byte data.</p>
 * 
 * <p>이 인터페이스를 구현한 객체는 바이트 데이터를 변환할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public interface CanConvertByte extends Module
{
	/**
	 * <p>Convert bytes.</p>
	 * 
	 * <p>바이트 배열을 변환합니다.</p>
	 * 
	 * 
	 * @param befores : original bytes
	 * @param parameters : parameters
	 * @return bytes
	 */
	public byte[] convert(byte[] befores, Map<String, String> parameters);
	
	/**
	 * <p>Convert bytes.</p>
	 * 
	 * <p>바이트 배열을 변환합니다.</p>
	 * 
	 * 
	 * @param befores : original bytes
	 * @param parameters : parameters
	 * @param statusBar : can show text, can be null
	 * @param statusViewer : can show this process is alive, can be null
	 * @return bytes
	 */
	public byte[] convert(byte[] befores, Map<String, String> parameters, StatusBar statusBar, StatusViewer statusViewer);
	
	/**
	 * <p>Return true if this module is encrypting function.</p>
	 * 
	 * <p>이 모듈이 암호화 모듈이면 true 를 반환합니다.</p>
	 * 
	 * @return true if this module is encrypting function
	 */
	public boolean isEncryptingModule();	
}
