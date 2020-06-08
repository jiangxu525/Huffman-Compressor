package gui;

import javax.swing.SwingUtilities;
/**
 * 
 * @ClassName: App
 * @Description: user app
 * @author Xu
 * @date 2020-06-08 10:12:44
 */
public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			@SuppressWarnings("unused")
			MainFrame frame = new MainFrame();
		});
	}
}
