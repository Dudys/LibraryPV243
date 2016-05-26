package cz.muni.fi.pv242.service.impl;

import cz.muni.fi.pv242.persistence.ReservationDAO;
import cz.muni.fi.pv242.persistence.entity.Reservation;
import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;
import cz.muni.fi.pv242.service.ReservationService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

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

    @Override public ReservationDTO updateReservation(ReservationDTO r) {
            return null;
    }

    @Override
    public ReservationDTO updateBorrowing(ReservationDTO r) {
        Reservation reservation = mapper.map(r, Reservation.class);

        reservationDAO.update(reservation);
        return mapper.map(reservation, ReservationDTO.class);
    }

    @Override
    public void deleteReservation(ReservationDTO r) {
        reservationDAO.delete(mapper.map(r, Reservation.class));
    }

    @Override public ReservationDTO getReservationByID(long id) {
        return null;
    }

    public ReservationDTO deleteBorrowing(ReservationDTO r) {
        Reservation reservation = mapper.map(r, Reservation.class);

        reservationDAO.delete(reservation);
        return mapper.map(reservation, ReservationDTO.class);
    }

    @Override
    public ReservationDTO getBookByID(long id) {
        Reservation reservation = reservationDAO.getById(id);
        return mapper.map(reservation, ReservationDTO.class);
    }
}
