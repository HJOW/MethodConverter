package hjow.methodconverter.swingconverter;

import hjow.methodconverter.ui.StatusBar;
import java.awt.Component;
import javax.swing.JTextField;

/**
 * <p>Status bar created with Swing library</p>
 * 
 * <p>Swing 기반의 상태 바 입니다.</p>
 * 
 * @author HJOW
 *
 */
public class SwingStatusBar extends JTextField implements StatusBar
{
	private static final long serialVersionUID = -2042349145818520934L;

	public SwingStatusBar()
	{
		super(10);
		setEditable(false);
	}
	@Override
	public Component toComponent()
	{
		return this;
	}
	@Override
	public void clear()
	{
		setText("");
	}
}
