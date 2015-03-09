package hjow.methodconverter.ui;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import hjow.convert.module.CanConvertByte;
import hjow.methodconverter.ByteConverter;
import hjow.methodconverter.Controller;
import hjow.methodconverter.ParameterGetter;

/**
 * <p>This object helps to convert bytes as GUI.</p>
 * 
 * <p>이 객체는 바이트로 된 데이터를 GUI 환경에서 변환하는 것을 돕습니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
public abstract class GUIBinaryConverter extends ByteConverter implements ActionListener, ItemListener, WindowListener, HasParameterText
{
	protected List<CanConvertByte> modules = new Vector<CanConvertByte>();
	protected int selectedModule = -1;
	protected GUIParameterGetter paramGetter;
	protected StatusViewer statusViewer;
	protected StatusBar statusField;
	
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 만듭니다.</p>
	 */
	public GUIBinaryConverter()
	{
		init();
	}
	
	/**
	 * <p>Reset module list.</p>
	 * 
	 * <p>모듈 리스트를 초기화합니다.</p>
	 */
	protected void init()
	{
		this.modules.clear();
		for(int i=0; i<Controller.getModules().size(); i++)
		{
			if((Controller.getModules().get(i)) instanceof CanConvertByte)
			{
				this.modules.add((CanConvertByte) Controller.getModules().get(i));
			}
		}
	}
	
	/**
	 * <p>This method is called when the user press load button.</p>
	 * 
	 * <p>이 메소드는 사용자가 불러오기 버튼을 눌렀을 때 호출됩니다.</p>
	 */
	public abstract void loadButtonPressed();
	
	/**
	 * <p>This method is called when the user press save button.</p>
	 * 
	 * <p>이 메소드는 사용자가 저장하기 버튼을 눌렀을 때 호출됩니다.</p>
	 */
	public abstract void saveButtonPressed();
	
	/**
	 * <p>Return save field text value.</p>
	 * 
	 * <p>저장 텍스트필드의 텍스트를 반환합니다.</p>
	 * 
	 * @return save field text value
	 */
	public abstract String getSaveFieldText();
	
	/**
	 * <p>Return load field text value.</p>
	 * 
	 * <p>불러오기 텍스트필드의 텍스트를 반환합니다.</p>
	 * 
	 * @return save field text value
	 */
	public abstract String getLoadFieldText();
	
	/**
	 * <p>Set save field text.</p>
	 * 
	 * <p>저장 텍스트필드에 텍스트를 삽입합니다.</p>
	 * 
	 * @param text : new save field text value
	 */
	public abstract void setSaveFieldText(String text);
	
	/**
	 * <p>Set load field text.</p>
	 * 
	 * <p>불러오기 텍스트필드에 텍스트를 삽입합니다.</p>
	 * 
	 * @param text : new load field text value
	 */
	public abstract void setLoadFieldText(String text);
	
	/**
	 * <p>Return key field text value.</p>
	 * 
	 * <p>키 텍스트필드의 텍스트를 반환합니다.</p>
	 * 
	 * @return key field text
	 */
	public abstract String getKeyFieldText();
	
	/**
	 * <p>Return selected algorithm.</p>
	 * 
	 * <p>선택한 알고리즘 이름을 반환합니다.</p>
	 * 
	 * @return selected algorithm
	 */
	public abstract String getAlgorithmText();
	
	/**
	 * <p>Return selected keypadding method.</p>
	 * 
	 * <p>선택한 키 채우기 방법 이름을 반환합니다.</p>
	 * 
	 * @return selected keypadding method
	 */
	public abstract String getKeypadText();
	
	/**
	 * <p>Return parameter field text.</p>
	 * 
	 * <p>매개 변수 입력 텍스트필드 값을 반환합니다.</p>
	 * 
	 * @return parameter field text
	 */
	public abstract String getParameterText();
	
	/**
	 * <p>Set parameter field text.</p>
	 * 
	 * <p>매개 변수 입력 텍스트필드에 텍스트를 입력합니다..</p>
	 * 
	 * @param text : parameter text
	 */
	public abstract void SetParameterText(String text);
	
	/**
	 * <p>Get parameters user inputs.</p>
	 * 
	 * <p>파라미터를 받습니다.</p>
	 * 
	 * @return parameters
	 */
	public Map<String, String> getParameterInputs()
	{
		Map<String, String> parameters = new Hashtable<String, String>();
		
		parameters = ParameterGetter.toParameter(getParameterText());
		if(modules.get(selectedModule).isEncryptingModule())
		{
			parameters.put("key", getKeyFieldText());			
			parameters.put("keypadding", getKeypadText());
		}
		parameters.put("save_path", getSaveFieldText());
		parameters.put("load_path", getLoadFieldText());
		parameters.put("option", getAlgorithmText());
		return parameters;
	}
	/**
	 * <p>This method is called when the user press convert button.</p>
	 * 
	 * <p>이 메소드는 사용자가 변환 버튼을 눌렀을 때 호출됩니다.</p>
	 */
	public void convert()
	{
		try
		{
			File saveFile = new File(getSaveFieldText());
			File loadFile = new File(getLoadFieldText());
			
			if(! loadFile.exists()) return;
			
			enableAll(false);
			
			if(selectedModule >= 0 && modules.size() >= 1)
			{
				if(selectedModule < modules.size())
				{
					save(saveFile, modules.get(selectedModule).convert(load(loadFile), getParameterInputs(), statusField, statusViewer));			
				}
			}
			
			enableAll(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			enableAll(true);
		}
	}
	
	/**
	 * <p>Enable or disable all components.</p>
	 * 
	 * <p>컴포넌트들을 활성화, 혹은 비활성화합니다.</p>
	 * 
	 * @param l : If this is true, components are enabled.
	 */
	public abstract void enableAll(boolean l);
	
	/**
	 * <p>Open dialog.</p>
	 * 
	 * <p>대화 상자를 엽니다.</p>
	 */
	public abstract void open();
	
	/**
	 * <p>Close dialog.</p>
	 * 
	 * <p>대화 상자를 닫습니다.</p>
	 */
	public abstract void close();
	
	@Override
	public int getSelectedModule()
	{
		return selectedModule;
	}
	@Override
	public String getSelectedModuleParamHelp()
	{
		return modules.get(getSelectedModule()).getParameterHelp();
	}
	@Override
	public List<String> getSelectedModuleParameterKeys()
	{
		return modules.get(getSelectedModule()).parameterKeyList();
	}
	@Override
	public String getSelectedModuleName()
	{
		return modules.get(getSelectedModule()).getName(Controller.getSystemLocale());
	}
	
	@Override
	public void windowActivated(WindowEvent e)
	{
		
	}
	@Override
	public void windowClosed(WindowEvent e)
	{
		
	}
	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
	}
	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
	}
	@Override
	public void windowIconified(WindowEvent e)
	{
		
	}
	@Override
	public void windowOpened(WindowEvent e)
	{
		
	}

	public StatusViewer getStatusViewer()
	{
		return statusViewer;
	}

	public void setStatusViewer(StatusViewer statusViewer)
	{
		this.statusViewer = statusViewer;
	}

	public StatusBar getStatusField()
	{
		return statusField;
	}

	public void setStatusField(StatusBar statusField)
	{
		this.statusField = statusField;
	}
}
