package hjow.msq.query;

import hjow.msq.control.IOControl;
import hjow.msq.control.MainControl;

import java.io.File;

public class FileRelatedQuery extends StringQuery
{
	private static final long serialVersionUID = 408310875329024741L;
	private File file = null;
	
	public FileRelatedQuery()
	{
		super();
	}
	public FileRelatedQuery(String content)
	{
		super(content);
	}
	
	public void save() throws Exception
	{
		if(file != null) IOControl.saveText(file, getQuery(), MainControl.DEFAULT_IO_STREAM_PARAMETER);
	}
	public void load() throws Exception
	{
		if(file != null) setQuery(IOControl.readText(file, MainControl.DEFAULT_IO_STREAM_PARAMETER));
	}
	public File getFile()
	{
		return file;
	}
	public void setFile(File file)
	{
		this.file = file;
	}
}
