package hjow.msq.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class IOControl
{
	public static void saveText(File file, String contents, String additionalStreamOptions) throws FileNotFoundException
	{
		saveText(new FileOutputStream(file), contents, additionalStreamOptions);
	}
	public static void saveText(OutputStream stream, String contents, String additionalStreamOptions)
	{
		OutputStreamWriter reader = null;
		BufferedWriter buffered = null;
		List<OutputStream> additionalStreamChain = new Vector<OutputStream>();
		
		List<String> options = new Vector<String>();
		if(additionalStreamOptions != null)
		{
			String defaultStreamOption = "";
			if(getOption(MainControl.DEFAULT_IO_STREAM_PARAMETER) != null)
			{
				defaultStreamOption = getOption(MainControl.DEFAULT_IO_STREAM_PARAMETER);
			}
			
			StringTokenizer tokens = new StringTokenizer(new String(additionalStreamOptions.trim() + " " + defaultStreamOption).trim(), " ");
			while(tokens.hasMoreTokens())
			{
				options.add(tokens.nextToken());
			}
		}
		
		try
		{	
			additionalStreamChain.add(stream);
			if(options.contains("GZIP"))
			{
				additionalStreamChain.add(new java.util.zip.GZIPOutputStream(additionalStreamChain.get(additionalStreamChain.size() - 1)));
			}
			if(options.contains("Data"))
			{
				additionalStreamChain.add(new DataOutputStream(additionalStreamChain.get(additionalStreamChain.size() - 1)));
			}
			
			reader = new OutputStreamWriter(additionalStreamChain.get(additionalStreamChain.size() - 1));
			buffered = new BufferedWriter(reader);
			
			StringTokenizer lineTokenizer = new StringTokenizer(contents, "\n");
			while(lineTokenizer.hasMoreTokens())
			{
				buffered.write(lineTokenizer.nextToken());
			}
		}
		catch(Exception e)
		{
			ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
		}
		finally
		{
			try
			{
				buffered.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				reader.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				for(int i = additionalStreamChain.size() - 1 ; i >= 0 ; i--)
				{
					try
					{
						additionalStreamChain.get(i).close();
					}
					catch(Exception e1)
					{
						
					}
				}
			}
			catch(Exception e)
			{
				
			}
		}
	}
	public static String readText(File file, String additionalStreamOptions) throws FileNotFoundException
	{
		return readText(new FileInputStream(file), additionalStreamOptions);
	}
	public static String readText(java.net.URL url, String additionalStreamOptions) throws IOException
	{
		return readText(url.openStream(), additionalStreamOptions);
	}
	public static String readText(InputStream stream, String additionalStreamOptions)
	{
		InputStreamReader reader = null;
		BufferedReader buffered = null;
		List<InputStream> additionalStreamChain = new Vector<InputStream>();
		StringBuffer reads = new StringBuffer("");
		String readLines = null;	
		
		List<String> options = new Vector<String>();
		if(additionalStreamOptions != null)
		{
			String defaultStreamOption = "";
			if(getOption(MainControl.DEFAULT_IO_STREAM_PARAMETER) != null)
			{
				defaultStreamOption = getOption(MainControl.DEFAULT_IO_STREAM_PARAMETER);
			}
			
			StringTokenizer tokens = new StringTokenizer(new String(additionalStreamOptions.trim() + " " + defaultStreamOption).trim(), " ");
			while(tokens.hasMoreTokens())
			{
				options.add(tokens.nextToken());
			}
		}
		
		try
		{	
			additionalStreamChain.add(stream);
			if(options.contains("GZIP"))
			{
				additionalStreamChain.add(new java.util.zip.GZIPInputStream(additionalStreamChain.get(additionalStreamChain.size() - 1)));
			}
			if(options.contains("Data"))
			{
				additionalStreamChain.add(new DataInputStream(additionalStreamChain.get(additionalStreamChain.size() - 1)));
			}
			
			reader = new InputStreamReader(additionalStreamChain.get(additionalStreamChain.size() - 1));
			buffered = new BufferedReader(reader);
			
			while(true)
			{
				readLines = buffered.readLine();
				if(readLines == null) break;
				reads = reads.append(readLines);
			}
			
			return reads.toString();
		}
		catch(Exception e)
		{
			ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
			return null;
		}
		finally
		{
			try
			{
				buffered.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				reader.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				for(int i = additionalStreamChain.size() - 1 ; i >= 0 ; i--)
				{
					try
					{
						additionalStreamChain.get(i).close();
					}
					catch(Exception e1)
					{
						
					}
				}
			}
			catch(Exception e)
			{
				
			}
		}
	}
	public static String getOption(String k)
	{
		return MainControl.getOptionValue(k);
	}
}
