package cz.muni.fi.pv242.service;

import java.util.List;

import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;
import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;
import cz.muni.fi.pv242.rest.model.UserAuthenticateDTO;
import cz.muni.fi.pv242.rest.model.UserCreateDTO;
import cz.muni.fi.pv242.rest.model.UserDTO;
import cz.muni.fi.pv242.rest.model.UserUpdateDTO;

/**
 * Created by honza on 5/18/16.
 */
public interface UserService {

	
    UserDTO createUser(UserCreateDTO user);
    UserDTO updateUser(UserUpdateDTO user);
    UserDTO getUserById(long id);
    UserDTO getUserByEmail(String email);
    void enableUser(long u);
    boolean disableUser(long u);
    boolean authenticate(String email, String password);
    List<UserDTO> getAllUsers();
    List<ReservationDTO> getReservationsOfUser(long userId);
    List<BorrowingDTO> getBorrowingsOfUser(long userId);
    ReservationDTO addReservationToUser(long id, ReservationCreateDTO reservation);
    BorrowingDTO addBorrowingToUser(long id, BorrowingCreateDTO borrowing, long bookId);
    void removeReservationOfUser(long id, long reservationId);
    void removeBorrowingOfUser(long id, long borrowingId);
}
