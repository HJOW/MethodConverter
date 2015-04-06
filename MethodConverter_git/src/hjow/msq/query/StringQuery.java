package hjow.msq.query;

import java.io.IOException;

public class StringQuery extends Query
{
	private static final long serialVersionUID = 6207678894143406311L;
	private String queryContent = "";
	
	public StringQuery()
	{
		super();
	}
	public StringQuery(String content)
	{
		super();
		this.queryContent = content;
	}
	
	@Override
	public String getQuery() throws IOException
	{
		return queryContent;
	}
	@Override
	public void setQuery(String content) throws IOException
	{
		queryContent = content;
	}
}
