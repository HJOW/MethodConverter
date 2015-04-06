package hjow.msq.control;

public class LogControl
{
	protected int level = 0;
	public void print(Object ob)
	{
		System.out.print(ob);
	}
	public void println()
	{
		System.out.println();
	}
	public void println(Object ob)
	{
		System.out.println(ob);
	}	
	public void print(Object ob, int level)
	{
		if(this.level <= level)
		{
			print(ob);
		}
	}
	public void println(Object ob, int level)
	{
		if(this.level <= level)
		{
			println(ob);
		}
	}
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
}
