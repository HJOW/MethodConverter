package hjow.hobject;

import hjow.network.SerializableObject;
/**
 * <p>Many classes and interfaces in hjow.hobject package are not completed and not used in MethodConverter.</p>
 * 
 * <p>hjow.hobject 패키지 내의 클래스와 인터페이스는 아직 완성되지 않았으며 MethodConverter 에 사용되지 않았습니다.</p>
 * 
 * 
 * 
 * @author HJOW
 *
 */
public abstract class HInstance implements SerializableObject
{
	private static final long serialVersionUID = -8517437013761639275L;

	@Override
	public String toString()
	{
		return serialize();
	}
}
