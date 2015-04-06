package hjow.msq.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainListener implements ActionListener, ItemListener, WindowListener, MouseListener, MouseMotionListener, ChangeListener
{
	private MainFrame ui;
	public MainListener(MainFrame ui)
	{
		this.ui = ui;
	}
	public void close()
	{
		ui = null;
	}
	@Override
	public void mouseDragged(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
		
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == ui.getWindow())
		{
			ui.close();
		}
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		
	}
	@Override
	public void stateChanged(ChangeEvent e)
	{
		
	}

}
