public class Book {
	
	String title;
	String author;
	String borrower;
	String date;
	boolean borrowed;
	
	//constructor
	public Book(String t, String a, String b, String d) {
		title = t;
		author = a;
		borrower = b;
		date = d;
		borrowed = false;
	}
	
	//getters and setters
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isBorrowed() {
		return borrowed;
	}
	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}
	public boolean firstLast6(int[] nums) {
		  if (nums[0] == 6 || nums[nums.length] == 6) {
		    return true;
		  }
		  return false;
		}
	
}
