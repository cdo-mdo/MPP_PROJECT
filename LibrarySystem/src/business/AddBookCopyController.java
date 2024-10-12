package business;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.DataAccessFacade;
import lab_app.AddBookCopyPanel;

public class AddBookCopyController implements ActionListener {
	AddBookCopyPanel addBookCopyPanel;
	
	public AddBookCopyController(AddBookCopyPanel addBookCopyPanel) {
		this.addBookCopyPanel = addBookCopyPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String isbn = addBookCopyPanel.getISBNText().trim();
		if ("".equals(isbn)) {
			addBookCopyPanel.setStatus("ISBN is empty");
			return;
		}
		
		String numStr = addBookCopyPanel.getCopyNumber().trim();
		int num;
		try {
			num = Integer.parseInt(numStr);
		}
		catch (NumberFormatException exception) {
			addBookCopyPanel.setStatus("Wrong number of copies: " + exception.getMessage());
			return;
		}
		
		DataAccessFacade dataAccessFacade = new DataAccessFacade();
		
		HashMap<String, Book> maps = dataAccessFacade.readBooksMap();
		for (HashMap.Entry<String, Book> entry: maps.entrySet()) {
			if (isbn.equals(entry.getKey())) {
				Book book = entry.getValue();
				for (int i = 0; i < num; i++) {
					book.addCopy();
				}
				addBookCopyPanel.setStatus("Add " + num + " of copies to book ISBN " + isbn);
				List<Book> list = new ArrayList<>();
				maps.values().forEach(value -> list.add(value));
				DataAccessFacade.loadBookMap(list);
				return;
			}
		}
		addBookCopyPanel.setStatus("Not found book isbn " + isbn);
	}

}
