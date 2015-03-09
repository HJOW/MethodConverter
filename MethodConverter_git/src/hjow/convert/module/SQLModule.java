package hjow.convert.module;

import hjow.methodconverter.ClassManager;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <p>This module can access RDBMS.</p>
 * 
 * <p>이 모듈은 관계형 데이터베이스에 접근할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class SQLModule implements ConvertModule
{
	private static final long serialVersionUID = -154221485766784242L;
	private String driverName = "";
	private String jdbcUrl = "";
	private String jdbcId = "";
	private String jdbcPw = "";
	private Connection conn = null;
	private PreparedStatement statements = null;
	private ResultSet resultSet = null;
	private boolean initialized = false;
	private InsertTokenModule tokenModule = new InsertTokenModule();
	
	/**
	 * <p>Kinds of sql query.</p>
	 */
	public static final int SELECT = 0
							, INSERT = 1
							, UPDATE = 2
							, DELETE = 3
							, EXECUTE = 4
							, OTHERS = 5;
	
	/**
	 * <p>Create module object without initialization.</p>
	 * 
	 * <p>모듈 객체를 생성합니다. 초기화되지 않습니다.</p>
	 */
	public SQLModule()
	{
		super();	
	}
	/**
	 * <p>Initialize JDBC connection.</p>
	 * 
	 * <p>JDBC에 접속하고 객체를 초기화합니다.</p>
	 * 
	 * @param driverName : JDBC Driver classPath
	 * @param jdbcUrl : JDBC URL to access
	 * @param jdbcId : Database ID
	 * @param jdbcPw : Database Password
	 * @throws Exception : All exceptions of accessing JDBC
	 */
	public void initializeJdbc(String driverName, String jdbcUrl, String jdbcId, String jdbcPw) throws Exception
	{
		initializeJdbc(null, driverName, jdbcUrl, jdbcId, jdbcPw);
	}
	/**
	 * <p>Initialize JDBC connection.</p>
	 * 
	 * <p>JDBC에 접속하고 객체를 초기화합니다.</p>
	 * 
	 * @param file : JAR file, or class file
	 * @param driverName : JDBC Driver classPath
	 * @param jdbcUrl : JDBC URL to access
	 * @param jdbcId : Database ID
	 * @param jdbcPw : Database Password
	 * @throws Exception : All exceptions of accessing JDBC
	 */
	public void initializeJdbc(File file, String driverName, String jdbcUrl, String jdbcId, String jdbcPw) throws Exception
	{
		boolean needInitialize = false;
		
		if(initialized)
		{
			if((! needInitialize) && (! this.driverName.equals(driverName))) needInitialize = true;
			if((! needInitialize) && (! this.jdbcUrl.equals(jdbcUrl))) needInitialize = true;
			if((! needInitialize) && (! this.jdbcId.equals(jdbcId))) needInitialize = true;
			if((! needInitialize) && (! this.jdbcPw.equals(jdbcPw))) needInitialize = true;
		}
		else
		{
			needInitialize = true;
		}
		
		if(needInitialize)
		{
			close();
			try
			{
				if(file == null) ClassManager.load(driverName);
				else ClassManager.load(file, driverName);
				conn = DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPw);
				initialized = true;
			}
			catch (Exception e)
			{
				close();
				throw e;
			}	
		}
	}

	@Override
	public String getName()
	{		
		return getName(Controller.getSystemLocale());
	}

	@Override
	public String getName(String locale)
	{
		if(Statics.isKoreanLocale())
		{
			return "SQL 실행";
		}
		else
		{
			return "Execute SQL";
		}
	}
	
	@Override
	public String convert(StringTable stringTable, Map<String, String> syntax,
			String before, StatusViewer statusViewer, StatusBar statusField,
			long threadTerm, Map<String, String> parameters)
	{
		return execute(before, statusViewer, statusField, threadTerm, parameters, false);
	}
	
	public String execute(String queries)
	{
		return execute(queries, driverName, jdbcId, jdbcPw, jdbcUrl, true, true);
	}
	public String execute(String queries, String driver, String id, String password, String url)
	{
		return execute(queries, driver, id, password, url, true, false);
	}
	public String execute(String queries, String driver, String id, String password, String url, boolean ignoreInitialize)
	{
		return execute(queries, driver, id, password, url, true, ignoreInitialize);
	}
	public String execute(String queries, StatusViewer statusViewer, StatusBar statusField)
	{
		return execute(queries, driverName, jdbcId, jdbcPw, jdbcUrl, true, true);
	}
	public String execute(String queries, String driver, String id, String password, String url, StatusViewer statusViewer, StatusBar statusField)
	{
		return execute(queries, driver, id, password, url, true, false);
	}
	public String execute(String queries, String driver, String id, String password, String url, boolean ignoreInitialize, StatusViewer statusViewer, StatusBar statusField)
	{
		return execute(queries, driver, id, password, url, true, ignoreInitialize);
	}
	/**
	 * <p>Run SQL statement. If this object is not initialized, try it.</p>
	 * 
	 * <p>SQL문을 실행합니다. 이 객체가 초기화되지 않았다면 초기화를 시도합니다.</p>
	 * 
	 * 
	 * @param queries : SQL statement
	 * @param driver : JDBC driver classPath
	 * @param id : Database ID
	 * @param password : Database password
	 * @param url : JDBC URL to access
	 * @param onlyResult : If this is true, only results are returned
	 * @param ignoreInitialize : If this is true, this method does not try to initialize this object
	 * @return results
	 */
	public String execute(String queries, String driver, String id, String password, String url, boolean onlyResult
            , boolean ignoreInitialize)
    {
		return execute(queries, driver, id, password, url, onlyResult, null, null, ignoreInitialize);
    }
	/**
	 * <p>Run SQL statement. If this object is not initialized, try it.</p>
	 * 
	 * <p>SQL문을 실행합니다. 이 객체가 초기화되지 않았다면 초기화를 시도합니다.</p>
	 * 
	 * 
	 * @param queries : SQL statement
	 * @param driver : JDBC driver classPath
	 * @param id : Database ID
	 * @param password : Database password
	 * @param url : JDBC URL to access
	 * @param onlyResult : If this is true, only results are returned
	 * @param statusViewer : Object which can show the process is alive, can be null
	 * @param statusField : Object which can show text on the GUI screen, can be null
	 * @param ignoreInitialize : If this is true, this method does not try to initialize this object
	 * @return results
	 */
	public String execute(String queries, String driver, String id, String password, String url, boolean onlyResult
			              , StatusViewer statusViewer, StatusBar statusField, boolean ignoreInitialize)
	{
		Map<String, String> parameters = new Hashtable<String, String>();
		
		parameters.put("DRIVER", driver);
		parameters.put("URL", url);
		parameters.put("ID", id);
		parameters.put("PASSWORD", password);
		parameters.put("only_results", String.valueOf(onlyResult));
		
		return execute(queries, null, null, 100, parameters, ignoreInitialize);
	}
	/**
	 * <p>Run SQL statement. If this object is not initialized, try it.</p>
	 * 
	 * <p>SQL문을 실행합니다. 이 객체가 초기화되지 않았다면 초기화를 시도합니다.</p>
	 * 
	 * @param queryStatement : SQL statement
	 * @param parameters : Parameters
	 * @param ignoreInitialize : If this is true, this method does not try to initialize this object
	 * @return results
	 */
	public String execute(String queryStatement, Map<String, String> parameters, boolean ignoreInitialize)
	{
		return execute(queryStatement, null, null, 100, parameters, ignoreInitialize);
	}

	/**
	 * <p>Run SQL statement. If this object is not initialized, try it.</p>
	 * 
	 * <p>SQL문을 실행합니다. 이 객체가 초기화되지 않았다면 초기화를 시도합니다.</p>
	 * 
	 * @param queryStatement : SQL statement
	 * @param statusViewer : Object which can show the process is alive, can be null
	 * @param statusField : Object which can show text on the GUI screen, can be null
	 * @param viewerTerm : If this is big, performance can be better but it can make GUI shows slow
	 * @param parameters : Parameters
	 * @param ignoreInitialize : If this is true, this method does not try to initialize this object
	 * @return results
	 */
	public String execute(String queryStatement, StatusViewer statusViewer, StatusBar statusField,
			long viewerTerm, Map<String, String> parameters, boolean ignoreInitialize)
	{
		try
		{
			if(! ignoreInitialize)
			{
				setFields(parameters);
				
				String filePath = parameters.get("driverFile");
				File fileObj = null;
				if(filePath != null)
				{
					try
					{
						fileObj = new File(filePath);
						if(! (fileObj.exists())) fileObj = null;
					}
					catch(Exception e)
					{
						e.printStackTrace();
						Controller.print("Error", true);
						Controller.println(" : " + e.getMessage());
						fileObj = null;
					}
				}
				
				initializeJdbc(fileObj, driverName, jdbcUrl, jdbcId, jdbcPw);
			}			
			closeBefores();
			
			boolean resultReplaceBefore = false;
			if(parameters.get("only_results") != null && (! parameters.get("only_results").equals("")))
			{
				resultReplaceBefore = Statics.parseBoolean(parameters.get("only_results"));
			}
			
			int mode = OTHERS;
						
			String queries = queryStatement.trim();
			StringBuffer removeComments = new StringBuffer("");
			char[] queriesArray = queries.toCharArray();
			char beforeChar = ' ';
			boolean commentMode = false;
			for(int i=0; i<queriesArray.length; i++)
			{
				if(commentMode)
				{
					if(beforeChar == '*' && queriesArray[i] == '/')
					{
						commentMode = false;
					}
				}
				else
				{
					if(beforeChar == '/' && queriesArray[i] == '*')
					{
						commentMode = true;
					}
					else
					{
						removeComments = removeComments.append(String.valueOf(queriesArray[i]));
					}
				}
				beforeChar = queriesArray[i];
				if(i % viewerTerm == 0)
				{
					if(statusViewer != null) statusViewer.nextStatus();
				}
			}
			queries = removeComments.toString().trim();
			
			if(queries.startsWith("SELECT") || queries.startsWith("select"))
			{
				mode = SELECT;
			}
			else if(queries.startsWith("INSERT") || queries.startsWith("insert"))
			{
				mode = INSERT;
			}
			else if(queries.startsWith("UPDATE") || queries.startsWith("update"))
			{
				mode = UPDATE;
			}
			else if(queries.startsWith("DELETE") || queries.startsWith("delete"))
			{
				mode = DELETE;
			}
			else if(queries.startsWith("COMMIT") || queries.startsWith("commit")
					|| queries.startsWith("ROLLBACK") || queries.startsWith("rollback"))
			{
				mode = EXECUTE;
			}
			
			String stringParams = parameters.get("varchar");
			if(stringParams != null && (! stringParams.equals("")))
			{
				Map<String, String> tokenModuleParams = new Hashtable<String, String>();
				tokenModuleParams.put("option", Controller.getString("Make SQL query from JDBC log"));
				tokenModuleParams.put("tokens", stringParams);
				
				queries = tokenModule.convert(queryStatement, tokenModuleParams);
			}
			
			statements = conn.prepareStatement(queries);
			
			String directParamText = parameters.get("parameters");
			List<SQLParameter> directParams = toSQLParameters(directParamText);
			
			SQLParameter s = null;
			for(int i=0; i<directParams.size(); i++)
			{
				s = directParams.get(i);
				switch(s.getDataType())
				{
				case Types.BIGINT:
					statements.setLong(i+1, Long.parseLong(s.getValue()));
					break;
				case Types.INTEGER:
					statements.setInt(i+1, Integer.parseInt(s.getValue()));
					break;
				case Types.NUMERIC:
					statements.setInt(i+1, Integer.parseInt(s.getValue()));
					break;
				case Types.FLOAT:
					statements.setFloat(i+1, Float.parseFloat(s.getValue()));
					break;
				case Types.DOUBLE:
					statements.setDouble(i+1, Double.parseDouble(s.getValue()));
					break;
				case Types.DATE:
					statements.setDate(i+1, toDate(s.getValue()));
					break;
				case Types.TIME:
					statements.setTime(i+1, toTime(s.getValue()));
					break;
				case Types.TIMESTAMP:
					statements.setTimestamp(i+1, toTimestamp(s.getValue()));
					break;
				case Types.VARCHAR:
					statements.setString(i + 1, s.getValue());
					break;
				case Types.CHAR:
					statements.setString(i + 1, s.getValue());
					break;
				case Types.CLOB:
					statements.setString(i + 1, s.getValue());
					break;
				default:
					statements.setString(i + 1, s.getValue());
					break;
				}
				
				if(statusViewer != null) statusViewer.nextStatus();
			}		
			
			int resultExecute = PreparedStatement.EXECUTE_FAILED;
			resultSet = null;
			switch(mode)
			{
			case OTHERS:
				statements.execute();
				resultSet = statements.getResultSet();
				break;
			case SELECT:
				resultSet = statements.executeQuery();
				resultExecute = PreparedStatement.SUCCESS_NO_INFO;
				break;
			case INSERT:
				resultExecute = statements.executeUpdate();
				break;
			case UPDATE:
				resultExecute = statements.executeUpdate();
				break;
			case DELETE:
				resultExecute = statements.executeUpdate();
				break;
			case EXECUTE:
				statements.execute();
				resultSet = statements.getResultSet();
				break;
			default:
				statements.execute();
				resultSet = statements.getResultSet();
				break;
			}
			
			if(statusViewer != null) statusViewer.nextStatus();
			
			StringBuffer resultSetAsText = new StringBuffer("");
			if(resultSet != null)
			{					
				resultSetAsText = resultSetAsText.append(Controller.getString("SQL Execution") + " : " + resultExecute + "\n\n");
				
				ResultSetMetaData metaData = resultSet.getMetaData();
				List<SQLParameter> columns = new Vector<SQLParameter>();
				for(int i=0; i<metaData.getColumnCount(); i++)
				{
					columns.add(new SQLParameter(metaData.getColumnName(i + 1), metaData.getColumnType(i + 1)));
				}
				
				resultSetAsText = resultSetAsText.append(Controller.getString("Results") + "\n");
				
				// Print Column Titles
				resultSetAsText = resultSetAsText.append(Controller.getString("No\t"));
				for(int i=0; i<columns.size(); i++)
				{
					resultSetAsText = resultSetAsText.append(columns.get(i).getValue());
					if(i < columns.size() - 1) resultSetAsText = resultSetAsText.append("\t");
				}
				
				// Print Rows
				resultSet.beforeFirst();
				int nowRow = 0;
				while(resultSet.next())
				{
					resultSetAsText = resultSetAsText.append((nowRow + 1));
					for(int i=0; i<columns.size(); i++)
					{
						switch(columns.get(i + 1).getDataType())
						{
						case Types.BIGINT:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getLong(i + 1)));
							break;
						case Types.INTEGER:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getInt(i + 1)));
							break;
						case Types.NUMERIC:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getInt(i + 1)));
							break;
						case Types.FLOAT:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getFloat(i + 1)));
							break;
						case Types.DOUBLE:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getDouble(i + 1)));
							break;
						case Types.DATE:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getDate(i + 1)));
							break;
						case Types.TIME:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getTime(i + 1)));
							break;
						case Types.TIMESTAMP:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getTimestamp(i + 1)));
							break;
						case Types.VARCHAR:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getString(i + 1)));
							break;
						case Types.CHAR:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getString(i + 1)));
							break;
						case Types.CLOB:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getString(i + 1)));
							break;
						default:
							resultSetAsText = resultSetAsText.append(String.valueOf(resultSet.getString(i + 1)));
							break;
						}						
						if(i < columns.size() - 1) resultSetAsText = resultSetAsText.append("\t");
					}
					nowRow++;
					resultSetAsText = resultSetAsText.append("\n");
					if(statusViewer != null) statusViewer.nextStatus();
				}			
			}
			
			if(resultReplaceBefore)
			{
				return Statics.applyScript(this, parameters, resultSetAsText.toString());
			}
			else
			{
				String returnText = queryStatement;				
				returnText = returnText + "\n\n/*\n" + resultSetAsText.toString() + "\n*/";
				
				return Statics.applyScript(this, parameters, returnText);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();			
			Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
			if(statusField != null) statusField.setText(Controller.getString("Error") + " : " + e.getMessage());
		}
		return Statics.applyScript(this, parameters, queryStatement);
	}
	
	/**
	 * <p>Convert text into Timestamp object. (Format : dd/MM/yyyy/hh:mm:ss)</p>
	 * 
	 * <p>텍스트를 Timestamp 객체로 변환합니다. (형식 : dd/MM/yyyy/hh:mm:ss)</p>
	 * 
	 * @param befores : dd/MM/yyyy/hh:mm:ss formed text
	 * @return Timestamp object
	 */
	@SuppressWarnings("deprecation")
	public static Timestamp toTimestamp(String befores)
	{
		Timestamp timestamp = new Timestamp(0);
		
		String dateInString = new java.text.SimpleDateFormat("dd/MM/yyyy/hh:mm:ss").format(befores);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss");
        try
		{
			java.util.Date formatResult = formatter.parse(dateInString);
						
			timestamp.setYear(formatResult.getYear());
			timestamp.setDate(formatResult.getDate());
			timestamp.setMonth(formatResult.getMonth());
			timestamp.setHours(formatResult.getHours());
			timestamp.setMinutes(formatResult.getMinutes());
			timestamp.setSeconds(formatResult.getSeconds());
			timestamp.setNanos(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Controller.println(Controller.getString("Error") + " : " + e.getMessage());
			return null;
		}
		
		return timestamp;
	}
	/**
	 * <p>Convert text into Time object. (Format : hh:mm:ss)</p>
	 * 
	 * <p>텍스트를 Time 객체로 변환합니다. (형식 : hh:mm:ss)</p>
	 * 
	 * @param befores : hh:mm:ss formed text
	 * @return Time object
	 */
	@SuppressWarnings("deprecation")
	public static Time toTime(String befores)
	{
		Time time = new Time(0);
		
		String dateInString = new java.text.SimpleDateFormat("hh:mm:ss").format(befores);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        try
		{
			java.util.Date formatResult = formatter.parse(dateInString);
						
			time.setHours(formatResult.getHours());
			time.setMinutes(formatResult.getMinutes());
			time.setSeconds(formatResult.getSeconds());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Controller.println(Controller.getString("Error") + " : " + e.getMessage());
			return null;
		}
		
		return time;
	}
	/**
	 * <p>Convert text into Date object. (Format : dd/MM/yyyy)</p>
	 * 
	 * <p>텍스트를 Date 객체로 변환합니다. (형식 : dd/MM/yyyy)</p>
	 * 
	 * @param befores : dd/MM/yyyy formed text
	 * @return Date object
	 */
	@SuppressWarnings("deprecation")
	public static Date toDate(String befores)
	{
		Date date = new Date(0);
		
		String dateInString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(befores);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try
		{
			java.util.Date formatResult = formatter.parse(dateInString);
						
			date.setYear(formatResult.getYear());
			date.setMonth(formatResult.getMonth());
			date.setDate(formatResult.getDate());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Controller.println(Controller.getString("Error") + " : " + e.getMessage());
			return null;
		}
		
		return date;
	}
	/**
	 * <p>Convert text into SQLParameter objects.</p>
	 * 
	 * <p>텍스트를 SQLParameter 객체들로 변환합니다.</p>
	 * 
	 * @param befores : Text
	 * @return SQLParameter list
	 */
	public static List<SQLParameter> toSQLParameters(String befores)
	{
		boolean quotes = false;
		boolean typeOfValue = false;
		char[] charArr = befores.toCharArray();
		
		List<SQLParameter> parameters = new Vector<SQLParameter>();
		
		StringBuffer types = new StringBuffer("");
		StringBuffer values = new StringBuffer("");
		for(int i=0; i<charArr.length; i++)
		{
			if(quotes)
			{
				if(charArr[i] == '\'')
				{
					quotes = false;
				}
				else
				{
					if(typeOfValue)
					{
						values = values.append(String.valueOf(charArr[i]));
					}
					else
					{
						types = types.append(String.valueOf(charArr[i]));
					}
				}
			}
			else
			{
				if(charArr[i] == '\'')
				{
					quotes = true;
				}
				else if(charArr[i] == ' ')
				{
					
				}
				else if(charArr[i] == ',')
				{
					parameters.add(sqlParameter(types.toString(), values.toString()));
					types = new StringBuffer("");
					values = new StringBuffer("");
					typeOfValue = false;
					quotes = false;
				}
				else if(charArr[i] == ':')
				{
					typeOfValue = true;
				}
				else
				{
					if(typeOfValue)
					{
						values = values.append(String.valueOf(charArr[i]));
					}
					else
					{
						types = types.append(String.valueOf(charArr[i]));
					}
				}
			}
			if(i == charArr.length - 1)
			{
				if(! (values == null || values.toString().equals("")))
				{
					parameters.add(sqlParameter(types.toString(), values.toString()));
					types = new StringBuffer("");
					values = new StringBuffer("");
					typeOfValue = false;
					quotes = false;
				}
			}
		}
		
		return parameters;
	}
	/**
	 * <p>Create new SQLParameter object.</p>
	 * 
	 * <p>SQLParameter 객체를 만듭니다.</p>
	 * 
	 * @param value : Value as String
	 * @param dataType : Data type (See java.sql.Types)
	 * @return SQLParameter object
	 */
	public static SQLParameter sqlParameter(String value, int dataType)
	{
		return new SQLParameter(value, dataType);
	}
	
	/**
	 * <p>Create new SQLParameter object.</p>
	 * 
	 * <p>SQLParameter 객체를 만듭니다.</p>
	 * 
	 * @param dataType : Data type as String
	 * @param value : Value as String
	 * @return SQLParameter object
	 */
	public static SQLParameter sqlParameter(String dataType, String value)
	{
		int dataTypeInt = Types.CHAR;
		
		if(value.equalsIgnoreCase("CHAR") || value.equalsIgnoreCase("C"))
		{
			dataTypeInt = Types.CHAR;
		}
		else if(value.equalsIgnoreCase("VARCHAR") || value.equalsIgnoreCase("VARCHAR2") || value.equalsIgnoreCase("V"))
		{
			dataTypeInt = Types.VARCHAR;
		}
		else if(value.equalsIgnoreCase("CLOB") || value.equalsIgnoreCase("CL"))
		{
			dataTypeInt = Types.CLOB;
		}
		else if(value.equalsIgnoreCase("NUMBER") || value.equalsIgnoreCase("N"))
		{
			dataTypeInt = Types.NUMERIC;
		}
		else if(value.equalsIgnoreCase("INTEGER") || value.equalsIgnoreCase("INT") || value.equalsIgnoreCase("I"))
		{
			dataTypeInt = Types.INTEGER;
		}
		else if(value.equalsIgnoreCase("BIGINT") || value.equalsIgnoreCase("LONG") || value.equalsIgnoreCase("L"))
		{
			dataTypeInt = Types.BIGINT;
		}
		else if(value.equalsIgnoreCase("FLOAT") || value.equalsIgnoreCase("F"))
		{
			dataTypeInt = Types.FLOAT;
		}
		else if(value.equalsIgnoreCase("DOUBLE") || value.equalsIgnoreCase("D"))
		{
			dataTypeInt = Types.DOUBLE;
		}
		else if(value.equalsIgnoreCase("DATE") || value.equalsIgnoreCase("DT"))
		{
			dataTypeInt = Types.DATE;
		}
		else if(value.equalsIgnoreCase("TIME") || value.equalsIgnoreCase("T"))
		{
			dataTypeInt = Types.TIME;
		}
		else if(value.equalsIgnoreCase("TIMESTAMP") || value.equalsIgnoreCase("TS"))
		{
			dataTypeInt = Types.TIMESTAMP;
		}
		else if(value.equalsIgnoreCase("DATETIME") || value.equalsIgnoreCase("DTM"))
		{
			dataTypeInt = Types.TIMESTAMP;
		}
		
		return new SQLParameter(value, dataTypeInt);
	}

	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(Controller.getStringTable(), null, before, null, null, 20, parameters);
	}

	@Override
	public List<String> optionList()
	{
		List<String> options = new Vector<String>();
		options.add("org.apache.derby.jdbc.ClientDriver");
		options.add("org.h2.Driver");
		options.add("com.mysql.jdbc.Driver");
		options.add("oracle.jdbc.driver.OracleDriver");
		options.add("cubrid.jdbc.driver.CUBRIDDriver");
		
		return options;
	}

	@Override
	public boolean isOptionEditable()
	{
		return true;
	}
	
	private void setFields(Map<String, String> parameters)
	{
		driverName = parameters.get("option");
		if(driverName == null) driverName = parameters.get("driver");
		if(driverName == null) driverName = parameters.get("DRIVER");
		jdbcUrl = parameters.get("url");
		if(jdbcUrl == null) jdbcUrl = parameters.get("URL");
		jdbcId = parameters.get("id");
		if(jdbcId == null) jdbcId = parameters.get("ID");
		jdbcPw = parameters.get("password");
		if(jdbcPw == null) jdbcPw = parameters.get("PASSWORD");
		if(jdbcPw == null) jdbcPw = parameters.get("pw");
		if(jdbcPw == null) jdbcPw = parameters.get("PW");
	}

	@Override
	public List<String> parameterKeyList()
	{
		List<String> results = new Vector<String>();
		results.add("URL");
		results.add("ID");
		results.add("PASSWORD");
		results.add("DRIVER");
		results.add("only_results");
		results.add("driverFile");
		return results;
	}

	@Override
	public String defaultParameterText()
	{
		return "";
	}

	@Override
	public boolean isAuthorized()
	{
		return true;
	}

	@Override
	public boolean isAuthCode(String input_auths)
	{
		return true;
	}

	@Override
	public String getParameterHelp()
	{
		String results = "";
		if(Statics.isKoreanLocale())
		{
			results = results + "사용 가능한 키 : URL, ID, PASSWORD, DRIVER, only_results, driverFile" + "\n\n";
			results = results + "URL : 데이터베이스 접속 URL" + "\n\n";
			results = results + "ID : 데이터베이스 ID" + "\n\n";
			results = results + "PASSWORD : 데이터베이스 비밀번호" + "\n\n";
			results = results + "DRIVER : JDBC 드라이버 클래스패스" + "\n\n";
			results = results + "driverFile : JDBC 드라이버를 JAR 파일 혹은 class 파일에서 직접 불러와야 할 경우 해당 파일 경로, 파일을 불러올 필요가 없으면 의무사항 아님" + "\n\n";
			results = results + "only_results : true 입력 시 결과만 화면에 나타납니다. false 이면 쿼리문 하단에 주석으로 결과가 붙어 나옵니다." + "\n\n";
		}
		else
		{
			results = results + "Available keys : URL, ID, PASSWORD, DRIVER, only_results, driverFile" + "\n\n";
			results = results + "URL : JDBC url to access" + "\n\n";
			results = results + "ID : Database ID" + "\n\n";
			results = results + "PASSWORD : Database password" + "\n\n";
			results = results + "DRIVER : Driver class name with classPath" + "\n\n";
			results = results + "driverFile : JDBC driver file path, can be JAR or class file. Only necessary when JDBC driver API is not loaded on the Java classpath." + "\n\n";
			results = results + "only_results : If this is true, only results will be shown" + "\n\n";
		}
		return results;
	}

	private void closeBefores()
	{
		try
		{
			resultSet.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			statements.close();
		}
		catch(Exception e)
		{
			
		}
	}
	@Override
	public void close()
	{
		closeBefores();
		
		try
		{
			statements = conn.prepareStatement("ROLLBACK");
			statements.execute();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			conn.close();
		}
		catch(Exception e)
		{
			
		}
		initialized = false;
		
		tokenModule.close();
	}
	@Override
	public String getDefinitionName()
	{
		return "SQL";
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
}
/**
 * <p>This class object can includes value and its data type.</p>
 * 
 * <p>이 클래스 객체는 값과 그의 데이터 타입(SQL)을 포함할 수 있습니다.</p>
 * 
 * @author HJOW
 *
 */
class SQLParameter implements Serializable
{
	private static final long serialVersionUID = 8920783270652468196L;
	private String value;
	private int dataType;

	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 * 
	 * @param value : Value
	 * @param dataType : Data type (See java.sql.Types)
	 */
	public SQLParameter(String value, int dataType)
	{
		super();
		this.value = value;
		this.dataType = dataType;
	}

	/**
	 * <p>Return value as String.</p>
	 * 
	 * <p>값을 문자열로 반환합니다.</p>
	 * 
	 * @return value as String
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * <p>Set value.</p>
	 * 
	 * <p>값을 문자열로 입력합니다.</p>
	 * 
	 * @param value : Value as String
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * <p>Return data type (See java.sql.Types)</p>
	 * 
	 * <p>데이터 타입을 반환합니다. (java.sql.Types 참고)</p>
	 * 
	 * @return
	 */
	public int getDataType()
	{
		return dataType;
	}

	/**
	 * <p>Set data type. (See java.sql.Types)</p>
	 * 
	 * <p>데이터 타입을 입력합니다. (java.sql.Types 참고)</p>
	 * 
	 * @param dataType
	 */
	public void setDataType(int dataType)
	{
		this.dataType = dataType;
	}
}