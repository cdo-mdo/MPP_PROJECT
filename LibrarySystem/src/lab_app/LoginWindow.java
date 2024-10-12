package lab_app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dataaccess.DataAccessFacade;
import dataaccess.DataAccess;
import dataaccess.User;
import dataaccess.Auth;

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
	private JTextField userText;
	private JPasswordField pwdText;
	private JButton signin;
	private String ID;
	private String pass;

	private static LibWindow[] allWindows = { 
		LoginWindow.INSTANCE, 
	};

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
		//set sign in as default button when press enter to login in
		getRootPane().setDefaultButton(signin);
		setSize(960, 540);
		isInitialized = true;

	}
	private String getPassword() {
    	char[] pwdAsChars = pwdText.getPassword();
		String pwd = new String(pwdAsChars);
		return pwd;
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
		JLabel label = new JLabel("Welcome to the Library System. Please login!");
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
		userText = new JTextField(11);
		pwdText = new JPasswordField(11);
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
		signin = new JButton("Sign in");
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
			DataAccess da = new DataAccessFacade();
			HashMap<String, User> users = da.readUserMap();
			for (Map.Entry<String, User> entry : users.entrySet()) {
			    System.out.println("Key: " + entry.getKey() + ", User: " + entry.getValue());
			}
			ID = userText.getText();
			pass = getPassword();
			
			System.out.println(ID);
			System.out.println(pass);
			
			if (users.containsKey(ID)){
				User user = users.get(ID);
				if (pass.equals(user.getPassword())) {
					Auth auth = user.getAuthorization();
					System.out.println(auth.toString());
					LoginWindow.hideAllWindows();
					EventQueue.invokeLater(() ->
			         {
			        	MainWindow frame = new MainWindow();
			        	if (auth==Auth.ADMIN) {
			        		frame.updateList(frame.group2);
			        	}else if(auth==Auth.LIBRARIAN) {
			        		frame.updateList(frame.group1);
			        	}else {
			        		frame.updateList(null);
			        	}
			        	clearFields();
			            frame.setTitle("Library Management System");
			            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			            centerFrameOnDesktop(frame);
			            frame.setVisible(true);
			            
			         });
					
				}else {
					JOptionPane.showMessageDialog(LoginWindow.this, "Password is wrong! Please try again.");
				}
				
			}else {
				JOptionPane.showMessageDialog(LoginWindow.this, "Username not found! Please try again.");
			}

		});
	}
	   public static void centerFrameOnDesktop(Component f) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int height = toolkit.getScreenSize().height;
			int width = toolkit.getScreenSize().width;
			int frameHeight = f.getSize().height;
			int frameWidth = f.getSize().width;
			f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
		}
	public static void hideAllWindows() {
		for (LibWindow frame : allWindows) {
			frame.setVisible(false);
		}
	}
	
	private void clearFields() {
		userText.setText("");
		pwdText.setText("");
	}

//	private void addLoginButtonListener(JButton butn) {
//		butn.addActionListener(evt -> {
//			JOptionPane.showMessageDialog(this,"Successful Login");
//
//		});
//	}

}
