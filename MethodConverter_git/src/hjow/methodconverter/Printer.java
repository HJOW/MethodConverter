package hjow.methodconverter;

/**
 * 
 * <p>This class object print text on console. Console can be the system console.</p>
 * 
 * <p>이 객체는 텍스트를 콘솔에 출력합니다.</p>
 * 
 * @author HJOW
 *
 */
public class Printer implements ThreadRunner
{
	private long id = ((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long)(Math.random() * (Long.MAX_VALUE / 100000)));
	protected volatile boolean threadRun = false;
	protected long prequency = 50;
	protected boolean autoMode = false;
	private String buffer;
	
	
	/**
	 * <p>Create Printer object.</p>
	 * 
	 * <p>프린터 객체를 생성합니다.</p>
	 */
	public Printer()
	{
		
	}
	
	/**
	 * <p>Default print method. Override this method to change console.</p>
	 * 
	 * <p>기본 출력 메소드. 다른 콘솔 방식으로 바꾸려면 이 메소드를 오버라이드하면 됩니다.</p>
	 * 
	 * @param str : text
	 */
	public void print(String str)
	{
		System.out.print(str);		
	}
	
	/**
	 * <p>Print text and jump to the new line.</p>
	 * 
	 * <p>출력 후 줄을 띄웁니다.</p>
	 * 
	 * @param str : text
	 */
	public void println(String str)
	{
		System.out.print(str);
		newLine();
	}
	
	/**
	 * <p>Jump to the new line.</p>
	 * 
	 * <p>기본 줄 띄우기</p>
	 */
	public void newLine()
	{
		print("\n");
	}
	
	/**
	 * <p>This method run in thread repeatly.</p>
	 * 
	 * <p>이 메소드는 쓰레드 내에서 반복 실행됩니다.</p>
	 */
	public void runInThread()
	{
		int preventInfinites = 0;
		while(Controller.getTextCountOnList() >= 1)
		{
			buffer = Controller.getTextOnList();
			if(buffer != null) print(buffer);
			else if(autoMode)
			{
				if(Controller.getTextCountOnList() <= 0) close();
			}
			preventInfinites++;
			if(preventInfinites >= 1000)
			{
				try
				{
					preventInfinites = 0;
					Thread.sleep(20);
				}
				catch(Exception e)
				{
					
				}				
			}
		}		
	}
	
	/**
	 * <p>Return true if this thread is alive.</p>
	 * 
	 * <p>쓰레드가 살아 있으면 true를 반환합니다.</p>
	 * 
	 * @return
	 */
	public boolean isAlive()
	{
		return threadRun;
	}
	
	/**
	 * <p>Set auto mode. When auto mode is turned on, the thread is closed when no text is on the list.</p>
	 * 
	 * <p>자동 모드를 설정합니다. 자동 모드가 켜져 있으면 출력할 텍스트가 없을 때 쓰레드가 자동으로 닫힙니다.</p>
	 * 
	 * @param auto : auto mode
	 */
	public void setAuto(boolean auto)
	{
		autoMode = auto;
	}
	
	/**
	 * <p>Close the thread.</p>
	 * 
	 * <p>쓰레드를 닫습니다.</p>
	 */
	public void close()
	{
		threadRun = false;
	}
	/**
	 * <p>Open the thread. If auto mode is turned on, thread is open when the thread is closed.</p>
	 * 
	 * <p>쓰레드를 엽니다. 자동 모드가 켜져 있으면 쓰레드가 닫혀 있을 때만 엽니다.</p>
	 */
	public void open()
	{
		if(autoMode)
		{
			openWhenClosed();
		}
		else
		{
			threadRun = true;
			Thread createThread = new Thread(this);
			Controller.insertThreadObject(createThread);
			Controller.insertThreadObject(this);
			createThread.start();
		}
	}
	
	/**
	 * <p>Thread is open when the thread is closed.</p>
	 * 
	 * <p> 쓰레드가 닫혀 있다면 쓰레드를 엽니다.</p>
	 */
	public void openWhenClosed()
	{
		if(! isAlive())
		{
			threadRun = true;
			Thread createThread = new Thread(this);
			Controller.insertThreadObject(createThread);
			Controller.insertThreadObject(this);
			createThread.start();
		}
	}
	
	/**
	 * <p>Request focus here when this object is GUI based.</p>
	 * 
	 * <p>이 객체가 GUI 환경용일 경우 입력 커서를 요청합니다.</p>
	 */
	public void focus()
	{
		
	}
	
	/**
	 * <p>Clear here when this object is GUI based.</p>
	 * 
	 * <p>이 객체가 GUI 환경용일 경우 출력 내용을 비웁니다.</p>
	 */
	public void clear()
	{
		
	}
	
	@Override
	public void run()
	{
		while(threadRun)
		{			
			try
			{
				try
				{
					runInThread();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				Thread.sleep(prequency);
			}
			catch(Exception e)
			{
				
			}
		}
	}

	@Override
	public void start()
	{
		open();
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
