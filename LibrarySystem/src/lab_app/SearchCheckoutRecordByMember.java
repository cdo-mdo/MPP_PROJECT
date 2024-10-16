package lab_app;

import java.awt.BorderLayout;
import java.awt.Color;
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

import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class SearchCheckoutRecordByMember {
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JScrollPane scrollPane = new JScrollPane();

	private JTextField memberID;

	private JButton checkAvailableButton;

	public SearchCheckoutRecordByMember() {
		defineTopPanel();
		defineMiddlePanel();
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);

	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Search Checkout Record");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
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

		JPanel N1 = new JPanel();
		N1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel name1 = new JLabel("Member ID");
		memberID = new JTextField(10);
		N1.add(name1);
		N1.add(memberID);
		addItemPanel.add(N1, BorderLayout.NORTH);

		JPanel addItemPanel1 = new JPanel();
		addItemPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		checkAvailableButton = new JButton("Search");
		addItemPanel1.add(checkAvailableButton);

		checkAvailableButton.addActionListener(new SubmitLoginListener());

		middlePanel.add(addItemPanel, BorderLayout.NORTH);
		middlePanel.add(addItemPanel1, BorderLayout.CENTER);

	}

	public void defineScrollPane(String[][] data) {
		String[] columnNames = { "Member ID", "Name", "Checkout Date", "Due Date", "Title", "Copy Number" };
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
		scrollPane = new JScrollPane(table);
	}

	class SubmitLoginListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			DataAccess da = new DataAccessFacade();
			HashMap<String, LibraryMember> members = da.readMemberMap();
			String memberIDText = memberID.getText();

			if (members.containsKey(memberIDText)) {
				LibraryMember member = members.get(memberIDText);

				member.displayRecord();

			} else {
				StatusPanel.STATUS_INSTANCE.setStatus("Member ID not found!");
			}

		}
	}

//	public static void main(String[] args) {
//		JFrame a = new JFrame();
//		a.setSize(640, 360);
//		JPanel mainPanel = new SearchCheckoutRecordByMember().getMainPanel();
//		a.add(mainPanel);
//		a.setVisible(true);
//	}

}
