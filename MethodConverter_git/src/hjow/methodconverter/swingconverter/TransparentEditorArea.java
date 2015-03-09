package hjow.methodconverter.swingconverter;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ui.TextAreaComponent;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;

/**
 * <p>This editor panel can be transparent.</p>
 * 
 * <p>이 에디터 패널은 투명해질 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class TransparentEditorArea extends JEditorPane implements CanBeTransparent, TextAreaComponent
{
	private static final long serialVersionUID = 6326782043227913526L;
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
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				repaint();
			}			
		});
	}
	@Override
	public Component getComponent()
	{
		return this;
	}
}
