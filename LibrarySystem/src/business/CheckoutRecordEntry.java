package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {
	private static final long serialVersionUID = -63976228084869822L;
	LocalDate checkoutDate;
	LocalDate dueDate;
	BookCopy bookCopy;
	CheckoutRecordEntry(BookCopy bookCopy){
		this.checkoutDate = LocalDate.now();
		this.bookCopy = bookCopy;
		this.dueDate = checkoutDate.plusDays(this.bookCopy.getMaxCheckoutLength());
	}
	@Override
	public String toString() {
		return ", checkoutDate: "  + checkoutDate + ", dueDate: " + dueDate + ", title "  + bookCopy.getBook().getTitle()+ ", copy number "  + bookCopy.getCopyNum();
	}
}
