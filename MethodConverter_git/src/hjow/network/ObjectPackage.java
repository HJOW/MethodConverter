package hjow.network;
import hjow.methodconverter.Controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * <p>This package can include object.</p>
 * 
 * <p>이 패키지는 객체를 포함할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class ObjectPackage extends NetworkPackage
{
	private static final long serialVersionUID = -4318236317808258719L;
	private Serializable object;
	
	/**
	 * <p>Create new package.</p>
	 * 
	 * <p>새 패키지 객체를 만듭니다.</p>
	 */
	public ObjectPackage()
	{
		super();
	}
	
	/**
	 * <p>Create new package.</p>
	 * 
	 * <p>새 패키지 객체를 만듭니다.</p>
	 * 
	 * @param object : Object you want to include. String object is available.
	 * @param name : name(title) of the package
	 */
	public ObjectPackage(Serializable object, String name)
	{
		super(name);
		setObject(object);
	}
	
	/**
	 * <p>Return object included in.</p>
	 * 
	 * <p>포함된 객체를 반환합니다.</p>
	 * 
	 * @return object
	 */
	public Serializable getObject()
	{
		return object;
	}
	
	/**
	 * <p>Insert object.</p>
	 * 
	 * <p>객체를 삽입합니다.</p>
	 * 
	 * @param object : The object you want to insert
	 */
	public void setObject(Serializable object)
	{
		this.object = object;
	}
	
	@Override
	public String toString()
	{
		String results = "";
		if(getName() != null) results = Controller.getString("Title") + " : " + getName() + "\n";
		if(getPackId() != null) results = Controller.getString("Package ID") + " : " + String.valueOf(getPackId()) + "\n";
		if(getEncrypted() != null) results = Controller.getString("Nickname") + " (" + Controller.getString("Encrypted") + ") : " + getEncrypted() + "\n";
		
		results = results + "\n" + String.valueOf(object);
		return results;
	}
	@Override
	public String getPackageTypeName()
	{
		return "Object";
	}
	@Override
	public Serializable getContents()
	{
		return object;
	}

	@Override
	public void saveContents(File file) throws Exception
	{
		if(object == null) return;
		
		if((object instanceof byte[]) || (object instanceof HasByteData))
		{
			byte[] datas;
			if(object instanceof byte[]) datas = (byte[]) object;
			else datas = ((HasByteData) object).getBytes();
			
			FileOutputStream stream = null;
			BufferedOutputStream bufferedStream = null;
			try
			{		
				stream = new FileOutputStream(file);
				bufferedStream = new BufferedOutputStream(stream);
				bufferedStream.write(datas);				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					bufferedStream.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					stream.close();
				}
				catch(Exception e)
				{
					
				}
			}			
		}
		else
		{
			FileWriter writer = null;
			BufferedWriter bufferedWriter = null;
			try
			{
				StringTokenizer lineToken = null;
				if(object instanceof SerializableObject) lineToken = new StringTokenizer(String.valueOf(((SerializableObject) object).serialize()), "\n");
				else  lineToken = new StringTokenizer(String.valueOf(object), "\n");
				
				writer = new FileWriter(file);
				bufferedWriter = new BufferedWriter(writer);
				
				while(lineToken.hasMoreTokens())
				{
					bufferedWriter.write(lineToken.nextToken());
					bufferedWriter.newLine();
				}				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					bufferedWriter.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					writer.close();
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}
}	
