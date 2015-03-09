package hjow.network;

import java.io.File;

/**
 * <p>This interface is implemented by the savable packages.</p>
 * 
 * <p>이 인터페이스는 저장 가능한 패키지 클래스에 의해 구현됩니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public interface FileSavable extends SerializableObject
{
	/**
	 * <p>Save contents as file.</p>
	 * 
	 * <p>내용을 파일로 저장합니다.</p>
	 * 
	 * @param file : File object includes the file path.
	 * @throws Exception
	 */
	public void saveContents(File file) throws Exception;
}
