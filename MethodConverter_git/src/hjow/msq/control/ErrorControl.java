package hjow.msq.control;

public class ErrorControl
{
	public static final int ERROR_JUST_EXIT = 0;
	public static final int ERROR_WARN_CONTINUE = 1;
	public static final int ERROR_WARN_EXIT = 2;
	public static final int ERROR_JUST_CONTINUE = 3;
	public static void printErrorTrace(Exception e, int afterAction)
	{
		e.printStackTrace();
	}
}
