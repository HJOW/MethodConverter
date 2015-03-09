package hjow.methodconverter.swingconverter;

/**
 * <p>The object which implements this interface can be transparent.</p>
 * 
 * <p>이 인터페이스를 구현한 객체는 투명해질 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public interface CanBeTransparent
{
	/**
	 * <p>Return opacity rates as float value. If this value is lower, this panel is become more transparent. 0.0 ~ 1.0</p>
	 * 
	 * <p>투명도를 실수 값으로 반환합니다. 낮을 수록 투명해집니다. 0.0 ~ 1.0 사이값을 사용합니다.</p>
	 * 
	 * @return opacity percentage
	 */
	public float getTransparency_opacity();
	/**
	 * <p>Set opacity as float value. If this value is lower, this panel is become more transparent. 0.0 ~ 1.0</p>
	 * 
	 * <p>투명도를 지정합니다. 낮을 수록 투명해집니다. 0.0 ~ 1.0 사이값을 사용합니다.</p>
	 * 
	 * @param transparency_opacity : opacity rates
	 */
	public void setTransparency_opacity(float transparency_opacity);
}
