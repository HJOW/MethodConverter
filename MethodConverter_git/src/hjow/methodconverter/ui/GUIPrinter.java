package hjow.methodconverter.ui;
import hjow.convert.javamethods.ConsoleContainer;
import hjow.methodconverter.Controller;
import hjow.methodconverter.Printer;
import hjow.methodconverter.Statics;

/**
 * <p>GUI Printer class.</p>
 * 
 * 
 * <p>GUI 기반의 Printer 클래스</p>
 * 
 * 
 * @author HJOW
 *
 */
public abstract class GUIPrinter extends Printer implements IsComponent
{
	private ConsoleContainer console = new ConsoleContainer();
	protected boolean printOnTerminal = true;
	/**
	 * <p>Return input field text.</p>
	 * 
	 * <p>입력 필드에 있는 텍스트 값을 반환합니다.</p>
	 * 
	 * @return text on the input field
	 */
	public abstract String getFieldText();
	
	/**
	 * <p>Input text on the input field.</p>
	 * 
	 * <p>입력 필드에 텍스트를 입력합니다.</p>
	 * 
	 * @param str : text
	 */
	public abstract void setFieldText(String str);	 
	
	/**
	 * <p>This is called when user input text on the script field.</p>
	 * 
	 * <p>이 메소드는 사용자가 입력 필드에 텍스트를 입력했을 때 호출됩니다.</p>
	 * 
	 * @param str
	 */
	public void runScript(String str)
	{
		if(str == null) return;
		try
		{
			newLine();
			print(console.pwd() + " > " + str);
			Object results = Controller.runScript(str);
			if(results != null)
			{				
				newLine();
				print(String.valueOf(results));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			newLine();
			print(Statics.fullErrorMessage(e));
		}
		setFieldText("");
	}
	
	/**
	 * 
	 * <p>Enable, or disable all components.</p>
	 * 
	 * <p>컴포넌트를 모두 활성화하거나 비활성화합니다.</p>
	 * 
	 * @param l : If this is true, all components will be enabled.
	 */
	public abstract void enableAll(boolean l);
}
