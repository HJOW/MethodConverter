package hjow.network;

import hjow.convert.module.UserDefinedModule;
import hjow.methodconverter.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * <p>Package which can includes module.</p>
 * 
 * <p>모듈을 내장할 수 있는 패키지입니다.</p>
 * 
 * @author HJOW
 *
 */
public class ModulePackage extends NetworkPackage
{
	private static final long serialVersionUID = -3878163885980623884L;
	private UserDefinedModule module;
	
	/**
	 * <p>Create new package.</p>
	 * 
	 * <p>새 패키지 객체를 만듭니다.</p>
	 */
	public ModulePackage()
	{
		super();
	}

	@Override
	public void saveContents(File file) throws Exception
	{
		FileWriter writer = null;
		BufferedWriter bufferedWriter = null;
		try
		{
			StringTokenizer lineToken = null;
			lineToken = new StringTokenizer(String.valueOf(module.serialize().trim()), "\n");
			
			writer = new FileWriter(file);
			bufferedWriter = new BufferedWriter(writer);
			
			bufferedWriter.write("  ");
			bufferedWriter.newLine();
			while(lineToken.hasMoreTokens())
			{
				bufferedWriter.write(lineToken.nextToken());
				bufferedWriter.newLine();
			}				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				bufferedWriter.close();
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
	}

	@Override
	public String getPackageTypeName()
	{
		return "Module";
	}

	
	@Override
	public Serializable getContents()
	{
		return Controller.getString("Title") + " : " + getName() + "\n"
	+ Controller.getString("Package ID") + " : " + String.valueOf(getPackId()) + "\n"
	+ Controller.getString("When") + " : " + String.valueOf(getTime())
				+ "\n\n" + module.serialize();
	}
	public UserDefinedModule getModule()
	{
		return module;
	}

	public void setModule(UserDefinedModule module)
	{
		this.module = module;
	}

}
