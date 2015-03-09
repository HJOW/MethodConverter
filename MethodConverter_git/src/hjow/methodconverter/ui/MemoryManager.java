package hjow.methodconverter.ui;

import hjow.methodconverter.Controller;
import hjow.methodconverter.MemoryTreats;
import hjow.methodconverter.ThreadRunner;
import hjow.methodconverter.ThreadTracer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Vector;

/**
 * <p>This class can help to show the memory status to user.</p>
 * 
 * <p>이 클래스는 사용자에게 메모리 상태를 보여주는 데 도움을 줍니다.</p>
 * 
 * @author HJOW
 *
 */
public class MemoryManager extends MemoryTreats implements ActionListener, WindowListener, ThreadRunner
{
	private long id = ((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long)(Math.random() * (Long.MAX_VALUE / 100000)));
	protected Component component;
	protected boolean threadRun = false;
	protected long threadGap = 500;
	protected List<Long> threadList = new Vector<Long>();
	
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 * 
	 */
	public MemoryManager()
	{
		super();
		refreshList();
	}
	
	/**
	 * <p>Return component object.</p>
	 * 
	 * <p>컴포넌트 객체를 반환합니다.</p>
	 * 
	 * @return component object (such as Panel, JPanel)
	 */
	public Component getComponent()
	{
		return component;
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}
	
	/**
	 * <p>Run thread.</p>
	 * 
	 * <p>쓰레드를 실행합니다.</p>
	 */
	public void open()
	{
		if(! isAlive())
		{
			threadRun = true;
			Thread createThread = new Thread(this);
			Controller.insertThreadObject(createThread);
			Controller.insertThreadObject(this);
			createThread.start();
		}		
		else threadRun = true;
	}
	/**
	 * <p>Return true if the thread is alive.</p>
	 * 
	 * <p>쓰레드가 살아 있으면 true 를 반환합니다.</p>
	 * 
	 * @return true if the thread is alive
	 */
	public boolean isAlive()
	{
		return threadRun;
	}
	/**
	 * <p>Stop thread.</p>
	 * 
	 * <p>쓰레드를 정지합니다.</p>
	 */
	public void close()
	{
		threadRun = false;
	}
	/**
	 * <p>This method is called in the thread.</p>
	 * 
	 * <p>이 메소드는 쓰레드 내에서 실행됩니다.</p>
	 */
	public synchronized void threadWork()
	{
		
	}
	
	/**
	 * <p>This method is called in the thread when the list is changed.</p>
	 * 
	 * <p>이 메소드는 리스트가 바뀌었을 때 쓰레드 내에서 실행됩니다.</p>
	 */
	public synchronized void refresh()
	{
		setStackTraceText(ThreadTracer.getThreadState());
	}
	
	/**
	 * <p>Input text on the stack trace area.</p>
	 * 
	 * <p>스택 추적 텍스트 영역에 텍스트를 넣습니다.</p>
	 * 
	 * @param text : stack trace infos
	 */
	protected void setStackTraceText(String text)
	{
		
	}
	
	/**
	 * <p>Load thread list again if the thread list is changed.</p>
	 * 
	 * <p>쓰레드 리스트를 다시 불러옵니다.</p>
	 */
	protected synchronized void refreshList()
	{
		List<Long> ids = Controller.getThreadIDs();
		boolean equals = true;
		
		
		if(ids.size() != threadList.size()) equals = false;
		else
		{
			for(int i=0; i<ids.size(); i++)
			{
				if(ids.get(i).longValue() != threadList.get(i).longValue())
				{
					equals = false;
					break;
				}
			}
		}
		
		if(! equals)
		{
			threadList = ids;			
			refresh();
		}
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
					threadWork();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				refreshList();
				Thread.sleep(threadGap + (int)(Math.random() * 10) + (ThreadTracer.activeCount() * 5));
			}
			catch (InterruptedException e)
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
		return getClass().getCanonicalName();
	}

	@Override
	public long getId()
	{
		return id;
	}
}
