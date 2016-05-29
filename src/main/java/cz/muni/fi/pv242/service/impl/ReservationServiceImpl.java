package cz.muni.fi.pv242.service.impl;

import cz.muni.fi.pv242.persistence.ReservationDAO;
import cz.muni.fi.pv242.persistence.entity.Borrowing;
import cz.muni.fi.pv242.persistence.entity.Reservation;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;
import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;
import cz.muni.fi.pv242.service.ReservationService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
public class ReservationServiceImpl implements ReservationService {

    @Inject
    ReservationDAO reservationDAO;

    private Mapper mapper = new DozerBeanMapper();

    @Override
    public ReservationDTO createReservation(ReservationCreateDTO r) {
        Reservation reservation = mapper.map(r, Reservation.class);

        reservationDAO.create(reservation);
        return mapper.map(reservation, ReservationDTO.class);
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO r) {
        Reservation reservation = mapper.map(r, Reservation.class);

        reservationDAO.update(reservation);
        return mapper.map(reservation, ReservationDTO.class);
    }

    @Override
    public void deleteReservation(ReservationDTO r) {
        reservationDAO.delete(mapper.map(r, Reservation.class));
    }

    @Override
    public ReservationDTO getReservationByID(long id) {
        Reservation reservation = reservationDAO.getById(id);
        return mapper.map(reservation, ReservationDTO.class);
    }

	@Override
	public List<ReservationDTO> getAllReservations() {
		List<Reservation> reservations = reservationDAO.getAll();
    	List<ReservationDTO> reservationDTOs = new ArrayList<>();
    	for (Reservation reservation : reservations) {
    		reservationDTOs.add(mapper.map(reservation, ReservationDTO.class));
		}
    	
    	return reservationDTOs;
	}
}
