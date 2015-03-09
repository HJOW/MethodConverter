package hjow.network;

import java.io.Serializable;

/**
 * <p>The object implements this interface can serialize to text.</p>
 * 
 * <p>이 인터페이스를 구현한 객체는 텍스트 형식으로 직렬화할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public interface SerializableObject extends Serializable
{
	/**
	 * <p>Serialize the object.</p>
	 * 
	 * <p>객체를 직렬화합니다.</p>
	 * 
	 * @return serialized text
	 */
	public String serialize();
}
