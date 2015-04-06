package hjow.msq.query;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Query implements Serializable
{
	private static final long serialVersionUID = -1785174020740495783L;
	private transient PreparedStatement preparedStatement = null;
	private boolean readOnly = false;
	
	public abstract String getQuery() throws IOException;
	public abstract void setQuery(String content) throws IOException;
	public PreparedStatement getPreparedStatement(Connection connection) throws IOException, SQLException
	{
		if(preparedStatement == null) preparedStatement = connection.prepareStatement(getQuery()); 
		return preparedStatement;
	}
	public boolean isReadOnly()
	{
		return readOnly;
	}
	public void setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly;
	}
	public void save() throws Exception
	{
		
	}
	public void load() throws Exception
	{
		
	}
	public void close()
	{
		try
		{
			preparedStatement.close();
		}
		catch(Exception e)
		{
			
		}
		preparedStatement = null;
	}
}
