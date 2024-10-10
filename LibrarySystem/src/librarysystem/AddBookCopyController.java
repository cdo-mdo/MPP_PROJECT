package librarysystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Book;
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
		String numStr = addBookCopyPanel.getCopyNumber().trim();
		int num = Integer.parseInt(numStr);
		
		DataAccessFacade dataAccessFacade = new DataAccessFacade();
		
		HashMap<String, Book> maps = dataAccessFacade.readBooksMap();
		for (HashMap.Entry<String, Book> entry: maps.entrySet()) {
			if (isbn.equals(entry.getKey())) {
				System.out.println("found isbn: " + isbn);
				Book book = entry.getValue();
				for (int i = 0; i < num; i++) {
					book.addCopy();
				}
				System.out.println("add " + num + " copies of isbn: " + isbn);
				List<Book> list = new ArrayList<>();
				maps.values().forEach(value -> list.add(value));
				System.out.println("persistent");
				DataAccessFacade.loadBookMap(list);
				return;
			}
		}
		System.out.println("not found isbn " + isbn);
	}

}
