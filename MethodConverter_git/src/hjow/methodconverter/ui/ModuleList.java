package hjow.methodconverter.ui;

import hjow.convert.module.ScriptModule;
import hjow.convert.module.UserDefinedModule;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Statics;
import hjow.network.ModulePackage;
import hjow.network.Refreshable;

import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 * <p>This class helps to show unauthorized modules list.</p>
 * 
 * <p>이 클래스는 사용자에게 비인증 모듈 리스트를 보여주는 것을 돕습니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class ModuleList implements ActionListener, Refreshable, IsComponent
{
	protected Manager upper = null;
	protected Vector<UserDefinedModule> moduleLists;
	
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 * 
	 * @param upper : Manager object
	 */
	public ModuleList(Manager upper)
	{
		init(upper);
	}
	
	/**
	 * <p>Initialize all components.</p>
	 * 
	 * <p>컴포넌트들을 초기화합니다.</p>
	 * 
	 * @param upper : Manager object
	 */
	protected void init(Manager upper)
	{
		this.upper = upper;
	}
	
	@Override
	public void refresh()
	{
		moduleLists = new Vector<UserDefinedModule>();
		
		for(int i=0; i<Controller.userModules().size(); i++)
		{
			if(! (Controller.userModules().get(i) instanceof ScriptModule)) moduleLists.add(Controller.userModules().get(i));
		}		

		String[] listData = new String[moduleLists.size()];
		for(int i=0; i<listData.length; i++)
		{
			listData[i] = moduleLists.get(i).getName();
		}
		setList(listData);
	}
	/**
	 * <p>Set module lists.</p>
	 * 
	 * <p>모듈 리스트를 입력합니다.</p>
	 * 
	 * @param listData : module list
	 */
	public abstract void setList(String[] listData);
	/**
	 * <p>This method is called when user press "add" button.</p>
	 * 
	 * <p>이 메소드는 사용자가 "추가" 버튼을 눌렀을 때 실행됩니다.</p>
	 */
	public void addButtonPressed()
	{
		upper.enableAll(false);
		try
		{
			InputStream stream = null;
			ObjectInputStream objectStream = null;
			try
			{
				if(upper.requestYes(Controller.getString("Do you want to download module from following URL?") + "\nURL : " + getAddFieldText().trim()))
				{
					java.net.URL newURL = new java.net.URL(getAddFieldText().trim());
					
					UserDefinedModule newModule = null;
					if(isBinaryMode())
					{
						stream = newURL.openStream();
						objectStream = new ObjectInputStream(stream);
						newModule = (UserDefinedModule) objectStream.readObject();
					}
					else
					{
						String contents = Controller.readWeb(getAddFieldText().trim(), 20, null, true);
						newModule = new UserDefinedModule(contents);
					}
					if(upper.requestYes(Controller.getString("Do you want to use this module?") + " (" + newModule.getName() + ")"))
					{
						Controller.insertModule(newModule);
						if(Statics.autoSavingLevel() <= 2)
						{
							if(upper.requestYes(Controller.getString("Do you want to turn on the auto saving?")))
							{
								Controller.setOption("autoSave", String.valueOf(3));
							}
						}
					}
					
				}
			}
			catch(java.net.MalformedURLException e)
			{
				upper.alert(Controller.getString("Please check the url is correct.") + "\nURL : " + getAddFieldText().trim());
			}
			catch(Exception e)
			{
				e.printStackTrace();
				upper.alert(Controller.getString("Error") + " : " + e.getMessage());
			}
			finally
			{
				try
				{
					objectStream.close();
				}
				catch (Exception e)
				{
					
				}			
				try
				{
					stream.close();
				}
				catch (Exception e)
				{
					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		upper.enableAll(true);
	}
	
	/**
	 * <p>Return selected module number.</p>
	 * 
	 * <p>선택된 모듈 번호를 반환합니다.</p>
	 * 
	 * @return selected module number
	 */
	public abstract int getSelectedModuleIndex();
	
	/**
	 * <p>This method is called when user press "New" button.</p>
	 *
	 * <p>이 버튼은 사용자가 "새로 만들기" 버튼을 클릭했을 때 실행됩니다.</p>
	 */
	public void newButtonPressed()
	{
		upper.moduleEditorOpenMenuSelected();
	}
	
	/**
	 * <p>This method is called when user press "Send" button.</p>
	 *
	 * <p>이 버튼은 사용자가 "보내기" 버튼을 클릭했을 때 실행됩니다.</p>
	 */
	public void sendButtonPressed()
	{
		upper.enableAll(false);
		
		try
		{
			int selectedIndex = getSelectedModuleIndex();
			if(selectedIndex < 0)
			{
				upper.alert(Controller.getString("Please select module and retry."));
			}
			else
			{
				UserDefinedModule gets = moduleLists.get(selectedIndex);
				String ips = upper.requestInput(Controller.getString("Please input IP address to send."));
				ModulePackage newPack = new ModulePackage();
				newPack.setName(gets.getName());
				newPack.setEncryptedInput(upper.requestInput(Controller.getString("Please input nickname.")));
				newPack.setModule(gets);
				newPack.setSender();
				Controller.send(ips, newPack);
				upper.alert(Controller.getString("Successful") + " !");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			upper.alert(Controller.getString("Error") + " : " + e.getMessage());
		}
			
		upper.enableAll(true);
	}
	
	/**
	 * <p>Return true if the setting is to download module as binary data.</p>
	 * 
	 * <p>모듈 다운로드 설정이 바이트 데이터로 되어 있는지 여부를 반환합니다.</p>
	 * 
	 * @return true if the setting is to download module as binary data
	 */
	public boolean isBinaryMode()
	{
		return true;
	}
	
	/**
	 * <p>Return textfield text value.</p>
	 * 
	 * <p>텍스트필드 내에 있는 값을 반환합니다.</p>
	 * 
	 * @return textfield text
	 */
	public abstract String getAddFieldText();
	
	/**
	 * <p>Set textfield text value.</p>
	 * 
	 * <p>텍스트필드에 텍스트를 넣습니다..</p>
	 * 
	 * @param text : text what you want to input
	 */
	public abstract void setAddFieldText(String text);
			
		
	/**
	 * <p>Cut circular references.</p>
	 * 
	 * <p>상호 참조를 끊습니다.</p>
	 */
	public void close()
	{
		upper = null;
	}
}
