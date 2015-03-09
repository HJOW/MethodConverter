package hjow.messenger;

import java.util.List;
import java.util.Vector;

import hjow.network.StringPackage;

public class MessengerPackage extends StringPackage
{
	private static final long serialVersionUID = -2665085520695254811L;
	private List<EncryptedInfo> encInfo = new Vector<EncryptedInfo>();
	public MessengerPackage()
	{
		super();
	}
	public List<EncryptedInfo> getEncInfo()
	{
		return encInfo;
	}
	public void setEncInfo(List<EncryptedInfo> encInfo)
	{
		this.encInfo = encInfo;
	}

}
