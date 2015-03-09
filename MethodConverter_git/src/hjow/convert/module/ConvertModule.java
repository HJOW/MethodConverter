package hjow.convert.module;

import hjow.methodconverter.StringTable;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;

import java.util.Map;

public interface ConvertModule extends Module
{
	/**
	 * <p>This method will be overrided by any modules.
	 * Used to convert string.
	 * 
	 * <p>이 메소드는 모듈에 의해 재정의됩니다.
	 * 문자열을 변환하는 데 사용됩니다.</p>
	 * 
	 * 
	 * @param stringTable : String table 스트링 테이블
	 * @param syntax : Syntax table 문법 테이블
	 * @param before : Before text 실행 전 텍스트
	 * @param statusViewer : StatusViewer object 상태 변화 객체 (프로세스가 살아 있음을 알릴 때 사용) Can be null
	 * @param statusField : StatusBar object 상태바 객체 (상태를 텍스트로 출력할 때 사용) Can be null
	 * @param threadTerm : Thread-sleep term 쓰레드가 대기 상태에 있는 시간, 짧으면 작업이 빨리 끝나지만 너무 짧으면 시스템이 불안정해질 수 있습니다.
	 * @param parameters : Parameters as Hashtable object to convert String. 변환에 사용되는 매개 변수
	 * @return Converted string 변환된 텍스트
	 */
	public String convert(StringTable stringTable, Map<String, String> syntax, String before, StatusViewer statusViewer, StatusBar statusField, long threadTerm, Map<String, String> parameters);
	
	/**
	 * <p>This method will be overrided by any modules.
	 * Used to convert string.
	 * 
	 * <p>이 메소드는 모듈에 의해 재정의됩니다.
	 * 문자열을 변환하는 데 사용됩니다.</p>
	 * 
	 * @param before : Before text 실행 전 텍스트
	 * @param parameters : Parameters as Hashtable object to convert String. 변환에 사용되는 매개 변수
	 * @return
	 */
	public String convert(String before, Map<String, String> parameters);
	
	/**
	 * <p>Return default parameter text.</p>
	 * 
	 * <p>매개 변수 텍스트 기본값을 반환합니다.</p>
	 * 
	 * @return default parameter text
	 */
	public String defaultParameterText();
}
