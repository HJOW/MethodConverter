package hjow.methodconverter;

import java.io.File;

/**
 * <p>This class helps to load classes and java archieves.</p>
 * 
 * <p>이 클래스는 외부 자바 클래스와 자바 아카이브들을 동적으로 불러오는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
public class ClassManager
{
	/**
	 * <p>Load class dynamic.</p>
	 * 
	 * <p>클래스를 동적으로 불러오는 데 사용됩니다.</p>
	 * 
	 * @param file : File object. If this is jar file, find class files from jar file. If this is directory, find class files in directory. 
	 * @param className : class full name
	 * @return Class object which has fields and methods
	 * @throws Exception : Exceptions can be occured if className is not exist, file object is invalid, or the class is initializing wrong.
	 */
	@SuppressWarnings("rawtypes")
	public static Class load(File file, String className) throws Exception
	{
		java.net.URI uri = file.toURI();
		java.net.URL[] urls = new java.net.URL[1];
		urls[0] = uri.toURL();
		ClassLoader loader = new java.net.URLClassLoader(urls);
		return loader.loadClass(className);
	}
	/**
	 * <p>Load class dynamic.</p>
	 * 
	 * <p>클래스를 동적으로 불러오는 데 사용됩니다.</p>
	 * 
	 * @param file : File object. If this is jar file, find class files from jar file. If this is directory, find class files in directory. 
	 * @param className : class full name
	 * @param forceInitialize : If this is true, class will be initialized immediately.
	 * @return Class object which has fields and methods
	 * @throws Exception : Exceptions can be occured if className is not exist, file object is invalid, or the class is initializing wrong.
	 */
	@SuppressWarnings("rawtypes")
	public static Class load(File file, String className, boolean forceInitialize) throws Exception
	{
		java.net.URI uri = file.toURI();
		java.net.URL[] urls = new java.net.URL[1];
		urls[0] = uri.toURL();
		ClassLoader loader = new java.net.URLClassLoader(urls);
		Class target = loader.loadClass(className);
		if(forceInitialize) Class.forName(className);
		return target;
	}
	/**
	 * <p>Load class dynamic.</p>
	 * 
	 * <p>클래스를 동적으로 불러오는 데 사용됩니다.</p>
	 * 
	 * @param className : class full name
	 * @return Class object which has fields and methods
	 * @throws Exception : Exceptions can be occured if className is not exist or other exceptions occurs during the class is initializing.
	 */
	@SuppressWarnings("rawtypes")
	public static Class load(String className) throws Exception
	{
		return Class.forName(className);
	}
}
