package cz.muni.fi.pv242.service;


import java.util.List;

import cz.muni.fi.pv242.rest.model.ReservationDTO;

/**
 * Created by Jan Duda on 5/26/2016.
 */
public interface ReservationService {

    ReservationDTO updateReservation(ReservationDTO r);
    ReservationDTO getReservationByID(long id);
    List<ReservationDTO> getAllReservations();
}
