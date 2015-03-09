package hjow.messenger;

import hjow.methodconverter.Controller;

import java.io.Serializable;

public class EncryptedInfo implements Serializable
{
	private static final long serialVersionUID = -3155611470938899370L;
	private String algorithm = "AES";
	private String key = Controller.getGlobal_encrypt_key();
	public String getAlgorithm()
	{
		return algorithm;
	}
	public void setAlgorithm(String algorithm)
	{
		this.algorithm = algorithm;
	}
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
}
