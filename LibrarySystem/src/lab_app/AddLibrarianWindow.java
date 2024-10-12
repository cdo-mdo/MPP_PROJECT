package lab_app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

public class AddLibrarianWindow extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 410778370071592347L;
	JPanel mainPanel, topPanel, middlePanel, lowerPanel, idPanel, fnPanel, lnPanel, stPanel, cityPanel, statePanel,
			zipPanel, phonePanel;
	JTextField idText, fnText, lnText, stText, cityText, stateText, zipText, phoneText;

	public AddLibrarianWindow() {
		setSize(640, 360);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		addLibrarian();
		add(mainPanel);
		setVisible(true);

		//checkStorage();
	}

	private void addLibrarian() {

		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		JLabel label = new JLabel("Add a New Librarian");
		label.setForeground(Color.BLUE.darker().darker());
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		topPanel.add(label);

		middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(8, 1));

		idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel idLabel = new JLabel("User ID");
		idText = new JTextField(11);
		idPanel.add(idLabel);
		idPanel.add(idText);

		fnPanel = new JPanel();
		fnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel fnLabel = new JLabel("First Name");
		fnText = new JTextField(11);
		fnPanel.add(fnLabel);
		fnPanel.add(fnText);

		lnPanel = new JPanel();
		lnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel lnLabel = new JLabel("Last Name");
		lnText = new JTextField(11);
		lnPanel.add(lnLabel);
		lnPanel.add(lnText);

		stPanel = new JPanel();
		stPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel stLabel = new JLabel("Street");
		stText = new JTextField(11);
		stPanel.add(stLabel);
		stPanel.add(stText);

		cityPanel = new JPanel();
		cityPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel cityLbl = new JLabel("City");
		cityText = new JTextField(11);
		cityPanel.add(cityLbl);
		cityPanel.add(cityText);

		statePanel = new JPanel();
		statePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel stateLabel = new JLabel("State");
		stateText = new JTextField(11);
		statePanel.add(stateLabel);
		statePanel.add(stateText);

		zipPanel = new JPanel();
		zipPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel zipLabel = new JLabel("Zip");
		zipText = new JTextField(11);
		zipPanel.add(zipLabel);
		zipPanel.add(zipText);

		phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		JLabel phoneLabel = new JLabel("Telephone");
		phoneText = new JTextField(11);
		phonePanel.add(phoneLabel);
		phonePanel.add(phoneText);

		// fnLabel.setPreferredSize(phoneLabel.getPreferredSize());
		idLabel.setPreferredSize(fnLabel.getPreferredSize());
		lnLabel.setPreferredSize(fnLabel.getPreferredSize());
		stLabel.setPreferredSize(fnLabel.getPreferredSize());
		cityLbl.setPreferredSize(fnLabel.getPreferredSize());
		stateLabel.setPreferredSize(fnLabel.getPreferredSize());
		zipLabel.setPreferredSize(fnLabel.getPreferredSize());

		middlePanel.add(idPanel);
		middlePanel.add(fnPanel);
		middlePanel.add(lnPanel);
		middlePanel.add(stPanel);
		middlePanel.add(cityPanel);
		middlePanel.add(statePanel);
		middlePanel.add(zipPanel);
		middlePanel.add(phonePanel);

		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout(8, 8));
		JPanel upper = new JPanel();
		JPanel lower = new JPanel();
		upper.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitButtonListener());
		// JButton back = new JButton("Return");
		// back.addActionListener(evt -> Control.INSTANCE.backToStart(this));
		upper.add(submit);
		// upper.add(back);
		lower.setLayout(new FlowLayout(FlowLayout.RIGHT));
		lowerPanel.add(upper, BorderLayout.NORTH);
		lowerPanel.add(lower, BorderLayout.CENTER);

		// mainPanel.add(mainPanel);
		mainPanel.setLayout(new BorderLayout(12, 12));
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		// getContentPane().add(mainPanel);

	}

	private void checkStorage() {
		DataAccessFacade userList = new DataAccessFacade();
		HashMap<String, LibraryMember> hm = userList.readMemberMap();
		String key = idText.getText();
		LibraryMember member = hm.get(key);

		if (member != null) {
			System.out.println(member.toString());
		} else {
			System.out.println("User not found for key: " + key);
		}
	}

	class SubmitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {

			try {
				validateFields();

				Address memberAddress = new Address(stText.getText(), cityText.getText(), stateText.getText(),
						zipText.getText());

				LibraryMember newMember = new LibraryMember(idText.getText(), fnText.getText(), lnText.getText(),
						phoneText.getText(), memberAddress);

				DataAccessFacade newDAF = new DataAccessFacade();
				newDAF.saveNewMember(newMember);

				clearFields();
				checkStorage();
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(AddLibrarianWindow.this, e.getMessage());

			}
		}

	}

	private void validateFields() throws IllegalArgumentException {

		if (idText.getText().isEmpty()) {
			throw new IllegalArgumentException("ID cannot be empty.");
		}
		if (!idText.getText().matches("\\d+")) {
			throw new IllegalArgumentException("ID must be a numeric value.");
		}

		if (fnText.getText().isEmpty()) {
			throw new IllegalArgumentException("First name cannot be empty.");
		}
		if (!fnText.getText().matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("First name must contain only letters.");
		}

		if (lnText.getText().isEmpty()) {
			throw new IllegalArgumentException("Last name cannot be empty.");
		}
		if (!lnText.getText().matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("Last name must contain only letters.");
		}

		if (stText.getText().isEmpty()) {
			throw new IllegalArgumentException("Street address cannot be empty.");
		}

		if (cityText.getText().isEmpty()) {
			throw new IllegalArgumentException("City cannot be empty.");
		}

		if (stateText.getText().isEmpty()) {
			throw new IllegalArgumentException("State cannot be empty.");
		}

		if (zipText.getText().isEmpty()) {
			throw new IllegalArgumentException("Zip code cannot be empty.");
		}
		if (!zipText.getText().matches("\\d{5}")) {
			throw new IllegalArgumentException("Zip code must be a 5-digit number.");
		}

		if (phoneText.getText().isEmpty()) {
			throw new IllegalArgumentException("Phone number cannot be empty.");
		}
		if (!phoneText.getText().matches("\\d{10}")) {
			throw new IllegalArgumentException("Phone number must be a 10-digit number.");
		}
	}

	public String getIdText() {
		return idText.getText();
	}

	public String getFnText() {
		return fnText.getText();
	}

	public String getLnText() {
		return lnText.getText();
	}

	public String getStText() {
		return stText.getText();
	}

	public String getCityText() {
		return cityText.getText();
	}

	public String getStateText() {
		return stateText.getText();
	}

	public String getZipText() {
		return zipText.getText();
	}

	public String getPhoneText() {
		return phoneText.getText();
	}

	private void clearFields() {
		idText.setText("");
		fnText.setText("");
		lnText.setText("");
		stText.setText("");
		cityText.setText("");
		stateText.setText("");
		zipText.setText("");
		phoneText.setText("");
	}

//    public static void main(String[] args) {
//        new AddLibrarianWindow();
//    }
}
