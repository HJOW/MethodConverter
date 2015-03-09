/**
 * <p>This class have main method which run the program as default arguments.</p>
 * 
 * <p>이 클래스에는 기본 설정으로 프로그램을 실행하는 main 메소드가 포함되어 있습니다.
 * <br>이 클래스를 메인으로 하여 실행하면 GUI로 메소드 변환기가 실행됩니다.</p>
 * 
 * <p>Run the program.</p>
 * 
 * <p>프로그램을 실행합니다.</p>
 * 
 * <p>콘솔 상에서 사용하려는 경우 다음과 같이 여러 매개 변수를 부여해야 합니다.<br>
 * --gui : GUI로 실행할 지 console 로 실행할 지를 결정합니다. gui 혹은 console 을 값으로 사용합니다.<br>
 * --load : 불러올 텍스트 파일의 절대 경로를 지정합니다.<br>
 * --save : 결과를 저장할 절대 경로(파일 이름 포함)를 지정합니다.<br>
 * --class : 클래스 이름을 지정합니다.<br>
 * --method : 메소드 이름을 지정합니다.<br>
 * --variable : 변수 이름을 지정합니다.</p>
 * 
 * <p>예를 들어 다음과 같은 형식으로 사용합니다.</p>
 * 
 * <p>java -jar methodConverter.jar --gui console --load "C:\Users\HJOW\Desktop\1.txt" --save "C:\Users\HJOW\Desktop\Writer.java"  --class "Writer" --method "write" --variable "r"</p>
 * 
 * <p>이외에도, 이 클래스에는 "메소드" 문자열을 생성하기 위한 여러 메소드가 포함되어 있습니다.
 * 이 클래스는 당신이 원하는 문자열을 반환하는 자바 메소드 선언문을 생성하는 데 도움을 줍니다.</p>
 * 
 * @author HJOW
 *
 */
public class RunDefault
{
	/**
	 * <p>Main function.</p>
	 * 
	 * <p>메인 함수입니다.</p>
	 * 
	 * @param args : arguments
	 */
	public static void main(String[] args)
	{
		hjow.methodconverter.Controller.main(args);
	}
}
