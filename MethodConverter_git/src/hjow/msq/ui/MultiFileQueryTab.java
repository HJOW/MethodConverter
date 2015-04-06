package hjow.msq.ui;

import hjow.msq.control.ErrorControl;
import hjow.msq.control.MainControl;
import hjow.msq.query.FileQuery;

import java.awt.Component;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

public class MultiFileQueryTab implements HasComponent
{
	private List<FileQuery> files = new Vector<FileQuery>();
	
	private String name;
	private JPanel panel;
	private long uniqueKey = new Random().nextLong();
	
	public MultiFileQueryTab()
	{
		panel = new JPanel();
	}
	
	@Override
	public void run()
	{
		for(int i=0; i<files.size(); i++)
		{
			try
			{
				MainControl.getControl().query(files.get(i));
			}
			catch (SQLException e)
			{
				ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
			}
			catch (IOException e)
			{
				ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
			}
		}		
	}

	@Override
	public Component getComponent()
	{
		return panel;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public long getUniqueKey()
	{
		return uniqueKey;
	}

	@Override
	public boolean canBeRun()
	{
		return true;
	}

	@Override
	public void refreshContent() throws IOException
	{
		
	}

	@Override
	public void close()
	{
		
	}

	public List<FileQuery> getFiles()
	{
		return files;
	}

	public void setFiles(List<FileQuery> files)
	{
		this.files = files;
	}
}
