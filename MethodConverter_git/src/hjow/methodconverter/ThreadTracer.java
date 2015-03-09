package hjow.methodconverter;

import java.util.Map;
import java.util.Set;

/**
 * <p>Print out or return all thread status.</p>
 * 
 * <p>전체 쓰레드 상태를 출력하거나 반환합니다.</p>
 * 
 * @author HJOW
 *
 */
public class ThreadTracer
{
	/**
	 * <p>Return how many threads are active.</p>
	 * 
	 * <p>얼마나 많은 쓰레드가 활성화되어 있는지를 반환합니다. 이 프로그램의 전체 자바 쓰레드가 그 대상입니다.</p>
	 * 
	 * @return how many threads are active
	 */
	public static int activeCount()
	{
		return Thread.activeCount();
	}
	/**
	 * <p>Return all thread status.</p>
	 * 
	 * <p>전체 쓰레드 상태를 반환합니다.</p>
	 * 
	 * @return all thread states
	 */
	public static String getThreadState()
	{
		StringBuffer results = new StringBuffer("");
		
		results = results.append(Controller.getString("Active threads") + " : " + Thread.activeCount() + "\n");
		results = results.append("\n\n");
		
		Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
		Set<Thread> keySet = stacks.keySet();
		
		for(Thread t : keySet)
		{
			StackTraceElement[] elements = stacks.get(t);
			results = results.append(Controller.getString("State") + " : " + String.valueOf(t.getState()) + "\n");
			results = results.append(Controller.getString("Name") + " : " + String.valueOf(t.getName()) + "\n");
			results = results.append(Controller.getString("ID") + " : " + String.valueOf(t.getId()) + ", " 
			+ Controller.getString("Priority") + " : " + String.valueOf(t.getPriority()) + "\n");
			for(StackTraceElement e : elements)
			{
				results = results.append(String.valueOf(e.toString()) + "\n");
			}	
			results = results.append("\n");
		}
		
		return results.toString();
	}
	/**
	 * <p>Print out all thread status.</p>
	 * 
	 * <p>전체 쓰레드 상태를 출력합니다.</p>
	 */
	public static void threadState()
	{
		Controller.println(getThreadState());
	}
}
