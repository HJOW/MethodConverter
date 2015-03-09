package hjow.convert.javamethods;

import hjow.methodconverter.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * <p>This object is used in script. In script, you can use this object named console.<br>
 *    Only work on the user-input-script console.</p>
 * 
 * <p>This class can be used as a library.</p>
 *  
 * 
 * <p>이 객체는 스크립트 내에서 사용됩니다. console 라는 이름으로 사용할 수 있습니다.<br>
 *    오직 사용자가 직접 입력하는 스크립트에서만 사용할 수 있습니다.</p>
 * 
 * <p>라이브러리로 사용할 수도 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class ConsoleContainer implements Serializable
{
	private static final long serialVersionUID = 4455139411222677584L;
	
	/**
	 * <p>Create new object.</p>
	 * 
	 * <p>객체를 생성합니다.</p>
	 */
	public ConsoleContainer()
	{
		
	}
	/**
	 * <p>Return now-selected path.</p>
	 * 
	 * <p>현재 선택된 경로를 반환합니다.</p>
	 * 
	 * @return now-selected path
	 */
	public String pwd()
	{
		return Controller.getDirCursor().getAbsolutePath();
	}
	/**
	 * <p>Return now-selected path and file list in the path.</p>
	 * 
	 * <p>현재 선택된 경로와 그 안의 파일 목록을 반환합니다.</p>
	 * 
	 * @return now-selected path and file list
	 */
	public String ls()
	{
		StringBuffer results = new StringBuffer("");
		
		File[] files = Controller.getDirCursor().listFiles();
		List<File> fileList = new Vector<File>();
		
		List<File> dirList = new Vector<File>();
		List<File> nonDirList = new Vector<File>();
		for(File f : files)
		{
			if(f.isDirectory()) dirList.add(f);
			else nonDirList.add(f);
		}
		fileList.addAll(dirList);
		fileList.addAll(nonDirList);
		
		results = results.append(Controller.getString("Selected") + " : " + pwd() + "\n");
		String permissions = "";
		String isDir = "";
		File target = null;
		for(int i=0; i<fileList.size(); i++)
		{
			target = fileList.get(i);
			permissions = "";
			isDir = "";
			try
			{
				if(target.canRead()) permissions = permissions + "r";
				else permissions = permissions + "-";
			}
			catch(Exception e)
			{
				permissions = permissions + "-";
			}
			try
			{
				if(target.canWrite()) permissions = permissions + "w";
				else permissions = permissions + "-";
			}
			catch(Exception e)
			{
				permissions = permissions + "-";
			}
			try
			{
				if(target.canExecute()) permissions = permissions + "e";
				else permissions = permissions + "-";
			}
			catch(Exception e)
			{
				permissions = permissions + "-";
			}
			try
			{
				if(target.isDirectory()) isDir = isDir + "dir";
				else isDir = isDir + "";
			}
			catch(Exception e)
			{
				isDir = isDir + "";
			}
			results = results.append(String.format("%6d " + permissions + " " + isDir + " : ", new Integer(i)) + target.getName() + "\n");
		}
		
		return results.toString();
	}
	/**
	 * <p>Select directory.</p>
	 * 
	 * <p>디렉토리를 선택합니다.</p>
	 * 
	 * @param dirName : directory name
	 * @return result of pwd() of newly selected directory
	 * @throws IOException : IOException occurs at accessing File object
	 */
	public String cd(String dirName) throws IOException
	{
		if(dirName == null) return pwd();
		File newTarget = new File(Controller.getDirCursor() + Controller.getFileSeparator() + dirName);
		if((! newTarget.exists()) || (! newTarget.isDirectory()))
		{
			newTarget = new File(dirName);
		}
		Controller.setDirCursor(newTarget);
		return pwd();
	}
	/**
	 * <p>Run the file.</p>
	 * 
	 * <p>파일을 실행합니다..</p>
	 * 
	 * @param order : file name
	 * @return result of execution
	 * @throws Exception : Exception from OS
	 */
	public Process run(String order) throws Exception
	{
		return Runtime.getRuntime().exec(Controller.getDirCursor().getAbsolutePath() + Controller.getFileSeparator() + order);
	}
	
	/**
	 * <p>Run OS command</p>
	 * 
	 * <p>OS 명령문을 실행합니다..</p>
	 * 
	 * @param order : command
	 * @return result of execution
	 * @throws Exception : Exception from OS
	 */
	public Process runCommand(String order) throws Exception
	{
		return Runtime.getRuntime().exec(order);
	}
	
	/**
	 * <p>Make a directory.</p>
	 * 
	 * <p>디렉토리를 만듭니다.</p>
	 * 
	 * @param dirName : New directory name, must not be a full path
	 * @throws Exception : May be occured by OS (Previleges)
	 */
	public void mkdir(String dirName) throws Exception
	{
		File newTarget = new File(Controller.getDirCursor() + Controller.getFileSeparator() + dirName);
		newTarget.mkdir();
	}
	/**
	 * <p>Delete file or directory (All files inside directory)</p>
	 * 
	 * <p>파일이나 디렉토리를 삭제합니다. 디렉토리 삭제 시 그 안의 모든 파일이 대상이 됩니다.</p>
	 * 
	 * @param dirName : target file or directory name or path
	 * @throws Exception : Exception while deletion
	 */
	public void remove(String dirName) throws Exception
	{
		remove(dirName, false);
	}
	/**
	 * <p>Delete file or directory (All files inside directory)</p>
	 * 
	 * <p>파일이나 디렉토리를 삭제합니다. 디렉토리 삭제 시 그 안의 모든 파일이 대상이 됩니다.</p>
	 * 
	 * @param dirs : target file or directory name, path, or File list
	 * @param noWarn : if this is true, warn asking dialog is not occured
	 * @throws Exception : Exception while deletion
	 */
	public void remove(Object dirs, boolean noWarn) throws Exception
	{
		String dirName = "";
		File newTarget = Controller.getDirCursor();
		FileList deleteList = new FileVector();
		
		if((dirs instanceof String) || (dirs instanceof File))
		{
			if(dirs instanceof String)
			{
				newTarget = new File(Controller.getDirCursor() + Controller.getFileSeparator() + dirName);				
			}
			else if(dirs instanceof File)
			{
				newTarget = (File) dirs;
			}
			if(! newTarget.exists())
			{
				newTarget = new File(dirName);
			}
			if(! newTarget.exists()) throw new FileNotFoundException(Controller.getString("Is not exists") + " : " + dirName);
			
			if(newTarget.isDirectory())
			{
				File[] insides = newTarget.listFiles();
				for(File f : insides)
				{
					deleteList.add(f);
				}
			}
			deleteList.add(newTarget);
		}
		else if(dirs instanceof FileList)
		{
			deleteList = (FileList) dirs;
		}
		
		if(! noWarn)
		{
			if(! (Controller.requestYes(Controller.getString("Do you want to delete?") + "\n" 
					+ Controller.getString("Target") + " : " + String.valueOf(dirs) + "\n"
					+ Controller.getString("Count") + " : " + deleteList.size())))
			{
				return;
			}
		}
		
		for(int i=0; i<deleteList.size(); i++)
		{
			Controller.print("Try to delete", true);
			Controller.println(" : " + deleteList.get(i).getAbsolutePath());
			if(deleteList.get(i).isDirectory())
			{				
				remove(deleteList.get(i), true);
			}
			else
			{
				deleteList.get(i).delete();
			}
			Controller.print("Delete", true);
			Controller.println(" : " + deleteList.get(i).getAbsolutePath() + " : " + Controller.getString("Successful"));
		}
	}
}
/**
 * <p>List<File> interface.</p>
 * 
 * <p>List<File> 와 동일한 인터페이스입니다.</p>
 * 
 * 
 * @author HJOW
 *
 */
interface FileList extends List<File>
{
	
}
/**
 * <p>List of File objects which is implements FileList.</p>
 * 
 * <p>FileList 를 구현하는 File 객체들의 리스트입니다.</p>
 * 
 * @author HJOW
 *
 */
class FileVector implements FileList
{
	private Vector<File> files = new Vector<File>();
	public FileVector()
	{
		super();
	}
	@Override
	public boolean add(File e)
	{
		return files.add(e);
	}

	@Override
	public void add(int index, File element)
	{
		files.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends File> c)
	{
		return files.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends File> c)
	{
		return files.addAll(c);
	}

	@Override
	public void clear()
	{
		files.clear();
	}

	@Override
	public boolean contains(Object o)
	{
		return files.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return files.containsAll(c);
	}

	@Override
	public File get(int index)
	{
		return files.get(index);
	}

	@Override
	public int indexOf(Object o)
	{
		return files.indexOf(o);
	}

	@Override
	public boolean isEmpty()
	{
		return files.isEmpty();
	}

	@Override
	public Iterator<File> iterator()
	{
		return files.iterator();
	}

	@Override
	public int lastIndexOf(Object o)
	{
		return files.lastIndexOf(o);
	}

	@Override
	public ListIterator<File> listIterator()
	{
		return files.listIterator();
	}

	@Override
	public ListIterator<File> listIterator(int index)
	{
		return files.listIterator(index);
	}

	@Override
	public boolean remove(Object o)
	{
		return files.remove(o);
	}

	@Override
	public File remove(int index)
	{
		return files.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return files.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return files.retainAll(c);
	}

	@Override
	public File set(int index, File element)
	{
		return files.set(index, element);
	}

	@Override
	public int size()
	{
		return files.size();
	}

	@Override
	public List<File> subList(int fromIndex, int toIndex)
	{
		return files.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray()
	{
		return files.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return files.toArray(a);
	}	
}
