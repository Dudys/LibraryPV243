package cz.muni.fi.pv242.rest;

import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/reservations")
public interface RestReservationService {
	
	@GET
	@Path("/")
	List<ReservationDTO> getAllReservations();

    @GET
    @Path("/{id}")
    ReservationDTO getReservation(@PathParam("id") long id);

    @PUT
    @Path("/update")
    ReservationDTO updateReservation(ReservationDTO updatedResetvation);
    
    @DELETE
    @Path("/{id}/delete")
    void deleteReservation(@PathParam("id") long id);
}
