package lab_app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class AddBookPanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3335987885575467350L;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel outerMiddle;

	private JTextField ISBNField;
	private JTextField authField;
	private JTextField titleField;
	private JTextField maximumCheckoutLengthField;
	private JTextField numbefOfCopyField;

	public AddBookPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineOuterMiddle();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(outerMiddle, BorderLayout.CENTER);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Add Book Title");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		AddBookLabel.setForeground(Color.BLUE.darker().darker());
		AddBookLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		topPanel.add(AddBookLabel);
	}

	public void defineOuterMiddle() {
		outerMiddle = new JPanel();
		outerMiddle.setLayout(new BorderLayout());

		// set up left and right panels
		JPanel middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 25, 25);
		middlePanel.setLayout(fl);
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		JLabel ISBN = new JLabel("ISBN");
		JLabel titleLabel = new JLabel("Book Title");
		JLabel auth = new JLabel("Author");
		JLabel maximumCheckoutLength = new JLabel("Maximum checkout length");
		JLabel numbefOfCopy = new JLabel("Number of copy");

		ISBNField = new JTextField(10);
		titleField = new JTextField(10);
		authField = new JTextField(10);
		maximumCheckoutLengthField = new JTextField(10);
		numbefOfCopyField = new JTextField(10);

		leftPanel.add(ISBN);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(titleLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(auth);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(maximumCheckoutLength);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(numbefOfCopy);

		rightPanel.add(ISBNField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(titleField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(authField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(maximumCheckoutLengthField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(numbefOfCopyField);

		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);

		// add button at bottom
		JButton addBookButton = new JButton("Add Book");
		addBookButton.addActionListener(new SubmitLoginListener());
		JPanel addBookButtonPanel = new JPanel();
		addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		addBookButtonPanel.add(addBookButton);
		outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);

	}

	class SubmitLoginListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			try {
				validateBookInputs(ISBNField.getText(), authField.getText(), titleField.getText(),
						maximumCheckoutLengthField.getText(), numbefOfCopyField.getText());
				DataAccess da = new DataAccessFacade();
				HashMap<String, Book> books = da.readBooksMap();
				String isbn = ISBNField.getText();
				if (books.containsKey(isbn)) {
					StatusPanel.STATUS_INSTANCE.setStatus(isbn + " already exists");
				} else {
					int numbefOfCopy = Integer.valueOf(numbefOfCopyField.getText());
					int maximumCheckoutLength = Integer.valueOf(maximumCheckoutLengthField.getText());
					Book book = new Book(isbn, titleField.getText(), maximumCheckoutLength, null);
					for (int i = 0; i < numbefOfCopy; i++) {
						book.addCopy();
					}
					books.put(isbn, book);
					DataAccessFacade.saveBooks(books);
					StatusPanel.STATUS_INSTANCE
							.setStatus("Saved " + titleField.getText() + " with " + numbefOfCopy + " copys");
				}
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(AddBookPanel.this, e.getMessage());
			}
		}
	}

	public void updateData() {
		// nothing to do
	}

	public void validateBookInputs(String isbn, String title, String author, String maxCheckoutLength, String numCopies)
			throws IllegalArgumentException {
		// Check if ISBN is empty
		if (isbn == null || isbn.trim().isEmpty()) {
			throw new IllegalArgumentException("ISBN cannot be empty.");
		}

		// Check if the title is empty
		if (title == null || title.trim().isEmpty()) {
			throw new IllegalArgumentException("Book title cannot be empty.");
		}

		// Check if author is empty
		if (author == null || author.trim().isEmpty()) {
			throw new IllegalArgumentException("Author name cannot be empty.");
		}

		// Check if max checkout length is empty
		if (maxCheckoutLength == null || maxCheckoutLength.trim().isEmpty()) {
			throw new IllegalArgumentException("Maximum checkout length cannot be empty.");
		}

		// Check if number of copies is empty
		if (numCopies == null || numCopies.trim().isEmpty()) {
			throw new IllegalArgumentException("Number of copies cannot be empty.");
		}
	}

//	public static void main(String[] args) {
//		JFrame a = new JFrame();
//		a.setSize(640, 360);
//		JPanel mainPanel = new AddBookPanel().getMainPanel();
//		a.add(mainPanel);
//		a.setVisible(true);
//	}
}
