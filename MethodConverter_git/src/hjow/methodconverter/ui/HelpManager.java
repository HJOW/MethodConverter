package hjow.methodconverter.ui;

import hjow.convert.module.JustHelpModule;
import hjow.convert.module.Module;
import hjow.methodconverter.Controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Vector;

/**
 * <p>Help dialog object.</p>
 * 
 * <p>도움말 대화 상자 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class HelpManager implements ActionListener, ItemListener, MouseListener, WindowListener
{
	protected Window window;
	protected List<Module> modules = new Vector<Module>();
	protected int nameMaxLength = 20;
	
	/**
	 * <p>Open dialog.</p>
	 * 
	 * <p>대화 상자를 엽니다.</p>
	 */
	public abstract void open();
	
	/**
	 * <p>Close dialog.</p>
	 * 
	 * <p>대화 상자를 닫습니다.</p>
	 */
	public abstract void close();
	/**
	 * <p>Show help.</p>
	 * 
	 * <p>도움말 메시지를 표시합니다.</p>
	 * 
	 * @param module : Module object
	 */
	public void showHelp(Module module)
	{
		setHelpText(module.getHelps());
	}
	/**
	 * <p>Set text on the help message area.</p>
	 * 
	 * <p>도움말 영역에 텍스트를 표시합니다.</p>
	 * 
	 * @param text : help message
	 */
	public abstract void setHelpText(String text);
	
	/**
	 * <p>Take module list.</p>
	 * 
	 * <p>모듈 리스트를 가져와 보입니다.</p>
	 * 
	 */
	public void setList()
	{
		List<Module> modules = new Vector<Module>();		
		modules.addAll(Controller.getModules());
		
		setList(modules);
	}
	
	/**
	 * <p>Take module list.</p>
	 * 
	 * <p>모듈 리스트를 가져와 보입니다.</p>
	 * 
	 * @param modules : Module list
	 */
	public void setList(List<Module> modules)
	{
		List<String> moduleNames = new Vector<String>();
		String names = "";
		
		names = Controller.getString(Controller.firstMessage);
		if(names.length() >= nameMaxLength) names = names.substring(0, nameMaxLength - 2) + "...";
		moduleNames.add(names);
		
		for(int i=0; i<modules.size(); i++)
		{
			names = modules.get(i).getName();
			if(names.length() >= nameMaxLength) names = names.substring(0, nameMaxLength - 2) + "...";
			moduleNames.add(names);
		}
		setListText(moduleNames);
		this.modules.clear();
		this.modules.add(new JustHelpModule());
		this.modules.addAll(modules);
		
		showHelp(this.modules.get(0));
	}
	
	/**
	 * <p>This method will be overrided by AWT/Swing class.</p>
	 * 
	 * <p>이 메소드는 AWT/Swing 클래스들에 의해 재정의될 것입니다.</p>
	 * 
	 * @param lists : Module names
	 */
	protected abstract void setListText(List<String> lists);
	@Override
	public void windowActivated(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		Object ob = e.getSource();
		if(ob == window)
		{
			close();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e)
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
	public void mousePressed(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
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
	
}
