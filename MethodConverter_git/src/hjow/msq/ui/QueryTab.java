package hjow.msq.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Random;

import hjow.methodconverter.swingconverter.LineNumberTextArea;
import hjow.msq.control.ErrorControl;
import hjow.msq.control.MainControl;
import hjow.msq.query.Query;

import javax.swing.JPanel;

public class QueryTab implements Serializable, HasComponent
{
	private static final long serialVersionUID = 6028056532278746729L;
	private Query query;	
	private String name;	
	private long uniqueKey = new Random().nextLong();
	
	private transient MainFrame superComp = null;
	
	private transient JPanel panel = new JPanel();
	private transient LineNumberTextArea area = new LineNumberTextArea();
	
	public QueryTab()
	{
		init();
	}
	public QueryTab(Query query, String name)
	{
		super();
		this.query = query;
		this.name = name;
		init();
	}
	
	public void close()
	{
		superComp = null;
		panel = null;		
		area = null;
	}
	public void init()
	{
		panel.setLayout(new BorderLayout());
		panel.add(area, BorderLayout.CENTER);
	}
	public Component getComponent()
	{
		return panel;
	}
	public Query getQuery()
	{
		return query;
	}
	public void setQuery(Query query)
	{
		this.query = query;
		try
		{
			area.setText(query.getQuery());
		}
		catch (IOException e)
		{
			ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
		}
	}
	public void refreshContent() throws IOException
	{
		area.setText(query.getQuery());
	}
	public JPanel getPanel()
	{
		return panel;
	}
	public void setPanel(JPanel panel)
	{
		this.panel = panel;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	@Override
	public long getUniqueKey()
	{
		return uniqueKey;
	}
	public MainFrame getSuperComp()
	{
		return superComp;
	}
	public void setSuperComp(MainFrame superComp)
	{
		this.superComp = superComp;
	}
	public void setUniqueKey(long uniqueKey)
	{
		this.uniqueKey = uniqueKey;
	}
	@Override
	public boolean canBeRun()
	{
		return true;
	}
	public void run()
	{
		try
		{
			MainControl.getControl().query(query);
		}
		catch (SQLException e)
		{
			ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
		}
		catch (IOException e)
		{
			ErrorControl.printErrorTrace(e, ErrorControl.ERROR_WARN_CONTINUE);
		}
	}
}
