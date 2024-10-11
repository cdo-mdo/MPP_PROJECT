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

import librarysystem.AddBookCopyController;
import librarysystem.StatusPanel;

public class AddBookCopyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1561596036523125788L;
	JPanel titlePanel;
	JLabel title;
	
	JPanel addPanel;
	JLabel isbnLabel;
	JTextField isbnText;
	JLabel copiesLabel;
	JTextField copiesText;
	JButton addButton;
	
	StatusPanel statusPanel;
	
	public String getISBNText() {
		return isbnText.getText();
	}
	
	public String getCopyNumber() {
		return copiesText.getText();
	}
	
	public void setStatus(String message) {
		statusPanel.setStatus(message);
	}
	
	public AddBookCopyPanel(StatusPanel statusPanel) {
		
		setSize(780, 540);
		this.statusPanel = statusPanel;
		
		this.setLayout(new GridLayout(15,1));
		
		defineTitlePanel();
		defineSearchPanel();
		
		add(titlePanel);
		add(addPanel);
		
	}
	
	void defineTitlePanel() {
		titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		
		title = new JLabel("Add Copy of Book");
		titlePanel.add(title);
	}
	
	void defineSearchPanel() {
		addPanel = new JPanel();
		addPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		
		isbnLabel = new JLabel("ISBN");
		isbnText = new JTextField(12);
		copiesLabel = new JLabel("number of copies");
		copiesText = new JTextField(5);
		addButton = new JButton("Add Copies");
		addButton.addActionListener(new AddBookCopyController(this));
		
		addPanel.add(isbnLabel);
		addPanel.add(isbnText);
		addPanel.add(copiesLabel);
		addPanel.add(copiesText);
		addPanel.add(addButton);
	}

}
