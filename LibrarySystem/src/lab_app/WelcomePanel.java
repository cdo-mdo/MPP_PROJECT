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


public class WelcomePanel {
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JTextField isnb;
	private JTextField memberID;

	private JButton checkAvailableButton;
	
	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	
	public WelcomePanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JLabel welcome = new JLabel("WELCOME TO LIBRARY MANAGEMENT SYSTEM");
		mainPanel.add(welcome);
		
	}
	public JPanel getMainPanel() {
		return mainPanel;
	}

	public static void main(String[] args) {
		JFrame a = new JFrame();
		a.setSize(640, 360);
		JPanel mainPanel = new WelcomePanel().getMainPanel();
		a.add(mainPanel);
		a.setVisible(true);
	}

}
