package hjow.convert.module;

/**
 * <p>Class which implement this interface encrypt or decrypt texts.</p>
 * 
 * <p>이 인터페이스를 구현하는 클래스는 텍스트를 암호화하거나 복호화하는 데 사용됩니다.</p>s
 * 
 * @author HJOW
 *
 */
public interface SecurityModule extends BothModule
{
	/**
	 * <p>Encrypt or decrypt texts.</p>
	 * 
	 * <p>텍스트를 암호화/복호화합니다.</p>
	 * 
	 * @param before : original text
	 * @param threadTerm : Thread term. If this is short, the process will finished early, but if this is too short, it will be unstability.
	 * @param algorithm : How to encrypt or decrypt text
	 * @param key : password of encryption
	 * @param keypadding : How to pad the password
	 * @param byteArrayEncoding : How to encode bytes after convertion
	 * @return converted text
	 */
	public String convert(String before, long threadTerm, String algorithm, String key, String keypadding, String byteArrayEncoding);
}
