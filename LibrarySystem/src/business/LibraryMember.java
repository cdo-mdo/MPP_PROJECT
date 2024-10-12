package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<CheckoutRecordEntry> checkouts = new ArrayList<CheckoutRecordEntry>();

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname, lname, tel, add);
		this.memberId = memberId;

	}

	public String getMemberId() {
		return memberId;
	}

	
	public void addCheckout(BookCopy bookCopy) {
		checkouts.add(new CheckoutRecordEntry(bookCopy));
		
	}
	
	public void displayRecord() {
		for (CheckoutRecordEntry checkout : checkouts) {
			String m = memberId + checkout;
			System.out.println(m);
		}
	}
	
	public String[][] getAllCheckouts() {
		String[][] data = new String[checkouts.size()][7];
		for (int i=0;i<checkouts.size();i++) {
			data[i][0] = this.getFirstName();
			data[i][1] = memberId;
			data[i][2] = checkouts.get(i).checkoutDate.toString();
			data[i][3] = checkouts.get(i).dueDate.toString();
			data[i][4] = checkouts.get(i).bookCopy.getBook().getTitle();
			data[i][5] = String.valueOf(checkouts.get(i).bookCopy.getCopyNum());
			data[i][6] = checkouts.get(i).bookCopy.getBook().getIsbn();
		}
		return data;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + ", "
				+ getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
