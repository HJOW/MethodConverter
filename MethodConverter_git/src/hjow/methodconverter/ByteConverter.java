package hjow.methodconverter;

import hjow.convert.module.DecryptModule;
import hjow.convert.module.EncryptModule;
import hjow.convert.module.HashModule;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Vector;

/**
 * <p>This class object is used to convert byte array.</p>
 * 
 * <p>이 클래스 객체는 바이트 배열을 변환하는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class ByteConverter
{
	private static int bufferSize = 1024;
	private static byte[] buffer;
	
	/**
	 * <p>Clear buffer.</p>
	 * 
	 * <p>버퍼를 비웁니다.</p>
	 */
	public static void clearBuffer()
	{
		buffer = new byte[bufferSize];
		for(int i=0; i<buffer.length; i++)
		{
			buffer[i] = 0;
		}
	}
	
	/**
	 * <p>Read bytes from file.</p>
	 * 
	 * <p>파일로부터 바이트를 읽습니다.</p>
	 * 
	 * @param file : File object includes the file path information
	 * @return bytes
	 */
	public static byte[] load(File file)
	{
		FileInputStream inputStream = null;
		BufferedInputStream buffered = null;
		List<byte[]> byteList = new Vector<byte[]>();
		try
		{
			inputStream = new FileInputStream(file);
			buffered = new BufferedInputStream(inputStream);
			byte[] results;
			int getLengths;
			
			while(true)
			{
				clearBuffer();
				getLengths = buffered.read(buffer);
				if(getLengths < 0) break;
				results = new byte[getLengths];				
				for(int i=0; i<results.length; i++)
				{
					results[i] = buffer[i];
				}
				byteList.add(results);
			}
			
			getLengths = 0;
			for(int i=0; i<byteList.size(); i++)
			{
				getLengths = getLengths + byteList.get(i).length;
			}
			results = new byte[getLengths];
			getLengths = 0;
			for(int i=0; i<byteList.size(); i++)
			{
				for(int j=0; j<byteList.get(i).length; j++)
				{
					results[getLengths] = byteList.get(i)[j];
					getLengths++;
				}
			}
			return results;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				buffered.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				inputStream.close();
			}
			catch(Exception e)
			{
				
			}
		}		
	}
	
	/**
	 * <p>Save bytes as file.</p>
	 * 
	 * <p>파일로 바이트를 저장합니다.</p>
	 * 
	 * @param file : File object includes the file path information
	 * @param bytes : bytes
	 */
	public static void save(File file, byte[] bytes)
	{
		if(bytes == null) return;
		
		FileOutputStream outputStream = null;
		BufferedOutputStream buffered = null;
		try
		{
			outputStream = new FileOutputStream(file);
			buffered = new BufferedOutputStream(outputStream);
			
			buffered.write(bytes);		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				buffered.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				outputStream.close();
			}
			catch(Exception e)
			{
				
			}
		}
	}	
	
	/**
	 * <p>Get hash value of bytes.</p>
	 * 
	 * <p>바이트의 해시값을 얻습니다.</p>
	 * 
	 * @param befores : bytes
	 * @param algorithm : hashing function (MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512)
	 * @return hash value
	 */
	public static String hash(byte[] befores, String algorithm)
	{
		HashModule module = new HashModule();
		return module.encrypt(befores, algorithm);
	}
	
	/**
	 * <p>Encrypt bytes.</p>
	 * 
	 * <p>바이트를 암호화합니다.</p>
	 * 
	 * @param befores : original bytes
	 * @param key : password
	 * @param algorithm : encryption algorithm (DES, AES, DESede)
	 * @param keypadMethod : how to pad key
	 * @return encrypted bytes
	 */
	public static byte[] encrypt(byte[] befores, String key, String algorithm, String keypadMethod)
	{
		EncryptModule module = new EncryptModule();
		return module.convert(befores, key, algorithm, keypadMethod);
	}
	
	/**
	 * <p>Decrypt bytes.</p>
	 * 
	 * <p>바이트를 복호화합니다.</p>
	 * 
	 * @param befores : encrypted bytes
	 * @param key : password
	 * @param algorithm : decryption algorithm (DES, AES, DESede)
	 * @param keypadMethod : how to pad key
	 * @return original bytes
	 */
	public static byte[] decrypt(byte[] befores, String key, String algorithm, String keypadMethod)
	{
		DecryptModule module = new DecryptModule();
		return module.convert(befores, key, algorithm, keypadMethod);
	}

	/**
	 * <p>Convert long-digit code into the byte array.</p>
	 * 
	 * <p>긴 자리수 코드를 바이트 배열로 변환합니다.</p>
	 * 
	 * @param digits : long-digit code
	 * @return byte array
	 */
	public static byte[] stringToBytes(String digits)
	{		
		String gets = digits.trim();
		char[] charArray = gets.toCharArray();
		List<byte[]> byteList = new Vector<byte[]>();
		int countThree = 0;
		byte[] byteOne = null;
		for(int i=0; i<charArray.length; i++)
		{
			if(byteOne == null) byteOne = new byte[5];
			if(countThree <= 3)
			{
				byteOne[countThree] = (byte) Integer.parseInt(String.valueOf(charArray[i]));
				countThree++;
			}
			else
			{				
				byteOne[countThree] = (byte) Integer.parseInt(String.valueOf(charArray[i]));
				
				byteList.add(byteOne);
				countThree = 0;
				byteOne = new byte[5];
			}			
		}
		byte[] resultArray = new byte[byteList.size()];
		for(int i=0; i<resultArray.length; i++)
		{
			resultArray[i] = 0;
		}
		
		int resultIndex = 0;
		int resultMultiply = 1;
		int reverse_digit = 0;
		int each_value = 0;
		boolean negatives = false;
		for(int i=0; i<byteList.size(); i++)
		{
			each_value = 0;
			negatives = false;
			
			for(int j=0; j<byteList.get(i).length; j++)
			{
				resultMultiply = 1;
				reverse_digit = byteList.get(i).length - j - 1;		
				if(reverse_digit >= 4)
				{
					if(byteList.get(i)[j] == 1) negatives = true;
					else negatives = false;
				}
				else
				{
					for(int k=0; k<reverse_digit; k++)
					{
						resultMultiply = resultMultiply * 10;
					}	
				}				
				each_value = (byte) (each_value + (byteList.get(i)[j] * resultMultiply));	
				if(negatives)
				{
					resultArray[resultIndex] = (byte) (each_value * (-1));
					resultArray[resultIndex] = (byte) (resultArray[resultIndex] + 1);
				}
				else
				{
					resultArray[resultIndex] = (byte) each_value;
				}
			}
			
			resultIndex++;
		}
		
		return resultArray;
	}

	/**
	 * <p>Convert byte array into the long-digit code.</p>
	 * 
	 * <p>바이트 배열을 긴 자리수 코드로 변환합니다.</p>
	 * 
	 * @param byteArray : byte array
	 * @return long-digit code
	 */
	public static String bytesToString(byte[] byteArray)
	{		
		StringBuffer results = new StringBuffer("");
		int[] blocks = new int[5];
		int blockValue;
		for(int i=0; i<byteArray.length; i++)
		{
			for(int j=0; j<blocks.length; j++)
			{
				blocks[j] = 0;
			}
			blockValue = (int) byteArray[i];
			if(blockValue < 0)
			{
				blocks[4] = 1;
				blockValue = blockValue * (-1);
			}
			else
			{
				blocks[4] = 0;
			}
			while(blockValue >= 1)
			{
				if(blockValue >= 1000)
				{
					blocks[3] = blocks[3] + 1;
					blockValue = blockValue - 1000;
				}
				else if(blockValue >= 100)
				{
					blocks[2] = blocks[2] + 1;
					blockValue = blockValue - 100;
				}
				else if(blockValue >= 10)
				{
					blocks[1] = blocks[1] + 1;
					blockValue = blockValue - 10;
				}
				else if(blockValue >= 1)
				{
					blocks[0] = blocks[0] + 1;
					blockValue = blockValue - 1;
				}
			}
			for(int j=blocks.length-1; j>=0; j--)
			{
				results.append(String.valueOf((int) blocks[j]));
			}				
		}
		return String.valueOf(results);
	}

	/**
	 * <p>Convert byte array into the text. There will only numbers on the text.</p>
	 * 
	 * <p>바이트 배열을 문자열로 변환합니다. 문자열에는 숫자만 보일 것입니다.</p>
	 * 
	 * @param byteArray : byte array
	 * @return text
	 */
	public static String seeBytes(byte[] byteArray)
	{
		StringBuffer results = new StringBuffer("");
		for(int i=0; i<byteArray.length; i++)
		{
			results.append(String.valueOf((int)byteArray[i]) + " ");
		}
		return String.valueOf(results).trim();
	}
	
	/**
	 * <p>Run binary converter.</p>
	 * 
	 * <p>바이너리 변환기를 실행합니다.</p>
	 */
	public static void runBinaryConverter(Object ob)
	{
		InputStream stream = System.in;
		Reader reader = null;
		BufferedReader buffered = null;
		
		boolean readerLoad = true;
		boolean bufferedLoad = true;
		boolean doNotCloseStream = true;
		
		if(ob != null)
		{
			if(ob instanceof BufferedReader)
			{
				buffered = (BufferedReader) ob;
				readerLoad = false;
				bufferedLoad = false;
				doNotCloseStream = true;
			}
			else if(ob instanceof Reader)
			{
				reader = (Reader) ob;
				readerLoad = false;
				bufferedLoad = true;
				doNotCloseStream = true;
			}
			else if(ob instanceof InputStream)
			{
				stream = (InputStream) ob;
				readerLoad = true;
				bufferedLoad = true;
				doNotCloseStream = false;
			}
		}
		
		try
		{
			if(readerLoad) reader = new InputStreamReader(stream);
			if(bufferedLoad) buffered = new BufferedReader(reader);
			String gets = "";
			File file1 = null, file2 = null;
			int selects;
			boolean endWorks = true;
			boolean binaryMode = true;
			boolean continueFlag = false;
			while(endWorks)
			{
				try
				{		
					continueFlag = false;
					Controller.println();
					Controller.println("Method Converter", true);
					Controller.println();
					Controller.print("Mode", true);
					Controller.print(" : ", true);
					if(binaryMode) Controller.println("binary", true);
					else Controller.println("character", true);					
					Controller.print("Selected file", true);
					if(file1 == null) Controller.println(" : " + "none");
					else Controller.println(" : " + file1.getAbsolutePath());
					Controller.print("Selected where to save", true);
					if(file2 == null) Controller.println(" : " + "none");
					else Controller.println(" : " + file2.getAbsolutePath());
					
					Controller.println();
					Controller.println("1. Encrypt", true);
					Controller.println("2. Decrypt", true);
					Controller.println("3. Hash", true);
					Controller.println("4. Select file to load", true);
					Controller.println("5. Select where to save", true);
					Controller.println("6. Set mode", true);
					Controller.println("7. Exit", true);
					Controller.print("Select : ", true);
					gets = buffered.readLine();
					selects = Integer.parseInt(gets);
					
					String method = "AES";
					
					switch(selects)
					{
					case 1: // Encrypt
						method = "AES";
						while(true)
						{
							Controller.println();
							Controller.println("Please input one of the following options.", true);
							Controller.println("DES, DESede, AES, cancel", true);
							Controller.println();
							Controller.print("Select : ", true);
							gets = buffered.readLine();
							gets = gets.trim();
							if(gets.equalsIgnoreCase("cancel"))
							{
								continueFlag = true;
								break;
							}
							else if(gets.equalsIgnoreCase("DES") || gets.equalsIgnoreCase("DESede") || gets.equalsIgnoreCase("AES"))
							{
								method = gets.trim();
								break;
							}
						}
						if(continueFlag) continue;
						
						Controller.print("Password", true);
						Controller.print(" : ");
						String keys = buffered.readLine();
						
						Controller.print("Byte encoding method", true);
						Controller.print(" (");
						Controller.print("Recommend : base64", true);
						Controller.print(") : ");
						String byteencoding = buffered.readLine();
						
						Controller.print("Key padding method", true);
						Controller.print(" (");
						Controller.print("Recommend : special", true);
						Controller.print(") : ");
						String keypads = buffered.readLine();
						
						if(file1 == null)
						{
							while(true)
							{
								Controller.println("What file to load (Input 'cancel' to cancel)", true);
								Controller.print("Select", true);
								Controller.print(" : ");
								gets = buffered.readLine();
								gets = gets.trim();
								if(gets.equalsIgnoreCase("cancel"))
								{
									continueFlag = true;
									break;
								}
								
								File selectFiles = new File(gets);								
								if(! selectFiles.exists())
								{
									Controller.println("File is not exist : " + selectFiles.getAbsolutePath(), true);
								}
								else
								{
									file1 = selectFiles;
									break;
								}
							}
							if(continueFlag) continue;
						}
						
						EncryptModule encryptor = new EncryptModule();
						if(binaryMode) save(file2, encryptor.convert(load(file1), keys, method, keypads));
						else Controller.saveFile(file2, encryptor.convert(Controller.readFile(file1, 20, null), 20, method, keys, keypads, byteencoding), null);
						
						Controller.print("Save completely.", true);
						
						break;
					case 2: // Decrypt
						method = "AES";
						while(true)
						{
							Controller.println();
							Controller.println("Please input one of the following options.", true);
							Controller.println("DES, DESede, AES, cancel", true);
							Controller.println();
							Controller.print("Select : ", true);
							gets = buffered.readLine();
							gets = gets.trim();
							if(gets.equalsIgnoreCase("cancel"))
							{
								continueFlag = true;
								break;
							}
							else if(gets.equalsIgnoreCase("DES") || gets.equalsIgnoreCase("DESede") || gets.equalsIgnoreCase("AES"))
							{
								method = gets.trim();
								break;
							}
						}
						if(continueFlag) continue;
						
						Controller.print("Password", true);
						Controller.print(" : ");
						String keys2 = buffered.readLine();
						
						Controller.print("Byte encoding method", true);
						Controller.print(" (");
						Controller.print("Recommend : base64", true);
						Controller.print(") : ");
						String byteencoding2 = buffered.readLine();
						
						Controller.print("Key padding method", true);
						Controller.print(" (");
						Controller.print("Recommend : special", true);
						Controller.print(") : ");
						String keypads2 = buffered.readLine();
						
						if(file1 == null)
						{
							while(true)
							{
								Controller.println("What file to load (Input 'cancel' to cancel)", true);
								Controller.print("Select", true);
								Controller.print(" : ");
								gets = buffered.readLine();
								gets = gets.trim();
								if(gets.equalsIgnoreCase("cancel"))
								{
									continueFlag = true;
									break;
								}
								
								File selectFiles = new File(gets);								
								if(! selectFiles.exists())
								{
									Controller.println("File is not exist : " + selectFiles.getAbsolutePath(), true);
								}
								else
								{
									file1 = selectFiles;
									break;
								}
							}
							if(continueFlag) continue;
						}
						
						DecryptModule decryptor = new DecryptModule();
						if(binaryMode) save(file2, decryptor.convert(load(file1), keys2, method, keypads2));
						else Controller.saveFile(file2, decryptor.convert(Controller.readFile(file1, 20, null), 20, method, keys2, keypads2, byteencoding2), null);
						
						Controller.print("Save completely.", true);
						
						break;
					case 3: // Hash
						method = "MD2";
						while(true)
						{
							Controller.println();
							Controller.println("Please input one of the following options.", true);
							Controller.println("MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512, cancel", true);
							Controller.println();
							Controller.print("Select : ", true);
							gets = buffered.readLine();
							gets = gets.trim();
							if(gets.equalsIgnoreCase("cancel"))
							{
								continueFlag = true;
								break;
							}
							else if(gets.equalsIgnoreCase("MD2") || gets.equalsIgnoreCase("MD5") || gets.equalsIgnoreCase("SHA-1")
									|| gets.equalsIgnoreCase("SHA-256") || gets.equalsIgnoreCase("SHA-384") 
									|| gets.equalsIgnoreCase("SHA-512"))
							{
								method = gets.trim();
								break;
							}
						}
						if(continueFlag) continue;
						HashModule hashModule = new HashModule();
						Controller.println();
						Controller.println("Results", true);
						Controller.println();
						String resultValue = hashModule.encrypt(load(file1), method);
						Controller.println(resultValue);
						Controller.println();
						Controller.println("If you want to save as file, input 'save'.", true);
						Controller.println("Else, just input any text.", true);
						Controller.print("Select : ", true);
						gets = buffered.readLine();
						if(gets != null && gets.trim().equalsIgnoreCase("save"))
						{
							Controller.println();
							Controller.print("Input where to save : ");
							gets = buffered.readLine();
							gets = gets.trim();
							if(! (gets.equalsIgnoreCase("cancel")))
							{
								Controller.saveFile(new File(gets), resultValue, null);
								Controller.print("Saved at", true);
								Controller.println(" " + new File(gets).getAbsolutePath());
							}
						}
						break;
					case 4: // Load file select
						Controller.print("Please input the file path : ", true);
						gets = buffered.readLine();
						try
						{
							File selectFile = new File(gets);
							if(selectFile.exists()) file1 = selectFile;
							else Controller.println("File is not exist : " + selectFile.getAbsolutePath(), true);
						}
						catch(Exception e)
						{
							Statics.fullErrorMessage(e);
						}
						break;
					case 5: // Save file select
						Controller.print("Please input where to save with the file name : ", true);
						gets = buffered.readLine();
						try
						{
							File selectFile = new File(gets);
							file2 = selectFile;
						}
						catch(Exception e)
						{
							Controller.print("Error", true);
							Controller.print(" : ", true);
							Controller.println(e.getMessage(), true);
						}
						break;						
					case 6: // Set mode
						Controller.println("Please input one of the following options.", true);
						Controller.println("binary, character", true);
						Controller.print("Select", true);
						Controller.print(" : ");
						gets = buffered.readLine();
						gets = gets.trim();
						if(gets.equalsIgnoreCase("binary"))
						{
							binaryMode = true;
						}
						else if(gets.equalsIgnoreCase("character"))
						{
							binaryMode = false;
						}
						else
						{
							Controller.print("Error", true);
							Controller.print(" : ");
							Controller.println("Cannot select modes : " + gets);
						}						
						break;
					case 7: // Exit
						endWorks = false;
						break;
					default:
						Controller.println("Please input correct numbers.", true);
					}
				}
				catch(Exception e)
				{
					Statics.fullErrorMessage(e);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				buffered.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				reader.close();
			}
			catch(Exception e)
			{
				
			}
			if(! doNotCloseStream)
			{
				try
				{
					stream.close();
				}
				catch(Exception e)
				{
					
				}
			}
			System.out.println(Controller.getString("The program will be closed. It needs around 2 seconds."));
			Controller.closeAll();			
			System.out.println(Controller.getString("Bye"));
			System.exit(0);
		}
	}
}
