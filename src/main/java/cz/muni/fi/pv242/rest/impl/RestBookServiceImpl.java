package cz.muni.fi.pv242.rest.impl;

import cz.muni.fi.pv242.persistence.entity.Borrowing;
import cz.muni.fi.pv242.rest.RestBookService;
import cz.muni.fi.pv242.rest.model.BookCreateDTO;
import cz.muni.fi.pv242.rest.model.BookDTO;
import cz.muni.fi.pv242.rest.model.DateModel;
import cz.muni.fi.pv242.service.BookService;
import cz.muni.fi.pv242.service.ReservationService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Stateless
public class RestBookServiceImpl implements RestBookService {

    @Inject
    BookService bookService;
    
    @Inject
    ReservationService reservationService;
    
    @Override
    public List<BookDTO> getAllBooks(){
    	return bookService.getAllBooks();
    }

    @Override
    public BookDTO addBook(BookCreateDTO book){
        return bookService.createBook(book);
    }

    @Override
    public BookDTO getBook(long id) {
    	return bookService.getBookByID(id);
    }

    @Override
    public BookDTO updateBook(BookDTO updatedBook) {
        return bookService.updateBook(updatedBook);
    }

    @Override
    public boolean deleteBook(long id) {
        return bookService.deleteBook(id);
    }

	@Override
	public DateModel whenIsBookAvailable(long id) {
		BookDTO book = bookService.getBookByID(id);
		List<Borrowing> borrowings = book.getBorrowings();
		DateModel borrowedTo = new DateModel();
		if(borrowings.size() == book.getTotalItems()){
			borrowedTo.setDate(borrowings.get(0).getEndDate());
			for (int i = 1; i < borrowings.size(); i++){
				if (borrowings.get(i).getStartDate().before(borrowedTo)){
					borrowedTo.setDate(borrowings.get(i).getStartDate());
				}
			}
		}
		return borrowedTo;
	}
	
	@Override
	public boolean isBookAvailable(long id){
		BookDTO book = bookService.getBookByID(id);
		return (book.getBorrowings().size() <  book.getTotalItems());
	}
	
	@Override
	public boolean canBookBeReserved(long id){
		BookDTO book = bookService.getBookByID(id);
		return ((book.getTotalItems() - book.getBorrowings().size()) == 0) && (reservationService.getByBook(id) == null);
	}
}
