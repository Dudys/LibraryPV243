package cz.muni.fi.pv242.service.impl;

import cz.muni.fi.pv242.persistence.BookDAO;
import cz.muni.fi.pv242.persistence.BorrowingDAO;
import cz.muni.fi.pv242.persistence.ReservationDAO;
import cz.muni.fi.pv242.persistence.UserDAO;
import cz.muni.fi.pv242.persistence.entity.Book;
import cz.muni.fi.pv242.persistence.entity.Borrowing;
import cz.muni.fi.pv242.persistence.entity.Reservation;
import cz.muni.fi.pv242.persistence.entity.User;
import cz.muni.fi.pv242.rest.model.BookDTO;
import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;
import cz.muni.fi.pv242.service.BorrowingService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Stateless
public class BorrowingServiceImpl implements BorrowingService {

	@Inject
	BookDAO bookDAO;
	
	@Inject
    BorrowingDAO borrowingDAO;
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	ReservationDAO reservationDAO;

    private Mapper mapper = new DozerBeanMapper();

    @Override
    public BorrowingDTO createBorrowing(BorrowingCreateDTO b) {
        Borrowing borr = mapper.map(b, Borrowing.class);

        borrowingDAO.create(borr);
        BorrowingDTO borrowing = mapper.map(borr, BorrowingDTO.class);
        
        Book book = getBookOfBorrowing(borr);
        borrowing.setBorrowedBook(book);
        return borrowing;
    }

    @Override
    public BorrowingDTO updateBorrowing(BorrowingDTO b) {
        Borrowing borr = mapper.map(b, Borrowing.class);

        borrowingDAO.update(borr);
        BorrowingDTO borrowing = mapper.map(borr, BorrowingDTO.class);
        
        Book book = getBookOfBorrowing(borr);
        borrowing.setBorrowedBook(book);
        return borrowing;
    }

    @Override
    public void deleteBorrowing(BorrowingDTO b) {
    	Borrowing borrowing = borrowingDAO.getById(b.getId());
    	
    	List<User> users = userDAO.getAll();
    	for (User user : users) {
			if(user.getBorrowings().contains(borrowing)){
				user.getBorrowings().remove(borrowing);
				userDAO.update(user);
				break;
			}
		}
    	
    	List<Book> books = bookDAO.getAll();
    	for (Book book : books) {
    		if(book.getBorrowings().contains(borrowing)){
    			book.getBorrowings().remove(borrowing);
				bookDAO.update(book);
				break;
			}
		}
    	
        borrowingDAO.delete(borrowing);
    }

    @Override
    public BorrowingDTO getBorrowingByID(long id) {
        Borrowing borr = borrowingDAO.getById(id);

        BorrowingDTO borrowing = mapper.map(borr, BorrowingDTO.class);
        
        Book book = getBookOfBorrowing(borr);
        borrowing.setBorrowedBook(book);
        return borrowing;
    }

	@Override
	public List<BorrowingDTO> getAllBorrowings() {
		List<Borrowing> borrowings = borrowingDAO.getAll();
    	List<BorrowingDTO> borrowingDTOs = new ArrayList<>();
    	for (Borrowing borrowing : borrowings) {
    		BorrowingDTO b = mapper.map(borrowing, BorrowingDTO.class);
        
    		Book book = getBookOfBorrowing(borrowing);
    		b.setBorrowedBook(book);
    		borrowingDTOs.add(b);
		}
    	
    	return borrowingDTOs;
	}
	
	@Override
	public BorrowingDTO addBorrowingFromReservation(long reservationId, BorrowingCreateDTO b){
		Borrowing borrowing = mapper.map(b, Borrowing.class);
		borrowingDAO.create(borrowing);
		
		Reservation reservation = reservationDAO.getById(reservationId);
		List<User> users = userDAO.getAll();
		
		for (User user : users) {
			if(user.getReservations().contains(reservation)){
				List<Reservation> reservations = user.getReservations();
				reservations.remove(reservation);
				user.setReservations(reservations);
				user.addBorrowing(borrowing);
				userDAO.update(user);
				break;
			}
		}
		
		Book book = bookDAO.getById(reservation.getReservedBook().getId());
		book.addBorrowing(borrowing);
		bookDAO.update(book);
		
		reservationDAO.delete(reservation);
		
		BorrowingDTO borr = mapper.map(borrowing, BorrowingDTO.class);
		borr.setBorrowedBook(book);
		return borr;
	}
	
	private Book getBookOfBorrowing(Borrowing borrowing){
		List<Book> books = bookDAO.getAll();
		for (Book book : books) {
			if(book.getBorrowings().contains(borrowing)){
				return book;
			}
		}
		return null;
	}
}
