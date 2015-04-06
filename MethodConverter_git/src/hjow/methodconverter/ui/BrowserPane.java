package hjow.methodconverter.ui;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.ThreadRunner;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * <p>The object which extends this class will be able to browse the web.</p>
 * 
 * <p>이 클래스를 상속하는 객체는 웹을 탐색할 수 있습니다.</p>
 * 
 * @author eduncom_cyk
 *
 */
public abstract class BrowserPane implements CanBeSurfWeb, ActionListener, ItemListener, WindowListener, HyperlinkListener, MouseListener, MouseMotionListener, ComponentListener
{
	protected String[] binaryForms = {"zip", "rar", "exe", "jar", "egg", "tar", "gz", "doc", "docx", "ppt", "pptx", "xls", "xlsx", "pdf"};
	protected List<String> visitList = new Vector<String>();
	protected int nowVisitingIndex = 0;
	protected String errorText = "";
	protected TextFieldComponent addressArea = null;
	protected StatusViewer statusViewer = null;
	protected StatusBar statusBar = null;
	
	/**
	 * <p>Return visited URL list.</p>
	 * 
	 * <p>방문했던 URL 리스트를 반환합니다. 이 리스트는 저장되지 않으므로 프로그램이 시작될 때마다 초기화됩니다.</p>
	 * 
	 * @return visited URLs
	 */
	public List<String> getBackList()
	{
		List<String> results = new Vector<String>();
		int limits = nowVisitingIndex;
		if(limits >= visitList.size()) limits = visitList.size() - 1;
		
		for(int i=0; i<limits; i++)
		{
			results.add(visitList.get(i));
		}
		return results;
	}
	/**
	 * <p>Return URLs can be go "forward".</p>
	 * 
	 * <p>앞으로 이동할 수 있는 페이지 리스트를 반환합니다.</p>
	 * 
	 * @return uRLs
	 */
	public List<String> getForwardList()
	{
		List<String> results = new Vector<String>();
		int limits = nowVisitingIndex;
		if(limits < 0) limits = 0;
		
		for(int i=limits; i<visitList.size(); i++)
		{
			results.add(visitList.get(i));
		}
		return results;
	}
	
	/**
	 * <p>This method will be overrided by child classes.</p>
	 * 
	 * <p>이 메소드는 하위 클래스로부터 재정의될 것입니다.</p>
	 * 
	 * @param url : URL
	 */
	protected abstract void setPage(String url) throws Exception;

	@Override
	public void goPage(String url) throws Exception
	{
		goPage(url, false);
	}
	
	/**
	 * <p>Load page and show.</p>
	 * 
	 * <p>페이지를 불러오고 보입니다.</p>
	 * 
	 * @param url : URL
	 * @param insideAction : If this is true, do not add URL on the visitList
	 * @exception MalformedURLException
	 */
	public void goPage(String url, boolean insideAction) throws Exception
	{
		if(! insideAction)
		{
			visitList.add(url);
			nowVisitingIndex = visitList.size() - 1;
		}
		setPage(url);
	}
	
	/**
	 * <p>Return component object which display webpage.</p>
	 * 
	 * <p>웹 페이지를 표현하는 컴포넌트 객체를 반환합니다.</p>
	 * 
	 * @return component object which display webpage
	 */
	public Component getRealComponent()
	{
		return getComponent();
	}

	@Override
	public void goForward() throws Exception
	{
		nowVisitingIndex++;
		if(nowVisitingIndex >= visitList.size()) nowVisitingIndex = visitList.size() - 1;
		if(nowVisitingIndex < 0) nowVisitingIndex = 0;
		if(visitList.size() == 0) return;
		goPage(visitList.get(nowVisitingIndex));
	}

	@Override
	public void goBack() throws Exception
	{
		nowVisitingIndex--;
		if(nowVisitingIndex < 0) nowVisitingIndex = 0;
		if(nowVisitingIndex >= visitList.size()) nowVisitingIndex = visitList.size() - 1;
		if(visitList.size() == 0) return;
		goPage(visitList.get(nowVisitingIndex));
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		if(ob == addressArea)
		{
			try
			{
				goPage(addressArea.getText());
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				showException(e1);
			}
		}		
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
	public void mouseDragged(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
		
	}
	protected void showException(Exception e1)
	{
		Controller.println(Statics.fullErrorMessage(e1));
		if(errorText != null)
		{
			String errorTextEdits = errorText;
			int paramIndex = errorTextEdits.indexOf("{ERROR_MESSAGE}");
			
			if(paramIndex >= 0)
			{
				errorTextEdits = errorTextEdits.replace("{ERROR_MESSAGE}", e1.getMessage());
			}
			
			paramIndex = errorTextEdits.indexOf("{ERROR_LOCALIZE_MESSAGE}");
			
			if(paramIndex >= 0)
			{
				errorTextEdits = errorTextEdits.replace("{ERROR_LOCALIZE_MESSAGE}", e1.getLocalizedMessage());
			}
			
			paramIndex = errorTextEdits.indexOf("{FULL_MESSAGE}");
			
			if(paramIndex >= 0)
			{
				errorTextEdits = errorTextEdits.replace("{FULL_MESSAGE}", Statics.fullErrorMessage(e1));
			}
		}
	}
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e)
	{		
		Object ob = e.getSource();
		if(ob == getRealComponent())
		{
			boolean isBinary = false;
			String urlGets;
			try
			{
				urlGets = String.valueOf(e.getURL());
				StringTokenizer slashTokenizer = new StringTokenizer(urlGets, "/");
				
				String fileName = "";
				while(slashTokenizer.hasMoreTokens())
				{
					fileName = slashTokenizer.nextToken();
				}
				
				if(binaryForms != null)
				{
					for(int i=0; i<binaryForms.length; i++)
					{
						if(fileName.endsWith(binaryForms[i]) || fileName.endsWith(binaryForms[i].toLowerCase()) || fileName.endsWith(binaryForms[i].toUpperCase()))
						{
							isBinary = true;
							break;
						}
					}
				}
				if(isBinary)
				{
					DownloadThread newDownload = new DownloadThread(e.getURL(), new File(Controller.getDefaultPath() + Controller.getFileSeparator() + fileName), getStatusViewer(), 25, 50);
					Controller.insertThreadObject(newDownload);
					newDownload.start();
				}
				else
				{
					goPage(String.valueOf(e.getURL()));
				}				
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				showException(e1);
			}
		}
	}
	
	/**
	 * <p>Return status viewer object which can show process status.</p>
	 * 
	 * <p>프로세스 상태를 화면에 표시하는 객체를 반환합니다.</p>
	 * 
	 * @return status viewer object
	 */
	public StatusViewer getStatusViewer()
	{
		return statusViewer;
	}
	public String[] getBinaryForms()
	{
		return binaryForms;
	}
	public void setBinaryForms(String[] binaryForms)
	{
		this.binaryForms = binaryForms;
	}
	public List<String> getVisitList()
	{
		return visitList;
	}
	public void setVisitList(List<String> visitList)
	{
		this.visitList = visitList;
	}
	public int getNowVisitingIndex()
	{
		return nowVisitingIndex;
	}
	public void setNowVisitingIndex(int nowVisitingIndex)
	{
		this.nowVisitingIndex = nowVisitingIndex;
	}
	public String getErrorText()
	{
		return errorText;
	}
	public void setErrorText(String errorText)
	{
		this.errorText = errorText;
	}
	public TextFieldComponent getAddressArea()
	{
		return addressArea;
	}
	public void setAddressArea(TextFieldComponent addressArea)
	{
		this.addressArea = addressArea;
	}
	public void setStatusViewer(StatusViewer statusViewer)
	{
		this.statusViewer = statusViewer;
	}
	@Override
	public void componentHidden(ComponentEvent e)
	{
		
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		
	}
	public StatusBar getStatusBar()
	{
		return statusBar;
	}
	public void setStatusBar(StatusBar statusBar)
	{
		this.statusBar = statusBar;
	}
}
class DownloadThread implements ThreadRunner
{
	private boolean alives = false;
	private URL url = null;
	private File target = null;
	private StatusViewer viewer = null;
	private int threadGap = 25;
	private int viewerGap = 100;
	private transient InputStream gets = null;
	private transient BufferedInputStream bufferedInput = null;
	private transient FileOutputStream outputs = null;
	private transient BufferedOutputStream bufferdOutput = null;
	
	public DownloadThread()
	{
		
	}
	public DownloadThread(URL url, File target)
	{
		this.url = url;
		this.target = target;
		start();
	}
	public DownloadThread(URL url, File target, int threadGap)
	{
		this.url = url;
		this.target = target;
		this.threadGap = threadGap;
	}
	public DownloadThread(URL url, File target, StatusViewer viewer)
	{
		this.url = url;
		this.target = target;
		this.viewer = viewer;
	}
	public DownloadThread(URL url, File target, StatusViewer viewer, int threadGap, int viewerGap)
	{
		this.url = url;
		this.target = target;
		this.viewer = viewer;
		this.threadGap = threadGap;
		this.viewerGap = viewerGap;
	}
	
	@Override
	public void run()
	{
		byte[] buffers = new byte[1024];
		int i = 0;
		int j = 0;
		int k = 0;
		try
		{
			gets = url.openStream();
			bufferedInput = new BufferedInputStream(gets);
			outputs = new FileOutputStream(target);
			bufferdOutput = new BufferedOutputStream(outputs);
			
			while(alives)
			{
				for(i=0; i<buffers.length; i++)
				{
					buffers[i] = 0;
				}
				
				k = bufferedInput.read(buffers);
				
				if(k < 0)
				{
					break;
				}
				
				bufferdOutput.write(buffers);
				
				try
				{
					j++;
					if(j >= viewerGap)
					{
						j = 0;
						if(viewer != null) viewer.nextStatus();
					}
				}
				catch(Exception e)
				{
					
				}
				
				try
				{
					Thread.sleep(threadGap);
				}
				catch(Exception e)
				{
					
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try
			{
				close();
			}
			catch(Exception e1)
			{
				
			}
		}
		finally
		{			
			try
			{
				close();
			}
			catch(Exception e)
			{
				
			}
		}
	}
	@Override
	public void close() throws IOException
	{
		alives = false;
		
		try
		{
			bufferdOutput.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			bufferedInput.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			outputs.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			gets.close();
		}
		catch(Exception e)
		{
			
		}
	}

	@Override
	public boolean isAlive()
	{
		return alives;
	}

	@Override
	public void start()
	{
		alives = true;
		new Thread(this).start();
	}

	@Override
	public String getThreadName()
	{
		return Thread.currentThread().getName();
	}

	@Override
	public long getId()
	{
		return Thread.currentThread().getId();
	}
	public boolean isAlives()
	{
		return alives;
	}
	public void setAlives(boolean alives)
	{
		this.alives = alives;
	}
	public URL getUrl()
	{
		return url;
	}
	public void setUrl(URL url)
	{
		this.url = url;
	}
	public File getTarget()
	{
		return target;
	}
	public void setTarget(File target)
	{
		this.target = target;
	}
	public StatusViewer getViewer()
	{
		return viewer;
	}
	public void setViewer(StatusViewer viewer)
	{
		this.viewer = viewer;
	}
	public int getThreadGap()
	{
		return threadGap;
	}
	public void setThreadGap(int threadGap)
	{
		this.threadGap = threadGap;
	}
	public int getViewerGap()
	{
		return viewerGap;
	}
	public void setViewerGap(int viewerGap)
	{
		this.viewerGap = viewerGap;
	}
}