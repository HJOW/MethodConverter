package hjow.methodconverter.ui;

/**
 * 
 * <p>Manager class that have some tabs.</p>
 * 
 * <p>매니저 클래스입니다. 여러 탭으로 구성되어 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class AdvancedManager extends Manager
{
	private static final long serialVersionUID = -5241198124518399003L;
	
	public static final String CONVERT = "convert";
	public static final String MULTIPLE_CONVERT = "multiple_convert";
	public static final String PACKAGE_RECEIVE = "package_receive";
	public static final String MODULE_LIST = "module_list";
	public static final String MEMORY_MANAGER = "memory_manager";
	public static final String PRINTER = "printer";
	
	/**
	 * <p>Return now-selected tab name.</p>
	 * 
	 * <p>현재 선택된 탭 이름을 반환합니다.</p>
	 * 
	 * @return tab name
	 */
	public abstract String getSelectedTab();	
}
