package hjow.msq.control;

import hjow.methodconverter.Controller;
import hjow.msq.ui.MainFrame;

public class MainControl
{
	private static MainFrame ui;
	private static QueryControl control;	
	
	public static final String DEFAULT_IO_STREAM_PARAMETER = "defaultStreamOption";
	public static final String STRING_TABLE_NAME = "localename";
	public static final String STRING_TABLE_FULL_NAME = "localename_formal";
	
	public static LogControl logControl = new LogControl();
	
	public static void main(String[] args)
	{
		control = new QueryControl();
		
		runGUI();
	}
	
	public static void runConsole()
	{
		
	}
	
	public static void runGUI()
	{
		ui = new MainFrame();
		ui.open();
	}
	
	public static MainFrame getMainFrame()
	{
		return ui;
	}
	public static QueryControl getControl()
	{
		return control;
	}
	public static String getOptionValue(String key)
	{
		return Controller.getOption(key);
	}
}
