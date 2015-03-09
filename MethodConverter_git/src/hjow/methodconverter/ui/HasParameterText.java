package hjow.methodconverter.ui;

import java.awt.Window;
import java.util.List;

/**
 * <p>Object which implement this interface have parameter input GUI component.</p>
 * 
 * <p>이 인터페이스를 구현한 객체는 매개 변수를 입력받을 수 있는 GUI 컴포넌트를 가지고 있는 것입니다.</p>
 * 
 * @author HJOW
 *
 */
public interface HasParameterText
{
	/**
	 * <p>Return parameter text.</p>
	 * 
	 * <p>매개 변수 텍스트를 반환합니다.</p>
	 * 
	 * @return parameter text
	 */
	public String getParameterFieldText();
	
	/**
	 * <p>Set text on the parameter textfield.</p>
	 * 
	 * <p>매개 변수 입력 텍스트필드에 텍스트를 입력합니다.
	 * 
	 * @param text : parameter text
	 */
	public void setParameterFieldText(String text);
	
	/**
	 * <p>Open message dialog.</p>
	 * 
	 * <p>메시지 대화 상자를 엽니다.</p>
	 * 
	 * @param text : message
	 */
	public void alert(String text);
	
	/**
	 * <p>Return Window object which includes this.</p>
	 * 
	 * <p>이 객체를 포함하고 있는 창 객체를 반환합니다.</p>
	 * 
	 * @return Window object, such as Frame, Dialog, or JFrame, JDialog
	 */
	public Window getWindow();
	
	/**
	 * <p>Return number of module selected.</p>
	 * 
	 * <p>선택된 모듈 번호를 반환합니다.</p>
	 * 
	 * @return number of module selected
	 */
	public int getSelectedModule();
	
	/**
	 * <p>Return helps about parameter of selected module.</p>
	 * 
	 * <p>선택된 모듈의 매개 변수 관련 도움말을 반환합니다.</p>
	 * 
	 * @return helps about parameter of selected module
	 */
	public String getSelectedModuleParamHelp();
	
	/**
	 * <p>Return available parameter keys of selected module.</p>
	 * 
	 * <p>선택된 모듈의 매개 변수로 가능한 키들을 반환합니다.</p>
	 * 
	 * @return available parameter keys of selected module
	 */
	public List<String> getSelectedModuleParameterKeys();
	
	/**
	 * <p>Return selected module name.</p>
	 * 
	 * <p>선택한 모듈 이름을 반환합니다.</p>
	 * 
	 * @return selected module name
	 */
	public String getSelectedModuleName();
}
