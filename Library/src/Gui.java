import java.util.*;
import javax.swing.*;
import BreezySwing.*;
import java.text.SimpleDateFormat;
public class Gui extends GBFrame {
	//directions
	JLabel label = addLabel ("Enter Book Details", 1, 1, 1, 1);
	
	//labels + input fields
	JLabel titleLabel = addLabel ("Title:", 2, 1, 1, 1);
	JTextField titleInput = addTextField ("", 2, 2, 1, 1);
	JLabel authorLabel = addLabel ("Author:", 3, 1, 1, 1);
	JTextField authorInput = addTextField ("", 3, 2, 1, 1);
	JLabel borrowerLabel = addLabel ("Borrower Name:", 4, 1, 1, 1);
	JTextField borrowerInput = addTextField ("", 4, 2, 1, 1);
	JLabel dateLabel = addLabel ("Date (MM/DD/YYYY):", 5, 1, 1, 1);
	JTextField dateInput = addTextField ("", 5, 2, 1, 1);
	
	//requirement captions
	JLabel titleDescription = addLabel ("Required for Checkout, Add, Search, Return", 2, 3, 1, 1);
	JLabel authorDescription = addLabel ("Required for Add", 3, 3, 1, 1);
	JLabel borrowerDescription = addLabel ("Required for Checkout", 4, 3, 1, 1);
	JLabel dateDescription = addLabel ("Required for Checkout", 5, 3, 1, 1);
	
	//buttons row 1
	JButton add = addButton("Add", 6, 1, 1, 1);
	JButton search = addButton("Search", 6, 2, 1, 1);
	JButton checkOut = addButton("Check Out", 6, 3, 1, 1);
	JButton Return = addButton("Return", 6, 4, 1, 1);
	
	//buttons row 2
	JButton borrowed = addButton("View Borrowed Books", 7, 1, 1, 1);
	JButton overdue = addButton("View Overdue Books", 7, 2, 1, 1);
	JButton viewLibrary = addButton("View Library", 7, 3, 1, 1);
	JButton totalBooks = addButton("View Number Of Available Books", 7, 4, 1, 1);
	
	//initializing variables
	ArrayList<Book> books = new ArrayList<Book>();
	Book book;
	int counter = 0;
	String messageBoxOutput = "";
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy");
	
	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == add) {
			if ((titleInput.getText()).isBlank() || (authorInput.getText()).isBlank()) {
				messageBox("Please fill all of the required fields");
			}
			else {
				book = new Book(titleInput.getText().trim(), authorInput.getText().trim(), "", "");
				books.add(book);
				counter++;
				messageBox(titleInput.getText() + " has successfully been added to the library", 550, 125);
				titleInput.setText("");
				authorInput.setText("");
				borrowerInput.setText("");
				dateInput.setText("");
			}
		}
		//////////////////////////////////////////////////////////
		if (buttonObj == search) {
			messageBoxOutput = "";
			if ((titleInput.getText()).isBlank()) {
				messageBox("Please fill all of the required fields");
			}
			else {
				for (int i = 0; i < counter; i++) {
					if (titleInput.getText().equals(books.get(i).getTitle())) {
						if (books.get(i).isBorrowed()) {
							messageBoxOutput = "Title: " + books.get(i).getTitle() + '\n' + "Author: " + books.get(i).getAuthor() + '\n' + "Status: Borrowed" + '\n' + "Borrower: " + books.get(i).getBorrower();
						}
						else {
							messageBoxOutput = "Title: " + books.get(i).getTitle() + '\n' + "Author: " + books.get(i).getAuthor() + '\n' + "Status: Available";
						}
					}
				}
				if (messageBoxOutput == "") {
					messageBox("This book does not exist");
				}
				else {
					messageBox(messageBoxOutput);
				}
				titleInput.setText("");
				authorInput.setText("");
				borrowerInput.setText("");
				dateInput.setText("");
			}
		}
		//////////////////////////////////////////////////////////
		if (buttonObj == checkOut) {
			try {
				messageBoxOutput = "";
				Calendar calendar = Calendar.getInstance();
				String stringCurrentDate = Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(calendar.get(Calendar.YEAR));
				String month = dateInput.getText().substring(0,2);
				String day = dateInput.getText().substring(3,5);
				String year = dateInput.getText().substring(6,10);
				
				if ((titleInput.getText()).isBlank() || (borrowerInput.getText()).isBlank() || (dateInput.getText()).isBlank()) {
					messageBox("Please fill all of the required fields");
				}
				else if (sdf.parse(stringCurrentDate).getTime() - sdf.parse(dateInput.getText()).getTime() < 0) {
					messageBox("Error: That date is in the future");
				}
				
				//error checking if date exists
				else if ((month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) && Integer.parseInt(day) > 30) {
					messageBox("That date does not exist"); //checks months with 30 days
				}
				else if ((month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) && Integer.parseInt(day) > 31) {
					messageBox("That date does not exist"); //checks months with 31 days
				}
				else if (month.equals("02") && Integer.parseInt(day) > 28 && Integer.parseInt(year) % 4 != 0) {
					messageBox("That date does not exist"); //checks February on non-leap years
				}
				else if (month.equals("02") && Integer.parseInt(day) > 29 && Integer.parseInt(year) % 4 == 0) {
					messageBox("That date does not exist"); //checks February on leap years
				}
				
				
				else {
					for (int i = 0; i < counter; i++) {
						if (titleInput.getText().equals(books.get(i).getTitle())) {
							if (books.get(i).isBorrowed()) {
								messageBoxOutput = "This book is already checked out";
							}
							else {
								books.get(i).setBorrowed(true);
								books.get(i).setBorrower(borrowerInput.getText());
								books.get(i).setDate(dateInput.getText());
								messageBoxOutput = titleInput.getText() + " has successfully been checked out";
							}
						}
					}
				if (messageBoxOutput == "") {
					messageBox("This book does not exist");
				}
				else {
					messageBox(messageBoxOutput, 500, 200);
				}
				titleInput.setText("");
				authorInput.setText("");
				borrowerInput.setText("");
				dateInput.setText("");
				}
			}
			catch (Exception E) {
				messageBox ("Invalid input. Make sure the date is in the correct format. Ex. 02/09/2021", 600, 125);
			}
		}
		//////////////////////////////////////////////////////////
		if (buttonObj == Return) {
			messageBoxOutput = "";
			if ((titleInput.getText()).isBlank()) {
				messageBox("Please fill all of the required fields");
			}
			else {
				for (int i = 0; i < counter; i++) {
					if (titleInput.getText().equals(books.get(i).getTitle())) {
						if (books.get(i).isBorrowed()) {
							books.get(i).setBorrowed(false);
							books.get(i).setBorrower("");
							books.get(i).setDate("");
							messageBoxOutput = titleInput.getText() + " has successfully been returned";
						}
						else {
							messageBoxOutput = "This book has not been checked out";
						}
					}
				}
				if (messageBoxOutput == "") {
					messageBox("This book does not exist");
				}
				else {
					messageBox(messageBoxOutput);
				}
				titleInput.setText("");
				authorInput.setText("");
				borrowerInput.setText("");
				dateInput.setText("");
			}
		}
		//////////////////////////////////////////////////////////
		if (buttonObj == borrowed) {
			messageBoxOutput = "";
			for (int i = 0; i < counter; i++) {
				if (books.get(i).isBorrowed()) {
					messageBoxOutput += "Title: " + books.get(i).getTitle() + "    Borrower: " + books.get(i).getBorrower() + "    Date Borrowed: " + books.get(i).getDate() + '\n';
				}
			}
			if (messageBoxOutput == "") {
				messageBoxOutput = "There are no books that have been checked out";
			}
			messageBox(messageBoxOutput, 500, 200);
		}
		//////////////////////////////////////////////////////////
		if (buttonObj == overdue) {
			messageBoxOutput = "";
			for (int i = 0; i < counter; i++) {
				try {
					if (books.get(i).isBorrowed()) {
						Calendar calendar = Calendar.getInstance();
						String stringCurrentDate = Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(calendar.get(Calendar.YEAR));
						
						//System.out.println(sdf.parse(stringCurrentDate).getTime() - sdf.parse(books.get(i).getDate()).getTime());
						
						if (sdf.parse(stringCurrentDate).getTime() - sdf.parse(books.get(i).getDate()).getTime() > 1209600000) {
							messageBoxOutput += "Title: " + books.get(i).getTitle() + "     Borrower: " + books.get(i).getBorrower() + '\n';
						}
					}
				} catch (Exception e) {
					messageBoxOutput = "Error";
				}
			}
			if (messageBoxOutput == "") {
				messageBox("There are no overdue books", 500, 200);
			}
			else {
				messageBox(messageBoxOutput, 500, 200);
			}
		}
		//////////////////////////////////////////////////////////
		if (buttonObj == viewLibrary) {
			messageBoxOutput = "";
			if (counter == 0) {
				messageBoxOutput = "There are no books in the library";
			}
			else {
				for (int i = 0; i < counter; i++) {
					messageBoxOutput += "Title: " + books.get(i).getTitle() + "    Author: " + books.get(i).getAuthor() + '\n';
				}
			}
			messageBox(messageBoxOutput, 500, 200);
		}
		//////////////////////////////////////////////////////////
		if (buttonObj == totalBooks) {
			int availableCounter = 0;
			for (int i = 0; i < counter; i++) {
				if (books.get(i).isBorrowed()) {
					availableCounter += 0;
				}
				else {
					availableCounter ++;
				}
			}
			messageBox("Number of available books: " + availableCounter, 500, 200);
		}
		
	}
	
	//outputs GUI
	public static void main(String[] args) {
		JFrame frm = new Gui();
		frm.setTitle("Parser Program");
		frm.setSize(900, 300);
		frm.setVisible(true);
	}
}
