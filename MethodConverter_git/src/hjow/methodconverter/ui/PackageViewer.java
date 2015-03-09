package hjow.methodconverter.ui;

import hjow.methodconverter.Controller;
import hjow.network.NetworkPackage;
import hjow.network.Refreshable;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

/**
 * <p>This class object can show packages received.</p>
 * 
 * <p>이 클래스는 받은 패키지를 보여 주는 데 사용됩니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public abstract class PackageViewer implements ActionListener, ItemListener, IsComponent, Refreshable
{
	protected Component component;
	protected Object upper;
	protected List<NetworkPackage> packages;
	
	/**
	 * <p>Create the object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 */
	public PackageViewer()
	{
		super();
		Controller.insertRefreshes(this);
	}
	/**
	 * <p>Create the object.</p>
	 * 
	 * <p>새 객체를 만듭니다.</p>
	 */
	public PackageViewer(Object upper)
	{
		super();
		Controller.insertRefreshes(this);
	}
	
	/**
	 * <p>Initialize the component.</p>
	 * 
	 * <p>컴포넌트를 초기화합니다.</p>
	 * 
	 * 
	 * @param upper : The component include this component.
	 */
	public abstract void init(Object upper);
	/**
	 * <p>Refresh the list. This method is called by super manager (Manager object).</p>
	 * 
	 * <p>리스트를 새로 고칩니다. 이 메소드는 상위 매니저(Manager 객체)에 의해 호출됩니다.)</p>
	 * 
	 */
	public abstract void refresh();
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		
	}
	
	
	/**
	 * <p>Return component object.</p>
	 * 
	 * <p>컴포넌트를 반환합니다.</p>
	 * 
	 * @return component
	 */
	@Override
	public Component getComponent()
	{
		return component;
	}
	
	/**
	 * <p>Return true if alive checkbox is checked.</p>
	 * 
	 * <p>수신 중 여부 체크박스가 체크되어 있는지의 여부를 반환합니다.</p>
	 * 
	 * @return true if alive checkbox is checked
	 */
	public abstract boolean isAliveChecked();
	
	/**
	 * <p>This method is called at the alive checkbox is checked or not.</p>
	 * 
	 * <p>이 메소드는 수신 중 여부 체크박스 상태가 변경되었을 때 호출됩니다.</p>
	 * 
	 * 
	 */
	public abstract void aliveBoxChanged();
	
	/**
	 * <p>Close the object.</p>
	 * 
	 * <p>객체를 닫습니다.</p>
	 * 
	 */
	public void close()
	{
		
	}
	
	/**
	 * <p>Return packages list.</p>
	 * 
	 * <p>패키지 리스트를 반환합니다.</p>
	 * 
	 * @return packages list
	 */
	protected List<NetworkPackage> getPackageList()
	{
		List<NetworkPackage> packages = new Vector<NetworkPackage>();
		NetworkPackage pack;
		int counts = Controller.getReceivesCount();
		for(int i=0; i<counts; i++)
		{
			try
			{
				pack = Controller.getReceive(i);
				if(pack != null) packages.add(pack);
			}
			catch(Exception e)
			{
				
			}
		}
		return packages;
	}
}
