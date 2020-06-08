package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * 
 * @ClassName: TextPanel
 * @Description: TextPanel for the gui
 * @author Xu
 * @date 2020-06-08 10:20:50
 */
public class TextPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	public TextPanel() {
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Arial", Font.PLAIN,12));
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		textArea.setEditable(false);
		appendText("Welcome!\nBased on Huffman Code, this compressor is very powerful in compressing text files like .txt or .bat instead of files like video or PPT files which have been already compressed."
				+ "\n********************************************************************************************************************");
	}
	/**
	 * 
	 * @Title: appendText
	 * @Description: append text to the TextPanel
	 * @param text
	 * @author Xu
	 * @date 2020-06-08 10:21:02
	 */
	public void appendText(String text) {
		textArea.append(text + "\n");
	}
}
