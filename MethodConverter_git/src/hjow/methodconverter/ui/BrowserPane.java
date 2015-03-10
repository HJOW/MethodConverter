package hjow.methodconverter.ui;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
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
public abstract class BrowserPane implements CanBeSurfWeb, ActionListener, ItemListener, WindowListener, HyperlinkListener, MouseListener, MouseMotionListener
{
	protected List<String> visitList = new Vector<String>();
	protected int nowVisitingIndex = 0;
	protected String errorText = "";
	
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
	 * @param insideAction : If this is true, do not add URL on the visitList.
	 * @exception MalformedURLException, or IOException can be occured
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
		goPage(visitList.get(nowVisitingIndex));
	}

	@Override
	public void goBack() throws Exception
	{
		nowVisitingIndex--;
		if(nowVisitingIndex < 0) nowVisitingIndex = 0;
		goPage(visitList.get(nowVisitingIndex));
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		
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
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e)
	{		
		Object ob = e.getSource();
		if(ob == getRealComponent())
		{
			try
			{
				goPage(String.valueOf(e.getURL()));
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
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
		}
	}
}