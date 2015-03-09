package hjow.methodconverter.ui;

import hjow.methodconverter.Controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * <p>This class has many static members of Font settings.</p>
 * 
 * <p>이 클래스에는 폰트 설정을 위한 여러 정적 멤버들이 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class FontSetting
{
	/**
	 * <p>Font objects.</p>
	 * 
	 * <p>글꼴 객체들입니다.</p>
	 */
	public static Font usingFont, usingFont2, usingFontB, usingFont2B, usingFontP;
	
	/**
	 * <p>Default font size.</p>
	 * 
	 * <p>기본 글꼴 크기입니다.</p>
	 */
	public static int default_fontSize = 12;
	public static String usingFontName = null;
	public static void prepareFont()
	{
		boolean font_loaded = false;
		String osName = System.getProperty("os.name");
		String locale = System.getProperty("user.language");
		int fontSize = default_fontSize;
		
		try
		{
			fontSize = Integer.parseInt(Controller.getOption("fontSize"));
		}
		catch(Exception e)
		{
			fontSize = default_fontSize;
		}
		
		InputStream infs = null;
		FileInputStream finfs = null;
		ObjectInputStream objs = null;
		if(osName.startsWith("Windows") || osName.startsWith("windows") || osName.startsWith("WINDOWS"))
		{
			try
			{
				File fontFile = new File(Controller.getDefaultPath() + "basic_font.ttf");
				if(fontFile.exists())
				{
					finfs = new FileInputStream(fontFile);
					infs = new BufferedInputStream(finfs);
					usingFont = Font.createFont(Font.TRUETYPE_FONT, infs);
					usingFont = usingFont.deriveFont(Font.PLAIN, fontSize);
					usingFont2 = usingFont.deriveFont(Font.PLAIN, fontSize * 2);
					usingFontB = usingFont.deriveFont(Font.BOLD, fontSize);
					usingFont2B = usingFont.deriveFont(Font.BOLD, fontSize * 2);
					usingFontP = usingFont.deriveFont(Font.BOLD, fontSize - 2);
					font_loaded = true;
				}
				else font_loaded = false;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				font_loaded = false;
			}
			finally
			{
				try
				{
					infs.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					finfs.close();
				}
				catch(Exception e)
				{
					
				}
			}
		}
		String truetype = "돋움";
		try
		{
			GraphicsEnvironment gr = GraphicsEnvironment.getLocalGraphicsEnvironment();
			String[] fontList = gr.getAvailableFontFamilyNames();
			for(int i=0; i<fontList.length; i++)
			{
				if(fontList[i].equals("나눔고딕코딩") || fontList[i].equalsIgnoreCase("NanumGothicCoding"))
				{
					truetype = fontList[i];
					break;
				}
			}
		} 
		catch (Exception e1)
		{
			truetype = "돋움";
		}
		
		if(! font_loaded)
		{
			try
			{
				File fontObjectFile = new File(Controller.getDefaultPath() + "defaultFont.font");
				if(fontObjectFile.exists())
				{
					finfs = new FileInputStream(fontObjectFile);				
					objs = new ObjectInputStream(finfs);
					usingFont = (Font) objs.readObject();
					usingFont2 = usingFont.deriveFont(Font.PLAIN, usingFont.getSize() * 2);
					usingFontB = usingFont.deriveFont(Font.BOLD, usingFont.getSize());
					usingFont2B = usingFont.deriveFont(Font.BOLD, usingFont.getSize() * 2);
					usingFontP = usingFont.deriveFont(Font.BOLD, fontSize - 2);
				}
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				font_loaded = false;
			}
			finally
			{
				try
				{
					objs.close();
				}
				catch(Exception e)
				{
					
				}
				try
				{
					finfs.close();
				}
				catch(Exception e)
				{
					
				}
			}
		}
		
		if(! font_loaded)
		{
			if(osName.equalsIgnoreCase("Windows Vista") || osName.equalsIgnoreCase("Windows 7")
					|| osName.equalsIgnoreCase("Windows 8")|| osName.equalsIgnoreCase("Windows 8.1"))
			{
				if(locale.startsWith("ko") || locale.startsWith("KO") || locale.startsWith("kr") || locale.startsWith("KR") || locale.startsWith("kor") || locale.startsWith("KOR"))
					usingFontName = truetype;
				else
					usingFontName = "Arial";
			}
			else if(osName.startsWith("Windows") || osName.startsWith("windows") || osName.startsWith("WINDOWS"))
			{
				if(osName.endsWith("95") || osName.endsWith("98") || osName.endsWith("me") || osName.endsWith("ME") || osName.endsWith("Me") || osName.endsWith("2000"))
				{
					if(locale.startsWith("ko") || locale.startsWith("KO") || locale.startsWith("kr") || locale.startsWith("KR") || locale.startsWith("kor") || locale.startsWith("KOR"))
						usingFontName = "돋움";
					else
						usingFontName = "Dialog";
				}
				else
				{
					if(locale.startsWith("ko") || locale.startsWith("KO") || locale.startsWith("kr") || locale.startsWith("KR") || locale.startsWith("kor") || locale.startsWith("KOR"))
						usingFontName = truetype;
					else
						usingFontName = "Arial";
				}
				
			}
			try
			{
				usingFont = new Font(usingFontName, Font.PLAIN, fontSize);
				usingFontB = new Font(usingFontName, Font.BOLD, fontSize);
				usingFont2 = new Font(usingFontName, Font.PLAIN, fontSize * 2);
				usingFont2B = new Font(usingFontName, Font.BOLD, fontSize * 2);
				usingFontP = usingFont.deriveFont(Font.BOLD, fontSize - 2);
				font_loaded = true;
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				font_loaded = false;
				usingFont = null;
			}
		}
		if(font_loaded)
		{
			try
			{
				UIManager.put("OptionPane.messageFont", new FontUIResource(usingFont));
				UIManager.put("OptionPane.font", new FontUIResource(usingFont));
				UIManager.put("OptionPane.buttonFont", new FontUIResource(usingFont));
				UIManager.put("JOptionPane.font", new FontUIResource(usingFont));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void setFontRecursively(Component comp, Font font)
	{
		setFontRecursively(comp, font, 1000);
	}
	public static void setFontRecursively(Component comp, Font font, int prevent_infiniteLoop)
	{
		try
		{
			if(font == null) return;
			comp.setFont(font);
			int max_limits = prevent_infiniteLoop;
			if(comp instanceof Container)
			{
				Container cont = (Container) comp;
				int ub = cont.getComponentCount();
				for(int  j=0; j<ub; j++)
				{
					ub = cont.getComponentCount();
					if(ub > max_limits) ub = max_limits;
					max_limits--;
					if(max_limits <= 0) break;
					setFontRecursively(cont.getComponent(j), font, max_limits);					
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
