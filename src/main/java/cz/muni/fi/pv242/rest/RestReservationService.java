package cz.muni.fi.pv242.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

import cz.muni.fi.pv242.rest.filters.Authenticate;
import cz.muni.fi.pv242.rest.model.ReservationDTO;

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
	@Authenticate
    @Path("/update")
    ReservationDTO updateReservation(ReservationDTO updatedResetvation);
    
    @DELETE
	@Authenticate
    @Path("/{id}/delete")
    void deleteReservation(@PathParam("id") long id);
}
