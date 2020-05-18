package gui;

import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			@SuppressWarnings("unused")
			MainFrame frame = new MainFrame();
		});
	}
}
