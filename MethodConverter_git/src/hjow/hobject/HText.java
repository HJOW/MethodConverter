package hjow.hobject;


/**
 * <p>Many classes and interfaces in hjow.hobject package are not completed and not used in MethodConverter.</p>
 * 
 * <p>hjow.hobject 패키지 내의 클래스와 인터페이스는 아직 완성되지 않았으며 MethodConverter 에 사용되지 않았습니다.</p>
 * 
 * 
 * <p>This class instance includes text data.</p>
 * 
 * <p>이 클래스 객체는 텍스트 데이터를 포함합니다.</p>
 * 
 * @author HJOW
 *
 */
public class HText extends HInstance implements CharSequence, Comparable<HText>
{	
	private static final long serialVersionUID = 3366644716207915941L;
	private String text = "";
	
	
	/**
	 * <p>Create new HText object.</p>
	 * 
	 * <p>HText 객체를 만듭니다.</p>
	 */
	public HText()
	{
		
	}
	/**
	 * <p>Create new HText object with String object.</p>
	 * 
	 * <p>String 객체를 통해 HText 객체를 만듭니다.</p>
	 */
	public HText(String text)
	{
		this.text = text;
	}
	/**
	 * <p>Copy object.</p>
	 * 
	 * <p>객체를 복사해 새 객체를 만듭니다.</p>
	 * 
	 * @param text : original HText object
	 */
	public HText(HText text)
	{
		this.text = new String(text.getText());
	}
	/**
	 * <p>Create text with char array.</p>
	 * 
	 * <p>문자 배열을 텍스트로 만듭니다.</p>
	 * 
	 * @param text : char array
	 */
	public HText(char[] text)
	{
		this.text = new String(text);
	}
	
	/**
	 * <p>Create text with byte array.</p>
	 * 
	 * <p>바이트 데이터를 텍스트로 만듭니다.</p>
	 * 
	 * @param text : byte array
	 */
	public HText(byte[] text)
	{
		this.text = new String(text);
	}
	/**
	 * <p>Create text with byte array.</p>
	 * 
	 * <p>바이트 데이터를 텍스트로 만듭니다.</p>
	 * 
	 * @param text : byte array
	 * @param charset : charset, such as UTF-8
	 * @throws Exception
	 */
	public HText(byte[] text, String charset) throws Exception
	{
		this.text = new String(text, charset);
	}
	
	/**
	 * <p>Copy object.</p>
	 * 
	 * <p>객체를 복사해 새 객체를 만듭니다.</p>
	 * 
	 * @return new object
	 */
	@Override
	public HText clone()
	{
		return new HText(text);
	}
	@Override
	public String toString()
	{
		return text;
	}
	@Override
	public String serialize()
	{
		if(text == null) return "null";
		return "\"" + text + "\"";
	}
	@Override
	public CharSequence subSequence(int start, int end)
	{
		return text.subSequence(start, end);
	}
	/**
	 * <p>Return String object.</p>
	 * 
	 * <p>String 객체를 반환합니다.</p>
	 * 
	 * @return String object
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * <p>Set text data from String object.</p>
	 * 
	 * <p>String 객체의 텍스트를 삽입합니다.</p>
	 * 
	 * @param text : String object
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	
	/**
	 * <p>Return length of text.</p>
	 * 
	 * <p>텍스트의 길이를 반환합니다.</p>
	 * 
	 * @return text length
	 */
	public int length()
	{
		return text.length();
	}
	/**
	 * <p>Return text which is added.</p>
	 * 
	 * <p>텍스트를 연접한 새로운 텍스트를 반환합니다.</p>
	 * 
	 * @param other : text
	 * @return text which is added
	 */
	public HText append(HText other)
	{
		return new HText(text + other.getText());
	}
	
	/**
	 * <p>Return index'th character.</p>
	 * 
	 * <p>index 번째 글자를 반환합니다.</p>
	 */
	public char charAt(int index)
	{
		return text.charAt(index);
	}
	
	/**
	 * <p>Return true if text is includes s.</p>
	 * 
	 * <p>텍스트가 s를 포함하고 있으면 true를 반환합니다.</p>
	 * 
	 * @param s : other text or patterns
	 * @return true if text is includes s
	 */
	public boolean contains(CharSequence s)
	{
		return text.contains(s);
	}
	
	/**
	 * <p>Return sub text begins at beginIndex.</p>
	 * 
	 * <p>beginIndex 부터 시작하는 부분 텍스트를 반환합니다.</p>
	 * 
	 * @param beginIndex : index of start text
	 * @return sub text
	 */
	public HText substring(int beginIndex)
	{
		return new HText(text.substring(beginIndex));
	}
	/**
	 * <p>Return sub text begins at beginIndex and ends at endIndex.</p>
	 * 
	 * <p>beginIndex 부터 시작하고 endIndex 에서 끝나는 부분 텍스트를 반환합니다.</p>
	 * 
	 * @param beginIndex : index of start text
	 * @param endIndex : index of end text
	 * @return sub text
	 */
	public HText substring(int beginIndex, int endIndex)
	{
		return new HText(text.substring(beginIndex, endIndex));
	}
	
	/**
	 * <p>Return upper cased text.</p>
	 * 
	 * <p>소문자가 모두 대문자로 바뀐 결과를 반환합니다.</p>
	 * 
	 * @return upper cased text
	 */
	public HText toUpperCase()
	{
		return new HText(text.toUpperCase());
	}
	
	/**
	 * <p>Return lower cased text.</p>
	 * 
	 * <p>대문자가 모두 소문자로 바뀐 결과를 반환합니다.</p>
	 * 
	 * @return lower cased text
	 */
	public HText toLowerCase()
	{
		return new HText(text.toLowerCase());
	}
	
	/**
	 * <p>Return true if other text is same text without case-sensitive.</p>
	 * 
	 * <p>대소문자 구별하지 않고 같은 텍스트이면 true 를 반환합니다.</p>
	 * 
	 * @param other : other text
	 * @return true if other text is same text without case-sensitive
	 */
	public boolean equalsIgnoreCase(HText other)
	{
		return text.equalsIgnoreCase(other.getText());
	}
	/**
	 * <p>Return true if other text is same text without case-sensitive.</p>
	 * 
	 * <p>대소문자 구별하지 않고 같은 텍스트이면 true 를 반환합니다.</p>
	 * 
	 * @param other : other text
	 * @return true if other text is same text without case-sensitive
	 */
	public boolean equalsIgnoreCase(String other)
	{
		return text.equalsIgnoreCase(other);
	}
	/**
	 * <p>Return true if other text is same text.</p>
	 * 
	 * <p>대소문자 구별하고 같은 텍스트이면 true 를 반환합니다.</p>
	 * 
	 * @param other : other text
	 * @return true if other text is same text
	 */
	public boolean equals(HText other)
	{
		return text.equals(other.getText());
	}
	/**
	 * <p>Return true if other text is same text.</p>
	 * 
	 * <p>대소문자 구별하고 같은 텍스트이면 true 를 반환합니다.</p>
	 * 
	 * @param other : other text
	 * @return true if other text is same text
	 */
	public boolean equals(String other)
	{
		return text.equals(other);
	}
	
	/**
	 * <p>Compare with other text value of dictionary-index.</p>
	 * 
	 * <p>사전식 번호를 다른 텍스트와 비교한 결과를 반환합니다.</p>
	 * 
	 * @return result of comparing
	 */
	public int compareTo(HText other)
	{
		return text.compareTo(other.getText());
	}
	
	/**
	 * <p>Compare with other text value of dictionary-index.</p>
	 * 
	 * <p>사전식 번호를 다른 텍스트와 비교한 결과를 반환합니다.</p>
	 * 
	 * @return result of comparing
	 */
	public int compareTo(String other)
	{
		return text.compareTo(other);
	}
	
	/**
	 * <p>Return character array of text.</p>
	 * 
	 * <p>이 텍스트를 글자 배열로 반환합니다.</p>
	 * 
	 * @return character array of text
	 */
	public char[] toCharArray()
	{
		return text.toCharArray();
	}
	
	/**
	 * <p>Return byte array of text.</p>
	 * 
	 * <p>이 텍스트를 바이트 배열로 반환합니다.</p>
	 * 
	 * @return byte array
	 */
	public byte[] getBytes()
	{
		return text.getBytes();
	}
	/**
	 * <p>Return byte array of text.</p>
	 * 
	 * <p>이 텍스트를 바이트 배열로 반환합니다.</p>
	 * 
	 * @param charsetName : charset, such as UTF-8
	 * @return byte array
	 */
	public byte[] getBytes(String charsetName) throws Exception
	{
		return text.getBytes(charsetName);
	}
	
	/**
	 * <p>Remove \65279 unicode symbol.</p>
	 * 
	 * <p>\65279 유니코드 기호를 제거합니다.</p>
	 */
	public void removeR65279()
	{
		text = text.replace("\65279", "");
	}
	
	/**
	 * <p>Replace some text to other.</p>
	 * 
	 * <p>어떤 부분의 텍스트를 다른 부분으로 일괄 대체합니다.</p>
	 * 
	 * @param before : target pattern
	 * @param after : objective pattern
	 * @return : replaced text
	 */
	public HText replace(CharSequence before, CharSequence after)
	{
		return new HText(text.replace(before, after));
	}
	
	/**
	 * <p>Remove empty spaces at starts and ends.</p>
	 * 
	 * <p>텍스트의 양쪽 끝 공백을 제거합니다.</p>
	 * 
	 * @return trimmed text
	 */
	public HText trim()
	{
		return new HText(text.trim());
	}
	
	/**
	 * <p>Splits this string around matches of the given regular expression.</p>
	 * 
	 * <p>텍스트를 나눕니다.</p>
	 * 
	 * @param regex : the delimiting regular expression
	 * @return the array of strings computed by splitting this string around matches of the given regular expression
	 */
	public HText[] split(HText regex)
	{
		String[] tx =  text.split(regex.getText());
		HText[] results = new HText[tx.length];
		for(int i=0; i<results.length; i++)
		{
			results[i] = new HText(results[i]);
		}
		return results;
	}
	/**
	 * 
	 * <p>Replace " to \"
	 * if q is false, replace ' to \'</p>
	 * 
	 * <p>모든 쌍따옴표 앞에 \ 기호를 붙입니다.
	 * q가 false 이면 모든 일반 따옴표 앞에 \ 기호를 붙입니다.</p>
	 * 
	 * @param str : text
	 * @param q : define ' and "
	 * @return adds \ at each " if q is true, adds \ at each ' if q is false
	 */
	public HText replaceQuote(HText str, boolean q)
	{
		HText results;
		if(q) results = str.replace("\"", "\\" + "\"");
		else results = str.replace("'", "\\" + "'");
		return results;
	}	
}
