package hjow.methodconverter;

import hjow.convert.module.ConvertModule;
import hjow.convert.module.RemoveCommentModule;
import hjow.convert.module.ScriptModule;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <p>This class have static methods which can be used anywhere.</p>
 * 
 * <p>이 클래스에는 어디서나 쓸 수 있는 정적 메소드들이 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class Statics
{	
	
	/**
	 * 
	 * <p>Replace " to \"
	 * if q is false, replace ' to \'</p>
	 * 
	 * <p>모든 쌍따옴표 앞에 \ 기호를 붙입니다.
	 * q가 false 이면 모든 일반 따옴표 앞에 \ 기호를 붙입니다.</p>
	 * 
	 * @param str
	 * @param q
	 * @return adds \ at each " if q is true, adds \ at each ' if q is false
	 */
	public static String replaceQuote(String str, boolean q)
	{
		String results;
		if(q) results = str.replace("\"", "\\" + "\"");
		else results = str.replace("'", "\\" + "'");
		return results;
	}
	
	/**
	 * <p>Convert text to hashtable.</p>
	 * <p>Line starts with # will be terminated.</p>
	 * <p>Each lines are delimited by ";".</p>
	 * <p>An line, read key and value as key::value; form.</p>
	 * 
	 * <p>텍스트를 해시테이블로 변환합니다.</p>
	 * <p># 으로 시작되는 줄은 제거됩니다.</p>
	 * <p>각 줄은 ; 기호로 구분됩니다.</p>
	 * <p>키::값; 형태로 읽습니다.</p>
	 * 
	 * @param str : text
	 * @return hashtable
	 */
	public static Map<String, String> stringToHashtable(String str)
	{
		String gets;
		if(str == null) return null;
		gets = new String(str);
		Hashtable<String, String> results = new Hashtable<String, String>();
		
		StringTokenizer lineToken, keyToken;
		String lines, keys, values;
		
		gets = removeComment(gets);		
		
		lineToken = new StringTokenizer(gets, ";");
		while(lineToken.hasMoreTokens())
		{
			lines = lineToken.nextToken().trim();
			if(lines.equals("")) continue;
			keyToken = new StringTokenizer(lines, Controller.delimiter());
			keys = keyToken.nextToken().trim();
			if(keyToken.hasMoreTokens())
			{
				values = keyToken.nextToken().trim();
			}
			else
			{
				values = "true";
			}
			results.put(keys, values);
		}
		
		return results;
	}
	/**
	 * <p>Convert map(hashtable can be) into the text.</p>
	 * 
	 * <p>맵(해시테이블도 가능)을 텍스트로 변환합니다.</p>
	 * 
	 * @param table : hashtable
	 * @return : text
	 */
	public static String hashtableToString(Map<String, String> table)
	{
		Set<String> keySet = table.keySet();
		StringBuffer results = new StringBuffer(" \n");
		
		for(String k : keySet)
		{
			results = results.append(k + Controller.delimiter() + table.get(k) + ";\n");
		}
		
		return String.valueOf(results);
	}		
	
	/**
	 * <p>Copy hashtable.</p>
	 * 
	 * <p>해시테이블을 복사합니다.</p>
	 * 
	 * @param tables : original
	 * @return copied table
	 */
	public static Map<String, String> copy(Map<String, String> tables)
	{
		if(tables == null) return null;
		Set<String> keySet = tables.keySet();
		Hashtable<String, String> newTable = new Hashtable<String, String>();
		for(String k : keySet)
		{
			newTable.put(k, tables.get(k));
		}
		return newTable;
	}
	
	/**
	 * <p>Return true if untested function is on.</p>
	 * 
	 * <p>테스트 중인 기능 사용 옵션이 켜져 있는지 여부를 반환합니다.</p>
	 * 
	 * @return true if untested function is on
	 */
	public static boolean useUntestedFunction()
	{
		String useTestOption = Controller.getOption("useBeta");
		if(useTestOption == null) return false;
		return parseBoolean(useTestOption);		
	}
	
	/**
	 * <p>Return true if online-content function is on.</p>
	 * 
	 * <p>온라인 기능 사용 옵션이 켜져 있는지 여부를 반환합니다.</p>
	 * 
	 * @return true if online-content function is on
	 */
	public static boolean useOnlineContent()
	{
		String useTestOption = Controller.getOption("useOnline");
		if(useTestOption == null) return false;
		return parseBoolean(useTestOption);		
	}
	
	/**
	 * <p>Return auto-saving level</p>
	 * 
	 * <p>자동 저장 수준을 반환합니다.</p>
	 * 
	 * @return auto-saving level
	 */
	public static int autoSavingLevel()
	{
		String useTestOption = Controller.getOption("autoSave");
		if(useTestOption == null) return 0;
		return Integer.parseInt(useTestOption.trim());		
	}
	
	/**
	 * <p>Apply 'script' parameter to get results.</p>
	 * 
	 * <p>script 파라미터 값을 적용해 결과에 반영합니다.</p>
	 * 
	 * @param module : Module object
	 * @param parameters : Parameters
	 * @param target : Target text
	 * @return converted text
	 */
	public static String applyScript(ConvertModule module, Map<String, String> parameters, String target)
	{
		String scripts = parameters.get("script");
		ScriptModule scriptEngine = null;
		if(scripts == null) return target;
		if(scripts.trim().equals("")) return target;
		
		try
		{
			scriptEngine = new ScriptModule(false, 0);
			scriptEngine.insertObject("targetText", target);
			scriptEngine.runCode(scripts);
			return String.valueOf("runScript(targetText)");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.print("Error", true);
			Controller.println(" : " + e.getMessage());
			return target;
		}
		finally
		{
			try
			{
				scriptEngine.close();
			}
			catch(Exception e)
			{
				
			}
		}
	}
	/**
	 * <p>Return true if str is "true", "yes", "y", "1".</p>
	 * <p>Return false if str is "false", "no", "n", "0".</p>
	 * <p>In this case, "TRUE", "YES", and "Y" also can convert to true.</p>
	 * 
	 * <p>str 이 true, yes, y 또는 1이라면 true를 반환합니다.</p>
	 * <p>str 이 false, no, n 또는 0이라면 false를 반환합니다.</p>
	 * <p>이 메소드는 대소문자를 구분하지 않습니다.</p>
	 * 
	 * @param str : may be boolean
	 * @return parsed boolean
	 */
	public static boolean parseBoolean(String str)
	{
		if(str == null) throw new NullPointerException("Cannot parse " + "null" + " to the boolean value.");
		String mayBeBoolean = str.trim();
		
		if(mayBeBoolean.equalsIgnoreCase("true") || mayBeBoolean.equalsIgnoreCase("yes")
				|| mayBeBoolean.equalsIgnoreCase("y") || mayBeBoolean.equalsIgnoreCase("1"))
		{
			return true;
		}
		else if(mayBeBoolean.equalsIgnoreCase("false") || mayBeBoolean.equalsIgnoreCase("no")
				 || mayBeBoolean.equalsIgnoreCase("n") || mayBeBoolean.equalsIgnoreCase("0"))
		{
			return false;
		}
		else throw new NumberFormatException("Cannot parse " + str + " to the boolean value.");
	}

	/**
	 * 
	 * <p>Return programming language syntax table that are already prepared now.</p>
	 * 
	 * <p>이미 준비되어 있는 프로그래밍 언어 문법 테이블을 반환합니다.
	 * 상수값을 넣으면 그에 대한 언어 테이블이 반환됩니다.</p>
	 * 
	 * <p>Constant values that are available<br>
	 * SYNTAX_JAVA : Java<br>
	 * SYNTAX_JAVA_BUFFER : Java (StringBuffer)<br>
	 * SYNTAX_JAVASCRIPT : Javascript</p>
	 * 
	 * @param syntax : constant value of programming language. 프로그래밍 언어에 대한 상수값
	 * @return Programming Language Syntax Table 프로그래밍 언어 문법 테이블
	 */
	public static Syntax basicSyntax(int syntax)
	{		
		Syntax results = new Syntax();
		switch(syntax)
		{
		case Controller.SYNTAX_JAVA:
			results.put("name", "JAVA");
			results.put("firstString", "");
			results.put("lastString", "");
			results.put("classDeclarator", "class");
			results.put("blockStarter", "{");
			results.put("blockEachLine", "   ");
			results.put("blockEnder", "}");
			results.put("methodStarter", "public static");
			results.put("methodReturnType", "String");
			results.put("returnStarter", "return ");
			results.put("returnEnder", "");
			results.put("lineEnder", ";");
			results.put("substitutor", "=");
			results.put("quotes", "\"");
			results.put("lineSkip", "\\n");
			results.put("concatStarter", " + ");
			results.put("concatEnder", "");
			results.put("functionParameterStarter", "(");
			results.put("functionParameterEnder", ")");
			results.put("callByReferenceParameterType", "");
			results.put("callByReferenceParameter", "");
			results.put("variableDeclare", "String");
			results.put("strcpys", "strcpy");
			results.put("returnGetter", "");
			results.put("newStringInit", "\"\"");
			results.put("callByReference", String.valueOf(false));
			results.put("concat_type", String.valueOf(Controller.CONCAT_NORMAL));
			results.put("create_class", String.valueOf(true));
			results.put("escape_need_normal", String.valueOf(false));
			results.put("escape_need_double", String.valueOf(true));
			break;
		case Controller.SYNTAX_JAVA_BUFFER:
			results.put("name", "JAVA (StringBuffer)");
			results.put("firstString", "");
			results.put("lastString", "");
			results.put("classDeclarator", "class");
			results.put("blockStarter", "{");
			results.put("blockEachLine", "   ");
			results.put("blockEnder", "}");
			results.put("methodStarter", "public static");
			results.put("methodReturnType", "String");
			results.put("returnStarter", "return ");
			results.put("returnEnder", "");
			results.put("lineEnder", ";");
			results.put("substitutor", "=");
			results.put("quotes", "\"");
			results.put("lineSkip", "\\n");
			results.put("concatStarter", ".append(");
			results.put("concatEnder", ")");
			results.put("functionParameterStarter", "(");
			results.put("functionParameterEnder", ")");
			results.put("callByReferenceParameterType", "");
			results.put("callByReferenceParameter", "");
			results.put("variableDeclare", "StringBuffer");
			results.put("strcpys", "strcpy");
			results.put("returnGetter", ".toString()");
			results.put("newStringInit", "new StringBuffer(\"\")");
			results.put("callByReference", String.valueOf(false));
			results.put("concat_type", String.valueOf(Controller.CONCAT_NORMAL));
			results.put("create_class", String.valueOf(true));
			results.put("escape_need_normal", String.valueOf(false));
			results.put("escape_need_double", String.valueOf(true));
			break;
		case Controller.SYNTAX_JAVASCRIPT:
			results.put("name", "JavaScript");
			results.put("firstString", "");
			results.put("lastString", "");
			results.put("classDeclarator", "class");
			results.put("blockStarter", "{");
			results.put("blockEachLine", "   ");
			results.put("blockEnder", "}");
			results.put("methodStarter", "function");
			results.put("methodReturnType", "");
			results.put("returnStarter", "return ");
			results.put("returnEnder", "");
			results.put("lineEnder", ";");
			results.put("substitutor", "=");
			results.put("quotes", "'");
			results.put("lineSkip", "\\n");
			results.put("concatStarter", " + ");
			results.put("concatEnder", "");
			results.put("functionParameterStarter", "(");
			results.put("functionParameterEnder", ")");
			results.put("callByReferenceParameterType", "");
			results.put("callByReferenceParameter", "");
			results.put("variableDeclare", "var");
			results.put("strcpys", "strcpy");
			results.put("returnGetter", "");
			results.put("newStringInit", "'" + "'");
			results.put("callByReference", String.valueOf(false));
			results.put("concat_type", String.valueOf(Controller.CONCAT_NORMAL));
			results.put("create_class", String.valueOf(false));
			results.put("escape_need_normal", String.valueOf(true));
			results.put("escape_need_double", String.valueOf(false));
			break;
		}
		
		return results;
	}

	/**
	 * <p>Return basic string tables. Locale ko, en are available.</p>
	 * 
	 * <p>기본 스트링 테이블을 반환합니다.</p>
	 * 
	 * @return string tables
	 */
	public static Map<String, StringTable> defaultLanguages()
	{
		Hashtable<String, StringTable> results = new Hashtable<String, StringTable>();
		StringTable tables;
		
		tables = new DefaultStringTable();
		tables.put("localename", "ko");
		tables.put("localename2", "ko-KR");
		tables.put("localename_formal", "한글");
		tables.put("Method Converter", "메소드 변환기");
		tables.put("Save from...", "저장할 위치 지정");
		tables.put("Save", "저장");
		tables.put("...", "...");
		tables.put("Load from...", "불러올 파일 선택");
		tables.put("Load", "불러오기");
		tables.put("Exit", "종료");
		tables.put("Close", "닫기");
		tables.put("Add", "추가");
		tables.put("Remove", "제거");
		tables.put("New parameter is added.", "새로운 매개 변수가 추가되었습니다.");	
		tables.put("Key", "키");
		tables.put("Value", "값");
		tables.put("There is no helps about this module.", "이 모듈에 대한 도움말을 찾을 수 없습니다.");		
		tables.put("Class", "클래스");
		tables.put("Method", "메소드");
		tables.put("Variable", "변수");
		tables.put("Convert", "변환");
		tables.put("File", "파일");
		tables.put("Undo", "되돌리기");
		tables.put("Loading...", "불러오는 중...");
		tables.put("Saving...", "저장하는 중...");
		tables.put("Converting...", "변환하는 중...");
		tables.put("Load complete", "불러오기 완료");
		tables.put("Save complete", "저장 완료");
		tables.put("Convert complete", "변환 완료");
		tables.put("Tool", "도구");
		tables.put("Preference", "환경 설정");
		tables.put("Theme", "테마");
		tables.put("Clear", "비우기");
		tables.put("Copy", "복사");
		tables.put("Error", "오류");
		tables.put("Locale", "언어");
		tables.put("Help", "도움말");		
		tables.put("Memory", "메모리");
		tables.put("Level of saving options and packages", "옵션, 패키지 자동 저장 수준");
		tables.put("Close selected thread", "선택한 쓰레드 닫기 (해당 기능이 꺼집니다)");		
		tables.put("Converting Module (.module)", "텍스트 변환 모듈 (.module)");
		tables.put("Converting Module binary file (.bmodule)", "텍스트 변환 모듈 바이너리 파일 (.bmodule)");		
		tables.put("Converting Module xml file (.xmodule)", "텍스트 변환 모듈 XML 파일 (.xmodule)");
		tables.put("Converting Module binary compressed file (.bzmodule)", "압축된 텍스트 변환 모듈 바이너리 파일 (.bzmodule)");
		tables.put("Converting Module xml compressed file (.xzmodule)", "압축된 텍스트 변환 모듈 XML 파일 (.xzmodule)");
		tables.put("compress", "압축");
		tables.put("decompress", "압축 해제");
		tables.put("Module", "모듈");
		tables.put("Name", "이름");
		tables.put("Option", "옵션");
		tables.put("Editor", "편집기");
		tables.put("Authority code (Optional)", "인증 코드 (선택)");
		tables.put("Code executed at closing", "종료 시 실행할 코드");
		tables.put("Use test functions", "테스트 중인 기능 사용");
		tables.put("Refresh", "새로 고침");
		tables.put("Delete", "삭제");
		tables.put("Accept", "확인");
		tables.put("Package", "패키지");
		tables.put("Receive", "수신");
		tables.put("Send", "송신");
		tables.put("Title", "제목");
		tables.put("Nickname", "닉네임");
		tables.put("Encrypted", "암호화된");
		tables.put("Console", "콘솔");
		tables.put("Run", "실행");
		tables.put("Parameter", "매개 변수");
		tables.put("Security Level", "보안 수준");
		tables.put("Binary", "이진");
		tables.put("Text", "텍스트");
		tables.put("Converter", "변환기");
		tables.put("User defined", "사용자 정의");
		tables.put("Some works needs to your allows because this works are probability dangerous.", "잠재적으로 위험한 작업을 수행하려 합니다. 수행하시겠습니까?");
		tables.put("Input URL to download module", "다운로드할 모듈이 있는 URL");
		tables.put("Module list", "모듈");
		tables.put("Do you want to download module from following URL?", "다음 URL에서 모듈을 다운로드받으려 합니다.");
		tables.put("Do you want to use this module?", "이 모듈을 사용하시겠습니까?");
		tables.put("Please check the url is correct.", "URL을 제대로 입력하였는지 확인해 주세요.");
		tables.put("Do you want to turn on the auto saving?", "자동 저장 옵션을 켜시겠습니까? (켜지 않으면 다운로드한 모듈이 저장되지 않습니다.)");		
		tables.put("Please input IP address to send.", "상대방 IP 주소를 입력해 주세요.");
		tables.put("Please input nickname.", "패키지에 넣을 닉네임을 입력해 주세요. 입력한 닉네임의 해시값만 전송됩니다.");
		tables.put("Successful", "성공");		
		tables.put("Parameter Agent", "매개 변수 도우미");
		tables.put("Receiving realtime", "패키지 실시간 수신");
		tables.put("Fail to send package.", "패키지를 보내는 데 실패하였습니다.");
		tables.put("No one connect here long time.", "긴 시간 동안 아무도 접속하지 않았습니다.");
		tables.put("Success to send !", "패키지를 전송하였습니다.");
		tables.put("Use online contents", "온라인 컨텐츠 사용");
		tables.put("Multiples", "다수 모듈 사용");
		tables.put("Align", "정렬");
		tables.put("New", "새로 만들기");
		tables.put("About", "이 프로그램은...");
		tables.put("Method Converter", "메소드 변환기");		
		tables.put("Level 0 : No check", "레벨 0 : 확인하지 않음");
		tables.put("Level 1 : Only check area counts", "레벨 1 : 입력한 토큰 수가 영역 수보다 많을 때 경고");
		tables.put("Level 2 : Check all", "레벨 2 : 영역 수와 입력한 토큰 수가 다르면 경고");
		tables.put("Make SQL query from JDBC log", "JDBC 로그로부터 SQL 쿼리문 생성");
		
		tables.put("Do you want to delete?", "삭제하시겠습니까?");
		tables.put("Target", "대상");
		tables.put("Count", "수");
		tables.put("Try to delete", "삭제 시도");
		tables.put("Daemon", "데몬");
		tables.put("Login", "접속");
		tables.put("Disconnect", "접속 해제");
		tables.put("Daemon Client", "데몬 접속기");
		tables.put("Please input the port number", "사용할 포트 번호를 입력해 주세요");
		tables.put("Please input the root ID", "허용할 ID를 입력해 주세요");
		tables.put("Please input the root Password", "허용할 비밀번호를 입력해 주세요");
		tables.put("Please input the login key", "로그인 키를 입력해 주세요");
		tables.put("Optional", "선택 사항");
		tables.put("Please see following", "아래 사항을 다시 검토해 주세요");
		tables.put("Daemon is already running", "데몬이 이미 작동하고 있습니다");
		tables.put("Do you want to close it?", "이 것을 닫을까요?");
		
		
		tables.put("Other user who know ID and password will be able to access here", "ID와 비밀번호를 알고 있는 다른 사용자가 이 시스템을 사용할 수 있게 됩니다.");
		tables.put("Do you want to run daemon?", "데몬을 실행하시겠습니까?");
		
		tables.put("Send to the convert area", "변환 탭으로 옮기기");
		tables.put("Text is copied to the clipboard.", "클립보드에 텍스트가 복사되었습니다.");
		tables.put("Please select package and retry.", "먼저 패키지를 선택하고 다시 시도해 보세요.");
		tables.put("Please select module and retry.", "먼저 모듈을 선택하고 다시 시도해 보세요.");
		tables.put("You didn\'t select where to save, and what name to save.", "저장할 파일 경로를 지정하여야 합니다.");
		tables.put("Converted text is saved at", "변환된 텍스트가 다음 장소에 저장됨");
		tables.put("Try to select language", "언어 선택");
		tables.put("Cannot found the target file like", "선택한 파일을 찾을 수 없습니다");
		tables.put("UDP protocol do not check the message is successfully arrived.", "UDP 프로토콜은 패키지가 성공적으로 전달되었는지 확인하지 않습니다.");
		tables.put("Save and restart this program to apply changes.", "저장 후 재시작해야 설정이 적용됩니다.");
		tables.put("Do you want to use online contents?", "온라인 모듈과 네트워크 기능을 사용할까요?");
		results.put(tables.get("localename"), tables);
		
		tables = new DefaultStringTable();
		tables.put("localename", "en");
		tables.put("localename2", "en-US");
		tables.put("localename_formal", "English");
		tables.put("Method Converter", "Method Converter");
		tables.put("Save from...", "Save from...");
		tables.put("Save", "Save");
		tables.put("...", "...");
		tables.put("Load from...", "Load from...");
		tables.put("Load", "Load");
		tables.put("Exit", "Exit");
		tables.put("Close", "Close");
		tables.put("Add", "Add");
		tables.put("Remove", "Remove");
		tables.put("New parameter is added.", "New parameter is added.");
		tables.put("Key", "Key");
		tables.put("Value", "Value");
		tables.put("There is no helps about this module.", "There is no helps about this module.");
		tables.put("Copy", "Copy");
		tables.put("Class", "Class");
		tables.put("Method", "Method");
		tables.put("Variable", "Variable");	
		tables.put("Convert", "Convert");
		tables.put("Undo", "Undo");
		tables.put("File", "File");
		tables.put("Loading...", "Loading...");
		tables.put("Saving...", "Saving...");
		tables.put("Title", "Title");
		tables.put("Nickname", "Nickname");
		tables.put("Encrypted", "Encrypted");
		tables.put("Converting...", "Converting...");
		tables.put("Load complete", "Load complete");
		tables.put("Save complete", "Save complete");
		tables.put("Convert complete", "Convert complete");
		tables.put("Tool", "Tool");
		tables.put("Preference", "Preference");
		tables.put("Parameter Agent", "Parameter Agent");
		tables.put("Theme", "Theme");
		tables.put("Error", "Error");
		tables.put("Locale", "Locale");
		tables.put("Help", "Help");
		tables.put("Package", "Package");
		tables.put("Parameter", "Parameter");
		tables.put("Use online contents", "Use online contents");
		tables.put("Use test functions", "Use test functions");
		tables.put("Align", "Align");
		tables.put("Send", "Send");
		tables.put("Receive", "Receive");
		tables.put("Refresh", "Refresh");
		tables.put("Delete", "Delete");
		tables.put("Clear", "Clear");
		tables.put("Accept", "Accept");
		tables.put("Console", "Console");
		tables.put("Run", "Run");
		tables.put("Memory", "Memory");
		tables.put("Binary", "Binary");
		tables.put("Converter", "Converter");
		tables.put("User defined", "User defined");
		tables.put("Please select module and retry.", "Please select module and retry.");
		tables.put("Level of saving options and packages", "Level of saving options and packages");
		tables.put("Close selected thread", "Close selected thread");
		tables.put("Security Level", "Security Level");
		tables.put("Text is copied to the clipboard.", "Text is copied to the clipboard.");
		tables.put("Please select package and retry.", "Please select package and retry.");
		tables.put("Send to the convert area", "Send to the convert area");
		tables.put("Receiving realtime", "Receiving realtime");
		tables.put("Converting Module (.module)", "Converting Module (.module)");
		tables.put("Converting Module binary file (.bmodule)", "Converting Module binary file (.bmodule)");
		tables.put("Converting Module xml file (.xmodule)", "Converting Module xml file (.xmodule)");
		tables.put("Converting Module binary compressed file (.bzmodule)", "Converting Module binary compressed file (.bzmodule)");
		tables.put("Converting Module xml compressed file (.xzmodule)", "Converting Module xml compressed file (.xzmodule)");
		tables.put("Module", "Module");
		tables.put("Name", "Name");
		tables.put("Option", "Option");
		tables.put("Editor", "Editor");
		tables.put("UDP protocol do not check the message is successfully arrived.", "UDP protocol do not check the message is successfully arrived.");
		tables.put("Authority code (Optional)", "Authority code (Optional)");
		tables.put("Do you want to use online contents?", "Do you want to use online contents?");
		tables.put("Code executed at closing", "Code executed at closing");
		tables.put("Please input IP address to send.", "Please input IP address to send.");
		tables.put("Please input nickname.", "Please input nickname.");
		tables.put("Successful", "Successful");
		tables.put("Some works needs to your allows because this works are probability dangerous.", "Some works needs to your allows because this works are probability dangerous.");
		tables.put("Input URL to download module", "Input URL to download module");
		tables.put("Module list", "Module list");
		tables.put("Do you want to download module from following URL?", "Do you want to download module from following URL?");
		tables.put("Do you want to use this module?", "Do you want to use this module?");
		tables.put("Please check the url is correct.", "Please check the url is correct.");
		tables.put("Do you want to turn on the auto saving?", "Do you want to turn on the auto saving?");
		
		tables.put("About", "About");
		tables.put("New", "New");
		tables.put("You didn\'t select where to save, and what name to save.", "You didn\'t select where to save, and what name to save.");
		tables.put("Method Converter", "Method Converter");
		tables.put("Converted text is saved at", "Converted text is saved at");
		tables.put("Try to select language", "Try to select language");
		tables.put("Cannot found the target file like", "Cannot found the target file like");
		tables.put("Save and restart this program to apply changes.", "Save and restart this program to apply changes.");
		results.put(tables.get("localename"), tables);
		
		return results;
	}

	/**
	 * <p>Take string table object with locale name</p>
	 * 
	 * <p>로캘(언어) 이름을 입력하여 스트링 테이블을 반환합니다.</p>
	 * 
	 * @param locale : locale name 로캘 이름
	 * @return 스트링 테이블
	 */
	public static StringTable toLanguage(String locale)
	{		
		StringTable tables = null;
		
		Controller.println(Controller.getString("Try to select language") + " : " + locale);
		
		Set<String> others = Controller.otherStringTables.keySet();
		for(String k : others)
		{
			Controller.println("key : " + k + ", " + Controller.otherStringTables.get(k).get("File"));
			if(k.equalsIgnoreCase(locale))				
			{				
				tables = Controller.otherStringTables.get(k);
				break;
			}
		}
		
		return tables;
	}

	/**
	 * <p>Execute auto-saving function.</p>
	 * 
	 * <p>자동 저장 기능을 실행합니다.</p>
	 * 
	 * @param level : Setting of auto-saving
	 */
	public static void saveOptionByLevel(int level)
	{
		if(level <= 0) return;
		switch(level)
		{
		case 1:
			try
			{
				Controller.saveOption();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		case 2:
			try
			{
				Controller.savePackages();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Statics.saveOptionByLevel(1);
			break;
		case 3:
			try
			{
				Controller.saveModules();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Statics.saveOptionByLevel(2);
			break;
		case 4:
			try
			{
				Controller.saveStringTables();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Statics.saveOptionByLevel(3);
			break;
		default:
			Statics.saveOptionByLevel(4);
			break;
		}
	}
	
	/**
	 * <p>Remove comments on parameter text.</p>
	 * 
	 * <p>파라미터 텍스트에서 주석을 제거합니다.</p>
	 * 
	 * @param befores : text
	 * @return text without comments
	 */
	public static String removeComment(String befores)
	{
		RemoveCommentModule rmCom = new RemoveCommentModule();
		Hashtable<String, String> parameters = new Hashtable<String, String>();
		parameters.put("option", "toParams");
		String results = rmCom.convert(befores, parameters);
		rmCom.close();
		return results;
	}
	
	/**
	 * <p>Return true if the character is number.</p>
	 * 
	 * <p>해당하는 글자가 숫자 형태이면 true를 반환합니다.</p>
	 * 
	 * @param ch : character
	 * @return true if the character is number
	 */
	public static boolean isNumber(char ch)
	{
		if(ch == '0'
		|| ch == '1'
		|| ch == '2'
		|| ch == '3'
		|| ch == '4'
		|| ch == '5'
		|| ch == '6'
		|| ch == '7'
		|| ch == '8'
		|| ch == '9') return true;
		else return false;
	}
	
	/**
	 * <p>Return true if the character is quotion symbol.</p>
	 * 
	 * <p>해당하는 글자가 따옴표 형태이면 true를 반환합니다.</p>
	 * 
	 * @param ch : character
	 * @return true if the character is quotion symbol
	 */
	public static boolean isQuote(char ch)
	{
		if(ch == '\''
		|| ch == '"') return true;
		else return false;
	}
	
	/**
	 * <p>Return true if the character is blacket symbol.</p>
	 * 
	 * <p>해당하는 글자가 괄호 형태이면 true를 반환합니다.</p>
	 * 
	 * @param ch : character
	 * @return true if the character is blacket symbol
	 */
	public static boolean isBlacket(char ch)
	{
		if(ch == '(' || ch == ')'
	    || ch == '{' || ch == '}'
	    || ch == '[' || ch == ']') return true;
		else return false;
	}
	
	/**
	 * <p>Return true if the character is space symbol.</p>
	 * 
	 * <p>해당하는 글자가 빈칸 형태이면 true를 반환합니다.</p>
	 * 
	 * @param ch : character
	 * @return true if the character is space symbol
	 */
	public static boolean isSpace(char ch)
	{
		if(ch == ' ' || ch == '\n' || ch == '\t') return true;
		else return false;
	}
	
	/**
	 * <p>Return true if the character is alphabet symbol.</p>
	 * 
	 * <p>해당하는 글자가 알파벳 형태이면 true를 반환합니다.</p>
	 * 
	 * @param ch : character
	 * @return true if the character is alphabet symbol
	 */
	public static boolean isAlphabet(char ch)
	{
		if(ch == 'a' || ch == 'k' || ch == 'u'
		|| ch == 'b' || ch == 'l' || ch == 'v'
		|| ch == 'c' || ch == 'm' || ch == 'w'
		|| ch == 'd' || ch == 'n' || ch == 'x'
		|| ch == 'e' || ch == 'o' || ch == 'y'
		|| ch == 'f' || ch == 'p' || ch == 'z'
		|| ch == 'g' || ch == 'q'
		|| ch == 'h' || ch == 'r'
		|| ch == 'i' || ch == 's'
		|| ch == 'j' || ch == 't'
		|| ch == 'A' || ch == 'K' || ch == 'U'
		|| ch == 'B' || ch == 'L' || ch == 'V'
		|| ch == 'C' || ch == 'M' || ch == 'W'
		|| ch == 'D' || ch == 'N' || ch == 'X'
		|| ch == 'E' || ch == 'O' || ch == 'Y'
		|| ch == 'F' || ch == 'P' || ch == 'Z'
		|| ch == 'G' || ch == 'Q'
		|| ch == 'H' || ch == 'R'
		|| ch == 'I' || ch == 'S'
		|| ch == 'J' || ch == 'T') return true;
		else return false;
	}
	
	/**
	 * <p>Return true if the character is operation symbol.</p>
	 * 
	 * <p>해당하는 글자가 연산자 형태이면 true를 반환합니다.</p>
	 * 
	 * @param ch : character
	 * @return true if the character is operation symbol
	 */
	public static boolean isOperationSymbol(char ch)
	{
		if(ch == '+'
		|| ch == '-'
		|| ch == '*'
		|| ch == '/'
		|| ch == '='
		|| ch == '!'
		|| ch == '<'
		|| ch == '>'
		|| ch == '%') return true;
		else return false;
	}
	
	/**
	 * <p>Return true if the system locale is korean.</p>
	 * 
	 * <p>시스템 로캘이 한국어로 되어 있으면 true 를 반환합니다.</p>
	 * 
	 * 
	 * @return true if the system locale is korean
	 */
	public static boolean isKoreanLocale()
	{
		String locale = System.getProperty("user.language");
		return isKoreanLocale(locale);
	}
	/**
	 * <p>Return true if the locale is korean.</p>
	 * 
	 * <p>로캘이 한국어로 되어 있으면 true 를 반환합니다.</p>
	 * 
	 * @param locale : locale
	 * @return true if the system locale is korean
	 */
	public static boolean isKoreanLocale(String locale)
	{
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
