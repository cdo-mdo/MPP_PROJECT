package librarysystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import business.ControllerInterface;
import business.SystemController;

import lab_app.AddBookCopyPanel;
import lab_app.checkoutBookPanel;

public class LibrarySystem extends JFrame implements LibWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8218447337255928117L;
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE = new LibrarySystem();
	
	public static final String ADD_MEMBER = "Add Library Member";
	public static final String CHECKOUT_BOOK = "Checkout Book";
	public static final String ADD_COPY_OF_BOOK = "Add Copy of Book";
	
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
	
	AddLibrarianWindow menuPanel1;
	JPanel menuPanel2;
	AddBookCopyPanel menuPanel3;
	
	StatusPanel statusPanel;
	//JLabel outputDisplay;
    
    
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
	
//	public void setMessage(String s) {
//		outputDisplay.setText(s);
//	}

    private LibrarySystem() {}

    @Override
	public void init() {
		setSize(960,540);
		isInitialized = true;
		
		String[] items = {ADD_MEMBER, CHECKOUT_BOOK, ADD_COPY_OF_BOOK};
		linkList = new JList<String>(items);	
		statusPanel = new StatusPanel();
		createPanels();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
		splitPane.setDividerLocation(180);
		statusPanel = StatusPanel.STATUS_INSTANCE;
		JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, statusPanel);
		outerPane.setDividerLocation(432);
		add(outerPane, BorderLayout.CENTER);
    }
    
    public void createPanels() {
    	menuPanel1 = new AddLibrarianWindow();
    	menuPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPanel2 = new checkoutBookPanel().getMainPanel();
        menuPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
	    menuPanel3 = new AddBookCopyPanel(statusPanel);
        
		cards = new JPanel(new CardLayout());
		cards.add(menuPanel1, ADD_MEMBER);
		cards.add(menuPanel2, CHECKOUT_BOOK);
		cards.add(menuPanel3, ADD_COPY_OF_BOOK);
		
		linkList.addListSelectionListener(event -> {
			String value = linkList.getSelectedValue().toString();
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, value);
		});

	}
    
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
