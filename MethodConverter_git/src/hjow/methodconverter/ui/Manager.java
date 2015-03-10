package hjow.methodconverter.ui;

import hjow.convert.module.ScriptModule;
import hjow.daemon.Daemon;
import hjow.methodconverter.Controller;
import hjow.methodconverter.ParameterGetter;
import hjow.methodconverter.Statics;
import hjow.methodconverter.StringTable;
import hjow.methodconverter.Syntax;
import hjow.methodconverter.ThreadRunner;
import hjow.network.Refreshable;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 
 * <p>Manager class</p>
 * 
 * <p>매니저 클래스입니다.</p>
 * 
 * @author HJOW
 *
 */
public abstract class Manager implements ActionListener, WindowListener, ItemListener, ThreadRunner, Serializable, HasParameterText, Refreshable
{
	private static final long serialVersionUID = -8731065689920486819L;
	private long id = ((long) (Math.random() * (Long.MAX_VALUE / 2))) + ((long)(Math.random() * (Long.MAX_VALUE / 100000)));
	protected boolean threadswitch = true;
	protected boolean loadNextTime = false;
	protected boolean saveNextTime = false;
	protected SettingManager settingManager;
	protected StatusViewer status;
	protected AboutDialog aboutDialog;
	protected PackageViewer packageView;
	protected ParameterGetter parameterGetter;
	protected MemoryManager memoryManager;
	protected GUIBinaryConverter binaryConverter;
	protected ModuleList moduleList;
	protected ModuleEditor moduleEditor;
	protected String nowShowingParamPanelName;
	protected GUIDaemonClient daemonClient;
	protected Daemon daemon;
		
	/**
	 * <p>It is not a good idea to override this constructor.
	 * Please use init() method instead.</p>
	 * 
	 * <p>이 메소드를 재정의하는 것은 권장하지 않습니다.
	 * init() 메소드를 대신 사용하세요.</p>
	 */
	public Manager()
	{
		init();
		Thread createThread = new Thread(this);
		Controller.insertThreadObject(createThread);
		createThread.start();
		Controller.insertRefreshes(this);
	}
	/**
	 * <p>This method will be run to initialize this GUI program.
	 * Recommended to override this method.</p>
	 * 
	 * <p>GUI 프로그램을 초기화할 때 자동으로 호출됩니다.
	 * 이 메소드를 재정의해 사용하세요.</p>
	 * 
	 */
	protected void init()
	{
		firstSelectModule();
	}
	
	/**
	 * <p>Refresh syntax list</p>
	 * 
	 * <p>프로그래밍 언어 문법 리스트를 새로 고칩니다.</p>
	 */
	protected abstract void syntaxChoiceRefresh();
	
	/**
	 * <p>Load programming language datas from files</p>
	 * 
	 * <p>파일로부터 프로그래밍 언어 문법 테이블을 읽어옵니다.
	 * 지정된 디렉토리에 있는 syn 확장자로부터 읽어옵니다.</p>
	 */
	public void loadSyntaxes()
	{		
		try
		{
			// Prepare variables
			InputStreamReader reader = null;
			BufferedReader buffered = null;
			Syntax syntaxes;
			
			// reset the syntax list
			Controller.resetSyntaxList();
			
			Vector<InputStream> inputStreams = new Vector<InputStream>();
			Vector<URL> resultUrls = new Vector<URL>();
			
			if(Statics.useOnlineContent())
			{
				// take URL list
				try
				{
					if(Controller.getDefaultURL() != null)
					{					
						URL target = new URL(Controller.getDefaultURL() + "list.txt");
						String urlLine;
						boolean firstLine = true;
						try
						{
							reader = new InputStreamReader(target.openStream());
							buffered = new BufferedReader(reader);
							while(true)
							{
								urlLine = buffered.readLine();
								if(urlLine == null) break;
								if(firstLine)
								{
									firstLine = false;
									continue;
								}
								urlLine = urlLine.trim();
								if(urlLine.startsWith("#")) continue;
								try
								{
									resultUrls.add(new URL(urlLine));
								}
								catch(Exception e)
								{
									
								}
							}
						}
						catch(Exception e)
						{
							
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
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				// read syntax setting from urls
				for(URL urls : resultUrls)
				{
					try
					{
						reader = new InputStreamReader(urls.openStream());
						buffered = new BufferedReader(reader);
						String readLine, readAll = "";
						while(true)
						{
							readLine = buffered.readLine();
							if(readLine == null) break;
							readAll = readAll + readLine + "\n";
						}
						syntaxes = new Syntax(readAll);
						String syntaxName = syntaxes.get("name");
						if(syntaxName == null)
						{
							syntaxName = String.valueOf(Math.random());
						}
						Controller.insertSyntax(syntaxName, syntaxes);
						//Converter.syntaxes.put(syntaxName, syntaxes);
					}
					catch (Exception e)
					{
						
					}
					finally
					{
						try
						{
							buffered.close();
						}
						catch (Exception e)
						{
							
						}
						try
						{
							reader.close();
						}
						catch (Exception e)
						{
							
						}
					}
				}
			}
			
			// prepare to load files
			File target_file = new File(Controller.getDefaultPath());
			if(! target_file.exists()) return;
			File[] files = target_file.listFiles(new FileFilter()
			{				
				@Override
				public boolean accept(File e)
				{
					try
					{
						if(e.getAbsolutePath().endsWith("syntax") || e.getAbsolutePath().endsWith("SYNTAX") || e.getAbsolutePath().endsWith("syn") || e.getAbsolutePath().endsWith("SYN"))
						{
							return true;
						}
						return false;
					}
					catch(Exception e2)
					{
						e2.printStackTrace();
						return false;
					}
				}
			});
			if(files == null) return;
			
			// prepare file input streams
			FileInputStream newInput = null;			
			for(int i=0; i<files.length; i++)
			{				
				try
				{
					newInput = new FileInputStream(files[i]);
					inputStreams.add(newInput);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					try
					{
						newInput.close();
					}
					catch(Exception e2)
					{
						
					}
				}
			}
			
			// read syntax setting from input streams
			for(InputStream f : inputStreams)
			{		
				try
				{
					reader = new InputStreamReader(f);
					buffered = new BufferedReader(reader);
					String readLine, readAll = "";
					while(true)
					{
						readLine = buffered.readLine();
						if(readLine == null) break;
						readAll = readAll + readLine + "\n";
					}
					syntaxes = new Syntax(readAll);
					String syntaxName = syntaxes.get("name");
					if(syntaxName == null)
					{
						syntaxName = String.valueOf(Math.random());
					}
					//Converter.syntaxes.put(syntaxName, syntaxes);
					Controller.insertSyntax(syntaxName, syntaxes);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					Controller.setStatusText("There is a problem : " + e.getMessage());
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
				}
			}
			
			// close all streams
			try
			{
				for(InputStream in : inputStreams)
				{
					try
					{
						in.close();
					}
					catch(Exception e)
					{
						
					}
				}
			}
			catch(Exception e)
			{
				
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.setStatusText("There is a problem : " + e.getMessage());
		}
	}
	
	

	/**
	 * <p>Load string tables from files</p>
	 * 
	 * <p>파일로부터 스트링 테이블을 읽어옵니다.
	 * 지정된 경로에서 strings.txt 파일을 읽습니다.</p>
	 */
	public void loadStringTable()
	{
		try
		{
			File target_file = new File(Controller.getDefaultPath() + "strings.txt");
			
			FileReader reader = null;
			BufferedReader buffered = null;
			String lines;
			StringTokenizer colonToken;
			String keys, values;
			if(! target_file.exists()) return;
			try
			{
				reader = new FileReader(target_file);
				buffered = new BufferedReader(reader);
				while(true)
				{
					lines = buffered.readLine();
					if(lines == null) break;
					lines = lines.trim();
					if(lines.equals("")) continue;
					if(lines.startsWith("#")) continue;
					try
					{
						colonToken = new StringTokenizer(lines, ":");
						keys = colonToken.nextToken().trim();
						values = colonToken.nextToken().trim();
						Controller.setString(keys, values);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						continue;
					}
				}	
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Controller.setStatusText("There is a problem : " + e.getMessage());
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
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <p>Set string labels on GUI with string table</p>
	 * 
	 * <p>스트링 테이블을 GUI에 적용합니다.</p>
	 * 
	 */
	public abstract void setLanguage();
	/**
	 * 
	 * <p>Set string labels on GUI with string table</p>
	 * 
	 * <p>스트링 테이블을 GUI에 적용합니다.</p>
	 * 
	 * @param stringTable String table 스트링 테이블
	 */
	public void setLanguage(StringTable stringTable)
	{
		Controller.setLanguage(stringTable);
		setLanguage();
	}
	/**
	 * 
	 * <p>Enable, or disable all components.</p>
	 * 
	 * <p>컴포넌트를 모두 활성화하거나 비활성화합니다.</p>
	 * 
	 * @param l : If this is true, all components will be enabled.
	 */
	public abstract void enableAll(boolean l);
	
	/**
	 * 
	 * <p>Open manager window.</p>
	 * 
	 * <p>매니저 창을 엽니다.</p>
	 * 
	 */
	public void open()
	{
		askUseOnlineContentsIfFirst();
	}
	
	/**
	 * <p>Close manager window and stop threads, then finish the program.</p>
	 * 
	 * <p>매니저 창을 닫고, 쓰레드를 모두 닫고 프로그램을 종료합니다.</p>
	 */
	public void close()
	{
		Controller.println("Preparing to exit...", true);
		enableAll(false);
		threadswitch = false;
		closeResource();
		setVisible(false);
		System.out.println(Controller.getString("The program will be closed. It needs around 2 seconds."));
		Statics.saveOptionByLevel(Statics.autoSavingLevel());
		Controller.closeAll();			
		System.out.println(Controller.getString("Bye"));
		System.exit(0);
	}
	
	/**
	 * <p>Close relatived resources.</p>
	 * 
	 * <p>관련된 리소스들을 닫습니다.</p>
	 */
	public void closeResource()
	{
		try
		{
			status.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			packageView.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			parameterGetter.close(true);
		}
		catch(Exception e)
		{
			
		}
		try
		{
			memoryManager.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			binaryConverter.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Set visibility of main window.</p>
	 * 
	 * <p>메인 창을 보이거나 숨깁니다.</p>
	 * 
	 * @param visible : visibility
	 */
	protected abstract void setVisible(boolean visible);
	
	/**
	 * <p>Return save-file-path-textfield value</p>
	 * 
	 * <p>저장 경로 텍스트필드 안의 값을 반환합니다.</p>
	 * 
	 * @return text on textfield
	 */
	protected abstract String selectedSaveFile();
	/**
	 * <p>Return load-file-path-textfield value</p>
	 * 
	 * <p>불러오기 경로 텍스트필드 안의 값을 반환합니다.</p>
	 * 
	 * @return text on textfield
	 */
	protected abstract String selectedLoadFile();
	/**
	 * <p>Return selected programming-language syntax</p>
	 * 
	 * <p>선택된 프로그래밍 언어 문법 이름을 반환합니다.</p>
	 * 
	 * @return selected programming-language syntax 
	 */
	protected abstract String selectedSyntax();
	
	/**
	 * <p>Return class-name-textfield value</p>
	 * 
	 * <p>클래스 이름 텍스트필드 안의 값을 반환합니다.</p>
	 * 
	 * @return class name user-input.
	 */
	protected abstract String takeClassName();
	/**
	 * <p>Return method-name-textfield value</p>
	 * 
	 * <p>메소드 이름 텍스트필드 안의 값을 반환합니다.</p>
	 * 
	 * @return method name user-input.
	 */
	protected abstract String takeMethodName();
	/**
	 * <p>Return variable-name-textfield value</p>
	 * 
	 * <p>변수 이름 텍스트필드 안의 값을 반환합니다.</p>
	 * 
	 * @return variable name user-input.
	 */
	protected abstract String takeVariableName();
	
	/**
	 * <p>Return true if align checkbox is checked.</p>
	 * 
	 * <p>정렬 체크박스가 체크되어 있으면 true를 반환합니다.</p>
	 * 
	 * @return true if align checkbox is checked
	 */
	protected abstract boolean takeAlignCheck();
	
	/**
	 * <p>Get yes or no selection from user.</p>
	 * 
	 * <p>사용자로부터 예, 아니오 선택지를 입력받습니다.</p>
	 * 
	 * @param message : message on the request dialog
	 * @return selection
	 */
	public abstract boolean requestYes(String message);
	
	/**
	 * <p>Get text input from user.</p>
	 * 
	 * <p>사용자로부터 텍스트를 입력받습니다.</p>
	 * 
	 * @param message : message on the request dialog
	 * @return text
	 */
	public abstract String requestInput(String message);
	
	/**
	 * <p>Save contents as file.</p>
	 * 
	 * <p>내용을 파일로 저장합니다.</p>
	 */
	public void save()
	{		
		try
		{
			Controller.setContent(getTextOnArea());
			Controller.setSaveFile(new File(selectedSaveFile()));
			Controller.saveFile(Controller.getSaveFile(), Controller.getContent(), status);
		}
		catch (FileNotFoundException e1)
		{
			Controller.setStatusText(e1.getMessage());
			Controller.println(e1.getMessage());
		}
		catch (Exception e1)
		{
			Controller.println(Controller.getString("There is problem when saving : ") + e1.getMessage());
			Controller.setStatusText(Controller.getString("There is problem when saving : ") + e1.getMessage());
		}
	}
	/**
	 * <p>Load contents from file.</p>
	 * 
	 * <p>컨텐츠를 파일로부터 불러옵니다.</p>
	 */
	public void load()
	{		
		try
		{
			Controller.setLoadFile(new File(selectedLoadFile()));
			Controller.setContent(Controller.readFile(Controller.getLoadFile(), 20, status));
			setTextOnArea(Controller.getContent());			
		}
		catch (FileNotFoundException e1)
		{
			Controller.setStatusText(e1.getMessage());
			alert(Statics.fullErrorMessage(e1));
			Controller.println(e1.getMessage());
			setTextOnArea("");
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			alert(Statics.fullErrorMessage(e1));
			Controller.setStatusText(Controller.getString("There is problem when loading : ") + e1.getMessage());
			Controller.println(Controller.getString("There is problem when loading : ") + e1.getMessage());
		}
	}	
	/**
	 * <p>Convert contents to the method declaration statement.</p>
	 * 
	 * <p>컨텐츠를 메소드 선언문으로 변환합니다.</p>
	 */
	public void convert()
	{
		try
		{
			Controller.setContent(getTextOnArea());
			Controller.selectSyntax(selectedSyntax());
			String parameterFields = getParameterFieldText();
			parameterFields = "--option \"" + String.valueOf(getSyntaxSelectedItem()).trim() + "\" --defaultoption " + parameterFields;
			
			Controller.setContent(Controller.convert(Controller.getSyntax(Controller.getSelectedSyntax()), Controller.getContent(), takeClassName(), takeMethodName(), takeVariableName(), takeAlignCheck(), 20, status, parameterFields));
			setTextOnArea(Controller.getContent());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			alert(Statics.fullErrorMessage(e1));
			Controller.println(Controller.getString("There is problem when converting : ") + e1.getMessage());
			Controller.setStatusText(Controller.getString("There is problem when converting : ") + e1.getMessage());
		}
	}
	
	/**
	 * <p>Return parameter textfield value.</p>
	 * 
	 * <p>매개변수 적는 란에 입력된 텍스트를 반환합니다.</p>
	 * 
	 * @return parameter textfield value
	 */
	public abstract String getParameterFieldText();
	
	/**
	 * <p>Set parameter textfield value.</p>
	 * 
	 * <p>매개변수 적는 란에 텍스트를 입력합니다.</p>
	 * 
	 * @param params : parameter textfield value
	 */
	public abstract void setParameterFieldText(String params);
	
	/**
	 * <p>Return selected syntax combobox index.</p>
	 * 
	 * <p>선택된 프로그래밍 언어 문법 번호를 반환합니다.</p>
	 * 
	 * @return syntax combobox selected index
	 */
	public abstract int getSyntaxSelectedIndex();
	
	/**
	 * <p>Return selected syntax combobox value.</p>
	 * 
	 * <p>선택된 프로그래밍 언어 문법 내용을 반환합니다.</p>
	 * 
	 * @return syntax combobox selected index
	 */
	public abstract String getSyntaxSelectedItem();
	
	/**
	 * <p>Return window object. (JDialog, Dialog, or JFrame, Frame)</p>
	 * 
	 * <p>Window 객체를 반환합니다.</p>
	 * 
	 * @return Window object
	 */
	public abstract Window getWindow();
	
	/**
	 * 
	 * <p>Open file-open dialog to take file path.</p>
	 * 
	 * <p>파일 불러오기 창을 엽니다.</p>
	 */
	public void loadFileSelect()
	{
		loadFileSelect(false);
	}
	/**
	 * 
	 * <p>Open file-open dialog to take file path.
	 * If loadAfterThis is true, load contents from selected file.
	 * If loadAfterThis is false, just open file-open dialog.</p>
	 * 
	 * <p>파일 불러오기 창을 엽니다.
	 * loadAfterThis 가 true 이면, 선택된 파일로부터 컨텐츠를 불러옵니다.
	 * false 이면 파일을 선택하기만 합니다.</p>
	 * 
	 * @param loadAfterThis : If this is true, file will be loaded
	 */
	public abstract void loadFileSelect(boolean loadAfterThis);
	/**
	 * 
	 * <p>Open file-save dialog to take file path.</p>
	 * 
	 * <p>파일 저장 창을 엽니다.</p>
	 */
	public void saveFileSelect()
	{
		saveFileSelect(false);
	}
	/**
	 * 
	 * <p>Open file-save dialog to take file path.
	 * If saveAfterThis is true, save contents from selected file.
	 * If saveAfterThis is false, just open file-save dialog.</p>
	 * 
	 * <p>파일 저장 창을 엽니다..
	 * saveAfterThis 가 true 이면, 선택된 파일에 내용을 저장합니다.
	 * false 이면 파일을 선택하기만 합니다.</p>
	 * 
	 * @param saveAfterThis : If this is true, file will be saved
	 */
	public abstract void saveFileSelect(boolean saveAfterThis);
	
	/**
	 * <p>This method is called by Converter object when new package is arrived.</p>
	 * 
	 * <p>이 메소드는 패키지가 이 곳에 도착했을 때 Converter 객체에 의해 호출됩니다.</p>
	 * 
	 */
	public void refreshPackageList()
	{
		
	}
	
	/**
	 * <p>This method is called by other object which is included this..</p>
	 * 
	 * <p>이 메소드는 이 객체에 포함된 다른 객체에 의해 호출됩니다.</p>
	 */
	public void refreshObjects()
	{
		
	}
	
	/**
	 * <p>Ask user to use online contents at first time.</p>
	 * 
	 * <p>처음 사용 시 사용자에게 온라인 컨텐츠 사용 여부를 묻습니다.</p>
	 */
	public void askUseOnlineContentsIfFirst()
	{
		if(Controller.firstTime)
		{
			int defaultOnlineUseOption = Controller.DEFAULT_ONLINE_USE;
			if(defaultOnlineUseOption != 0) return;
			if(requestYes(Controller.getString("Do you want to use online contents?")))
			{
				Controller.setOption("useOnline", "true");
				Controller.setOption("autoSave", "3");
				try
				{
					Controller.saveOption();
					Controller.prepareReceiver();
					Controller.prepareSender();
					reinitializeAvailableComponents();
					packageView.refresh();					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					Controller.saveOption();
				}
				catch (Exception e)
				{
					
				}
			}
		}
	}
	
	/**
	 * <p>Reinitialize components.</p>
	 * 
	 * <p>컴포넌트들을 가능한 한 다시 초기화합니다.</p>
	 */
	public void reinitializeAvailableComponents()
	{
		
	}
	
	@Override
	public void windowActivated(WindowEvent e)
	{
		
	}
	@Override
	public void windowClosed(WindowEvent e)
	{
		
	}	
	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
	}
	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
	}
	@Override
	public void windowIconified(WindowEvent e)
	{
		
	}
	@Override
	public void windowOpened(WindowEvent e)
	{
		
	}
	
	/**
	 * <p>Run when user try to close the window.
	 * Recommended to override this method.</p>
	 * 
	 * <p>이 메소드는 사용자가 창을 닫으려 했을 때 실행됩니다.
	 * 필요 시 이 메소드를 오버라이드해 사용하세요.</p>
	 */
	public void windowClosing(WindowEvent e)
	{
		
	}
	
	/**
	 * <p>Print out text on screen.</p>
	 * 
	 * <p>텍스트를 화면에 보입니다.</p>
	 * 
	 * @param str text 문자열
	 */
	public abstract void setTextOnArea(String str);
	
	/**
	 * <p>Clear text area.</p>
	 * 
	 * <p>중앙의 텍스트 영역을 비웁니다.</p>
	 * 
	 */
	public void clearArea()
	{
		setTextOnArea("");
	}
	
	/**
	 * <p>Copy text to the clipboard.</p>
	 * 
	 * <p>클립보드로 텍스트를 복사합니다.</p>
	 * 
	 * @param str : text to the clipboard
	 */
	public static void setClipboard(String str)
	{
		try
		{
			String target = str;
			if(target == null) target = "";
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection contents = new StringSelection(target);
			clipboard.setContents(contents, null);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * <p>Return text which is on screen.</p>
	 * 
	 * <p>화면에 있는 텍스트를 반환합니다.</p>
	 * 
	 * @return text 문자열
	 */
	public abstract String getTextOnArea();
	
	/**
	 * <p>Show message dialog.</p>
	 * 
	 * <p>메시지 대화 상자를 엽니다.</p>
	 * 
	 * @param str : text
	 */
	public abstract void alert(String str);
	
	/**
	 * <p>Show or hide loading, saving fields.</p>
	 * 
	 * <p>저장, 불러오기 영역을 숨기거나 보입니다.</p>
	 */
	public abstract void reverseHiding();
	
	/**
	 * <p>Refresh module list</p>
	 * 
	 * <p>모듈 리스트를 다시 불러옵니다.</p>
	 */
	public abstract void refreshModule();
	/**
	 * <p>This method will be run when user use mode selector.</p>
	 * 
	 * <p>이 메소드는 사용자가 모드를 선택한 경우 실행됩니다.</p>
	 * 
	 * @param index
	 */
	public abstract void selectModule(int index);
	
	private void firstSelectModule()
	{
		String className = "";
		String defaultModuleName = Controller.getOption("defaultModuleName");
		if(defaultModuleName == null) return;
		else
		{
			defaultModuleName = defaultModuleName.trim();
		}
		if(defaultModuleName.equalsIgnoreCase(""))
		{
			return;
		}
		else if(defaultModuleName.equalsIgnoreCase("JDBC"))
		{
			defaultModuleName = "InsertTokenModule";
		}
		for(int i=0; i<Controller.getModules().size(); i++)
		{
			try
			{
				className = Controller.getModules().get(i).getClass().getName();
				StringTokenizer pointToken = new StringTokenizer(className, ".");
				String lastClassName = "";
				while(pointToken.hasMoreTokens())
				{
					lastClassName = pointToken.nextToken();
				}
				if(lastClassName.equals(defaultModuleName))
				{
					// selectModule(i);
					setSelectModuleComboBox(i);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <p>Select i'th module selector on GUI.</p>
	 * 
	 * <p>i 번째 모듈을 GUI로 선택한 것처럼 동작합니다.</p>
	 * 
	 * @param i : Index
	 */
	protected void setSelectModuleComboBox(int i)
	{
		
	}
	
	@Override
	public String getThreadName()
	{
		return this.getClass().getCanonicalName();
	}
	
	@Override
	public void start()
	{
		open();
	}
	
	@Override
	public boolean isAlive()
	{
		return threadswitch;
	}
	
	/**
	 * <p>This method will be run simultaneously.
	 * Recommended to override this method when you need to use thread.</p>
	 * 
	 * <p>이 메소드는 병렬적으로 반복 실행됩니다.
	 * 쓰레드 사용이 필요한 경우 이 메소드를 재정의해 사용하세요.</p>
	 */
	protected void runInThread()
	{
		
	}
	
	/**
	 * <p>It is not a good idea to override this constructor.
	 * Please use runInThread() method instead.</p>
	 * 
	 * <p>이 메소드를 재정의하는 것은 좋은 생각이 아닙니다.
	 * runInThread() 메소드를 대신 사용하세요.</p>
	 */
	@Override
	public void run()
	{
		while(threadswitch)
		{
			if(loadNextTime)
			{	
				Controller.println("Order : Loading", true);
				load();
				enableAll(true);
				loadNextTime = false;
			}
			if(saveNextTime)
			{
				Controller.println("Order : Saving", true);
				save();	
				enableAll(true);
				saveNextTime = false;
			}
			try
			{
				runInThread();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try
			{
				Thread.sleep(50 + (int)(Math.random() * 10));
			}
			catch (InterruptedException e)
			{
				
			}
		}		
	}	
	@Override
	public long getId()
	{
		return id;
	}
	@Override
	public int getSelectedModule()
	{
		return Controller.getSelectedMode();
	}

	@Override
	public String getSelectedModuleParamHelp()
	{
		return Controller.getModules().get(getSelectedModule()).getParameterHelp();
	}
	@Override
	public List<String> getSelectedModuleParameterKeys()
	{
		return Controller.getModules().get(getSelectedModule()).parameterKeyList();
	}
	@Override
	public String getSelectedModuleName()
	{
		return Controller.getModules().get(getSelectedModule()).getName(Controller.getSystemLocale());
	}
	
	@Override
	public void refresh()
	{
		refreshModule();
		refreshObjects();
		refreshPackageList();
	}
	
	/**
	 * <p>This method is called when user select "Module Editor" in menu.</p>
	 * 
	 * <p>이 메소드는 사용자가 "모듈 에디터"를 메뉴에서 선택했을 때 실행됩니다.</p>
	 */
	public void moduleEditorOpenMenuSelected()
	{
		moduleEditor.open();
	}
	
	/**
	 * <p>Ask port number, root ID and passwords to run daemon.</p>
	 * 
	 * <p>데몬을 실행하기 위한 포트 번호와 ID, 비밀번호 등을 물어보고 데몬을 실행합니다.</p>
	 */
	protected void runDaemon()
	{
		try
		{
			if(daemon != null)
			{
				boolean askClose = Controller.requestYes(Controller.getString("Daemon is already running") + ".\n" + Controller.getString("Do you want to close it?"));
				if(askClose) daemon.close();
				else return;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		String port, id, pw, dKey;
		port = Controller.requestInput(Controller.getString("Please input the port number") + "\n" 
		       + "(" + Controller.getString("Default") + " : " + String.valueOf(Daemon.default_port) + ", " + Controller.getString("Optional") + ")");		
		id = Controller.requestInput(Controller.getString("Please input the root ID"));
		if(id == null || id.equals("")) return;
		pw = Controller.requestInput(Controller.getString("Please input the root Password"));
		if(pw == null || pw.equals("")) return;
		
		dKey = Controller.requestInput(Controller.getString("Please input the login key") + "(" + Controller.getString("Optional") + ")");
		if(dKey == null || dKey.equals("")) dKey = Controller.DEFAULT_DAEMON_KEY;
		
		boolean askLast = Controller.requestYes(Controller.getString("Please see following") + "...\n"
				 + Controller.getString("Port") + " : " + port + "\n"
				 + Controller.getString("Root ID") + " : " + id + "\n"
				 + Controller.getString("Root PW") + " : " + pw + "\n"
				 + Controller.getString("Login Key") + " : " + dKey + "\n"
				 + Controller.getString("Other user who know ID and password will be able to access here") + ".\n"
				 + Controller.getString("Do you want to run daemon?"));
		if(askLast)
		{
			try
			{				
				if(port == null) daemon = new Daemon(new ScriptModule(false), id, pw, dKey);
				else
				{
					try
					{
						daemon = new Daemon(new ScriptModule(false), Integer.parseInt(port), id, pw, dKey);
					}
					catch (NumberFormatException e)
					{
						daemon = new Daemon(new ScriptModule(false), id, pw, dKey);
					}
				}
				daemon.start();
				Controller.println("Daemon is started", true);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Controller.alert(Statics.fullErrorMessage(e));
			}
		}
	}
	
	/**
	 * <p>Run GUI based daemon client.</p>
	 * 
	 * <p>데몬 클라이언트를 실행합니다.</p>
	 */
	protected void runDaemonClient()
	{
		daemonClient.open();
	}
}
