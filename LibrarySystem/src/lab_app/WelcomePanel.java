package lab_app;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePanel {
	private JPanel mainPanel;
	private String pathToImage;

	public WelcomePanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setPathToImage();
		insertSplashImage();
		//JLabel welcome = new JLabel("WELCOME TO LIBRARY MANAGEMENT SYSTEM");
		//mainPanel.add(welcome);

	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	private void setPathToImage() {
		String currDirectory = System.getProperty("user.dir");
		pathToImage = currDirectory + File.separator + "src" + File.separator + "librarysystem" + File.separator
				+ "pexels-repuding-12064.jpg";
	}
	
	private void insertSplashImage() {
		JPanel imagePanel = new JPanel();
		ImageIcon image = new ImageIcon(pathToImage);
		imagePanel.add(new JLabel(image));
		mainPanel.add(imagePanel);
	}

	public static void main(String[] args) {
		JFrame a = new JFrame();
		a.setSize(640, 360);
		JPanel mainPanel = new WelcomePanel().getMainPanel();
		a.add(mainPanel);
		a.setVisible(true);
	}

}
