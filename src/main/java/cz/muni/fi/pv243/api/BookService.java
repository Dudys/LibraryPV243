package cz.muni.fi.pv243.api;

public interface BookService {

	void addBook(BookDTO book);
	BookDTO getBook(long id);
	void reserveBook(ReservationDTO reservation);
	void unreserveBook(ReservationDTO reservation);
	void removeBook(long id);
	void updateBook(BookDTO book);
	void borrowBook(BorrowingDTO borrowing);
	void returnBook(BorrowingDTO borrowing);

}
