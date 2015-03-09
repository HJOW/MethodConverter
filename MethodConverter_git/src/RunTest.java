import java.util.List;
import java.util.Vector;

import hjow.hobject.HArray;
import hjow.hobject.HObject;
import hjow.hobject.HText;

/**
 * <p>This class is made to do some tests.</p>
 * 
 * 
 * @author HJOW
 *
 */
public class RunTest
{
	public static void main(String[] args) throws Exception
	{
		HObject newObject = new HObject();
		
		HArray newArray = new HArray();
		List<HObject> instances = new Vector<HObject>();
		for(int i=0; i<10; i++)
		{
			HText newOne = new HText();
			newOne.setText("World" + i);
			HObject newOne2 = new HObject();
			newOne2.put("arr" + i, newOne);
			instances.add(newOne2);
		}
		newArray.setArray(instances);
		
		newObject.put("Hello", newArray);
		
		HText newTwo = new HText();
		newTwo.setText("World !!");
		newObject.put("Hello2", newTwo);
		
		String serializes = newObject.serialize();
		System.out.println("\n\nFirst\n");
		System.out.println(serializes);
		HObject reOne = null;
		try
		{
			reOne = new HObject(serializes);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("\n\nSecond\n");
		System.out.println(reOne.serialize());
	}
}
