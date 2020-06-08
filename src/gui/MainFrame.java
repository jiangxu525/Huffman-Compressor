package gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import Utils.Compressor;

/**
 * 
 * @ClassName: MainFrame
 * @Description: main GUI frame
 * @author Xu
 * @date 2020-06-08 10:21:20
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private TextPanel textPanel;
	private JFileChooser zipFiles;
	private JFileChooser unzipFile;
	private FormPanel formPanel;

	public MainFrame() {
		// configuration for mainFrame
		super("Huffman Compressor");
		setLayout(new BorderLayout());
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		// set up menu bar
		setJMenuBar(createMenuBar());

		// initiate parameters
		textPanel = new TextPanel();
		zipFiles = new JFileChooser();
		zipFiles.setMultiSelectionEnabled(true);
		unzipFile = new JFileChooser();
		formPanel = new FormPanel();

		// add content to mainFrame
		add(formPanel, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);

		formPanel.setFormListener(event -> {
			action(event.getEventType());
		});

	}

	/**
	 * 
	 * @Title: createMenuBar
	 * @Description: menu bar
	 * @return
	 * @author Xu
	 * @date 2020-06-08 10:21:31
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem importDataItem = new JMenuItem("Import files to zip...");
		JMenuItem exportDataItem = new JMenuItem("Unzip file...");
		JMenuItem exitItem = new JMenuItem("Exit");

		// set up file menu
		fileMenu.add(importDataItem);
		fileMenu.add(exportDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		// action for importDataItem
		importDataItem.addActionListener(e -> {
			action(0);
		});

		// action for exportDataItem
		exportDataItem.addActionListener(e -> {
			action(1);
		});

		// action for exitItem
		exitItem.addActionListener(e -> {
			int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the application?",
					"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
			if (action == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		});

		// set up window menu
		JMenu windowMenu = new JMenu("Window");
		JMenu recent = new JMenu("Recent");
		JMenuItem help = new JMenuItem("Help");

		help.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URL("https://github.com/jiangxu525").toURI());
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		});

		windowMenu.add(recent);
		windowMenu.add(help);

		// set up menuBar
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		return menuBar;
	}

	/**
	 * 
	 * @Title: action
	 * @Description: eventType = 0 --> choose file , eventType = 1 --> zip file to,
	 *               eventType = 2 --> unzip file to
	 * @param eventType
	 * @author Xu
	 * @date 2020-06-08 10:21:42
	 */

	private void action(int eventType) {
		if (eventType == 0) {
			if (zipFiles.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
				File[] files = zipFiles.getSelectedFiles();
				for (File file : files) {
					textPanel.appendText(file.toString());
				}
				formPanel.getChooseFileField().setText(Compressor.zipFiles(files).getAbsolutePath());
				textPanel.appendText(
						"***********************************Files have been zipped successfully***********************************");
			}
		}
		if (eventType == 1) {
			if (unzipFile.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
				File file = unzipFile.getSelectedFile();
				formPanel.getSaveFileField().setText(unzipFile.getSelectedFile().toString());
				textPanel.appendText(unzipFile.getSelectedFile().toString());
				Compressor.unzipFiles(file);
				textPanel.appendText("*****Files have been zipped in the folder successfully*****");
			}
		}

	}
}
