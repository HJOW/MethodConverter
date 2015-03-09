package hjow.network;

import hjow.convert.module.HashModule;
import hjow.convert.module.UserDefinedModule;
import hjow.methodconverter.Controller;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.StringTokenizer;

/**
 * <p>Package object to send message.</p>
 * 
 * <p>패키지 객체입니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class NetworkPackage implements Serializable, Comparable<NetworkPackage>, FileSavable
{
	private static final long serialVersionUID = -7686076962260179243L;
	private Long packId = new Long(((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long) (Math.random() * (Long.MAX_VALUE / 1000000000))));
	private Long time;
	private String name;
	private String sender;
	private String encrypted;
	private boolean read = false;
	
	/**
	 * <p>Create new package object.</p>
	 * 
	 * <p>새 패키지 객체를 생성합니다.</p>
	 */
	public NetworkPackage()
	{
		super();
		randomId();
		resetTime();
		setSender();
	}
	
	/**
	 * <p>Create new package object.</p>
	 * 
	 * <p>새 패키지 객체를 생성합니다.</p>
	 * 
	 * @param name : package name (title)
	 */
	public NetworkPackage(String name)
	{
		super();
		randomId();
		resetTime();
		setName(name);
		setSender();
	}
	/**
	 * <p>Insert now time in this package.</p>
	 * 
	 * <p>현재 시간을 패키지에 삽입합니다.</p>
	 */
	public void resetTime()
	{
		time = System.currentTimeMillis();
	}
	
	/**
	 * <p>Return time information in this package.</p>
	 * 
	 * <p>이 패키지에 있는 시간 정보를 반환합니다.</p>
	 * 
	 * @return time value
	 */
	public Long getTime()
	{
		return time;
	}
	
	/**
	 * <p>Set time information in this package.</p>
	 * 
	 * <p>패키지에 시간 정보를 삽입합니다.</p>
	 * 
	 * @param time : time value
	 */
	public void setTime(Long time)
	{
		this.time = new Long(time.longValue());
	}
	
	/**
	 * <p>Return name(title) of this package.</p>
	 * 
	 * <p>이 패키지의 이름(제목)을 반환합니다.</p>
	 * 
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * <p>Set name(title) of this package.</p>
	 * 
	 * <p>이 패키지의 이름(제목)을 입력합니다.</p>
	 * 
	 * @param name : name(title) of this package
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * <p>Return sender IP address.</p>
	 * 
	 * <p>보낸 이의 IP 주소를 반환합니다.</p>
	 * 
	 * @return sender IP address
	 */
	public String getSender()
	{
		return sender;
	}
	
	/**
	 * <p>Set sender IP address.</p>
	 * 
	 * <p>보낸 이의 IP 주소를 입력합니다.</p>
	 * 
	 * 
	 * @param sender : new IP address
	 */
	public void setSender(String sender)
	{
		this.sender = sender;
	}
	
	/**
	 * <p>Set sender IP address to this system's IP.</p>
	 * 
	 * <p>현재 시스템의 IP 주소를 보낸 이의 주소로 입력합니다.</p>
	 * 
	 */
	public void setSender()
	{
		try
		{
			setSender(InetAddress.getLocalHost().toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public int compareTo(NetworkPackage o)
	{
		return this.getTime().compareTo(o.getTime());
	}

	/**
	 * <p>Ramdomize unique ID.</p>
	 * 
	 * <p>고유 식별용 ID를 임의로 설정합니다.</p>
	 */
	public void randomId()
	{
		packId = new Long((long) (Math.random() * (Long.MAX_VALUE / 10.0)));
	}
	
	/**
	 * <p>Return unique ID.</p>
	 * 
	 * <p>고유 식별 ID를 반환합니다.</p>
	 * 
	 * @return unique ID
	 */
	public Long getPackId()
	{
		return packId;
	}

	/**
	 * <p>Set unique ID.</p>
	 * 
	 * <p>고유 식별 ID를 변경합니다.</p>
	 * 
	 * @param packId : unique ID value
	 */
	public void setPackId(Long packId)
	{
		this.packId = packId;
	}

	/**
	 * <p>Return true if this package is opened.</p>
	 * 
	 * <p>이 패키지가 읽혀졌다면 true를 반환합니다.</p>
	 * 
	 * @return true if this package is opened
	 */
	public boolean isRead()
	{
		return read;
	}

	/**
	 * <p>Set this package read.</p>
	 * 
	 * <p>이 패키지를 읽음으로 표시합니다.</p>
	 * 
	 * @param read : Set or reset this package as read
	 */
	public void setRead(boolean read)
	{
		this.read = read;
	}
	
	/**
	 * <p>Set nickname.</p>
	 * 
	 * <p>닉네임을 설정합니다.</p>
	 * 
	 * @param original : nickname
	 */
	public void setEncryptedInput(String original)
	{
		if(original == null) return;
		encrypted = new HashModule().encrypt(original.trim(), Controller.getNicknameEncoding());
	}
	
	/**
	 * <p>Check the nickname is text you know.</p>
	 * 
	 * <p>닉네임이 당신이 알고 있는 텍스트인지 여부를 반환합니다.</p>
	 * 
	 * @param text : nickname you know
	 * @return true if the text is true nickname
	 */
	public boolean isCorrectEncrypted(String text)
	{
		if(text == null) return false;
		if(encrypted == null) return true;
		String gets = new HashModule().encrypt(text.trim(), Controller.getNicknameEncoding());
		return encrypted.equals(gets);
	}

	/**
	 * <p>Return encrypted nickname.</p>
	 * 
	 * <p>암호화된 닉네임을 반환합니다.</p>
	 * 
	 * @return encrypted nickname
	 */
	public String getEncrypted()
	{
		return encrypted;
	}

	/**
	 * <p>Set encrypted nickname.</p>
	 * 
	 * <p>암호화된 닉네임을 입력합니다.</p>
	 * 
	 * @param encrypted : encrypted nickname
	 */
	public void setEncrypted(String encrypted)
	{
		this.encrypted = encrypted;
	}
	
	/**
	 * <p>Create package object from text.</p>
	 * 
	 * <p>텍스트로부터 패키지 객체를 만듭니다.</p>
	 * 
	 * @param bytes : datas
	 * @return package object
	 */
	public static NetworkPackage toPackage(byte[] bytes) throws Exception
	{
		return toPackage(new String(bytes, "UTF-8"));
	}
	/**
	 * <p>Create package object from text.</p>
	 * 
	 * <p>텍스트로부터 패키지 객체를 만듭니다.</p>
	 * 
	 * @param text : datas
	 * @return package object
	 */
	public static NetworkPackage toPackage(String text)
	{
		StringTokenizer lineToken = new StringTokenizer(text, "\n");
		try
		{
			String lines;
			int type = -1; // 0 : Close, 1 : Object, 2 : String
			long packId, time;
			String name, sender, encrypted, contents;
			
			// First line will be skipped
			lineToken.nextToken();
			
			// Second line decide type of package
			lines = lineToken.nextToken().trim();
			
			if(lines.equalsIgnoreCase("Close")) type = 0;
			else if(lines.equalsIgnoreCase("Object")) type = 1;
			else if(lines.equalsIgnoreCase("String")) type = 2;
			else if(lines.equalsIgnoreCase("Success")) type = 3;
			else if(lines.equalsIgnoreCase("Module")) type = 4;
			else return null;
			
			// Third line decide package ID
			lines = lineToken.nextToken().trim();
			packId = Long.parseLong(lines);
			
			// Fourth line is time data
			lines = lineToken.nextToken().trim();
			time = Long.parseLong(lines);
			
			// Fifth line is name
			lines = lineToken.nextToken().trim();
			name = lines;
			
			// Sixth line is sender IP address
			lines = lineToken.nextToken().trim();
			sender = lines;
			
			// Seventh line is encrypted nickname
			lines = lineToken.nextToken().trim();
			encrypted = lines;
			
			// Other lines is contents
			contents = "";
			while(lineToken.hasMoreTokens())
			{
				lines = lineToken.nextToken();
				contents = contents + lines + "\n";
			}
			contents = contents.trim();
			
			NetworkPackage results = null;
			switch(type)
			{
			case 0:
				results = new ClosePackage();
				break;
			case 1:
				results = new ObjectPackage(contents, name);
				break;
			case 2:
				results = new StringPackage(contents, name);
				break;
			case 3:
				results = new SuccessReceivedPackage();
				((SuccessReceivedPackage) results).setSuccessReceivedPackageId(new Long(contents));
				break;
			case 4:
				results = new ModulePackage();
				((ModulePackage) results).setModule(new UserDefinedModule(contents));
				break;
			}
			if(results == null) return null;
			results.setPackId(new Long(packId));
			results.setTime(time);
			results.setName(name);
			results.setSender(sender);
			results.setEncrypted(encrypted);
			return results;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <p>Make text which has all data of this package.</p>
	 * 
	 * <p>패키지의 데이터를 모두 포함한 텍스트를 반환합니다.</p>
	 * 
	 * @return text
	 */
	public String serialize()
	{
		String results = "  \n";
		results = results + getPackageTypeName() + "\n";
		results = results + String.valueOf(packId) + "\n";
		results = results + String.valueOf(time) + "\n";
		results = results + String.valueOf(name) + "\n";
		results = results + String.valueOf(sender) + "\n";
		results = results + String.valueOf(encrypted) + "\n";		
		results = results + String.valueOf(getContents());
		
		return results;
	}
	
	/**
	 * <p>Return package type name.</p>
	 * 
	 * <p>패키지 종류 이름을 반환합니다.</p>
	 * 
	 * @return package type name
	 */
	public abstract String getPackageTypeName();
	
	/**
	 * <p>Return contents of this package.</p>
	 * 
	 * <p>패키지의 내용을 반환합니다.</p>
	 * 
	 * @return contents of this package
	 */
	public Serializable getContents()
	{
		return toString();
	}
	
	/**
	 * <p>Return contents of this package.</p>
	 * 
	 * <p>패키지의 내용을 반환합니다.</p>
	 * 
	 * @return contents of this package
	 */
	public byte[] getContentBytes()
	{
		try
		{
			return String.valueOf(serialize()).getBytes("UTF-8");
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
