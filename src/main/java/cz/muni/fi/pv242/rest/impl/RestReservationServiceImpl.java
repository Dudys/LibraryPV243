package cz.muni.fi.pv242.rest.impl;

import cz.muni.fi.pv242.rest.RestReservationService;
import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;
import cz.muni.fi.pv242.service.ReservationService;

import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
public class RestReservationServiceImpl implements RestReservationService {

    @Inject
    private ReservationService reservationService;


    @Override
    public ReservationDTO addBook(ReservationCreateDTO reservation) {
        return reservationService.createReservation(reservation);
    }

    @Override
    public ReservationDTO getBorrowing(long id) {
        return reservationService.getReservationByID(id);
    }

    @Override
    public ReservationDTO updateBook(ReservationDTO updatedResetvation) {
        return reservationService.updateReservation(updatedResetvation);
    }

    @Override
    public void deleteBook(long id) {
        reservationService.deleteReservation(reservationService.getReservationByID(id));
    }
}
