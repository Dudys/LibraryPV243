package cz.muni.fi.pv242.service.impl;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv242.jms.JMSService;
import cz.muni.fi.pv242.persistence.BookDAO;
import cz.muni.fi.pv242.persistence.BorrowingDAO;
import cz.muni.fi.pv242.persistence.ReservationDAO;
import cz.muni.fi.pv242.persistence.UserDAO;
import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;
import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;
import cz.muni.fi.pv242.rest.model.UserCreateDTO;
import cz.muni.fi.pv242.rest.model.UserDTO;
import cz.muni.fi.pv242.rest.model.UserUpdateDTO;
import cz.muni.fi.pv242.service.UserService;
import cz.muni.fi.pv242.persistence.entity.Book;
import cz.muni.fi.pv242.persistence.entity.Borrowing;
import cz.muni.fi.pv242.persistence.entity.Reservation;
import cz.muni.fi.pv242.persistence.entity.User;

/**
 * Created by honza on 5/18/16.
 */
@Stateless
public class UserServiceImpl implements UserService{

	@Inject
    UserDAO userDao;
    
	@Inject 
    ReservationDAO reservationDao;
    
	@Inject
    BorrowingDAO borrowingDao;
    
	@Inject
    BookDAO bookDao;

	@Inject
    JMSService jmsService;

    private Mapper mapper = new DozerBeanMapper();

    @Override
    public UserDTO createUser(UserCreateDTO user) {
        User usr = mapper.map(user, User.class);
        usr.setPasswordHash(hashPassword(user.getPassword()));
        userDao.create(usr);
        UserDTO newUser =  mapper.map(usr,UserDTO.class);

        //send message to JMS
        jmsService.sendMessage("Created new User: " + newUser);

        return newUser;
    }

    @Override
    public UserDTO updateUser(UserUpdateDTO user) {
        User usr = mapper.map(user, cz.muni.fi.pv242.persistence.entity.User.class);
        usr.setPasswordHash(hashPassword(user.getPassword()));
        userDao.update(usr);
        return mapper.map(usr, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(long id) {
    	User usr = userDao.getById(id);
    	return mapper.map(usr, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User usr = userDao.getByEmail(email);
        return mapper.map(usr, UserDTO.class);
    }

    @Override
    public void enableUser(long id) {
    	User usr = userDao.getById(id);
    	usr.setEnabled(true);
    	userDao.update(usr);
    }

    @Override
    public boolean disableUser(long id) {
        User usr = userDao.getById(id);
    	if(usr.getBorrowings().size() != 0){
    		return false;
    	}
    	List<Reservation> reservations = usr.getReservations();
    	usr.setReservations(new ArrayList<Reservation>());
    	if(reservations.size() != 0){
    		for (Reservation reservation : reservations) {
        		reservationDao.delete(reservation);
        		reservation = null;
			}
    	}
        usr.setEnabled(false);
        userDao.update(usr);
        return true;
    }

    @Override
    public boolean authenticate(String email, String password) {
        User u = userDao.getByEmail(email);
        return u.getPasswordHash().equals(hashPassword(password));
    }

    private String hashPassword(String password)
    {
        String generatedHash = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedHash = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedHash;
    }

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userDao.getAll();
    	List<UserDTO> userDTOs = new ArrayList<>();
    	for (User user : users) {
    		userDTOs.add(mapper.map(user, UserDTO.class));
		}
    	
    	return userDTOs;
	}

	@Override
	public List<ReservationDTO> getReservationsOfUser(long userId) {
		List<Reservation> reservations = userDao.getById(userId).getReservations();
		List<ReservationDTO> reservationsDTO = new ArrayList<>();
		for (Reservation reservation : reservations) {
			reservationsDTO.add(mapper.map(reservation, ReservationDTO.class));
		}
		return reservationsDTO;
	}

	@Override
	public List<BorrowingDTO> getBorrowingsOfUser(long userId) {
		List<Borrowing> borrowings = userDao.getById(userId).getBorrowings();
		List<BorrowingDTO> borrowingsDTO = new ArrayList<>();
		for (Borrowing borrowing : borrowings) {
			BorrowingDTO b = mapper.map(borrowing, BorrowingDTO.class);
	        
    		Book book = getBookOfBorrowing(borrowing);
    		b.setBorrowedBook(book);
    		borrowingsDTO.add(b);
		}
		return borrowingsDTO;
	}

	@Override
	public ReservationDTO addReservationToUser(long id, ReservationCreateDTO reservation) {
		Reservation r = mapper.map(reservation, Reservation.class);
		reservationDao.create(r);
		User u = userDao.getById(id);
		u.addReservation(r);
		userDao.update(u);
		return mapper.map(r, ReservationDTO.class);
	}

	@Override
	public BorrowingDTO addBorrowingToUser(long id, BorrowingCreateDTO borrowing, long bookId) {
		Borrowing b = mapper.map(borrowing, Borrowing.class);
		borrowingDao.create(b);
		User u = userDao.getById(id);
		u.addBorrowing(b);
		userDao.update(u);
		Book book = bookDao.getById(bookId);
		book.addBorrowing(b);
		bookDao.update(book);
		return mapper.map(b, BorrowingDTO.class);
	}

	@Override
	public void removeReservationOfUser(long id, long reservationId) {
		Reservation r = reservationDao.getById(reservationId);
		reservationDao.delete(r);
		User u = userDao.getById(id);
		u.removeReservation(r);
	}

	@Override
	public void removeBorrowingOfUser(long id, long borrowingId) {
		Borrowing b = borrowingDao.getById(borrowingId);
		User u = userDao.getById(id);
		u.removeBorrowing(b);
		borrowingDao.delete(b);
	}
	
	private Book getBookOfBorrowing(Borrowing borrowing){
		List<Book> books = bookDao.getAll();
		for (Book book : books) {
			if(book.getBorrowings().contains(borrowing)){
				return book;
			}
		}
		return null;
	}
}
