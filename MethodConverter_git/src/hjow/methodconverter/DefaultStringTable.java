package hjow.methodconverter;
import java.util.Hashtable;
import java.util.Map;
/**
 * <p>Default string table object.</p>
 * 
 * <p>기본 스트링 테이블 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public class DefaultStringTable extends StringTable
{
	private static final long serialVersionUID = 8554441167442469208L;
	
	/**
	 * <p>Create new string table.</p>
	 * 
	 * <p>새 스트링 테이블을 만듭니다.</p>
	 */
	public DefaultStringTable()
	{
		super();
		table = new Hashtable<String, String>();
	}
	/**
	 * <p>Create new string table from Map object.</p>
	 * 
	 * <p>Map 객체로부터 새 스트링 테이블을 만듭니다.</p>
	 * 
	 * @param data : Map object, can be Hashtable object
	 */
	public DefaultStringTable(Map<String, String> data)
	{
		super();
		table = new Hashtable<String, String>(data);
	}	
	
	/**
	 * <p>Create new string table from text.</p>
	 * 
	 * <p>텍스트로부터 새 스트링 테이블을 만듭니다.</p>
	 * 
	 * @param data : text
	 */
	public DefaultStringTable(String data)
	{
		super();
		table = Statics.stringToHashtable(data);
	}
}
