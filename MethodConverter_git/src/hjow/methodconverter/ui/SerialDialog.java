package hjow.methodconverter.ui;

import hjow.convert.module.EncryptModule;
import hjow.methodconverter.Controller;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

/**
 * <p>This dialog can get serial code from user input.</p>
 * 
 * <p>이 대화 상자는 사용자에게 시리얼 코드 입력을 받습니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class SerialDialog implements ActionListener, WindowListener
{
	protected Dialog dialog;
	protected static int serialBlockCount = 5;
	protected static int eachBlockSize = 5;
	
	public abstract String getSerialText(int index);
	public void input()
	{
		String serialString = "";
		Vector<String> blocks = new Vector<String>();
		for(int i=0; i<serialBlockCount; i++)
		{
			serialString = serialString + getSerialText(i);
			if(i != 0) blocks.add(getSerialText(i));
			if(i < serialBlockCount - 1) serialString = serialString + "-";
		}
		
		Controller.setOption("serial", new EncryptModule().convert(serialString.trim(), 20, "AES", "serial", "special", "longcode"));
		// Controller.println(Controller.getGradeName());
		Controller.alert(Controller.getString("Save and restart this program to apply changes."));
		try
		{
			Controller.saveOption();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		close();
	}
	public void close()
	{
		dialog.setVisible(false);
	}
	public void open()
	{
		dialog.setVisible(true);
	}
	@Override
	public void windowClosed(WindowEvent e)
	{
		
		
	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		close();		
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
	public void actionPerformed(ActionEvent e)
	{
		
		
	}	
}
