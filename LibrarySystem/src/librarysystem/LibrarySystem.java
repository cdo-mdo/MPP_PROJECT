package librarysystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import business.ControllerInterface;
import business.SystemController;


public class LibrarySystem extends JFrame implements LibWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8218447337255928117L;
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds;
    String pathToImage;
    
    JPanel leftPanel;
	JPanel rightPanel;
	
	
	JList<String> linkList;
	JPanel cards;
	JPanel outputpanel;
	
	JPanel menuPanel1;
	JPanel menuPanel2;
	JPanel menuPanel3;
    
    
    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,
		AllBookIdsWindow.INSTANCE
	};

	public static void hideAllWindows() {
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);
		}
	}

    private LibrarySystem() {}

    @Override
	public void init() {
//    	formatContentPane();
//    	setPathToImage();
//    	insertSplashImage();
//    	addLoginPanel();

		//createMenus();
		//pack();
		setSize(960,540);
		//setSize(640,360);
		isInitialized = true;
		
		String[] items = {"Login", "View Titles", "Add Book"};
		linkList = new JList<String>(items);				
		createPanels();
//		createOutputBar();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
		splitPane.setDividerLocation(150);
		JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, outputpanel);
		outerPane.setDividerLocation(350);
		add(outerPane, BorderLayout.CENTER);
    }
    
    public void createPanels() {

    	menuPanel1 = new JPanel();
    	menuPanel1.setLayout(new BorderLayout(12,12));
        //createLoginPanel();
        
        menuPanel2 = new JPanel();
        menuPanel2.setLayout(new BorderLayout(12,12));
	    //createTitlesPanel();
        
	    menuPanel3 = new JPanel();
	    menuPanel3.setLayout(new BorderLayout(12,12));
        //createAddBookPanel();
        
		cards = new JPanel(new CardLayout());
		cards.add(menuPanel1, "Login");
		cards.add(menuPanel2, "View Titles");
		cards.add(menuPanel3, "Add Book");
		
		linkList.addListSelectionListener(event -> {
			String value = linkList.getSelectedValue().toString();
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, value);
		});

	}

    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		getContentPane().add(mainPanel);
	}

    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+File.separator+"src"+File.separator+"librarysystem"+File.separator+"pexels-gesel-757855.jpg";
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
		usnrupper.setLayout(new FlowLayout(FlowLayout.CENTER,15,4));
		pwdlower.setLayout(new FlowLayout(FlowLayout.CENTER,15,4));
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
		lowerPanel.setLayout(new BorderLayout(8,8));
		JPanel upper = new JPanel();
		JPanel lower = new JPanel();
		upper.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton signin = new JButton("Sign in");
		//signin.addActionListener(Control.INSTANCE.getSubmitLoginListener());
		JButton back = new JButton("Exit");
		//back.addActionListener(evt -> Control.INSTANCE.backToStart(this));
		upper.add(signin);
		upper.add(back);
		lower.setLayout(new FlowLayout(FlowLayout.CENTER));
		lowerPanel.add(upper, BorderLayout.NORTH);
		lowerPanel.add(lower, BorderLayout.CENTER);
		
		
		mainLoginPanel.setLayout(new BorderLayout(12,12));
		mainLoginPanel.add(topPanel, BorderLayout.NORTH);
		mainLoginPanel.add(middlePanel, BorderLayout.CENTER);
		mainLoginPanel.add(lowerPanel, BorderLayout.SOUTH);
		//getContentPane().add(mainPanel);
		
		rightPanel.add(mainLoginPanel);
		mainPanel.add(rightPanel);
	}
    
//    private void createMenus() {
//    	menuBar = new JMenuBar();
//		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
//		addMenuItems();
//		setJMenuBar(menuBar);
//    }

//    private void addMenuItems() {
//       options = new JMenu("Options");
// 	   menuBar.add(options);
// 	   login = new JMenuItem("Login");
// 	   login.addActionListener(new LoginListener());
// 	   allBookIds = new JMenuItem("All Book Ids");
// 	   allBookIds.addActionListener(new AllBookIdsListener());
// 	   allMemberIds = new JMenuItem("All Member Ids");
// 	   allMemberIds.addActionListener(new AllMemberIdsListener());
// 	   options.add(login);
// 	   options.add(allBookIds);
// 	   options.add(allMemberIds);
//    }

    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);

		}

    }
    class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);

		}

    }

    class AllMemberIdsListener implements ActionListener {

    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setVisible(true);


			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
			AllMemberIdsWindow.INSTANCE.pack();
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);


		}

    }

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;

	}

}
