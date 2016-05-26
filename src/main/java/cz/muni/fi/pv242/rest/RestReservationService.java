package cz.muni.fi.pv242.rest;

import cz.muni.fi.pv242.rest.model.ReservationCreateDTO;
import cz.muni.fi.pv242.rest.model.ReservationDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/reservation")
public interface RestReservationService {

    @POST
    @Path("/add")
    ReservationDTO addBook(ReservationCreateDTO reservation);

    @GET
    @Path("/{id}")
    ReservationDTO getBorrowing(@PathParam("id") long id);

    @PUT
    @Path("/update")
    ReservationDTO updateBook(ReservationDTO updatedResetvation);

    @PUT
    @Path("/{id}/delete")
    void deleteBook(@PathParam("id") long id);
}
