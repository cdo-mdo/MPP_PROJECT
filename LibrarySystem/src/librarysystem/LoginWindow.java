package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends JFrame implements LibWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1046853482432425957L;
	public static final LoginWindow INSTANCE = new LoginWindow();
	private boolean isInitialized = false;
	private JPanel mainPanel;
	private String pathToImage;
	private JPanel leftPanel;
	private JPanel rightPanel;

	private static LibWindow[] allWindows = { 
			LibrarySystem.INSTANCE, 
			LoginWindow.INSTANCE, 
			AllMemberIdsWindow.INSTANCE,
			AllBookIdsWindow.INSTANCE };

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private JTextField messageBar = new JTextField();

	public void clear() {
		messageBar.setText("");
	}
	
	private LoginWindow() {
	}

	@Override
	public void init() {
    	formatContentPane();
		setPathToImage();
		insertSplashImage();
		addLoginPanel();
		setSize(960, 540);
		isInitialized = true;

	}

	private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		getContentPane().add(mainPanel);
	}

	private void setPathToImage() {
		String currDirectory = System.getProperty("user.dir");
		pathToImage = currDirectory + File.separator + "src" + File.separator + "librarysystem" + File.separator
				+ "pexels-gesel-757855.jpg";
		System.out.println(pathToImage);
	}

	private void insertSplashImage() {
		leftPanel = new JPanel();
		ImageIcon image = new ImageIcon(pathToImage);
		leftPanel.add(new JLabel(image));
		mainPanel.add(leftPanel);
	}

	private void addLoginPanel() {
		rightPanel = new JPanel();
		JPanel mainLoginPanel = new JPanel();

 		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel label = new JLabel("Welcome to the Lybrary System. Please login!");
		label.setForeground(Color.BLUE.darker().darker());
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		topPanel.add(label);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		JPanel usnrupper = new JPanel();
		JPanel pwdlower = new JPanel();
		usnrupper.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4));
		pwdlower.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4));
		JLabel username = new JLabel("Username");
		JLabel password = new JLabel("Password");
		password.setPreferredSize(username.getPreferredSize());
		JTextField userText = new JTextField(11);
		JTextField pwdText = new JTextField(11);
		usnrupper.add(username);
		usnrupper.add(userText);
		pwdlower.add(password);
		pwdlower.add(pwdText);
		middlePanel.add(usnrupper, BorderLayout.NORTH);
		middlePanel.add(pwdlower, BorderLayout.CENTER);

		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout(8, 8));
		JPanel upper = new JPanel();
		JPanel lower = new JPanel();
		upper.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton signin = new JButton("Sign in");
		addLoginButtonListener(signin);
		// signin.addActionListener(Control.INSTANCE.getSubmitLoginListener());
		JButton exit = new JButton("Exit");
		exit.addActionListener(new TerminateProgram());
		
		upper.add(signin);
		upper.add(exit);
		lower.setLayout(new FlowLayout(FlowLayout.CENTER));
		lowerPanel.add(upper, BorderLayout.NORTH);
		lowerPanel.add(lower, BorderLayout.CENTER);

		mainLoginPanel.setLayout(new BorderLayout(12, 12));
		mainLoginPanel.add(topPanel, BorderLayout.NORTH);
		mainLoginPanel.add(middlePanel, BorderLayout.CENTER);
		mainLoginPanel.add(lowerPanel, BorderLayout.SOUTH);
		// getContentPane().add(mainPanel);

		rightPanel.add(mainLoginPanel);
		mainPanel.add(rightPanel);
	}
	
	class TerminateProgram implements ActionListener{
		@Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);  // Terminates the program
        }
	}

	private void addLoginButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LoginWindow.hideAllWindows();
			LibrarySystem.INSTANCE.setTitle("Lybrary Management System");
			LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			LibrarySystem.INSTANCE.init();
			Main.centerFrameOnDesktop(LibrarySystem.INSTANCE);
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	public static void hideAllWindows() {
		for (LibWindow frame : allWindows) {
			frame.setVisible(false);
		}
	}

//	private void addLoginButtonListener(JButton butn) {
//		butn.addActionListener(evt -> {
//			JOptionPane.showMessageDialog(this,"Successful Login");
//
//		});
//	}

}
