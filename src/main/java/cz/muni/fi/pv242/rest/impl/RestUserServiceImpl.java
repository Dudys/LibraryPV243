package cz.muni.fi.pv242.rest.impl;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.List;
import java.util.Properties;

import cz.muni.fi.pv242.rest.RestUserService;
import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;
import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;
import cz.muni.fi.pv242.rest.model.UserAuthenticateDTO;
import cz.muni.fi.pv242.rest.model.UserCreateDTO;
import cz.muni.fi.pv242.rest.model.UserDTO;
import cz.muni.fi.pv242.rest.model.UserUpdateDTO;
import cz.muni.fi.pv242.service.BorrowingService;
import cz.muni.fi.pv242.service.ReservationService;
import cz.muni.fi.pv242.service.UserService;

/**
 * Created by honza on 5/18/16.
 */
@Stateless
public class RestUserServiceImpl implements RestUserService {

    @Inject
    UserService userService;
    
    @Inject
    ReservationService reservationService;
    
    @Inject
    BorrowingService borrowingService;

    @Override
    public UserDTO addUser(UserCreateDTO user) {
        return userService.createUser(user);
    }
    
    @Override
    public UserDTO authUser(UserAuthenticateDTO user){
    	UserDTO u = userService.getUserByEmail(user.getEmail());
    	if(u == null){
    		return null;
    	}
    	if(userService.authenticate(user.getEmail(), user.getPassword())){
    		return u;
    	}
    	return null;
    }

    @Override
    public UserDTO getUser(long id) {
        return userService.getUserById(id);
    }

    @Override
    public void disableUser(long id) {
        userService.disableUser(id);

    }

    @Override
    public void enableUser(long id) {
        userService.enableUser(id);
    }

    @Override
    public UserDTO updateUser(UserUpdateDTO updatedUser) {
        return userService.updateUser(updatedUser);
    }

    @Override
    public UserDTO findUserByEmail(String email) {

        return userService.getUserByEmail(email);
    }

    @Override
    public String runJob() {
        JobOperator jo = BatchRuntime.getJobOperator();
        long jid = jo.start("createDataJob", new Properties());

        return "job started with jobId " + String.valueOf(jid);
    }

	@Override
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}

	@Override
	public List<ReservationDTO> getReservationsOfUser(long id) {
		return userService.getReservationsOfUser(id);
	}

	@Override
	public List<BorrowingDTO> getBorrowingsOfUser(long id) {
		return userService.getBorrowingsOfUser(id);
	}

	@Override
	public ReservationDTO addReservationToUser(long id, ReservationCreateDTO reservation) {
		return userService.addReservationToUser(id, reservation);
	}

	@Override
	public BorrowingDTO addBorrowingToUser(long id, BorrowingCreateDTO borrowing, long bookId) {
		return userService.addBorrowingToUser(id, borrowing, bookId);
	}

	@Override
	public void removeReservationOfUser(long id, long reservationId) {
		userService.removeReservationOfUser(id, reservationId);
	}

	@Override
	public void removeBorrowingOfUser(long id, long borrowingId) {
		userService.removeBorrowingOfUser(id, borrowingId);
	}
}
