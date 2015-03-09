package hjow.network;

import java.io.File;

/**
 * <p>This package will be sent when you receive the package successfully.</p>
 * 
 * <p>이 패키지는 당신이 어떤 패키지를 성공적으로 받았을 때 보내집니다.</p>
 * 
 * @author HJOW
 *
 */
public class SuccessReceivedPackage extends NetworkPackage
{
	private static final long serialVersionUID = 1912460025791110048L;
	private Long successReceivedPackageId;

	/**
	 * <p>Create new package object.</p>
	 * 
	 * <p>새 패키지 객체를 생성합니다.</p>
	 */
	public SuccessReceivedPackage()
	{
		super();
	}
	/**
	 * <p>Create new package object.</p>
	 * 
	 * <p>새 패키지 객체를 생성합니다.</p>
	 * 
	 * @param successReceivedPackageId : Successfully received package ID
	 */
	public SuccessReceivedPackage(long successReceivedPackageId)
	{
		super();
		this.successReceivedPackageId = new Long(successReceivedPackageId);
	}
	
	@Override
	public void saveContents(File file) throws Exception
	{
		new StringPackage("Success", "").saveContents(file);
	}

	@Override
	public String getPackageTypeName()
	{
		return "Success";
	}
	/**
	 * <p>Return successfully received package ID.</p>
	 * 
	 * <p>성공적으로 받은 패키지의 ID를 반환합니다.</p>
	 * 
	 * @return Successfully received package ID
	 */
	public Long getSuccessReceivedPackageId()
	{
		return successReceivedPackageId;
	}
	/**
	 * <p>Set successfully received package ID.</p>
	 * 
	 * <p>성공적으로 받은 패키지의 ID 정보를 입력합니다.</p>
	 * 
	 * @param successReceivedPackageId : Successfully received package ID
	 */
	public void setSuccessReceivedPackageId(Long successReceivedPackageId)
	{
		this.successReceivedPackageId = successReceivedPackageId;
	}

	@Override
	public String getContents()
	{
		return String.valueOf(successReceivedPackageId);
	}
}
