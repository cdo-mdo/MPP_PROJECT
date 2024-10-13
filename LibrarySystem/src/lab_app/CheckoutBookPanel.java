package lab_app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

public class CheckoutBookPanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8475148587360736553L;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JTextField isnb;
	private JTextField memberID;

	private JButton checkAvailableButton;

	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;

	public CheckoutBookPanel() {
		mainPanel = new JPanel();
		defineTopPanel();
		defineMiddlePanel();

		String[] columnNames = { "Name", "Member ID", "Checkout Date", "Due Date", "Title", "Copy Number", "ISBN" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(600, 100));
		scrollPane = new JScrollPane(table);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		JLabel AddBookLabel = new JLabel("Check out Book");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		AddBookLabel.setForeground(Color.BLUE.darker().darker());
		AddBookLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		topPanel.add(AddBookLabel);
	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());

		// add item components
		JPanel addItemPanel = new JPanel();
		addItemPanel.setLayout(new BorderLayout());

		JPanel midSection = new JPanel();
		midSection.setLayout(new BorderLayout());

		JPanel N1 = new JPanel();
		N1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel name1 = new JLabel("Book ISBN");
		isnb = new JTextField(10);
		N1.add(name1);
		N1.add(isnb);
		midSection.add(N1, BorderLayout.NORTH);

		JPanel N2 = new JPanel();
		N2.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel name2 = new JLabel("Member ID");
		memberID = new JTextField(10);
		N2.add(name2);
		N2.add(memberID);
		midSection.add(N2, BorderLayout.CENTER);

		JPanel addItemPanel1 = new JPanel();
		addItemPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));

		checkAvailableButton = new JButton("Check Out");
		checkAvailableButton.addActionListener(new SubmitLoginListener());
		addItemPanel1.add(checkAvailableButton);

		addItemPanel.add(midSection, BorderLayout.NORTH);
		addItemPanel.add(addItemPanel1, BorderLayout.CENTER);
		middlePanel.add(addItemPanel);

	}

	public void defineScrollPane(String[][] data) {
		for (Object[] row : data) {
			tableModel.addRow(row);
		}
	}

	class SubmitLoginListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			DataAccess da = new DataAccessFacade();
			HashMap<String, LibraryMember> members = da.readMemberMap();
			String memberIDText = memberID.getText();
			String isnbText = isnb.getText();
			if (members.containsKey(memberIDText)) {
				HashMap<String, Book> books = da.readBooksMap();
				if (books.containsKey(isnbText)) {
					Book book = books.get(isnbText);
					BookCopy bookcopy = book.getNextAvailableCopy();
					if (bookcopy == null) {
						JOptionPane.showMessageDialog(CheckoutBookPanel.this, "The book is no longer available!");
					} else {
						int n = tableModel.getRowCount();
						System.out.println(n);
						for (int i = n - 1; i > -1; i--) {
							tableModel.removeRow(i);
						}
						bookcopy.changeAvailability();
						LibraryMember member = members.get(memberIDText);
						member.addCheckout(bookcopy);
						DataAccessFacade.saveBooks(books);
						DataAccessFacade.saveMembers(members);
						defineScrollPane(member.getAllCheckouts());
						StatusPanel.STATUS_INSTANCE.setStatus("Checkout Book " + isnbText);
					}
				} else {
					JOptionPane.showMessageDialog(CheckoutBookPanel.this, "ISBN not found!");
				}
			} else {
				JOptionPane.showMessageDialog(CheckoutBookPanel.this, "Member ID not found!");
			}
		}
	}

//	public static void main(String[] args) {
//		JFrame a = new JFrame();
//		a.setSize(640, 360);
//		JPanel mainPanel = new CheckoutBookPanel().getMainPanel();
//		a.add(mainPanel);
//		a.setVisible(true);
//	}

}
