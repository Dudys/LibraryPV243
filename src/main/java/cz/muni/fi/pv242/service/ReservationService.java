package cz.muni.fi.pv242.service;


import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;

/**
 * Created by Jan Duda on 5/26/2016.
 */
public interface ReservationService {

    ReservationDTO createReservation(ReservationCreateDTO r);
    ReservationDTO updateReservation(ReservationDTO r);
    void deleteReservation(ReservationDTO r);
    ReservationDTO getReservationByID(long id);
    ReservationDTO updateBorrowing(ReservationDTO r);
    ReservationDTO deleteBorrowing(ReservationDTO r);
    ReservationDTO getBookByID(long id);
}
