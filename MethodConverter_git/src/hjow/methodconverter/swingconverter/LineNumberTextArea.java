package hjow.methodconverter.swingconverter;

import hjow.methodconverter.ui.TextAreaComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.PopupMenu;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorListener;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * <p>This swing component can show line number.</p>
 * 
 * <p>이 Swing 컴포넌트는 여러 줄 텍스트와 줄 번호까지 보여줍니다.</p>
 * 
 * @author HJOW
 *
 */
public class LineNumberTextArea extends TransparentPanel implements TextAreaComponent
{
	private static final long serialVersionUID = 2879465797975417227L;
	private TransparentTextArea textPanel;
	private TransparentScrollPane textScroll;
	private JTextArea lineNumberPanel;
	private boolean hideNumberFieldWhenEmpty = true;
	private boolean numberFieldIsHidden = true;
	public LineNumberTextArea()
	{
		setLayout(new BorderLayout());
		
		System.out.println("LineNumberTextArea is initialized : " + this.getClass());
		
		textPanel = new TransparentTextArea();	
		textScroll = new TransparentScrollPane();		
		lineNumberPanel = new JTextArea();
		
		lineNumberPanel.setEditable(false);
		
		textPanel.getDocument().addDocumentListener(new DocumentListener()
		{			
			private String getText()
			{
				int caretPos = textPanel.getDocument().getLength();
				Element el = textPanel.getDocument().getDefaultRootElement();
				
				StringBuffer text = new StringBuffer("1\n");
				for(int i=2; i<el.getElementIndex(caretPos) + 2; i++)
				{
					text = text.append(String.valueOf(i));
					text = text.append("\n");
				}
				SwingUtilities.invokeLater(new Runnable()
				{					
					@Override
					public void run()
					{
						refreshLineNumberArea();
					}
				});
				return text.toString();
			}
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				lineNumberPanel.setText(getText());				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				lineNumberPanel.setText(getText());				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e)
			{
				lineNumberPanel.setText(getText());				
			}
		});
		
		textScroll.getViewport().add(textPanel);
		textScroll.setRowHeaderView(lineNumberPanel);
		textScroll.setVerticalScrollBarPolicy(TransparentScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(textScroll, BorderLayout.CENTER);
		refreshLineNumberArea();
	}
	
	public void setText(String text)
	{
		textPanel.setText(text);
	}
	public String getText()
	{
		return textPanel.getText();
	}
	public void append(String text)
	{
		textPanel.append(text);
	}
	public void setVisible(boolean visible)
	{
		textPanel.setVisible(visible);
	}
	public boolean isVisible()
	{
		return textPanel.isVisible();
	}
	public void setEditable(boolean editables)
	{
		textPanel.setEditable(editables);
	}
	public void setLineWrap(boolean lw)
	{
		textPanel.setLineWrap(lw);
	}
	public void setFont(Font font)
	{
		try
		{
			textPanel.setFont(font);
		}
		catch(NullPointerException e)
		{
			
		}
	}
	public void setEnabled(boolean enabled)
	{
		textPanel.setEnabled(enabled);
	}
	public void addCaretListener(CaretListener listener)
	{
		textPanel.addCaretListener(listener);
	}
	public void addFocusListener(FocusListener listener)
	{
		textPanel.addFocusListener(listener);
	}
	public void addKeyListener(KeyListener listener)
	{
		textPanel.addKeyListener(listener);
	}
	public void addMouseListener(MouseListener listener)
	{
		textPanel.addMouseListener(listener);
	}
	public void addAncestorListener(AncestorListener listener)
	{
		textPanel.addAncestorListener(listener);
	}
	public void removeKeyListener(KeyListener listener)
	{
		textPanel.removeKeyListener(listener);
	}
	public void removeaddFocusListener(FocusListener listener)
	{
		textPanel.removeFocusListener(listener);
	}
	public void removeCaretListener(CaretListener listener)
	{
		textPanel.removeCaretListener(listener);
	}
	public void removeMouseListener(MouseListener listener)
	{
		textPanel.removeMouseListener(listener);
	}
	public void removeAncestorListener(AncestorListener listener)
	{
		textPanel.removeAncestorListener(listener);
	}
	public Document getDocument()
	{
		return textPanel.getDocument();
	}
	public void setDocument(Document doc)
	{
		textPanel.setDocument(doc);
	}
	public void setCaretPosition(int caretPos)
	{
		textPanel.setCaretPosition(caretPos);
	}
	public void setBackground(Color background)
	{
		try
		{
			textPanel.setBackground(background);
		}
		catch(NullPointerException e)
		{
			
		}
	}
	public void setForeground(Color foreground)
	{
		try
		{
			textPanel.setForeground(foreground);
		}
		catch(NullPointerException e)
		{
			
		}
	}
	public JTextArea getTextArea()
	{
		return textPanel;
	}
	public JTextArea getLineNumberArea()
	{
		return lineNumberPanel;
	}
	public TransparentScrollPane getScrollPane()
	{
		return textScroll;
	}
	public void setLineNumberVisible(boolean v)
	{
		lineNumberPanel.setVisible(v);
		refreshLineNumberArea();
	}
	public boolean isLineNumberVisible()
	{
		return lineNumberPanel.isVisible();
	}
	@Override
	public Component add(Component comp)
	{
		return textPanel.add(comp);
	}
	public void add(PopupMenu comp)
	{
		textPanel.add(comp);
	}
	protected void refreshLineNumberArea()
	{
		if(hideNumberFieldWhenEmpty)
		{
			if(getText() == null || getText().equals(""))
			{
				lineNumberPanel.setVisible(false);
				turnOffLineNumber();
			}
			else
			{
				lineNumberPanel.setVisible(true);
				turnOnLineNumber();
			}				
		}
	}
	
	private void turnOffLineNumber()
	{
		if(numberFieldIsHidden)
		{
			lineNumberPanel.setBackground(textPanel.getBackground());
			lineNumberPanel.setForeground(textPanel.getForeground());
			
			numberFieldIsHidden = false;
		}
	}
	
	private void turnOnLineNumber()
	{
		if(! numberFieldIsHidden)
		{
			Color textPanelColor = textPanel.getBackground();
			int maxColorValue = 0;
			double ratio = 1.2;
			if(maxColorValue < textPanelColor.getRed()) maxColorValue = textPanelColor.getRed();
			if(maxColorValue < textPanelColor.getGreen()) maxColorValue = textPanelColor.getGreen();
			if(maxColorValue < textPanelColor.getBlue()) maxColorValue = textPanelColor.getBlue();
			if(((double) maxColorValue) > (255.0 / 2.0))
			{
				lineNumberPanel.setBackground(new Color((int)(textPanelColor.getRed()/ratio)
						, (int)(textPanelColor.getGreen()/ratio), (int)(textPanelColor.getBlue()/ratio)));
			}
			else
			{
				lineNumberPanel.setBackground(new Color((int)(textPanelColor.getRed()*ratio)
						, (int)(textPanelColor.getGreen()*ratio), (int)(textPanelColor.getBlue()*ratio)));
			}
			
			textPanelColor = textPanel.getForeground();
			maxColorValue = 0;
			ratio = 1.2;
			if(maxColorValue < textPanelColor.getRed()) maxColorValue = textPanelColor.getRed();
			if(maxColorValue < textPanelColor.getGreen()) maxColorValue = textPanelColor.getGreen();
			if(maxColorValue < textPanelColor.getBlue()) maxColorValue = textPanelColor.getBlue();
			if(((double) maxColorValue) > (255.0 / 2.0))
			{
				lineNumberPanel.setForeground(new Color((int)(textPanelColor.getRed()/ratio)
						, (int)(textPanelColor.getGreen()/ratio), (int)(textPanelColor.getBlue()/ratio)));
			}
			else
			{
				lineNumberPanel.setForeground(new Color((int)(textPanelColor.getRed()*ratio)
						, (int)(textPanelColor.getGreen()*ratio), (int)(textPanelColor.getBlue()*ratio)));
			}
			numberFieldIsHidden = true;
		}		
	}
	
	/**
	 * <p>Return opacity rates as float value. If this value is lower, this panel is become more transparent. 0.0 ~ 1.0</p>
	 * 
	 * <p>투명도를 실수 값으로 반환합니다. 낮을 수록 투명해집니다. 0.0 ~ 1.0 사이값을 사용합니다.</p>
	 * 
	 * @return opacity percentage
	 */
	public float getTransparency_opacity()
	{
		return textPanel.getTransparency_opacity();
	}
	/**
	 * <p>Set opacity as float value. If this value is lower, this panel is become more transparent. 0.0 ~ 1.0</p>
	 * 
	 * <p>투명도를 지정합니다. 낮을 수록 투명해집니다. 0.0 ~ 1.0 사이값을 사용합니다.</p>
	 * 
	 * @param transparency_opacity : opacity rates
	 */
	public void setTransparency_opacity(float transparency_opacity)
	{
		textPanel.setTransparency_opacity(transparency_opacity);
	}

	public TransparentTextArea getTextPanel()
	{
		return textPanel;
	}

	public void setTextPanel(TransparentTextArea textPanel)
	{
		this.textPanel = textPanel;
	}

	public TransparentScrollPane getTextScroll()
	{
		return textScroll;
	}

	public void setTextScroll(TransparentScrollPane textScroll)
	{
		this.textScroll = textScroll;
	}

	public JTextArea getLineNumberPanel()
	{
		return lineNumberPanel;
	}

	public void setLineNumberPanel(JTextArea lineNumberPanel)
	{
		this.lineNumberPanel = lineNumberPanel;
	}

	public boolean isHideNumberFieldWhenEmpty()
	{
		return hideNumberFieldWhenEmpty;
	}

	public void setHideNumberFieldWhenEmpty(boolean hideNumberFieldWhenEmpty)
	{
		this.hideNumberFieldWhenEmpty = hideNumberFieldWhenEmpty;
	}

	@Override
	public Component getComponent()
	{
		return this;
	}
}