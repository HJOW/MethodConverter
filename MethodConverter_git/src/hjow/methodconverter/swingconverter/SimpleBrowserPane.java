package hjow.methodconverter.swingconverter;

import hjow.methodconverter.ui.BrowserPane;

import java.awt.Component;

import javax.swing.JScrollPane;

/**
 * <p>This component can surf the internet.</p>
 * 
 * <p>이 컴포넌트 객체는 인터넷에 접속할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class SimpleBrowserPane extends BrowserPane
{
	protected TransparentEditorArea area;
	protected JScrollPane scrollPane;
	
	/**
	 * <p>Create new component object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 */
	public SimpleBrowserPane()
	{
		area = new TransparentEditorArea();
		area.setEditable(false);
		area.addHyperlinkListener(this);
		area.addMouseListener(this);
		area.addMouseMotionListener(this);
		scrollPane = new JScrollPane(area);
	}
	
	@Override
	protected void setPage(String url) throws Exception
	{
		area.setPage(url);
	}
	
	@Override
	public Component getRealComponent()
	{
		return area;
	}

	@Override
	public Component getComponent()
	{
		return scrollPane;
	}
}
