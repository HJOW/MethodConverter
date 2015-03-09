package hjow.network;

import hjow.methodconverter.Controller;

import java.io.Serializable;

/**
 * <p>This is package for text.</p>
 * 
 * <p>이 패키지는 텍스트 전달을 위한 것입니다.</p>
 * 
 * @author HJOW
 *
 */
public class StringPackage extends ObjectPackage implements HasByteData
{
	private static final long serialVersionUID = -9072478786785148432L;
	
	/**
	 * <p>Create new package.</p>
	 * 
	 * <p>새 패키지 객체를 만듭니다.</p>
	 */
	public StringPackage()
	{
		super();
	}
	/**
	 * <p>Create new package.</p>
	 * 
	 * <p>새 패키지 객체를 만듭니다.</p>
	 */
	public StringPackage(String text, String name)
	{
		super(text, name);
	}
	
	/**
	 * <p>Return text includes.</p>
	 * 
	 * <p>포함된 텍스트를 반환합니다.</p>
	 * 
	 * @return text
	 */
	public String getText()
	{
		return (String) getObject();
	}
	
	/**
	 * <p>Input texts into this package.</p>
	 * 
	 * <p>텍스트를 이 패키지에 삽입합니다.</p>
	 * 
	 * @param text : text you want to input
	 */
	public void setText(String text)
	{
		setObject(text);
	}
	
	@Override
	public String getPackageTypeName()
	{
		return "String";
	}
	@Override
	public Serializable getContents()
	{
		return Controller.getString("Title") + " : " + getName() + "\n"
	+ Controller.getString("Package ID") + " : " + String.valueOf(getPackId()) + "\n"
	+ Controller.getString("When") + " : " + String.valueOf(getTime())
				+ "\n\n" + getText();
	}
	@Override
	public byte[] getBytes()
	{
		try
		{
			return getText().getBytes("UTF-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
