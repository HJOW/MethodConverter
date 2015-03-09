package hjow.methodconverter.ui;
import hjow.methodconverter.Controller;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.StringTokenizer;

/**
 * <p>This component can be used for convert text via multiple modules.</p>
 * 
 * <p>이 컴포넌트는 여러 모듈을 사용해 텍스트를 변환하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class MultipleConvertPanel implements IsComponent, ActionListener, ItemListener
{
	protected Component component;
	
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 */
	public MultipleConvertPanel()
	{
		super();
		init();
	}
	
	/**
	 * <p>Initialize many components.</p>
	 * 
	 * <p>컴포넌트들을 초기화합니다.</p>
	 */
	public void init()
	{
		
	}
	
	@Override
	public Component getComponent()
	{
		return component;
	}
	
	public void convert()
	{
		String gets = getOrderText();
		String results = getContentText();
		StringTokenizer lineToken = new StringTokenizer(gets, "\n");
		String lineOne;
		long lineNumber = 0;
		while(lineToken.hasMoreTokens())
		{
			lineNumber++;
			try
			{
				lineOne = lineToken.nextToken().trim();
				if(lineOne.startsWith("#")) continue;
				results = Controller.convert(results, lineOne);
			}
			catch(Exception e)
			{
				Controller.alert(Controller.getString("Error") + " " + Controller.getString("at") + " " + String.valueOf(lineNumber) + " : " + e.getMessage());
				e.printStackTrace();
			}			
		}
		setContentText(results);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{		
		
	}
	
	/**
	 * <p>Return order text.</p>
	 * 
	 * <p>명령 텍스트를 반환합니다.</p>
	 * 
	 * 
	 * @return order textarea value
	 */
	public abstract String getOrderText();
	
	/**
	 * <p>Set order text on the textarea.</p>
	 * 
	 * <p>명령문을 텍스트 영역에 삽입합니다.</p>
	 * 
	 * @param text : orders
	 */
	public abstract void setOrderText(String text);
	
	/**
	 * <p>Return contents.</p>
	 * 
	 * <p>변환할 텍스트로 입력된 내용을 반환합니다.</p>
	 * 
	 * @return text
	 */
	public abstract String getContentText();
	
	/**
	 * <p>Set contents which will be converted.</p>
	 * 
	 * <p>변환할 텍스트 영역 란에 텍스트를 입력합니다.</p> 
	 * 
	 * @param text : target text
	 */
	public abstract void setContentText(String text);
}
