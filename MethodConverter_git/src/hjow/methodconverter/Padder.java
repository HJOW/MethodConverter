package hjow.methodconverter;

public class Padder
{
	private long paddingProgress = 0;
	public Padder()
	{
		resetPadding();
	}
	/**
	 * <p>Reset padding progress.</p>
	 * 
	 * <p>바이트 패딩 진행을 초기화합니다.</p>
	 */
	public void resetPadding()
	{
		paddingProgress = 0;
	}
	/**
	 * <p>Get byte value from padding algorithm.</p>
	 * 
	 * <p>패딩 알고리즘을 통해 바이트 값을 가져옵니다.</p>
	 * 
	 * @param keys : key of algorithm
	 * @return byte value
	 */
	public byte getPaddingByte(String keys)
	{
		long keyValue = 1;
		long calculated = 0;
		char[] charValue = keys.toCharArray();
		for(int i=0; i<charValue.length; i++)
		{
			calculated = (((int) charValue[i]) * paddingProgress);
			if(keyValue >= Long.MAX_VALUE - calculated) keyValue = 1;
			keyValue = keyValue + calculated;
		}
		paddingProgress++;
		return (byte) (keyValue % 127);		
	}
}
