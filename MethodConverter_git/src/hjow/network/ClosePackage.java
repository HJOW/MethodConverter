package hjow.network;

import java.io.File;

/**
 * <p>This package is send to close the connection.</p>
 * 
 * <p>이 패키지는 연결을 닫기 위해 전송됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class ClosePackage extends NetworkPackage
{
	private static final long serialVersionUID = 7101948414956601452L;
	
	/**
	 * <p>Create new package.</p>
	 * 
	 * <p>새 패키지 객체를 만듭니다.</p>
	 */
	public ClosePackage()
	{
		super("close connection");
	}
	
	@Override
	public String toString()
	{
		return "Close connection from " + getName();
	}

	@Override
	public String getPackageTypeName()
	{
		return "Close";
	}

	@Override
	public void saveContents(File file) throws Exception
	{
		new StringPackage("Close", "").saveContents(file);		
	}
}
