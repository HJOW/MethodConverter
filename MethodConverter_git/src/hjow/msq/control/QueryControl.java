package hjow.msq.control;

import hjow.methodconverter.ClassManager;
import hjow.msq.query.Query;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

public class QueryControl
{
	private Connection conn = null;
	private String connectionName = "";
	public Map<Query, String> statements = new Hashtable<Query, String>();
	public boolean connect(File file, String driverName, String url, String id, String pw)
	{
		boolean results = false;
		
		close();
		
		try
		{
			if(file != null) ClassManager.load(file, driverName, true);
			else ClassManager.load(driverName);
		}
		catch(Exception e)
		{
			ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
		}
		
		try
		{
			conn = DriverManager.getConnection(url, id, pw);
			connectionName = id;
			
			results = true;
		}
		catch(Exception e)
		{
			ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
			close();
		}
		return results;
	}
	public boolean connect(String driverName, String url, String id, String pw)
	{
		return connect(null, driverName, url, id, pw);
	}
	public void close()
	{
		try
		{
			conn.close();
		}
		catch(Exception e)
		{
			
		}
		conn = null;
	}
	public ResultSet query(Query query) throws SQLException, IOException
	{
		query.getPreparedStatement(conn).execute();
		return query.getPreparedStatement(conn).getResultSet();
	}
	public boolean isConnected()
	{
		return (conn == null);
	}
	public String getConnectionName()
	{
		return connectionName;
	}
}
