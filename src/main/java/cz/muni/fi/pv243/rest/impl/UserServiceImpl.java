package cz.muni.fi.pv243.rest.impl;

import javax.ejb.EJB;

import java.util.List;

import cz.muni.fi.pv243.rest.UserService;
import cz.muni.fi.pv243.rest.model.Borrowing;
import cz.muni.fi.pv243.rest.model.Reservation;

public class UserServiceImpl implements UserService {

	@EJB
	private cz.muni.fi.pv243.api.UserService service;

	public void borrowBook(long userId, Borrowing borrowing) {
		service.borrowBook(borrowing, userId);
	}

	public void returnBook(long userId, Borrowing borrowing) {
		service.returnBook(borrowing, userId);
	}

	public void reserveBook(long userId, Reservation reservation) {
		service.reserveBook(reservation, userId);

	}

	public void unreserveBook(long userId, Reservation reservation) {
		service.unreserveBook(reservation, userId);
	}

	public List<Reservation> getAllReservations(long userId) {
		return service.showUserReservations(userId);
	}

	public List<Borrowing> getHistory(long userId) {
		return service.showUserBorrowing(userId);
	}
}
