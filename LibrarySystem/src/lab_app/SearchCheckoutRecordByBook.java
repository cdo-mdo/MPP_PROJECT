package lab_app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

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
	private String[] columnNames = {"Name","Member ID", "Checkout Date", "Due Date", "Title", "Copy Number","ISBN"};
	private JTextField isnb;

	private JButton searchButton;
	private JButton sortButton;
	private DefaultTableModel tableModel;
	private JTable table;

	public SearchCheckoutRecordByBook() {
		defineTopPanel();
		defineMiddlePanel();
		
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
		searchButton = new JButton("Search");
		addItemPanel1.add(searchButton, BorderLayout.CENTER);
		
		
		sortButton = new JButton("Sort by DueDay");
		addItemPanel1.add(sortButton, BorderLayout.SOUTH);

		searchButton.addActionListener(new SubmitLoginListener());
		sortButton.addActionListener(new SubmitSortListener());

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

			int n = tableModel.getRowCount();
			for (int i=n-1;i>-1;i--) {
				tableModel.removeRow(i);
			}
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
	
	class SubmitSortListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			sortTableModelByColumn(tableModel,3);

		}
	}
    private void sortTableModelByColumn(DefaultTableModel model, int columnIndex) {
        // Get all rows from the model into a List
        List<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            rows.add(new Object[]{
                model.getValueAt(i, 0),  // Name column
                model.getValueAt(i, 1),
                model.getValueAt(i, 2),
                model.getValueAt(i, 3),
                model.getValueAt(i, 4),
                model.getValueAt(i, 5),
                model.getValueAt(i, 6)// Age column
            });
        }

        // Sort the list based on the specified column index
        Collections.sort(rows, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] row1, Object[] row2) {
                // For numerical sorting of the "Age" column, cast to Integer
                String due1 = row1[columnIndex].toString();
                String due2 = row2[columnIndex].toString();
                return due1.compareTo(due2);
            }
        });

        // Clear the model and add the sorted rows back
        model.setRowCount(0); // Remove all existing rows
        for (Object[] row : rows) {
            model.addRow(row);
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

