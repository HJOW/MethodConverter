import hjow.methodconverter.Controller;

/**
 * <p>This class have main method which run binary converter.</p>
 * 
 * <p>이 클래스에는 이진 변환기를 실행하는 main 메소드가 포함되어 있습니다.
 * <br>이 클래스를 메인으로 하여 실행하면 GUI로 이진 변환기가 실행됩니다.</p>
 * 
 * 
 * <p>Run the binary converter.</p>
 * 
 * <p>바이너리 컨버터를 실행합니다.</p>
 * 
 * @author HJOW
 *
 */
public class RunBinaryConverter
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
		String[] newArgs = new String[2 + args.length];
		newArgs[0] = "--gui true";
		newArgs[1] = "--binary true";
		for(int i=0; i<args.length; i++)
		{
			newArgs[i + 2] = args[i];
		}
		Controller.main(newArgs);
	}
}