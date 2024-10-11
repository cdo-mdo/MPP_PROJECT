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
import librarysystem.LibrarySystem;
import librarysystem.StatusPanel;



public class checkoutBookPanel{
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	
	private JTextField isnb;
	private JTextField memberID;
	
	private JButton checkAvailableButton;
	
	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	
	public checkoutBookPanel() {
		mainPanel = new JPanel();
		
		defineTopPanel();
		defineMiddlePanel();
		
		String[] columnNames = {"Member ID","Name", "Checkout Date", "Due Date", "Title", "Copy Number"};
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
	
	private void defineMiddlePanel(){
		middlePanel=new JPanel();
		middlePanel.setLayout(new BorderLayout());
		
		
		//add item components
		JPanel addItemPanel = new JPanel();
		addItemPanel.setLayout(new BorderLayout());
		
		JPanel N1 = new JPanel();
		N1.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel name1 = new JLabel("Book ISBN");
		isnb = new JTextField(10);
		N1.add(name1);
		N1.add(isnb);
		addItemPanel.add(N1,BorderLayout.NORTH);
		
		JPanel N2 = new JPanel();
		N2.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel name2 = new JLabel("Member ID");
		memberID = new JTextField(10);

		N2.add(name2);
		N2.add(memberID);
		addItemPanel.add(N2,BorderLayout.CENTER);
		
		JPanel addItemPanel1 = new JPanel();
		addItemPanel1.setLayout(new BorderLayout());
		checkAvailableButton = new JButton("Check Out");
		addItemPanel1.add(checkAvailableButton,BorderLayout.SOUTH);

		
		checkAvailableButton.addActionListener(new SubmitLoginListener());	
		
		middlePanel.add(addItemPanel, BorderLayout.NORTH);
		middlePanel.add(addItemPanel1, BorderLayout.CENTER);
			
	}
	
	public void defineScrollPane(String[][] data) {
		for (Object[] row : data) {
            tableModel.addRow(row);
        }
	}
	
	class SubmitLoginListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			DataAccess da = new DataAccessFacade();
			HashMap<String,LibraryMember> members = da.readMemberMap();
			String memberIDText = memberID.getText();
			String isnbText = isnb.getText();
			
			if (members.containsKey(memberIDText)){
				HashMap<String,Book> books = da.readBooksMap();
				if (books.containsKey(isnbText)) {
					Book book = books.get(isnbText);
					BookCopy bookcopy = book.getNextAvailableCopy();
					if (bookcopy == null){
						StatusPanel.STATUS_INSTANCE.setStatus("The book is not available!");
					} else {
						bookcopy.changeAvailability();
						LibraryMember member = members.get(memberIDText);
						member.addCheckout(bookcopy);
						//member.displayRecord();
						
						DataAccessFacade.saveBooks(books);
						DataAccessFacade.saveMembers(members);
						
						defineScrollPane(member.getAllCheckouts());
						StatusPanel.STATUS_INSTANCE.setStatus("Checkout Book " + isnbText);
					}
				} else {
					StatusPanel.STATUS_INSTANCE.setStatus("ISBN not found!");
				}
			} else {
				StatusPanel.STATUS_INSTANCE.setStatus("Member ID not found!");
			}
		}
	}

//	public static void main(String[] args) {
//		JFrame a = new JFrame();
//		a.setSize(640, 360);
//		JPanel mainPanel = new checkoutBookPanel().getMainPanel();
//		a.add(mainPanel);
//		a.setVisible(true);
//	}

}
