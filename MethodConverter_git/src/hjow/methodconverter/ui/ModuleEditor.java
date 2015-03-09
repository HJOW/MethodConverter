package hjow.methodconverter.ui;

import hjow.convert.module.UserDefinedByteModule;
import hjow.convert.module.UserDefinedModule;
import hjow.methodconverter.StringTable;

import java.awt.Dialog;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.StringTokenizer;

/**
 * <p>Module-Editor object</p>
 * 
 * <p>모듈 편집기 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class ModuleEditor implements ActionListener, WindowListener
{
	protected Dialog dialog;
	protected UserDefinedModule target;
	/**
	 * <p>Create editor object.</p>
	 * 
	 * <p>모듈 편집기 객체를 생성합니다.</p>
	 */
	public ModuleEditor()
	{
		super();
		init(null);
	}
	/**
	 * <p>Create editor object.</p>
	 * 
	 * <p>모듈 편집기 객체를 생성합니다.</p>
	 * 
	 * @param upper : Frame or JFrame object
	 */
	public ModuleEditor(Object upper)
	{
		super();
		init(upper);
	}
	/**
	 * <p>This method will be run to initialize this GUI program.
	 * Recommended to override this method.</p>
	 * 
	 * <p>GUI 프로그램을 초기화할 때 자동으로 호출됩니다.
	 * 이 메소드를 재정의해 사용하세요.</p>
	 * 
	 * @param upper : Frame or JFrame object
	 */
	public abstract void init(Object upper);
	
	/**
	 * <p>Apply string table value on components.</p>
	 * 
	 * <p>스트링 테이블을 컴포넌트에 적용합니다.</p>
	 * 
	 * @param stringTable : String table
	 */
	public abstract void setLanguage(StringTable stringTable);
	
	/**
	 * <p>This method is called when user click Save button.</p>
	 * 
	 * <p>이 메소드는 사용자가 저장 버튼을 클릭했을 때 실행됩니다.</p>
	 */
	public abstract void save();
	
	/**
	 * <p>This method is called when user click Load button.</p>
	 * 
	 * <p>이 메소드는 사용자가 불러오기 버튼을 클릭했을 때 실행됩니다.</p>
	 */
	public abstract void load();
	
	/**
	 * <p>Open module editor.</p>
	 * 
	 * <p>모듈 편집기 창을 엽니다.</p>
	 */
	public abstract void open();
	
	/**
	 * <p>Close module editor.</p>
	 * 
	 * <p>모듈 편집기 창을 닫습니다.</p>
	 */
	public abstract void close();
	
	/**
	 * <p>Reset module editor.</p>
	 * 
	 * <p>모듈 편집기 창을 초기화합니다.</p>
	 */
	public void reset()
	{
		if(target == null)
		{
			try
			{
				target = new UserDefinedByteModule();				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			objectToField();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Return center area text value.</p>
	 * 
	 * <p>중앙의 텍스트 영역 안의 텍스트를 반환합니다.</p>
	 * 
	 * @return text inside center area
	 */
	public abstract String getCenterText();
	
	/**
	 * <p>Set center area text value.</p>
	 * 
	 * <p>중앙의 텍스트 영역 안에 텍스트를 입력합니다.</p>
	 * 
	 * @param value : text
	 */
	public abstract void setCenterText(String value);
	
	/**
	 * <p>Return center area text value.</p>
	 * 
	 * <p>중앙의 텍스트 영역 안의 텍스트를 반환합니다.</p>
	 * 
	 * @return text inside center area
	 */
	public abstract String getNameText();
	
	/**
	 * <p>Set center area text value.</p>
	 * 
	 * <p>중앙의 텍스트 영역 안에 텍스트를 입력합니다.</p>
	 * 
	 * @param name : text
	 */
	public abstract void setNameText(String name);
	
	/**
	 * <p>Return option field text value.</p>
	 * 
	 * <p>옵션 입력 필드 안의 텍스트를 반환합니다.</p>
	 * 
	 * @return text inside center area
	 */
	public abstract String getOptionText();
	
	/**
	 * <p>Set option field text value.</p>
	 * 
	 * <p>옵션 입력 필드 안에 텍스트를 입력합니다.</p>
	 * 
	 * @param value : text
	 */
	public abstract void setOptionText(String value);
	
	/**
	 * <p>Return finalize call field text value.</p>
	 * 
	 * <p>종료 시 실행 코드 입력 필드 안의 텍스트를 반환합니다.</p>
	 * 
	 * @return text inside center area
	 */
	public abstract String getFinalizeCallText();
	
	
	/**
	 * <p>Set finalize call field text value.</p>
	 * 
	 * <p>종료 시 실행 코드 입력 필드 안에 텍스트를 입력합니다.</p>
	 * 
	 * @param value : text
	 */
	public abstract void setFinalizeCallText(String value);
	
	/**
	 * <p>Return first authority code field text value.</p>
	 * 
	 * <p>첫 번째 인증 코드 입력 필드 안의 텍스트를 반환합니다.</p>
	 * 
	 * @return text inside center area
	 */
	public abstract String getAuth1Text();
	
	/**
	 * <p>Set first authority code field text value.</p>
	 * 
	 * <p>첫 번째 인증 코드 입력 필드 안에 텍스트를 입력합니다.</p>
	 * 
	 * @param value : text
	 */
	public abstract void setAuth1Text(String value);
	
	/**
	 * <p>Return second authority code field text value.</p>
	 * 
	 * <p>두 번째 인증 코드 입력 필드 안의 텍스트를 반환합니다.</p>
	 * 
	 * @return text inside center area
	 */
	public abstract String getAuth2Text();
	
	/**
	 * <p>Set second authority code field text value.</p>
	 * 
	 * <p>두 번째 인증 코드 입력 필드 안에 텍스트를 입력합니다.</p>
	 * 
	 * @param value : text
	 */
	public abstract void setAuth2Text(String value);
	
	/**
	 * <p>Return script type selector text value.</p>
	 * 
	 * <p>스크립트 타입 선택 필드 안의 텍스트를 반환합니다.</p>
	 * 
	 * @return text inside center area
	 */
	public abstract String getScriptTypeText();
	
	/**
	 * <p>Set script type selector text value.</p>
	 * 
	 * <p>스크립트 타입 선택 필드 안에 텍스트를 입력합니다.</p>
	 * 
	 * @param value : text
	 */
	public abstract void setScriptTypeText(String value);
	
	/**
	 * <p>Return option editable checkbox value.</p>
	 * 
	 * <p>옵션 입력가능 체크박스의 체크 여부를 반환합니다.</p>
	 * 
	 * @return option editable checkbox value
	 */
	public abstract boolean isOptionEditable();
	
	/**
	 * <p>Set option editable checkbox value.</p>
	 * 
	 * <p>옵션 입력가능 체크박스를 체크하거나 해제합니다.</p>
	 * 
	 * @param editables : true or false
	 */
	public abstract void setOptionEditable(boolean editables);
	
	/**
	 * <p>This method is called when user click New button.</p>
	 * 
	 * <p>이 메소드는 사용자가 새로 만들기 버튼을 클릭했을 때 실행됩니다.</p>
	 */
	public void newone()
	{
		try
		{
			target = new UserDefinedByteModule();
			reset();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
		
	/**
	 * <p>Convert values user input to the module object.</p>
	 * 
	 * <p>사용자가 입력한 값들을 모듈로 변환합니다.</p>
	 * 
	 * @throws Exception
	 */
	public void fieldToObject() throws Exception
	{
		target = new UserDefinedModule();
		target.setName(getNameText());
		target.setAuths(getAuth1Text());
		target.setAddauth(getAuth2Text());
		target.setDeclareStatements(getCenterText());
		target.setFinalizeCall(getFinalizeCallText());
		target.setScriptType(getScriptTypeText());
		target.setOptionEditable(isOptionEditable());
		try
		{
			if(target.canConvertByte())
			{
				UserDefinedModule newModule = new UserDefinedByteModule(false);
				newModule.setAll(target);
				newModule.init();
				target = newModule;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		String options = getOptionText();
		options = options.trim();
		if(! (options.equals("")))
		{
			StringTokenizer tokenizer = new StringTokenizer(options, ",");
			target.getOptions().clear();
			while(tokenizer.hasMoreTokens())
			{
				target.getOptions().add(tokenizer.nextToken().trim());
			}
		}		
	}
	/**
	 * <p>Insert at text fields with module data.</p>
	 * 
	 * <p>모듈 데이터를 텍스트 필드에 채웁니다.</p>
	 * 
	 * @throws Exception
	 */
	public void objectToField() throws Exception
	{
		setNameText(target.getName());
		setAuth1Text(target.getAuths());
		setAuth2Text(target.getAddauth());
		setCenterText(target.getDeclareStatements());
		setFinalizeCallText(target.getFinalizeCall());
		setScriptTypeText(target.getScriptType());
		setOptionEditable(target.isOptionEditable());
		
		String options = "";
		if(target.getOptions() != null)
		{
			for(int i=0; i<target.getOptions().size(); i++)
			{
				options = options + target.getOptions().get(i);
				if(i < target.getOptions().size() - 1) options = options + ",";
			}			
		}
		setOptionText(options);
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
}
