package lab_app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.BookCopy;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class SearchCheckoutRecordByBook {
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JScrollPane scrollPane = new JScrollPane();

	private JTextField isnb;

	private JButton checkAvailableButton;
	private DefaultTableModel tableModel;
	private JTable table;

	public SearchCheckoutRecordByBook() {
		defineTopPanel();
		defineMiddlePanel();
		String[] columnNames = {"Name","Member ID", "Checkout Date", "Due Date", "Title", "Copy Number","ISBN"};
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(600, 100));
        scrollPane = new JScrollPane(table);
        
		mainPanel = new JPanel();
		mainPanel.add(topPanel,BorderLayout.NORTH);
		mainPanel.add(middlePanel,BorderLayout.CENTER);
		// defineScrollPane();
		mainPanel.add(scrollPane, BorderLayout.SOUTH);

	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Search checkout record");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(AddBookLabel);
	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());

		// add item components
		JPanel addItemPanel = new JPanel();
		addItemPanel.setLayout(new BorderLayout());

		JPanel N1 = new JPanel();
		N1.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel name1 = new JLabel("ISBN");
		isnb = new JTextField(10);
		N1.add(name1);
		N1.add(isnb);
		addItemPanel.add(N1, BorderLayout.NORTH);

		JPanel addItemPanel1 = new JPanel();
		addItemPanel1.setLayout(new BorderLayout());
		checkAvailableButton = new JButton("Search");
		addItemPanel1.add(checkAvailableButton, BorderLayout.SOUTH);

		checkAvailableButton.addActionListener(new SubmitLoginListener());

		middlePanel.add(addItemPanel, BorderLayout.NORTH);
		middlePanel.add(addItemPanel1, BorderLayout.CENTER);

	}

	public void defineScrollPane(String[][] data,String isbn) {
		for (String[] row : data) {
			if(row[6].equals(isbn)) {
				tableModel.addRow(row);
			}
        }
	}

	class SubmitLoginListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			DataAccess da = new DataAccessFacade();
			HashMap<String,Book> books = da.readBooksMap();
			String isbn = isnb.getText();

			if (books.containsKey(isbn)) {
				HashMap<String, LibraryMember> members = da.readMemberMap();
				for (LibraryMember member : members.values()) {
					defineScrollPane(member.getAllCheckouts(),isbn);
				}

			} else {
				StatusPanel.STATUS_INSTANCE.setStatus("ISBN not found");
			}

		}
	}

	public static void main(String[] args) {
		JFrame a = new JFrame();
		a.setSize(640, 360);
		JPanel mainPanel = new SearchCheckoutRecordByBook().getMainPanel();
		a.add(mainPanel);
		a.setVisible(true);
	}

}

