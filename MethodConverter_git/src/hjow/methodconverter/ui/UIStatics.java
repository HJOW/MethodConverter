package hjow.methodconverter.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.SwingUtilities;

/**
 * <p>This class has many static methods about UI.</p>
 * 
 * <p>이 클래스에는 UI 에 관련된 여러 정적 메소드들이 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class UIStatics
{
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Map<String, Object> staticObjects = new Hashtable<String, Object>();
	
	/**
	 * <p>Locate center on screen.</p>
	 * 
	 * <p>화면 가운데로 창을 위치시킵니다.</p>
	 * 
	 * @param window : Frame, Dialog object
	 */
	public static void locateCenter(Window window)
	{
		window.setLocation((int)(screenSize.getWidth()/2 - window.getWidth()/2), (int)(screenSize.getHeight()/2 - window.getHeight()/2));
	}
	/**
	 * <p>Run action safety.</p>
	 * 
	 * <p>동작을 안전하게 실행합니다.</p>
	 * 
	 * @param actions : Runnable object
	 */
	public static void runSafe(Runnable actions, Map<String, Object> params)
	{
		staticObjects.clear();
		staticObjects.putAll(params);
		SwingUtilities.invokeLater(actions);
	}
}
