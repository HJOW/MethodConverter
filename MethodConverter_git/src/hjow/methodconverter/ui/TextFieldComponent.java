package hjow.methodconverter.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * <p>Objects implements this interface are may be TextField compoenent.</p>
 * 
 * <p>이 인터페이스를 구현한 객체들은 텍스트 필드 컴포넌트 역할을 수행합니다.</p>
 * 
 * @author HJOW
 *
 */
public interface TextFieldComponent
{
	/**
	 * <p>Set text value.</p>
	 * 
	 * <p>텍스트 값을 설정합니다.</p>
	 * 
	 * @param str : text
	 */
	public void setText(String str);
	
	/**
	 * <p>Return text value.</p>
	 * 
	 * <p>텍스트 값을 반환합니다.</p>
	 * 
	 * @return text
	 */
	public String getText();
	
	/**
	 * <p>Return component object can be added on AWT and Swing panel.</p>
	 * 
	 * <p>AWT와 Swing 과 호환되는 컴포넌트 객체를 반환합니다.</p>
	 * 
	 * @return Component object
	 */
	public Component getComponent();
	
	/**
	 * <p>Return background color value as Color object.</p>
	 * 
	 * <p>배경색 값을 Color 객체로 반환합니다.</p>
	 * 
	 * @return Color object
	 */
	public Color getBackground();
	
	/**
	 * <p>Return foreground color value as Color object.</p>
	 * 
	 * <p>전경색 값을 Color 객체로 반환합니다.</p>
	 * 
	 * @return Color object
	 */
	public Color getForeground();
	
	/**
	 * <p>Set background.</p>
	 * 
	 * <p>배경색을 지정합니다.</p>
	 * 
	 * @param color : background
	 */
	public void setBackground(Color color);
	
	/**
	 * <p>Set foreground.</p>
	 * 
	 * <p>전경색을 지정합니다.</p>
	 * 
	 * @param color : foreground
	 */
	public void setForeground(Color color);
	
	/**
	 * <p>Add action event.</p>
	 * 
	 * <p>AWT/Swing 호환 액션 이벤트를 연결시킵니다.</p>
	 * 
	 * @param e : ActionListener object
	 */
	public void addActionListener(ActionListener e);
	
	/**
	 * <p>Resize this component.</p>
	 * 
	 * <p>이 컴포넌트의 크기를 변경합니다.</p>
	 * 
	 * @param w : Width
	 * @param h : Height
	 */
	public void setSize(int w, int h);
}
