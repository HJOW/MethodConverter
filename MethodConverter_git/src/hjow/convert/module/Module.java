package hjow.convert.module;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Module standard.</p>
 * 
 * <p>모듈 표준이 되는 인터페이스입니다.</p>
 * 
 * @author HJOW
 *
 */
public interface Module extends Serializable
{
	/**
	 * <p>Return name of the module.</p>
	 * 
	 * <p>모듈 이름을 반환합니다.</p>
	 * 
	 * @return Name of the module 모듈 이름
	 */
	public String getName();
	
	/**
	 * <p>Return name of the module.</p>
	 * 
	 * <p>모듈 이름을 반환합니다.</p>
	 * 
	 * @param locale : locale. default : en
	 * @return Name of the module 모듈 이름
	 */
	public String getName(String locale);
	
	/**
	 * <p>Return null if this module do not have any options.
	 * Else, return option list.</p>
	 * 
	 * <p>이 모듈이 실행되기 위한 옵션이 없으면 null 을 반환합니다.
	 * 그렇지 않으면 옵션들을 반환합니다.</p>
	 * 
	 * @return options
	 */
	public List<String> optionList();
	
	/**
	 * <p>Return available parameter keys.</p>
	 * 
	 * <p>가능한 매개 변수 키들을 반환합니다.</p>
	 * 
	 * @return available parameter keys
	 */
	public List<String> parameterKeyList();
	
	/**
	 * <p>This is true if this module is authorized.</p>
	 * 
	 * <p>인증된 모듈인지 여부를 반환합니다.</p>
	 * 
	 * @return true if this module is authorized.
	 */
	public boolean isAuthorized();
	
	/**
	 * <p>Check if the text is correct code to use additional priority.</p>
	 * 
	 * <p>추가 기능을 사용할 수 있기 위한 코드인지를 확인합니다.</p>
	 * 
	 * @param input_auths : priority code
	 * @return true if the priority code is correct
	 */
	public boolean isAuthCode(String input_auths);
	
	/**
	 * <p>Stop to use this object. If user close this program, this method is called.</p>
	 * 
	 * <p>이 객체 사용을 중단합니다. 프로그램 종료 시 이 메소드가 자동 호출됩니다.</p>
	 */
	public void close();
	
	/**
	 * <p>Return parameter help text.</p>
	 * 
	 * <p>매개변수 관련 도움말을 반환합니다.</p>
	 * 
	 * @return help messages
	 */
	public String getParameterHelp();
	
	/**
	 * <p>Return english name that is unique.</p>
	 * 
	 * <p>고유한 이름을 반환합니다. 일반적으로 영어 이름을 반환합니다.</p>
	 * 
	 * @return unique name
	 */
	public String getDefinitionName();
	
	/**
	 * <p>Return true if option combobox needs to be editabled.</p>
	 * 
	 * <p>옵션 선택 콤보박스에 직접 값을 입력할 수 있는 모듈이면 true 를 반환합니다.</p>
	 * 
	 * @return true if option combobox needs to be editabled
	 */
	public boolean isOptionEditable();
	
	/**
	 * <p>Return help message.</p>
	 * 
	 * <p>도움말을 반환합니다.</p>
	 * 
	 * @return help message
	 */
	public String getHelps();
}
