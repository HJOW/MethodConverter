package hjow.convert.download;

import hjow.methodconverter.ui.StatusViewer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;

/**
 * <p>This class object is used to download files.</p>
 * 
 * <p>이 클래스 객체는 파일을 다운로드하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class Downloader
{
	/**
	 * <p>Create downloader object.</p>
	 * 
	 * <p>다운로더 객체를 생성합니다.</p>
	 */
	public Downloader()
	{
		
	}
	
	/**
	 * <p>Create batch file.</p>
	 * 
	 * <p>배치 파일을 만듭니다.</p>
	 * 
	 * @param target : Where to save
	 * @param jarFileName : Target jar file name
	 * @param afters : Arguments, can be null
	 * @param osType : OS type. (0 : Windows)
	 */
	public static void makeBatch(File target, String jarFileName, String afters, int osType)
	{
		String saves = "";
		if(target == null) return;
		FileWriter writer = null;
		BufferedWriter buffered = null;
		
		switch(osType)
		{
		case 0: // Windows
			saves = "java -jar " + jarFileName;
			if(afters != null) saves = saves + " " + afters;
			try
			{
				writer = new FileWriter(target);
				buffered = new BufferedWriter(writer);
				
				buffered.write("  ");
				buffered.newLine();
				buffered.write(saves);
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
				catch(Exception e)
				{
					
				}
				try
				{
					writer.close();
				}
				catch(Exception e)
				{
					
				}
			}
			break;
			
		}
	}
	/**
	 * <p>Download the file.</p>
	 * 
	 * <p>파일을 다운로드합니다.</p>
	 * 
	 * @param url : Target file URL 다운로드할 파일이 있는 URL
	 * @param target : Where to save 어디에 저장할 것인지의 정보가 들어 있는 File 객체
	 * @param viewer : Status viewer with gagebar, can be null 게이지바로 상태를 알려 주는 객체
	 * @param speed : How many times working in 20 millisecond 20 밀리초 안에 수행할 작업 횟수, 너무 크면 불안정해 질 수 있음
	 */
	public static void download(String url, File target, StatusViewer viewer, int speed)
	{
		try
		{
			java.net.URL urls = null;
			InputStream stream = null;
			BufferedInputStream bufferedInput = null;
			FileOutputStream fileStream = null;
			BufferedOutputStream bufferedOutput = null;
			
			if(target == null) return;
			
			if(viewer != null) viewer.reset();
			int reads = -1;
			
			try
			{
				urls = new java.net.URL(url);
				stream = urls.openStream();
				bufferedInput = new BufferedInputStream(stream);
				fileStream = new FileOutputStream(target);
				bufferedOutput = new BufferedOutputStream(fileStream);
				
				int period = speed;
				int now_period = 0;
				if(period <= 0) period = 10000; 
				
				while(true)
				{
					reads = bufferedInput.read();
					if(reads == -1) break;
					
					bufferedOutput.write(reads);
					now_period++;				
					if(now_period >= period)
					{
						now_period = 0;
						if(viewer != null) viewer.nextStatus();
						try
						{
							Thread.sleep(20);
						}
						catch(Exception e1)
						{
							
						}					
					}
				}			
				
				if(viewer != null) viewer.reset();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					bufferedOutput.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					bufferedInput.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					fileStream.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					stream.close();
				}
				catch(Exception e)
				{
					
				}
				if(viewer != null) viewer.reset();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
