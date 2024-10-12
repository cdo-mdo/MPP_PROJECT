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
import java.util.ArrayList;

/**
 * This sample code is like splitpane example, but also illustrates:
 * 
 * 4. Using a Cell Renderer to control the colors of the list items 
 * 
 * @author corazza
 *
 */
public class SampleFrame extends JFrame {
	
	String[] links;
	JList<ListItem> linkList;
	JPanel cards;
	JPanel buttonBar;
	JLabel label;
	StatusPanel statusPanel;
	public static final String ADD_MEMBER = "Add Library Member";
	public static final String CHECKOUT_BOOK = "Checkout Book";
	public static final String ADD_COPY_OF_BOOK = "Add Copy of Book";
//	Login login = new Login();
//	Titles titles = new Titles();
//	AddBook add = new AddBook();
	
	// list items which will be added to the ListModel for linkList
	ListItem item1 = new ListItem(ADD_MEMBER, true);
	ListItem item2 = new ListItem(CHECKOUT_BOOK, true);
	ListItem item3 = new ListItem(ADD_COPY_OF_BOOK, true);
	
	ListItem[] group1 = {item2  };
	ListItem[] group2 = { item1, item3 };

	public ListItem[] getGroup1Items() {
		return group1;
	}

	public ListItem[] getGroup2Items() {
		return group2;
	}

	public JList<ListItem> getLinkList() {
		return linkList;
	}

	public SampleFrame() {
		setSize(960, 540);

		createLinkLabels();
		createMainPanels();
		createButtonBar();
		statusPanel = new StatusPanel();
		linkList.addListSelectionListener(event -> {
			String value = linkList.getSelectedValue().getItemName();
			boolean allowed = linkList.getSelectedValue().highlight();
			setMessage("");
			//System.out.println(value + " " + allowed);

			//System.out.println("selected = " + value);
			CardLayout cl = (CardLayout) (cards.getLayout());

			if (!allowed) {
				value = item1.getItemName();
				linkList.setSelectedIndex(0);
			}
			cl.show(cards, value);
		});

		// set up split panes

		JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				linkList, cards);
		statusPanel = StatusPanel.STATUS_INSTANCE;
		innerPane.setDividerLocation(180);
		JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				innerPane, statusPanel);
		outerPane.setDividerLocation(432);
		add(outerPane, BorderLayout.CENTER);
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
		model.addElement(item1);
		model.addElement(item2);
		model.addElement(item3);
	
		linkList = new JList<ListItem>(model);
	    linkList.setCellRenderer(new DefaultListCellRenderer() {

			@SuppressWarnings("rawtypes")
			@Override
			public Component getListCellRendererComponent(JList list, 
					Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, 
						value, index, isSelected, cellHasFocus);
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
						setBackground(new Color(236,243,245));
					}
				} else {
					setText("illegal item");
				}
				return c;
			}

		});
	}

	public void createMainPanels() {
		// item1 panel

		cards = new JPanel(new CardLayout());
		cards.add(new AddLibrarianWindow(), item1.getItemName());
		cards.add(new CheckoutBookPanel().getMainPanel(), item2.getItemName());
		cards.add(new AddBookCopyPanel(), item3.getItemName());

	}

	private static final long serialVersionUID = -7438493168871970597L;
}
