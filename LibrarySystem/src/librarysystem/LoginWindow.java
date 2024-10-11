package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

//	private JPanel mainPanel;
//	private JPanel upperHalf;
//	private JPanel middleHalf;
//	private JPanel lowerHalf;
//	private JPanel container;
//
//	private JPanel topPanel;
//	private JPanel middlePanel;
//	private JPanel lowerPanel;
//	private JPanel leftTextPanel;
//	private JPanel rightTextPanel;
//
//	private JTextField username;
//	private JTextField password;
//	private JLabel label;
//	private JButton loginButton;
//	private JButton logoutButton;

	private JPanel mainPanel;

	private String pathToImage;

	private JPanel leftPanel;
	private JPanel rightPanel;

	private static LibWindow[] allWindows = { LibrarySystem.INSTANCE, LoginWindow.INSTANCE, AllMemberIdsWindow.INSTANCE,
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

	/* This class is a singleton */
	private LoginWindow() {
	}

	@Override
	public void init() {
//    		mainPanel = new JPanel();
//    		defineUpperHalf();
//    		defineMiddleHalf();
//    		defineLowerHalf();
//    		BorderLayout bl = new BorderLayout();
//    		bl.setVgap(30);
//    		mainPanel.setLayout(bl);
//
//    		mainPanel.add(upperHalf, BorderLayout.NORTH);
//    		mainPanel.add(middleHalf, BorderLayout.CENTER);
//    		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
//    		getContentPane().add(mainPanel);
//    		isInitialized(true);
//    		pack();
		// setSize(660, 500);

		formatContentPane();
		setPathToImage();
		insertSplashImage();
		addLoginPanel();

		// createMenus();
		// pack();
		setSize(960, 540);
		// setSize(640,360);
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
		JButton back = new JButton("Exit");
		// back.addActionListener(evt -> Control.INSTANCE.backToStart(this));
		upper.add(signin);
		upper.add(back);
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

//    private void defineUpperHalf() {
//
//		upperHalf = new JPanel();
//		upperHalf.setLayout(new BorderLayout());
//		defineTopPanel();
//		defineMiddlePanel();
//		defineLowerPanel();
//		upperHalf.add(topPanel, BorderLayout.NORTH);
//		upperHalf.add(middlePanel, BorderLayout.CENTER);
//		upperHalf.add(lowerPanel, BorderLayout.SOUTH);
//
//	}
//	private void defineMiddleHalf() {
//		middleHalf = new JPanel();
//		middleHalf.setLayout(new BorderLayout());
//		JSeparator s = new JSeparator();
//		s.setOrientation(SwingConstants.HORIZONTAL);
//		//middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
//		middleHalf.add(s, BorderLayout.SOUTH);
//
//	}
//	private void defineLowerHalf() {
//
//		lowerHalf = new JPanel();
//		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
//
//		JButton backButton = new JButton("<= Back to Main");
//		addBackButtonListener(backButton);
//		lowerHalf.add(backButton);
//
//	}
//	private void defineTopPanel() {
//		topPanel = new JPanel();
//		JPanel intPanel = new JPanel(new BorderLayout());
//		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
//		JLabel loginLabel = new JLabel("Login");
//		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
//		intPanel.add(loginLabel, BorderLayout.CENTER);
//		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		topPanel.add(intPanel);
//
//	}
//
//
//
//	private void defineMiddlePanel() {
//		middlePanel=new JPanel();
//		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		defineLeftTextPanel();
//		defineRightTextPanel();
//		middlePanel.add(leftTextPanel);
//		middlePanel.add(rightTextPanel);
//	}
//	private void defineLowerPanel() {
//		lowerPanel = new JPanel();
//		loginButton = new JButton("Login");
//		addLoginButtonListener(loginButton);
//		lowerPanel.add(loginButton);
//	}
//
//	private void defineLeftTextPanel() {
//
//		JPanel topText = new JPanel();
//		JPanel bottomText = new JPanel();
//		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
//		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
//
//		username = new JTextField(10);
//		label = new JLabel("Username");
//		label.setFont(Util.makeSmallFont(label.getFont()));
//		topText.add(username);
//		bottomText.add(label);
//
//		leftTextPanel = new JPanel();
//		leftTextPanel.setLayout(new BorderLayout());
//		leftTextPanel.add(topText,BorderLayout.NORTH);
//		leftTextPanel.add(bottomText,BorderLayout.CENTER);
//	}
//	private void defineRightTextPanel() {
//
//		JPanel topText = new JPanel();
//		JPanel bottomText = new JPanel();
//		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
//		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
//
//		password = new JPasswordField(10);
//		label = new JLabel("Password");
//		label.setFont(Util.makeSmallFont(label.getFont()));
//		topText.add(password);
//		bottomText.add(label);
//
//		rightTextPanel = new JPanel();
//		rightTextPanel.setLayout(new BorderLayout());
//		rightTextPanel.add(topText,BorderLayout.NORTH);
//		rightTextPanel.add(bottomText,BorderLayout.CENTER);
//	}

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
