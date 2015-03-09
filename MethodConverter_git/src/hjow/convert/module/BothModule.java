package hjow.convert.module;

/**
 * <p>Object which implements this interface can convert both byte data and text data.</p>
 * 
 * <p>이 인터페이스를 구현한 객체는 텍스트 데이터는 물론 바이트 데이터를 변환할 수도 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public interface BothModule extends ConvertModule, CanConvertByte
{

}
