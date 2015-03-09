package hjow.hobject;

import java.util.List;
import java.util.Vector;

/**
 * <p>Many classes and interfaces in hjow.hobject package are not completed and not used in MethodConverter.</p>
 * 
 * <p>hjow.hobject 패키지 내의 클래스와 인터페이스는 아직 완성되지 않았으며 MethodConverter 에 사용되지 않았습니다.</p>
 * 
 * 
 * <p>Array object which can include HObjects.</p>
 * 
 * <p>HObject 들을 많이 담을 수 있는 배열 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public class HArray extends HInstance
{
	private static final long serialVersionUID = -3133130273013428523L;
	private List<HObject> array;

	public HArray()
	{
		array = new Vector<HObject>();
	}
	
	public List<HObject> getArray()
	{
		return array;
	}

	public void setArray(List<HObject> array)
	{
		this.array = array;
	}

	@Override
	public String serialize()
	{
		if(array == null) return "[]";
		StringBuffer results = new StringBuffer("");
		
		results = results.append("[\n");
		for(int i=0; i<array.size(); i++)
		{
			results = results.append(" " + array.get(i).serialize() + "\n");
			if(i < array.size() - 1) results = results.append(",");
		}
		results = results.append("]");
		return results.toString();
	}
}
