package librarysystem;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3153946918224260301L;
	public final static StatusPanel STATUS_INSTANCE = new StatusPanel();
	private JLabel outputDisplay = new JLabel("Welcome to the Book Club!");
	
	public StatusPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		add(outputDisplay);
	}
	
	public void setStatus(String s) {
		outputDisplay.setText(s);
	}
}
