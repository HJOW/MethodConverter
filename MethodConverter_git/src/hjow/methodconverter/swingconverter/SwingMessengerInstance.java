package hjow.methodconverter.swingconverter;

import hjow.methodconverter.ui.GUIMessengerInstance;

import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class SwingMessengerInstance extends GUIMessengerInstance
{
	private JFrame frame;
	private JDialog window;
	
	@Override
	public void init()
	{
		frame = new JFrame();
		window = new JDialog(frame);
		window.addWindowListener(this);
		frame.addWindowListener(this);
		
		window.setVisible(true);
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == frame || ob == window)
		{
			window.setVisible(false);
			frame.setVisible(false);
		}
	}
}
