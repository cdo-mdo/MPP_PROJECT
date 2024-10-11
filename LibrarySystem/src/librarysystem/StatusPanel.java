package librarysystem;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3153946918224260301L;
	JLabel outputDisplay;
	
	public StatusPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		outputDisplay = new JLabel("Welcome to the Book Club!");
		add(outputDisplay);
	}
	
	public void setStatus(String s) {
		outputDisplay.setText(s);
	}
}
