package hjow.methodconverter.swingconverter;
import hjow.methodconverter.Controller;
import hjow.methodconverter.ThreadRunner;
import hjow.methodconverter.ui.StatusViewer;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 * 
 * <p>Show gagebar to show that the process is alive.</p>
 * 
 * <p>현재 프로세스 상태를 게이지바로 보이는 데 사용되는 클래스입니다.
 * Swing 라이브러리가 사용되었습니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingGageBar extends JProgressBar implements ThreadRunner, StatusViewer
{
	private static final long serialVersionUID = 2810323272734444923L;
	private long id = ((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long)(Math.random() * (Long.MAX_VALUE / 100000)));
	boolean threadswitch = true;
	boolean autoNext = false;
	int v = 0;
	SwingGageBar selfs = null;
	public SwingGageBar()
	{		
		selfs = this;
		start();
	}
	/**
	 * <p>Stop status-showing thread.</p>
	 * 
	 * <p>상태 보이기 쓰레드를 중지합니다.</p>
	 */
	public void close()
	{
		selfs = null;
		threadswitch = false;
	}
	/**
	 * <p>Set auto mode.</p>
	 * 
	 * <p>자동 모드를 켜거나 끕니다.</p>
	 * 
	 * @param mode : If this is true, auto mode will turned on.
	 */
	public void setAuto(boolean mode)
	{
		autoNext = mode;
	}
	/**
	 * <p>true if the auto mode is on.</p>
	 * 
	 * <p>자동 모드가 켜져 있는지 여부를 반환합니다.</p>
	 * 
	 * @return Auto mode.
	 */
	public boolean isAuto()
	{
		return autoNext;
	}
	/**
	 * <p>Reset gagebar.</p>
	 * 
	 * <p>게이지바의 값을 초기화합니다.</p>
	 */
	public void reset()
	{
		setValue(0);
	}
	@Override
	public void run()
	{
		while(threadswitch)
		{
			if(autoNext) nextStatus();
			SwingUtilities.invokeLater(new Runnable()
			{				
				@Override
				public void run()
				{
					selfs.setValue(v);					
				}
			});
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				
			}
		}
	}
	/**
	 * <p>Return component object to be added on GUI container.</p>
	 * 
	 * <p>GUI 컴포넌트를 반환합니다.</p>
	 */
	@Override
	public Component toComponent()
	{
		return this;
	}
	/**
	 * <p>Change status.</p>
	 * 
	 * <p>다음 상태로 바꿉니다.
	 * 프로세스가 살아있음을 보이려면 이 메소드를 반복 호출하세요.</p>
	 */
	@Override
	public void nextStatus()
	{
		v++;
		if(v >= 101) v = 0;
	}
	@Override
	public boolean isAlive()
	{
		return threadswitch;
	}
	@Override
	public void start()
	{
		Thread createThread = new Thread(this);
		Controller.insertThreadObject(createThread);
		Controller.insertThreadObject(this);
		createThread.start();
	}
	@Override
	public String getThreadName()
	{
		return this.getClass().getCanonicalName();
	}
	@Override
	public long getId()
	{
		return id;
	}

}
