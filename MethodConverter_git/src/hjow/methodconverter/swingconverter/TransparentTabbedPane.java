package hjow.methodconverter.swingconverter;

import hjow.methodconverter.Controller;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 * <p>This Tab Panel object can be transparent several percents.</p>
 * 
 * <p>이 탭 패널 객체는 일정 비율로 투명해질 수 있습니다. JTabbedPane 과 호환됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class TransparentTabbedPane extends JTabbedPane implements CanBeTransparent
{
	private static final long serialVersionUID = 3632898711014871814L;
	protected float transparency_opacity = Controller.DEFAULT_OPACITY_RATIO;
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(transparency_opacity >= 1.0) return;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency_opacity));
	}
	@Override
	public float getTransparency_opacity()
	{
		return transparency_opacity;
	}
	@Override
	public void setTransparency_opacity(float transparency_opacity)
	{
		this.transparency_opacity = transparency_opacity;
		for(int i=0; i<getComponentCount(); i++)
		{
			try
			{
				if(getComponent(i) instanceof CanBeTransparent)
				{
					((CanBeTransparent) getComponent(i)).setTransparency_opacity(transparency_opacity);
				}
			}
			catch(Exception e)
			{
				
			}
		}
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				repaint();
			}			
		});
	}
}
