package hjow.methodconverter;

import java.math.BigDecimal;

/**
 * <p>
 * This class have memory-treating static methods.
 * </p>
 * 
 * <p>
 * 이 클래스에는 메모리를 다루는 메소드들이 존재합니다.
 * </p>
 * 
 * @author HJOW
 *
 */
public class MemoryTreats
{
	/**
	 * <p>Convert data size value to the text with units.</p>
	 * 
	 * <p>데이터 크기 값을 텍스트로 변환합니다. 단위를 적용합니다.</p>
	 * 
	 * @param v : data size value (byte)
	 * @param scale : How precision to calculate
	 * @return text
	 */
	public static String dataSizeString(long v, int scale)
	{
		int unit = 0;
		BigDecimal value = new BigDecimal(String.valueOf(v));
		BigDecimal thousands = new BigDecimal("1024");
		value.setScale(scale);

		while (value.compareTo(thousands) > 0)
		{
			value = value.divide(thousands, scale, BigDecimal.ROUND_HALF_UP);
			unit++;
			if (unit >= 5) break;
		}

		String results = "";

		switch (unit)
		{
		case 0:
			results = " byte";
			break;
		case 1:
			results = " KB";
			break;
		case 2:
			results = " MB";
			break;
		case 3:
			results = " GB";
			break;
		case 4:
			results = " TB";
			break;
		case 5:
			results = " FB";
			break;
		}

		return String.valueOf(value) + results;
	}
	/**
	 * <p>Return memory status.</p>
	 * 
	 * <p>메모리 상태를 반환합니다.</p>
	 * 
	 * @return memory usage rate (freeMemory / totalMemory)
	 */
	public static double getMemoryStatus()
	{
		long totals = totalMemory();
		long frees = freeMemory();
		return ((totals - frees) / ((double) totals));
	}
	
	/**
	 * <p>Return total memory size.</p>
	 * 
	 * <p>사용 가능한 전체 메모리 크기를 반환합니다.</p>
	 * 
	 * @return memory size
	 */
	public static long totalMemory()
	{
		Runtime runtime = Runtime.getRuntime();
		return runtime.totalMemory();
	}
	
	/**
	 * <p>Return left memory size.</p>
	 * 
	 * <p>남은 메모리 크기를 반환합니다.</p>
	 * 
	 * @return memory size
	 */
	public static long freeMemory()
	{
		Runtime runtime = Runtime.getRuntime();
		return runtime.freeMemory();
	}
	
	/**
	 * <p>Return memory status as text. It seems such a gage bar.</p>
	 * 
	 * <p>메모리 상태를 텍스트로 반환합니다. 게이지바처럼 보이게 됩니다.</p>
	 * 
	 * @return text
	 */
	public static String getMemoryBar()
	{
		int bar_limits = 20;
		int calcs = (int) Math.round(getMemoryStatus() * bar_limits);
		String results = "";
		for(int i=0; i<bar_limits; i++)
		{
			if(i < calcs) results = results + "■";
			else results = results + "□";
		}
		return results;
	}
	
	/**
	 * <p>Remove unusing resources.</p>
	 * 
	 * <p>사용하지 않는 시스템 자원들을 반환합니다.</p>
	 */
	public static void clean()
	{
		System.gc();
	}
}
