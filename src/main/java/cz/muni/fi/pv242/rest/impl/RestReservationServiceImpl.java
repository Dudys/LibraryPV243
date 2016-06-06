package cz.muni.fi.pv242.rest.impl;

import cz.muni.fi.pv242.rest.RestReservationService;
import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;
import cz.muni.fi.pv242.service.ReservationService;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Stateless
public class RestReservationServiceImpl implements RestReservationService {

    @Inject
    private ReservationService reservationService;

    @Override
    public ReservationDTO getReservation(long id) {
        return reservationService.getReservationByID(id);
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO updatedResetvation) {
        return reservationService.updateReservation(updatedResetvation);
    }

	@Override
	public List<ReservationDTO> getAllReservations() {
		return reservationService.getAllReservations();
	}
}
