package hjow.msq.ui;

import java.awt.Component;
import java.io.IOException;

public interface HasComponent extends Runnable
{
	public Component getComponent();
	public String getName();
	public long getUniqueKey();
	public boolean canBeRun();
	public void refreshContent() throws IOException;
	public void close();
}
