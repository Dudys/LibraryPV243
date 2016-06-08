package cz.muni.fi.pv242.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

import cz.muni.fi.pv242.rest.filters.Authenticate;
import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/borrowings")
public interface RestBorrowingService {
	
	@GET
	@Path("/")
	List<BorrowingDTO> getAllBorrowings();

    @POST
    @Authenticate
    @Path("/add")
    BorrowingDTO addBorrowing(BorrowingCreateDTO borrowing);

    @GET
    @Path("/{id}")
    BorrowingDTO getBorrowing(@PathParam("id") long id);

    @PUT
    @Authenticate
    @Path("/update")
    BorrowingDTO updateBorrowing(BorrowingDTO updatedBorrowing);

    @DELETE
    @Authenticate
    @Path("/{id}/delete")
    void deleteBorrowing(@PathParam("id") long id);
    
    @POST
    @Path("/add/{reservationId}")
    BorrowingDTO addBorrowingFromReservation(@PathParam("reservationId") long id, BorrowingCreateDTO borrowing);
}
