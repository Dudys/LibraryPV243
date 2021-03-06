package cz.muni.fi.pv242.service.impl;

import cz.muni.fi.pv242.persistence.BookDAO;
import cz.muni.fi.pv242.persistence.ReservationDAO;
import cz.muni.fi.pv242.persistence.entity.Book;
import cz.muni.fi.pv242.persistence.entity.Reservation;
import cz.muni.fi.pv242.rest.model.BookCreateDTO;
import cz.muni.fi.pv242.rest.model.BookDTO;
import cz.muni.fi.pv242.service.BookService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Stateless
public class BookServiceImpl implements BookService {

	@Inject
    BookDAO bookDAO;
	
	@Inject
	ReservationDAO reservationDAO;

    private Mapper mapper = new DozerBeanMapper();


    @Override
    public BookDTO createBook(BookCreateDTO b) {
        Book book = mapper.map(b, Book.class);

        bookDAO.create(book);
        return mapper.map(book, BookDTO.class);
    }

    @Override
    public BookDTO updateBook(BookDTO b) {
        Book book = mapper.map(b, Book.class);

        bookDAO.update(book);
        return mapper.map(book, BookDTO.class);
    }

    @Override
    public boolean deleteBook(long id) {
    	Reservation reservation = reservationDAO.getByBook(id);
		if(reservation != null){
			//reservationDAO.delete(reservation);
			return false;
		}
		
		Book book = bookDAO.getById(id);
    	if(book.getBorrowings().size() != 0){
    		return false;
    	}
		bookDAO.delete(book);
		
    	return true;
    }

    @Override
    public BookDTO getBookByID(long id) {
        Book book = bookDAO.getById(id);
        return mapper.map(book, BookDTO.class);
    }
    
    @Override
    public List<BookDTO> getAllBooks(){
    	List<Book> books = bookDAO.getAll();
    	List<BookDTO> bookDTOs = new ArrayList<>();
    	for (Book book : books) {
    		bookDTOs.add(mapper.map(book, BookDTO.class));
		}
    	
    	return bookDTOs;
    }
}
