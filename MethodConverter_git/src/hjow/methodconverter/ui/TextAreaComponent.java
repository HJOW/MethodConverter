package hjow.methodconverter.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.text.Document;

/**
 * <p>Objects implements this interface are may be TextArea compoenent.</p>
 * 
 * <p>이 인터페이스를 구현한 객체들은 텍스트 영역 컴포넌트 역할을 수행합니다.</p>
 * 
 * @author HJOW
 *
 */
public interface TextAreaComponent
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
	 * <p>Return Document object which has text value.</p>
	 * 
	 * <p>텍스트 값을 포함한 Document 객체를 반환합니다.</p>
	 * 
	 * @return Document object
	 */
	public Document getDocument();
	
	/**
	 * <p>Set document value.</p>
	 * 
	 * <p>문서값을 설정합니다.</p>
	 * 
	 * @param doc : Document
	 */
	public void setDocument(Document doc);
}
