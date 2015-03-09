package hjow.convert.module;

import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * <p>This module can compress or decompress data.</p>
 * 
 * <p>이 모듈은 데이터를 압축/해제하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class CompressModule implements BothModule
{
	private static final long serialVersionUID = -6260190954081013315L;
	
	public static final int GZIP = 0;
	public static final int ZIP = 1;

	@Override
	public String getName()
	{
		return getName("en");
	}
	@Override
	public String getName(String locale)
	{
		if(Statics.isKoreanLocale())
		{
			return "압축";
		}
		else
		{
			return "Compress";
		}
	}

	@Override
	public String convert(StringTable stringTable,
			Map<String, String> syntax, String before,
			StatusViewer statusViewer, StatusBar statusField, long threadTerm,
			Map<String, String> parameters)
	{
		try
		{
			return new String(convert(before.getBytes(), parameters, statusField, statusViewer), "UTF-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String convert(String before, Map<String, String> parameters)
	{
		return convert(Controller.getStringTable(), null, before, null, null, 20, parameters);
	}

	@Override
	public List<String> optionList()
	{
		List<String> results = new Vector<String>();
		results.add(Controller.getString("compress"));
		results.add(Controller.getString("decompress"));
		return results;
	}

	@Override
	public List<String> parameterKeyList()
	{
		Vector<String> keys = new Vector<String>();
		keys.add("mode");	
		return keys;
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
		String locale = Controller.getSystemLocale();
		String results = "";
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			results = results + "사용 가능한 키 : mode" + "\n\n";
			results = results + "mode : 압축 방식 (gzip)" + "\n\n";
		}
		else
		{
			results = results + "Available keys : mode" + "\n\n";
			results = results + "mode : How to compress/decompress. (gzip)" + "\n\n";
		}
		return results;
	}

	@Override
	public void close()
	{
		
	}
	public byte[] convert(byte[] befores, Map<String, String> parameters)
	{
		return convert(befores, parameters, null, null);
	}
	@Override
	public byte[] convert(byte[] befores, Map<String, String> parameters, StatusBar bar, StatusViewer viewer)
	{
		ByteArrayInputStream newByteStream = new ByteArrayInputStream(befores);
		ByteArrayOutputStream outputs = new ByteArrayOutputStream();
		OutputStream zipper = null;
		InputStream  opener = null;
		
		try 
		{
			boolean zip = true;
			int mode = GZIP;
			if(parameters.get("option") != null)
			{
				if(parameters.get("option").equalsIgnoreCase(Controller.getString("compress")))
				{
					zip = true;
				}
				else if(parameters.get("option").equalsIgnoreCase(Controller.getString("decompress")))
				{
					zip = false;
				}
				else zip = true;
			}
			if(parameters.get("zip") != null)
			{
				if(Statics.parseBoolean(parameters.get("zip")))
				{
					zip = false;
				}
				else
				{
					zip = true;
				}
			}
			else
			{
				zip = true;
			}
			if(parameters.get("mode") != null)
			{
				if(parameters.get("mode").equalsIgnoreCase("gzip") || parameters.get("mode").equalsIgnoreCase("gz"))
				{
					mode = GZIP;
				}
				else if(parameters.get("mode").equalsIgnoreCase("zip") || parameters.get("mode").equalsIgnoreCase("z"))
				{
					mode = ZIP;
				}
			}
			switch(mode)
			{
			case GZIP:
				if(zip)
				{
					zipper = new GZIPOutputStream(outputs);	
					
				}
				else
				{
					opener = new GZIPInputStream(newByteStream);
				}
				break;
			case ZIP:
				if(zip)
				{
					ZipOutputStream inst = new ZipOutputStream(outputs);
					zipper = inst;			
					inst.putNextEntry(new ZipEntry(parameters.get("load_path")));
				}
				else
				{
					ZipInputStream inst = new ZipInputStream(newByteStream);
					opener = inst;
					inst.getNextEntry();
				}
				break;
			}	
			int readData = -1;
			int printCounts = 0;
			if(opener == null) opener = newByteStream;
			if(zipper == null) zipper = outputs;
			while(true)
			{
				readData = opener.read();
				if(readData == -1) break;
				zipper.write(readData);
				
				printCounts++;
				if(printCounts >= 100)
				{
					printCounts = 0;
					if(viewer != null) viewer.nextStatus();				
					Controller.print("Working", true);
					Controller.println(" : " + readData);
				}				
			}
			return outputs.toByteArray();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				zipper.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				opener.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				newByteStream.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				outputs.close();
			}
			catch(Exception e)
			{
				
			}
		}		
		
		return null;
	}

	@Override
	public boolean isEncryptingModule()
	{
		return false;
	}
	@Override
	public boolean isOptionEditable()
	{
		return false;
	}
	@Override
	public String getDefinitionName()
	{
		return "Compress";
	}
	@Override
	public String getHelps()
	{
		String results = "";
		results = results + getName() + "\n\n";
		
		return results + "\n\n" + getParameterHelp();
	}
}
