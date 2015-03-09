package hjow.methodconverter.ui;

import java.util.StringTokenizer;

import hjow.network.SerializableObject;

/**
 * <p>Includes 3 integer values.</p>
 * 
 * <p>3개의 정수 값을 포함하는 클래스입니다.</p>
 * 
 * @author HJOW
 *
 */
public class RGB implements SerializableObject
{
	private static final long serialVersionUID = 3551121268480103362L;
	private int r, g, b;
	
	public RGB()
	{
		r = 0;
		g = 0;
		b = 0;
	}
	public RGB(int r, int g, int b)
	{
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public RGB(String data)
	{
		StringTokenizer andToken = new StringTokenizer(data.trim(), ",");
		StringTokenizer colonToken = new StringTokenizer(andToken.nextToken().trim(), ":");
		colonToken.nextToken();
		r = Integer.parseInt(colonToken.nextToken().trim());
		colonToken = new StringTokenizer(andToken.nextToken().trim(), ":");
		colonToken.nextToken();
		g = Integer.parseInt(colonToken.nextToken().trim());
		colonToken = new StringTokenizer(andToken.nextToken().trim(), ":");
		colonToken.nextToken();
		b = Integer.parseInt(colonToken.nextToken().trim());
	}

	public int getR()
	{
		return r;
	}

	public void setR(int r)
	{
		this.r = r;
	}

	public int getG()
	{
		return g;
	}

	public void setG(int g)
	{
		this.g = g;
	}

	public int getB()
	{
		return b;
	}

	public void setB(int b)
	{
		this.b = b;
	}
	@Override
	public String serialize()
	{
		return "r:" + r + ",g:" + g + ",b:" + b;
	}
}
