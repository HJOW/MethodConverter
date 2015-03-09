package hjow.network;

/**
 * <p>Classes implement this interface has data as byte[] form.</p>
 * 
 * <p>이 인터페이스를 구현하는 클래스 객체는 바이트 데이터 형태를 가집니다.</p>
 * 
 * @author HJOW
 *
 */
public interface HasByteData
{
	/**
	 * <p>Return byte data.</p>
	 * 
	 * <p>바이트 데이터를 반환합니다.</p>
	 * 
	 * @return bytes
	 */
	public byte[] getBytes();
}
