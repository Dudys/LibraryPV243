package cz.muni.fi.pv243.api;

import java.util.List;

public interface UserService {

	List<UserRole> getRole(String email);
	void addUser(UserDTO user, String creatorEmail) ;
	void desableUser(String email, String rootEmail);
	void enableUser(String email, String rootEmail);
	void updateUser(UserDTO user, String rootEmail);

	/**
	 *
	 * @param credentials username + hashed password
	 * @return
	 */
	boolean authenticate(String credentials);


	List<BorrowingDTO> showUserBorrowing(String email);
	List<ReservationDTO> showUserReservations(String email);


}
