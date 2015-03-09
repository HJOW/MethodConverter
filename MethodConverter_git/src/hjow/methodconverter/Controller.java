package hjow.methodconverter;
import hjow.convert.javamethods.ConsoleContainer;
import hjow.convert.module.CharEncoder;
import hjow.convert.module.CompressModule;
import hjow.convert.module.ConvertModule;
import hjow.convert.module.DaemonClient;
import hjow.convert.module.DecryptModule;
import hjow.convert.module.EncryptModule;
import hjow.convert.module.HashModule;
import hjow.convert.module.InsertTokenModule;
import hjow.convert.module.IsNotDirectoryException;
import hjow.convert.module.NoSuchModuleException;
import hjow.convert.module.ReadWebModule;
import hjow.convert.module.RemoveCommentModule;
import hjow.convert.module.ReturnSelfMethod;
import hjow.convert.module.ScriptModule;
import hjow.convert.module.TCPClientModule;
import hjow.convert.module.TCPHostModule;
import hjow.convert.module.TCPSender;
import hjow.convert.module.UDPSender;
import hjow.convert.module.UserDefinedByteModule;
import hjow.convert.module.UserDefinedModule;
import hjow.convert.module.plugin.ModulePlugin;
import hjow.daemon.Daemon;
import hjow.messenger.Messenger;
import hjow.methodconverter.awtconverter.AWTBinaryConverter;
import hjow.methodconverter.awtconverter.AWTManager;
import hjow.methodconverter.awtconverter.AWTPrinter;
import hjow.methodconverter.awtconverter.AWTScriptConsole;
import hjow.methodconverter.swingconverter.SwingDaemonClient;
import hjow.methodconverter.ui.GUIBinaryConverter;
import hjow.methodconverter.ui.GUIPrinter;
import hjow.methodconverter.ui.Manager;
import hjow.methodconverter.ui.RGB;
import hjow.methodconverter.ui.StatusBar;
import hjow.methodconverter.ui.StatusViewer;
import hjow.network.Communicator;
import hjow.network.DoNotConnectedException;
import hjow.network.NetworkPackage;
import hjow.network.Receiver;
import hjow.network.Refreshable;
import hjow.network.Sender;
import hjow.network.TCPReceiver;
import hjow.network.UDPReceiver;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p>This class has main method.</p>
 * 
 * <p>This class have many of method to create "method" string.
 * This class help you to create new Java method declaration statements which is return String value.</p>
 * 
 * <p>이 클래스는 main 메소드를 포함하고 있습니다.
 * 바로 실행할 경우 GUI 환경에서 실행됩니다.</p>
 * 
 * <p>콘솔 상에서 사용하려는 경우 다음과 같이 여러 매개 변수를 부여해야 합니다.<br>
 * --gui : GUI로 실행할 지 console 로 실행할 지를 결정합니다. gui 혹은 console 을 값으로 사용합니다.<br>
 * --load : 불러올 텍스트 파일의 절대 경로를 지정합니다.<br>
 * --save : 결과를 저장할 절대 경로(파일 이름 포함)를 지정합니다.<br>
 * --class : 클래스 이름을 지정합니다.<br>
 * --method : 메소드 이름을 지정합니다.<br>
 * --variable : 변수 이름을 지정합니다.</p>
 * 
 * <p>예를 들어 다음과 같은 형식으로 사용합니다.</p>
 * 
 * <p>java -jar methodConverter.jar --gui console --load "C:\Users\HJOW\Desktop\1.txt" --save "C:\Users\HJOW\Desktop\Writer.java"  --class "Writer" --method "write" --variable "r"</p>
 * 
 * <p>이외에도, 이 클래스에는 "메소드" 문자열을 생성하기 위한 여러 메소드가 포함되어 있습니다.
 * 이 클래스는 당신이 원하는 문자열을 반환하는 자바 메소드 선언문을 생성하는 데 도움을 줍니다.</p>
 * 
 * @author HJOW
 *
 */
public class Controller
{
	/**
	 * <p>Version of this program.</p>
	 * 
	 * <p>이 프로그램의 버전 정보를 담은 배열입니다.</p>
	 */
	public static int[] versions = {1, 0, 1, 7};
	
	/**
	 * <p>Build number.</p>
	 * 
	 * <p>빌드 번호입니다.</p>
	 */
	public static long buildNumber = 100000000000L + (versions[0] * 1000000000L) + (versions[1] * 100000000L) + (versions[2] * 10000000L) + (versions[3] * 1000000L);
	
	/**
	 * <p>If this is true, build number is appear on the about dialog.</p>
	 * 
	 * <p>이 값이 true 이면 About 대화 상자에 빌드 번호가 표시됩니다.</p>
	 */
	public static boolean showBuildNumber = false;
	
	/**
	 * <p>Part of default daemon-login password.</p>
	 * 
	 * <p>기본 데몬 로그인 비밀번호의 일부분입니다.</p>
	 */
	public static final String DEFAULT_DAEMON_KEY = "39581";
	
	/**
	 * <p>Module which will be selected at the first time.</p>
	 * 
	 * <p>프로그램이 실행되었을 때 선택되어 있어야 할 모듈 이름을 지정합니다.</p>
	 */
	public static final String DEFAULT_MODULE_NAME = "";
	
	/**
	 * <p>If this is -1, use-online-content option will be turn off at the first time.
	 * <br>If this is 1, use-online-content option will be turn on at the first time.
	 * <br>If this is 0, work default.</p>
	 * 
	 * <p>온라인 컨텐츠 사용 여부를 선택합니다.
	 * <br>0 이면 처음 실행 시 온라인 컨텐츠 사용 여부를 묻습니다. 이후 설정 파일에 저장됩니다.
	 * <br>-1 이면 온라인 컨텐츠를 사용하지 않도록 설정합니다. 설정 화면에서 사용하도록 할 수는 있습니다.
	 * <br>1 이면 온라인 컨텐츠를 사용하도록 설정합니다. 설정 화면에서 사용하도록 할 수는 있습니다.</p>
	 */
	public static final int DEFAULT_ONLINE_USE = 0;
	
	/**
	 * <p>Default GUI-opacity ratio. 0.0f ~ 1.0f values are available.
	 * <br>This value can be changed by settings.</p>
	 * 
	 * <p>기본 선명도 비율입니다. 이 값이 낮으면 GUI 창이 투명해집니다.
	 * <br>이 값은 설정에 영향을 받습니다. 이 값을 편집기로 직접 수정해도 설정 파일에 다른 값으로 되어 있다면 설정 파일의 값이 유효합니다.</p>
	 */
	public static float DEFAULT_OPACITY_RATIO = 1.0F;
	
	/**
	 * <p>Default port number of TCP communication.</p>
	 * 
	 * <p>TCP 통신을 위한 기본 포트 번호</p>
	 */
	public static int default_tcp_receiver_port = 55221;
	
	/**
	 * <p>Default port number of UDP communication.</p>
	 * 
	 * <p>UDP 통신을 위한 기본 포트 번호</p>
	 */
	public static int default_udp_receiver_port = 55222;
	
	/**
	 * <p>Default port number of TCP communication.</p>
	 * 
	 * <p>TCP 통신을 위한 기본 포트 번호</p>
	 */
	public static int default_tcp_sender_port = 55223;
	
	/**
	 * <p>Default port number of UDP communication.</p>
	 * 
	 * <p>UDP 통신을 위한 기본 포트 번호</p>
	 */
	public static int default_udp_sender_port = 55224;
	
	/**
	 * <p>Default UDP packet buffer size.</p>
	 * 
	 * <p>기본 UDP 패킷 버퍼 크기</p>
	 */
	public static int default_udp_buffer_size = 50000;
	
	/**
	 * <p>Time limit of connection.</p>
	 * 
	 * <p>연결에 대한 시간 제한.</p>
	 */
	public static int default_timelimit = 4000;
	
	/**
	 * <p>Program name.</p>
	 * 
	 * <p>프로그램 이름입니다.</p>
	 */
	public static final String TITLES = "Method Converter";
	
	/**
	 * <p>First message of this program. This will be shown on the status bar at the program starts.</p>
	 * 
	 * <p>이 메시지는 프로그램 시작 시 상태 바에 표시됩니다.</p>
	 */
	public static String firstMessage = "Method Converter";
	
	/**
	 * <p>License.</p>
	 * 
	 * <p>라이센스, 저작권 관련 메시지를 표시합니다.</p>
	 */
	public static String licenseMessage = "You can commercial, and non-commercial use this program free.";
	
	/**
	 * <p>This flag means this program runs at the first time at this PC. This is turned off when the option file is exist.</p>
	 * 
	 * <p>이 표시는 이 프로그램이 이 PC에서 처음 실행되었음을 의미합니다. 이 표시는 옵션 파일이 있다는 것이 확인되면 false가 됩니다.</p>
	 */
	public static boolean firstTime = true;	
	
	private static InputStreamReader defaultReader;
	private static BufferedReader defaultBuffered;	
	private static File dirCursor = null;
	
	private static String delimiters = ":";
	private static String defaultPath = "/";
	private static String fileSeparator = "";
	private static String defaultURL = "http://netstorm.woobi.co.kr/methodconverter/";
	
	private static File loadFile, saveFile;
	private static String contents = "";
	private static Hashtable<String, Syntax> syntaxes = new Hashtable<String, Syntax>();
	private static String selectedSyntax = "JAVA";
	private static StatusBar statusField = new ConsoleStatusBar();
	private static FileWriter logWriter = null;
	private static BufferedWriter logBuffer = null;
	static Map<String, StringTable> otherStringTables = new Hashtable<String, StringTable>();
	private static String selectedLanguage = null;
	private static List<String> printTarget = new Vector<String>();
	private static Printer printer = null;
	
	private static List<ThreadRunner> closables = new Vector<ThreadRunner>();
	private static List<Thread> threads = new Vector<Thread>();
	private static List<Refreshable> refreshables = new Vector<Refreshable>();
	
	private static Stack<String> undoStack = new Stack<String>();
	private static int undoListMaxCount = 20;
	
	private static Receiver tcp_receiver = null;
	private static Receiver udp_receiver = null;
	private static Sender tcp_sender = null;
	private static Sender udp_sender = null;
	
	private static String nickEncoding = "SHA-256";
	
	private static OptionTable optionTable = new OptionTable();
	
	private static int now_convert_mode = 0;	
	private static List<ConvertModule> modules = new Vector<ConvertModule>();
	private static List<UserDefinedModule> userDownloadModules = new Vector<UserDefinedModule>();
	private static ScriptModule scriptModule = null;
	
	private static String global_encrypt_key = "";
	
	private static List<Communicator> communicators = new Vector<Communicator>();
	private static List<NetworkPackage> receivedPackage = new Vector<NetworkPackage>();
	
	private static List<ModulePlugin> plugins = new Vector<ModulePlugin>();
	
	private static int loadedScriptsAuthI = 20;
	private static String[] loadedScriptsAuthList = {};
	private static String loadedScripts = "";
	
	private static Manager uimanager;
	
	/**
	 * <p>These static values are meaning each modes.</p>
	 * 
	 * <p>이 정적 상수들은 각각 이 프로그램의 모드를 나타냅니다.</p>
	 */
	public static final int MODE_CONSOLE = 0
	, MODE_GUI = 1
	, MODE_SCRIPT = 2
	, MODE_BINARY = 3
	, MODE_MESSENGER = 7
	, MODE_DAEMON = 8
	, MODE_DAEMON_CLIENT = 9
	, MODE_GUI_SCRIPT = 4
	, MODE_GUI_BINARY = 5
	, MODE_GUI_MESSENGER = 6
	, MODE_GUI_DAEMON_CLIENT = 10;
	
	/**
	 * <p>These static values are meaning programming languages.</p>
	 * 
	 * <p>이 정적 상수들은 프로그래밍 언어들을 나타냅니다.</p>
	 */
	public static final int SYNTAX_JAVA = 0
	, SYNTAX_JAVASCRIPT = 1
	, SYNTAX_JAVA_BUFFER = 2;
	
	/**
	 * <p>These static values are meaning how to concat words when ReturnSelfMethod module works.</p>
	 * 
	 * <p>이 정적 상수들은 메소드 선언문 생성 모듈 동작 시 어떻게 단어들을 합칠 지를 나타냅니다.</p>
	 */
	public static final int CONCAT_NORMAL = 0
	, CONCAT_FUNCTION = 1
	, CONCAT_METHOD = 2;
	
	private static int scriptSecurityLevel = 1;
	
	/**
	 * <p>main method.</p>
	 * 
	 * <p>There are some argument options available.
	 * (Option should be started with "--".)</p>
	 * 
	 * <p>load, save, class, method, variable, locale, gui</p>
	 * 
	 * <p>For example, </p>
	 * 
	 * <p>--load "C:\load.txt" --save "C:\save.txt" --locale "en" --gui 0</p>
	 * 
	 * <p>"load" option select file to load. This file contents will be returned by the method created.
	 * "save" option select file to save.<br>
	 * If "gui" option is 0, this program will be started as console mode.</p>
	 * 
	 * <p>If you do not use arguments, then the program will be started with GUI.</p>
	 * 
	 * <p>메인 메소드입니다.</p>
	 * 
	 * <p>여러 매개 변수를 사용할 수 있습니다. 옵션은 -- 로 시작해야 합니다.</p>
	 * 
	 * <p>매개 변수를 사용하지 않으면 GUI 모드로 실행됩니다.</p>
	 * 
	 * @param args : arguments as String[]
	 */
	public static void main(String[] args)
	{
		int mode = Controller.MODE_CONSOLE;
		
		// mode = Controller.MODE_SCRIPT;
		
		prepareDefaultInputStream();
		
		String className = "Converted", methodName = "text", varName = "r";
		boolean guiOrNot = true;
		
		// Prepare first message
		for(int i=0; i<Controller.versions.length; i++)
		{
			if(i == 0) Controller.firstMessage = Controller.firstMessage + " v" + String.valueOf(Controller.versions[i]);
			else Controller.firstMessage = Controller.firstMessage + "." + String.valueOf(Controller.versions[i]);
		}
					
		// Prepare default path
		try
		{
			try
			{
				fileSeparator = System.getProperty("file.separator");
			}
			catch (Exception e)
			{
				fileSeparator = "/";
			}
			Controller.setDefaultPath(System.getProperty("user.home"));
			if(! (Controller.getDefaultPath().endsWith(fileSeparator)))
			{
				Controller.setDefaultPath(Controller.getDefaultPath() + fileSeparator);
				Controller.setDefaultPath(Controller.getDefaultPath() + "method_converter" + fileSeparator);
			}
		}
		catch(Exception e)
		{
			fileSeparator = "/";
			Controller.setDefaultPath("/");			
		}
		try
		{
			Controller.setDirCursor(new File(Controller.getDefaultPath()));
		}
		catch(Exception e)
		{			
			try
			{
				Controller.setDirCursor(new File(System.getProperty("user.home")));
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
		// Get system locale
		Controller.selectedLanguage = Controller.getSystemLocale();
		
		// Try to read config
		prepareOptions();
				
		String securities = getOption("securityLevel");
		if(securities != null)
		{
			try
			{
				scriptSecurityLevel = Integer.parseInt(securities);
			}
			catch(Exception e)
			{
				scriptSecurityLevel = 1;
			}
		}
			
		// Get string table
		Controller.otherStringTables = loadStringTables();	
		
		// Prepare argument list
		String arg_accum = "";
		
		// Accumulates all arguments into arg_accum
		for(String s : args)
		{
			if(s != null)
			{
				arg_accum = arg_accum + s + " ";
			}
		}
		
			
		
		
		// Apply options from arguments
		Map<String, String> getArguments = ParameterGetter.toParameter(arg_accum);
		Vector<String> argkeys = new Vector<String>();
		argkeys.addAll(getArguments.keySet());
		for(String k : argkeys)
		{
			System.out.println(k + " : " + getArguments.get(k));
			if(k.equalsIgnoreCase("gui"))
			{
				if(getArguments.get(k) == null)
				{
					guiOrNot = true;
				}
				else if(getArguments.get(k).equalsIgnoreCase("gui"))
				{
					guiOrNot = true;
				}
				else if(getArguments.get(k).equalsIgnoreCase("console"))
				{
					guiOrNot = false;
				}
				else
				{
					guiOrNot = Statics.parseBoolean(getArguments.get(k));
				}
			}
			else if(k.equalsIgnoreCase("script"))
			{
				if(Statics.parseBoolean(getArguments.get(k))) mode = MODE_SCRIPT;
			}
			else if(k.equalsIgnoreCase("binary"))
			{
				if(Statics.parseBoolean(getArguments.get(k))) mode = MODE_BINARY;
			}
			else if(k.equalsIgnoreCase("messenger"))
			{
				if(Statics.parseBoolean(getArguments.get(k))) mode = MODE_MESSENGER;
			}
			else if(k.equalsIgnoreCase("daemon"))
			{
				if(Statics.parseBoolean(getArguments.get(k))) mode = MODE_DAEMON;
			}
			else if(k.equalsIgnoreCase("daemon_client"))
			{
				if(Statics.parseBoolean(getArguments.get(k))) mode = MODE_DAEMON_CLIENT;
			}
			else if(k.equalsIgnoreCase("load"))
			{
				loadFile = new File(getArguments.get(k));
			}
			else if(k.equalsIgnoreCase("save"))
			{
				saveFile = new File(getArguments.get(k));
			}
			else if(k.equalsIgnoreCase("class"))
			{
				className = getArguments.get(k);
			}
			else if(k.equalsIgnoreCase("method"))
			{
				methodName = getArguments.get(k);
			}
			else if(k.equalsIgnoreCase("variable"))
			{
				varName = getArguments.get(k);
			}
			else if(k.equalsIgnoreCase("locale"))
			{
				selectedLanguage = new String(getArguments.get(k));
			}
			else if(k.equalsIgnoreCase("mode"))
			{
				try
				{
					now_convert_mode = Integer.parseInt(getArguments.get(k));
				}
				catch(NumberFormatException e)
				{
					for(int j=0; j<modules.size(); j++)
					{
						if(getArguments.get(k).equals(modules.get(j).getName(selectedLanguage)))
						{
							now_convert_mode = j;
							break;
						}
					}
				}
			}
			else if(k.equalsIgnoreCase("theme") || k.equalsIgnoreCase("look_and_feel"))
			{
				setOption("theme", new String(getArguments.get(k)));
			}
			else
			{
				setOption(k, getArguments.get(k));
			}
		}
		
		if(getOption("log") != null)
		{
			try
			{
				if(Boolean.parseBoolean(getOption("log")))
				{
					newLogger();
				}
			}
			catch(Exception e)
			{
				
			}		
		}
		
		// Load packages from file if exists
		loadPackages();
		
		// GUI mode checking
		switch(mode)
		{
		case MODE_CONSOLE:
			if(guiOrNot)
			{
				mode = MODE_GUI;				
			}
			break;
		case MODE_GUI:
			if(! guiOrNot)
			{
				mode = MODE_CONSOLE;				
			}
			break;
		case MODE_SCRIPT:
			if(guiOrNot)
			{
				mode = MODE_GUI_SCRIPT;				
			}
			break;
		case MODE_MESSENGER:
			if(guiOrNot)
			{
				mode = MODE_GUI_MESSENGER;				
			}
			break;
		case MODE_GUI_SCRIPT:
			if(! guiOrNot)
			{
				mode = MODE_SCRIPT;				
			}
			break;
		case MODE_BINARY:
			if(guiOrNot)
			{
				mode = MODE_GUI_BINARY;				
			}
			break;
		case MODE_GUI_BINARY:
			if(! guiOrNot)
			{
				mode = MODE_BINARY;				
			}
			break;
		case MODE_DAEMON_CLIENT:
			if(guiOrNot)
			{
				mode = MODE_GUI_DAEMON_CLIENT;				
			}
			break;
		case MODE_GUI_DAEMON_CLIENT:
			if(! guiOrNot)
			{
				mode = MODE_DAEMON_CLIENT;				
			}
			break;
		case MODE_GUI_MESSENGER:
			if(! guiOrNot)
			{
				mode = MODE_MESSENGER;				
			}
			break;
		}
		
		// Create printer object
		printer = new Printer();
		printer.open();
		
		// Prepare modules
		prepareModules(guiOrNot);
		preparePlugins();
		
		prepareScripts();
		
		
		// Print out several options
		try
		{
			Controller.println("Default path : " + getDefaultPath());
			Controller.println("Locale : " + String.valueOf(getLanguage().get("localename")));
		}
		catch(Exception e)
		{
			
		}
		
		// Run the program
		switch(mode)
		{
		case MODE_CONSOLE:
			runOnConsole(loadFile, saveFile, className, methodName, varName);
			break;
		case MODE_GUI:
			runOnGUI(selectedLanguage);			
			break;
		case MODE_SCRIPT:
			runAsScriptConsole();
			break;
		case MODE_GUI_SCRIPT:
			runAsScriptGUI();
			break;
		case MODE_BINARY:
			ByteConverter.runBinaryConverter(null);
			break;
		case MODE_GUI_BINARY:
			GUIBinaryConverter binaryConverter = null;
			try
			{
				binaryConverter = new hjow.methodconverter.swingconverter.SwingBinaryConverter();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				binaryConverter = new AWTBinaryConverter();
			}
			binaryConverter.open();
			break;
		case MODE_MESSENGER:
			new Messenger().runMain(false);
			break;
		case MODE_GUI_MESSENGER:
			new Messenger().runMain(true);
			break;
		case MODE_DAEMON:
			runDaemon();
			break;
		case MODE_DAEMON_CLIENT:
			runDaemonClient(false);
			break;
		case MODE_GUI_DAEMON_CLIENT:
			runDaemonClient(true);
			break;
		}		
	}

	private static void prepareScripts()
	{
		File defaultPaths = null;
		String reads = "";
		String authLine = "";
		StringTokenizer tokenizer = null;
		try
		{
			defaultPaths = new File(defaultPath);
			if(defaultPaths.exists())
			{
				File[] files = defaultPaths.listFiles();
				for(int i=0; i<files.length; i++)
				{	
					if(files[i].getAbsolutePath().endsWith(".js") || files[i].getAbsolutePath().endsWith(".JS"))
					{						
						reads = readFile(files[i], 20, null);
						tokenizer = new StringTokenizer(reads, "\n");
						if(tokenizer.hasMoreTokens())
						{
							tokenizer.nextToken();
						}
						if(tokenizer.hasMoreTokens())
						{
							authLine = tokenizer.nextToken();
							if(authLine != null)
							{
								authLine = authLine.trim();
								if(authLine.length() >= 10)
								{
									authLine = authLine.substring(2, authLine.length());
									authLine = authLine.trim();
									
									long authValues = 0;
									boolean loaded = false;
									char[] chars = authLine.toCharArray();
									for(int j=0; j<chars.length; j++)
									{
										authValues = authValues + ((int) chars[j]);
									}
									
									if(loadedScriptsAuthList != null)
									{
										for(int j=0; j<loadedScriptsAuthList.length; j++)
										{
											if(authLine.equals(loadedScriptsAuthList[j]))
											{
												loadedScripts = loadedScripts + reads + "\n";
												loaded = true;
												break;
											}
										}
									}
									
									if(loadedScriptsAuthI >= 1 && (! loaded))
									{
										if(authValues % loadedScriptsAuthI == 0)
										{
											loadedScripts = loadedScripts + reads + "\n";
										}
									}
								}
							}							
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * <p>Create new sender object and connections.</p>
	 * 
	 * <p>패키지 송신 객체와 연결을 생성합니다.</p>
	 */
	public static void prepareSender()
	{
		try
		{
			tcp_sender.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			udp_sender.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			if((udp_receiver != null) && (udp_receiver instanceof UDPReceiver))
			{
				udp_sender = new UDPSender(((UDPReceiver) udp_receiver).getSocketIfExist());
			}
			else udp_sender = new UDPSender();
			
			tcp_sender = new TCPSender();
			
			modules.add(tcp_sender);
			modules.add(udp_sender);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try
			{
				if(tcp_sender != null) modules.remove(tcp_sender);
			}
			catch(Exception e1)
			{
				
			}
			try
			{
				if(udp_sender != null) modules.remove(udp_sender);
			}
			catch(Exception e1)
			{
				
			}
			try
			{
				tcp_sender.close();
			}
			catch(Exception e1)
			{
				
			}
			try
			{
				udp_sender.close();
			}
			catch(Exception e1)
			{
				
			}
			tcp_sender = null;	
			udp_sender = null;
		}	
	}

	/**
	 * <p>Create new receiver object and connections.</p>
	 * 
	 * <p>패키지 수신 객체와 연결을 생성합니다.</p>
	 */
	public static void prepareReceiver()
	{
		try
		{
			tcp_receiver.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			udp_receiver.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{			
			tcp_receiver = new TCPReceiver();	
		}
		catch(Exception e)
		{
			try
			{
				if((e instanceof java.net.BindException) || (e instanceof DoNotConnectedException))
				{
					
				}
				else e.printStackTrace();
			}
			catch(Exception e1)
			{
				e.printStackTrace();
			}
			
			try
			{
				tcp_receiver.close();
			}
			catch(Exception e1)
			{
				
			}
			tcp_receiver = null;
		}
		try
		{			
			udp_receiver = new UDPReceiver();	
		}
		catch(Exception e)
		{
			try
			{
				if((e instanceof java.net.BindException) || (e instanceof DoNotConnectedException))
				{
					
				}
				else e.printStackTrace();
			}
			catch(Exception e1)
			{
				e.printStackTrace();
			}
			
			try
			{
				udp_receiver.close();
			}
			catch(Exception e1)
			{
				
			}
			udp_receiver = null;
		}
		try
		{
			if(tcp_sender instanceof UDPSender)
			{
				tcp_sender.close();
				modules.remove(tcp_sender);
				tcp_sender = new UDPSender(((UDPReceiver) udp_receiver).getSocketIfExist());
				modules.add(tcp_sender);
				
				uimanager.selectModule(modules.indexOf(tcp_sender));
				uimanager.refreshModule();				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Close package receiver object.</p>
	 * 
	 * <p>패키지 수신 객체를 닫습니다.</p>
	 */
	public static void closeReceiver()
	{
		try
		{
			tcp_receiver.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			udp_receiver.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Close package sender object.</p>
	 * 
	 * <p>패키지 송신 객체를 닫습니다.</p>
	 */
	public static void closeSender()
	{
		try
		{
			tcp_sender.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Insert and load options and configurations.</p>
	 * 
	 * <p>옵션과 설정을 가져옵니다.</p>
	 */
	public static void prepareOptions()
	{
		prepareOptions(true);
	}

	/**
	 * <p>Insert and load options and configurations.</p>
	 * 
	 * <p>옵션과 설정을 가져옵니다.</p>
	 * 
	 * @param loadFiles : If this is true, load options from file
	 */
	public static void prepareOptions(boolean loadFiles)
	{
		setOption("useBeta", "false");
		setOption("useOnline", "false");
		setOption("defaultURL", defaultURL);
		setOption("securityLevel", "1");
		setOption("quickexit", "false");
		setOption("defaultDaemonKey", DEFAULT_DAEMON_KEY);
		setOption("defaultModuleName", DEFAULT_MODULE_NAME);
		setOption("showLineNumbers", "true");
		
		int defaultOnlineUseOption = DEFAULT_ONLINE_USE;
		if(defaultOnlineUseOption != 0)
		{
			boolean onlineUseOption = (DEFAULT_ONLINE_USE > 0);
			if(onlineUseOption)
			{
				setOption("useOnline", "true");
			}
			else
			{
				setOption("useOnline", "false");
			}
		}
		
		if(loadFiles)
		{
			try
			{
				File optionFile = new File(Controller.getDefaultPath() + "config.cfg");
				if(optionFile.exists())
				{
					Controller.loadOption(optionFile);
					firstTime = false;
				}
				else firstTime = true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			String getOpacity = getOption("gui_opacity");
			if(getOpacity != null) DEFAULT_OPACITY_RATIO = (float)(Double.parseDouble(getOpacity));
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * <p>Load string tables from files.</p>
	 * 
	 * <p>파일들로부터 스트링 테이블들을 불러옵니다.</p>
	 * 
	 * @return string table
	 */
	public static Map<String, StringTable> loadStringTables()
	{
		Map<String, StringTable> stringTables = Statics.defaultLanguages();
		File defaultPaths = null;
		String reads;
		Map<String, String> ones;
		try
		{
			defaultPaths = new File(defaultPath);
			if(defaultPaths.exists())
			{
				File[] files = defaultPaths.listFiles();
				for(int i=0; i<files.length; i++)
				{	
					if(files[i].getAbsolutePath().endsWith(".lang") || files[i].getAbsolutePath().endsWith(".LANG"))
					{						
						reads = readFile(files[i], 20, null);
						ones = Statics.stringToHashtable(reads);
						
						if(ones.get("localename") != null) stringTables.put(ones.get("localename"), new DefaultStringTable(ones));
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return stringTables;
	}
	
	/**
	 * <p>Save string tables as files.</p>
	 * 
	 * <p>스트링 테이블들을 저장합니다.</p>
	 * 
	 */
	public static void saveStringTables()
	{
		Set<String> keys = otherStringTables.keySet();
		for(String k : keys)
		{
			try
			{
				saveFile(new File(defaultPath + k + ".lang"), otherStringTables.get(k).serialize(), null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <p>Get grade of serial input.</p>
	 * 
	 * <p>시리얼 인증 수준을 반환합니다.</p>
	 * 
	 * @return grade
	 */
	public static int getGrade()
	{
		String serials = getOption("serial");
		if(serials == null) return 0;
		
		serials = new DecryptModule().convert(serials, 20, "AES", "serial", "special", "longcode");
		if(serials == null) return 0;
		serials = serials.trim();
		return getGrade(serials);
	}
	
	/**
	 * <p>Get RGB data of grade.</p>
	 * 
	 * <p>인증 수준에 따른 RGB 값을 반환합니다.</p>
	 * 
	 * @return RGB data
	 */
	public static RGB getGradeRGB()
	{
		RGB newRGB = null;
		switch(getGrade())
		{
		case 1:
			newRGB = new RGB(0, 255, 255);
			break;
		case 2:
			newRGB = new RGB(255, 0, 0);
			break;
		case 3:
			newRGB = new RGB(255, 255, 0);
			break;
		case 4:
			newRGB = new RGB(50, 50, 70);
			break;
		default:
			newRGB = new RGB(255, 255, 255);
			break;
		}
		return newRGB;
	}
	
	/**
	 * <p>Get grade of serial input.</p>
	 * 
	 * <p>시리얼 인증 수준을 반환합니다.</p>
	 * 
	 * @param serial : serial code
	 * @return grade
	 */
	public static int getGrade(String serial)
	{
		String serials = serial;
		if(serials == null) return 0;		
		
		StringTokenizer slashToken = new StringTokenizer(serials, "-");
		List<String> slashes = new Vector<String>();
		while(slashToken.hasMoreTokens())
		{
			slashes.add(slashToken.nextToken());
		}
		long auths = 0;
		for(int i=0; i<slashes.size(); i++)
		{
			char[] charArray = slashes.get(i).toCharArray();
			for(int j=0; j<charArray.length; j++)
			{
				auths = auths + (((int) charArray[j]) * i);
			}
		}
		
		int twos = 1;
		int results = 0;
				
		for(int i=0; i<5; i++)
		{
			twos = 1;
			for(int j = 12 - (8 - i); j>=0; j--)
			{
				twos = twos * 2;
				twos = twos + 1;
			}
			if(auths % twos == 0)
			{
				results = i;
				break;
			}
		}		
		if(results < 0 || results > 4) results = 0;
		return results;
	}

	
	/**
	 * <p>Return true if the receiver object is still alive.</p>
	 * 
	 * <p>리시버 객체가 활성화되어 있는지 반환합니다.</p>
	 * 
	 * @return
	 */
	public static boolean isReceiverAlive()
	{
		if(tcp_receiver == null) return false;
		else 
		{
			print("Receiver " + tcp_receiver.getClass().getName() + " is ");
			if(tcp_receiver.isAlive()) println("alive.");
			else println("not alive.");
			return tcp_receiver.isAlive();
		}
	}
	
	/**
	 * <p>Convert grade of serial code to the grade name.</p>
	 * 
	 * <p>시리얼 인증 수준 이름을 반환합니다.</p>
	 * 
	 * @return grade name
	 */
	public static String getGradeName()
	{
		return getGradeName(getGrade());
	}
	
		
	/**
	 * <p>Convert grade of serial code to the grade name.</p>
	 * 
	 * <p>시리얼 인증 수준 이름을 반환합니다.</p>
	 * 
	 * @param grade : grade of serial code
	 * @return grade name
	 */
	public static String getGradeName(int grade)
	{
		switch(grade)
		{
		case 1:
			return "ADVANCED";
		case 2:
			return "PROFESSIONAL";
		case 3:
			return "ULTIMATE";
		case 4:
			return "MASTER";
		default:
			return "BASIC";
		}
	}
	
	/**
	 * <p>Return how to hash nickname.</p>
	 * 
	 * <p>닉네임을 어떻게 해싱할 지를 반환합니다.</p>
	 * 
	 * @return hashing function
	 */
	public static String getNicknameEncoding()
	{
		return nickEncoding;
	}
	
	/**
	 * <p>Convert text, select module and parameters with another text.</p>
	 * 
	 * <p>텍스트를 변환합니다. 변환하는 데 쓰일 모듈과 그 매개 변수를 또 다른 텍스트로 선택합니다.</p>
	 * 
	 * @param before : original text 변환할 텍스트
	 * @param lines : module name and parameters 모듈 이름과 매개 변수
	 * @return converted text 변환된 텍스트
	 * @exception NoSuchModuleException occur when user input wrong module name
	 */
	public static String convert(String before, String lines) throws NoSuchModuleException
	{
		StringTokenizer commaToken = new StringTokenizer(lines, ",");
		String moduleName = commaToken.nextToken().trim();
		String params = "";
		
		while(commaToken.hasMoreTokens())
		{
			params = params + commaToken.nextToken();
			if(commaToken.hasMoreTokens()) params = params + ",";
		}
		
		ConvertModule module = null;
		for(int i=0; i<modules.size(); i++)
		{
			if(modules.get(i).getName(getSystemLocale()).equals(moduleName))
			{
				module = modules.get(i);
				break;
			}
		}
		if(module == null) throw new NoSuchModuleException(getString("Following module name is not exist") + " : " + moduleName);
		
		Map<String, String> parameters = new Hashtable<String, String>();
		Map<String, String> convertedParams = Statics.stringToHashtable(params);		
		parameters.putAll(getPluginParameters(module, convertedParams.get("option")));
		parameters.putAll(convertedParams);
		
		return module.convert(before, parameters);
	}
	
	/**
	 * 
	 * <p>Convert string.</p>
	 * 
	 * <p>문자열을 변환합니다.</p>
	 * 
	 * @param syntax : Constant value that is matched with programming language : 이미 준비되어 있는 상수값이 필요합니다. 이 상수는 프로그래밍 언어 문법을 지정합니다.
	 * @param before : String value which will be returned by the method created : 만들어질 메소드 선언문이 반환해야 할 원본 텍스트 문자열
	 * @param className : Class name, will be included in statement. : 선언문에 사용될 클래스 이름 
	 * @param methodName : Method name, will be included in statement. : 선언문에 사용될 메소드 이름
	 * @param variableName : Variable name, will be included in statement. : 선언문에 사용될 변수 이름
	 * @param threadTerm : Thread term, Make the process faster if this value is short. : 쓰레드 간격, 짧을 수록 빠르게 끝나지만 부하가 커집니다.
	 * @param statusViewer : The object which can show that process is alive on screen. : 작업 중 상태를 출력하는 데 사용되는 객체 (Can be null)
	 * @param params : Parameters : 매개 변수들 (Can be null)
	 * @return method-declaration statements 메소드 선언문 
	 */
	public static String convert(int syntax, String before, String className, String methodName, String variableName, long threadTerm, StatusViewer statusViewer, String params)
	{
		return convert(Statics.basicSyntax(syntax), before, className, methodName, variableName, threadTerm, statusViewer, params);
	}
	/**
	 * 
	 * <p>Convert string.</p>
	 * 
	 * <p>문자열을 변환합니다.</p>
	 * 
	 * @param syntax : Programming language syntax table (Syntax object can be used) : 프로그래밍 언어 문법 테이블, Syntax 객체 사용 가능
	 * @param before : String value which will be returned by the method created : 만들어질 메소드 선언문이 반환해야 할 원본 텍스트 문자열
	 * @param className : Class name, will be included in statement. : 선언문에 사용될 클래스 이름 
	 * @param methodName : Method name, will be included in statement. : 선언문에 사용될 메소드 이름
	 * @param variableName : Variable name, will be included in statement. : 선언문에 사용될 변수 이름
	 * @param threadTerm : Thread term, Make the process faster if this value is short. : 쓰레드 간격, 짧을 수록 빠르게 끝나지만 부하가 커집니다.
	 * @param statusViewer : The object which can show that process is alive on screen. : 작업 중 상태를 출력하는 데 사용되는 객체 (Can be null)
	 * @param params : Parameters : 매개 변수들 (Can be null)
	 * @return method-declaration statements 메소드 선언문
	 */
	public static String convert(Hashtable<String, String> syntax, String before, String className, String methodName, String variableName, long threadTerm, StatusViewer statusViewer, String params)
	{
		return convert(syntax, before, className, methodName, variableName, false, threadTerm, statusViewer, params);
	}
	
	/**
	 * 
	 * <p>Convert string.</p>
	 * 
	 * <p>문자열을 변환합니다.</p>
	 * 
	 * @param syntax : Programming language syntax table (Syntax object can be used) : 프로그래밍 언어 문법 테이블, Syntax 객체 사용 가능
	 * @param before : String value which will be returned by the method created : 만들어질 메소드 선언문이 반환해야 할 원본 텍스트 문자열
	 * @param className : Class name, will be included in statement. : 선언문에 사용될 클래스 이름 
	 * @param methodName : Method name, will be included in statement. : 선언문에 사용될 메소드 이름
	 * @param variableName : Variable name, will be included in statement. : 선언문에 사용될 변수 이름
	 * @param align : If this is true right blacket will be aligned. : 결과를 정렬할 지 여부를 결정합니다.
	 * @param threadTerm : Thread term, Make the process faster if this value is short. : 쓰레드 간격, 짧을 수록 빠르게 끝나지만 부하가 커집니다.
	 * @param statusViewer : The object which can show that process is alive on screen. : 작업 중 상태를 출력하는 데 사용되는 객체 (Can be null)
	 * @param params : Parameters : 매개 변수들 (Can be null)
	 * @return method-declaration statements 메소드 선언문
	 */
	public static String convert(Hashtable<String, String> syntax, String before, String className, String methodName, String variableName, boolean align, long threadTerm, StatusViewer statusViewer, String params)
	{
		if(before == null) return null;
		if(statusViewer != null) statusViewer.reset();
		if(statusField != null) statusField.setText(getString("Converting...", getStringTable()));
		String beforeArgs = new String(before.trim());
		
		StringBuffer results = new StringBuffer("");
		Map<String, String> parameters = new Hashtable<String, String>();
		
		if(params != null)
		{			
			parameters.putAll(ParameterGetter.toParameter(params));
		}
		
		if(className != null) parameters.put("class", className);
		if(methodName != null) parameters.put("method", methodName);
		if(variableName != null) parameters.put("variable", variableName);	
		
		parameters.put("align", String.valueOf(align));	
		
		println("Convert with " + modules.get(now_convert_mode).getName(getSystemLocale()));
		
		results = results.append(modules.get(now_convert_mode).convert(getStringTable(), syntax, beforeArgs, statusViewer, statusField, threadTerm, parameters));					
		
		if(statusField != null) statusField.setText(getString("Convert complete", getStringTable()));
		if(statusViewer != null) statusViewer.reset();
		println("Convert complete");
		return results.toString();
	}
	
	
	/**
	 * 
	 * <p>Save text as file.</p>
	 * 
	 * <p>텍스트를 파일로 저장합니다.</p>
	 * 
	 * @param target : File object includes the file path : 파일 경로 정보를 포함한 파일 객체
	 * @param contents : Contents that need to be saved : 저장할 내용
	 * @param statusViewer : The object which can show that process is alive on screen. : 작업 상태를 출력할 때 사용할 객체 
	 * @throws Exception
	 */
	public static void saveFile(File target, String contents, StatusViewer statusViewer) throws Exception
	{
		FileWriter writer = null;
		BufferedWriter buffered = null;
		if(statusViewer != null) statusViewer.reset();
		if(statusField != null) statusField.setText(getString("Saving...", getStringTable()));
		println(contents.trim());
		try
		{
			String targetText = contents.replace("\n\n", "\n \n");
			writer = new FileWriter(target);
			buffered = new BufferedWriter(writer);
			//buffered.write(contents);
			StringTokenizer lineToken = new StringTokenizer(targetText, "\n");
			while(lineToken.hasMoreTokens())
			{
				buffered.write(lineToken.nextToken());
				buffered.newLine();
				if(statusViewer != null) statusViewer.nextStatus();
			}
		}
		catch(FileNotFoundException e)
		{			
			throw new FileNotFoundException(getString("You didn\'t select where to save, and what name to save."));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.statusField.setText(getString("Error") + " : " + e.getMessage());
			throw e;
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
				writer.close();
			}
			catch(Exception e)
			{
				
			}
			if(statusViewer != null) statusViewer.reset();
			if(statusField != null) statusField.setText(getString("Save complete", getStringTable()));
		}
	}
	
	/**
	 * <p>Read text from web.</p>
	 * 
	 * <p>웹으로부터 텍스트를 읽어 옵니다.</p>
	 * 
	 * @param url : URL
	 * @param threadTerm : Thread term, Make the process faster if this value is short. : 쓰레드 간격, 짧을 수록 빨리 끝나지만 부하가 커집니다.
	 * @param statusViewer : The object which can show that process is alive on screen. : 작업 상태를 출력할 때 사용할 객체
	 * @param printStackTrace : If this is false, ignore exceptions. : 이 값이 false인 경우 예외 무시
	 * @return text from file 웹으로부터 읽어 온 텍스트
	 * @throws Exception
	 */
	public static String readWeb(String url, long threadTerm, StatusViewer statusViewer, boolean printStackTrace) throws Exception
	{
		InputStreamReader reader = null;
		BufferedReader buffered = null;
		String accum = "", lines;
		if(statusViewer != null) statusViewer.reset();
		try
		{
			reader = new InputStreamReader(new java.net.URL(url).openStream());
			buffered = new BufferedReader(reader);
			while(true)
			{
				lines = buffered.readLine();
				if(lines == null) break;
				lines = lines.trim();
				if(lines.startsWith("#")) continue;
				accum = accum + lines + "\n";
				if(statusViewer != null) statusViewer.nextStatus();
				try
				{
					if(threadTerm >= 1) Thread.sleep(threadTerm);
				}
				catch(Exception e)
				{
					
				}
			}
			
			return accum;
		}
		catch(Exception e)
		{
			if(printStackTrace) e.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				buffered.close();
			}
			catch(Exception e1)
			{
				
			}
			try
			{
				reader.close();
			}
			catch(Exception e1)
			{
				
			}
			if(statusViewer != null) statusViewer.reset();
		}
	}
	/**
	 * <p>Read text from web.</p>
	 * 
	 * <p>웹으로부터 텍스트를 읽어 옵니다.</p>
	 * 
	 * @param url : URL
	 * @param threadTerm : Thread term, Make the process faster if this value is short. : 쓰레드 간격, 짧을 수록 빨리 끝나지만 부하가 커집니다.
	 * @param statusViewer : The object which can show that process is alive on screen. : 작업 상태를 출력할 때 사용할 객체
	 * @param printStackTrace : If this is false, ignore exceptions. : 이 값이 false인 경우 예외 무시
	 * @param charset : target text character set : 웹 텍스트의 캐릭터 셋
	 * @return text from file 웹으로부터 읽어 온 텍스트
	 * @throws Exception
	 */
	public static String readWeb(String url, long threadTerm, StatusViewer statusViewer, boolean printStackTrace, String charset) throws Exception
	{
		if(charset == null) return readWeb(url, threadTerm, statusViewer, printStackTrace);
		if(charset.trim().equals("")) return readWeb(url, threadTerm, statusViewer, printStackTrace);
		
		InputStream stream = new java.net.URL(url).openStream();
		BufferedInputStream buffered = new BufferedInputStream(stream);
		List<Byte> bytes = new LinkedList<Byte>();
		byte[] byteBuffer = new byte[1024];
		int gets;
		int i;
		while(true)
		{
			gets = buffered.read(byteBuffer);
			for(i=0; i<byteBuffer.length; i++)
			{
				bytes.add(new Byte(byteBuffer[i]));
			}
			if(gets < 0) break;
			if(statusViewer != null) statusViewer.nextStatus();
		}
		
		byte[] results = new byte[bytes.size()];
		for(i=0; i<bytes.size(); i++)
		{
			results[i] = bytes.get(i).byteValue();
		}
		return new String(results, charset);
	}
	
	/**
	 * 
	 * <p>Load texts from file and return.</p>
	 * 
	 * <p>파일로부터 텍스트를 불러와 반환합니다.</p>
	 * 
	 * @param target : File object includes the file path : 파일 경로 정보를 포함한 파일 객체
	 * @param threadTerm : Thread term, Make the process faster if this value is short. : 쓰레드 간격, 짧을 수록 빨리 끝나지만 부하가 커집니다.
	 * @param statusViewer : The object which can show that process is alive on screen. : 작업 상태를 출력할 때 사용할 객체
	 * @throws Exception
	 * @return text from file 파일로부터 읽어 온 텍스트
	 */
	public static String readFile(File target, long threadTerm, StatusViewer statusViewer) throws Exception
	{
		FileReader reader = null;
		BufferedReader buffered = null;
		if(statusViewer != null) statusViewer.reset();
		if(statusField != null) statusField.setText(getString("Loading...", getStringTable()));
		println("Read contents from " + target);
		try
		{
			reader = new FileReader(target);
			buffered = new BufferedReader(reader);
			String readLine;
			StringBuffer result = new StringBuffer("");
			
			while(true)
			{
				readLine = buffered.readLine();
				if(readLine == null) break;
				result = result.append("\n" + readLine);
				println("Read : " + readLine);
				if(statusViewer != null) statusViewer.nextStatus();
				try
				{
					if(threadTerm >= 1) Thread.sleep(threadTerm);
				}
				catch(Exception e)
				{
					
				}
			}
			println("Read complete");
			return result.toString().trim();
		}
		catch(FileNotFoundException e)
		{
			String fileTarget = "";
			try
			{
				fileTarget = target.getAbsolutePath();
			}
			catch(Exception e2)
			{
				fileTarget = "?";
			}
			throw new FileNotFoundException("Cannot found the target file like" + " " + fileTarget);
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw e;
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
			if(statusViewer != null) statusViewer.reset();
			if(statusField != null) statusField.setText(getString("Load complete", getStringTable()));
		}
	}
	
	/**
	 * <p>Prepare default reader object.</p>
	 * 
	 * <p>기본 Reader 객체를 준비합니다.</p>
	 * 
	 */
	public static void prepareDefaultInputStream()
	{
		defaultReader = new InputStreamReader(System.in);
		defaultBuffered = new BufferedReader(defaultReader);
	}
	
	/**
	 * <p>Run program as script-console mode.</p>
	 * 
	 * <p>프로그램을 스크립트 콘솔 모드로 실행합니다.</p>
	 */
	public static void runAsScriptConsole()
	{		
		BufferedReader buffered = defaultBuffered;
		try
		{
			String gets;
			ConsoleContainer console = new ConsoleContainer();
			Controller.println(firstMessage, true);
			Controller.println("\n\nScript is ready.", true);
			while(true)
			{
				print(console.pwd() + " > ");
				gets = buffered.readLine();
				if(gets != null)
				{
					gets = gets.trim();
					
					runScript(gets);					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeAll();
		}
	}
	
	/**
	 * <p>Run program as script-gui mode.</p>
	 * 
	 * <p>프로그램을 스크립트 GUI 모드로 실행합니다.</p>
	 */
	public static void runAsScriptGUI()
	{
		try
		{			
			try
			{
				printer.close();
			}
			catch (Exception e)
			{
				
			}
			printer = new hjow.methodconverter.swingconverter.SwingPrinter();
			new hjow.methodconverter.swingconverter.SwingScriptConsole((GUIPrinter) printer).open();			
		}
		catch(Exception e)
		{
			try
			{
				printer.close();
			}
			catch (Exception e1)
			{
				
			}
			printer = new AWTPrinter();
			new AWTScriptConsole((GUIPrinter) printer).open();
		}
	}
	/**
	 * 
	 * <p>Run program as GUI mode.</p>
	 * 
	 * <p>프로그램을 GUI 모드로 실행합니다.</p>
	 * 
	 * @param locales : Locale : 언어 (en, ko, ...)
	 */
	public static void runOnGUI(String locales)
	{
		uimanager = null;
		if(Controller.getStringTable() == null) Controller.selectedLanguage = locales;
		String themes = Controller.optionTable.get("theme");
		
		
		boolean swingLoad = true;
		if(themes == null)
		{
			swingLoad = true;
		}
		else if(themes.equalsIgnoreCase("original"))
		{
			printer.close();
			printer = new AWTPrinter();
			printer.open();
			uimanager = new AWTManager();			
			swingLoad = false;
		}
		else
		{
			swingLoad = true;
		}
		
		if(swingLoad)
		{
			try
			{
				printer.close();
				printer = new hjow.methodconverter.swingconverter.SwingPrinter();
				printer.open(); 
				uimanager = new hjow.methodconverter.swingconverter.SwingManager();				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				try
				{
					uimanager.close();
				}
				catch(Exception e2)
				{
					
				}
				uimanager = new AWTManager();			
			}	
		}
		
		uimanager.setLanguage();
		uimanager.open();
	}
	/**
	 * 
	 * <p>Run program as console mode.</p>
	 * 
	 * <p>프로그램을 콘솔 모드로 실행합니다.
	 * 작업이 완료되면 프로그램이 종료됩니다.</p>
	 * 
	 * @param loadFile : File object includes the file path : 불러올 파일 경로를 담은 파일 객체
	 * @param saveFile : File object includes the file path : 저장할 파일 경로를 담은 파일 객체
	 * @param className : Class name, will be included in statement. : 생성될 메소드 선언문에 포함될 클래스 이름
	 * @param methodName : Method name, will be included in statement. : 생성될 메소드 선언문에 포함될 메소드 이름 
	 * @param variableName : Variable name, will be included in statement. : 생성될 메소드 선언문에 포함될 변수 이름
	 */
	public static void runOnConsole(File loadFile, File saveFile, String className, String methodName, String variableName)
	{
		try
		{
			println(getString("Method Converter"));
			contents = readFile(loadFile, 0, null);
			saveFile(saveFile, convert(null, contents, className, methodName, variableName, 0, null, null), null);
			println(getString("Converted text is saved at") + " " + saveFile.getAbsolutePath());
			
			closeAll();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			println(getString("Error") + " : " + e.getMessage());
			statusField.setText(getString("Error") + " : " + e.getMessage());
		}
	}
		
	static void setDefaultPath(String path)
	{
		defaultPath = path;
	}
	
	
	static Hashtable<String, Syntax> getSyntaxTable()
	{
		return syntaxes;
	}
	
	static boolean quickExit()
	{
		String getExitQuick = getOption("quickexit");
		if(getExitQuick == null) return false;
		try
		{
			return Statics.parseBoolean(getExitQuick);
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * <p>Send package with TCP protocol.</p>
	 * 
	 * <p>패키지를 TCP로 보냅니다.</p>
	 * 
	 * @param ip : IP address
	 * @param target : Package
	 * @throws Exception
	 */
	public static void send(String ip, NetworkPackage target) throws Exception
	{
		if(tcp_sender != null) ((TCPSender) tcp_sender).send(target, ip, false);
		else throw new NullPointerException(Controller.getString("TCP Sender is not prepared."));
	}
	
	/**
	 * <p>Send package with TCP protocol.</p>
	 * 
	 * <p>패키지를 TCP로 보냅니다.</p>
	 * 
	 * @param ip : IP address
	 * @param port : Port number
	 * @param target : Package
	 * @throws Exception
	 */
	public static void send(String ip, int port, NetworkPackage target) throws Exception
	{
		if(tcp_sender != null) ((TCPSender) tcp_sender).send(target, ip, port, false);
		else throw new NullPointerException(Controller.getString("TCP Sender is not prepared."));
	}
	
	/**
	 * <p>Reload module list</p>
	 * 
	 * <p>모듈 리스트를 다시 불러옵니다.</p>
	 */
	public static void prepareModules(boolean guiBased)
	{
		modules.clear();	
				
		modules.add(new ReturnSelfMethod());	
		modules.add(new HashModule());
		modules.add(new EncryptModule());
		modules.add(new DecryptModule());
		modules.add(new CharEncoder());
		modules.add(new CompressModule());
		modules.add(new InsertTokenModule());
		modules.add(new RemoveCommentModule());
		
		try
		{
			scriptModule = new ScriptModule(guiBased);
			modules.add(scriptModule);
		}
		catch (Exception e)
		{
			
		}
						
		if(Statics.useOnlineContent())
		{
			prepareReceiver();
			prepareSender();
		}
		if(Statics.useUntestedFunction())
		{						
			try
			{
				modules.add(new TCPClientModule());
				modules.add(new TCPHostModule());
				modules.add(new ReadWebModule());
				modules.add(new hjow.convert.module.SQLModule());
			}
			catch (Exception e)
			{
				
			}
		}
		loadModules();
	}
	
	private static void loadModules()
	{
		File defaultPaths = null;
		String reads, lines, gets;
		
		if(Statics.useOnlineContent())
		{
			Vector<String> readTarget = new Vector<String>();
			
			int grades = getGrade();
			if(grades >= 1)
			{
				readTarget.add("modules.txt");
				if(grades >= 2) readTarget.add("modules_professional.txt");
				if(grades >= 3) readTarget.add("modules_ultimate.txt");
				if(grades >= 4) readTarget.add("modules_master.txt");
			}
			
			for(int i=0; i<readTarget.size(); i++)
			{
				try
				{
					reads = readWeb(getDefaultURL() + readTarget.get(i), 20, null, false);
					if(reads != null)
					{
						StringTokenizer lineToken = new StringTokenizer(reads, "\n");
						while(lineToken.hasMoreTokens())
						{
							try
							{
								lines = lineToken.nextToken().trim();
								if(lines.startsWith("#")) continue;
								gets = readWeb(lines, 20, null, false);
								if(gets != null) modules.add(new UserDefinedModule(gets));
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		try
		{
			defaultPaths = new File(defaultPath);
			if(defaultPaths.exists())
			{
				File[] files = defaultPaths.listFiles();
				
				for(int i=0; i<files.length; i++)
				{	
					try
					{
						if(files[i].getAbsolutePath().endsWith(".module") || files[i].getAbsolutePath().endsWith(".MODULE"))
						{						
							reads = readFile(files[i], 20, null);
							if(reads != null)
							{
								UserDefinedModule m = new UserDefinedModule(reads);
								modules.add(m);
								userDownloadModules.add(m);
							}
						}
						else if(files[i].getAbsolutePath().endsWith(".bmodule") || files[i].getAbsolutePath().endsWith(".BMODULE"))
						{												
							UserDefinedModule m = new UserDefinedModule(files[i]);
							modules.add(m);
							userDownloadModules.add(m);
						}
						else if(files[i].getAbsolutePath().endsWith(".bzmodule") || files[i].getAbsolutePath().endsWith(".BZMODULE")
								|| files[i].getAbsolutePath().endsWith(".xmodule") || files[i].getAbsolutePath().endsWith(".XMODULE")
								|| files[i].getAbsolutePath().endsWith(".xzmodule") || files[i].getAbsolutePath().endsWith(".XZMODULE")
								|| files[i].getAbsolutePath().endsWith(".xml") || files[i].getAbsolutePath().endsWith(".XML"))
						{												
							UserDefinedModule m = UserDefinedModule.load(files[i]);
							modules.add(m);
							userDownloadModules.add(m);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	/**
	 * <p>Reload plugin list</p>
	 * 
	 * <p>플러그인 리스트를 다시 불러옵니다.</p>
	 */
	public static void preparePlugins()
	{
		File defaultPaths; //  // pgin, zgin, bgin
		try
		{
			defaultPaths = new File(defaultPath);
			if(defaultPaths.exists())
			{
				File[] files = defaultPaths.listFiles();
				
				for(int i=0; i<files.length; i++)
				{	
					try
					{
						if((! files[i].exists()) || 
								(! (files[i].getName().endsWith("pgin") || files[i].getName().endsWith("PGIN")
										|| files[i].getName().endsWith("zgin") || files[i].getName().endsWith("ZGIN")
										|| files[i].getName().endsWith("bgin") || files[i].getName().endsWith("BGIN")))) continue;
						ModulePlugin m = ModulePlugin.load(files[i]);
						plugins.add(m);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	/**
	 * <p>Return loaded module names</p>
	 * 
	 * <p>불러온 모듈 이름을 모두 반환합니다.</p>
	 * 
	 * @return module names
	 */
	public static List<String> getModuleList()
	{
		List<String> names = new Vector<String>();
		
		for(ConvertModule c : modules)
		{
			
			names.add(c.getName(getSystemLocale()));
		}
		return names;
	}
	/**
	 * <p>Return system locale</p>
	 * 
	 * <p>시스템 로캘(언어)을 반환합니다.</p> 
	 * 
	 * @return system locale
	 */
	public static String getSystemLocale()
	{
		String locales = "en";
		try
		{	
			locales = getOption("locale");
			if(locales == null) locales = System.getProperty("user.language");
		}
		catch(Exception e)
		{
			locales = "en";
		}
		return locales;
	}
	/**
	 * 
	 * <p>Return file object that have load-target file path info.</p>
	 * 
	 * <p>불러올 파일 경로 정보를 포함하고 있는 파일 객체 반환</p>
	 * 
	 * @return file object target for load
	 */
	public static File getLoadFile()
	{
		return loadFile;
	}
	/**
	 * <p>Return file object that have save-target file path info.</p>
	 * 
	 * <p>저장할 위치와 파일 이름 정보를 포함하고 있는 파일 객체 반환</p>
	 * 
	 * @return file object target for save
	 */
	public static File getSaveFile()
	{
		return saveFile;
	}
	/**
	 * 
	 * <p>Set target of load-file</p>
	 * 
	 * <p>불러올 파일 경로와 이름을 입력합니다.</p>
	 * 
	 * 
	 * @param f : file
	 */
	public static void setLoadFile(File f)
	{
		loadFile = f;
	}
	/**
	 * 
	 * <p>Set target of save-file</p>
	 * 
	 * <p>저장할 파일 경로와 이름을 입력합니다.</p>
	 * 
	 * 
	 * @param f : file
	 */
	public static void setSaveFile(File f)
	{
		saveFile = f;
	}
	/**
	 * <p>Return available options of selected module.</p>
	 * 
	 * <p>선택된 모듈의 가능한 옵션들을 반환합니다.</p>
	 * 
	 * @return available options of selected modul
	 */
	public static List<String> getAvailableOptions()
	{
		try
		{
			if(now_convert_mode == 0)
			{
				List<String> lists = new Vector<String>();
				lists.addAll(syntaxes.keySet());
				return lists;
			}
			else
			{
				if(now_convert_mode < modules.size() && now_convert_mode >= 0) return modules.get(now_convert_mode).optionList();
				return null;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * <p>Return true if option combobox needs to be editabled.</p>
	 * 
	 * <p>선택된 모듈이 자유롭게 옵션을 입력할 수 있어야 한다면 true 를 반환합니다.</p>
	 * 
	 * @return true if option combobox needs to be editabled
	 */
	public static boolean isOptionFieldEditable()
	{
		return modules.get(now_convert_mode).isOptionEditable();
	}
	
	/**
	 * <p>Return syntax object from syntax table</p>
	 * 
	 * <p>프로그래밍 언어 문법 정보를 담은 객체를 반환합니다.</p>
	 * 
	 * @param str : key of syntax table
	 * @return Syntax object
	 */
	public static Syntax getSyntax(String str)
	{
		return syntaxes.get(str);
	}
	/**
	 * <p>Insert new syntax table</p>
	 * 
	 * <p>새 프로그래밍 언어 문법 테이블을 리스트에 넣습니다.</p>
	 * 
	 * @param keys : syntax table name
	 * @param syn : syntax table
	 */
	public static void insertSyntax(String keys, Syntax syn)
	{
		syntaxes.put(keys, syn);
	}
	
	/**
	 * <p>Return setting of default directory which is used to save settings.</p>
	 * 
	 * <p>설정 저장 기본 디렉토리 경로를 반환합니다.</p>
	 * 
	 * @return directory
	 */
	public static String getDefaultPath()
	{
		return defaultPath;
	}
	
	/**
	 * <p>Return first message which is shown at the program starts.</p>
	 * 
	 * <p>프로그램 시작 시 보이는 시작 메시지를 반환합니다.</p>
	 * 
	 * @return start message
	 */
	public static String getFirstMessage()
	{
		return firstMessage;
	}
	
	/**
	 * <p>Return option delimiter.</p>
	 * 
	 * <p>옵션 구분자를 반환합니다.</p>
	 * 
	 * @return delimiter
	 */
	public static String delimiter()
	{
		return delimiters;
	}
	
	/**
	 * <p>Add communicator object into manage list.</p>
	 * 
	 * <p>Communicator 객체를 관리 리스트에 추가합니다.</p>
	 * 
	 * @param comm : communicator object
	 */
	public static void addCommunicator(Communicator comm)
	{
		communicators.add(comm);
	}
	
	/**
	 * <p>Return communicator object from manage list.</p>
	 * 
	 * <p>관리 리스트에 있는 Communicator 객체를 반환합니다.</p>
	 * 
	 * @param index : index
	 * @return Communicator object
	 */
	public static Communicator getCommunicator(int index)
	{
		return communicators.get(index);
	}
	
	/**
	 * <p>Remove communicator object from manage list.</p>
	 * 
	 * <p>관리 리스트서 Communicator 객체를 삭제합니다.</p>
	 * 
	 * @param index : index
	 * @return Communicator object
	 */
	public static Communicator removeCommunicator(int index)
	{
		return communicators.remove(index);
	}
	
	/**
	 * <p>Close all modules and communicator object.</p>
	 * 
	 * <p>모듈과 Communicator 리소스를 시스템에 반환합니다.</p>
	 */
	public static void closeAll()
	{
		for(int i=0; i<modules.size(); i++)
		{
			try
			{
				modules.get(i).close();
			}
			catch(Exception e)
			{
				
			}
		}
		for(int i=0; i<communicators.size(); i++)
		{
			try
			{
				communicators.get(i).close();
			}
			catch(Exception e)
			{
				
			}
		}
		for(int i=0; i<closables.size(); i++)
		{
			try
			{
				if(closables.get(i).isAlive()) closables.get(i).close();
			}
			catch(Exception e)
			{
				
			}
		}
		try
		{
			tcp_receiver.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			tcp_sender.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			receivedPackage.clear();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			closeLogger();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			printer.close();
		}
		catch(Exception e)
		{
			
		}
		try
		{
			closeAllThreads();
		}
		catch(Exception e)
		{
			
		}	
		try
		{
			defaultBuffered.close();
		}
		catch(Exception e)
		{
			
		}	
		try
		{
			defaultReader.close();
		}
		catch(Exception e)
		{
			
		}	
	}
	
	private static void closeAllThreads()
	{
		int repeatCount = 30;		
		if(quickExit())
		{
			repeatCount = 1;
		}
		
		System.out.println("Waiting until all threads are closed.");
		for(int i=0; i<threads.size(); i++)
		{				
			try
			{
				for(int j=0; j<repeatCount; j++)
				{
					System.out.print(".");
					threads.get(i).join(100);
					if(! (threads.get(i).isAlive())) break;
				}
				if(threads.get(i).isAlive())
				{
					System.out.println();
					System.out.println("Thread " + String.valueOf(threads.get(i)) + " is not closed in several seconds. Trying to interrupt this thread.");
					threads.get(i).interrupt();
					try
					{
						for(int j=0; j<repeatCount; j++)
						{
							Thread.sleep(100);
							System.out.print(".");
							if(! (threads.get(i).isAlive())) break;
						}
						System.out.println();
					}
					catch(Exception e)
					{
						
					}
					if(threads.get(i).isAlive()) System.out.println("Thread " + String.valueOf(threads.get(i)) + " is not closed. This thread will be closed by force.");
					else System.out.println("Thread " + String.valueOf(threads.get(i)) + " is closed safely.");
				}
			}
			catch(Exception e)
			{
				
			}
		}
		System.out.println();
	}
	
	/**
	 * <p>Insert thread object in the management list.</p>
	 * 
	 * <p>쓰레드 객체를 관리 리스트에 넣습니다.</p>
	 * 
	 * @param thread : closable object
	 */
	public static void insertThreadObject(ThreadRunner thread)
	{
		closables.add(thread);
	}
	
	/**
	 * <p>Insert thread object in the management list.</p>
	 * 
	 * <p>쓰레드 객체를 관리 리스트에 넣습니다.</p>
	 * 
	 * @param thread : Thread object
	 */
	public static void insertThreadObject(Thread thread)
	{
		threads.add(thread);
	}
	
	/**
	 * <p>Insert refreshable object in the management list.</p>
	 * 
	 * <p>새로 고칠 수 있는 객체를 관리 리스트에 넣습니다.</p>
	 * 
	 * @param refreshables : Refreshable object
	 */
	public static void insertRefreshes(Refreshable refreshables)
	{
		if(! (Controller.refreshables.contains(refreshables))) Controller.refreshables.add(refreshables);
	}
	
	/**
	 * <p>Refresh all object in the management list.</p>
	 * 
	 * <p>관리 리스트에 있는 새로 고칠 수 있는 객체들을 새로 고칩니다.</p>
	 */
	public static void refreshAll()
	{
		for(int i=0; i<refreshables.size(); i++)
		{
			try
			{
				refreshables.get(i).refresh();
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	/**
	 * <p>Return true if the thread object is in the list.</p>
	 * 
	 * <p>리스트 안에 해당하는 쓰레드가 있는지 여부를 반환합니다.</p>
	 * 
	 * @param thread : Thread object
	 * @return true if the thread object is in the list
	 */
	public static boolean existInList(Thread thread)
	{
		return threads.contains(thread);
	}
	
	/**
	 * <p>Return loaded scripts.</p>
	 * 
	 * <p>js 파일로부터 불러온 스크립트들을 반환합니다.</p>
	 * 
	 * @return scripts from js
	 */
	public static String getLoadedScript()
	{
		if(loadedScripts == null) return "";
		return loadedScripts;
	}
	
	/**
	 * <p>Return thread names on the management list.</p>
	 * 
	 * <p>관리 리스트에 있는 쓰레드 이름들을 반환합니다.</p>
	 * 
	 * @return thread names
	 */
	public static List<String> getThreadNames()
	{
		List<String> names = new Vector<String>();
		refreshThreads();
		for(int i=0; i<closables.size(); i++)
		{
			names.add(closables.get(i).getThreadName());
		}
		return names;
	}
	
	private static void refreshThreads()
	{
		try
		{
			for(int i=0; i<closables.size(); i++)
			{
				try
				{
					if(! closables.get(i).isAlive())
					{
						closables.remove(i);
						i = 0;
					}
				}
				catch(Exception e)
				{
					
				}
			}
			for(int i=0; i<threads.size(); i++)
			{
				try
				{
					if(! threads.get(i).isAlive())
					{
						threads.remove(i);
						i = 0;
					}
				}
				catch (Exception e)
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
	 * <p>Return thread ids on the management list.</p>
	 * 
	 * <p>관리 리스트에 있는 쓰레드 ID들을 반환합니다.</p>
	 * 
	 * @return thread ids
	 */
	public static List<Long> getThreadIDs()
	{
		List<Long> ids = new Vector<Long>();
		refreshThreads();
		for(int i=0; i<closables.size(); i++)
		{
			ids.add(new Long(closables.get(i).getId()));
		}
		return ids;
	}
	
	/**
	 * <p>Return thread which ID is id.</p>
	 * 
	 * <p>ID가 id인 쓰레드 이름을 반환합니다.</p>
	 * 
	 * @param id : Thread ID
	 * @return Thread name
	 */
	public static String getThreadName(long id)
	{
		for(int i=0; i<closables.size(); i++)
		{
			if(closables.get(i).getId() == id)
			{
				return closables.get(i).getThreadName();
			}
		}
		return null;
	}
	
	/**
	 * <p>Close the thread.</p>
	 * 
	 * <p>쓰레드를 닫습니다.</p>
	 * 
	 * 
	 * @param name : thread name
	 */
	public static void closeThread(String name)
	{
		for(int i=0; i<closables.size(); i++)
		{
			if(closables.get(i).getThreadName().equals(name))
			{
				try
				{
					closables.get(i).close();
					closables.remove(i);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}				
			}
		}
	}
	
	/**
	 * <p>Close the thread.</p>
	 * 
	 * <p>쓰레드를 닫습니다.</p>
	 * 
	 * @param id : thread id
	 */
	public static void closeThread(long id)
	{
		for(int i=0; i<closables.size(); i++)
		{
			if(closables.get(i).getId() == id)
			{
				try
				{
					closables.get(i).close();
					closables.remove(i);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}				
			}
		}
	}
	
	/**
	 * <p>Take all received packages from communicator object to the list.</p>
	 * 
	 * <p>Communicator 객체로부터 받은 패키지들을 모두 리스트로 옮깁니다.</p>
	 * 
	 * @param comm : Communicator object
	 */
	public static void receiveAll(Communicator comm)
	{
		removeAllReceives();
		while(comm.getReceivedPackageCount() >= 1)
		{
			NetworkPackage packages = comm.receive();
			boolean isExists = false;
			for(int i=0; i<receivedPackage.size(); i++)
			{
				if(packages.getPackId().longValue() == receivedPackage.get(i).getPackId().longValue())
				{
					isExists = true;
					break;
				}
			}
			if(! isExists) receivedPackage.add(packages);
		}
		
		try
		{
			if(uimanager != null) uimanager.refreshPackageList();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Take all received packages from communicator object to the list.</p>
	 * 
	 * <p>Communicator 객체로부터 받은 패키지들을 모두 리스트로 옮깁니다.</p>
	 * 
	 */
	public static void receiveAll()
	{
		removeAllReceives();
		for(int i=0; i<communicators.size(); i++)
		{
			try
			{
				receiveAll(communicators.get(i));
			}
			catch(Exception e)
			{
				
			}
		}
		try
		{
			if(uimanager != null) uimanager.refreshPackageList();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Insert new package into the list.</p>
	 * 
	 * <p>리스트에 새로운 패키지를 넣습니다.</p>
	 * 
	 * @param pack : package
	 */
	public static void insertPackage(NetworkPackage pack)
	{
		receivedPackage.add(pack);
		try
		{
			if(uimanager != null) uimanager.refreshPackageList();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Insert new module into the list.</p>
	 * 
	 * <p>리스트에 새로운 모듈을 넣습니다.</p>
	 * 
	 * @param module : Module
	 */
	public static void insertModule(UserDefinedModule module)
	{
		userDownloadModules.add(module);
		modules.add(module);
		Controller.refreshAll();
	}
	
	/**
	 * <p>Return unauthorized modules.</p>
	 * 
	 * <p>인증되지 않은 모듈들을 반환합니다.</p>
	 * 
	 * @return modules user-defined
	 */
	public static List<UserDefinedModule> userModules()
	{
		return userDownloadModules;
	}
	
	/**
	 * <p>Return index'th package from list.</p>
	 * 
	 * <p>리스트에 있는 index 번째 패키지를 반환합니다.</p>
	 * 
	 * @param index : number
	 * @return package
	 */
	public static NetworkPackage getReceive(int index)
	{
		return receivedPackage.get(index);
	}
	
	/**
	 * <p>Return package count in list.</p>
	 * 
	 * <p>리스트에 있는 패키지 수를 반환합니다.</p>
	 * 
	 * @return package count
	 */
	public static int getReceivesCount()
	{
		return receivedPackage.size();
	}
	
	/**
	 * <p>Return non-read package count in list.</p>
	 * 
	 * <p>리스트에 있는, 읽지 않은 패키지 수를 반환합니다.</p>
	 * 
	 * @return non-read package count
	 */
	public static int getNotReadReceivesCount()
	{
		int counts = 0;
		for(int i=0; i<receivedPackage.size(); i++)
		{
			if(! (receivedPackage.get(i).isRead()))
			{
				counts++;
			}
		}
		return counts;
	}
	
	/**
	 * <p>Return non-read packages in list.</p>
	 * 
	 * <p>리스트에 있는, 읽지 않은 패키지들을 반환합니다.</p>
	 * 
	 * @return non-read packages
	 */
	public static List<NetworkPackage> getNotReadReceives()
	{
		List<NetworkPackage> packs = new Vector<NetworkPackage>();
		for(int i=0; i<receivedPackage.size(); i++)
		{
			if(! (receivedPackage.get(i).isRead()))
			{
				packs.add(receivedPackage.get(i));
			}
		}
		return packs;
	}
	
	/**
	 * <p>Remove index'th package from list.</p>
	 * 
	 * <p>리스트에 있는 index 번째 패키지를 제거합니다.</p>
	 * 
	 * @param index : number
	 */
	public static void removeReceive(int index)
	{
		receivedPackage.remove(index);
		try
		{
			if(uimanager != null) uimanager.refreshPackageList();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 *
	 * <p>Remove all packages from list.</p>
	 * 
	 * <p>리스트에서 패키지를 전부 제거합니다.</p>
	 */
	public static void removeAllReceives()
	{
		receivedPackage.clear();
		try
		{
			if(uimanager != null) uimanager.refreshPackageList();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * <p>Save all received modules as file.</p>
	 * 
	 * <p>받은 모듈들을 전부 저장합니다.</p>
	 * 
	 */
	public static void saveModules()
	{
		for(UserDefinedModule u : userDownloadModules)
		{			
			try
			{
				if(u instanceof ScriptModule) continue;
				if(u instanceof UserDefinedByteModule) u.save(new File(defaultPath + u.getName() + ".bmodule"));
				else u.save(new File(defaultPath + u.getName() + ".module"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <p>Save all packages as file.</p>
	 * 
	 * <p>패키지를 전부 저장합니다.</p>
	 * 
	 */
	public static void savePackages()
	{		
		for(NetworkPackage p : receivedPackage)
		{		
			try
			{
				File dirs = new File(getDefaultPath());
				if(! (dirs.exists())) dirs.mkdir();
				saveFile(new File(getDefaultPath() + String.valueOf(p.getPackId()) + ".pck"), p.serialize(), null);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <p>Load all packages from file.</p>
	 * 
	 * <p>패키지를 전부 불러옵니다.</p>
	 * 
	 */
	public static void loadPackages()
	{
		File dirs = new File(getDefaultPath());
		if(dirs.exists())
		{
			try
			{
				File[] lists = dirs.listFiles(new FileFilter()
				{				
					@Override
					public boolean accept(File pathname)
					{
						try
						{
							if(pathname.getAbsolutePath().endsWith(".pck") || pathname.getAbsolutePath().endsWith(".PCK")) return true;
							return false;
						}
						catch(Exception e)
						{
							return false;
						}
					}
				});
				
				if(receivedPackage == null) receivedPackage = new Vector<NetworkPackage>();
				receivedPackage.clear();
				for(File f : lists)
				{
					try
					{
						String getTexts = readFile(f, 20, null);
						receivedPackage.add(NetworkPackage.toPackage(getTexts));
					}
					catch(Exception e)
					{
						e.printStackTrace();
						Controller.println(Controller.getString("Error") + " : " + e.getMessage());
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <p>Show text on the status bar.</p>
	 * 
	 * <p>상태바에 텍스트를 보입니다.</p>
	 * 
	 * @param str : text
	 */	
	public static void setStatusText(String str)
	{
		statusField.setText(str);
		if(logWriter != null)
		{
			try
			{
				logBuffer.write(String.valueOf(System.currentTimeMillis()) + " - " + str);
				logBuffer.newLine();
			}
			catch(Exception e)
			{
				try
				{
					logBuffer.close();
					logBuffer = null;
				}
				catch(Exception e1)
				{
					
				}
				try
				{
					logWriter.close();
					logWriter = null;
				}
				catch(Exception e1)
				{
					
				}
			}
		}
	}
	/**
	 * <p>Set status bar object</p>
	 * 
	 * <p>상태바 객체를 삽입합니다. </p>
	 * 
	 * @param bar : status bar object
	 */
	public static void setStatusBar(StatusBar bar)
	{
		statusField = bar;
	}
	/**
	 * <p>Return status bar object as component</p>
	 * 
	 * <p>상태바 객체를 GUI 컴포넌트 객체 형태로 반환합니다. </p>
	 * 
	 * @return Component object
	 */
	public static Component getStatusBar()
	{
		return statusField.toComponent();
	}
	
	/**
	 * <p>Read options on default file</p>
	 * 
	 * <p>기본 파일로부터 옵션을 읽습니다.
	 * 기본 파일은 기본 경로에 config.cfg 로 지정되어 있습니다.</p>
	 * 
	 */
	public static void loadOption()
	{
		loadOption(new File(defaultPath + "config.cfg"));
	}
	
	/**
	 * <p>Read options on file</p>
	 * 
	 * <p>파일로부터 옵션을 읽습니다.</p>
	 * 
	 * @param file : File object 파일 객체
	 */
	public static void loadOption(File file)
	{		
		optionTable.input(file);			
	}
	
	/**
	 * 
	 * <p>Read options on text.</p>
	 * 
	 * <p>텍스트로부터 옵션을 읽습니다.</p>
	 * 
	 * @param options : Option text 옵션 텍스트
	 */
	public static void loadOption(String options)
	{
		optionTable.input(options);
	}
	
	/**
	 * <p>Save options as file.</p>
	 * 
	 * <p>파일로 옵션 테이블을 저장합니다.</p>
	 * 
	 * @throws Exception
	 */
	public static void saveOption() throws Exception
	{
		File dir = new File(defaultPath);
		if(! dir.exists()) dir.mkdir();
		saveOption(new File(defaultPath + "config.cfg"), null);
	}
	
	/**
	 * <p>Save options as file.</p>
	 * 
	 * <p>파일로 옵션 테이블을 저장합니다.</p>
	 * 
	 * @param file : File object
	 * @param viewer : The object can show the process is alive, can be null
	 * @throws Exception
	 */
	public static void saveOption(File file, StatusViewer viewer) throws Exception
	{		
		Controller.saveFile(file, optionTable.toString(), viewer);
	}
	
	/**
	 * <p>Return option value</p>
	 * 
	 * <p>옵션 값 반환</p>
	 * 
	 * @param keys : Option key 옵션 키
	 * @return 옵션 값
	 */
	public static String getOption(String keys)
	{
		if(optionTable == null) return null;
		return optionTable.get(keys);
	}
	
	/**
	 * <p>Return option table keys.</p>
	 * 
	 * <p>옵션 테이블의 모든 키를 반환</p>
	 * 
	 * @return all keys of option table
	 */
	public static Set<String> getOptionKeys()
	{
		if(optionTable == null) return null;
		return optionTable.keySet();
	}
	
	/**
	 * <p>Set option</p>
	 * 
	 * <p>새로운 옵션 값을 넣거나 기존 옵션을 변경합니다.</p>
	 * 
	 * @param keys : key
	 * @param values : value of option
	 */
	public static void setOption(String keys, String values)
	{
		if(optionTable == null) return;
		if(keys == null) return;
		if(values == null) return;

		optionTable.put(keys, values);
		
		try
		{
			if(keys.equalsIgnoreCase("securityLevel"))
			{
				scriptSecurityLevel = Integer.parseInt(values);
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	
		
	/**
	 * <p>Return contents loaded.</p>
	 * 
	 * <p>담겨 있는 텍스트를 반환합니다.</p>
	 * 
	 * @return contents
	 */
	public static String getContent()
	{
		return contents;
	}
	/**
	 * <p>Insert contents.</p>
	 * 
	 * <p>텍스트를 삽입합니다.</p>
	 * 
	 * @param contents
	 */
	public static void setContent(String contents)
	{		
		Controller.contents = contents;
		undoStack.push(contents);
		while(undoStack.size() >= undoListMaxCount)
		{
			undoStack.remove(0);	
		}
	}
	
	/**
	 * <p>Undo contents.</p>
	 * 
	 * <p>컨텐츠를 이전 것으로 바꿉니다.</p>
	 */
	public static void undo()
	{
		if(undoStack.size() >= 1)
		{
			Controller.contents = undoStack.pop();
		}
	}
	
	/**
	 * <p>Return undo stack size.</p>
	 * 
	 * <p>되돌리기 스택의 크기를 반환합니다.</p>
	 * 
	 * @return undo stack size
	 */
	public static int getUndoCount()
	{
		return undoStack.size();
	}
	
	/**
	 * <p>Return selected syntax name.</p>
	 * 
	 * <p>선택되어 있는 프로그래밍 언어 문법 이름을 반환합니다.</p>
	 * 
	 * @return syntax name
	 */
	public static String getSelectedSyntax()
	{
		return selectedSyntax;
	}
	
	/**
	 * <p>Select syntax.</p>
	 * 
	 * <p>프로그래밍 언어 문법을 선택합니다.</p>
	 * 
	 * @param syntaxName : syntax table name
	 */
	public static void selectSyntax(String syntaxName)
	{
		selectedSyntax = syntaxName;
	}
	
	/**
	 * <p>Get option value.</p>
	 * 
	 * <p>옵션 값을 반환합니다.</p>
	 * 
	 * @param index : option index
	 * @return option value
	 */
	public static String getOption(int index)
	{
		return optionTable.get(index);
	}
	
	/**
	 * <p>Get default URL.</p>
	 * 
	 * <p>기본 URL를 반환합니다.</p>
	 * 
	 * @return default URL
	 */
	public static String getDefaultURL()
	{
		if(getOption("defaultURL") == null)
		{
			return defaultURL;
		}
		else
		{
			return getOption("defaultURL");
		}
	}
	
	/**
	 * <p>Get selected module number.</p>
	 * 
	 * <p>선택된 모듈 번호를 반환합니다.</p>
	 * 
	 * @return module index
	 */
	public static int getSelectedMode()
	{
		return now_convert_mode;
	}
	
	/**
	 * <p>Select module.</p>
	 * 
	 * <p>모듈을 선택합니다.</p>
	 * 
	 * @param mode : module number
	 */
	public static void selectMode(int mode)
	{
		now_convert_mode = mode;
	}
		
	/**
	 * <p>Return list of modules.</p>
	 * 
	 * <p>모듈들을 반환합니다.</p>
	 * 
	 * @return modules
	 */
	public static List<ConvertModule> getModules()
	{
		return modules;
	}
	
	/**
	 * <p>Print out text on GUI dialog.</p>
	 * 
	 * <p>텍스트를 GUI 대화 상자로 출력합니다.</p>
	 * 
	 * @param str : text
	 */
	public static void alert(String str)
	{
		if(uimanager != null) uimanager.alert(str);
		println(str);
	}
	/**
	 * <p>Print out text on GUI dialog and request selection from user.</p>
	 * 
	 * <p>텍스트를 GUI 대화 상자로 출력하고 사용자로부터 선택을 요구합니다.</p>
	 * 
	 * @param str : text
	 * @return selection
	 */
	public static boolean requestYes(String str)
	{
		if(uimanager != null) return uimanager.requestYes(str);
		else
		{
			try
			{
				System.out.print(str + " (y/n) : ");
				return Statics.parseBoolean(defaultBuffered.readLine());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}		
	}
	/**
	 * <p>Print out text on GUI dialog and request inputs from user.</p>
	 * 
	 * <p>텍스트를 GUI 대화 상자로 출력하고 사용자로부터 입력을 요구합니다.</p>
	 * 
	 * @param str : text
	 * @return text input
	 */
	public static String requestInput(String str)
	{
		if(uimanager != null) return uimanager.requestInput(str);
		else
		{			
			try
			{
				System.out.print(str + " : ");
				return defaultBuffered.readLine();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * <p>Print text on console.</p>
	 * 
	 * <p>콘솔에 텍스트를 출력합니다.</p>
	 * 
	 * @param str : text
	 */
	public static void print(String str)
	{
		if(printer != null)
		{
			printer.openWhenClosed();
			printTarget.add(str);
		}
		else System.out.print(str);
	}
	
	/**
	 * <p>Print text on console.</p>
	 * 
	 * <p>콘솔에 텍스트를 출력합니다.</p>
	 * 
	 * @param str : text
	 * @param changeFromStringTable : If this is true, change text from string table.
	 */
	public static void print(String str, boolean changeFromStringTable)
	{
		if(changeFromStringTable) print(getString(str));
		else print(str);
	}
	
	/**
	 * <p>Jump to the next line on console.</p>
	 * 
	 * <p>콘솔에서 줄을 띕니다.</p>
	 * 
	 */
	public static void println()
	{
		if(printer != null)
		{
			printer.openWhenClosed();
			printTarget.add("\n");
		}
		else System.out.println();
	}
	/**
	 * <p>Print text and jump to the next line on console.</p>
	 * 
	 * <p>콘솔에 텍스트를 출력하고 줄을 띕니다.</p>
	 * 
	 * @param str : text
	 */
	public static void println(String str)
	{
		if(printer != null)
		{
			printer.openWhenClosed();
			printTarget.add(str);
			println();
		}
		else System.out.println(str);
	}
	
	/**
	 * <p>Print text and jump to the next line on console.</p>
	 * 
	 * <p>콘솔에 텍스트를 출력하고 줄을 띕니다.</p>
	 * 
	 * @param str : text
	 * @param changeFromStringTable : If this is true, change text from string table.
	 */
	public static void println(String str, boolean changeFromStringTable)
	{
		if(changeFromStringTable) println(getString(str));
		else println(str);
	}
	
	/**
	 * <p>Return component if Printer object is component. Return null if Printer object is not created or not a component.</p>
	 * 
	 * <p>Printer 객체가 컴포넌트이면 이를 반환합니다.</p>
	 * 
	 * @return Component
	 */
	public static Component getPrinterComponent()
	{
		if(printer == null) return null;
		if(printer instanceof GUIPrinter)
		{
			return ((GUIPrinter) printer).getComponent();
		}
		else return null;
	}
	
	/**
	 * <p>Check printer object and reopen it.</p>
	 * 
	 * <p>Printer 객체를 확인하고 다시 엽니다.</p>
	 */
	public static void checkPrinter()
	{		
		if(! printer.isAlive())
		{
			try
			{
				printer.close();
			}
			catch(Exception e)
			{
				
			}
			
			printer = new Printer();
			printer.openWhenClosed();
		}
	}
	
	/**
	 * <p>Return Printer object class name.</p>
	 * 
	 * <p>Printer 객체의 클래스 이름을 반환합니다.</p>
	 */
	public static String getPrinterName()
	{
		if(printer != null) return printer.getClass().getSimpleName();
		else return "null";
	}
	
	/**
	 * <p>Request focus here when Printer object is GUI based.</p>
	 * 
	 * <p>Printer 객체가 GUI 환경용일 경우 입력 커서를 요청합니다.</p>
	 */
	public static void focusOnPrinter()
	{
		if(printer != null) printer.focus();
	}
	
	/**
	 * <p>Clear here when this object is GUI based.</p>
	 * 
	 * <p>이 객체가 GUI 환경용일 경우 출력 내용을 비웁니다.</p>
	 */
	public static void clearPrinter()
	{
		printTarget.clear();
		if(printer != null) printer.clear();
	}
	
	/**
	 * 
	 * <p>Enable, or disable all components on printer.</p>
	 * 
	 * <p>Printer 객체 내의 컴포넌트를 모두 활성화하거나 비활성화합니다.</p>
	 * 
	 * @param l : If this is true, all components will be enabled.
	 */
	public static void enablePrinter(boolean l)
	{
		if(printer != null)
		{
			if(printer instanceof GUIPrinter) ((GUIPrinter) printer).enableAll(l);
		}
	}
	
	/**
	 * <p>Take out the text from the list.</p>
	 * 
	 * <p>출력할 리스트에서 텍스트를 꺼냅니다.</p>
	 * 
	 * @return text on the print list, this can be null when the list is empty
	 */
	public synchronized static String getTextOnList()
	{
		if(printTarget.size() <= 0) return null;
		else
		{
			String gets = printTarget.get(0);
			printTarget.remove(0);
			return gets;
		}
	}
	
	/**
	 * <p>Return how many texts on the list. Return negative value when the list is null.</p>
	 * 
	 * <p>출력 리스트에 텍스트가 얼마나 있는지를 반환합니다. 리스트가 초기화되지 않았으면 음수를 반환합니다.</p>
	 * 
	 * @return how many texts on the list
	 */
	public synchronized static int getTextCountOnList()
	{
		if(printTarget.size() <= 0) return -1;
		else return printTarget.size();
	}
	
	/**
	 * <p>Return security level setting.</p>
	 * 
	 * <p>보안 레벨 설정을 반환합니다.</p>
	 * 
	 * @return security level
	 */
	public static int getSecurityLevel()
	{
		return scriptSecurityLevel;
	}
	
	/**
	 * <p>Set string table </p>
	 * 
	 * <p>스트링 테이블을 설정합니다.</p>
	 * 
	 * @param stringTable : String table
	 */
	public static void setLanguage(StringTable stringTable)
	{
		Controller.otherStringTables.put(stringTable.get("localename"), stringTable);
		Controller.selectedLanguage = stringTable.get("localename");
	}
	
	/**
	 * <p>Return string table</p>
	 * 
	 * <p>스트링 테이블을 반환합니다.</p>
	 * 
	 * @return String table
	 */
	public static StringTable getLanguage()
	{
		return Controller.otherStringTables.get(Controller.selectedLanguage);
	}
	
	/**
	 * <p>Get string on selected string table.</p>
	 * 
	 * <p>선택한 스트링 테이블로부터 문자열을 반환합니다.</p>
	 * 
	 * @param keys : key of string table
	 * @return string
	 */
	public static String getString(String keys)
	{
		try
		{
			return getString(keys, otherStringTables.get(selectedLanguage));
		}
		catch(Exception e)
		{
			return keys;
		}
	}
	
	/**
	 * <p>Set string on selected string table.</p>
	 * 
	 * <p>스트링 테이블에 문자열을 삽입합니다.</p>
	 * 
	 * @param keys : key of string table
	 * @param value : value
	 */
	public static void setString(String keys, String value)
	{
		otherStringTables.get(selectedLanguage).put(keys, value);
	}
		
	/**
	 * <p>Take string on the string table</p>
	 * 
	 * <p>스트링 테이블로부터 문자열을 꺼내 반환합니다.</p>
	 *  
	 * @param v : key 키
	 * @param stringTable string table 스트링 테이블
	 * @return string taken 꺼낸 문자열
	 */
	public static String getString(String v, StringTable stringTable)
	{
		if(stringTable == null)
		{			
			return Statics.defaultLanguages().get(getSystemLocale()).get(v);			
		}
		String result = stringTable.get(v);
		if(result == null) return v;
		else return result;
	}
	
	/**
	 * <p>Get all string table keys</p>
	 * 
	 * <p>스트링 테이블의 키들을 반환합니다.</p>
	 * 
	 * 
	 * @return string table keys
	 */
	public static Set<String> getStringTableKeys()
	{
		return otherStringTables.keySet();
	}
	/**
	 * <p>Get string table formal name of locale</p>
	 * 
	 * <p>스트링 테이블의 정식 이름을 반환합니다.</p>
	 * 
	 * @param locale : locale
	 * @return formal name of locale
	 */
	public static String getStringTableFormalName(String locale)
	{
		return otherStringTables.get(locale).get("localename_formal");
	}
	
	/**
	 * <p>Get string table.</p>
	 * 
	 * <p>스트링 테이블을 반환합니다.</p>
	 * 
	 * @param locale : locale
	 * @return string table
	 */
	public static StringTable getStringTable(String locale)
	{
		return otherStringTables.get(locale);
	}
	
	/**
	 * <p>Return selected string table.</p>
	 * 
	 * <p>현재 선택되어 있는 스트링 테이블을 반환합니다.</p>
	 * 
	 * @return string table
	 */
	public static StringTable getStringTable()
	{
		return otherStringTables.get(selectedLanguage);
	}
	
	/**
	 * <p>Insert string table on the list.</p>
	 * 
	 * <p>스트링 테이블을 리스트에 넣습니다.</p>
	 * 
	 */
	public static void insertStringTable(StringTable tables)
	{
		otherStringTables.put(tables.get("localename"), tables);
	}
	
	/**
	 * <p>Reset syntax list</p>
	 * 
	 * <p>프로그래밍 언어 문법 테이블을 다시 초기화합니다.</p>
	 */
	public static void resetSyntaxList()
	{
		Controller.getSyntaxTable().clear();
		Controller.getSyntaxTable().put(Statics.basicSyntax(Controller.SYNTAX_JAVA_BUFFER).get("name"), Statics.basicSyntax(Controller.SYNTAX_JAVA_BUFFER));
		Controller.getSyntaxTable().put(Statics.basicSyntax(Controller.SYNTAX_JAVASCRIPT).get("name"), Statics.basicSyntax(Controller.SYNTAX_JAVASCRIPT));
		Controller.getSyntaxTable().put(Statics.basicSyntax(Controller.SYNTAX_JAVA).get("name"), Statics.basicSyntax(Controller.SYNTAX_JAVA));
	}	
	
	/**
	 * <p>Run script code.</p>
	 * 
	 * <p>스크립트를 실행합니다.</p>
	 * 
	 * @param code : script code
	 * @return result of execution
	 * @throws Exception
	 */
	public static Object runScript(String code) throws Exception
	{
		return scriptModule.runCode(code);
	}
	/**
	 * <p>Close log file resource.</p>
	 * 
	 * <p>로그 파일 리소스를 시스템에 반환합니다.</p>
	 */
	public static void closeLogger()
	{
		try
		{
			logBuffer.close();
			logBuffer = null;
		}
		catch(Exception e1)
		{
			
		}
		try
		{
			logWriter.close();
			logWriter = null;
		}
		catch(Exception e1)
		{
			
		}
	}
	/**
	 * <p>New log file stream.</p>
	 * 
	 * <p>로그 파일 생성 스트림을 다시 만듭니다.</p>
	 */
	public static void newLogger()
	{
		try
		{
			File defdirectory =  new File(Controller.getDefaultPath());
			if(! defdirectory.exists()) defdirectory.mkdir();
			
			logWriter = new FileWriter(new File(Controller.getDefaultPath() + "log_" + System.currentTimeMillis() + ".txt"));
			logBuffer = new BufferedWriter(logWriter);
		}
		catch(Exception e)
		{
			try
			{
				logBuffer.close();
				logBuffer = null;
			}
			catch(Exception e1)
			{
				
			}
			try
			{
				logWriter.close();
				logWriter = null;
			}
			catch(Exception e1)
			{
				
			}
		}
	}


	/**
	 * <p>Return global encryption key.</p>
	 * 
	 * <p>전역 암호화 비밀번호를 반환합니다. 이는 메신저 기능에 사용될 예정입니다.</p>
	 * 
	 * 
	 * @return global encryption key
	 */
	public static String getGlobal_encrypt_key()
	{
		return global_encrypt_key;
	}

	/**
	 * <p>Set global encryption key.</p>
	 * 
	 * <p>전역 암호화 비밀번호를 설정합니다.</p>
	 * 
	 * @param global_encrypt_key : global encryption key
	 */
	public static void setGlobal_encrypt_key(String global_encrypt_key)
	{
		HashModule hasher = new HashModule();
		Controller.global_encrypt_key = hasher.encrypt(global_encrypt_key, "SHA-256");
		hasher.close();
	}


	/**
	 * <p>Return now-selected-path.</p>
	 * 
	 * <p>현재 선택된 디렉토리 경로를 반환합니다.</p>
	 * 
	 * @return now-selected-path
	 */
	public static File getDirCursor()
	{
		return dirCursor;
	}

	/**
	 * <p>Select path.</p>
	 * 
	 * <p>디렉토리 경로를 선택합니다.</p>
	 * 
	 * @param dirCursor : Directory path
	 * @throws IOException : occurs when dirCursor is not a directory
	 */
	public static void setDirCursor(File dirCursor) throws IOException
	{
		if(! dirCursor.exists()) throw new FileNotFoundException(Controller.getString("Is not exists") + " : " + dirCursor.getAbsolutePath());
		if(! dirCursor.isDirectory()) throw new IsNotDirectoryException();
		Controller.dirCursor = dirCursor;
	}
	
	/**
	 * <p>Return file path separator symbol.</p>
	 * 
	 * <p>OS의 파일 경로 구분자를 반환합니다.</p>
	 * 
	 * @return file path separator symbol. On Windows, \ will be returned.
	 */
	public static String getFileSeparator()
	{
		return fileSeparator;
	}
	
	/**
	 * <p>Return UI Manager.</p>
	 * 
	 * <p>UI Manager 객체를 반환합니다.</p>
	 * 
	 * @return UI Manager object
	 */
	public static Manager getUIManager()
	{
		return uimanager;
	}
	
	/**
	 * <p>Return plugin data of the module.</p>
	 * 
	 * <p>해당 모듈의 플러그인 데이터를 반환합니다.</p>
	 * 
	 * @param module : module object
	 * @param selectedOption : option user selected
	 * @return data as parameters
	 */
	public static Map<String, String> getPluginParameters(ConvertModule module, String selectedOption)
	{	
		if(selectedOption == null) return new Hashtable<String, String>();
		
		try
		{
			for(int i=0; i<plugins.size(); i++)
			{
				try
				{
					if(plugins.get(i).getTargetDefinitionName().equals(module.getDefinitionName()))
					{
						if(selectedOption.equals(plugins.get(i).getName())) return plugins.get(i).getDataMap();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.print("Error", true);
			Controller.println(" : " + e.getMessage());
		}
		return new Hashtable<String, String>();
	}
	
	/**
	 * <p>Return available plugins of module.</p>
	 * 
	 * <p>모듈에 해당하는 사용 가능한 플러그인 이름들을 반환합니다. 선택 가능한 옵션으로 추가됩니다.</p>
	 * 
	 * @return additional options
	 */
	public static List<String> getPluginNames()
	{
		if(now_convert_mode < modules.size() && now_convert_mode >= 0) return getPluginNames(modules.get(now_convert_mode));
		else return new Vector<String>();
	}
	/**
	 * <p>Return available plugins of module.</p>
	 * 
	 * <p>모듈에 해당하는 사용 가능한 플러그인 이름들을 반환합니다. 선택 가능한 옵션으로 추가됩니다.</p>
	 * 
	 * @param module
	 * @return additional options
	 */
	public static List<String> getPluginNames(ConvertModule module)
	{
		try
		{
			List<String> results = new Vector<String>();
			for(int i=0; i<plugins.size(); i++)
			{
				try
				{
					if(plugins.get(i).getTargetDefinitionName().equals(module.getDefinitionName()))
					{
						results.add(plugins.get(i).getName());
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			return results;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Controller.print("Error", true);
			Controller.println(" : " + e.getMessage());
		}
		return new Vector<String>();
	}
	
	private static void runDaemon()
	{
		Daemon daemon = null;		
		
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
				Controller.alert(Controller.getString("Error") + " : " + e.getMessage());
			}
		}
	}
	/**
	 * <p>Run daemon client which can access other PC.</p>
	 * 
	 * <p>데몬 클라이언트를 사용해 다른 PC에 접속합니다.</p>
	 * 
	 * @param guiBased : Decide GUI / Console mode
	 */
	public static void runDaemonClient(boolean guiBased)
	{
		if(guiBased)
		{
			try
			{
				java.awt.Window window = Controller.getUIManager().getWindow();
				if(window instanceof javax.swing.JFrame) new SwingDaemonClient((javax.swing.JFrame) Controller.getUIManager().getWindow()).open();
				else if(window instanceof java.awt.Frame) new SwingDaemonClient((java.awt.Frame) Controller.getUIManager().getWindow()).open();
				else new SwingDaemonClient().open();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Controller.println(Controller.getString("Error") + " : " + e.getMessage());
			}
		}
		else
		{
			InputStreamReader converts = null;
			BufferedReader reader = null;
			DaemonClient client = null;
			try
			{
				converts = new InputStreamReader(System.in);
				reader = new BufferedReader(converts);
				
				System.out.print(Controller.getString("Where to access") + " : ");
				String ip = reader.readLine();
				if(ip == null)
				{
					System.out.println(Controller.getString("Fail to prepare"));
					return;
				}
				ip = ip.trim();
				
				System.out.println(Controller.getString("What port to access") + "(" + Controller.getString("Default") + " : " + Daemon.default_port + ") : ");
				String port = reader.readLine();
				if(port == null || port.trim().equals(""))
				{
					port = String.valueOf(Daemon.default_port);
				}
				port = port.trim();
				
				String id, pw;
				System.out.print(Controller.getString("ID") + " : ");
				id = reader.readLine();
				if(id == null)
				{
					System.out.println(Controller.getString("Fail to prepare"));
					return;
				}
				id = id.trim();
				
				System.out.print(Controller.getString("Password") + " : ");
				pw = reader.readLine();
				if(pw == null)
				{
					System.out.println(Controller.getString("Fail to prepare"));
					return;
				}
				pw = pw.trim();
				
				client = new DaemonClient();
				client.connect(id, pw, ip, Integer.parseInt(port));
				
				String gets = "";
				while(true)
				{
					System.out.print(client.getThreadName() + ">");
					gets = reader.readLine();
					if(gets.equals(""))
					{
						
					}
					else if(gets.equalsIgnoreCase("EXIT") || gets.equalsIgnoreCase("LOGOUT"))
					{
						client.close();
						break;
					}
					else
					{
						client.send(gets);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Controller.println(Controller.getString("Error") + " : " + e.getMessage());
			}
			finally
			{
				try
				{
					client.close();
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
				try
				{
					converts.close();
				}
				catch(Exception e)
				{
					
				}
			}
			
		}
	}
}
/**
 * 
 * <p>This class is used to print status on console</p>
 * 
 * <p>이 클래스 객체는 콘솔 상에서 상태를 보여주는 데 사용됩니다.</p>
 * 
 * @author HJOW
 *
 */
class ConsoleStatusBar implements StatusBar
{
	@Override
	public void setText(String str)
	{
		Controller.println(str);		
	}

	@Override
	public void clear()
	{
		Controller.println("\n");
	}

	@Override
	public Component toComponent()
	{
		return null;
	}	
}