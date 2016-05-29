package cz.muni.fi.pv242.rest;

import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    @Path("/add")
    BorrowingDTO addBorrowing(BorrowingCreateDTO borrowing);

    @GET
    @Path("/{id}")
    BorrowingDTO getBorrowing(@PathParam("id") long id);

    @PUT
    @Path("/update")
    BorrowingDTO updateBorrowing(BorrowingDTO updatedBorrowing);

    @DELETE
    @Path("/{id}/delete")
    void deleteBorrowing(@PathParam("id") long id);
}
