package lab_app;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddBookCopyPanel extends JFrame {
	JPanel titlePanel;
	JLabel title;
	
	JPanel searchPanel;
	JLabel isbnLabel;
	JTextField isbnText;
	JButton searchButton;
	
	AddBookCopyPanel() {
		setSize(600, 150);
		
		this.setLayout(new GridLayout(3,1));
		
		defineTitlePanel();
		defineSearchPanel();
		
		add(titlePanel);
		add(searchPanel);
	}
	
	void defineTitlePanel() {
		titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		title = new JLabel("Add Copy of Book");
		titlePanel.add(title);
	}
	
	void defineSearchPanel() {
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		isbnLabel = new JLabel("ISBN");
		isbnText = new JTextField(12);
		searchButton = new JButton("Search");
		
		searchPanel.add(isbnLabel);
		searchPanel.add(isbnText);
		searchPanel.add(searchButton);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new AddBookCopyPanel();
			frame.setTitle("Add Book Copy");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			centerFrameOnDesktop(frame);
			frame.setVisible(true);
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
}
