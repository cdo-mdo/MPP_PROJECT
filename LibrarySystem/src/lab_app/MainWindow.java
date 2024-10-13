package lab_app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = -7438493168871970597L;

	public static final String WELCOME = "Home";
	public static final String ADD_MEMBER = "Add Library Member";
	public static final String CHECKOUT_BOOK = "Checkout Book";
	public static final String ADD_COPY_OF_BOOK = "Add Copy of Book";
	public static final String SEARCH_CHECKOUT = "Search Checkout Record by Member";
	public static final String ADD_BOOK = "Add New Book";
	public static final String SEARCH_CHECKOUT_BOOK = "Search Checkout Record by Book";
	public static final MainWindow INSTANCE = new MainWindow();
	
	private JList<ListItem> linkList;
	private JPanel cards;
	private JPanel buttonBar;
	private JLabel label;
	private StatusPanel statusPanel;
	
	// list items which will be added to the ListModel for linkList
	ListItem item = new ListItem(WELCOME, true);
	ListItem item1 = new ListItem(ADD_MEMBER, true);
	ListItem item2 = new ListItem(CHECKOUT_BOOK, true);
	ListItem item3 = new ListItem(ADD_COPY_OF_BOOK, true);
	ListItem item4 = new ListItem(SEARCH_CHECKOUT, true);
	ListItem item5 = new ListItem(ADD_BOOK, true);
	ListItem item6 = new ListItem(SEARCH_CHECKOUT_BOOK, true);
	
	ListItem[] group1 = { item, item2, item4, item5, item6 };
	ListItem[] group2 = { item, item1, item3 };
	
	public MainWindow() {
		setSize(960, 540);

		createLinkLabels();
		createMainPanels();
		createButtonBar();
		statusPanel = StatusPanel.STATUS_INSTANCE;
		linkList.addListSelectionListener(event -> {
			String value = linkList.getSelectedValue().getItemName();
			boolean allowed = linkList.getSelectedValue().highlight();
			setMessage("");
			CardLayout cl = (CardLayout) (cards.getLayout());
			if (!allowed) {
				linkList.setSelectedIndex(0);
				value = item.getItemName();
			}
			cl.show(cards, value);
		});

		// set up split panes
		JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton logout = new JButton("Log Out");
		logout.addActionListener(new LogoutProgram());
		btnPanel.add(logout);
		bottomPanel.add(statusPanel);
		bottomPanel.add(btnPanel);
		innerPane.setDividerLocation(250);

		JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, bottomPanel);
		outerPane.setDividerLocation(432);
		add(outerPane, BorderLayout.CENTER);
	}

	public ListItem[] getGroup1Items() {
		return group1;
	}

	public ListItem[] getGroup2Items() {
		return group2;
	}

	public JList<ListItem> getLinkList() {
		return linkList;
	}

	class LogoutProgram implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			LoginWindow.INSTANCE.setVisible(true);
		}
	}

	public void createButtonBar() {
		buttonBar = new JPanel();
		label = new JLabel();
		buttonBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonBar.add(label);
	}

	public void setMessage(String s) {
		label.setText(s);
	}

	public void addLeftButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			updateList(group1);
			repaint();
		});
	}

	public void addRightButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			updateList(group2);
			repaint();
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateList(ListItem[] items) {
		DefaultListModel<ListItem> model = (DefaultListModel) linkList.getModel();
		int size = model.getSize();
		if (items != null) {
			java.util.List<Integer> indices = new ArrayList<>();
			for (ListItem item : items) {
				int index = model.indexOf(item);
				indices.add(index);
				ListItem next = (ListItem) model.get(index);
				next.setHighlight(true);

			}
			for (int i = 0; i < size; ++i) {
				if (!indices.contains(i)) {
					ListItem next = (ListItem) model.get(i);
					next.setHighlight(false);
				}
			}
		} else {
			for (int i = 0; i < size; ++i) {
				ListItem next = (ListItem) model.get(i);
				next.setHighlight(true);
			}
		}
	}

	@SuppressWarnings("serial")
	public void createLinkLabels() {
		DefaultListModel<ListItem> model = new DefaultListModel<>();
		model.addElement(item);
		model.addElement(item1);
		model.addElement(item2);
		model.addElement(item3);
		model.addElement(item4);
		model.addElement(item5);
		model.addElement(item6);

		linkList = new JList<ListItem>(model);
		linkList.setCellRenderer(new DefaultListCellRenderer() {

			@SuppressWarnings("rawtypes")
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof ListItem) {
					ListItem nextItem = (ListItem) value;
					setText(nextItem.getItemName());
					if (nextItem.highlight()) {
						setForeground(Util.LINK_AVAILABLE);
					} else {
						setForeground(Util.LINK_NOT_AVAILABLE);
					}
					if (isSelected) {
						setForeground(Color.BLACK);
						setBackground(new Color(236, 243, 245));
					}
				} else {
					setText("illegal item");
				}
				return c;
			}

		});
	}

	public void createMainPanels() {
		cards = new JPanel(new CardLayout());
		cards.add(new WelcomePanel().getMainPanel(), item.getItemName());
		JPanel addLib = new AddLibrarianPanel();
		addLib.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
		cards.add(addLib, item1.getItemName());
		cards.add(new CheckoutBookPanel().getMainPanel(), item2.getItemName());
		cards.add(new AddBookCopyPanel(), item3.getItemName());
		cards.add(new SearchCheckoutRecordByMember().getMainPanel(), item4.getItemName());
		cards.add(new AddBookPanel().getMainPanel(), item5.getItemName());
		cards.add(new SearchCheckoutRecordByBook().getMainPanel(), item6.getItemName());

	}
}
