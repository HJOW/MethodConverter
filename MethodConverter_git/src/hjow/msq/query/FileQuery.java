package hjow.msq.query;

import hjow.msq.control.IOControl;

import java.io.File;
import java.io.IOException;

public class FileQuery extends Query
{
	private static final long serialVersionUID = -3555421960839351023L;
	private File file;

	public FileQuery(File file)
	{
		super();
		this.file = file;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	@Override
	public String getQuery() throws IOException
	{
		return IOControl.readText(file, "");
	}

	@Override
	public void setQuery(String content) throws IOException
	{
		// TODO Auto-generated method stub
		
	}
}
