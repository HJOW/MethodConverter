package hjow.convert.module.plugin;

import hjow.methodconverter.Controller;
import hjow.methodconverter.ParameterGetter;
import hjow.network.FileSavable;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * <p>This class object will be used as plugin of module.</p>
 * 
 * <p>이 클래스 객체는 어떠한 모듈의 플러그인으로 작동하게 됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class ModulePlugin implements FileSavable
{
	private static final long serialVersionUID = 6084247643595523979L;
	protected String name;
	protected Map<String, String> dataMap;
	protected Long definitionId;
	protected String targetDefinitionName;
	
	/**
	 * <p>Create new plugin object.</p>
	 * 
	 * <p>새 플러그인 객체를 만듭니다.</p>
	 */
	public ModulePlugin()
	{
		definitionId = new Long(new Random().nextLong());
		dataMap = new Hashtable<String, String>();
	}

	@Override
	public String serialize()
	{
		StringBuffer results = new StringBuffer("");
		dataMap.put("pluginName", name);
		dataMap.put("definitionId", String.valueOf(definitionId));
		dataMap.put("targetDefinitionName", targetDefinitionName);
		results = results.append(ParameterGetter.toParameterString(dataMap));
		dataMap.remove("pluginName");
		dataMap.remove("definitionId");
		dataMap.remove("targetDefinitionName");
		return results.toString();
	}

	@Override
	public void saveContents(File file) throws Exception
	{
		String datas = serialize();
		Controller.saveFile(file, datas, null);
	}
	/**
	 * <p>Load plugin from file.</p>
	 * 
	 * <p>플러그인을 파일로부터 불러옵니다.</p>
	 * 
	 * @param file : File object
	 * @return plugin
	 * @throws Exception : occurs while accessing file
	 */
	public static ModulePlugin load(File file) throws Exception
	{
		if(file == null) return null;
		String name = file.getName();
		
		ModulePlugin newPlugin = null;;
		
		if(name.endsWith("pgin") || name.endsWith("PGIN"))
		{
			String gets = Controller.readFile(file, 20, null);
			newPlugin = new ModulePlugin();
			Map<String, String> mapData = ParameterGetter.toParameter(gets);
			newPlugin.setDefinitionId(new Long(mapData.get("definitionId")));
			newPlugin.setTargetDefinitionName(mapData.get("targetDefinitionName"));
			newPlugin.setName(mapData.get("pluginName"));
			mapData.remove("pluginName");
			mapData.remove("definitionId");
			mapData.remove("targetDefinitionName");
			newPlugin.setDataMap(mapData);
		}
		else
		{
			FileInputStream inputs = null;
			InputStream zipper = null;
			ObjectInputStream objectInput = null;
			
			try
			{
				inputs = new FileInputStream(file);
				if(name.endsWith("zgin") || name.endsWith("ZGIN"))
				{
					zipper = new java.util.zip.GZIPInputStream(inputs);
					objectInput = new ObjectInputStream(zipper);
				}
				else if(name.endsWith("bgin") || name.endsWith("BGIN"))
				{
					objectInput = new ObjectInputStream(inputs);
				}
				newPlugin = (ModulePlugin) objectInput.readObject();
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				try
				{
					inputs.close();
				}
				catch(Exception e1)
				{
					
				}
				try
				{
					zipper.close();
				}
				catch(Exception e1)
				{
					
				}
				try
				{
					objectInput.close();
				}
				catch(Exception e1)
				{
					
				}
			}
		}
		return newPlugin;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public Long getDefinitionId()
	{
		return definitionId;
	}

	public void setDefinitionId(Long definitionId)
	{
		this.definitionId = definitionId;
	}

	public String getTargetDefinitionName()
	{
		return targetDefinitionName;
	}

	public void setTargetDefinitionName(String targetDefinitionName)
	{
		this.targetDefinitionName = targetDefinitionName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
