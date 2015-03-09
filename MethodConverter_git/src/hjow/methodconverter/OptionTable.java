package hjow.methodconverter;

import hjow.convert.module.RemoveCommentModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <p>Option table</p>
 * 
 * <p>옵션 테이블입니다.
 * 이 프로그램에 대한 각종 설정이 저장되는 객체를 생성하는 클래스입니다. </p>
 * 
 * @author HJOW
 *
 */
public class OptionTable extends Hashtable<String, String>
{
	private static final long serialVersionUID = 464184262297895766L;
	/**
	 * <p>Create empty table</p>
	 * 
	 * <p>빈 테이블을 만듭니다.</p>
	 */
	public OptionTable()
	{
		super();
	}
	/**
	 * <p>Create table and input data from text</p>
	 * 
	 * <p>텍스트로부터 테이블 데이터를 꺼내 넣어 테이블을 만듭니다. </p>
	 * 
	 * @param str : text 텍스트
	 */
	public OptionTable(String str)
	{
		this();
		input(str);		
	}
	/**
	 * <p>Create table and input data from file</p>
	 * 
	 * <p>파일로부터 테이블 데이터를 꺼내 넣어 테이블을 만듭니다. </p>
	 * 
	 * @param file : File object 파일 객체
	 */
	public OptionTable(File file)
	{
		this();
		input(file);
	}
	/**
	 * <p>Input data from file</p>
	 * 
	 * <p>파일로부터 테이블 데이터를 꺼내 넣습니다.</p>
	 * 
	 * @param file : File object 파일 객체
	 */
	public void input(File file)
	{
		FileReader reader = null;
		BufferedReader buffered = null;
		RemoveCommentModule temp = null;
		String accums = "", readLine;
		try
		{
			if(! file.exists()) return;
			Controller.firstTime = false;
			reader = new FileReader(file);
			buffered = new BufferedReader(reader);
			
			while(true)
			{
				readLine = buffered.readLine();
				if(readLine == null) break;
				readLine = readLine.trim();
				if(readLine.startsWith("#")) continue;
				accums = accums + readLine + "\n";
			}
			
			temp = new RemoveCommentModule();
			Map<String, String> parameters = new Hashtable<String, String>();
			parameters.put("option", "toParams");
			
			input(temp.convert(accums, parameters));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				buffered.close();
			}
			catch(Exception e1)
			{
				
			}
			try
			{
				reader.close();
			}
			catch(Exception e1)
			{
				
			}
			try
			{
				temp.close();
			}
			catch(Exception e1)
			{
				
			}
		}
	}
	/**
	 * <p>Input data from text</p>
	 * 
	 * <p>텍스트로부터 테이블 데이터를 꺼내 넣습니다.</p>
	 * 
	 * @param str : text 텍스트
	 */
	public void input(String str)
	{
		String reaccum = "", lines, keys, values;
		
//		StringTokenizer lineToken = new StringTokenizer(str, "\n");
//		while(lineToken.hasMoreTokens())
//		{
//			lines = lineToken.nextToken().trim();
//			if(lines.startsWith("#")) continue;
//			reaccum = reaccum + lines + "\n";
//		}
//		reaccum = reaccum.trim();
		
		RemoveCommentModule temp = new RemoveCommentModule();
		Map<String, String> parameters = new Hashtable<String, String>();
		parameters.put("option", "toParams");		
		
		reaccum = temp.convert(str, parameters);
		
		StringTokenizer semicolonToken = new StringTokenizer(reaccum, ";");
		StringTokenizer colonToken;
		while(semicolonToken.hasMoreTokens())
		{
			try
			{
				lines = new String(semicolonToken.nextToken());				
				colonToken = new StringTokenizer(lines, Controller.delimiter());
				keys = colonToken.nextToken().trim();
				if(! colonToken.hasMoreTokens()) continue;
				values = colonToken.nextToken().trim();
				while(colonToken.hasMoreTokens())
				{
					try
					{
						values = values + Controller.delimiter() +  colonToken.nextToken().trim();
					}
					catch(Exception e)
					{
						
					}
				}
				
				this.put(keys, values);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * <p>Return text which has all of data.</p>
	 * 
	 * <p>모든 데이터를 포함한 텍스트를 반환합니다.
	 * 이 텍스트를 생성자에 넣어 다시 객체화할 수 있습니다.</p>
	 */
	public String toString()
	{
		String results = "  ;\n";
		Set<String> keys = keySet();
		for(String s : keys)
		{
			results = results + s + Controller.delimiter() + get(s) + ";\n";
		}
		return results;
	}
	
}
